import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Fournisseur extends Thread {
	
	private ServerSocket socket;
	private ArrayList<BilletAvion> billets;
	private BilletAvion offre;
	private int nbPropositions;
	private int prixActuel;
	
	public Fournisseur() {
		try {
			this.socket = new ServerSocket(8080);
			this.billets = new ArrayList<BilletAvion>();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		//Reception appel offre
		Socket socketClient;
		try {
			socketClient = this.socket.accept();
			ObjectOutputStream outToClient = new ObjectOutputStream(socketClient.getOutputStream());
	        ObjectInputStream inFromClient = new ObjectInputStream(socketClient.getInputStream());
	        
	        boolean finNego = false;
	        
	        while(!finNego) {
		        Message msg = (Message) inFromClient.readObject();
		        
		        switch(msg.getType()) {
		        
		        case CALL : 
		        	if(billets.contains(msg.getBillet())) {
		        		int i = billets.indexOf(msg.getBillet());
		        		this.offre = billets.get(i);
		        		msg = new Message(Commons.TypeMessage.PROPOSITION, this.offre.getPrix());
		        	}
		        		
		        case COUNTER :
		        	
		        case ACCEPT : 
		        	log("Proposition acceptée : " + msg.getPrix());
		        default :
		        	System.out.println("Default");
		        }
	        }
	        
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//Proposition offre valable
		
		//Début négociation
		
		//pour chaque proposition vérification de l'acceptation
		
		//sinon continue ou pas
	}
	
	private boolean acceptProposition(int prix) {
		if(prix < this.offre.getPrixMin()) {
			log("proposition < prixMin");
			return false;
		} else {
			log("proposition > prixMin");
			if((new Random().nextInt(10)) > 5 && this.nbPropositions < 6) {
				this.prixActuel += (prix - this.prixActuel)/8;
				log("On essaye de faire monter le prix");
				return false;
			}
			else {
				this.prixActuel = prix;
				return true;
			}
		}
	}
	
	private void log(String s) {
		if(Main.verbose)
			System.out.println("Fournisseur : " + s);
	}

	public ArrayList<BilletAvion> getBillets() {
		return billets;
	}

	public void setBillets(ArrayList<BilletAvion> billets) {
		this.billets = billets;
	}

	public ServerSocket getSocket() {
		return socket;
	}
}
