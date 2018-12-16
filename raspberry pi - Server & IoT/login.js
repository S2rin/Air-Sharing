const express = require("express");
const mysql = require("mysql");
const bodyparser = require("body-parser");
const crypto = require('crypto');
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

var key = "secret key";   // 암호화 키


// 네이버로 로그인
router.post('/naver/login', function(req, res){
	console.log("로그인 정보 받아오기");
	var name = req.body.name;
  var email = req.body.email;
  var gender = req.body.gender;
  var birthdate = req.body.birthdate;
	var naver_id = req.body.naver_id;


	console.log("회원 데이터: " + name + "/" + email + "/" + gender + "/" + birthdate + "/" + naver_id);

	client.query("select email from member where email='" + email + "'", function(error, result){
		if(result == ""){  // 최초 로그인이라면
      var count = 1;
			client.query("insert into member (uuid, name, email, gender, birth, count) values (?, ?, ?, ?, ?, ?)", [naver_id, name, email, gender, birthdate, count], function(error, result){
				if(error){
					console.log("에러 발생: ", error);
					res.send("error");
				}
				else{
					console.log("최초 로그인 성공! " + email);
					res.send("최초 로그인");
				}
			});
		 }
		else{   // 최초 로그인이 아니면
			if(error){
					console.log("에러 발생: ", error);
					res.send("error");
				}
				else{
					console.log("로그인 성공! " + email);
          res.send("로그인 성공");
				}
		}
	});
});



// 로그인
router.post('/login', function(req, res){
	console.log("로그인");

	var id = req.body.id;
	var pw = req.body.pw;

	console.log("회원 데이터: " + id + "/" + pw);


	// 암호화
	var cipher = crypto.createCipher('aes256', key);
  cipher.update(pw, 'utf8', 'base64');
  var pw_output = cipher.final('base64');
 	console.log(pw_output);

	client.query("select count(*) AS cnt from member where uuid = ? and pw = ?", [id, pw_output], function(error, result){
		var cnt = result[0].cnt;

		if(cnt == 1){
			console.log("로그인 성공");
			res.send(result);
		}

		else if(error){
			console.log("error 발생", error);
			res.send(error);
		}
		else{
			console.log("계정이 존재하지 않습니다.");
			res.send(result);
		}
	});

});



// 회원가입
router.post('/registeration', function(req, res){
	console.log("로그인");

	var id = req.body.id;
	var pw = req.body.pw;
	var name = req.body.name;
	var email = req.body.email;
	var phone = req.body.phone;
  var gender = req.body.gender;

	console.log("회원 데이터: " + id + "/" + pw + "/" + name + "/" + email + "/" + gender);


	     // 암호화
	    var cipher = crypto.createCipher('aes256', key);
    	cipher.update(pw, 'utf8', 'base64');
    	var pw_output = cipher.final('base64');

	client.query("insert into member(uuid , pw, name, email, phone, gender) values(?, ?, ?, ?, ?, ?)", [id, pw_output, name, email, phone, gender], function(error, result){
		if(error){
			console.log("error 발생", error);
			res.send(error);
		}
		else{
			console.log("회원 가입 성공");
			res.send(result);
		}
	});

});



// 아이디 찾기
router.post('/request/id', function(req, res){
	console.log("아이디 찾기");

	var name = req.body.name;
	var email = req.body.email;

	console.log("회원 데이터: " + name + "/" + email);

	client.query("select uuid from member where name = ? and email = ?", [name, email], function(error, result){
		if(error){
			console.log("error 발생", error);
			res.send(error);
		}
		else{
			console.log("아이디 찾기 성공");
			console.log(result);
			res.send(result);
		}
	});

});



// 비밀번호 찾기
router.post('/request/passwd', function(req, res){
	console.log("비밀번호 찾기 찾기");

	var userid = req.body.userid;
	var email = req.body.email;

	console.log("회원 데이터: " + userid + "/" + email);

	client.query("select pw from member where uuid = ? and email = ?", [userid, email], function(error, result){
		if(error){
			console.log("error 발생: ", error);
			res.send(error);
		}
		else{
			var pw = result[0].pw;
			console.log("비밀번호 찾기 성공: " + pw);

			var decipher = crypto.createDecipher('aes256', key);
            		decipher.update(pw, 'base64', 'utf8');
            		var password = decipher.final('utf8');
			console.log("비밀번호: " + password);
			result[0].pw = password;
			res.send(result);
		}
	});

});



// 탈퇴하기
router.post('/withdrawal', function(req, res){
	console.log("탈퇴하기");

	var id = req.body.userid;

	console.log("회원 데이터: " + id);

	client.query("delete from member where uuid = ?", [id], function(error, result){
		if(error){
			console.log("error 발생", error);
			res.send(error);
		}
		else{
			console.log("탈퇴 성공");
			res.send(result);
		}
	});

});

module.exports = router
