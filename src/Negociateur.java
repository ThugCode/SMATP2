import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Classe modélisant l'agent voulant acheter un billet d'avion
 * à une compagnie aérienne
 * 
 * @author Loïc GERLAND / Léo LETOURNEUR
 *
 */
public class Negociateur extends Thread {
	
	private final static int MAX_PROPOSITIONS = 5;

	private String nom;
	private Socket socket;
	private BilletAvion billet;
	private ArrayList<Fournisseur> fournisseurs;
	private String meilleurFournisseur;
	private int meilleurOffre;
	private int prixActuel;
	private int prixMax;
	private int nbPropositions;

	public Negociateur(String nom, ArrayList<Fournisseur> f) {
		this.nom = nom;
		this.fournisseurs = f;
		this.nbPropositions = 0;
		this.meilleurFournisseur = null;
		this.meilleurOffre = 0;
	}

	@Override
	public void run() {

		try {
			//On effectue une négociation avec chaque fournisseur
			for(Fournisseur fournisseur : this.fournisseurs) {

				this.socket = new Socket(fournisseur.getSocket().getInetAddress(), fournisseur.getSocket().getLocalPort());
				ObjectOutputStream outToServer = new ObjectOutputStream(this.socket.getOutputStream());
				ObjectInputStream inFromServer = new ObjectInputStream(this.socket.getInputStream());

				//Envoie de l'appel d'offre pour le billet
				Message msg = new Message(Commons.TypeMessage.CALL, this.billet);
				this.prixMax = (int) (this.billet.getPrix() * 1.2);
				this.nbPropositions = 0;

				outToServer.writeObject(msg);

				boolean finNego = false;

				while(!finNego) {
					msg = (Message) inFromServer.readObject();

					switch(msg.getType()) {

					//Cas d'une réception d'une proposition de billet
					case PROPOSITION :
						log("Proposition reçue : " + msg.getPrix());

						if(this.acceptProposition(msg.getPrix())) {
							log("Proposition acceptée : " + this.prixActuel);
							msg = new Message(Commons.TypeMessage.ACCEPT, this.prixActuel);
							outToServer.writeObject(msg);
						} else {
							log("Contre-Proposition soumise : " + this.prixActuel);
							msg = new Message(Commons.TypeMessage.COUNTER, this.prixActuel);
							outToServer.writeObject(msg);

							this.nbPropositions++;
						}
						break;
					
					//Cas de l'acceptation de l'enchère
					case ACCEPT :
						log("Billet négocié au prix de : " + this.prixActuel);
						finNego = true;
						
						//Enregistrement de la meilleur offre
						if(this.meilleurFournisseur == null) {
							this.meilleurFournisseur = fournisseur.getNom();
							this.meilleurOffre = this.prixActuel;
						} else if (this.prixActuel < this.meilleurOffre) {
							this.meilleurFournisseur = fournisseur.getNom();
							this.meilleurOffre = this.prixActuel;
						}
						break;

					default :
						System.out.println("Default");
					}

				}

				this.socket.close();
			}
			
			if(this.meilleurFournisseur == null) {
				log("Aucun billet acheté ou trouvé");
			} else {
				log("Billet acheté chez : " + this.meilleurFournisseur + " pour : " + this.meilleurOffre);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Fonction qui gère la négociation
	 */
	private boolean acceptProposition(int prix) {
		double pourcentage = ThreadLocalRandom.current().nextDouble(0.1, 0.4);
		int rand = ThreadLocalRandom.current().nextInt(11);
		
		if(this.nbPropositions == 0) {
			this.prixActuel = (int) (prix - (prix * pourcentage));
			return false;
		}
		
		if(prix > this.prixMax) {
			log("proposition > prixMax");
			this.prixActuel = (int) (prix - (prix*pourcentage));
			return false;
		}
		else {
			log("proposition < prixMax");
			if(rand > 1 && this.nbPropositions < Negociateur.MAX_PROPOSITIONS) {
				int tmp = prix - this.prixActuel;
				this.prixActuel = (int) (this.prixActuel + (tmp * pourcentage));
				log("On essaye de faire baisser le prix");
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
			System.out.println("Négociateur " + this.nom + " : " + s);
	}

	public BilletAvion getBillet() {
		return billet;
	}

	public void setBillet(BilletAvion billet) {
		this.billet = billet;
	}

	public Socket getSocket() {
		return socket;
	}
}
