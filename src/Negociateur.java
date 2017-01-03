import java.io.IOException;
import java.net.Socket;

public class Negociateur extends Thread {

	private Socket socket;
	private BilletAvion billet;
	private Fournisseur fournisseur;

	public Negociateur(Fournisseur f) {
		this.fournisseur = f;
	}

	@Override
	public void run() {

		try {
			this.socket = new Socket(this.fournisseur.getSocket().getInetAddress(),this.fournisseur.getSocket().getLocalPort());
		} catch (IOException e) {
			e.printStackTrace();
		}

		//formulation appel offre

		//Engagement de la négociation

		//tant que négociation

		//pour chaque proposition vérification de l'acceptation

		//sinon continue ou pas

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
