package client;

import java.net.Socket;

public class ClientProcess {

	public static void main(String[] args) throws Exception {

		Socket sc = new Socket("127.0.0.1", 4000);
		// System.out.println(s.getInetAddress()+"/"+s.getPort());

		System.out.println("Tape LOGIN or ADMIN ");

		ClientReceive cr = new ClientReceive(sc);
		ClientSend cs = new ClientSend(sc);

		cr.start();
		cs.start();
	}

}
