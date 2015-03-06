package pedals.is.soundup;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.IBinder;

public class ScreenOffService extends Service {

	private final int ONGOING_NOTIFICATION = 986;
	public static boolean RUNNING = false;

	private final BroadcastReceiver screenOffReciever = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

				AudioManager manager = (AudioManager) context
						.getSystemService(Context.AUDIO_SERVICE);

				manager.setStreamVolume(AudioManager.STREAM_SYSTEM,
						manager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM),
						AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

			}
		}
	};

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		super.onCreate();

		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		registerReceiver(screenOffReciever, filter);

		Notification notification = new Notification(R.drawable.ic_launcher,
				"SoundUp Started", System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(this, "SoundUp is running",
				"Tap to stop", pendingIntent);
		startForeground(ONGOING_NOTIFICATION, notification);
		RUNNING = true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(screenOffReciever);
		RUNNING = false;
	}

}
