const {ipcRenderer} = require('electron')
const dialog = require('electron').dialog;

// function init() {
//     ipcRenderer.on('downloading', (event, arg) => {
//         alert("downloading: " + arg);
//     })
// }

function upload() {
    var uploadfile_path = "D:\\electron_test\\upload_test.txt";
    var uploadsaved_path = "D:\\electron_test\\1\\";
    ipcRenderer.send('to_upload', uploadfile_path + "," + uploadsaved_path)
}


function download() {
    var downloadsaved_path = document.getElementById('selected_directory').innerHTML;
    var new_downloadsaved_path ="";
    if (downloadsaved_path != "") {
        var savedpath_arr = downloadsaved_path.split("\\");
        for (var i = 0; i < savedpath_arr.length; i++) {  //e.g. D:\1\a -> D:\\1\\a\\
            new_downloadsaved_path += savedpath_arr[i];
            new_downloadsaved_path += '\\';
        }
        var downloadURL = "http://img.ivsky.com/img/bizhi/pre/201710/23/youbaojiding-009.jpg";  //set by user
        console.log(new_downloadsaved_path)
        console.log(downloadURL)
        ipcRenderer.send('to_download', downloadURL + "," + new_downloadsaved_path)
    }
    else {
        download_saved_path.value = "Please choose a path";
    }
}

//prevent from executing the script before the page fully loads.
window.onload = function() {
    user = localStorage.getItem("user");  
    document.getElementById("welcome").innerHTML="Hello "+ user;

    document.getElementById('choose_download_saved_path').addEventListener('click', function (event) {
        ipcRenderer.send('open-directory-dialog')
    })

    ipcRenderer.on('select_directory', function (event, path) {
        document.getElementById('selected_directory').innerHTML = `${path}`
    })
}

function logout(){
    //sessionStorage.clear();
    window.location.href='page_login.html';
}


function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px";
    document.body.style.backgroundColor = "rgba(0,0,0,0.4)";
}
/*关闭侧栏，恢复原始侧栏宽度，主体左跨度、背景透明度*/
function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.getElementById("main").style.marginLeft= "0";
    document.body.style.backgroundColor = "white";
}
    


function singleupload(){
        var fs = require("fs");
        var request = require("request");

        var options = { method: 'POST',
        url: 'http://46.101.20.26:80/UploadSingle',
        headers: 
        { 'Postman-Token': 'cdc14b46-b63b-41c4-8a6f-00370edbdd89',
            'cache-control': 'no-cache',
            'Content-Type': 'application/json',
            'content-type': 'multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' },
        formData: 
        { singlefile: 
            { value: 'fs.createReadStream("/Users/tianning/Desktop/Group Project/Multi-Host-File-Synchroniser.pptx")',
                options: 
                { filename: '/Users/tianning/Desktop/Group Project/Multi-Host-File-Synchroniser.pptx',
                contentType: null } } } };

        request(options, function (error, response, body) {
        if (error) throw new Error(error);

        console.log(body);
        });
}



function multiupload(){

    var fs = require("fs");
    var request = require("request");

    var options = { method: 'POST',
    url: 'http://46.101.20.26:80/UploadMultiple',
    headers: 
    { 'Postman-Token': 'c84eb1a5-8b0d-4f9e-8278-173000056a4a',
        'cache-control': 'no-cache',
        'Content-Type': 'application/json',
        'content-type': 'multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' },
    formData: 
    { files: 
        { value: 'fs.createReadStream("/Users/tianning/Desktop/Group Project/modified-file-system-db_260119.sql")',
            options: 
            { filename: '/Users/tianning/Desktop/Group Project/modified-file-system-db_260119.sql',
            contentType: null } } } };

    request(options, function (error, response, body) {
    if (error) throw new Error(error);

    console.log(body);
    });
}


//get file list
function getAllFile(){

    var request = require("request");

    var options = { method: 'GET',
    url: 'http://46.101.20.26:80/Files',
    headers: 
    { 'Postman-Token': 'bca4991d-ec12-4afa-affb-79b119da9c1b',
        'cache-control': 'no-cache',
        'Content-Type': 'application/json' } };

    request(options, function (error, response, body) {
    if (error) throw new Error(error);

    console.log(body);
    //var myobj = response;
    //console.log(response);
    document.getElementById("yixin").innerHTML = "hello";

    });
}


//下载id为1的文件
function downloadByID(){
    var request = require("request");

    var options = { method: 'GET',
    url: 'http://46.101.20.26:80/download/1',
    headers: 
    { 'Postman-Token': 'a1d64c04-8ff6-439e-ae63-5a9c93b216ab',
        'cache-control': 'no-cache',
        'Content-Type': 'application/json' } };

    request(options, function (error, response, body) {
    if (error) throw new Error(error);

    console.log(body);

    });
}