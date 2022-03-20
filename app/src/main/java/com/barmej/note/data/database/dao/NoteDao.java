package com.barmej.note.data.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.barmej.note.data.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    LiveData<List<Note>> getAllNote();

    @Query("SELECT * FROM note where id = :id")
    LiveData<Note> getNote(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addNote(Note note);

    @Update(onConflict = REPLACE)
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

}
