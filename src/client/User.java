package client;

import utils.FileHandler;
import utils.KeyValueObj;
import java.util.Scanner;

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
 * The interaction interface
 */

public class User {
    Scanner scanner = new Scanner(System.in);
    FileHandler handler = FileHandler.sharedInstance();

    public User(){

    }

    /*
        High level interaction: get and put
        ex. put k1 v2
            put k2 v2
            get k1
            getall "k1" "k2"
            delete "k1"
            get "k1"
     */
    public void listenUserInput(){
        boolean quit = false;
        while(!quit) {
            String head = scanner.next();
            switch (head) {
                case "get": {
                    String key = scanner.next();
                    if (key == null) {
                        System.out.println("[User] No key specified");
                        continue;
                    }
                    this.get(key);
                    continue;
                }
                case "put": {
                    String key = scanner.next();
                    String value = scanner.next();
                    if (key == null || value == null) {
                        System.out.println("[User] No key or value specified");
                        continue;
                    }
                    Object obj = parseValueType(value);
                    if (obj != null) {
                        this.put(key, parseValueType(value));
                    }
                    else {
                        System.out.println("[User] Unknown data type");
                    }
                    continue;
                }
                case "delete": {
                    String key = scanner.next();
                    if (key == null) {
                        System.out.println("[User] No key specified");
                        continue;
                    }
                    this.delete(key);
                    continue;
                }
                case "getall":{
                    this.getAll();
                    continue;
                }
                case "quit":{
                    System.out.println("[User] Goodbye");
                    quit = true;
                    break;
                }
                default:{
                    System.out.println("[User] Invalid command");
                }
            }
        }
    }

    final String Digits      = "^([+-]?\\d*)$";
    final String FloatDigits = "^([+-]?\\d*\\.?\\d*)$";
    final String StringType  = "^(\".*\")$";

    private Object parseValueType(String value){
        if (value.matches(Digits)){
            Integer intObj = new Integer(Integer.parseInt(value));
            return intObj;
        }else if(value.matches(FloatDigits)){
            Float floatObj = new Float(Float.parseFloat(value));
            return floatObj;
        }else if (value.matches(StringType)){
            return value.replace("\"", "");
        }else{
            return null;
        }
    }

    /*
        Low level manipulation of get, put and delete
        if succeed, then print result
     */
    private void get(String key){
        KeyValueObj obj = this.handler.getObj(key);
        if (obj == null){
            System.out.println("[User] value with key " + key + " was not found");
            return;
        }
        System.out.println(obj.toString());
    }

    private void put(String key, Object value){
        KeyValueObj obj = null;
        if (value instanceof Integer){
            obj = new KeyValueObj(key, (Integer)value);
        }
        if (value instanceof Float){
            obj = new KeyValueObj(key, (Float)value);
        }
        if (value instanceof String){
            obj = new KeyValueObj(key, (String)value);
        }
        boolean result = this.handler.addObj(obj);
        if (result){
            System.out.println("[User] put data successfully");
        }else{
            System.out.println("[User] fail to put data");
            System.out.println("[User] Obj: " + obj.toString());
        }
    }

    private void delete(String key){
        boolean result = this.handler.deleteObj(key);
        if (result){
            System.out.println("[User] delete data successfully");
        }else{
            System.out.println("[User] key " + key + " was not found");
        }
    }

    private void getAll(){
        this.handler.getAllObj();
    }
}
