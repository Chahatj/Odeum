package com.chahat.moviedom.activity;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chahat.moviedom.R;
import com.chahat.moviedom.api.ApiClient;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_POSTER_URL;

public class FullImageActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.imageView) ImageView imageView;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    private String imageURL;
    private NotificationCompat.Builder mBuilder;
    private String mCurrentPhotoPath;
    private File storageDirPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(getString(R.string.full_image_transition));
        }

        mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.app_name));

        if (savedInstanceState==null) {
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.hasExtra("ImageURL")) {
                    imageURL = intent.getStringExtra("ImageURL");
                }
            }
        }else {
            imageURL = savedInstanceState.getString(SAVEINSTANCE_POSTER_URL);
        }

        storageDirPic = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        new DownloadTask(this).execute(imageURL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + "_";
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                  storageDirPic /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVEINSTANCE_POSTER_URL,imageURL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_full_image,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.action_share:
                shareImage();
                break;
        }
        return true;
    }

    private void shareImage(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("image/*");
        i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(mCurrentPhotoPath)));
        startActivity(Intent.createChooser(i, "Share with"));
    }


    public static class DownloadTask extends AsyncTask<String,Integer,String>{

        WeakReference<FullImageActivity> weakReference;

        public DownloadTask(FullImageActivity activity){
            weakReference = new WeakReference<FullImageActivity>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakReference.get().progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {

            String imageURL = strings[0];

            int count;
            try {
                Log.d("DownloadTask","Downloading...");
                URL url = new URL(ApiClient.IMAGE_URL+imageURL);
                URLConnection connection = url.openConnection();
                connection.connect();
                // getting file length
                int lengthOfFile = connection.getContentLength();
                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                // Output stream to write file
                OutputStream output = new FileOutputStream(weakReference.get().createImageFile());
                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // writing data to file
                    output.write(data, 0, count);
                    if (isCancelled()) break;
                }
                // flushing output
                output.flush();
                // closing streams
                output.close();
                input.close();

                return weakReference.get().mCurrentPhotoPath;

            } catch (Exception e) {
                Log.e("Error: ", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s!=null){
                if (weakReference!=null && !weakReference.get().isFinishing()){
                    weakReference.get().progressBar.setVisibility(View.GONE);
                    Picasso.with(weakReference.get().getApplicationContext()).load(Uri.fromFile(new File(s))).into(weakReference.get().imageView);
                }
            }
        }
    }
}
