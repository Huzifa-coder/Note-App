package com.barmej.note.data.converters;

import android.net.Uri;
import android.text.TextUtils;

import androidx.room.TypeConverter;

public class NoteConverter {

    @TypeConverter
    public static Uri fromString(String value) {
        if(TextUtils.isEmpty(value))
            return null;
        else
            return Uri.parse(value);
    }

    @TypeConverter
    public static String fromUri(Uri uri) {
        if (uri == null)
            return null;
        else
            return uri.toString();
    }
}
