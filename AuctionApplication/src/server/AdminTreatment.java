package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import model.Auction;

public class AdminTreatment extends Thread {

	private Socket sc;

	public AdminTreatment(Socket sc) {
		this.sc = sc;
	}

	@Override
	public void run() {
		try {
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			PrintWriter outToClient = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sc.getOutputStream())),
					true);
			String clientMes;

			while (true) {

				outToClient.println("Please enter the password");
				clientMes = inFromClient.readLine();
				if (clientMes.startsWith("FSG2021")) {
					break;
				}
			}

			while (true) {

				outToClient.println("========= Admin Menu ===========");
				outToClient.println("A-CREATION");
				outToClient.println("B-LAUNCH");
				outToClient.println("C-ALL AUCTIONS");
				outToClient.println("D-RUNNING AUCTIONS");
				outToClient.println("E-ENDED AUCTIONS");

				clientMes = inFromClient.readLine();

				if (clientMes.startsWith("A")) {
					outToClient.println("Please enter the title, the price and time like this: (title##price##time)");
					clientMes = inFromClient.readLine();
					long count = clientMes.chars().filter(ch -> ch == '#').count();

					if (count == 4) {

						String t[] = clientMes.split("##");

						ServerProccess.auctionList
								.add(new Auction(t[0], Float.parseFloat(t[1]), Integer.parseInt(t[2]), 0));

						outToClient.println("You successfully added an auction");

					} else {
						outToClient.println("Wrong syntax!!!!!");
					}
				}

				if (clientMes.equals("B")) {

					outToClient.println("Enter the auction id you want to start.");
					
					try {
						int id = Integer.parseInt(inFromClient.readLine());
						boolean exist = false;

						if (ServerProccess.auctionList.size() != 0) {

							for (Auction auction : ServerProccess.auctionList) {


								if (auction.getId() == id) {

									exist = true;

									auction.setAuctionState(1);
									outToClient.println("You successfully started the auction with id:" + auction.getId());

									AuctionChrono ac = new AuctionChrono(auction);
									ac.start();
									break;
								}
							}

							if(!exist) {
								outToClient.println("You can't start this auction because it doesn't exist");
							}
						}

						else {
							outToClient.println("Your list is empty so you can't start an auction");
						}
					} catch (Exception e) {
						// TODO: handle exception
						
						outToClient.println("Please enter a number");
					}
				}

				if (clientMes.equals("C")) {
					for (Auction auction : ServerProccess.auctionList) {

						outToClient.println(auction.getId() + "//" + auction.getDescription() + "//"
								+ auction.getStartPrice() + "//" + auction.getEndHour());
					}
				}

				if (clientMes.equals("D")) {
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

				if (clientMes.equals("E")) {

					boolean isEnded = false;
					for (Auction auction : ServerProccess.auctionList) {
						if (auction.getAuctionState() == 2) {
							isEnded = true;
							outToClient.println(auction.getId() + "//" + auction.getDescription() + "//"
									+ auction.getStartPrice() + "//" + auction.getEndHour());
						}
					}

					if (!isEnded) {
						outToClient.println("There is no ended auction or they not even started");
					}
				}

			}
		}

		catch (Exception e) {
			// TODO: handle exception
		}
	}

}
