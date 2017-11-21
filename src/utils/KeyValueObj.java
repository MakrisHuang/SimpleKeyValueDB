package utils;

import java.io.Serializable;
import java.util.Objects;

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
 * This is the key-value object with many utilities functions
 */
// Should be refactored by polymoriphasm
public class KeyValueObj implements Serializable{
    public String key;

    private Integer intValue;
    private Float doubleValue;
    private String stringValue;

    private Type valueType;
    enum Type{
        Integer, Double, String
    }

    public KeyValueObj(String key){

    }

    public KeyValueObj(String key, Integer intValue){
        this.key = key;
        this.intValue = intValue;
        this.valueType = Type.Integer;
    }

    public KeyValueObj(String key, Float doubleValue){
        this.key = key;
        this.doubleValue = doubleValue;
        this.valueType = Type.Double;
    }

    public KeyValueObj(String key, String stringValue){
        this.key = key;
        this.stringValue = stringValue;
        this.valueType = Type.String;
    }

    @Override
    public String toString(){
        switch (this.valueType){
            case Integer:
                return "key: " + this.key + ", value: " + this.intValue.toString() +
                        ", type: Integer";
            case Double:
                return "key: " + this.key + ", value: " + this.doubleValue.toString() +
                        ", type: Float";
            case String:
                return "key: " + this.key + ", value: " + this.stringValue +
                        ", type: String";
        }
        return "Missing Key or value";
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(this.key);
    }
}

