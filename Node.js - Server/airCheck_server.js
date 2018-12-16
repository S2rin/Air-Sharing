var express = require('express');
var bodyparser = require('body-parser');
var http = require('http');
var mysql = require('mysql');

const gpio = require('wiring-pi');
const RED = 23;
const GREEN = 24;
const BLUE = 25;
const BUTTON = 0;
const LED = 1;

var airArray = [];
var count = 0;
var button_data = 0;
var button_state = 0;
var bcount = 0;
var jsonText;

var airResult = [];

var sid = 0; //timeout용

var app = express();

// 바디 파서 설정
app.use(bodyparser.urlencoded({extended: false}));
app.use(bodyparser.json());

//Mysql 세팅
const client = mysql.createConnection({   // db 연결
        host:'localhost',
        user:'root',
        password:'gachon654321',
        database:'airsharing'
});

//아두이노와 시리얼포트 연결을 한다
var SerialPort = require('serialport');
var port = new SerialPort('/dev/ttyACM0', {
baudrate: 9600,
parser: SerialPort.parsers.readline('\n')
});

//Arduino port on
port.on('open', function() {
  port.write('1', function(err) {
    if(err){
      return console.log('Error on write : ', err.message);
    }
    console.log('Run arduino');
  });
});

port.on('error', function() {
  console.log('Error : ', err.message);
});

//가져온 값 찍어내기
port.on('data', function(data) {
  console.log('air data : ', data);

  //port.write(data);
  airArray.push(data);


	for(var i = 0; i < airArray.length; i++){
		console.log(i + " : " + airArray[i]);
	}
  console.log("The arduino is running...");
});

//날짜 생성
Number.prototype.padLeft = function(base,chr){
    var  len = (String(base || 10).length - String(this).length)+1;
    return len > 0? new Array(len).join(chr || '0')+this : this;
}
var d = new Date,
    dformat = [d.getFullYear(), (d.getMonth()+1).padLeft(),
               d.getDate().padLeft()].join('-') +' ' +
              [d.getHours().padLeft(),
               d.getMinutes().padLeft(),
               d.getSeconds().padLeft()].join(':');

//               console.log("dformat : " + dformat);


const callTemp = function() {

        //라즈베리파이로 온습도 읽기
        const sensorLib = require("node-dht-sensor");
        const sensor = {
           sensors:[{name: "Indoor", type: 22, pin:21}],
           read: function() {
                for (var index in this.sensors) {
                var s = sensorLib.read(this.sensors[index].type, this.sensors[index].pin);
                console.log(this.sensors[index].name + "온도 : " + s.temperature.toFixed(1) + "도 /" +"습도 : " + s.humidity.toFixed(1) + "%");
                }
               // setTimeout(function() { sensor.read(); }, 2500);

               airArray.push(s.temperature.toFixed(1),s.humidity.toFixed(1)); //array에 추가
         }
        };

        sensor.read();
        for(var i = 0; i < airArray.length; i++){
      		console.log(i + " : " + airArray[i]);
      	}
        console.log("DHT22 is working...");
}


//data judgement handler
const dataJudge_handler = function(){
  if(airArray[3] > 80) {
    gpio.digitalWrite(RED, 1); //dust 수치가 80이상이면 RED LED ON
    gpio.delay(5000);
    console.log("BAD air-Quality");
  }else if (airArray[3] > 30 && airArray[3] <= 80) {
    gpio.digitalWrite(GREEN, 1); //dust 수치가 30-80이면 GREEN LED ON
    gpio.delay(5000);
    console.log("NOT BAD air-Quality");
  }else if(airArray[3] <= 30) {
    gpio.digitalWrite(BLUE, 1); //dust 수치가 30이하면 BLUE LED ON
    gpio.delay(5000);
    console.log("GOOD air-Quality");
  }else {
    console.log("Something Wrong with your device");
  }
}//data judgement handler end


const button_handler = function(){
  button_data = gpio.digitalRead(BUTTON);
  if(!button_data){
    if(count==0){ //버튼이 눌린 경우
      console.log("pressed the button");
      gpio.digitalWrite(LED, 1); //단색LED ON

      callTemp();
      dataJudge_handler();
      co2_judgement();
      co_judgement();
      o2_judgement();
      finedust_judgement();
      dust_judgement();
      data_handler();

      count += 1;
    }//버튼이 눌린 경우 끝
    //버튼이 다시 눌린 경우 시작
    else if(count == 1){
      console.log("Repressed the button");
      gpio.digitalWrite(LED, 0); //단색LED OFF
      gpio.digitalWrite(RED, 0);
      gpio.digitalWrite(GREEN, 0);
      gpio.digitalWrite(BLUE, 0);
      gpio.digitalWrite(LED, 0);
      count = 0;
    } // 버튼이 다시 눌린 경우 끝
  }
} //button_handler 종료

const android_air = function(){
  console.log("안드로이드용 call");
  gpio.digitalWrite(LED, 1); //단색LED ON

  callTemp();
  dataJudge_handler();
  co2_judgement();
  co_judgement();
  o2_judgement();
  finedust_judgement();
  dust_judgement();
  data_handler();

    bcount += 1;

  gpio.digitalWrite(RED, 0);
  gpio.digitalWrite(GREEN, 0);
  gpio.digitalWrite(BLUE, 0);
  gpio.digitalWrite(LED, 0);
}

