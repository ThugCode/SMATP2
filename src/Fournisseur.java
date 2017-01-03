import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Fournisseur extends Thread {
	
	private ServerSocket socket;
	private ArrayList<BilletAvion> billets;
	
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
		
		//Proposition offre valable
		
		//Début négociation
		
		//pour chaque proposition vérification de l'acceptation
		
		//sinon continue ou pas
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
