package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import model.Auction;
import model.Member;
import model.Offer;

public class MemberTreatment extends Thread {

	private Socket sc;
	private Member member;
	private List<Offer> yourOffers = new ArrayList<>();

	public MemberTreatment(Socket sc) {
		this.sc = sc;
	}

	@Override
	public void run() {

		try {
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			PrintWriter outToClient = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sc.getOutputStream())),
					true);

			while (true) {
				outToClient.println("Please enter a name for registration");
				String name = inFromClient.readLine();

				if (ServerProccess.checkMember(name) == null) {

					this.member = new Member(name);
					ServerProccess.memberList.add(member);
					outToClient.println("Account successfully created with id: " + member.getId());
					break;

				} else {
					outToClient.println("Name " + name + " already in use");
				}
			}

			while (true) {

				outToClient.println("========== Member Menu ==========");
				outToClient.println("A-ENCHERES");
				outToClient.println("B-JOIN");
				outToClient.println("C-OFFRE");
				outToClient.println("D-LIST");
				outToClient.println("E-RUNNING AUCTIONS");
				outToClient.println("F-ENDED AUCTIONS");
				outToClient.println("G-YOUR OFFERS");
				outToClient.println("H-YOUR WON OFFERS");

				String clientMes = inFromClient.readLine();

				if (clientMes.startsWith("A")) {

					if (ServerProccess.auctionList.size() != 0) {

						for (Auction auction : ServerProccess.auctionList) {

							outToClient.println(auction.getId() + "//" + auction.getDescription() + "//"
									+ auction.getStartPrice() + "//" + auction.getEndHour());

						}

					} else {
						outToClient.println("List of auction is empty");
					}
				}

				else if (clientMes.startsWith("B")) {

					outToClient.println("Please enter the auction id:");

					try {

						int id = Integer.parseInt(inFromClient.readLine());
						if (ServerProccess.auctionList.size() != 0) {

							Auction auction = ServerProccess.findActivAuction(id);

							if (auction == null) {

								outToClient.println("This auction is not actived.");
							}

							else {
								auction.setMembers(this.member);

								outToClient.println("You've been successfully added.");
							}
						} else {
							outToClient.println("There is no auction, list is empty");
						}

					} catch (Exception e) {
						// TODO: handle exception
						outToClient.println("Please enter a number");
					}

				}

				else if (clientMes.startsWith("C")) {

					outToClient.println("Please enter ProdId and Price like this: idProd##Price (Example:12##2000)");

					clientMes = inFromClient.readLine();

					long count = clientMes.chars().filter(ch -> ch == '#').count();

					if (count == 2) {
						String t[] = clientMes.split("##");
						int id = Integer.parseInt(t[0]);

						if (ServerProccess.isThisAuctionMember(id, member.getName())) {

							float price = Float.parseFloat(t[1]);

							Auction auction = ServerProccess.findActivAuction(id);

							Offer offer = new Offer(member, auction, price);
							if (ServerProccess.offerList.size() == 0) {

								if (auction != null) {
									if (auction.getStartPrice() < price) {

										ServerProccess.offerList.add(offer);
										auction.offersList.add(offer);
										yourOffers.add(offer);

										auction.setStartPrice(price);
										outToClient.println("First client. Accepted");
									}

									else {
										outToClient.println("Your offer has been rejected");
									}
								} else {
									outToClient
											.println("You can't propose an offer because the asuction is not actived.");
								}
							} else {
								float max = ServerProccess.findTheHighestOffer(id);

								if (auction != null) {

									if (price > max) {
										ServerProccess.offerList.add(offer);
										auction.offersList.add(offer);
										yourOffers.add(offer);
										auction.setStartPrice(price);
										outToClient.println("Your offer has been accepted");
									} else {
										outToClient.println("Your offer has been rejected");
									}

								} else {
									outToClient
											.println("You can't propose an offer because the asuction is not actived.");
								}

							}
						} else {
							outToClient.println("You must join before being able to do any offer");
						}

					} else {
						outToClient.println("Wrong syntax");
					}

				}

				else if (clientMes.startsWith("D")) {
					outToClient.println("Please enter the auction id:");
					clientMes = inFromClient.readLine();
					int id = Integer.parseInt(clientMes);
					boolean isOk = false;
					for (Offer offer : ServerProccess.offerList) {
						if (offer.getCurrentAuction().getId() == id) {
							isOk = true;
							outToClient.println(offer.getBidder().getName() + "//" + offer.getCurrentAuction().getId()
									+ "//" + offer.getBidPrice());
						}
					}

					if (isOk == false) {
						outToClient.println("There is no offer for this auction.");
					}
				}

				else if (clientMes.startsWith("E")) {
					boolean isRunning = false;
					for (Auction auction : ServerProccess.auctionList) {
						if (auction.getAuctionState() == 1) {
							isRunning = true;
							outToClient.println(auction.getId() + "#" + auction.getDescription() + "#"
									+ auction.getStartPrice() + "#" + auction.getEndHour());
						}
					}

					if (!isRunning) {
						outToClient.println("There is no auction running currently");
					}
				}

				else if (clientMes.startsWith("F")) {
					boolean isEnded = false;
					for (Auction auction : ServerProccess.auctionList) {
						if (auction.getAuctionState() == 2) {
							isEnded = true;
							outToClient.println(auction.getId() + "#" + auction.getDescription() + "#"
									+ auction.getStartPrice() + "#" + auction.getEndHour());
						}
					}

					if (!isEnded) {
						outToClient.println("There is no ended auction or they not even started");
					}
				} else if (clientMes.startsWith("G")) {
					if (yourOffers.size() != 0) {

						for (Offer offer : yourOffers) {
							outToClient.println(offer.getBidder().getName() + "//" + offer.getCurrentAuction().getId()
									+ "//" + offer.getBidPrice());
						}
					} else {
						outToClient.println("You didn't make any offer");
					}
				}

				else if (clientMes.startsWith("H")) {

					for (Offer offer : yourOffers) {
						if (offer.isWinner()) {
							outToClient.println(offer.getBidder().getName() + "//" + offer.getCurrentAuction().getId()
									+ "//" + offer.getBidPrice());
						}
					}

				}

			}

		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
