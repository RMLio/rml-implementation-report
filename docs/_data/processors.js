/**
 * author: Ben De Meester (ben.demeester@ugent.be)
 * Ghent University - imec - IDLab
 */

const TestResultFetcher = require('../test-result-fetcher');

module.exports = async () => {
    const processors = [
        {
            id: 'rmlmapper',
            name: "RMLMapper",
            version: "4.9.0",
            testDate: "2020-09-17",
            contact: "Anastasia Dimou",
            url: "https://github.com/rmlio/rmlmapper-java"
        }, {
            id: 'carml',
            name: 'CARML',
            version: "0.3.0",
            testDate: "2020-09-15",
            contact: "Pano Maria",
            url: "https://github.com/carml/carml"
        }, {
            id: 'rocketrml',
            name: 'RocketRML',
            version: "1.0.6",
            testDate: "2019-06-28",
            contact: "Umutcan Simsek",
            url: "https://github.com/semantifyit/RocketRML"
        }, {
            id: 'sdmrdfizer',
            name: 'SDM-RDFizer',
            version: "3.2",
            testDate: "2020-06-08",
            contact: "Umutcan Simsek",
            url: "https://github.com/SDM-TIB/SDM-RDFizer"
        }, {
            id: 'rmlstreamer-static',
            name: 'RMLStreamer',
            version: "2.0.0",
            testDate: "2020-06-10",
            contact: "Anastasia Dimou",
            url: "https://github.com/RMLio/RMLStreamer"
        }, {
            id: 'chimera',
            name: 'Chimera',
            version: "2.1",
            testDate: "2021-02-18",
            contact: "Mario Scrocca",
            url: "https://github.com/cefriel/chimera"
        }, {
            id: 'morph-kgc',
            name: 'Morph-KGC',
            version: "1.4.0",
            testDate: "2021-11-11",
            contact: "Julián Arenas-Guerrero",
            url: "https://github.com/oeg-upm/Morph-KGC"
            // }, {
            //     id: '',
            //     name: 'GeoTriples',
            //     version: "",
            //     testDate: "",
            //     contact: "Nikolaos Karalis",
            //     url: "http://geotriples.di.uoa.gr/"
            // }, {
            //     id: '',
            //     name: 'Ontario',
            //     version: "",
            //     testDate: "",
            //     contact: "Kemele M. Endris",
            //     url: "https://github.com/WDAqua/Ontario"
        },
    ];

    let port = 23001;
    for (const processor of processors) {
        const fetcher = new TestResultFetcher();
        processor.results = await fetcher.query(`./_data/${processor.id}.nt`, port);
        port++;
    }
    const results = {};
    for (const processor of processors) {
        const stats = {}
        for (const result of processor.results) {
            result.testUri = result.testUri.toLowerCase();
            if (!stats[result.outcome]) {
                stats[result.outcome] = 0;
            }
            stats[result.outcome]++;
            if (!results[result.testUri]) {
                results[result.testUri] = {};
            }
            results[result.testUri][processor.id] = result;
        }
        processor.stats = stats;
    }
    return {
        processors,
        results: Object.keys(results).map(testUri => {
            const row = {
                testUri,
                testName: testUri.replace("http://rml.io/test-cases/#", "").toUpperCase(),
                results: []
            };
            for (const processor of processors) {
                if (!results[testUri][processor.id]) {
                    row.results.push(null);
                } else {
                    row.results.push(results[testUri][processor.id])
                }
            }
            return row;
        })
    }
};