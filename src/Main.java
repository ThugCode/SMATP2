
public class Main {
	
	public static boolean verbose = false;
	
	public static void main(String[] args) {
		Fournisseur airFrance = new Fournisseur();
		Negociateur bob = new Negociateur(airFrance);
		
		BilletAvion billet = new BilletAvion(Commons.Destination.PARIS,Commons.Destination.LONDRES);
		
		bob.setBillet(billet);
		
		airFrance.start();
		bob.start();
	}

}
