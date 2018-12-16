var express = require('express');
var mysql = require('mysql');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyparser = require('body-parser');
var fs = require("fs");
const ejs = require('ejs');
var moment = require('moment');
const router = express.Router();

var app = express();

const dbConnection = mysql.createConnection({ // db 연결
  host: 'localhost',
  port: '8080',
  user: 'root',
  password: '24khands',
  database: 'air_sharing',
  multipleStatements: true
});

// 바디 파서 설정
router.use(bodyparser.urlencoded({
  extended: false
}));
router.use(bodyparser.json());

//오늘의 CO 차트
router.get('/co', function(req, res) {
  var uuid = 'QaSg14';
  var allQuery = '';
  var query = 'SELECT co,date FROM airdata WHERE uuid = \'QaSg14\'';
  var jsonData;

  /* 오늘의 일산화탄소 수치 */
  var limitDay = moment();
  limitDay = limitDay.subtract('days');
  limitDay = limitDay.format('YYYY-MM-DD HH:MM:SS');

    var today = moment()
    today = today.subtract('days');
    today = today.format('YYYY-MM-DD');

/*
    var tomorrow = moment().add(1, 'days');
    tomorrow = tomorrow.format('YYYY-MM-DD');
    console.log('limitDay: ' + limitDay);
    console.log('todayDay: ' + today);
    console.log('tomorrow: ' + tomorrow);

    //allQuery = allQuery + query + 'AND date =' + '"' + limitDay + '";';
    allQuery = allQuery + query + ' AND date >=' + '"' + today + '"' + '  and date <' + '"' + tomorrow + '";';
    console.log("쿼리문: " + allQuery);*/

  /*
    for(var i=0; i<7; i++){
      var limitDay = moment();
      limitDay = limitDay.subtract(i, 'days');
      limitDay = limitDay.format('YYYY-MM-DD');
      console.log('limitDay: ' + limitDay);
      allQuery = allQuery + query +'AND date ='+ '"'+limitDay+'"'+';';
      console.log("쿼리문 : " + allQuery);
    }
  */

  //allQuery = allQuery.substring(0,allQuery.length-1);

  dbConnection.query('select * from airdata where date(date) = date(now())',function(error,result){
    if(error){
      console.log('error :' + error);
    }else{
      console.log(result);
      res.end();
    }
  });
/*
  var co = new Array();
  var List = new Array();

dbConnection.connect();
  dbConnection.query(allQuery, function(error, results) {
    console.log("db안");
    if (error)
      console.error(error);
    else {
      console.log("result: " + results);
      console.log("size:" + results.length);
      for (var i = 0; i < results.length; i++) {
        co.push(results[i]);
        console.log("co: " + JSON.stringify(co));
        var obj = new Object();
        obj.x = limitDay;
        obj.y = co[i];
        List.push(obj);
      }
      jsonData = JSON.stringify(List);
    }
    console.log("jsonData: " + jsonData);
  });
  dbConnection.end();
  //res.render('co', {dayData:jsonData});
  res.render('co', {
    dayData: jsonData
  });*/
});

//오늘의 CO2 차트
router.get('/co2', function(req, res) {
  res.render('co2');
});

//오늘의 O2 차트
router.get('/o2', function(req, res) {
  res.render('o2');
});

//오늘의 dust 차트
router.get('/dust', function(req, res) {
  res.render('dust');
});

//모듈 설정
module.exports = router
