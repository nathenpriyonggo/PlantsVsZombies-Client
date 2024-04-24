import java.io.Serializable;
import java.util.Objects;


public class Message implements Serializable {


    static final long serialVersionUID = 42L;
    private String playerName, OpponentName, data, flag;


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
    public int getSunPoints() {return Integer.parseInt(data);}
    public boolean needToClearRankingList() {return Objects.equals(data, "clear");}
    public boolean flagIsCheckUniqueName() {return Objects.equals(flag, "flagIsCheckUniqueName");}
    public boolean usernameIsUnique() {return Objects.equals(data, "true");}
    public boolean flagIsNewClientJoined() {return Objects.equals(flag, "flagIsNewClientJoined");}
    public boolean flagIsClientWon() {return Objects.equals(flag, "flagIsClientWon");}
    public boolean flagIsClientLost() {return Objects.equals(flag, "flagIsClientLost");}
    public boolean flagIsStartGameYourTurn() {return Objects.equals(flag, "flagIsStartGameYourTurn");}
    public boolean flagIsStartGameOppTurn() {return Objects.equals(flag, "flagIsStartGameOppTurn");}
    public boolean flagIsSendPoints() {return Objects.equals(flag, "flagIsSendPoints");}
    public boolean flagIsUpdateRankings() {return Objects.equals(flag, "flagIsUpdateRankings");}
}