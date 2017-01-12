import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Fournisseur extends Thread {

	private final static int MAX_PROPOSITIONS = 5;

	private String nom;
	private ServerSocket socket;
	private ArrayList<BilletAvion> billets;
	private BilletAvion offre;
	private int nbPropositions;
	private int prixActuel;

	public Fournisseur(String nom) {
		try {
			this.nom = nom;
			this.socket = new ServerSocket(0);
			this.billets = new ArrayList<BilletAvion>();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Socket socketClient;
		try {

			while(true) {
				socketClient = this.socket.accept();
				ObjectOutputStream outToClient = new ObjectOutputStream(socketClient.getOutputStream());
				ObjectInputStream inFromClient = new ObjectInputStream(socketClient.getInputStream());

				boolean finNego = false;
				this.nbPropositions = 0;

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

						msg = new Message(Commons.TypeMessage.ACCEPT, this.prixActuel);
						outToClient.writeObject(msg);
						break;

					default :
						System.out.println("Default");
						break;
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private boolean acceptProposition(int prix) {
		double pourcentage = ThreadLocalRandom.current().nextDouble(1.1, 1.5);

		if(prix < this.offre.getPrix() / 2) {
			log("proposition < prixMin");
			this.prixActuel = (int) (this.offre.getPrix() * pourcentage);
			return false;
		} else {
			log("proposition > prixMin");
			if(this.nbPropositions < Fournisseur.MAX_PROPOSITIONS) {
				this.prixActuel = (int) (prix * pourcentage);
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
			System.out.println("Fournisseur "+ this.nom +" : " + s);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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
