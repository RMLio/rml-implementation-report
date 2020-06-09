const {Client} = require("graphql-ld");
const {QueryEngineComunica} = require("graphql-ld-comunica");
const fs = require('fs');
const http = require('http');
const path = require('path');

class TestResultFetcher {

  async query(filePath, port) {
    const context = {
      "platform": { "@id": "http://www.w3.org/ns/earl#subject", "@singular": true },
      "testUri": { "@id": "http://www.w3.org/ns/earl#test", "@singular": true },
      "result": { "@id": "http://www.w3.org/ns/earl#result", "@singular": true },
      "outcomeUri": { "@id": "http://www.w3.org/ns/earl#outcome", "@singular": true },
      "Assertion": "http://www.w3.org/ns/earl#Assertion"
    };

    const query = `{
    ... on Assertion {
        platform @single
        testUri @single
        result @single { 
            outcomeUri 
        }
      } 
    }`;

    this._startServer(filePath, port);

    const comunicaConfig = {
      sources: [
        "http://localhost:" + port
      ],
    };

    const client = new Client({context, queryEngine: new QueryEngineComunica(comunicaConfig)});
    const result = (await client.query({query: query})).data;

    result.forEach(test => {
      test.testName = test.testUri.replace("http://rml.io/test-cases/#", "").toUpperCase();
      test.outcomeUri = test.result.outcomeUri[0];
      test.outcome = test.outcomeUri.replace("http://www.w3.org/ns/earl#", "");
    });

    result.sort((a, b) => {
      if (a.testName > b.testName) {
        return 1;
      } else {
        return -1;
      }
    });

    this._stopServer();

    return result;
  }

  _startServer(filePath, port) {
    this.server = http.createServer(function (req, res) {
      fs.readFile(path.join(__dirname, filePath), function (err,data) {
        if (err) {
          res.writeHead(404);
          res.end(JSON.stringify(err));
          return;
        }
        res.setHeader('Content-Type', 'text/turtle');
        res.writeHead(200);
        res.end(data);
      });
    }).listen(port);
  }

  _stopServer() {
    this.server.close();
  }
}

module.exports = TestResultFetcher;
