const sorter = require("./sorter");
const fetcher = require("./fetcher");

const MINUTE = 60000;


function runBackEnd() {
    new Promise((resolve, reject) => {
        fetcher.fetchArticles()
        resolve();
    })
    .then(() => {
        console.log("Done fetching - sorting")
        sorter.createBundles()
    })
    .then(() => {
        console.log("Done sorting - pushing")
        setTimeout(sorter.pushBundles(), 1000)
    })
    .then(() => {
        console.log('Cycle done, wait for next');
    })
    .catch(error => {
        console.log("\nERROR\n" + error );
    });
}

setInterval(runBackEnd, 30 *  MINUTE)
// fetcher.fetchArticles()
// sorter.createBundles()
// sorter.pushBundles()
// runBackEnd()