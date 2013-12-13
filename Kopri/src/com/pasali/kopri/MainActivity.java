package com.pasali.kopri;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity implements View.OnClickListener {
	
	private EditText ip;
	private String ip_addr;
	public static DatagramSocket s;
	public static InetAddress serverAddr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button Button1 = (Button) findViewById(R.id.button1);
		Button1.setOnClickListener(this);
		ip = (EditText) findViewById(R.id.editText1);

	}


	public void onClick(View v) {
		ip_addr = ip.getText().toString();
		new Thread(new ClientThread()).start();
	}


	class ClientThread implements Runnable {

		@Override
		public void run() {
			try {
				s = new DatagramSocket();
				serverAddr = InetAddress.getByName(ip_addr);
				Intent touchIntent = new Intent(getApplicationContext(),
						TouchActivity.class);
				startActivity(touchIntent);
			} catch (UnknownHostException e1) {
				System.err.println("Bilinmeyen Sunucu");
			} catch (IOException e1) {
				System.err.println("Bağlantı Kurulamadı.");
			}

		}

	}

}
