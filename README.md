# Envio de Notificações
###### Criação de uma aplicação para envio de notificações 
Nesta secção apresenta-se a criação de uma aplicação Android que envia um texto escrito pelo utilizador como uma notificação para um dispositivo wearable. 

1. Primeiramente criamos um projeto no Android Studio. O novo projeto deverá ter duas aplicações, uma para o smartphone e outra para o wear. 
2. No módulo mobile adicione ao layout dois objetos de EditText, para o utilizador inserir o título e outro para conteúdo da notificação, e um objeto Button, que fica responsável pela criação da notificação.
3. Adicionar <b>Metadata</b> para conseguir trabalhar com os serviços da google.
```
<application>
. . .
	<meta-data
		android:name="com.google.android.gms.version”
		android:value="@integer/google_play_services_version" 
  />
</application>
```
4. No módulo <b>mobile</b>, implementar na main activity o seguinte código.
```
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
```

5. Para testar o código do passo anterior, é necessário emparelhar os dispositivos (smartphone e o smartwatch). Para emparelhar siga os passos da secção de “Emparelhamento dos dispositivos”. 
Como os dispositivos emparelhados, execute a aplicação no smartphone. Insira o texto para o título e conteúdo da notificação e clique no botão <b>Notificar</b>. 
<p>A notificação é enviada para ambos os dispositivos:
<p>
•	<b>No smartphone</b> – a notificação surge na área de notificações do equipamento.
<p>
•	<b>No smartwatch</b> – a notificação surge na parte inferior do ecrã.
<p>
<img src="http://i67.tinypic.com/2nsti6w.jpg" alt="Smiley face" height="450" width="250"><p>
<img src="http://i63.tinypic.com/e6epeu.png" alt="Smiley face" height="200" width="200">
