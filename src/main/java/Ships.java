import java.io.Serializable;
import java.util.Objects;

public class Ships implements Serializable {

    static final long serialVersionUID = 42L;
    Ship peaShip, sunShip, wallShip, snowShip, chompShip;
    String playerName, opponentName;
    boolean inGame, myTurn;
    private boolean duplicate = false;

    // Default constructor
    public Ships() {
        peaShip = new Ship(2);
        sunShip = new Ship(3);
        wallShip = new Ship(3);
        snowShip = new Ship(4);
        chompShip = new Ship(5);
    }

    // Copy constructor
    public Ships(Ships other) {
        this.peaShip = new Ships.Ship(other.peaShip);
        this.sunShip = new Ships.Ship(other.sunShip);
        this.wallShip = new Ships.Ship(other.wallShip);
        this.snowShip = new Ships.Ship(other.snowShip);
        this.chompShip = new Ships.Ship(other.chompShip);
        this.playerName = other.playerName;
        this.opponentName = other.opponentName;
        this.inGame = other.inGame;
        this.myTurn = other.myTurn;
        this.duplicate = other.duplicate;
    }

    /*
    Helper Functions
     */


    // Add two size ship based on user arguments
    public void addNodeToPea(Element elem) {
        peaShip.addNode(elem);
    }

    // Add first three size ship based on user arguments
    public void addNodeToSun(Element elem) {
        sunShip.addNode(elem);
    }

    // Add second three ship based on user arguments
    public void addNodeToWall(Element elem) {
        wallShip.addNode(elem);
    }

    // Add four size ship based on user arguments
    public void addNodeToSnow(Element elem) {
        snowShip.addNode(elem);
    }

    // Add five size ship based on user arguments
    public void addNodeToChomp(Element elem) {
        chompShip.addNode(elem);
    }


    // Update ships by calling all the didHit functions
    public Element didItHitZombie(int x, int y) {
        if (didHitPea(x, y)) {
            return new Element(x, y, 2, 2, "Zombies/dead_zombie.png", "");
        }
        else if (didHitSun(x, y)) {
            return new Element(x, y, 3, 2, "Zombies/dead_cone.png", "");
        }
        else if (didHitWall(x, y)) {
            return new Element(x, y, 3, 2, "Zombies/dead_bucket.png", "");
        }
        else if (didHitSnow(x, y)) {
            return new Element(x, y, 4, 2, "Zombies/dead_knight.png", "");
        }
        else if (didHitChomp(x, y)) {
            return new Element(x, y, 5, 2, "Zombies/dead_yeti.png", "");
        }
        else {
            return new Element(x, y, 0, 0, "Zombies/miss.png", "");
        }
    }

    // Update ships by calling all the didHit functions
    public Element didItHitPlant(int x, int y) {
        if (didHitPea(x, y)) {
            return new Element(x, y, 2, 2, "Plants/dead_peashooter.png", "");
        }
        else if (didHitSun(x, y)) {
            return new Element(x, y, 3, 2, "Plants/dead_sunflower.png", "");
        }
        else if (didHitWall(x, y)) {
            return new Element(x, y, 3, 2, "Plants/dead_wallnut.png", "");
        }
        else if (didHitSnow(x, y)) {
            return new Element(x, y, 4, 2, "Plants/dead_snowpea.png", "");
        }
        else if (didHitChomp(x, y)) {
            return new Element(x, y, 5, 2, "Plants/dead_chomper.png", "");
        }
        else {
            return new Element(x, y, 0, 0, "Plants/miss.png", "");
        }
    }

    // Update Pea ship, return if hit, false otherwise
    public boolean didHitPea(int x, int y) {
        Ship currShip = peaShip;
        if (x == currShip.elem1.getX() && y == currShip.elem1.getY()) {
            currShip.elem1.setElementState(2);
            return true;
        }
        else if (x == currShip.elem2.getX() && y == currShip.elem2.getY()) {
            currShip.elem2.setElementState(2);
            return true;
        }
        else {return false;}
    }

    // Update Sun ship, return if hit, false otherwise
    public boolean didHitSun(int x, int y) {
        Ship currShip = sunShip;
        if (x == currShip.elem1.getX() && y == currShip.elem1.getY()) {
            currShip.elem1.setElementState(2);
            return true;
        }
        else if (x == currShip.elem2.getX() && y == currShip.elem2.getY()) {
            currShip.elem2.setElementState(2);
            return true;
        }
        else if (x == currShip.elem3.getX() && y == currShip.elem3.getY()) {
            currShip.elem3.setElementState(2);
            return true;
        }
        else {return false;}
    }

    // Update Wall ship, return if hit, false otherwise
    public boolean didHitWall(int x, int y) {
        Ship currShip = wallShip;
        if (x == currShip.elem1.getX() && y == currShip.elem1.getY()) {
            currShip.elem1.setElementState(2);
            return true;
        }
        else if (x == currShip.elem2.getX() && y == currShip.elem2.getY()) {
            currShip.elem2.setElementState(2);
            return true;
        }
        else if (x == currShip.elem3.getX() && y == currShip.elem3.getY()) {
            currShip.elem3.setElementState(2);
            return true;
        }
        else {return false;}
    }

