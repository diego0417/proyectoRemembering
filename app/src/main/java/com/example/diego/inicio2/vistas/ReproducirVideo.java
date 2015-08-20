package com.example.diego.inicio2.vistas;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.diego.inicio2.R;

public class ReproducirVideo extends Activity {

    VideoView videoV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproducir_video);

        String url = getIntent().getStringExtra("url");
        videoV = (VideoView)findViewById(R.id.video_reproducir);
        reproducir(url);
    }

    private void reproducir(String url)
    {
        String  link= url;
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        MediaController mc = new MediaController(this);
        mc.setMediaPlayer(videoV);

        videoV.setMediaController(mc);
        videoV.setVideoURI(Uri.parse(link));
        videoV.requestFocus();
        videoV.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reproducir_video, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
