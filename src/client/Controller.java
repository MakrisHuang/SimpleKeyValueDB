package client;

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
 * This class interacts with users in command line
 * in which user can (1) put (2) get (3) getall (4) delete
 */
public class Controller {
    private ArrayList<User> users; // for future used

    public Controller(){
    }

    public static void main(String[] args){
        System.out.println("Welcome to use key-value database");
        System.out.println("put command: put k1 \"v1\"");
        System.out.println("Get command: get k1");
        System.out.println("Delete command: delete k1");
        System.out.println("Getall command: getall");
        User user = new User();
        user.listenUserInput();
    }
}
