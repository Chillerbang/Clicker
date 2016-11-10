package Client;
import java.awt.BorderLayout;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import Server.ClientToServer;
/**
 * 
 * @author Lance 215018300
 *	this class handles any data sent from the server to this class
 */

public class ClientGameThread implements Runnable{
	DatagramPacket receivePacket; 
	Clientpanel CPL;
	
	public ClientGameThread(Clientpanel CPL) {
		this.CPL = CPL;
	}
	
	@Override
	public void run() {
		byte[] receiveData = new byte[2024];
		while (true){
			try {
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				if ((CPL != null)&&(receivePacket != null)&&(Clientpanel.clientSocket != null)){ // avoiding null exceptions / corrupted packets
					Clientpanel.clientSocket.receive(receivePacket);
					DataServerToClient Dstc = (DataServerToClient) deserialize(receivePacket.getData()); // deserialize the byte[] from packet
					CPL.CTS = (ClientToServer)Dstc.CTS; // up casting class sent
					CPL.gs = new GameSpace(CPL.CTS.ID,Dstc.GameOver,Dstc.CTS.ilose); // creating a space class that handles the visuals of the game  
					CPL.gs.update(Dstc.scoreLocal, Dstc.scoreEnenmy); // updates the visuals
					CPL.UpdateScores(Dstc.scoreEnenmy, Dstc.scoreLocal); // updates locations in class to send to server again
					CPL.add(CPL.gs,BorderLayout.CENTER); // places visuals
					
				}
				
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException { // converts serialize class to byte[]
        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
            try(ObjectInputStream o = new ObjectInputStream(b)){
                return o.readObject();
            }
        }
    }
}
