import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ClientHandler2 extends Thread {

	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private Server2 server;
	private String user;

	public ClientHandler2(Socket socket, Server2 server) {
		this.clientSocket = socket;
		this.server = server;
	}

	public void run() {
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String inputline;
			out.println();
			out.println("Wellcome To Real Estate Auction");
			out.println();
			out.println("Please Login..!");

			if((inputline = in.readLine()) != null){
				String [] split = inputline.split(" ");
				String user = split[0];
				String password = split[1];
				if (server.getUsers().containsKey(user)){
					out.println("ERROR: user already exists");
					close();
				}else{
					server.getUsers().put(user, password);
					out.println(user + "' logged-in");
					this.user = user;
					System.out.println("PS: user " + user + " logged in");
				}
			}
			out.println();
			String inputLine2;
			out.println("Enter Publisher subscriber part here ....!");
			out.println();
			out.println("Please if you wont to published or subscribe infomation start with SUB or PUB");
			out.println();

			
			while ((inputLine2 = in.readLine()) != null){
				if (inputLine2.startsWith("PUB ")){
					if (user == null){
						out.println("-1");
						close();
					}
					String[] split = inputLine2.split(" ");
					String sym = split[1];
					String info = split[2];

					Topic topic = server.getTopics().get(sym);
					if (topic == null){
						out.println("-1");
					}
					else{
						topic.setInfo(info);
						out.println("0");
						server.notifySubscribers(topic, topic.getSym() + " " + topic.getInfo());
						System.out.println(" Published info of " + sym);
					}
				}else if ((inputLine2.startsWith("SUB "))){
					if (user == null){
						out.println("-1");
						close();
					}
					String[] split = inputLine2.split(" ");
					for (int i = 1; i < split.length; i++){
						String sym = split[i];
						Set<ClientHandler2> list = server.getSubscribers().get(sym);

						if (list == null){
							list = new HashSet<>();
							server.getSubscribers().put(sym, list);
						}
						list.add(this);
						System.out.println("Subscribed info of " + sym);
					}

				}
				out.println("0");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String msg) {
		out.println(msg);
	}

	private void close() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
	}

}
