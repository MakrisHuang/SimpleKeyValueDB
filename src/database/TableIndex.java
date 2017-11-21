package database;

import utils.FileHandler;
import utils.KeyValueObj;

import java.io.*;

/**
 * Created by makris on 2017/11/20.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * This class store 5 tables and each table holds the key-value objects
 * This content of this object will also be serialized into the file db.tableindex
 */

public class TableIndex implements Serializable{
    private final int numOfTables = 5;
    private String indexFilePaths[];
    private Table[] tables;

    public TableIndex(){
        if (this.indexFilePaths == null || this.tables == null){
            this.indexFilePaths = new String[numOfTables];
            this.tables = new Table[5];
        }
    }

    public boolean insertTableName(Integer index){
        if (index < numOfTables) {
            this.indexFilePaths[index] = this.getTableName(index);
            return true;
        }
        else{
            return false;
        }
    }

    private String getTableName(Integer index){
        return index.toString() + ".table";
    }

    /*
        High level manipulation on Table object
     */
    public synchronized Table loadTable(Integer tableIndex){
        // check if the table file exist, if so, load it from file
        String tableName = getTableName(tableIndex);
        File tableFile = new File(tableName);
        if (tableFile.exists()){
            try {
                FileInputStream fis = new FileInputStream(getTableName(tableIndex));
                ObjectInputStream ois = new ObjectInputStream(fis);
                // insert table name
                this.insertTableName(tableIndex);
                return (Table) ois.readObject();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{ // table not exist, create a new one
            this.insertTableName(tableIndex);
            return new Table();
        }
        return null;
    }

    public synchronized boolean storeTable(Integer tableIndex){
        boolean status = false;
        try {
            if (this.tables[tableIndex] != null) {
                FileOutputStream fos = new FileOutputStream(this.indexFilePaths[tableIndex]);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(this.tables[tableIndex]);
                oos.close();
                status = true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return status;
    }

    /*
       low level manipulation on get, add, delete KeyValueObj
     */
    public synchronized KeyValueObj getObjInTableIndex(int hashCode){
        int tableIndex = hashCode % numOfTables;
        if (this.tables[tableIndex] == null){
            this.tables[tableIndex] = this.loadTable(tableIndex);
        }
        return this.tables[tableIndex].findKeyValueObj(hashCode);
    }

    public synchronized void getAllObjsInAllTables(){
        for (int tableIndex = 0; tableIndex < numOfTables; tableIndex++){
            if (this.tables[tableIndex] != null){
                this.tables[tableIndex].showAllObjs();
            }
        }
    }

    public synchronized boolean addObjInTableIndex(KeyValueObj obj){
        int tableIndex = obj.hashCode() % numOfTables;
        if (this.tables[tableIndex] == null){
            this.tables[tableIndex] = this.loadTable(tableIndex);
        }
        boolean isAdded = this.tables[tableIndex].addKeyValueObj(obj);
        if (isAdded){
            // serialize table to file
            this.storeTable(tableIndex);
        }
        return isAdded;
    }

    public synchronized boolean deleteObjInTableIndex(int hashCode){
        int tableIndex = hashCode % numOfTables;
        if (this.tables[tableIndex] == null){
            this.tables[tableIndex] = this.loadTable(tableIndex);
        }
        boolean isDeleted = this.tables[tableIndex].deleteKeyValueObj(hashCode);
        if (isDeleted){
            // serialize table to file
            this.storeTable(tableIndex);
        }
        return isDeleted;
    }
}
