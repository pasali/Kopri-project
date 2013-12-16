package com.pasali.kopri;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.MotionEvent;
import android.widget.Button;

public class TouchActivity extends Activity implements View.OnClickListener {

	private int pre_x, pre_y;
	private String distance_x, distance_y;
	private String click = "";
	private DatagramSocket s;
	private InetAddress serverAddr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch);
		Button Button1 = (Button) findViewById(R.id.button1);
		Button1.setOnClickListener(this);
		Button Button2 = (Button) findViewById(R.id.Button2);
		Button2.setOnClickListener(this);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int action = event.getAction();

		switch (action & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN: {
				pre_x = (int) event.getX();
				pre_y = (int) event.getY();
				break;
			}
			case MotionEvent.ACTION_UP: {
				long delay = event.getEventTime() - event.getDownTime();
				if (delay < 200) {
					click = "left";
					new Thread(new ClientThread()).start();
				}
				break;
			}
			case MotionEvent.ACTION_MOVE: {
				distance_x = String.valueOf((int) event.getX() - pre_x);
				distance_y = String.valueOf((int) event.getY() - pre_y);
				pre_x = (int) event.getX();
				pre_y = (int) event.getY();
				click = "";
				new Thread(new ClientThread()).start();
				break;
			}
		}
		return true;
	}

	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.button1:
				click = "left";
				break;
			case R.id.Button2:
				click = "right";
				break;
			default:
				break;
		}
		new Thread(new ClientThread()).start();
	}

	class ClientThread implements Runnable {

		@Override
		public void run() {
			String pkg, ip_addr = null;
			try {
				if (click.equals("")) {
					pkg = distance_x + "," + distance_y;
				} else {
					pkg = click;
				}
				Bundle extras = getIntent().getExtras();
				if (extras != null) {
					ip_addr = extras.getString("ip_addr");
				}
				s = new DatagramSocket();
				serverAddr = InetAddress.getByName(ip_addr);
				byte[] Data = new byte[1024];
				Data = pkg.getBytes("UTF-8");
				DatagramPacket sendPacket = new DatagramPacket(Data,
						Data.length, serverAddr, 9876);
				s.send(sendPacket);
			} catch (IOException e1) {
				System.err.println("Bağlantı Kurulamadı.");
			}

		}

	}

}
