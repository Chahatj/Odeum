package com.chahat.moviedom.activity;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.widget.Toast;

import com.chahat.moviedom.R;
import com.chahat.moviedom.api.ApiClient;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_POSTER_URL;

public class FullImageActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imageView)
    ImageView imageView;
    private String imageURL;
    private NotificationCompat.Builder mBuilder;

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
        Picasso.with(this).load(ApiClient.IMAGE_URL + imageURL).into(imageView);
    }

    private void onClickNotification(String s){
        Intent intent = new Intent();
        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Moviedom");
        if (file.exists()){
            File outputFile = new File(file,s);
            intent.setDataAndType(Uri.fromFile(outputFile),"Image");
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
        }

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
            case R.id.action_download:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                0);
                    }
                } else {
                    downloadImage();
                }
                break;
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.action_share:
                shareImage();
                break;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadImage();
                } else {
                    Toast.makeText(this, R.string.permission_required, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void downloadImage(){
        Picasso.with(this).load(ApiClient.IMAGE_URL+imageURL).into(new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Moviedom");
                            if (!file.exists()){
                                file.mkdir();
                            }
                            CharSequence s  = DateFormat.format("MM-dd-yy hh-mm-ss", new Date().getTime());
                            File outputFile = new File(file,s+".png");
                            FileOutputStream ostream = new FileOutputStream(outputFile);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 95, ostream);
                            ostream.flush();
                            ostream.close();
                            sendNotification(s);
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    private void sendNotification(CharSequence name){
        int mNotificationId = new Random().nextInt();
        mBuilder.setContentText(name+".png");
        mBuilder.setAutoCancel(true);
        onClickNotification(name+".png");
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    private void shareImage(){
        Picasso.with(this).load(ApiClient.IMAGE_URL+imageURL).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                try {
                    File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Moviedom");
                    if (!file.exists()){
                        file.mkdir();
                    }
                    CharSequence s  = DateFormat.format("MM-dd-yy hh-mm-ss", new Date().getTime());
                    File outputFile = new File(file,s+".png");
                    FileOutputStream ostream = new FileOutputStream(outputFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 95, ostream);
                    ostream.flush();
                    ostream.close();
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("image/*");
                    i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(outputFile));
                    startActivity(Intent.createChooser(i, "Share with"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }
}
