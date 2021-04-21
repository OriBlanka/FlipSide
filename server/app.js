const express = require('express');
let app = express();
const request = require('request');
const fs = require('fs');
app.use(express.static('./JSON-Files'));
let path = require('path');

app.get('/', (req, res) => {
    let filePath = './JSON-Files/news.json';
    
    if(!(fs.existsSync(filePath))){ 
        res.send("There isn't such file - please try again");
        return; //If there isn't such file, there isn't reason to continue
    }
    //Reading the file from images folder and piping it
    fs.createReadStream(filePath).pipe(res);
});

app.listen(3000,() => {
    console.log('Listening on port 3000!')});