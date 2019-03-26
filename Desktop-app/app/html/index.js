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


function download(loki, type, original_name) {
    //ipcRenderer.send('open-directory-dialog');

    //ipcRenderer.on('select_directory', function (event, path) {
        //var downloadsaved_path = path.toString();
        var downloadsaved_path = document.getElementById('selected_directory').innerHTML;
        var new_downloadsaved_path ="";
        console.log(original_name);
        if (downloadsaved_path != "") {
            var savedpath_arr = downloadsaved_path.split("\\");
            for (var i = 0; i < savedpath_arr.length; i++) {  //e.g. D:\1\a -> D:\\1\\a\\
                new_downloadsaved_path += savedpath_arr[i];
                new_downloadsaved_path += '\\';
            }
            var downloadURL = "http://46.101.20.26:3001/download/" + loki + type;
            //console.log(new_downloadsaved_path)
            //console.log(downloadURL)
            ipcRenderer.send('to_download', downloadURL + "," + new_downloadsaved_path + "," + original_name)
        }
        else {
            alert("Please choose a path");
        }
    //})
}

//prevent from executing the script before the page fully loads.
window.onload = function() {
        user = localStorage.getItem("user");  
        document.getElementById("welcome").innerHTML="Hello "+ user;

    document.getElementById('choose_download_saved_path').addEventListener('click', function (event) {
        ipcRenderer.send('open-directory-dialog')
    })

    const ipc = require('electron').ipcRenderer
    document.getElementById('choose_upload_file').addEventListener('click', function (event) {
        ipc.send('open-file-dialog')
    })

    ipcRenderer.on('select_directory', function (event, path) {
        document.getElementById('selected_directory').innerHTML = `${path}`
    })

    ipc.on('selectedItem', function (event, path) {
        document.getElementById('selected_file').innerHTML = `${path}`
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

    var upload_file_path = document.getElementById('selected_file').innerHTML.toString();
    if(upload_file_path==""){
        alert("please choice file");
        return;
    }
    
    var options = { method: 'POST',
        url: 'http://46.101.20.26:3001/UploadSingle',
        headers: 
        { 'Postman-Token': '19aa52f3-a76f-4e5c-a96d-ae0cb6ecdfcf',
            'cache-control': 'no-cache',
            'content-type': 'multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' },
        formData: 
        { singlefile: 
            {   value: fs.createReadStream(upload_file_path),           
                options: 
                { 
                //filename: 'C:/Users/fuyx0/Desktop/test1.txt',
                filename: upload_file_path,
                contentType: null } } } };

    request(options, function (error, response, body) {
        if (error) throw new Error(error);
        console.log(body);
    });

}

/*function singleupload(){
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
            { value: fs.createReadStream("C:/Users/fuyx0/Desktop/hello.txt"),
                options: 
                { filename: 'C:/Users/fuyx0/Desktop/hello.txt',
                contentType: null } } } };

        request(options, function (error, response, body) {
            if (error) throw new Error(error);

            console.log(body);
        });
}*/



function multiupload(){

    var fs = require("fs");
    var request = require("request");
    
    var options = { method: 'POST',
      url: 'http://46.101.20.26:3001/UploadMultiple',
      headers: 
       { 'Postman-Token': '3fe9b3d5-7d53-47c4-89e8-a16182c93a70',
         'cache-control': 'no-cache',
         'content-type': 'multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' },
      formData: 
       { files: 
          { value: 'fs.createReadStream("/Users/tianning/Desktop/Group Project/Server-methods-and-Android-dev.docx")',
            options: 
             { filename: '/Users/tianning/Desktop/Group Project/Server-methods-and-Android-dev.docx',
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
    /*console.log(obj[1]);
    console.log(obj[1].size);
    console.log(obj[1].meta.created);*/
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
            //console.log(this.id);
            var file_name = obj[this.id - 1].originalname;
            var find_extension;
            for (var k_tmp = file_name.length - 1; k_tmp > 0; k_tmp--) {
                if (file_name[k_tmp] == '.') {
                    find_extension = k_tmp;
                    break;
                }
            }
            var file_type = file_name.substring(find_extension, file_name.length);
            //console.log(find_extension);
            //console.log(file_type);

            //ipcRenderer.send('open-directory-dialog');
            //ipcRenderer.on('select_directory', function (event, path) {
                //document.getElementById('selected_directory').innerHTML = `${path}`
                download(this.id, file_type, file_name);
            //})

            
        }
        //node4.onclick = download();


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