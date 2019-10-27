package com.indieprogress.shopinglisttest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.indieprogress.shopinglisttest.base.BaseActivity;
import com.indieprogress.shopinglisttest.shoplist.view.ShopBoughtFragment;
import com.indieprogress.shopinglisttest.shoplist.view.ShopListFragment;
import com.indieprogress.shopinglisttest.shoplist.view.ShopNotBoughtFragment;
import com.indieprogress.shopinglisttest.shoplist.view.dialog.GetImageDialog;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int GALLERY_REQUEST_CODE = 300;
    private static final int CAMERA_REQUEST_CODE = 400;
    @Inject
    ShopListFragment shopListFragment;

    @Inject
    GetImageDialog getImageDialog;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Inject
    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.page_all:
                            showFragment(R.id.fragment_container, shopListFragment);
                            break;
                        case R.id.page_not_bought:
                            showFragment(R.id.fragment_container, new ShopNotBoughtFragment());
                            break;
                        case R.id.page_bought:
                            showFragment(R.id.fragment_container, new ShopBoughtFragment());
                            break;
                        default: break;
                    }
                    return true;
                });
        showFragment(R.id.fragment_container, shopListFragment);

        fab.setOnClickListener(view -> getImageFromDevice());
    }

    private void getImageFromDevice() {
        checkPermission();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat
                .checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
        } else {
              showDialogImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            int counter = 0;
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                counter--;
            } else {
                counter++;
            }
            if (counter > 0) {
                checkPermission();
            } else if (counter < 0) {
                showDialogImage();
            }
        }
    }

    private void showDialogImage() {
        getImageDialog.setCallBack(new GetImageDialog.CallBack() {
            @Override
            public void onCamera() {
                pickFromCamera();
            }

            @Override
            public void onGallery() {
                pickFromGallery();
            }
        });
        getImageDialog.show(getSupportFragmentManager(), "image");
    }

    private void pickFromCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    private void pickFromGallery(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    Uri selectedImage = data.getData();
                    Toast.makeText(this,selectedImage.toString(),Toast.LENGTH_SHORT).show();
                    break;
                case CAMERA_REQUEST_CODE:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    Toast.makeText(this,photo.toString(),Toast.LENGTH_SHORT).show();
                    break;
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
