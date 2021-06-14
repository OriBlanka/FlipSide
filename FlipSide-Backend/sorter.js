const fs = require("fs");
const FCM = require("fcm-push");
const firebase = require("firebase");

const fcmKey = "AAAAl2T3cpA:APA91bFUxLrgV1sgAlkbQGqf8NE--IKg6Dw3kdREniKXHU9s2-v5bSG-m029IODhinJwXAHWATgk7zb3o2vNQfW41OrGNIgKK-GL3n_rRF0n-DGuq8Ynul-RtjmTtTwlCBV9bw09YcQy";

let token = "";
let fcm = new FCM(fcmKey);

const MINUTE = 60000;

var config = {
    apiKey: fcmKey,
    authDomain: "flipside-fc27f.firebaseapp.com",
    databaseURL: "https://flipside-fc27f-default-rtdb.firebaseio.com/",
};
firebase.initializeApp(config);
var database = firebase.database();


const webSites = ["walla",  "Maariv", "Mako - N12", "Ynet", "Kan-11", "Kipa", "News1", "Srugim"];


let pics = ["https://upload.wikimedia.org/wikipedia/commons/1/19/Benjamin_Netanyahu_2018.jpg", "https://upload.wikimedia.org/wikipedia/commons/8/84/Benny_Gantz_2019_%28cropped%29.jpg", "https://upload.wikimedia.org/wikipedia/commons/3/32/Naftali-Bennett.jpg", "https://www.idi.org.il/media/16389/prison.jpg"]
let subjects = ["בנימין נתניהו", "בני גנץ", "נפתלי בנט", "פרשת הקצין"]
let checkers = [bbCheck, gantzCheck, benetCheck, ltCheck]


async function sorter(subject, pic, checker) {
    let bundle = {
        News: [ ],
        "SubjectCategory": "פוליטיקה",
        "subjectName": subject,
        "subjectPic": pic
    }

    webSites.forEach(website => {
        fs.readFile(website + '.json', (err, file)  => {
            if (err) throw err

            let data = JSON.parse(file);
            let news = data.News

            for (let article of news){
                let title = article.title;
                let summary = article.summary;
                let link = article.link;
                let logo = article.webName;

                titleTokens = tokenizer(title)
                summaryTokens = tokenizer(summary)

                if (checker(titleTokens, summaryTokens)) {
                    var newArticle = {link: link, summary: summary, title: title, webName: logo}
                    bundle.News.push(newArticle)
                    break;
                }               
            }

            
            let json = JSON.stringify(bundle);

            fs.writeFile(subject + '.json', json, (err) => {
                if (err) throw err;
            });

        });

    });   
}

function tokenizer(str){
    str = str.replace(/\.|,|:|;/, "")
    tokens = str.split(" ");
    return tokens
}




function bbCheck(titleTokens, summaryTokens){
    if (titleTokens.includes("נתניהו") || titleTokens.includes("ביבי") || summaryTokens.includes("נתניהו") || summaryTokens.includes("ביבי")) return true;
    return false;
}
function gantzCheck(titleTokens, summaryTokens){
    if (titleTokens.includes("גנץ") || summaryTokens.includes("גנץ")) return true;
    return false;
}benetCheck

function benetCheck(titleTokens, summaryTokens){
    if (titleTokens.includes("נפתלי") || titleTokens.includes("בנט") || summaryTokens.includes("נפתלי") || summaryTokens.includes("בנט")) return true;
    return false;
}

function azaCheck(titleTokens, summaryTokens){
    if (titleTokens.includes("עזה") || summaryTokens.includes("עזה") || titleTokens.includes("מעזה") || summaryTokens.includes("מעזה") || titleTokens.includes("בעזה") || summaryTokens.includes("בעזה")) return true;
    return false;
}

function ltCheck(titleTokens, summaryTokens){
    if (titleTokens.includes("קצין") || summaryTokens.includes("קצין") || titleTokens.includes("הקצין") || summaryTokens.includes("הקצין") ) return true;
    return false;
}





function createBundles(){
    for (let i = 0; i < subjects.length; i++) {
        sorter(subjects[i], pics[i], checkers[i]);
    }
}

function pushBundles(){
    for (let i = 0; i < subjects.length; i++) {

        fs.readFile(subjects[i] + '.json', (err, file) => {
            if (err) throw err;
            
            let data = JSON.parse(file);
            database.ref('NewSubjects/').child(subjects[i]).set( data );
            console.log("Pushed: " + subjects[i]);
        });
    }
}



module.exports = { createBundles, pushBundles };