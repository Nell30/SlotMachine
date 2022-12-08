package ca.on.conestogac.slo.slot_machine;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        final Toast toast = Toast.makeText(this,
                "Service timer loop encountered", Toast.LENGTH_SHORT);
        final Timer timer = new Timer(true);
        final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        final Intent intent = new Intent(getApplicationContext(), MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        final PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationService.this, "My Notification");
        builder.setContentTitle("You have some money left");
        builder.setContentText("Come back and play");
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(NotificationService.this);


        //counter = 0;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
                //manager.notify(1, notification);
                managerCompat.notify(1, builder.build());
                timer.cancel();
                stopSelf();

            }
        }, 1000, 5000);


        //Toast.makeText(this, "Service onCreate called", Toast.LENGTH_SHORT).show();

        super.onCreate();

    }

    @Override
    public void onDestroy() {
        //Toast.makeText(this, "Service onDestroy called", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}