const express = require('express');
const mysql = require('mysql');
const http = require('http');
const bodyparser = require('body-parser');
const router = express.Router();

const client = mysql.createConnection({   // db 연결
        host:'localhost',
        user:'root',
        password:'24khands',
        database:'air_sharing'
});

// 바디 파서 설정
router.use(bodyparser.urlencoded({extended: false}));
router.use(bodyparser.json());

//IoT -> 서버 전송 받음
router.post('/outsidedata/transmission',function(req,res){
  var data = req.body;
  data = JSON.stringify(data);
  data = JSON.parse(data);

  //데이터 Parsing
  var userid = data.userid;
  var co = data.co[0];
  var co_result = data.co[1];
  var co2 = data.co2[0];
  var co2_result = data.co2[1];
  var o2 = data.o2[0];
  var o2_result = data.o2[1];
  var dust = data.dust[0];
  var dust_result = data.dust[1];
  var finedust = data.finedust[0];
  var finedust_result = data.finedust[1];
  var temp = data.temp;
  var humi = data.humi;
  var latitude = data.latitude;
  var longitude = data.longitude;
  var date = new Date();

  client.query('INSERT INTO airdata (uuid, co2, co2_result, co, co_result, o2, o2_result, dust, dust_result, finedust, finedust_result, temp, humidity, latitude,longitude,date) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)',
[userid,co2,co2_result,co,co_result,o2,o2_result,dust,dust_result,finedust,finedust_result,temp,humi,latitude,longitude,date], function(error, result){
  if(error){
    console.log("server DB insert fail");
    console.log(error);
  }else{
    console.log('server DB insert success');
  }
});


  res.send("");
});

//모듈 설정
module.exports = router
