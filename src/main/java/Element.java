import java.io.Serializable;
import java.util.Objects;

    /*   Element State:
         0 - Nothing there
         1 - Ship but not hit
         2 - Ship hit but not sunk
         3 - Ship hit and sunk -> for gravestones, return when have time
    */
    /* Player:
        "player1" "player2"
     */


public class Element implements Serializable {
    static final long serialVersionUID = 42L;
    private int x, y, shipSize, elementState;
    private String player, opp, flag, url;

    public Element(int x, int y, int shipSize,
                   int elementState, String url, String flag) {
        this.x = x;
        this.y = y;
        this.shipSize = shipSize;
        this.elementState = elementState;
        this.url = url;
        this.flag = flag;
    }

    /*
    Public Element Helper Functions
     */
//    public String getPlayer() {return player;}
//    public String getOpponent() {return opp;}
    public int getX() {return x;}
    public int getY() {return y;}
    public int getShipSize() {return shipSize;}
    public int getElementState() {return elementState;}
    public String getUrl() {return url;}
    public String getFlag() {return flag;}

    public void setElementState(int newElementState) {elementState = newElementState;}

    public boolean flagIsAddNewElement() {return Objects.equals(flag, "flagIsAddNewElement");}
}