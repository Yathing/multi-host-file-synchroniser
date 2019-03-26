// creating our Express server.
// import external variables
import * as express from 'express'
import * as mime from 'mime-types';
import * as multer from 'multer'
import * as cors from 'cors'
import * as fs from 'fs'
import * as path from 'path'
import * as Loki from 'lokijs'
import { loadCollection, cleanFolder} from './utils';


// setup
const DB_NAME = 'db.json';
const COLLECTION_NAME = 'files';
const UPLOAD_PATH = 'uploads';
const upload = multer({ dest: `${UPLOAD_PATH}/` }); // multer configuration
const db = new Loki(`${UPLOAD_PATH}/${DB_NAME}`, { persistenceMethod: 'fs' });

// optional: clean all data before start
cleanFolder(UPLOAD_PATH);

// app
const app = express();
app.use(cors());



// create a route to allow users to upload their profile avatar.
//upload.single('singlefile') is Multer middleware. 
//It means we accept a single file with the field name singlefile. File upload will be handled by Multer.
app.post('/UploadSingle', upload.single('singlefile'), async (req, res) => { 
    try {
        const col = await loadCollection(COLLECTION_NAME, db);
        const data = col.insert(req.file);

        db.saveDatabase();
        res.send({ id: data.$loki, fileName: data.filename, originalName: data.originalname });
    } catch (err) {
        res.sendStatus(400);
    }
})
//We will then load the LokiJs collection/table, 
//and insert the request file req.file to the collection.

//Handle multiple files upload
app.post('/UploadMultiple', upload.array('files', 6), async (req, res) => {
    try {
        const col = await loadCollection(COLLECTION_NAME, db)
        let data = [].concat(col.insert(req.files));

        db.saveDatabase();
        res.send(data.map(x => ({ id: x.$loki, fileName: x.filename, originalName: x.originalname })));
    } catch (err) {
        res.sendStatus(400);
    }
})

// Retrieve all images.
app.get('/Files', async (req, res) => {
    try {
        const col = await loadCollection(COLLECTION_NAME, db);
        res.send(col.data);
    } catch (err) {
        res.sendStatus(400);
    }
})


// app.get('/download', function (req, res) {

//     var file = __dirname + '/upload-folder/dramaticpenguin.MOV';

//     var filename = path.basename(file);
//     var mimetype = mime.lookup(file);

//     res.setHeader('Content-disposition', 'attachment; filename=' + filename);
//     res.setHeader('Content-type', mimetype);

//     var filestream = fs.createReadStream(file);
//     filestream.pipe(res);
// });

// download file --> Mobile 
app.get('/download/:id', async (req, res) => {
    try {
        const col = await loadCollection(COLLECTION_NAME, db);
        const file = col.get(req.params.id);
        const mimetype = mime.lookup(file);

        if (!file) {
            res.sendStatus(404);
            return;
        };

        res.setHeader('Content-Type', mimetype);  
        fs.createReadStream(path.join(UPLOAD_PATH, file.filename)).pipe(res);
    } catch (err) {
        res.sendStatus(400);
    }
})

//download file --> Desktop
// app.get('/desktopdownload/:id', async (req, res) => {
//     // try {
//         const col = await loadCollection(COLLECTION_NAME, db);
//         const file = col.get(req.params.id);
//         // const mimetype = mime.lookup(file);
//         // const filestream = fs.createReadStream(UPLOAD_PATH,file);

//         // if (!file) {
//         //     res.sendStatus(404);
//         //     return;
//         // };
//         res.download(file);
//         // res.setHeader('Content-Type', mimetype);

//         // filestream.pipe(res);        
//     } catch (err) {
//         res.sendStatus(400);
//     }
// });


app.get('/desktopdownload/:id', async (req, res) => {
    const col = await loadCollection(COLLECTION_NAME, db);
    const file = col.get(req.params.id);
    res.download(file); // Set disposition and send it.
});

app.listen(3000, function () {
    console.log('listening on port 3000!');
});