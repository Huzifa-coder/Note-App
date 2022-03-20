package com.barmej.note.ui;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.barmej.note.R;
import com.barmej.note.data.Note;
import com.barmej.note.ui.viewModel.MainViewModel;
import com.barmej.note.utiles.Constants;

public class ShowInfOfActivity extends AppCompatActivity {

    private static final int REDE_PERMISSION_PHOTO = 100;
    private static final int CHANGE_PHOTO = 150;
    private MainViewModel mainViewModel;

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

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getNote(getIntent().getIntExtra(Constants.KEY_OF_INTENT_NOTE_ID, -1)).observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                setColorBackground(note.getColor());
                editText.setText(note.getDetiels());
                if (note.isCheckBox()) {
                    checkBox.setVisibility(View.VISIBLE);
                    checkBox.setChecked(note.isClicked());
                }
                if(note.getImageUri() != null) {
                    onSurePhoto(note);
                }
            }
        });

        findViewById(R.id.save_note_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChangeNote();
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
    }//end of onRequestPermissionsResult

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null){
            if(resultCode == RESULT_OK && requestCode == CHANGE_PHOTO ){
                ChangePhoto(data);
                getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        }else{
            Toast.makeText(this, R.string.didnt_change_photo, Toast.LENGTH_SHORT).show();
        }
    }//end of onActivityResult

    private void onReturnInfoForMainActivity(){
        finish();
    }//end of onReturnInfoForMainActivity

    private void onSurePhoto(Note note) {
        mUri = note.getImageUri();
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
        intent.setFlags (Intent.FLAG_GRANT_READ_URI_PERMISSION);
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

    private void onChangeNote(){
        mainViewModel.getNote(getIntent().getIntExtra(Constants.KEY_OF_INTENT_NOTE_ID, -1)).observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                note.setDetiels(editText.getText().toString());
                if (note.getImageUri() != null) note.setImageUri(mUri);
                if(note.isCheckBox() == true) note.setClicked(checkBox.isChecked());
                mainViewModel.updateNote(note);
                finish();
            }
        });

    }
}//end of ShowInfOfActivity