const express = require("express")
const fs = require("fs") // Needs to be substituted for a method that related to a database

var app = express()

app.get("/", (req, res) => {
    res.send("Hello, World!")
})

app.get("/listJobs", (req, res) => {
    fs.readFile( __dirname + "/" + "mock.json", 'utf8', function (err, data) {
        console.log(data)
        res.send(data)
     })
})

app.listen(3000)