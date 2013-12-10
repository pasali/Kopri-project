import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {
	public static void main(String[] args) throws IOException, AWTException {
		try {
			DatagramSocket serverSocket = new DatagramSocket(9876);

			Robot robo = new Robot();
			PointerInfo a = MouseInfo.getPointerInfo();
			Point b = a.getLocation();
			int cx = (int) b.getX();
			int cy = (int) b.getY();
			byte[] receiveData = new byte[1024];
			while (true) {
				receiveData = new byte[1024];

				DatagramPacket receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				serverSocket.receive(receivePacket);
				String data = new String(receivePacket.getData());
				String[] Coords = data.split(",");
				int x = Integer.parseInt(Coords[0]);
				int y = (int) Float.parseFloat(Coords[1]);
				cx += x;
				cy += y;
				robo.mouseMove(cx, cy);
			}

		} catch (SocketException ex) {
			System.out.println("Port kullanımda");
			System.exit(1);
		}
	}
}