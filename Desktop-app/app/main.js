const electron = require('electron');
const {app, BrowserWindow} = require('electron');
const fs = require('fs');
const path = require('path');
const dialog = require('electron').dialog;

let mainWindow;

function createMainWindow() {
    mainWindow = new BrowserWindow({width: 800, height: 600});
    //mainWindow.loadURL(`file://${__dirname}/html/index.html`);
    mainWindow.loadURL(`file://${__dirname}/html/page_login.html`);
    mainWindow.webContents.openDevTools();

    mainWindow.webContents.session.on('will-download', (event, item, webContents) => {
        item.setSavePath(savefile_path + original_name)
        item.on('updated', (event, state) => {
          if (state === 'interrupted') {
            console.log('Download is interrupted but can be resumed')
          } else if (state === 'progressing') {
            if (item.isPaused()) {
              console.log('Download is paused')
            } else {
              console.log(`Received bytes: ${item.getReceivedBytes()}`)
            }
          }
        })
        item.once('done', (event, state) => {
          if (state === 'completed') {
            console.log('Download successfully')
            mainWindow.webContents.send('downloading', state)
          } else {
            console.log(`Download failed: ${state}`)
            mainWindow.webContents.send('downloading', state)
          }
        })
    })

    mainWindow.on('close', () => {
        mainWindow = null;
    });
}

app.on('ready', createMainWindow);

// Quit when all windows are closed.
app.on('window-all-closed', function () {
    // On macOS it is common for applications and their menu bar
    // to stay active until the user quits explicitly with Cmd + Q
    if (process.platform !== 'darwin') {
      app.quit()
    }
  });
  
  app.on('activate', function () {
    // On macOS it's common to re-create a window in the app when the
    // dock icon is clicked and there are no other windows open.
    if (mainWindow === null) {
      createWindow()
    }
  });
  
  // In this file you can include the rest of your app's specific main process
  // code. You can also put them in separate files and require them here.

const {ipcMain} = require('electron')
let download_path
let savefile_path
let original_name
ipcMain.on('to_download', (event, args) => {
    var path_arr = args.split(",");
    download_path = path_arr[0];
    savefile_path = path_arr[1];
    original_name = path_arr[2];
    console.log(download_path);
    console.log(savefile_path);
    console.log(original_name);
    mainWindow.webContents.downloadURL(download_path);  //call the 'will-download' event
})

ipcMain.on('open-directory-dialog', function (event) {
  dialog.showOpenDialog({
    //defaultPath :'../Desktop',
    properties: [
        'openDirectory',
    ],
    filters: [
        { name: 'All', extensions: ['*'] },
    ]
}, function(res) {
    //send the path
    if (res) event.sender.send('select_directory', res);
})
})

ipcMain.on('open-file-dialog', function (event) {
  let startPath = ''
  if (process.platform === 'darwin') {
    startPath = '/Users/<username>/Documents/'
  }
  dialog.showOpenDialog({
    title: 'Select a workspace...',
    properties: ['openFile'],
    defaultPath: startPath,
    buttonLabel: "Select...",
  }, function (files) {
    if (files) event.sender.send('selectedItem', files)
  })
})