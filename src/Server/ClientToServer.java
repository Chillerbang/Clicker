package Server;

import java.io.Serializable;

public class ClientToServer implements Serializable{
	/**
	 * this class is the basic request class sent from client to server
	 */
	private static final long serialVersionUID = 2L;
	public int ID;
	public boolean ilose = false;
}
