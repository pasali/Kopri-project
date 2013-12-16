package com.pasali.kopri;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener {

	private EditText ip;
	private String ip_addr;

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
		Intent touchIntent = new Intent(getApplicationContext(),
				TouchActivity.class);
		touchIntent.putExtra("ip_addr", ip_addr);
		startActivity(touchIntent);
	}

}
