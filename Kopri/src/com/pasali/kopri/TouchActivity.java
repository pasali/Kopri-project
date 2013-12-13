package com.pasali.kopri;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.UnknownHostException;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.Menu;
import android.view.MotionEvent;

import android.widget.Button;

public class TouchActivity extends Activity implements View.OnClickListener {

	private int pre_x, pre_y;
	private String distance_x, distance_y;
	private String click = "";

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class ClientThread implements Runnable {

		@Override
		public void run() {
			String pkg;
			try {
				if (click.equals("")) {
					pkg = distance_x + "," + distance_y;
				}else {
					pkg = click;
				}
				byte[] Data = new byte[1024];
				Data = pkg.getBytes("UTF-8");
				DatagramPacket sendPacket = new DatagramPacket(Data,
						Data.length, MainActivity.serverAddr, 9876);

				MainActivity.s.send(sendPacket);
			} catch (UnknownHostException e1) {
				System.err.println("Bilinmeyen Sunucu");
			} catch (IOException e1) {
				System.err.println("Bağlantı Kurulamadı.");
			}

		}

	}

}
