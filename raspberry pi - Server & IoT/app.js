var express = require('express');
var mysql = require('mysql');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyparser = require('body-parser');
var fs = require("fs");
var moment = require('moment');
const ejs = require('ejs');

//Router 선언
const login = require("./routes/login");
const index = require('./routes/index');
const settings = require('./routes/settings');
const connectAPI = require('./routes/connectAPI');
const chart = require('./routes/chart');
const air = require('./routes/air');
const community = require("./routes/community");

var app = express();

//Mysql 세팅
const client = mysql.createConnection({   // db 연결
        host:'localhost',
        user:'root',
        password:'24khands',
        database:'air_sharing'
});

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');
app.set('port',8080);

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));

// 바디 파서 설정
app.use(bodyparser.urlencoded({extended: false}));
app.use(bodyparser.json());

app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

// 외부 모듈
app.use('/mobile/member', login);   // 계정 관련  라우터 호출
app.use('/mobile/setting', settings); // 모바일 설정 라우터 호출
app.use('/mobile/opendata', connectAPI);
app.use('/chart', chart);
app.use('/device', air);
app.use('/community', community);
app.use('/', index);

app.use(express.static('./views'));

//오늘의 CO 차트
app.get('/mobile/chart/today/co',function(req,res){
  console.log('todayco');
  var jsonData;
  var List = new Array();

  client.query('select co, date from airdata where date(date) = date(now())', function(error, result){
    if(error){
      console.log('error' + error);
    }else{
      for(var i = 0; i<result.length; i++){
        var obj = {};
        var d = new Date(result[i].date);
        d = moment(d).format("YYYY-MM-DD HH:mm:ss");
        var v = result[i].co;

        obj['x'] = d;
        obj['y'] = v;
        List.push(obj);
      }

      jsonData = JSON.stringify(List);
      res.render('co',{dayData:jsonData});
    }
  });
});

//오늘의 CO2 차트
app.get('/mobile/chart/today/co2',function(req,res){
  console.log('todayco2');
  var jsonData;
  var List = new Array();

  client.query('select co2, date from airdata where date(date) = date(now())', function(error, result){
    if(error){
      console.log('error' + error);
    }else{
      for(var i = 0; i<result.length; i++){
        var obj = {};
        var d = new Date(result[i].date);
        d = moment(d).format("YYYY-MM-DD HH:mm:ss");
        var v = result[i].co2;

        obj['x'] = d;
        obj['y'] = v;
        List.push(obj);
      }

      jsonData = JSON.stringify(List);
      res.render('co2',{dayData:jsonData});
    }
  });
});

//오늘의 O2 차트
app.get('/mobile/chart/today/o2',function(req,res){
  console.log('todayo2');
  var jsonData;
  var List = new Array();

  client.query('select o2, date from airdata where date(date) = date(now())', function(error, result){
    if(error){
      console.log('error' + error);
    }else{
      for(var i = 0; i<result.length; i++){
        var obj = {};
        var d = new Date(result[i].date);
        d = moment(d).format("YYYY-MM-DD HH:mm:ss");
        var v = result[i].o2;

        obj['x'] = d;
        obj['y'] = v;
        List.push(obj);
      }

      jsonData = JSON.stringify(List);
      res.render('o2',{dayData:jsonData});
    }
  });
});

//오늘의 dust 차트
app.get('/mobile/chart/today/dust',function(req,res){
  console.log('todaydust');
  var jsonData;
  var List = new Array();

  client.query('select dust, date from airdata where date(date) = date(now())', function(error, result){
    if(error){
      console.log('error' + error);
    }else{
      for(var i = 0; i<result.length; i++){
        var obj = {};
        var d = new Date(result[i].date);
        d = moment(d).format("YYYY-MM-DD HH:mm:ss");
        var v = result[i].dust;

        obj['x'] = d;
        obj['y'] = v;
        List.push(obj);
      }

      jsonData = JSON.stringify(List);
      res.render('dust',{dayData:jsonData});
    }
  });
});

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

app.listen(app.get('port'),function(req, res){
	console.log('Start Server!');
});

module.exports = app;
