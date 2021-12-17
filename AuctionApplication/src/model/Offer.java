package model;

public class Offer {
	private static int idCpt =0;
	private int id;
	private Member bidder;
	private Auction currentAuction;
	private float bidPrice;
	private boolean isWinner = false;
	

	public Offer(Member bidder, Auction currentAuction, float bidPrice) {
		this.id = idCpt++;
		this.bidder = bidder;
		this.currentAuction = currentAuction;
		this.bidPrice = bidPrice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isWinner() {
		return isWinner;
	}
	
	public void setWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}
	public Member getBidder() {
		return bidder;
	}

	public void setBidder(Member bidder) {
		this.bidder = bidder;
	}

	public Auction getCurrentAuction() {
		return currentAuction;
	}

	public void setCurrentAuction(Auction currentAuction) {
		this.currentAuction = currentAuction;
	}

	public float getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(float bidPrice) {
		this.bidPrice = bidPrice;
	}
	
}
