import java.util.ArrayList;

public class Main {
	
	public static boolean verbose = true;
	
	public static void main(String[] args) {
		
		//Billets
		BilletAvion billet = new BilletAvion(Commons.Destination.PARIS,Commons.Destination.LONDRES, 200);
		
		//Fournisseurs
		ArrayList<Fournisseur> fournisseurs = new ArrayList<Fournisseur>();
		Fournisseur airFrance = new Fournisseur("AirFrance");
		Fournisseur americanAirline = new Fournisseur("American Airline");
		
		airFrance.addBillet(billet);
		americanAirline.addBillet(billet);
		
		fournisseurs.add(airFrance);
		fournisseurs.add(americanAirline);
		
		//Négociateurs
		Negociateur bob = new Negociateur(fournisseurs);
		bob.setBillet(billet);
		
		//Démarrage des threads
		airFrance.start();
		americanAirline.start();;
		bob.start();
	}

}
