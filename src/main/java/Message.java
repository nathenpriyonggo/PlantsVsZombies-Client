import java.io.Serializable;
import java.util.Objects;


public class Message implements Serializable {


    static final long serialVersionUID = 42L;
    private String playerName, OpponentName, data, flag;
    private Element element;


    /*
    Three-Argument Default Constructor
        ~ used for checking unique name
     */
    public Message(String playerName, String data, String flag) {
        this.playerName = playerName;
        this.data = data;
        this.flag = flag;
    }


    /*
    Public Message Helper Functions
     */
    public String getPlayerName() {return playerName;}
    public boolean flagIsCheckUniqueName() {return Objects.equals(flag, "flagIsCheckUniqueName");}
    public boolean usernameIsUnique() {return Objects.equals(data, "true");}
    public boolean flagIsNewClientJoined() {return Objects.equals(flag, "flagIsNewClientJoined");}




    /*
    Element Class
     */
    public class Element {
        int x_coord, y_coord; // The position of the element
        int shipSize; // To determine the element's size
        int elementState; // To determine the state of each element

        // Now we can create the 2d array of elements
    }
}