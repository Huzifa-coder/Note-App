package com.barmej.note;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class AddNoteActivity extends Activity {


    private static final int REDE_PERMISSION_PHOTO = 100;
    private static final int ADD_PHOTO = 110;

    private boolean KnowPhoto;
    private int knowColor;
    private boolean knowCheckbox;
    private EditText editText;
    private ImageButton btnRed, btnYallow, btnBlue, btncheckBoxType, btnPhotoType, btnNoteType;
    private Button buttonAddNote;
    private ImageView mImegeView;
    private CheckBox checkBox;
    private Uri mUri ;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        editText = findViewById(R.id.edit_text_note);
        btnRed = findViewById(R.id.btn_red);
        btnBlue = findViewById(R.id.btn_blue);
        btnYallow = findViewById(R.id.btn_yallow);
        btnNoteType = findViewById(R.id.btn_note_type);
        btncheckBoxType = findViewById(R.id.btn_check_box_tpye);
        btnPhotoType = findViewById(R.id.btn_photo_type);
        mImegeView = findViewById(R.id.imageView);
        checkBox = findViewById(R.id.checkBox);
        buttonAddNote = findViewById(R.id.btn_add_note);
        mImegeView.setImageURI(mUri);

        knowColor = getColor(R.color.blue);
        knowCheckbox = false;
        KnowPhoto = false;

        mImegeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seclecttPhoto();
            }
        });

        buttonAddNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(checkBox.getVisibility() == View.VISIBLE) knowCheckbox = true;
                Intent intent = new Intent();
                if(!editText.getText().toString().equals("")) {
                        sentInfoToMainActivity(intent);
                }else{
                        Toast.makeText(AddNoteActivity.this, "empty",Toast.LENGTH_SHORT).show();
                    }
            }

            private void sentInfoToMainActivity(Intent intent) {
                intent.putExtra(Constants.KEY_OF_INTENT_NOTE_DETILES, editText.getText().toString());
                intent.putExtra(Constants.KEY_OF_INTENT_NOTE_COlOR, knowColor);
                intent.putExtra(Constants.KEY_OF_INTENT_NOTE_KNOW_PHOTO, KnowPhoto);
                intent.putExtra(Constants.KEY_OF_INTENT_NOTE_CHEACKBOX, knowCheckbox);
                intent.setData(mUri);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REDE_PERMISSION_PHOTO){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                firePikePhoto();
        }else {
            Toast.makeText(this, R.string.didnt_add_photo, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_PHOTO){
            if(data != null && resultCode == RESULT_OK){
                setSelectPhotoUri(data.getData());
                getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }else
                Toast.makeText(this, R.string.get_faild_to_add_photo, Toast.LENGTH_SHORT).show();
        }
    }

    private void setSelectPhotoUri(Uri data) {
        mImegeView.setImageURI(data);
        mUri = data;
    }

    private void firePikePhoto(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.setFlags (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.didnt_add_photo)), ADD_PHOTO);
    }

    private void seclecttPhoto() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions( this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                    REDE_PERMISSION_PHOTO);
        }else {
            firePikePhoto();
        }
    }



    public void onClickToRed(View view) {
        editText.setBackgroundColor(getColor(R.color.red));
        btnBlue.setImageResource(R.drawable.unchecked_circle_blue);
        btnRed.setImageResource(R.drawable.checked_circle_red);
        btnYallow.setImageResource(R.drawable.unchecked_circle_yellow);
        knowColor = getColor(R.color.red);
    }


    public void onClickToYallow(View view) {
        editText.setBackgroundColor(getColor(R.color.yellow));
        btnBlue.setImageResource(R.drawable.unchecked_circle_blue);
        btnRed.setImageResource(R.drawable.unchecked_circle_red);
        btnYallow.setImageResource(R.drawable.checked_circle_yellow);
        knowColor = getColor(R.color.yellow);
    }

    @SuppressLint("ResourceAsColor")
    public void onClickToBlue(View view) {
        editText.setBackgroundColor(getColor(R.color.blue));
        btnBlue.setImageResource(R.drawable.checked_circle_blue);
        btnRed.setImageResource(R.drawable.unchecked_circle_red);
        btnYallow.setImageResource(R.drawable.unchecked_circle_yellow);
        knowColor = getColor(R.color.blue);
    }

    public void onClickChengeToNote(View view) {
        mImegeView.setVisibility(View.INVISIBLE);
        checkBox.setVisibility(View.INVISIBLE);
        editText.setGravity(Gravity.START);
        btnPhotoType.setImageResource(R.drawable.unchecked_photo);
        btnNoteType.setImageResource(R.drawable.checked_note);
        btncheckBoxType.setImageResource(R.drawable.unchecked_check_box);
        KnowPhoto = false;
        knowCheckbox = false;
    }

    public void onClickChangeToPhoto(View view) {
        mImegeView.setVisibility(View.VISIBLE);
        checkBox.setVisibility(View.INVISIBLE);
        editText.setGravity(Gravity.CENTER);
        editText.setGravity(Gravity.CENTER_VERTICAL);
        btnPhotoType.setImageResource(R.drawable.checked_photo);
        btnNoteType.setImageResource(R.drawable.unchecked_note);
        btncheckBoxType.setImageResource(R.drawable.unchecked_check_box);
        KnowPhoto = true;
        knowCheckbox = false;
    }

    public void onChangeToCheckBox(View view) {
        mImegeView.setVisibility(View.INVISIBLE);
        checkBox.setVisibility(View.VISIBLE);
        editText.setGravity(Gravity.START);
        btnPhotoType.setImageResource(R.drawable.unchecked_photo);
        btnNoteType.setImageResource(R.drawable.unchecked_note);
        btncheckBoxType.setImageResource(R.drawable.checked_check_box);
        knowCheckbox = true;
        KnowPhoto = false;
    }


}