//data making handler
const data_handler = function(){

  var airdata = new Object();
  airdata.co2 = airArray[0].replace("\r", "");
  airdata.co = airArray[1].replace("\r", "");
  airdata.o2 = airArray[2].replace("\r", "");
  airdata.finedust = airArray[3].replace("\r", "");
  airdata.dust = airArray[4].replace("\r", "");
  airdata.temp = airArray[5].replace("\r", "");
  airdata.humi = airArray[6].replace("\r", "");
  airdata.date = dformat;

  var airresult = new Object();
  airresult.co2 = airResult[0];
  airresult.co = airResult[1];
  airresult.o2 = airResult[2];
  airresult.finedust = airResult[3];
  airresult.dust = airResult[4];
  airresult.temp = null;
  airresult.humi = null;
  airresult.date = null;

  var airTotal = new Object();
  airTotal.co2 = [airdata.co2, airresult.co2];
  airTotal.co = [airdata.co, airresult.co];
  airTotal.o2 = [airdata.o2, airresult.o2];
  airTotal.finedust = [airdata.finedust, airresult.finedust];
  airTotal.dust = [airdata.dust, airresult.dust];
  airTotal.temp = airdata.temp;
  airTotal.humi = airdata.humi;
  airTotal.date = airdata.date;

  var airname = new Array();
  airname[0] = "co2";
  airname[1] = "co";
  airname[2] = "o2";
  airname[3] = "finedust";
  airname[4] = "dust"
  airname[4] = "temperature";
  airname[5] = "humidity";
  airname[8] = "date";
  //var jsonText1 = JSON.stringify(airdata, airresult, airname).replace("\r", "");
  jsonText = JSON.stringify(airTotal, airresult, airname);
  //console.log("jsonText : " + jsonText);
  console.log(jsonText);
} //data_handler 종료

const co2_judgement = function(){
  if(airArray[0] >= 1000){
    airResult.push("bad");
  }else if (airArray[0] < 1000) {
    airResult.push("good");
  }else {
    console.log("Co2's result was Something Wrong");
  }
}

const co_judgement = function(){
  if(airArray[1] >= 1000){
    airResult.push("bad");
  }else if (airArray[1] < 1000) {
    airResult.push("good");
  }else {
    console.log("Co's result was Something Wrong");
  }
}


const o2_judgement = function(){
  if(airArray[2] >= 19.5){
    airResult.push("good");
  }else if (airArray[2] < 19.5) {
    airResult.push("bad");
  }else {
    console.log("O2's result was Something Wrong");
  }
}

const finedust_judgement = function(){
  if(airArray[3] > 50) {
    airResult.push("bad");
  }else if (airArray[3] > 15 && airArray[3] <= 50) {
    airResult.push("soso");
  }else if(airArray[3] <= 15) {
    airResult.push("good");
  }else {
    console.log("Something Wrong with your device");
  }
}

const dust_judgement = function(){
  if(airArray[4] > 80) {
    airResult.push("bad");
  }else if (airArray[4] > 30 && airArray[4] <= 80) {
    airResult.push("soso");
  }else if(airArray[4] <= 30) {
    airResult.push("good");
  }else {
    console.log("Something Wrong with your device");
  }
}

process.on("SIGINT", function(){
  gpio.digitalWrite(RED, 0);
  gpio.digitalWrite(GREEN, 0);
  gpio.digitalWrite(BLUE, 0);
  gpio.digitalWrite(LED, 0);
  process.exit();
});

gpio.wiringPiSetup();
gpio.pinMode(BUTTON,gpio.INPUT);
gpio.pinMode(LED,gpio.OUTPUT);
gpio.pinMode(RED,gpio.OUTPUT);
gpio.pinMode(GREEN,gpio.OUTPUT);
gpio.pinMode(BLUE,gpio.OUTPUT);

gpio.wiringPiISR(BUTTON, gpio.INT_EDGE_RISING, button_handler);

//스마트 Airzone (실외) 공기 측정 요청
app.post('/device/measurement/outside',function(req,res){

  // TODO : 센서 측정 요청(실외)
  android_air();
  console.log("센서 측정 요청");

  var userid = req.body.userid;
  var longitude = req.body.longitude;
  var latitude = req.body.latitude;

  var data = JSON.parse(jsonText);
  data.userid = userid;
  data.longitude = longitude;
  data.latitude = latitude;
  console.log(data);

  //데이터 Parsing
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
  var date = data.date;

  data = JSON.stringify(data);
  var request = new http.ClientRequest({
    hostname : 'airsharing.hopto.org',
    port : 8080,
    path : '/device/outsidedata/transmission',
    method : 'POST',
    headers: {
      "Content-Type" : "application/json",
      "Content-Length" : Buffer.byteLength(data)
    }
  });
  request.end(data);

// userid, deviceid, CO, CO2, O2, dust,위도, 경도 ,온도, 습도 DB Insert
  client.query('INSERT INTO air (userid, co2, co2_result, co, co_result, o2, o2_result, dust, dust_result, finedust, finedust_result,temp, humidity, latitude,longitude,date) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)',
[userid,co2,co2_result,co,co_result,o2,o2_result,dust,dust_result,finedust,finedust_result,temp,humi,latitude,longitude,date], function(error, result){
    if(error){
      console.log("air zone DB insert fail");
      console.log(error);
    }else{
      console.log('airzone DB INSERT SUCCESS');
      client.query('SELECT * FROM air ORDER BY date DESC limit 1;',function(error, result){
        if(error){
          console.log('select 에러 : ' + error);
          res.send(error);
        }else {
          console.log(result);
          res.send(result);
        }
      });
    }
  });

});


//서버 실행
app.listen(3000, function(){
        console.log('스마트 Airzone(실외) 작동');
});
