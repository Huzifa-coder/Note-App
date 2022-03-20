package com.barmej.note.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.barmej.note.data.Note;
import com.barmej.note.data.database.AppDatabase;
import com.barmej.note.utiles.AppExecutor;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static AppExecutor mAppExecutor;
    private static AppDatabase mAppDatabase;

    public MainViewModel(@NonNull Application application){
        super(application);

        mAppDatabase = AppDatabase.getInstance(application);

        mAppExecutor = AppExecutor.getInstance();
    }


    public void deleteNote(Note note) {
        mAppExecutor.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAppDatabase.getNoteDao().deleteNote(note);
            }
        });
    }

    public void updateNote(Note note){
        mAppExecutor.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAppDatabase.getNoteDao().updateNote(note);
            }
        });
    }

    public void addNote(Note note){
        mAppExecutor.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAppDatabase.getNoteDao().addNote(note);
            }
        });
    }

    public LiveData<Note> getNote(int id){
        return mAppDatabase.getNoteDao().getNote(id);
    }

    public LiveData<List<Note>> getAllNotes(){
        return mAppDatabase.getNoteDao().getAllNote();
    }

}
