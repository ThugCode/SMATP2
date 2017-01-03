import java.util.Date;

public class BilletAvion {
	
	public String depart;
	public String arrivee;
	public int prix;
	public Date date;
	
	public BilletAvion(String dep, String arr, int p, Date d) {
		this.depart = dep;
		this.arrivee = arr;
		this.prix = p;
		this.date = d;
	}

}
