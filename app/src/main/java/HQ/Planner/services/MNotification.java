package HQ.Planner.services;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import HQ.Planner.R;

public class MNotification {


    Context context;
    String CHANNEL_ID;

    public MNotification(Context context, String CHANNEL_ID) {
        this.context = context;
        this.CHANNEL_ID = CHANNEL_ID;
    }

    public void notifyMyNotification(int notificationId){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("My notification")
                .setContentText("Much longer text that cannot fit one line...")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
    }

    public void notify2(){ Intent intentDismiss = new Intent(context, MyReceivers.class);
        Intent intentCancel = new Intent(context, MyReceivers.class);
        Intent intentReminder = new Intent(context, MyReceivers.class);


//        intentDismiss.putExtra("action", "Dismiss" + event.get(i).getId());
//        intentCancel.putExtra("action", "Cancel" + event.get(i).getId());
//        intentReminder.putExtra("action", "Reminder" + event.get(i).getId());

        PendingIntent pIntentDismiss = PendingIntent.getBroadcast(context, 0 + 44, intentDismiss, PendingIntent.FLAG_ONE_SHOT);
        PendingIntent pIntentCancel = PendingIntent.getBroadcast(context, 1 + 99, intentCancel, PendingIntent.FLAG_ONE_SHOT);
        PendingIntent pIntentReminder = PendingIntent.getBroadcast(context, 2 + 188, intentReminder, PendingIntent.FLAG_ONE_SHOT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Event Start Remind")
                .setContentText("baahahahha" + "\n" + "Start at:" )
                .addAction(R.drawable.ic_launcher_foreground, "Dismiss", pIntentDismiss)
                .addAction(R.drawable.ic_launcher_foreground, "Cancel", pIntentCancel)
                .addAction(R.drawable.ic_launcher_foreground, "Reminder", pIntentReminder)
        ;
        notificationManager.notify(111, builder.build());
    }



//    public static void showNoti(Context context){
//
//    }
//
//    public static void setNotiAction(Context context,String CHANNEL_ID,int notificationId){
//        Intent intent = new Intent(context, AlertDetails.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
//                .setSmallIcon(R.drawable.notification_icon)
//                .setContentTitle("My notification")
//                .setContentText("Hello World!")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                // Set the intent that will fire when the user taps the notification
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);
//
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//
//// notificationId is a unique int for each notification that you must define
//        notificationManager.notify(notificationId, builder.build());
//    }
//
//
//    public static void createNotificationChannel(Context context,String CHANNEL_ID) {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = context.getString(R.string.channel_name);
//            String description = context.getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
//
//    public static void noti(Context context,String CHANNEL_ID){
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
//                .setSmallIcon(R.drawable.notification_icon)
//                .setContentTitle("My notification")
//                .setContentText("Much longer text that cannot fit one line...")
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText("Much longer text that cannot fit one line..."))
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//    }
}