    // Update Snow ship, return if hit, false otherwise
    public boolean didHitSnow(int x, int y) {
        Ship currShip = snowShip;
        if (x == currShip.elem1.getX() && y == currShip.elem1.getY()) {
            currShip.elem1.setElementState(2);
            return true;
        }
        else if (x == currShip.elem2.getX() && y == currShip.elem2.getY()) {
            currShip.elem2.setElementState(2);
            return true;
        }
        else if (x == currShip.elem3.getX() && y == currShip.elem3.getY()) {
            currShip.elem3.setElementState(2);
            return true;
        }
        else if (x == currShip.elem4.getX() && y == currShip.elem4.getY()) {
            currShip.elem4.setElementState(2);
            return true;
        }
        else {return false;}
    }

    // Update Chomp ship, return if hit, false otherwise
    public boolean didHitChomp(int x, int y) {
        Ship currShip = chompShip;
        if (x == currShip.elem1.getX() && y == currShip.elem1.getY()) {
            currShip.elem1.setElementState(2);
            return true;
        }
        else if (x == currShip.elem2.getX() && y == currShip.elem2.getY()) {
            currShip.elem2.setElementState(2);
            return true;
        }
        else if (x == currShip.elem3.getX() && y == currShip.elem3.getY()) {
            currShip.elem3.setElementState(2);
            return true;
        }
        else if (x == currShip.elem4.getX() && y == currShip.elem4.getY()) {
            currShip.elem4.setElementState(2);
            return true;
        }
        else if (x == currShip.elem5.getX() && y == currShip.elem5.getY()) {
            currShip.elem5.setElementState(2);
            return true;
        }
        else {return false;}
    }

    public boolean isPositionHit(int x, int y) {
        return (didHitPea(x, y) || didHitSun(x, y) || didHitChomp(x,y) || didHitSnow(x, y) || didHitWall(x, y));

    }

    // Return if the all elements of pea is sunk
    public boolean isPeaSunk() {
        return peaShip.total() == 4;
    }

    // Return if the all elements of sun is sunk
    public boolean isSunSunk() {
        return sunShip.total() == 6;
    }

    // Return if the all elements of wall is sunk
    public boolean isWallSunk() {
        return wallShip.total() == 6;
    }

    // Return if the all elements of snow is sunk
    public boolean isSnowSunk() {
        return snowShip.total() == 8;
    }

    // Return if the all elements of chomp is sunk
    public boolean isChompSunk() {
        return chompShip.total() == 10;
    }

    // Return if all Ships have sunk
    public boolean isAllSunk() {
        return isPeaSunk() && isSunSunk() && isWallSunk() && isSnowSunk() && isChompSunk();
    }

    // Returns the sum of all the ships
    public int getSum() {
        return peaShip.total() + sunShip.total()
                + wallShip.total() + snowShip.total()
                + chompShip.total();
    }

    // FOR AI CLASS

    // Check if there is a ship at the specified position (x, y)
    public boolean isShipAtPosition(int x, int y) {
        return isPeaAtPosition(x, y) || isSunAtPosition(x, y) || isWallAtPosition(x, y) ||
                isSnowAtPosition(x, y) || isChompAtPosition(x, y);
    }

    // Check if there is a pea ship at the specified position
    private boolean isPeaAtPosition(int x, int y) {
        Ship currShip = peaShip;
        if (currShip.elem1 == null || currShip.elem2 == null) {
            return false;
        }

        if (x == currShip.elem1.getX() && y == currShip.elem1.getY()) {
            return true;
        }
        else if (x == currShip.elem2.getX() && y == currShip.elem2.getY()) {
            return true;
        }
        else {return false;}
    }

    // Check if there is a sun ship at the specified position
    private boolean isSunAtPosition(int x, int y) {
        Ship currShip = sunShip;
        if (currShip.elem1 == null || currShip.elem2 == null || currShip.elem3 == null) {
            return false;
        }

        if (x == currShip.elem1.getX() && y == currShip.elem1.getY()) {
            return true;
        }
        else if (x == currShip.elem2.getX() && y == currShip.elem2.getY()) {
            return true;
        }
        else if (x == currShip.elem3.getX() && y == currShip.elem3.getY()) {
            return true;
        }
        else {return false;}
    }

    // Check if there is a wall ship at the specified position
    private boolean isWallAtPosition(int x, int y) {
        Ship currShip = wallShip;
        if (currShip.elem1 == null || currShip.elem2 == null || currShip.elem3 == null) {
            return false;
        }

        if (x == currShip.elem1.getX() && y == currShip.elem1.getY()) {
            return true;
        }
        else if (x == currShip.elem2.getX() && y == currShip.elem2.getY()) {
            return true;
        }
        else if (x == currShip.elem3.getX() && y == currShip.elem3.getY()) {
            return true;
        }
        else {return false;}
    }

