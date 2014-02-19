import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {

	static Robot robo = null;
	static int left = InputEvent.BUTTON1_MASK;
	static int right = InputEvent.BUTTON3_MASK;
	public static void main(String[] args) throws IOException, AWTException {
		try {
			DatagramSocket serverSocket = new DatagramSocket(9876);

			robo = new Robot();
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
				String data = new String(receivePacket.getData(), 0, 
                        receivePacket.getLength(), "UTF-8");
				if (data.equals("left")) {
					click(cx, cy, left);
				} else if (data.equals("right")) {
					click(cx, cy, right);
					
				} else {
					String[] Coords = data.split(",");
					if(Coords[0].equals("scrl")) {
						int dist_y = Integer.parseInt(Coords[1]);
						robo.mouseWheel(dist_y);
					} else {
						int x = Integer.parseInt(Coords[0]);
						int y = (int) Float.parseFloat(Coords[1]);
						cx += x;
						cy += y;
						robo.mouseMove(cx, cy);
					}
					
				}

			}

		} catch (SocketException ex) {
			System.out.println("Port kullanımda");
			System.exit(1);
		}
	}

	public static void click(int x, int y, int button) {
		robo.mouseMove(x, y);
		robo.mousePress(button);
		robo.mouseRelease(button);
	}
}