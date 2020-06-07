package com.dimar.map2saveme;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.dimar.map2saveme.models.Animal;
import com.dimar.map2saveme.models.Photo;
import com.dimar.map2saveme.repository.Repository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

// Rzgleduvanje na kod smallPhoto
//request for permision proba detalna so fail UI/UX resenie
public class Map_Pic extends AppCompatActivity {

    TextView labelPhotograph;
    EditText photographID;
    TextView labelAnimal;
    EditText animalID;
    ImageView imageView;
    Button addPhoto;

    private Repository repository;

    private FusedLocationProviderClient fusedLocationClient;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map__pic);

        repository=new Repository();
        initView();

       checkLocationPermission();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            imageView.setImageBitmap(imageBitmap);

            addPhoto=findViewById(R.id.addPhotoBt);
            addPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    assert imageBitmap != null;
                    savePhotoAnimal(imageBitmap);
                }

            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
                    dispatchTakePictureIntent();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    labelPhotograph.setText("Location not allowe ERROR");
                    photographID.setText("please allow location for this aplication");

                }
                return;
            }

        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    //dozvoleno e i dokolku se prazni tekct polinjata
    private void savePhotoAnimal(Bitmap bitmap){

        String photograper=photographID.getText().toString().trim();
        String animal=animalID.getText().toString().trim();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encodedBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location!=null) {

                    String key=Repository.getPhotoKey();
                    Photo photo=new Photo(key,encodedBase64,location.getLongitude(),location.getLatitude(),photograper,animal);
                    repository.save(photo);

                    Animal animalObj=new Animal(animal,animal.toLowerCase().contains("dog")? true:false);
                    repository.save(animalObj);
                    addPhoto.setOnClickListener(null);
                }

            }
        });
    }

    private void initView(){
        imageView=findViewById(R.id.imageView);
        labelPhotograph=findViewById(R.id.labelPhotograph);
        photographID=findViewById(R.id.textPhotographID);
        labelAnimal=findViewById(R.id.labelAnimal);
        animalID=findViewById(R.id.txtAnimalID);
    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                                                        ContextCompat.checkSelfPermission(this,
                                                        Manifest.permission.ACCESS_COARSE_LOCATION)
                                                        != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
            || ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                if (ActivityCompat.shouldShowRequestPermissionRationale(Map_Pic.this,
                                        Manifest.permission.ACCESS_FINE_LOCATION)){
                                    ActivityCompat.requestPermissions(Map_Pic.this,
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                            MY_PERMISSIONS_REQUEST_LOCATION);
                                }else{
                                    ActivityCompat.requestPermissions(Map_Pic.this,
                                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                            MY_PERMISSIONS_REQUEST_LOCATION);
                                }

                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            dispatchTakePictureIntent();
            return true;
        }
    }


}
