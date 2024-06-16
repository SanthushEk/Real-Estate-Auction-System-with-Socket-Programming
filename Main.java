import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Main {
	
	private static final Map<String, Topic> topics = new HashMap<>();

	public static void main(String[] args) throws IOException {
		readCSV();
		
		TimeHandler timeHandler = new TimeHandler( 10 * 60 * 1000);
		
		// pub-sub server
		Server2 server2 = new Server2(topics);
		server2.start();
		
		// client server
		Server1 server1 = new Server1(topics, timeHandler, server2);
		server1.start();

		new Thread() {
			public void run() {
				while (true) {
					long s = timeHandler.getRemainingTime()/1000;
					String time = String.format("%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
					System.out.println("Remaining time " + time);

					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		
	}
	
	private static void readCSV() throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("data.csv"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(",");
		        Topic t = new Topic();
				t.setSym(values[0]);
				t.setValue(Integer.parseInt(values[1]));
				topics.put(t.getSym(), t);
		    }
		}
	}

}
