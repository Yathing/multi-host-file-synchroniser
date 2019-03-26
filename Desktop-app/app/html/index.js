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
        url: 'http://46.101.20.26:3001/UploadSingle',
        headers: 
        { 'Postman-Token': 'cdc14b46-b63b-41c4-8a6f-00370edbdd89',
            'cache-control': 'no-cache',
            'Content-Type': 'application/json',
            'content-type': 'multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' },
        formData: 
        { singlefile: 
            { value: 'fs.createReadStream("C:\Users/fuyx0/Desktop/hello.txt")',
                options: 
                { filename: '/Users/fuyx0/Desktop/hello.txt',
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
    url: 'http://46.101.20.26:3001/UploadMultiple',
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
    url: 'http://46.101.20.26:3001/Files',
    headers: 
    { 'Postman-Token': 'bca4991d-ec12-4afa-affb-79b119da9c1b',
        'cache-control': 'no-cache',
        'Content-Type': 'application/json' } };

    request(options, function (error, response, body) {
    if (error) throw new Error(error);

    //console.log(body);
    var obj = JSON.parse(body)
    console.log(obj[1]);
    console.log(obj[1].size);
    console.log(obj[1].meta.created);
    showTable(obj); 
    });
}

function showTable(obj){
    var list =  document.getElementById("filelist");
    if(document.getElementById("node_table")){
        list.removeChild(document.getElementById("node_table"));
    }
    
    var node_table = document.createElement("table");
    node_table.id="node_table";
    
    var tr1 = document.createElement("tr");

    var th1 = document.createElement("th");
    th1.appendChild(document.createTextNode("file name"));
    tr1.appendChild(th1);

    var th2 = document.createElement("th");
    th2.appendChild(document.createTextNode("file size"));
    tr1.appendChild(th2);

    var th3 = document.createElement("th");
    th3.appendChild(document.createTextNode("created time"));
    tr1.appendChild(th3);

    var th4 = document.createElement("th");
    th4.appendChild(document.createTextNode("download"));
    tr1.appendChild(th4);
    
    node_table.appendChild(tr1);

    for(var i=0;i<obj.length;i++){
        

        var node_tr=document.createElement("tr");
	
        var node1=document.createElement("td");
        node1.appendChild(document.createTextNode(obj[i].originalname));
        
        var node2 = document.createElement("td");
        node2.appendChild(document.createTextNode(obj[i].size));

        var node3=document.createElement("td");
        comtime = new Date(obj[i].meta.created).toLocaleString()
        node3.appendChild(document.createTextNode(comtime));

        var node4=document.createElement("button");
        node4.id = obj[i].$loki;
        //alert("node id "+i+" = "+node4.id);
        node4.onclick=function()
        {
            DBID(this.id);
        }
        node4.appendChild(document.createTextNode("download"));
        
        node_tr.appendChild(node1);
        node_tr.appendChild(node2);
        node_tr.appendChild(node3);
        node_tr.appendChild(node4);

        node_table.appendChild(node_tr);
    }
    document.getElementById("filelist").appendChild(node_table);   
}

function DBID(fileID){
    alert( fileID);
}


//下载id为1的文件
function downloadByID(){
    var request = require("request");
   

    var options = { method: 'GET',
    url: 'http://46.101.20.26:3001/download/1',
    headers: 
    { 'Postman-Token': 'a1d64c04-8ff6-439e-ae63-5a9c93b216ab',
        'cache-control': 'no-cache',
        'Content-Type': 'application/json' } };

    request(options, function (error, response, body) {
    if (error) throw new Error(error);
    alert("test dl1");
    console.log(body);

    });
}