package com.mrhungonline.sendsmsschedule;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendSMSActivity extends Activity {
	public static String SEND_BOCASD = "sendbocast";
	public static String MESSAGE = "message";
	public static String ACTIVE_SEND_BUTTON = "@*#active send button#*@";
	Button buttonSend, buttonHuy;
	EditText textPhoneNo, textCount, textRandomSeconds, textMinRandom;
	TextView textViewResult;
	EditText textSMS;
	Intent serviceIntent;
	public static int countInt = 0;
	public static int random = 0;
	public static int randomSecondeInt = 0;
	public static String phoneNo = "";
	public static String sms = "";
	public static String minRandom = "";
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(MESSAGE);
			// Waking up mobile if it is sleeping
			if (!newMessage.equals(ACTIVE_SEND_BUTTON)) {
				textViewResult.append(newMessage);
			} else {
				setComponentEnable(true);
			}

		}
	};

	protected void onDestroy() {
		try {
			unregisterReceiver(mHandleMessageReceiver);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		buttonHuy = (Button) findViewById(R.id.buttonHuy);
		buttonHuy.setEnabled(false);
		buttonSend = (Button) findViewById(R.id.buttonSend);
		textPhoneNo = (EditText) findViewById(R.id.editTextPhoneNo);
		textMinRandom = (EditText) findViewById(R.id.editTextMinRandom);
		textSMS = (EditText) findViewById(R.id.editTextSMS);
		textCount = (EditText) findViewById(R.id.editTextSolan);
		textViewResult = (TextView) findViewById(R.id.textViewResult);
		textViewResult.setMovementMethod(new ScrollingMovementMethod());
		textRandomSeconds = (EditText) findViewById(R.id.editTextThoiGian);
		registerReceiver(mHandleMessageReceiver, new IntentFilter(SEND_BOCASD));
		if(!RepeatCheckSendSMS.isActiveTimer){
			new RepeatCheckSendSMS(getApplicationContext()).schedule(null, -1, -1);
		}
		buttonHuy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				countInt = 0;
				RepeatCheckSendSMS.step = 0;
				RepeatCheckSendSMS.countDown = -1;
			}
		});

		buttonSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				phoneNo = textPhoneNo.getText().toString();
				sms = textSMS.getText().toString();
				minRandom = textMinRandom.getText().toString();
				try {
					appendString("\n-----------------------------------------------------\n");
					countInt = Integer.parseInt(textCount.getText().toString());
					randomSecondeInt = Integer.parseInt(textRandomSeconds
							.getText().toString());
					RepeatCheckSendSMS.isActive = true;
					setComponentEnable(false);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"SMS faild, please try again later!",
							Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}

		});

	}

	PowerManager.WakeLock wakeLock;

	public void acquirewakeLock() {

		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		// wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK,
		wakeLock = pm
				.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My wakelook");
		wakeLock.acquire();
		Toast acquire = Toast.makeText(getApplicationContext(), "Wake Lock ON",
				Toast.LENGTH_SHORT);
		acquire.show();

	}

	public void releaseWakelock() {
		wakeLock.release();
		Toast release = Toast.makeText(getApplicationContext(),
				"Wake Lock OFF", Toast.LENGTH_SHORT);
		release.show();

	}

	private void setComponentEnable(boolean b) {
		// kich hoạt wakelock
		if (!b)
			acquirewakeLock();
		else
			releaseWakelock();
		buttonHuy.setEnabled(!b);
		buttonSend.setEnabled(b);
		textPhoneNo.setEnabled(b);
		textMinRandom.setEnabled(b);
		textSMS.setEnabled(b);
		textCount.setEnabled(b);
		textRandomSeconds.setEnabled(b);
	}
	private void setCustomTitleFeatureInt(int value) {
	    try {
	    	// retrieve value for com.android.internal.R.id.title_container(=0x1020149)
	    	int titleContainerId = (Integer) Class.forName(
	    		"com.android.internal.R$id").getField("title_container").get(null);

	    	// remove all views from titleContainer
	    	((ViewGroup) getWindow().findViewById(titleContainerId)).removeAllViews();

	    	// add new custom title view 
	    	getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, value);

	    } catch(Exception ex) {
	    	// whatever you want to do here..
	    }
	}
	final Activity activity = this;

	private void appendString(final String string) {
		runOnUiThread(new Runnable() {
			public void run() {
				textViewResult.append(string);
			}
		});

	};
}