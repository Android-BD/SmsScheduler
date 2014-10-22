package com.mrhungonline.smsschedule;
/*package com.mkyong.android;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.gsm.SmsManager;

public class SmsService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		sendsms(intent.getExtras().getString("phoneNo"), intent.getExtras()
				.getString("sms"), intent.getExtras()
				.getInt("randomSecondeInt"),
				intent.getExtras().getString("minRandom"));
		
		return super.onStartCommand(intent, flags, startId);

	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	private void sendsms(final String phoneNo, final String sms,
			final int randomSecondeInt, final String minRandom) {
		if (SendSMSActivity.countInt > 0) {
//			SendSMSActivity.random = new Random().nextInt(randomSecondeInt);
//			try {
//				if (minRandom.length() > 0) {
//					int temp = Integer.parseInt(minRandom);
//					if (SendSMSActivity.random < temp) {
//						// if(random>randomSecondeInt/2){
//						// random= randomSecondeInt-random;
//						// }
//						SendSMSActivity.random += temp;
//						if (SendSMSActivity.random > randomSecondeInt) {
//							SendSMSActivity.random = randomSecondeInt;
//						}
//					}
//
//				}
//
//			} catch (Exception e) {
//			}
			new Thread() {
				public void run() {
					appendString("\nstarted at"
							+ new SimpleDateFormat("HH:mm:ss")
									.format(new Date()));
					try {
						appendString("\nsleep " + SendSMSActivity.random
								+ " seconds.");
						Thread.sleep((long) SendSMSActivity.random * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					appendString("\n"
							+ SendSMSActivity.countInt
							+ "->send: "
							+ sms
							+ " to "
							+ phoneNo
							+ " at "
							+ new SimpleDateFormat("HH:mm:ss")
									.format(new Date()));
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(phoneNo, null, sms, null, null);
					SendSMSActivity.countInt--;
					sendsms(phoneNo, sms, randomSecondeInt, minRandom);
				}

			}.start();
		} else {
			appendString("\nfinish");
			onDestroy();
		}
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	private void appendString(String string) {
		Intent intent = new Intent(SendSMSActivity.SEND_BOCASD);
		intent.putExtra(SendSMSActivity.MESSAGE, string);
		SmsService.this.sendBroadcast(intent);
	};

	@Override
	public void onDestroy() {
		try {
			Thread.currentThread().interrupt();
		} catch (Exception e) {
		}
		super.onDestroy();
	}

}
*/