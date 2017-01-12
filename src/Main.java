import java.util.ArrayList;

public class Main {
	
	public static boolean verbose = true;
	
	public static void main(String[] args) {
		
		//Billets
		BilletAvion billet1 = new BilletAvion(Commons.Destination.PARIS, Commons.Destination.LONDRES, 200);
		BilletAvion billet2 = new BilletAvion(Commons.Destination.BERLIN, Commons.Destination.NY, 600);
		
		//Fournisseurs
		ArrayList<Fournisseur> fournisseurs = new ArrayList<Fournisseur>();
		Fournisseur airFrance = new Fournisseur("AirFrance");
		Fournisseur americanAirline = new Fournisseur("American Airline");
		
		airFrance.addBillet(billet1);
		airFrance.addBillet(billet2);
		americanAirline.addBillet(billet1);
		
		fournisseurs.add(airFrance);
		fournisseurs.add(americanAirline);
		
		//Négociateurs
		Negociateur bob = new Negociateur("Bob", fournisseurs);
		Negociateur alice = new Negociateur("Alice", fournisseurs);
		
		alice.setBillet(billet2);
		bob.setBillet(billet1);
		
		//Démarrage des threads
		airFrance.start();
		americanAirline.start();;
		bob.start();
		alice.start();
	}

}
