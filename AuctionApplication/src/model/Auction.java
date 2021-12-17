package model;

import java.util.ArrayList;
import java.util.List;

public class Auction {
	private int id;
	private String description;
	private float startPrice;
	private int endHour;
	private int auctionState;
	public List<Offer> offersList =  new ArrayList<>();
	private List<Member> members = new ArrayList<>();
	private static int num = 0;

	public Auction(String description, float startPrice, int endHour, int auctionState) {

		this.id = num++;
		this.description = description;
		this.startPrice = startPrice;
		this.endHour = endHour;
		this.auctionState = auctionState;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(float startPrice) {
		this.startPrice = startPrice;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int startHour) {
		this.endHour = startHour;
	}

	public int getAuctionState() {
		return auctionState;
	}

	public void setAuctionState(int auctionState) {
		this.auctionState = auctionState;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(Member member) {
		this.members.add(member);
	}

	public void close() {
		
		auctionState = 2;
		if(this.offersList.size()>0){
            this.offersList.get(this.offersList.size()-1).setWinner(true);
        }
	}
	
	public float findbestOffer() {
        if(offersList.size()>0){
            return offersList.get(offersList.size()-1).getBidPrice();
        }
        return startPrice;
    }
	
}
