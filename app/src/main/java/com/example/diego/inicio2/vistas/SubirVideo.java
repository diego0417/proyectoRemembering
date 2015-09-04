package com.example.diego.inicio2.vistas;

import com.example.diego.inicio2.Conexion.AndroidMultiPartEntity;
import com.example.diego.inicio2.Conexion.AndroidMultiPartEntity.ProgressListener;
import com.example.diego.inicio2.Conexion.Conexion;
import com.example.diego.inicio2.MainActivity;
import com.example.diego.inicio2.Manejadores.ManejadorUsuario;
import com.example.diego.inicio2.Manejadores.ManejadorVideo;
import com.example.diego.inicio2.R;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


public class SubirVideo extends Activity {
	// LogCat tag
	private static final String TAG = "buenas :)";

	private ProgressBar progressBar;
	private String filePath = null;
	private TextView txtPercentage;
	private ImageView imgPreview;
	private VideoView vidPreview;
	private Button btnUpload;
	long totalSize = 0;
	private int idVideo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_upload_video);
		// PANTALLA EN VERTICAL
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//
		txtPercentage = (TextView) findViewById(R.id.txtPercentage);
		btnUpload = (Button) findViewById(R.id.btnUpload);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		vidPreview = (VideoView) findViewById(R.id.videoPreview);
		//id = ManejadorVideo.insertarVideo();
		// Changing action bar background color
		//getActionBar().setBackgroundDrawable(
		//		new ColorDrawable(Color.parseColor(getResources().getString(
		//				R.color.action_bar))));

		// Receiving the data from previous activity
		Intent i = getIntent();

		// image or video path that is captured in previous activity
		filePath = i.getStringExtra("filePath");

		// boolean flag to identify the media type, image or video

		if (filePath != null) {
			// Displaying the image or video on the screen
			previewMedia();
		} else {
			Toast.makeText(getApplicationContext(),
					"Sorry, file path is missing!", Toast.LENGTH_LONG).show();
		}

		btnUpload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// uploading the file to server
				new UploadFileToServer().execute();
			}
		});
	}

	/**
	 * Displaying captured image/video on the screen
	 * */
	private void previewMedia() {

			vidPreview.setVisibility(View.VISIBLE);
			vidPreview.setVideoPath(filePath);
			Log.i("sadas","tuhermana "+filePath);
			// start playing
			vidPreview.start();

	}

	/**
	 * Uploading the file to server
	 * */
	private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
		@Override
		protected void onPreExecute() {
			// setting progress bar to zero
			progressBar.setProgress(0);
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			// Making progress bar visible
			progressBar.setVisibility(View.VISIBLE);

			// updating progress bar value
			progressBar.setProgress(progress[0]);

			// updating percentage value
			txtPercentage.setText(String.valueOf(progress[0]) + "%");
		}

		@Override
		protected String doInBackground(Void... params) {
			return uploadFile();
		}

		@SuppressWarnings("deprecation")
		private String uploadFile() {
			String responseString = null;

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Conexion.FILE_UPLOAD_URL);

			try {
				Log.e("sad","YUYU1");
				AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
						new ProgressListener() {

							@Override
							public void transferred(long num) {
								publishProgress((int) ((num / (float) totalSize) * 100));
							}
						});
				Log.e("sad","YUYU2");

				File sourceFile = new File(filePath);
				Log.e("sad","YUYU3");

				// Adding file data to http body
				entity.addPart("image", new FileBody(sourceFile));
				Log.e("sad", "YUYU4");

				// Extra parameters if you want to pass to server
				entity.addPart("ID_USUARIO",
						new StringBody(Integer.toString(ManejadorUsuario.usuario.getIdUsuario())));
				//entity.addPart("email", new StringBody("abc@gmail.com"));
				Log.e("sad","YUYU5");
				totalSize = entity.getContentLength();
				Log.e("sad","YUYU6");
				httppost.setEntity(entity);

				// Making server call
				Log.e("sad","YUYU7");
				HttpResponse response = httpclient.execute(httppost);
				Log.e("sad","YUYU8");
				HttpEntity r_entity = response.getEntity();
				Log.e("sad","YUYU9");

				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 200) {
					// Server response
					responseString = EntityUtils.toString(r_entity);
				} else {
					responseString = "Error occurred! Http Status Code: "
							+ statusCode;
				}
			} catch (ClientProtocolException e) {
				responseString = e.toString();
			} catch (IOException e) {
				responseString = e.toString();
			}
			try{
				responseString = responseString.substring(1);
				idVideo = Integer.parseInt(responseString);
				responseString="";
			}catch (Exception e)
			{
				responseString="me rompi la colita";
			}
			return responseString;
		}

		@Override
		protected void onPostExecute(String result) {
			Log.e(TAG, "Response from server: " + result);
			// showing the server response in an alert dialog
			if(result=="")
			{
				Intent intent = new Intent(SubirVideo.this, DescripcionVideo.class);
				intent.putExtra("idVideo", idVideo);
				startActivity(intent);
				finish();
			}
			else
			{
				showAlert(result);
			}
			//Intent i = new Intent(this,GrabarVideo.class);
			//super.onPostExecute(result);
		}
	}

	/**
	 * Method to show alert dialog
	 * */
	private void showAlert(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message).setTitle("Response from Servers")
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// do nothing
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}





}