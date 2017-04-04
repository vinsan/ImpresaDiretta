package net.impresadiretta.impresadiretta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Toast errore = Toast.makeText(getApplicationContext(), "Abilita i dati a pacchetto oppure collegati ad una rete Wi-Fi per usufruire delle funzioni internet dell'app", Toast.LENGTH_LONG);

		final ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

		if(cd.isConnectingToInternet()){
			YouTubeTask task = new YouTubeTask();
			String params[] = new String[2];
			params[0] = DeveloperKey.DEVELOPER_KEY;
			params[1] = "UCzt6SKDEQFKc-Qopfqrw2fQ";
			task.execute(params);
		}else{
			errore.setDuration(Toast.LENGTH_SHORT);
			errore.show();
		}

		ArrayList<ImageView> immagini = new ArrayList<ImageView>();

		ImageView facebook = (ImageView) findViewById(R.id.imageView2);
		facebook.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				openSocialNetwork("https://m.facebook.com/impresadiretta");
			}
		});
		ImageView twitter = (ImageView) findViewById(R.id.imageView3);
		twitter.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				openSocialNetwork("https://m.twitter.com/impresadiretta");
			}
		});
		ImageView instagram = (ImageView) findViewById(R.id.imageView4);
		instagram.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				openSocialNetwork("https://plus.google.com/u/0/111852878048183491197");
			}
		});

        ImageView live = (ImageView) findViewById(R.id.imageView10);
        live.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                openSocialNetwork("https://vimeo.com/user36180525");
            }
        });

		immagini.add(facebook);
		immagini.add(twitter);
		immagini.add(instagram);
		immagini.add((ImageView) findViewById(R.id.imageView1));
		immagini.add((ImageView) findViewById(R.id.imageView5));
		ImageView check = (ImageView) findViewById(R.id.imageView6);
		immagini.add(check);
		ImageView articoli = (ImageView) findViewById(R.id.imageView7);
		immagini.add(articoli);
		ImageView video = (ImageView) findViewById(R.id.imageView8);
		immagini.add(video);
		ImageView scrivi = (ImageView) findViewById(R.id.imageView9);
		immagini.add(scrivi);
        immagini.add(live);

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int dimens = (size.x/3)-3;	//larghezza
		int altezza = (size.y/4)-4;
		int left = (size.x-(dimens/3))/12;
		int top = 0;
		int right = (size.x-(dimens/3))/6;
		int bottom = 0;
		int i = 0;
		while(i<immagini.size()){
			Bitmap img;
			if(i>=3){	//CONTROLLA NOTIZIE
                img = Bitmap.createScaledBitmap(getBitmap(i), dimens*3, altezza/2, true);
			}else{	//SOCIAL NETWORK
				img = Bitmap.createScaledBitmap(getBitmap(i), altezza/4, altezza/4, true);
			}
			immagini.get(i).setImageBitmap(img);
			i++;
		}
        immagini.get(0).setPadding(left, top, right, bottom);
        immagini.get(1).setPadding(left, top, right, bottom);
        immagini.get(2).setPadding(left, top, 0, bottom);

		articoli.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				openSocialNetwork("http://www.impresadiretta.net/atripalda/");
			}
		});
		video.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				openSocialNetwork("http://m.youtube.com/user/impresadirettalive");
			}
		});
		scrivi.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

				emailIntent.setType("plain/text");
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"impresadirettalive@gmail.com"});

				try{	
					startActivity(emailIntent);
				}catch(ActivityNotFoundException e){
					Toast.makeText(getApplicationContext(), "Nessuna App per l'invio di email. Per favore installane una e riprova.", Toast.LENGTH_LONG).show();
				}
			}
		});
		check.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if(cd.isConnectingToInternet()){
					HtmlTask task = new HtmlTask();
					String params[] = new String[2];
					task.execute(params);
				}else{
					errore.setDuration(Toast.LENGTH_LONG);
					errore.show();
				}
			}
		});
	}

	private Bitmap getBitmap(int i) {
		Resources res = getApplicationContext().getResources();
		if(i==0){
			return BitmapFactory.decodeResource(res, R.drawable.facebook);
		}else if(i==1){
			return BitmapFactory.decodeResource(res, R.drawable.twitter);
		}else if(i==2){
			return BitmapFactory.decodeResource(res, R.drawable.instagram);
		}else if(i==3){
			return BitmapFactory.decodeResource(res, R.drawable.mainlogo);
		}else if(i==4){
			return BitmapFactory.decodeResource(res, R.drawable.alempower);
		}else if(i==5){
			return BitmapFactory.decodeResource(res, R.drawable.button);
		}else if(i==6){
			return BitmapFactory.decodeResource(res, R.drawable.articoli);
		}else if(i==7){
			return BitmapFactory.decodeResource(res, R.drawable.video);
		}else if(i==8){
			return BitmapFactory.decodeResource(res, R.drawable.scrivici);
		}else if(i==9){
            return BitmapFactory.decodeResource(res, R.drawable.live);
		}else
			return null;
	}

	private void openSocialNetwork(String uri){
		Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
		startActivity(i);
	}

	class YouTubeTask extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			HttpClient client = new DefaultHttpClient();
			String html = "";
			String devKey= params[0];
			String channelID = params[1];
			HttpGet request = new HttpGet("https://www.googleapis.com/youtube/v3/activities?part=snippet&channelId="+channelID+"&maxResults=1&key="+devKey);
			try {
				HttpResponse response = client.execute(request);
				InputStream in = response .getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				StringBuilder str = new StringBuilder();
				String line = null;
				while((line = reader.readLine()) != null)
				{
					if(line.contains("title")){
						line = line.replace("title", "Titolo");
						str.append(line);
					}else if(line.contains("description")){
						line = line.replace("description", "Descrizione");
						str.append(line);
					}
				}
				in.close();
				html = str.toString();
				SharedPreferences prefs = getPreferences(MODE_PRIVATE);
				String video = prefs.getString("VIDEO", "null");
				if(!video.equals(html)){
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString("VIDEO", html);
					editor.commit();
				}else{
					html = "";
				}
			}catch (IOException e1) {
				Log.d(devKey, e1.getMessage());
				e1.printStackTrace();
			}
			return html;
		}

		protected void onPostExecute(String result) {
			if(!result.equals("")){
				Toast.makeText(getApplicationContext(), "Nuovo video disponibile sul canale YouTube. "+result, Toast.LENGTH_LONG).show();
			}	//end IF
		}	//end onPostExecute
	}	//end class

	class HtmlTask extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			HttpClient client = new DefaultHttpClient();
			String html = "";
			HttpGet request = new HttpGet("http://www.impresadiretta.net/atripalda/");
			try {
				HttpResponse response = client.execute(request);
				InputStream in = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String line = null;
				Boolean stop = false;
				Boolean flag = false;
				int counter = 0;
				while(!stop){
					line = reader.readLine();
					if(line==null){
						stop = true;
					}else{
						if(line.contains("Ultime Notizie")){
							flag = true;
						}
						if(flag){
							if(line.contains("a href")){
								counter++;
							}	//fine if a href
							if(counter==2){
								if(line.contains("title")){
									html = line;	//trova il titolo
									html = html.replace("title", "Titolo");
									html = html.replace(" >", ".");
									html = html.replaceAll("\t", "");
									stop = true;
								}	//fine if title
							}	//fine if counter
						}	//fine if flag
					}	//fine else
				}	//fine while
				in.close();
				SharedPreferences prefs = getPreferences(MODE_PRIVATE);
				String articolo = prefs.getString("ARTICOLO", "null");
				if(!articolo.equals(html)){
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString("ARTICOLO", html);
					editor.commit();
				}else{
					html = "";
				}
			}catch (IOException e1) {
				e1.printStackTrace();
			}
			return html;
		}

		protected void onPostExecute(String result) {
			if(!result.equals("")){
				Toast.makeText(getApplicationContext(), "Nuovo articolo disponibile: "+result, Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(getApplicationContext(), "Non sono stati aggiunti nuovi articoli dall'ultimo controllo", Toast.LENGTH_LONG).show();
			}
		}	//end onPostExecute
	}	//end class

	public class ConnectionDetector {
		private Context _context;
		public ConnectionDetector(Context context){
			this._context = context;
		}

		public boolean isConnectingToInternet(){
			ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null)
			{
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null)
					for (int i = 0; i < info.length; i++)
						if (info[i].getState() == NetworkInfo.State.CONNECTED)
						{
							return true;
						}
			}
			return false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}