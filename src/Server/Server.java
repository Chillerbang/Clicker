package Server;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Vector;

import Client.DataServerToClient;

/**
 * 
 * @author Lance
 * this class is the server that gets a packet stores it location it was sent from
 * then sends data when its game is initiated 
 */
		
public class Server {
	public static Vector<DataServerToClient> DataStr; // for information to client
	public static Vector<DatagramPacket> SendVect;
	 // to keep clients in order
	private static int scoreInc = 10;
	private static int winningScore = 1590;
	
	   public static void main(String args[]) throws Exception
	      {
	         DatagramSocket serverSocket = new DatagramSocket(6969);
	            byte[] receiveData = new byte[2024];
	            byte[] sendData = new byte[2024];
	            int pair =0;
	            SendVect = new Vector<DatagramPacket>(); // stores all clients locations
	            DataStr = new Vector<DataServerToClient>(); // stores all the data to be sent to clients, objects controlled by what the client sends
	            while(true)
	               {
	            	System.out.println("Space" + DataStr.size());
	                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		                  serverSocket.receive(receivePacket);
		                  ClientToServer CTS = (ClientToServer)deserialize(receivePacket.getData());
		                  
		                  if(CTS!=null){
		                	  
			                  if (CTS.ID == -1){ // client is new and needs some id
			                	  CTS.ID = DataStr.size();
			                	  SendVect.add(receivePacket);
			                	  System.out.println("Datasize " + DataStr.size());
			                	  DataStr.add(new DataServerToClient());
				                  switch(CTS.ID % 2){
				                  case 0:
				                	  // pair + 1
				                	  pair = 1;
				                	  break;
				                  case 1:
				                	  // pair - 1
				                	  pair = -1;
				                	  break;
				                  }
			                	  DataStr.elementAt(CTS.ID).CTS = new ClientToServer();
			                	  DataStr.elementAt(CTS.ID).CTS.ID = CTS.ID;
				                  int EnemyID = CTS.ID + pair;
				                  System.out.println("Enemy " + EnemyID);
				                  DataStr.elementAt(CTS.ID).EneID =EnemyID;
				                  DataStr.elementAt(CTS.ID).scoreLocal = 0;
				                  DataStr.elementAt(CTS.ID).scoreEnenmy = 0;
			                  }
			                  
			                  if (!DataStr.elementAt(CTS.ID).GameOver){
				                  System.out.println("Local ID: " + CTS.ID);
				                  //check score
				                  System.out.println("Enemy id: " + DataStr.elementAt(CTS.ID).EneID);
				                  // increment score
				                  DataStr.elementAt(CTS.ID).scoreLocal += scoreInc;
				                  
				                  if (DataStr.size() > DataStr.elementAt(CTS.ID).EneID){
				                  DataStr.elementAt(DataStr.elementAt(CTS.ID).EneID).scoreEnenmy = DataStr.elementAt(CTS.ID).scoreLocal;
				                  }
									  if (DataStr.elementAt(CTS.ID).scoreLocal > winningScore){
										  DataStr.elementAt(CTS.ID).CTS.ilose = false;
										  DataStr.elementAt(CTS.ID).GameOver = true;

						                  if (DataStr.size() > DataStr.elementAt(CTS.ID).EneID){
										  DataStr.elementAt(DataStr.elementAt(CTS.ID).EneID).CTS.ilose = true;
										  DataStr.elementAt(DataStr.elementAt(CTS.ID).EneID).GameOver = true;
						                  }
										  
									  }
			                  }
			                  //	construct packet for opponent update then send it
			                  if (DataStr.size() > DataStr.elementAt(CTS.ID).EneID){
			                  DataStr.elementAt(CTS.ID).scoreEnenmy = DataStr.elementAt(DataStr.elementAt(CTS.ID).EneID).scoreLocal;			                  
			                  InetAddress IPAddress1 = SendVect.get(DataStr.elementAt(CTS.ID).EneID).getAddress();
			                  int port1 = ((DatagramPacket) SendVect.get(DataStr.elementAt(CTS.ID).EneID)).getPort();
			                  byte[] sendData1 = serialize((DataServerToClient) DataStr.get(DataStr.elementAt(CTS.ID).EneID));
			                  DatagramPacket sendPacket1 = new DatagramPacket(sendData1, sendData1.length, IPAddress1, port1);
			                  serverSocket.send(sendPacket1);
			                  System.out.println("enimi Ended: " + ((DataServerToClient) DataStr.get(DataStr.elementAt(CTS.ID).EneID)).EneID);
			                  }			                  
			                  // sends data to client that connected
			                  InetAddress IPAddress = receivePacket.getAddress();
			                  System.out.println("CTS ID " +  CTS.ID);
			                  int port = receivePacket.getPort();
			                  sendData = serialize((DataServerToClient) DataStr.get(CTS.ID));
			                  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);			                  
			                  serverSocket.send(sendPacket);			                  
			                  System.out.println("Ended: ");
		                  
		                  }
	               }
	      }
	
	   
	    public static byte[] serialize(Object obj) throws IOException {
	        try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
	            try(ObjectOutputStream o = new ObjectOutputStream(b)){
	                o.writeObject(obj);
	            }
	            return b.toByteArray();
	        }
	    }
	    
	    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
	        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
	            try(ObjectInputStream o = new ObjectInputStream(b)){
	                return o.readObject();
	            }
	        }
	    }
	}