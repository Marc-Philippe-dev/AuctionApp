package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSend extends Thread {
	private Socket sc;

	public ClientSend(Socket sc) {
		super();
		this.sc = sc;
	}

	@Override
	public void run() {

		try {
			BufferedReader inKeyboard = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter outToServer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sc.getOutputStream())),
					true);
			String input;
			while (true) {
				
				input = inKeyboard.readLine();
				outToServer.println(input);

			}
		} 
		catch (Exception e) {
			// TODO: handle exception
		}
	}

}
