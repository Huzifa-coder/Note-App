package com.barmej.note.Listener;

import com.barmej.note.data.Note;

public interface ItemClickListener {
     void onClickItem(int id);

     void onUpdateNote(Note note);
}
