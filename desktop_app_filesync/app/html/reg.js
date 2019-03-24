 // test code only
 

var email = document.getElementById("email").value;
var user = document.getElementById("username").value;
var pwd = document.getElementById("pwd1").value;

alert(email+" "+user+" "+ pwd);

var request = require("request");

var options = { method: 'POST',
  url: 'http://46.101.20.26:3000/users/register',
  headers: 
   { 'Postman-Token': '6b5fbf61-7a24-475d-b01b-d66969e107dd',
     'cache-control': 'no-cache',
     'Content-Type': 'application/json' },
  body: 
   { name: user,
     email: email,
     username: user,
     password: pwd },
  json: true };

request(options, function (error, response, body) {
  if (error) throw new Error(error);

  console.log(body);
  alert("value= "+ body.success);
  if(body.success=='false') alert("2");
  else alert("1");
});


alert("end hee");