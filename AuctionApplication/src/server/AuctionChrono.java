package server;

import model.Auction;

public class AuctionChrono extends Thread {
	
    private Auction auction;

    public  AuctionChrono(Auction auction){
        this.auction = auction;
    }

    @Override
    public void run() {
        try {
            sleep(auction.getEndHour()*(long)120000);
        }catch (Exception e){

        }
        this.auction.close();
}
    
}
