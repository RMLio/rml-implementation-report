/**
 * author: Pieter Heyvaert (pheyvaer.heyvaert@ugent.be)
 * Ghent University - imec - IDLab
 */

const newEngine = require('@comunica/actor-init-sparql-rdfjs').newEngine;
const N3 = require('n3');
const Q = require('q');
const fs = require('fs');
const https = require('https');

module.exports = async () => {
  const result = await getTestCases('./carml.ttl');

  return result;
};

async function getTestCases(path) {
  const deferred = Q.defer();
  const rdfjsSource = await getRDFjsSourceFromFile(path);
  const engine = newEngine();
  const testcases = [];

  engine.query(`SELECT * {
      ?s a <http://www.w3.org/ns/earl#Assertion>;
      <http://www.w3.org/ns/earl#subject> ?platform;
        <http://www.w3.org/ns/earl#test> ?test .
      ?test <http://www.w3.org/ns/earl#outcome> ?result 
  }`,
    {sources: [{type: 'rdfjsSource', value: rdfjsSource}]})
    .then(function (result) {
      result.bindingsStream.on('data', async function (data) {
        data = data.toObject();

        let output;
        let outputStr;

       // if (data['?output']) {
       //   output = data['?output'].value;
       //   outputStr = (await _getHTTP(output)).replace(/</g, '&lt;').replace(/>/g, '&gt;')
       // }

        testcases.push({
          testUri:  data['?test'].value,
          testName: data['?test'].value.replace("http://rml.io/test-case/",""),
          outcomeUri: data['?result'].value,
          outcome: data['?result'].value.replace("http://www.w3.org/ns/earl#","")
        });
      });

      result.bindingsStream.on('end', function () {
        testcases.sort( (a, b) => {
          if (a.testName > b.testName) {
            return 1;
          } else {
            return -1;
          }
        });

        deferred.resolve(testcases);
      });
    });

  return deferred.promise;
}

/**
 * This method returns an RDFJSSource of an file
 * @param {string} path: path of the file
 * @returns {Promise}: a promise that resolve with the corresponding RDFJSSource
 */
function getRDFjsSourceFromFile(path) {
  const deferred = Q.defer();

  fs.readFile(path, 'utf-8', (err, data) => {
    if (err) deferred.reject(err);

    const parser = N3.Parser();
    const store = N3.Store();

    parser.parse(data, (err, quad, prefixes) => {
      if (err) {
        console.error(err);
        deferred.reject(err);
      } else if (quad) {
        store.addQuad(quad);
      } else {
        const source = {
          match: function (s, p, o, g) {
            return require('streamify-array')(store.getQuads(s, p, o, g));
          }
        };

        deferred.resolve(source);
      }
    });
  });

  return deferred.promise;
}

function _getHTTP(url) {
  const deferred = Q.defer();

  https.get(url, (res) => {
    const { statusCode } = res;

    let error;
    if (statusCode !== 200) {
      console.log(url);
      error = new Error('Request Failed.\n' +
        `Status Code: ${statusCode}`);
    }

    if (error) {
      console.error(error.message);
      // consume response data to free up memory
      res.resume();
      return;
    }

    //res.setEncoding('utf8');
    let rawData = '';
    res.on('data', (chunk) => { rawData += chunk; });
    res.on('end', () => {
      try {
        deferred.resolve(rawData);
      } catch (e) {
        console.error(e.message);
      }
    });
  }).on('error', (e) => {
    console.error(`Got error: ${e.message}`);
  });

  return deferred.promise;
}
