package com.barmej.note.data;

import android.net.Uri;

public class Note {
    private String Detiels;
    private int color;
    private Uri ImageUri;
    private boolean checkBox;

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

    public Note() {

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