    // Check if there is a snow ship at the specified position
    private boolean isSnowAtPosition(int x, int y) {
        Ship currShip = snowShip;
        if (currShip.elem1 == null || currShip.elem2 == null
                || currShip.elem3 == null || currShip.elem4 == null) {
            return false;
        }

        if (x == currShip.elem1.getX() && y == currShip.elem1.getY()) {
            return true;
        }
        else if (x == currShip.elem2.getX() && y == currShip.elem2.getY()) {
            return true;
        }
        else if (x == currShip.elem3.getX() && y == currShip.elem3.getY()) {
            return true;
        }
        else if (x == currShip.elem4.getX() && y == currShip.elem4.getY()) {
            return true;
        }
        else {return false;}
    }

    // Check if there is a chomp ship at the specified position
    private boolean isChompAtPosition(int x, int y) {
        Ship currShip = chompShip;
        if (currShip.elem1 == null || currShip.elem2 == null || currShip.elem3 == null
                || currShip.elem4 == null || currShip.elem5 == null) {
            return false;
        }

        if (x == currShip.elem1.getX() && y == currShip.elem1.getY()) {
            return true;
        }
        else if (x == currShip.elem2.getX() && y == currShip.elem2.getY()) {
            return true;
        }
        else if (x == currShip.elem3.getX() && y == currShip.elem3.getY()) {
            return true;
        }
        else if (x == currShip.elem4.getX() && y == currShip.elem4.getY()) {
            return true;
        }
        else if (x == currShip.elem5.getX() && y == currShip.elem5.getY()) {
            return true;
        }
        else {return false;}
    }

    // To return if this is the second size three ship
    public boolean isDuplicate(){
        return duplicate;
    }

    // If one size three ship is taken set duplicate to true
    public void setDuplicate(){
        this.duplicate = true;
    }

    // Returns the ship based on its size
    public Ship getShipOfSize(int size) {
        if(size == 2) {
            return peaShip;
        }
        else if(size == 3 && !isDuplicate()) {
            setDuplicate();
            return sunShip;
        }
        else if(size == 3 && isDuplicate()) {
            return wallShip;
        }
        else if (size == 4) {
            return snowShip;

        }
        else if (size == 5) {
            return chompShip;
        }
        else {
            return null;
        }
    }


    /*
    Individual Ship class
     */
    public class Ship implements Serializable {
        //        private ArrayList<Element> nodes;
        static final long serialVersionUID = 42L;
        private Element elem1, elem2, elem3, elem4, elem5;
        private int size;
        private int iter = 0;
        boolean shown = false;


        // Default constructor
        public Ship(int size) {
            elem1 = null;
            elem2 = null;
            elem3 = null;
            elem4 = null;
            elem5 = null;
            this.size = size;
        }

        // Copy constructor for Ship
        public Ship(Ship other) {
            this.size = other.size;
            this.elem1 = other.elem1 != null ? new Element(other.elem1) : null;
            this.elem2 = other.elem2 != null ? new Element(other.elem2) : null;
            this.elem3 = other.elem3 != null ? new Element(other.elem3) : null;
            this.elem4 = other.elem4 != null ? new Element(other.elem4) : null;
            this.elem5 = other.elem5 != null ? new Element(other.elem5) : null;
            this.iter = other.iter;
            this.shown = other.shown;
        }


        /*
        Helper Functions
         */

        // Add node to the ship
        public void addNode(Element elem) {
            if (elem1 == null && size >= 1) {
                elem1 = elem;
            }
            else if (elem2 == null && size >= 2) {
                elem2 = elem;
            }
            else if (elem3 == null && size >= 3) {
                elem3 = elem;
            }
            else if (elem4 == null && size >= 4) {
                elem4 = elem;
            }
            else if (elem5 == null && size >= 5) {
                elem5 = elem;
            }
            else {
                return;
            }
        }

        // Returns next node of ship     FIXME: actually until now don't need <--
        public Element next() {
            if (iter == size) {
                return null;
            }
            Element retElem;
            if (iter == 0) {retElem = elem1;}
            else if (iter == 1) {retElem = elem2;}
            else if (iter == 2) {retElem = elem3;}
            else if (iter == 3) {retElem = elem4;}
            else if (iter == 4) {retElem = elem5;}
            else {retElem = null;}
            iter++;
            return retElem;
        }

        // This function determines if the ship has been sunk also
        // Returns the sum, so we know if its sunk
        public int total() {
            int sum = 0;
            if (size >= 2) {
                sum += elem1.getElementState() + elem2.getElementState();
            }
            if (size >= 3) {
                sum += elem3.getElementState();
            }
            if (size >= 4) {
                sum += elem4.getElementState();
            }
            if (size >= 5) {
                sum += elem5.getElementState();
            }
            return sum;
        }
    }
}
