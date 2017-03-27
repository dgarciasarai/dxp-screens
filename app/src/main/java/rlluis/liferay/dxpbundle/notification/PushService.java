package rlluis.liferay.dxpbundle.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import rlluis.liferay.dxpbundle.R;
import rlluis.liferay.dxpbundle.activities.MenuActivity;
import com.liferay.mobile.screens.push.AbstractPushService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class PushService extends AbstractPushService {

	public static final int NOTIFICATION_ID = 2;

	public static void createGlobalNotification(String title, String description, Context context) {
		createGlobalNotification(title, description, context, R.id.nav_webat);
	}

	public static void createGlobalNotification(String title, String description, Context context, int fragmentId) {
		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
				.setContentTitle(title)
				.setContentText(description)
				.setAutoCancel(true)
				.setSound(uri)
				.setVibrate(new long[]{2000, 1000, 2000, 1000})
				.setSmallIcon(R.drawable.logo_notificacion);

		builder.setContentIntent(createPendingIntentForNotifications(context, fragmentId));

		Notification notification = builder.build();
		NotificationManager notificationManager =
				(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFICATION_ID, notification);
	}

	private static PendingIntent createPendingIntentForNotifications(Context context, int fragmentId) {
		Intent resultIntent = new Intent(context, MenuActivity.class);
		resultIntent.putExtra("fragmentId", fragmentId);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addNextIntent(resultIntent);
		return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	@Override
	protected void processJSONNotification(final JSONObject json) throws JSONException {
		String title = json.has("title") ? json.getString("title") : "Empty title";
		String body = json.has("body") ? json.getString("body") : "Empty body";

		createGlobalNotification(title, body, this);
	}
}
