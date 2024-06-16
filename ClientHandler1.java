import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler1 extends Thread {

	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private Server1 server;
	private String user;

	public ClientHandler1(Socket socket, Server1 server) {
		this.clientSocket = socket;
		this.server = server;
	}

	public void run() {
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.startsWith("LOGIN ")) { // login
					String[] split = inputLine.split(" ");
					String user = split[1];
					String password = split[2];
					if (server.getUsers().containsKey(user)) {
						out.println("ERROR: user already exists");
						close();
					} else {
						server.getUsers().put(user, password);
						out.println("User '" + user + "' logged-in");
						this.user = user;
						System.out.println("CS: user " + user + " logged-in");
					}
				} else {
					if (user == null) {
						out.println("-1");
						close();
					}
					
					String[] split = inputLine.split(" ");
					
					if(split.length == 1) { 
						String sym = split[0];
						Topic topic = server.getTopics().get(sym);
						
						if (topic == null) {
							out.println("-1");
						} else {
							out.println("Value :" +topic.getValue());
						}
						
					} else if (split.length == 2) { // do bid
						String sym = split[0];
						String bid = split[1];
						
						Topic topic = server.getTopics().get(sym);
						
						if (topic == null) {
							out.println("-1 : Unsuccesfull BID");
							
						} else {
							TimeHandler timeHandler = server.getTimeHandler();
							
							if(timeHandler.isTimeout()) {
								out.println("-1");
							} else {
								
								int bidNum = Integer.parseInt(bid);
								
								if(bidNum > topic.getValue()) {
									topic.setValue(bidNum);
									out.println("0 Succsesfull Bid");
									
									// check and increment timer, if less than a minute
									if(timeHandler.getRemainingTime() < 60 * 1000) {
										timeHandler.incrementTime(60 * 1000);
									}
									
									server.notifyBid(topic);
									
								} else {
									out.println("-1");
								}
							}
						}
					} else {
						out.println("-1");
					}
				}
			}
			close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void close() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
	}

}
