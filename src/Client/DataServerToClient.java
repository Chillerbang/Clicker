package Client;
import java.io.Serializable;
import java.net.DatagramPacket;

import Server.ClientToServer;

public class DataServerToClient implements Serializable{
	/**
	 * Class sent from server to client to control game data
	 * Client sends back part of this class then a new version of this class is created
	 * sent to client and so on to create program flow
	 */
	private static final long serialVersionUID = 1L;
	public int ID;
	public int EneID;
	public boolean GameOver;
	public int scoreLocal;
	public int scoreEnenmy;
	public ClientToServer CTS;
}
