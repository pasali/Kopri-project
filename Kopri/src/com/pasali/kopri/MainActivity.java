package com.pasali.kopri;

import android.os.Bundle;
import android.app.Activity;
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

}
