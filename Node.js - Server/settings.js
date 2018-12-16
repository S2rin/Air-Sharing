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

//개인정보 가져오기
router.get('/info/:userid',function(req, res){
  console.log("개인정보 가져오기");
  var userid = req.params.userid;
  console.log("요청자 아이디: " + userid);

  client.query('select name, phone, email from member where uuid = ?',[userid], function(error,result){
    if(error){
      console.log("에러 발생: ", error);
      res.send(error);
    }
    else{
      console.log("성공!");
      console.log(result);
      res.send(result);
    }
  });
});

/*
//개인 정보 변경
router.put('/modification/:userid',function(req,res){
  var userid = req.params.userid;
  var name = req.body.name;
  var email = req.body.email;
  var phone = req.body.phone;

  console.log("개인정보변경 : ",userid,name,email,phone);

  client.query('UPDATE member SET name = ?, email = ?, phone = ? WHERE userid = ?',[userid,name,email,phone],function(err,result){
    if(err){
      console.log("에러 발생: ", err);
      res.send(err);
    }else{
      res.send(result);
    }
  });
});
*/

//비밀번호 변경
router.put('/modification/passwd/:userid',function(req,res){
  var userid = req.params.userid;
  var passwd = req.body.passwd;

  client.query('UPDATE member SET pw = ? WHERE uuid = ?',[passwd,userid],function(err, result){
    if(err){
      console.log("에러 발생: ", err);
      res.send(err);
    }else{
      console.log("성공");
      res.send(result);
    }
  });
});

/*
//지역설정
router.put('/location/:location/:userid', function(req, res){
  var userid = req.params.userid;
  var temp = req.params.location;

  //구 뺴오기
  var location = temp.split(" ");
  console.log("지역: "+location[2]);

  console.log('지역설정');
    client.query('UPDATE member SET si = ? WHERE userid = ?' , [location[0], userid], function(err, result) {
      if(err){
        console.log("에러 발생: ", err);
        res.send(err);
      }else{
        res.send(result);
      }
    });
});
*/

//알람 온오프 설정
router.put('/alarm/:alarm/:userid', function (req, res) {
  var userid = req.params.userid;
  var alarm = req.params.alarm;
  var alarmNum = 0;

  if(alarm == 'false'){
    alarmNum = 0;
  }else{
    alarmNum = 1;
  }

  client.query('UPDATE member SET alarm = ? WHERE uuid = ?', [alarmNum, userid], function(err, result){
    if(err){
      console.log("에러 발생 : ", err);
      res.send(err);
    }else{
      console.log("성공");
      res.send(result);
    }
  });
});

//모듈 설정
module.exports = router
