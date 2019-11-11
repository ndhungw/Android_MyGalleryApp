package com.example.mygallery.AlbumsActivity;


import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;

import com.example.mygallery.DAO.DatabaseHandler;
import com.example.mygallery.DTOs.Image;
import com.example.mygallery.R;

import java.util.List;

public class AlbumActivity extends Activity {

    private static final int PICK_IMAGE = 100;

    //List các đối tượng
    TextView text;
    GridView gridView;
    Button button;

    //Biến
    List<Image> list;
    ImageAdapter adapter;
    String nameAlbum;

    int IDAlbum;

    public void init()
    {
        text = findViewById(R.id.nameAlbum) ;
        gridView = findViewById(R.id.gridview);
        button =  findViewById(R.id.add);

    }

    public void getData()
    {
        //Get data from HomeActivity
        Intent callingIntent = getIntent();
        Bundle myBundle = callingIntent.getExtras();
        IDAlbum= myBundle.getInt("IDAlbum");
        nameAlbum= myBundle.getString("nameAlbum") + String.valueOf(IDAlbum);
        list = DatabaseHandler.getInstance(AlbumActivity.this).getAllImageOfAlbum(IDAlbum);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagesofalbum);

        //Lay du lieu
        getData();

        //Lien Ket
        init();

        //Cai đặt các đối tượng
        text.setText(nameAlbum);
        adapter= new ImageAdapter(this, list);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    //Mở thư viện ảnh
    private void openGallery() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE);

        } else {

            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(gallery, PICK_IMAGE);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();

                for (int i = 0; i < mClipData.getItemCount(); i++) {

                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();
                    list.add(new Image(uri.toString()));
                    DatabaseHandler.getInstance(AlbumActivity.this).addImage(uri.toString(),IDAlbum);
                    adapter.notifyDataSetChanged();
                }

            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getParentActivityIntent() == null) {
                    Log.i("SearchActivity",

                            "Fix Manifest to indicate the parentActivityName");

                    onBackPressed();
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

