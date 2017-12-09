package jp.gr.java_conf.hs.roomba.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class Roomba {
	private static final int WAIT_TIME = 1000;
	private Socket socket;
	private DataOutputStream os = null;
	private Log log = LogFactory.getLog(Roomba.class);

	public boolean connect(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			os = new DataOutputStream(socket.getOutputStream());
			log.info("Connected to Roomba");
			return true;
		} catch (IOException e) {
			log.error("Failed to connect to Roomba", e);
			return false;
		}
	}

	public boolean disconnect() {
		try {
			socket.close();
			os.close();
			log.info("Disconnected from Roomba");
			return true;
		} catch (IOException e) {
			log.error("Failed to disconnect from Roomba", e);
			return false;
		}
	}

	public boolean send(String seq) {
		boolean result = true;
		
		if (socket != null && os != null) {
			log.info("Sending serial sequence [" + seq + "]");
			// split sequences by space and add them to a byte list
			List<Byte> byteList = new ArrayList<Byte>();
			Arrays.asList(seq.split(" ")).forEach(b -> byteList.add((byte) Integer.parseInt(b)));

			// convert a byte list to a byte array
			byte[] byteArray = new byte[byteList.size()];
			for (int index = 0; index < byteList.size(); index++) {
				byteArray[index] = byteList.get(index);
			}
			try {
				// send operations to Roomba
				os.write(byteArray);
				Thread.sleep(WAIT_TIME);
			} catch (IOException | InterruptedException e) {
				log.error("Failed to send command to Roomba", e);
				result = false;
			}
		}else {
			result = false;
		}
		return result;
	}
}
