import java.io.Serializable;
import java.util.Objects;

public class Element implements Serializable {
    static final long serialVersionUID = 42L;
    private int x, y, shipSize, elementState;
    private String player, opp, flag;

    public Element(String player, String opp, int x, int y, int shipSize, int elementState, String flag) {
        this.player = player;
        this.opp = opp;
        this.x = x;
        this.y = y;
        this.shipSize = shipSize;
        this.elementState = elementState;
        this.flag = flag;
    }

    /*
    Public Element Helper Functions
     */
    public String getPlayer() {return player;}
    public String getOpponent() {return opp;}
    public int getX() {return x;}
    public int getY() {return y;}
    public int getShipSize() {return shipSize;}
    public int getElementState() {return elementState;}

    public boolean flagIsAddNewElement() {return Objects.equals(flag, "flagIsAddNewElement");}
}