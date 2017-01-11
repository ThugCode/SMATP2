import java.io.Serializable;
import java.util.Date;

public class BilletAvion implements Serializable {

	private static final long serialVersionUID = 1L;
	private Commons.Destination depart;
	private Commons.Destination arrivee;
	private int prix;
	private Date date;
	
	public BilletAvion(Commons.Destination depart, Commons.Destination arrivee) {
		this.depart = depart;
		this.arrivee = arrivee;
	}
	
	public BilletAvion(Commons.Destination dep, Commons.Destination arr, int p) {
		this.depart = dep;
		this.arrivee = arr;
		this.prix = p;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof BilletAvion)) return false;
		
		if(((BilletAvion)obj).getDepart() == this.getDepart() 
		&& ((BilletAvion)obj).getArrivee() == this.getArrivee())
			return true;
		
		return false;
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
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
