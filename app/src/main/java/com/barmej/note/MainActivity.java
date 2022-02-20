package com.barmej.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.barmej.note.Adpters.NoteAdpter;
import com.barmej.note.Listener.ItemClickListener;
import com.barmej.note.Listener.ItemClickLongListener;
import com.barmej.note.data.Note;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_PHOTO = 150;
    public static final int SENT_CHANGES = 140;

    private RecyclerView recyclerView;
    private NoteAdpter noteAdpter;
    private ArrayList<Note> notes;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView = findViewById(R.id.recyclear_view);

        notes = new ArrayList<Note>();
        noteAdpter = new NoteAdpter(notes, new ItemClickListener() {
            @Override
            public void onClickItem(int postion) {
                onCilckMoveToShowActivity(postion);
            }
        }, new ItemClickLongListener() {
            @Override
            public void onClickLongItem(int postion) {
                deleteNote(postion);
            }
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(noteAdpter);

        findViewById(R.id.floatingActionButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCilckAddNewNote();
            }
        });
    }//end of onCreate
    

    private void onCilckMoveToShowActivity(int postion) {
        Intent intent = new Intent(MainActivity.this, ShowInfOfActivity.class);
        intent.putExtra(Constants.KEY_OF_INTENT_NOTE_DETILES, notes.get(postion).getDetiels());
        intent.putExtra(Constants.KEY_OF_INTENT_NOTE_COlOR, notes.get(postion).getColor());
        intent.putExtra(Constants.KEY_OF_INTENT_NOTE_IMEGE_URI, notes.get(postion).getImageUri());
        intent.putExtra(Constants.KEY_OF_INTENT_NOTE_CHEACKBOX, notes.get(postion).isCheckBox());
        intent.putExtra(Constants.KEY_OF_INTENT_NOTE_POSTION, postion);
        intent.setData(notes.get(postion).getImageUri());
        startActivityForResult(intent, SENT_CHANGES);
    }//end of onCilckMoveToShowActivity


    private void deleteNote(int postion) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.shor_delet_item))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notes.remove(postion);
                        noteAdpter.notifyItemRemoved(postion);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SENT_CHANGES){
            if (resultCode == RESULT_OK && data != null){
                int postion = data.getIntExtra(Constants.KEY_OF_INTENT_NOTE_POSTION, -1);
                notes.get(postion).setDetiels(data.getStringExtra(Constants.KEY_OF_INTENT_NOTE_DETILES));
                notes.get(postion).setImageUri(data.getData());
                noteAdpter.notifyItemChanged(postion);
            }else {
                Toast.makeText(this,R.string.has_not_change,Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == ADD_PHOTO){
            if(resultCode == RESULT_OK && data != null){
                if(data.getStringExtra(Constants.KEY_OF_INTENT_NOTE_DETILES) != null){

                    if (data.getBooleanExtra(Constants.KEY_OF_INTENT_NOTE_KNOW_PHOTO, false)) {
                        addNote(data.getStringExtra(Constants.KEY_OF_INTENT_NOTE_DETILES),
                                data.getData(),
                                data.getIntExtra(Constants.KEY_OF_INTENT_NOTE_COlOR, R.color.blue));
                        Toast.makeText(MainActivity.this, R.string.add_note, Toast.LENGTH_SHORT).show();

                    }else if (  data.getBooleanExtra(Constants.KEY_OF_INTENT_NOTE_CHEACKBOX, false)){
                        addNote(data.getStringExtra(Constants.KEY_OF_INTENT_NOTE_DETILES),
                                data.getBooleanExtra(Constants.KEY_OF_INTENT_NOTE_CHEACKBOX,false),
                                data.getIntExtra(Constants.KEY_OF_INTENT_NOTE_COlOR, R.color.blue));
                        Toast.makeText(MainActivity.this, R.string.add_note, Toast.LENGTH_SHORT).show();
                    }else {
                        addNote(data.getStringExtra(Constants.KEY_OF_INTENT_NOTE_DETILES),
                                data.getIntExtra(Constants.KEY_OF_INTENT_NOTE_COlOR, R.color.blue));
                        Toast.makeText(MainActivity.this, R.string.add_note, Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(MainActivity.this, R.string.dosent_add_note, Toast.LENGTH_SHORT).show();
                }

            }
        }
    }//end of onActivityResult

    private void addNote(String s, Uri uri, int n) {
        Note note = new Note(s,n,uri);

        notes.add(note);
        noteAdpter.notifyItemInserted(notes.size()-1);
    }// end of addNote-Uri


    private void addNote(String s,boolean isCheckBox, int n) {
        Note note = new Note(s, n, isCheckBox);

        notes.add(note);
        noteAdpter.notifyItemInserted(notes.size()-1);
    }// end of addNote-checkBox


    private void addNote(String s ,int n) {
        Note note = new Note(s,n);

        notes.add(note);
        noteAdpter.notifyItemInserted(notes.size()-1);
    }// end of addNote-note


    private void onCilckAddNewNote() {
        Intent intent = new Intent( MainActivity.this, AddNoteActivity.class);
        startActivityForResult(intent, ADD_PHOTO);
    }// end of startAddNewNoteActivity

}// end of MainActivity