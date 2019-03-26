 // test code only
 
  var user = document.getElementById("username").value;
  var pwd = document.getElementById("password").value;

  var request = require("request");

  var options = { method: 'POST',
  url: 'http://46.101.20.26:3000/users/authenticate',
  headers: 
   { 'Postman-Token': '6b5fbf61-7a24-475d-b01b-d66969e107dd',
     'cache-control': 'no-cache',
     'Content-Type': 'application/json' },
  body: 
   { username: user,
     password: pwd },
  json: true };

  request(options, function (error, response, body) {
    if (error) throw new Error(error);
    console.log(body);
    alert("value= "+ body.success);
    if(body.success=='false') alert("2");
    else alert("1");
  });
