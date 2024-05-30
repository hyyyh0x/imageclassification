package com.example.imageclassification;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.imageclassification.tflite.Classifier;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG="[IC]GalleryActivity";
    public static final int GALLERY_IMAGE_REQUEST_CODE=1;
    Classifier cls;
    Bitmap bitmap=null;
    private ImageView imageView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Button selectBtn=findViewById(R.id.selectBtn);
        textView=findViewById(R.id.textView);
        selectBtn.setOnClickListener(v->getImageFromGallery());
        Button classifyBtn=findViewById(R.id.classifyBtn);
        imageView=findViewById(R.id.imageView);
        imageView.buildDrawingCache();
        bitmap= imageView.getDrawingCache();
        classifyBtn.setOnClickListener(v->{
            /*if(bitmap!=null){
                Pair<Integer,Float> res=cls.classify(bitmap);
                String outStr=String.format(
                        Locale.ENGLISH,
                        "%d, %.0f%%",
                        res.first,
                        res.second*100.0f);
                textView.setText(outStr);
            }*/
            if(bitmap==null){
                Toast.makeText(getApplicationContext(),"Bitmap is null",Toast.LENGTH_LONG).show();
            }
            else{
                FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                        .getOnDeviceTextRecognizer();
                FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
                Task<FirebaseVisionText> result =
                        detector.processImage(image)
                                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                                    @Override
                                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                        process_text(firebaseVisionText);
                                        // Task completed successfully
                                        // [START_EXCLUDE]
                                        // [START get_text]
                                        /*for (FirebaseVisionText.TextBlock block : visionText.getTextBlocks()) {
                                            Rect boundingBox = block.getBoundingBox();
                                            Point[] cornerPoints = block.getCornerPoints();
                                            String text = block.getText();

                                            for (FirebaseVisionText.Line line: block.getLines()) {
                                                // ...
                                                for (FirebaseVisionText.Element element: line.getElements()) {
                                                    // ...

                                                }
                                            }
                                        }*/
                                        // [END get_text]
                                        // [END_EXCLUDE]
                                    }
                                });
            }
        });
        /*cls=new Classifier(this);
        try {
            cls.init();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }*/
    }
    private void process_text(FirebaseVisionText firebaseVisionText){
        List<FirebaseVisionText.TextBlock> blocks=firebaseVisionText.getTextBlocks();
        if(blocks.size()==0){
            Toast.makeText(getApplicationContext(),"No text detected",Toast.LENGTH_LONG).show();
        }
        else{
            for(FirebaseVisionText.TextBlock block:firebaseVisionText.getTextBlocks()){
                String text=block.getText();
                textView.setText(text);
            }
        }
    }
    private void getImageFromGallery(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
        startActivityForResult(i,GALLERY_IMAGE_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode== Activity.RESULT_OK&&
        requestCode==GALLERY_IMAGE_REQUEST_CODE){
            if(data==null){
                return;
            }
            Uri selectedImage=data.getData();
            try{
                if(Build.VERSION.SDK_INT>=29){
                    ImageDecoder.Source src=
                            ImageDecoder.createSource(getContentResolver(),selectedImage);
                    bitmap= ImageDecoder.decodeBitmap(src).copy(Bitmap.Config.ARGB_8888,true);
                }else{
                    bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
                }
            }
            catch (IOException ioe){
                Log.e(TAG,"Failed to read Image",ioe);
            }
            if(bitmap!=null){
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    protected void onDestroy(){
        cls.finish();
        super.onDestroy();
    }
}
