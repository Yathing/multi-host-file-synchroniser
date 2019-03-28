"use strict";
var __awaiter = (this && this.__awaiter) || function(thisArg, _arguments, P, generator) {
    return new(P || (P = Promise))(function(resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }

        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }

        function step(result) { result.done ? resolve(result.value) : new P(function(resolve) { resolve(result.value); }).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
// creating our Express server.
// import external variables
const express = require("express");
const mime = require("mime-types");
const multer = require("multer");
const cors = require("cors");
const fs = require("fs");
const path = require("path");
const Loki = require("lokijs");
const utils_1 = require("./utils");
// setup
const DB_NAME = 'db.json';
const COLLECTION_NAME = 'files';
const UPLOAD_PATH = 'uploads';
const upload = multer({ dest: `${UPLOAD_PATH}/` }); // multer configuration
const db = new Loki(`${UPLOAD_PATH}/${DB_NAME}`, { persistenceMethod: 'fs' });
// optional: clean all data before start
utils_1.cleanFolder(UPLOAD_PATH);
// app
const app = express();
app.use(cors());
// create a route to allow users to upload their profile avatar.
//upload.single('singlefile') is Multer middleware. 
//It means we accept a single file with the field name singlefile. File upload will be handled by Multer.
app.post('/UploadSingle', upload.single('singlefile'), (req, res) => __awaiter(this, void 0, void 0, function*() {
    try {
        const col = yield utils_1.loadCollection(COLLECTION_NAME, db);
        const data = col.insert(req.file);
        db.saveDatabase();
        res.send({ id: data.$loki, fileName: data.filename, originalName: data.originalname });
    } catch (err) {
        res.sendStatus(400);
    }
}));
//We will then load the LokiJs collection/table, 
//and insert the request file req.file to the collection.
//Handle multiple files upload
app.post('/UploadMultiple', upload.array('files', 6), (req, res) => __awaiter(this, void 0, void 0, function*() {
    try {
        const col = yield utils_1.loadCollection(COLLECTION_NAME, db);
        let data = [].concat(col.insert(req.files));
        db.saveDatabase();
        res.send(data.map(x => ({ id: x.$loki, fileName: x.filename, originalName: x.originalname })));
    } catch (err) {
        res.sendStatus(400);
    }
}));
// Retrieve all images.
app.get('/Files', (req, res) => __awaiter(this, void 0, void 0, function*() {
    try {
        const col = yield utils_1.loadCollection(COLLECTION_NAME, db);
        res.send(col.data);
    } catch (err) {
        res.sendStatus(400);
    }
}));
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
app.get('/download/:id', (req, res) => __awaiter(this, void 0, void 0, function*() {
    try {
        const col = yield utils_1.loadCollection(COLLECTION_NAME, db);
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
}));
//delete function
app.delete('/delete/:id', (req, res) => __awaiter(this, void 0, void 0, function*() {
    try {
        const col = yield utils_1.loadCollection(COLLECTION_NAME, db);
        const file = col.get(req.params.id);
        const info = db.getCollection('files');
        // if (!file) {
        //     res.sendStatus(404);
        //     return;
        // };
        fs.unlink(file.path, function() {
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
}));
//port
app.listen(3000, function() {
    console.log('listening on port 3000!');
});
//# sourceMappingURL=index.js.map