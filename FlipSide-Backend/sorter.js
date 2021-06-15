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
// const webSites = ["walla",  "Maariv", "Mako - N12", "Ynet", "Kipa", "News1", "Srugim"];



let subjectsPolitical = ["בנימין נתניהו", "בני גנץ", "נפתלי בנט", "פרשת הקצין"]
let checkersPolitical = [bbCheck, gantzCheck, benetCheck, ltCheck]
let picsPolitical = ["https://upload.wikimedia.org/wikipedia/commons/1/19/Benjamin_Netanyahu_2018.jpg", "https://upload.wikimedia.org/wikipedia/commons/8/84/Benny_Gantz_2019_%28cropped%29.jpg", "https://upload.wikimedia.org/wikipedia/commons/3/32/Naftali-Bennett.jpg", "https://www.idi.org.il/media/16389/prison.jpg"]

let subjectsWorld = ["ולדימיר פוטין"]
let checkersWorld = [putinCheck]
let picsWorld = ["https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Vladimir_Putin_%282020-02-20%29.jpg/1200px-Vladimir_Putin_%282020-02-20%29.jpg"]

let subjectsSecurity = ["עזה", "מצעד הדגלים"]
let checkersSecurity = [gazaCheck, flagsCheck]
let picsSecurity = ["https://static.gisha.org/uploads/sites/5/2014/04/gazamap525.jpg", "https://www.kolhair.co.il/wp-content/uploads/2019/05/424da200c2b3abd220ff1b204ddbdb9e.jpg"]

let subjectsSport = ["כוכב נבחרת דנמרק"]
let checkersSport = [eriksenCheck]
let picsSport = ["https://www.ynet.co.il/PicServer5/2019/08/10/9415054/941504601000100980666no.jpg"]

let subjects = [subjectsPolitical, subjectsWorld, subjectsSecurity, subjectsSport]
let checkers = [checkersPolitical, checkersWorld, checkersSecurity, checkersSport]
let categories = ["פוליטי", "בעולם", "ביטחון", "ספורט"]
let pics = [picsPolitical, picsWorld, picsSecurity, picsSport]



async function sort(subject, pic, checker, category) {
    var bundle = {
        News: [ ],
        "SubjectCategory": category,
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
            if (website == webSites[webSites.length - 1]){
                
                let json = JSON.stringify(bundle);

                fs.writeFile(subject + '.json', json, (err) => {
                    if (err) throw err;
                
                 });
            }
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

function ltCheck(titleTokens, summaryTokens){
    if (titleTokens.includes("קצין") || summaryTokens.includes("קצין") || titleTokens.includes("הקצין") || summaryTokens.includes("הקצין") ) return true;
    return false;
}

function gazaCheck(titleTokens, summaryTokens){
    if (titleTokens.includes("עזה") || summaryTokens.includes("עזה") || titleTokens.includes("מעזה") || summaryTokens.includes("מעזה") || titleTokens.includes("בעזה") || summaryTokens.includes("בעזה")) return true;
    return false;
}

function putinCheck(titleTokens, summaryTokens){
    if (titleTokens.includes("פוטין") || summaryTokens.includes("פוטין")  ) return true;
    return false;
}

function eriksenCheck(titleTokens, summaryTokens){
    if (titleTokens.includes("אריקסן") || summaryTokens.includes("אריקסן") || titleTokens.includes("דנמרק") || summaryTokens.includes("דנמרק") ) return true;
    return false;
}

function flagsCheck(titleTokens, summaryTokens){
    if (titleTokens.includes("הדגלים") || summaryTokens.includes("הדגלים")) return true;
    return false;
}



function createBundles(){
    for (let i = 0; i < subjects.length; ++i) { 
        for (let j = 0; j < subjects[i].length; ++j) {
            sort(subjects[i][j], pics[i][j], checkers[i][j], categories[i]);    
        }
    }
}

function pushBundles(){
    for (let i = 0; i < subjects.length; ++i) { 
        for (let j = 0; j < subjects[i].length; ++j) {

            fs.readFile(subjects[i][j] + '.json', (err, file) => {
                if (err) throw err;
                
                let data = JSON.parse(file);
                if (data.News.length > 0){
                    database.ref('NewSubjects/').child(subjects[i][j]).set( data );
                    console.log("Pushed: " + subjects[i][j]);
                }
            });
        }
    }
}

module.exports = { createBundles, pushBundles };