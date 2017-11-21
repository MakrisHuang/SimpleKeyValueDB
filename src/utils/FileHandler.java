package utils;

import database.TableIndex;

import java.io.*;
import java.util.Objects;

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
 * This class store, retrieve data by hash function
 * Each FileHandler owns an array of TableIndexes
 */
public class FileHandler{
    private final String tableIndexPath = "db.tableindex";
    private TableIndex tableIndex;

    private static FileHandler mInstance;
    public FileHandler(){
        this.readDbIndex();
    }

    public static synchronized FileHandler sharedInstance(){
        if (mInstance == null){
            mInstance = new FileHandler();
        }
        return mInstance;
    }

    // read tableIndex
    // for any object, it read corresponding table file to retrieve/store/modify it
    public boolean readDbIndex() {
        boolean status = false;

        File tableFile = new File(this.tableIndexPath);
        if (tableFile.exists() && !tableFile.isDirectory()){
            try {  // then read tableIndex file
                FileInputStream fis = new FileInputStream(this.tableIndexPath);
                ObjectInputStream ois = new ObjectInputStream(fis);
                this.tableIndex = (TableIndex) ois.readObject();
                ois.close();
                System.out.println("[FileHandler] TableIndexObj has been read successfully");
                status = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else { // then create a new db file
            try {
                tableFile.createNewFile();
                System.out.println("[FileHandler] TableIndexObj has been created successfully");
                this.tableIndex = new TableIndex();
                status = true;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return status;
    }

    public void storeDbIndex(){
        try {
            FileOutputStream fos = new FileOutputStream(this.tableIndexPath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.tableIndex);
            oos.flush();
            oos.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
        operation on KeyValueObj: get, add, delete
     */
    public KeyValueObj getObj(String key){
        int hashCode = key.hashCode();
        return this.tableIndex.getObjInTableIndex(hashCode);
    }

    public void getAllObj(){
        this.tableIndex.getAllObjsInAllTables();
    }

    public boolean addObj(KeyValueObj obj){
        boolean result =  this.tableIndex.addObjInTableIndex(obj);
        this.storeDbIndex();
        return result;
    }

    public boolean deleteObj(String key){
        int hashCode = Objects.hashCode(key);
        boolean result = this.tableIndex.deleteObjInTableIndex(hashCode);
        this.storeDbIndex();
        return result;
    }
}