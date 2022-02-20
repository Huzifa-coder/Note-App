package com.barmej.note;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class ShowInfOfActivity extends AppCompatActivity {

    private static final int REDE_PERMISSION_PHOTO = 100;
    private static final int CHANGE_PHOTO = 150;

    private Intent mIntentFromMainActivity;
    private Uri mUri;
    private EditText editText;
    private ImageView imageView;
    private CheckBox checkBox;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_inf_of_with_imgea);
        imageView = findViewById(R.id.imageView_show_activity);
        view = findViewById(R.id.layout_background);
        checkBox = findViewById(R.id.checkBox_show_activity);
        editText = findViewById(R.id.editeText_show_acitivity);
        mIntentFromMainActivity = getIntent();
        setColorBackground(mIntentFromMainActivity.getIntExtra(Constants.KEY_OF_INTENT_NOTE_COlOR, R.color.blue));

        editText.setText(getIntent().getStringExtra(Constants.KEY_OF_INTENT_NOTE_DETILES));

        if (mIntentFromMainActivity.getBooleanExtra(Constants.KEY_OF_INTENT_NOTE_CHEACKBOX, false))
            checkBox.setVisibility(View.VISIBLE);

        if (mIntentFromMainActivity.getData() != null) {
            onSurePhoto();
        }

    }//end of onCreate

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REDE_PERMISSION_PHOTO){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                firePikePhoto();
        }else {
            Toast.makeText(this, R.string.didnt_add_photo, Toast.LENGTH_SHORT).show();
        }
    }//end of onRequestPermissionsResult

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if(resultCode == RESULT_OK && requestCode == CHANGE_PHOTO ){
                ChangePhoto(data);
            }
        }
    }//end of onActivityResult

    private void onReturnInfoForMainActivity(){
        if (!editText.getText().equals(mIntentFromMainActivity.getStringExtra(Constants.KEY_OF_INTENT_NOTE_DETILES))){
            Intent mIntent = new Intent();
            mIntent.putExtra(Constants.KEY_OF_INTENT_NOTE_DETILES, editText.getText().toString());
            mIntent.putExtra(Constants.KEY_OF_INTENT_NOTE_POSTION, mIntentFromMainActivity.getIntExtra(Constants.KEY_OF_INTENT_NOTE_POSTION, -1));
            mIntent.setData(mUri);
            setResult(RESULT_OK, mIntent);
        }
        finish();

    }//end of onReturnInfoForMainActivity

    private void onSurePhoto() {
        mUri = mIntentFromMainActivity.getData();
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageURI(mUri);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seclecttPhoto();
            }
        });
    }//end of onSurePhoto

    private void firePikePhoto(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.setFlags (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intent, CHANGE_PHOTO);
    }//end of firePikePhoto

    private void ChangePhoto(Intent data) {
        mUri = data.getData();
        imageView.setImageURI(mUri);
    }//end of CHANGE_PHOTO

    private void seclecttPhoto() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions( this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REDE_PERMISSION_PHOTO);
        }else {
            firePikePhoto();
        }
    }//end of seclecttPhoto

    private void setColorBackground(int color){
        imageView.setBackgroundColor(color);
        editText.setBackgroundColor(color);
        view.setBackgroundColor(color);
    }//end of setColorBackground

    @Override
    public void onBackPressed() {
        onReturnInfoForMainActivity();
    }
}//end of ShowInfOfActivity