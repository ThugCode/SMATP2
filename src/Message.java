import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Commons.TypeMessage type;
	private BilletAvion billet;
	private int prix;
	
	public Message(Commons.TypeMessage t, BilletAvion b) {
		this.type = t;
		this.billet = b;
	}
	
	public Message(Commons.TypeMessage t, int p) {
		this.type = t;
		this.prix = p;
	}	

	public Commons.TypeMessage getType() {
		return type;
	}

	public void setType(Commons.TypeMessage type) {
		this.type = type;
	}

	public BilletAvion getBillet() {
		return billet;
	}

	public void setBillet(BilletAvion billet) {
		this.billet = billet;
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}
}
