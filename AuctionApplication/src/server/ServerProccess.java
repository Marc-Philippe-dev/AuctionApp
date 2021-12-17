package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import model.Auction;
import model.Member;
import model.Offer;

public class ServerProccess {

	static List<Auction> auctionList = new ArrayList<>();
	static List<Member> memberList = new ArrayList<>();
	static List<Offer> offerList = new ArrayList<>();
	static boolean adminConnected = false;

	public static void main(String[] args) throws IOException {

		ServerSocket sc = new ServerSocket(4000);
		while (true) {
			Socket s = sc.accept();
			LoginTreatment t = new LoginTreatment(s);
			t.start();
		}
	}

	public static Member checkMember(String name) {
		for (Member member : memberList) {
			if (member.getName().equals(name)) {
				return member;
			}
		}
		return null;
	}

	public static Auction findActivAuction(int id) {

		for (Auction auction : ServerProccess.auctionList) {

			if (auction.getId() == id && auction.getAuctionState() == 1) {

				return auction;
			}
		}
		return null;
	}

	public static float findTheHighestOffer(int id) {
		float max = 0;
		for (Offer offer : ServerProccess.offerList) {

			if (offer.getCurrentAuction().getId() == id) {
				if (offer.getBidPrice() > max) {
					max = offer.getBidPrice();
				}
			}

		}
		return max;
	}
	
	
	public static boolean isThisAuctionMember(int id , String name) {
		List<Member> members ;
		boolean isOk = false;
		
		for (Auction auction : auctionList) {
			if(auction.getId() == id){
				members = auction.getMembers();
				
				for(Member member: members) {
					if(member.getName().equals(name)) {
						isOk = true;
						break;
					}
				}
			}
			break;
		}
		
		return isOk;
	}
}
