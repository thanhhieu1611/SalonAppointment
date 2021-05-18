package com.example.salonappointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation;

public class FlashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);

        HideActionBar();
        try{
            // Transform image using picasso transformation, it remove animated effect
            TransformFlashImage(R.drawable.logo, R.id.imgFlashScreen);

            // Waiting time of splash screen
            WaitSeconds(5);
        }catch(Exception exception){
            Toast.makeText(FlashScreen.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Hide Action bar for this particular activity
    private void HideActionBar(){
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }

    //Wait for a couple seconds
    private void WaitSeconds(int sc){
        CountDownTimer countDownTimer = new CountDownTimer(sc * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                // Open user login activity
                startActivity(new Intent(FlashScreen.this, UserAuthentication.class));
            }
        }.start();

    }

    // Transform flash image
    private void TransformFlashImage(int imageId, int viewId){
        Picasso.get().load(imageId).resize(800,800).transform(new SepiaFilterTransformation(this)).transform(new CropCircleTransformation()).into((ImageView) findViewById(viewId));
    }
}