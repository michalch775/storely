const { app, BrowserWindow, ipcMain } = require('electron');

const path = require('path');
const isDev = require('electron-is-dev');
const Store = require('electron-store');

//console.log("TRALALAL",app.getPath('userData'));

const defaults = {
	settings: {
		darkMode:false,
    navWidth:"300"
	}
};



const store = new Store({defaults});

ipcMain.on('store-set', (event, {name, value}) => {
    store.set(name, value);
    event.returnValue=0;
});

ipcMain.on('store-get', (event, name) => {
  //event.returnValue = 1;
  event.returnValue = store.get(name, undefined);
});

//require('@electron/remote/main').initialize()

function createWindow() {
  // Create the browser window.
  const win = new BrowserWindow({
    width: 800,
    height: 600,
    minWidth:600,
    minHeight:400,
    webPreferences: {
      nodeIntegration: true,
      enableRemoteModule: true
    }
  })

  win.loadURL(
    isDev
      ? 'http://localhost:3000'
      : `file://${path.join(__dirname, '../build/index.html')}`
  )
}

app.on('ready', createWindow)

// Quit when all windows are closed.
app.on('window-all-closed', function () {
  // On OS X it is common for applications and their menu bar
  // to stay active until the user quits explicitly with Cmd + Q
  if (process.platform !== 'darwin') {
    app.quit()
  }
})

app.on('activate', function () {
  // On OS X it's common to re-create a window in the app when the
  // dock icon is clicked and there are no other windows open.
  if (BrowserWindow.getAllWindows().length === 0) createWindow()
})
