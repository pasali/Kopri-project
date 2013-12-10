package com.pasali.kopri;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends Activity {

	private int pre_x, pre_y;
	private String distance_x, distance_y;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final View touchView = findViewById(R.id.textView1);
		touchView.setBackgroundColor(Color.BLUE);
		touchView.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				final int action = event.getAction();
				switch (action & MotionEvent.ACTION_MASK) {

				case MotionEvent.ACTION_DOWN: {
					pre_x = (int) event.getX();
					pre_y = (int) event.getY();
					break;
				}

				case MotionEvent.ACTION_MOVE: {
					distance_x = String.valueOf((int) event.getX() - pre_x);
					distance_y = String.valueOf((int) event.getY() - pre_y);
					pre_x = (int) event.getX();
					pre_y = (int) event.getY();
					new Thread(new ClientThread()).start();
					break;
				}
				}
				return true;

			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class ClientThread implements Runnable {

		@Override
		public void run() {
			try {
				String pkg = distance_x + "," + distance_y;
				DatagramSocket s = new DatagramSocket();
				InetAddress serverAddr = InetAddress.getByName("192.168.1.41");
				byte[] Data = new byte[1024];
				Data = pkg.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(Data,
						Data.length, serverAddr, 9876);

				s.send(sendPacket);
			} catch (UnknownHostException e1) {
				System.err.println("Bilinmeyen Sunucu");
			} catch (IOException e1) {
				System.err.println("Bağlantı Kurulamadı.");
			}

		}

	}

}
