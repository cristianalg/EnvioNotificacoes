package ipt.ipg.envionotificacoes;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    EditText editTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        editTitle =(EditText) findViewById(R.id.editTitle);
        final Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.O)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View v) {

                //VARIAVEIS
                String notificationMessage  = editText.getText().toString();
                String notificationTitle  = editTitle.getText().toString();
                int NOTIFICATION_ID = 234;
                String CHANNEL_ID = "my_channel_01";
                Context ctx = getApplicationContext();
                NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

                //VERIFICAR AS CAIXAS DE TEXTO
                if(notificationTitle .isEmpty())
                    notificationTitle  = "Notificação sem titulo";

                if(notificationMessage .isEmpty())
                    notificationMessage  = "Notificação sem conteúdo";


                //CRIAR O CANAL DE COMUNICAÇÃO
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                    CharSequence name = "my_channel";
                    String Description = "This is my channel";
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                    mChannel.setDescription(Description);
                    mChannel.enableVibration(true);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    notificationManager.createNotificationChannel(mChannel);
                }

                //Cria a notificação
                NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(notificationTitle)
                        .setContentText(notificationMessage);

                //Cria a INTENT a ser enviada após o clique na notificação
                Intent resultIntent = new Intent(ctx, MainActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(resultPendingIntent);

                //Submete a notificação
                notificationManager.notify(NOTIFICATION_ID, builder.build());

                //Limpar as caixas de texto
                editTitle.getText().clear();
                editText.getText().clear();
            }
        });
    }
}

