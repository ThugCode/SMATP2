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
			this.nbPropositions = 0;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
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
		        		this.prixActuel = this.offre.getPrix();
		        		
		        		log("Envoie d'une offre : " + this.offre.getPrix());
		        		msg = new Message(Commons.TypeMessage.PROPOSITION, this.offre.getPrix());
		        		outToClient.writeObject(msg);
		        	} else {
		        		log("Pas de billet disponible");
		        	}
		        	break;
		        		
		        case COUNTER :
		        	log("Contre-proposition reçue : " + msg.getPrix());
		        	
		        	if(this.acceptProposition(msg.getPrix())) {
		        		log("Contre-proposition acceptée : " + this.prixActuel);
		        		
		        		msg = new Message(Commons.TypeMessage.ACCEPT, this.prixActuel);
		        		outToClient.writeObject(msg);
		        	} else {
		        		log("Envoie d'une nouvelle propostion : " + this.prixActuel);
		        		
		        		msg = new Message(Commons.TypeMessage.PROPOSITION, this.prixActuel);
		        		outToClient.writeObject(msg);
		        	}
		        	break;
		        	
		        case ACCEPT : 
		        	log("Proposition acceptée : " + msg.getPrix());
		        	finNego = true;
		        	break;
		        	
		        default :
		        	System.out.println("Default");
		        	break;
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
			this.prixActuel = (int) (prix * 1.2);
			return false;
		} else {
			log("proposition > prixMin");
			if((new Random().nextInt(10)) > 3 && this.nbPropositions < 6) {
				this.prixActuel = prix + prix/8;
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
	
	public void addBillet(BilletAvion billet) {
		this.billets.add(billet);
	}
}
