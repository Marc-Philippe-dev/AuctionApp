package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReceive extends Thread{
	
	private Socket sc;

	public ClientReceive(Socket sc) {
		super();
		this.sc = sc;
	}
	
	
	@Override
	public void run() {
		try {
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			 
			while(true) {
			 
				System.out.println(inFromServer.readLine());
			}
		} catch (Exception e) {
		  
		}
	}
}
