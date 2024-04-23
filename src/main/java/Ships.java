import java.io.Serializable;
import java.util.ArrayList;

public class Ships implements Serializable {

    static final long serialVersionUID = 42L;
    Ship peaShip, sunShip, wallShip, snowShip, chompShip;
    String playerName, opponentName;
    boolean inGame, myTurn;

    // Default constructor
    public Ships() {
        peaShip = new Ship(2);
        sunShip = new Ship(3);
        wallShip = new Ship(3);
        snowShip = new Ship(4);
        chompShip = new Ship(5);
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


    // Returns the sum of all the ships
    public int getSum() {
        return peaShip.total() + sunShip.total()
                + wallShip.total() + snowShip.total()
                + chompShip.total();
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
            System.out.println(iter);
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
