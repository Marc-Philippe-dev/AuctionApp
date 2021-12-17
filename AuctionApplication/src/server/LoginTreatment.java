package server;

import java.io.*;
 
import java.net.*;

 

public class LoginTreatment extends Thread {
	private Socket s;

	public LoginTreatment(Socket s) {
		this.s = s;
	}

	@Override
	public void run() {
		try {
			InputStreamReader in = new InputStreamReader(s.getInputStream());
			BufferedReader in_sc = new BufferedReader(in);
			OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream());
			PrintWriter out_sc = new PrintWriter(new BufferedWriter(out), true);

			while (true) {
				String m = in_sc.readLine();

				if (m.equals("LOGIN")) {

					MemberTreatment t = new MemberTreatment(s);
					t.start();
					break;

				}

				else if (m.equals("ADMIN")) {

					if (ServerProccess.adminConnected == false) {
						ServerProccess.adminConnected = true;
						out_sc.println("Welcome dear Admin");
						AdminTreatment t = new AdminTreatment(s);
						t.start();
						break;
					} else {
						out_sc.println("Admin already connected");
					}
				}

				else {
					out_sc.println("Tape LOGIN or ADMIN first");
				}
			}
		}

		catch (Exception e) {
			// TODO: handle exception
		}
	}
}
