
public class Main {
	
	public static boolean verbose = true;
	
	public static void main(String[] args) {
		Fournisseur airFrance = new Fournisseur();
		Negociateur bob = new Negociateur(airFrance);
		
		BilletAvion billet = new BilletAvion(Commons.Destination.PARIS,Commons.Destination.LONDRES, 200, 100, 160);
		
		bob.setBillet(billet);
		
		airFrance.addBillet(billet);
		
		airFrance.start();
		bob.start();
	}

}
