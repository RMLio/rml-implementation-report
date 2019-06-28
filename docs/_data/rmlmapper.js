const GraphqlExecutor = require('../graphqlexecutor');

module.exports = async () => {
    const executor = new GraphqlExecutor();
    const result = await executor.query('./_data/rmlmapper.nt', {
    "platform": { "@id": "http://www.w3.org/ns/earl#subject", "@singular": true },
    "testUri": { "@id": "http://www.w3.org/ns/earl#test", "@singular": true },
    "result": { "@id": "http://www.w3.org/ns/earl#result", "@singular": true },
    "outcomeUri": { "@id": "http://www.w3.org/ns/earl#outcome", "@singular": true },
    "Assertion": "http://www.w3.org/ns/earl#Assertion"
    }, `{... on Assertion {platform testUri result { outcomeUri }} }`, rmlmapper => {
        rmlmapper.forEach(result => {
            result.testName = result.testUri.replace("http://rml.io/test-cases/#","").toUpperCase();
            result.outcomeUri = result.result.outcomeUri[0];
            result.outcome = result.outcomeUri.replace("http://www.w3.org/ns/earl#","");
    });

    rmlmapper.sort((a,b) => {
        if (a.testName > b.testName) {
            return 1;
        } else {
            return -1;
        }
    });

    return rmlmapper;
});

console.log(result);

return result;
};
