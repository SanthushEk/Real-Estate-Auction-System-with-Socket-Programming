
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Server2 extends Thread {

	private Map<String, Set<ClientHandler2>> subscribers = new HashMap<>();
	private Map<String, String> users = new HashMap<>();

	private ServerSocket serverSocket;
	private final Map<String, Topic> topics;

	public Server2(Map<String, Topic> topics) {
		this.topics = topics;
	}

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(2022);
			System.out.println("PS: started");

			while (true) {
				Socket socket = serverSocket.accept();
				ClientHandler2 clientHandler = new ClientHandler2(socket, this);
				System.out.println("PS: created new client handler");
				clientHandler.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, Topic> getTopics() {
		return topics;
	}

	public Map<String, Set<ClientHandler2>> getSubscribers() {
		return subscribers;
	}

	public Map<String, String> getUsers() {
		return users;
	}

	public void notifySubscribers(Topic topic, String msg) {
		Set<ClientHandler2> clients = getSubscribers().get(topic.getSym());
		if (clients != null) {
			for (ClientHandler2 client : clients) {
				client.sendMessage(msg);
			}
		}
	}
}
