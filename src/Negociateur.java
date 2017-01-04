import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class Negociateur extends Thread {

	private Socket socket;
	private BilletAvion billet;
	private Fournisseur fournisseur;
	private int prixActuel;
	private int prixMax;
	private int nbPropositions;

	public Negociateur(Fournisseur f) {
		this.fournisseur = f;
		this.nbPropositions = 0;
	}

	@Override
	public void run() {

		try {
			this.socket = new Socket(this.fournisseur.getSocket().getInetAddress(),this.fournisseur.getSocket().getLocalPort());
			ObjectOutputStream outToServer = new ObjectOutputStream(this.socket.getOutputStream());
			ObjectInputStream inFromServer = new ObjectInputStream(this.socket.getInputStream());

			Message msg = new Message(Commons.TypeMessage.CALL, this.billet);

			outToServer.writeObject(msg);

			boolean finNego = false;

			while(!finNego) {
				msg = (Message) inFromServer.readObject();

				switch(msg.getType()) {

				case PROPOSITION :
					log("Proposition : " + msg.getPrix());

					if(this.acceptProposition(msg.getPrix())) {
						log("Proposition acceptée : " + this.prixActuel);
						finNego = true;
						msg = new Message(Commons.TypeMessage.ACCEPT, this.prixActuel);
						outToServer.writeObject(msg);
					} else {
						log("Contre-Proposition : " + this.prixActuel);
						msg = new Message(Commons.TypeMessage.COUNTER, this.prixActuel);
						outToServer.writeObject(msg);
					};
					
				case ACCEPT :
					log("Contre-Proposition acceptée par le fournisseur : " + this.prixActuel);
				default :
					System.out.println("Defaut");
				}

			}

			this.socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		//formulation appel offre

		//Engagement de la négociation

		//tant que négociation

		//pour chaque proposition vérification de l'acceptation

		//sinon continue ou pas

	}

	private boolean acceptProposition(int prix) {
		log("Prix actuel :"+ this.prixActuel);
		if(prix > this.prixMax) {
			log("proposition > prixMax");
			return false;
		}
		else {
			log("proposition < prixMax");
			if((new Random().nextInt(10)) > 5 && this.nbPropositions < 6) {
				this.prixActuel += (prix - this.prixActuel)/8;
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
			System.out.println("Négociateur : " + s);
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
