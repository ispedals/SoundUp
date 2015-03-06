package pedals.is.soundup;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (ScreenOffService.RUNNING) {
			stopService(new Intent(this, ScreenOffService.class));
		} else {
			startService(new Intent(this, ScreenOffService.class));
		}
		finish();
	}

}
