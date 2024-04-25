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
import java.io.Serializable;
import java.util.Objects;

public class Element implements Serializable {
    static final long serialVersionUID = 42L;
    private int x, y, shipSize, elementState;
    private String player, opp, flag, url;

    public Element(int x, int y, int shipSize, int elementState, String url, String flag) {
        this.x = x;
        this.y = y;
        this.shipSize = shipSize;
        this.elementState = elementState;
        this.url = url;
        this.flag = flag;
    }

    public Element(Element other) {
        this.x = other.x;
        this.y = other.y;
        this.shipSize = other.shipSize;
        this.elementState = other.elementState;
        this.url = other.url;
        this.flag = other.flag;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getShipSize() { return shipSize; }
    public int getElementState() { return elementState; }
    public String getUrl() { return url; }
    public String getFlag() { return flag; }

    public void setElementState(int newElementState) { this.elementState = newElementState; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return x == element.x && y == element.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
