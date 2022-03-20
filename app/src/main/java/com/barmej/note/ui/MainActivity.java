package com.barmej.note.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.barmej.note.Listener.ItemClickListener;
import com.barmej.note.Listener.ItemClickLongListener;
import com.barmej.note.R;
import com.barmej.note.data.Note;
import com.barmej.note.ui.Adpters.NoteAdpter;
import com.barmej.note.ui.viewModel.MainViewModel;
import com.barmej.note.utiles.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    private RecyclerView recyclerView;
    private NoteAdpter noteAdpter;
    private List<Note> notes;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclear_view);

        notes = new ArrayList<>();
        noteAdpter = new NoteAdpter(notes, new ItemClickListener() {
            @Override
            public void onClickItem(int id) {
                onGoShowActivity(id);
            }

            @Override
            public void onUpdateNote(Note note) {
                mainViewModel.updateNote(note);
            }
        }, new ItemClickLongListener() {
            @Override
            public void onClickLongItem(Note note) {
                deleteNote(note);
            }
        });

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(noteAdpter);

        findViewById(R.id.floatingActionButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddNote();
            }
        });

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notesList) {
                notes.clear();
                notes.addAll(notesList);
                noteAdpter.notifyDataSetChanged();
            }
        });
    }//end of onCreate

    private void onAddNote() {
        startActivity(new Intent( MainActivity.this, AddNoteActivity.class));
    }//end of onAddNote

    private void onGoShowActivity(int id) {
        Intent intent = new Intent(MainActivity.this, ShowInfOfActivity.class);
        intent.putExtra(Constants.KEY_OF_INTENT_NOTE_ID, id);
        startActivity(intent);
    }// end of onGoShowActivity

    private void deleteNote(Note note) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.delet_item))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mainViewModel.deleteNote(note);
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();

    }// end of deleteNote

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;
        getMenuInflater().inflate(R.menu.mune, menu);
        return super.onCreateOptionsMenu(menu);
    }// end of on onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.grid){
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            item.setVisible(false);
            mMenu.findItem(R.id.list).setVisible(true);

        }else if(item.getItemId() == R.id.list){
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
            item.setVisible(false);
            mMenu.findItem(R.id.grid).setVisible(true);

        }
        return super.onOptionsItemSelected(item);
    }// end of on onOptionsItemSelected

}// end of MainActivity