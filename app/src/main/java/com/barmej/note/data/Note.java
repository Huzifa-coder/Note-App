package com.barmej.note.data;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note")
 public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String Detiels;
    private int color;

    private Uri ImageUri;
    private boolean checkBox;
    private boolean isClicked;



    public Note() {
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Note(String detiels, int color, Uri imageUri) {
        Detiels = detiels;
        this.color = color;
        ImageUri = imageUri;
    }

    public Note(String detiels, int color, boolean checkBox) {
        Detiels = detiels;
        this.color = color;
        this.checkBox = checkBox;
    }

    public Note(String detiels, int color) {
        Detiels = detiels;
        this.color = color;
    }

    public Note(String detiels, int color, Uri imageUri, boolean checkBox, boolean isClicked) {
        this.id = id;
        Detiels = detiels;
        this.color = color;
        ImageUri = imageUri;
        this.checkBox = checkBox;
        this.isClicked = isClicked;
    }

    public Note(String detiels, int color, Uri imageUri, boolean checkBox) {
        Detiels = detiels;
        this.color = color;
        this.ImageUri = imageUri;
        this.checkBox = checkBox;
    }


    public String getDetiels() {
        return Detiels;
    }

    public void setDetiels(String detiels) {
        Detiels = detiels;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Uri getImageUri() {
        return ImageUri;
    }

    public void setImageUri(Uri imageUri) {
        ImageUri = imageUri;
    }

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }


}
