
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server1 extends Thread {

	private ServerSocket serverSocket;
	private Map<String, String> users = new HashMap<>();

	private final Map<String, Topic> topics;
	private final TimeHandler timeHandler;
	private final Server2 pubSubServer;

	public Server1(Map<String, Topic> topics, TimeHandler timeHandler, Server2 pubSubServer) {
		this.topics = topics;
		this.timeHandler = timeHandler;
		this.pubSubServer = pubSubServer;
	}

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(2021);
			System.out.println("CS: started");

			while (true) {
				Socket socket = serverSocket.accept();
				ClientHandler1 clientHandler = new ClientHandler1(socket, this);
				System.out.println("CS: created new client handler");
				clientHandler.start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, String> getUsers() {
		return users;
	}

	public Map<String, Topic> getTopics() {
		return topics;
	}

	public TimeHandler getTimeHandler() {
		return timeHandler;
	}

	public void notifyBid(Topic topic) {
		pubSubServer.notifySubscribers(topic, topic.getSym() + " BID " + topic.getValue());
	}
}
