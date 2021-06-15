const RSS_URL = ["https://rss.walla.co.il/feed/2686", "https://www.maariv.co.il/Rss/RssFeedsPolitiMedini", "https://rcs.mako.co.il/rss/news-military.xml", "http://www.ynet.co.il/Integration/StoryRss1854.xml", "https://www.kan.org.il/rss/allnews.ashx", "http://v20016.kipa.co.il/!bilder/_xml/news.xml", "https://www.news1.co.il/Rss/", "https://www.srugim.co.il/feed"]
const webSites = ["walla",  "Maariv", "Mako - N12", "Ynet", "Kan-11", "Kipa", "News1", "Srugim"];
const webLogo = ["https://upload.wikimedia.org/wikipedia/he/thumb/0/01/Walla_new.svg/1200px-Walla_new.svg.png", "https://www.maariv.co.il/HttpHandlers/ShowImage.ashx?id=404311&w=500&h=380", "https://img.mako.co.il/2020/02/17/SHAREIMG.png", "https://www.ynet.co.il/images/default_ynetLogo200_200.jpg", "https://yt3.ggpht.com/GOlHyQrQt2815v4nCO8YeUdvaoUqTcQ4LzaJESvndY4IB7Ll70HUYbS-vbpyXzQ8u8lst1FM=s900-c-k-c0x00ffffff-no-rj", "https://static14.kipa.co.il/images/logo.png", "https://shoob-law.co.il/news/News1.jpg", "https://www.srugim.co.il/wp-content/uploads/2018/08/%D7%9C%D7%95%D7%92%D7%95-%D7%9E%D7%9C%D7%91%D7%9F-%D7%A1%D7%A8%D7%95%D7%92%D7%99%D7%9D.png"];

const fetch = require("node-fetch");
const DOMParser = require("dom-parser");
const parser = new DOMParser();
const fs = require("fs");



async function fetcher(rss, website, logo) {
    await fetch(rss)
    .then(response => response.text())
    .then(str => {
 
        doc = parser.parseFromString(str, "text/xml")
        var titles =        doc.getElementsByTagName("title");
        var descriptions =  doc.getElementsByTagName("description");

        if (website == "Kan-11"){
            var links = str.match(/<link_url>(.*)<\/link_url>|<default>(.*)<\/default>/g);    
        } else if (website == "Kipa" || website == "News1" || website == "Ynet") {
            str = str.replace(/<description>/g, "\n");
            var links = str.match(/<link>(.*)<\/link>/g);
        } else {
            var links = str.match(/<link>(.*)<\/link>/g);    
        }
        
        const isWalla = (website == "walla");
        
        var subject = titles[0].childNodes[0].text;

        var bundle = {
            News: [ ],
            "SubjectCategory": subject,
            "subjectName": " ",
            "subjectPic": "https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png"
        }
  
        const start = (isWalla || website == "Ynet") ? 2 : 1;
        const limit = (isWalla) ? titles.length : titles.length - 1;

        for (var i = start; i < limit; i++){

            if (isWalla){
                if (i < titles.length - 1) {
                    var desc = descriptions[i-1].childNodes[1].childNodes[2].text;
                    var titleQuote = titles[i].childNodes[0].text;
                    var titleEx = /\[CDATA\[(.*)\]\]/
                    var title0 = titleEx.exec(titleQuote);

                    var title = title0[1]; 
                    var link = links[i].replace(/<link>/g, "").replace(/<\/link>/g, "");
                }
            } else if (website == "Ynet") {
                var desc = "ללא תקציר"

                var title = titles[i].childNodes[0].text;
                              
                var link = links[i].replace(/<link>/g, "").replace(/<\/link>/g, "");

            } else if (website == "Kan-11"){
                var titleQuote = titles[i].childNodes[0].text;
                var titleEx = /\[CDATA\[(.*)\]\]/
                var title0 = titleEx.exec(titleQuote);
                var title = title0[1];

                var descQuote = descriptions[i].childNodes[0].text;
                var descEx = /\[CDATA\[(.*)\]\]/
                var desc0 = descEx.exec(descQuote);
                var desc = desc0[1];
                
                var link = links[i-1].replace(/<link_url>/g, "").replace(/<\/link_url>/g, "");
                link = link.replace(/<default>/g, "").replace(/<\/default>/g, "");
                
                if (i == 20) {
                    i = limit;
                }

            } else if (website == "Kipa") {
                var title = titles[i+1].childNodes[0].text;

                var desc = descriptions[i].childNodes[2].text.replace(/]|]|>/g, "");

                var link = links[i+1].replace(/<link>/g, "").replace(/<\/link>/g, "");

            } else if (website == "News1"){
                var title = titles[i].childNodes[0].text;
                title = title.replace(/<!\[CDATA\[/, "").replace(/\]\]>/, "");

                var desc = descriptions[i-1].childNodes[2];
                if (desc){
                    desc = desc.text.replace(/]|]|>/g, "");
                } else {
                    desc = "";
                }

                var link = links[i].replace(/<link>/g, "").replace(/<\/link>/g, "");
                link = link.replace(/&amp;/, "&")             

            } else {

                var title = titles[i].childNodes[0].text;

                if (website == "Srugim"){
                    var desc = descriptions[i].childNodes[1].childNodes[0].text
                } else {
                    var desc = descriptions[i].childNodes[0].text.replace(/&lt;.*&gt;&lt;br\/&gt;/g, "")
                }
                
                var link = links[i].replace(/<link>/g, "").replace(/<\/link>/g, "");
                
            }
            
            desc = desc.replace(/&lt;|br\/|&gt|;/g, "");
            desc = desc.replace(/&quot|&ampquot;?/g, "\"");
            desc = desc.replace(/&#39;?/g, "\'");
            desc = desc.replace(/&#160/g, "");
            desc = desc.replace(/  +/g, "");
            desc = desc.replace(/(\r\n|\n|\r)/gm, "");

            title = title.replace(/&quot;?/g, "\"");
            title = title.replace(/&#039;/g, "\'");
            title = title.replace(/&#034;/g, "\"");
            title = title.replace(/&apos;/g, "\'");

        
            var newArticle = {link: link, summary: desc, title: title, webName: logo}

            bundle.News.push(newArticle)
        }

        let data = JSON.stringify(bundle);

        fs.writeFile(website + '.json', data, (err) => {
            if (err) throw err;
        });
    })
   
    .catch(error => {
        console.log(error + "\nERROR");
    });
}

function fetchArticles(){
    for (let i = 0; i < webSites.length; i++) {
        fetcher(RSS_URL[i], webSites[i], webLogo[i]);
    }
}

fetchArticles()
module.exports = { fetchArticles };