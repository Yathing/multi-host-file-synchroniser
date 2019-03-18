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
    document.getElementById('choose_download_saved_path').addEventListener('click', function (event) {
        ipcRenderer.send('open-directory-dialog')
    })

    ipcRenderer.on('select_directory', function (event, path) {
        document.getElementById('selected_directory').innerHTML = `${path}`
    })
}
    