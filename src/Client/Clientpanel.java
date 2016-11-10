package Client;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import Server.ClientToServer;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * 
 * @author Lance 215018300
 *	this class holds the clients GUI interactions
 */

public class Clientpanel extends JPanel {
	public static final int WIDTH = 940;
	public static final int HEIGHT = 200;
	private Thread t;
	private JButton jb;
	private JButton btnexit;
	private JTextArea ja;
	public ClientToServer CTS;
	ClientGameThread GCT ;
	public GameSpace gs;
    static BufferedReader inFromUser;
    public static DatagramSocket clientSocket ;
    DatagramPacket send; 
    static InetAddress IPAddress ;
    byte[] sendData = new byte[2024]; // random initialization
    byte[] receiveData = new byte[2024];// random initialization
    
    // game scores
    /*
    private int local = 0;
    private int vvenemy = 0;
	*/
	public void UpdateScores(int Enem, int local){
		ja.setText(" Good luck have fun! " ); // textual greeting
    	t = null;
    	GCT = new ClientGameThread(this);
		t = new Thread(GCT);
		t.start();
    }
    
    
	public Clientpanel(){
		//setting gui and listeners
		super();
		this.setLayout(new BorderLayout());
		btnexit = new JButton("Exit");
		jb = new JButton("Run!");
		add(jb,BorderLayout.WEST);
		add(btnexit,BorderLayout.EAST);
		CTS = new ClientToServer();
	    CTS.ID = -1;
	    
	    btnexit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientToServer CtsQuit = new ClientToServer(); // initialization
				CtsQuit.ID =CTS.ID;
				CtsQuit.ilose = true;
				InetAddress IPAddress;
				try {
					if (clientSocket == null){
						clientSocket = new DatagramSocket();	
					}
					IPAddress = InetAddress.getByName("localhost");
			    byte[] sendData = new byte[2024];
					sendData = serialize(CtsQuit); // sending a  new object
			    send = new DatagramPacket(sendData, sendData.length, IPAddress, 6969); // addressing to server
					clientSocket.send(send);
				} catch (IOException e1) {
					e1.printStackTrace();
				} // sends all those packets to server
			    System.out.println("its all over"); // Game is over
			    System.exit(0);
			}
		});
	    
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				add(); // click function
			}
		});
		ja  = new JTextArea();
		add(ja,BorderLayout.SOUTH);
		Dimension Dim = new Dimension(WIDTH , HEIGHT ); // setting text area
		setPreferredSize(Dim);
		setFocusable(true);
		requestFocus();
		GCT = new ClientGameThread(this);
		t = new Thread(GCT);
		t.start();
	}
	
	public void add(){ // sends that the thing is incremented
		try {
			if (clientSocket == null){
				clientSocket = new DatagramSocket();	
			}
		    InetAddress IPAddress = InetAddress.getByName("localhost");
		    byte[] sendData = new byte[2024];
		     // this minus one indicates its a new client
		    
		    sendData = serialize(CTS);
		    send = new DatagramPacket(sendData, sendData.length, IPAddress, 6969);
		    clientSocket.send(send); // sends all those packets to server
		    System.out.println("sending");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public static byte[] serialize(Object obj) throws IOException { // converts byte[] to object
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
            try(ObjectOutputStream o = new ObjectOutputStream(b)){
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }
    
}
