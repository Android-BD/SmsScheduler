package com.mrhungonline.smsschedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.telephony.gsm.SmsManager;

public class RepeatCheckSendSMS extends Timer {
	public long repeatTime = 1000;
	public long delayTime = 0;
	public static int step = 0;
	public static int countDown = -1;
	// private static long END_TIME = 30000;
	public static boolean isActive = false;
	public static boolean isActiveTimer = false;
	
	private TimerTask task = null;
	// GET CURRENT TIME
	final long crt = new Date().getTime();
	Context context = null;

	public RepeatCheckSendSMS(final Context context) {
		isActiveTimer = true;
		this.context = context;
		// get time repeat in here
		task = new TimerTask() {

			@Override
			public void run() {
				if (isActive) {
					if (SendSMSActivity.countInt > 0) {
						appendString("." + countDown);
						try {
							if (countDown == 0) {
								// start send sms
								sendsms();
								// countin --
								SendSMSActivity.countInt--;
								step = 0;
							}
							if (step == 0) {
								// generate random
								SendSMSActivity.random = new Random()
										.nextInt(SendSMSActivity.randomSecondeInt);
								// correct random value
								try {
									if (SendSMSActivity.minRandom.length() > 0) {
										int temp = Integer
												.parseInt(SendSMSActivity.minRandom);
										if (SendSMSActivity.random < temp) {
											SendSMSActivity.random += temp;
											if (SendSMSActivity.random > SendSMSActivity.randomSecondeInt) {
												SendSMSActivity.random = SendSMSActivity.randomSecondeInt;
											}
										}

									}

								} catch (Exception e) {
								}
								//reset countdown time to other value
								countDown = SendSMSActivity.random;
								step++;
							} else if (step == 1) {

							}
						} catch (Exception e) {
						}
						// count down
						countDown--;

					} else {
						// stop working
						isActive = false;
						appendString("\noh yeah finish!");
						appendString(SendSMSActivity.ACTIVE_SEND_BUTTON);
					}
				}
				System.out.println("--> working every 1 seconds");
			}
		};
	}

	@Override
	public void schedule(TimerTask task, long delay, long period) {
		if (delay == -1 | period == -1) {
			super.schedule(this.task, delayTime, repeatTime);
		} else {
			super.schedule(this.task, delay, period);
		}
	}

	private void sendsms() {
		appendString("\n" + SendSMSActivity.countInt + "->send: "
				+ SendSMSActivity.sms + " to " + SendSMSActivity.phoneNo
				+ " at " + new SimpleDateFormat("HH:mm:ss").format(new Date())
				+ "\n");
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(SendSMSActivity.phoneNo, null,
				SendSMSActivity.sms, null, null);

	}

	private void appendString(String string) {
		try {
			Intent intent = new Intent(SendSMSActivity.SEND_BOCASD);
			intent.putExtra(SendSMSActivity.MESSAGE, string);
			context.sendBroadcast(intent);
		} catch (Exception e) {
			System.out.println("error when send bocast -> "+ string);
		}

	};
}
