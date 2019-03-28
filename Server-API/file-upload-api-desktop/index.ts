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
const info = db.getCollection('files');

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

//delete function
app.delete('/delete/:id', async (req, res) => {
    try {
        const col = await loadCollection(COLLECTION_NAME, db);
        const file = col.get(req.params.id);
        const info = db.getCollection('files');

        if (!file) {
            res.sendStatus(404);
            return;
        };

        fs.unlink(file.path, function () {
            res.send({
                status: "200",
                responseType: "string",
                response: "delete success!"
            });
        });

        //Delete the file
        info.remove(file);
        db.saveDatabase();
        // res.send({ id: data.$loki, fileName: data.filename, originalName: data.originalname });

    } catch (err) {
        res.sendStatus(400);
    }
})


app.listen(3003, function () {
    console.log('listening on port 3003!');
});