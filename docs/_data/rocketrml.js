/**
 * author: Pieter Heyvaert (pieter.heyvaert@ugent.be)
 * Ghent University - imec - IDLab
 */

const TestResultFetcher = require('../test-result-fetcher');

module.exports = async () => {
    const fetcher = new TestResultFetcher();
    return await fetcher.query('./_data/rocketrml.nt', 23004);
};
