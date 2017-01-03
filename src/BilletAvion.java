import java.util.Date;

public class BilletAvion {
	
	private Commons.Destination depart;
	private Commons.Destination arrivee;
	private int prix;
	private int prixMin;
	private Date date;
	
	public BilletAvion(Commons.Destination dep, Commons.Destination arr, int p, int pMin, Date d) {
		this.depart = dep;
		this.arrivee = arr;
		this.prix = p;
		this.prixMin = pMin;
		this.date = d;
	}

	public Commons.Destination getDepart() {
		return depart;
	}

	public void setDepart(Commons.Destination depart) {
		this.depart = depart;
	}

	public Commons.Destination getArrivee() {
		return arrivee;
	}

	public void setArrivee(Commons.Destination arrivee) {
		this.arrivee = arrivee;
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}

	public int getPrixMin() {
		return prixMin;
	}

	public void setPrixMin(int prixMin) {
		this.prixMin = prixMin;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
