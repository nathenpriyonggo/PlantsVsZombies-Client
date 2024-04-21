import java.util.ArrayList;

public class Ships {

    Ship twoSizeShip, threeOneSizeShip, threeTwoSizeShip,
            fourSizeShip, fiveSizeShip;
    String playerName;

    // Default constructor
    public Ships() {
        twoSizeShip = new Ship();
        threeOneSizeShip = new Ship();
        threeTwoSizeShip = new Ship();
        fourSizeShip = new Ship();
        fiveSizeShip = new Ship();
    }

    /*
    Helper Functions
     */
    // Set two size ship based on user arguments
    public void setTwoSizeShip(Element elem1, Element elem2) {
        twoSizeShip.setSize(2);
        twoSizeShip.addNode(elem1);
        twoSizeShip.addNode(elem2);
    }
    // Set first three size ship based on user arguments
    public void setThreeOneSizeShip(Element elem1, Element elem2, Element elem3) {
        threeOneSizeShip.setSize(3);
        threeOneSizeShip.addNode(elem1);
        threeOneSizeShip.addNode(elem2);
        threeOneSizeShip.addNode(elem3);
    }
    // Set second three ship based on user arguments
    public void setThreeTwoSizeShip(Element elem1, Element elem2, Element elem3) {
        threeTwoSizeShip.setSize(3);
        threeTwoSizeShip.addNode(elem1);
        threeTwoSizeShip.addNode(elem2);
        threeTwoSizeShip.addNode(elem3);
    }
    // Set four size ship based on user arguments
    public void setFourSizeShip(Element elem1, Element elem2,
                                Element elem3, Element elem4) {
        fourSizeShip.setSize(4);
        fourSizeShip.addNode(elem1);
        fourSizeShip.addNode(elem2);
        fourSizeShip.addNode(elem3);
        fourSizeShip.addNode(elem2);
    }
    // Set five size ship based on user arguments
    public void setFiveSizeShip(Element elem1, Element elem2,
                                Element elem3, Element elem4, Element elem5) {
        fiveSizeShip.setSize(5);
        fiveSizeShip.addNode(elem1);
        fiveSizeShip.addNode(elem2);
        fiveSizeShip.addNode(elem3);
        fiveSizeShip.addNode(elem4);
        fiveSizeShip.addNode(elem5);
    }

    // Returns the sum of all the ships
    public int getSum() {
        return twoSizeShip.total() + threeTwoSizeShip.total()
                + threeOneSizeShip.total() + fourSizeShip.total()
                + fiveSizeShip.total();
    }

    /*
    Individual Ship class
     */
    public class Ship {
        private ArrayList<Element> nodes;
        private int size;
        private boolean sunk;
        private int iter = 0;

        // Default constructor
        public Ship() {
            nodes = new ArrayList<>();
        }

        /*
        Helper Functions
         */

        // Add node to the ship
        public void addNode(Element elem) {nodes.add(elem);}
        // Size of the ship
        public void setSize(int size) {this.size = size;}
        // Returns next node of ship
        public Element next() {
            if (iter == size) {
                return null;
            }
            Element elem = nodes.get(iter);
            iter++;
            return elem;
        }

        // This function determines if the ship has been sunk also
        // Returns the sum, so we know if its sunk
        public int total() {
            int sum = 0;
            for(Element node : nodes) {
                sum += node.getElementState();
            }
            //When all ships are hit (state 2), change ship states to 3 (sunk)
            if (sum == 2 * size) {
                for(int i = 0; i < size; i++) {
                    nodes.get(i).setElementState(3);
                }
                sunk = true;
            }
            return sum;
        }

        // Returns if ship is sunk
        public boolean isSunk() {return sunk;}
    }
}
