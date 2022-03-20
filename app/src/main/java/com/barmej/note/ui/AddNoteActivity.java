package com.barmej.note.ui;

import android.Manifest;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.barmej.note.R;
import com.barmej.note.data.Note;
import com.barmej.note.ui.viewModel.MainViewModel;

public class AddNoteActivity extends AppCompatActivity {

    private static final int REDE_PERMISSION_PHOTO = 100;
    private static final int ADD_PHOTO = 110;

    private static MainViewModel mainViewModel;

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

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        knowColor = getColor(R.color.blue);
        knowCheckbox = false;
        mUri = null;

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
                    mainViewModel.addNote(new Note(editText.getText().toString(), knowColor, mUri, knowCheckbox, checkBox.isChecked()));
                    finish();
                }else{
                        Toast.makeText(AddNoteActivity.this, R.string.add_text,Toast.LENGTH_SHORT).show();
                    }
            }
        });

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
    }// end of onRequestPermissionsResult

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_PHOTO){
            if(data != null && resultCode == RESULT_OK){
                Uri uri = data.getData();
                grantUriPermission(getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                setSelectPhotoUri(uri);
            }else
                Toast.makeText(this, R.string.get_faild_to_add_photo, Toast.LENGTH_SHORT).show();
        }
    }//end of onActivityResult

    private void setSelectPhotoUri(Uri data) {
        mImegeView.setImageURI(data);
        mUri = data;
    }//end of setSelectPhotoUri

    private void firePikePhoto(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addFlags (Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.didnt_add_photo)), ADD_PHOTO);
    }//end fo firePikePhoto

    private void seclecttPhoto() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions( this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                    REDE_PERMISSION_PHOTO);
        }else {
            firePikePhoto();
        }
    }//end of seclecttPhoto


    public void onClickToRed(View view) {
        editText.setBackgroundColor(getBaseContext().getColor(R.color.red));
        btnBlue.setImageResource(R.drawable.unchecked_circle_blue);
        btnRed.setImageResource(R.drawable.checked_circle_red);
        btnYallow.setImageResource(R.drawable.unchecked_circle_yellow);
        knowColor = getColor(R.color.red);
    }// end fo onClickToRed


    public void onClickToYallow(View view) {
        editText.setBackgroundColor(getBaseContext().getColor(R.color.yellow));
        btnBlue.setImageResource(R.drawable.unchecked_circle_blue);
        btnRed.setImageResource(R.drawable.unchecked_circle_red);
        btnYallow.setImageResource(R.drawable.checked_circle_yellow);
        knowColor = getColor(R.color.yellow);
    }//end of onClickToYallow

    public void onClickToBlue(View view) {
        editText.setBackgroundColor(getBaseContext().getColor(R.color.blue));
        btnBlue.setImageResource(R.drawable.checked_circle_blue);
        btnRed.setImageResource(R.drawable.unchecked_circle_red);
        btnYallow.setImageResource(R.drawable.unchecked_circle_yellow);
        knowColor = getColor(R.color.blue);
    }// end of onClickToBlue

    public void onClickChengeToNote(View view) {
        mImegeView.setVisibility(View.INVISIBLE);
        checkBox.setVisibility(View.INVISIBLE);
        editText.setGravity(Gravity.START);
        btnPhotoType.setImageResource(R.drawable.unchecked_photo);
        btnNoteType.setImageResource(R.drawable.checked_note);
        btncheckBoxType.setImageResource(R.drawable.unchecked_check_box);
        knowCheckbox = false;
        mUri = null;
    }// end of onClickChengeToNote

    public void onClickChangeToPhoto(View view) {
        mImegeView.setVisibility(View.VISIBLE);
        checkBox.setVisibility(View.INVISIBLE);
        editText.setGravity(Gravity.CENTER);
        editText.setGravity(Gravity.CENTER_VERTICAL);
        btnPhotoType.setImageResource(R.drawable.checked_photo);
        btnNoteType.setImageResource(R.drawable.unchecked_note);
        btncheckBoxType.setImageResource(R.drawable.unchecked_check_box);
        knowCheckbox = false;
        mUri = null;
    }// end of onClickChangeToPhoto

    public void onChangeToCheckBox(View view) {
        mImegeView.setVisibility(View.INVISIBLE);
        checkBox.setVisibility(View.VISIBLE);
        editText.setGravity(Gravity.START);
        btnPhotoType.setImageResource(R.drawable.unchecked_photo);
        btnNoteType.setImageResource(R.drawable.unchecked_note);
        btncheckBoxType.setImageResource(R.drawable.checked_check_box);
        knowCheckbox = true;
        mUri = null;
    }// end of onChangeToCheckBox


}