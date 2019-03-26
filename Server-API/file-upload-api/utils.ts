//Load LokiJs Collection
//A generic function to retrieve a LokiJs collection if exists, or create a new one if it doesn't.
import * as del from 'del';
import { Collection } from 'lokijs';

const loadCollection = function (colName, db: Loki): Promise<Collection<any>> {
    return new Promise(resolve => {
        db.loadDatabase({}, () => {
            const _collection = db.getCollection(colName) || db.addCollection(colName);
            resolve(_collection);
        })
    });
}

const cleanFolder = function (folderPath) {
    // delete files inside folder but not the folder itself
    del.sync([`${folderPath}/**`, `!${folderPath}`]);
};

export { loadCollection, cleanFolder }