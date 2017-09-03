package com.chahat.moviedom.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.chahat.moviedom.R;
import com.chahat.moviedom.api.ApiClient;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chahat.moviedom.utils.Constants.SAVEINSTANCE_POSTER_URL;

public class FullImageActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imageView)
    ImageView imageView;
    private String imageURL;

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
                Log.d("TAG","indownload");
                downloadImage();
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
