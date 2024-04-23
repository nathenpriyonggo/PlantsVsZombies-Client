import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;


/*
Client Thread Class
 */
public class Client extends Thread{

	
	public Socket socketClient;
	public ObjectOutputStream out;
	public ObjectInputStream in;
	private Consumer<Serializable> callback;

	// Default Constructor
	Client(Consumer<Serializable> call){callback = call;}
	
	public void run() {
		
		try {
			socketClient= new Socket("127.0.0.1",5555);
			out = new ObjectOutputStream(socketClient.getOutputStream());
			in = new ObjectInputStream(socketClient.getInputStream());
			socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {}
		
		while(true) {

			// Receives input message
			try {
				Object data = in.readObject();
				callback.accept((Serializable) data);
			}
			catch(Exception e) {}
		}
    }
	
	public void send(Object msg) {
		
		try {out.writeObject(msg);}
		catch (IOException e) {e.printStackTrace();}
	}
}
