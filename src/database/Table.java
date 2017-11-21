package database;

import utils.KeyValueObj;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by makris on 2017/11/20.
 * SimpleKeyValueDB project
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * This class implement the very low level manipulation: get, put, getall and delete
 * Each table will be serialized into the corresponding table file ex. 1.table
 */
public class Table implements Serializable{
    private ArrayList<KeyValueObj> objs;

    public Table(){
        objs = new ArrayList<>();
    }

    public boolean addKeyValueObj(KeyValueObj obj){
        return objs.add(obj);
    }

    public KeyValueObj findKeyValueObj(int hashCode){
        for(KeyValueObj obj: this.objs){
            if (obj.hashCode() == hashCode){
                return obj;
            }
        }
        return null;
    }

    public void showAllObjs(){
        for (KeyValueObj obj:objs) {
            System.out.println(obj.toString());
        }
    }

    public boolean deleteKeyValueObj(int hashCode){
        KeyValueObj obj = findKeyValueObj(hashCode);
        return this.objs.remove(obj);
    }
}