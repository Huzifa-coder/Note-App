
package com.barmej.note.Adpters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.barmej.note.Listener.ItemClickListener;
import com.barmej.note.Listener.ItemClickLongListener;
import com.barmej.note.R;
import com.barmej.note.data.Note;

import java.util.List;

public class NoteAdpter extends RecyclerView.Adapter<NoteAdpter.NoteViewHolder> {
    private boolean isClicked = true;
    private List<Note> notes;
    private ItemClickListener mOnClickListener;
    private ItemClickLongListener mOnClickLongListener;
    private ItemClickListener mItemCheckBoxClickListener;
    public NoteAdpter(List<Note> notes, ItemClickListener mItemClickListener, ItemClickLongListener mItemClickLongListener) {
        this.notes = notes;
        this.mOnClickLongListener = mItemClickLongListener;
        this.mOnClickListener = mItemClickListener;
    }//end of NoteAdpter

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card,parent,false);
        NoteViewHolder viewHolder = new NoteViewHolder(view, mOnClickListener, mOnClickLongListener);
        return viewHolder;
    }//end of NoteViewHolder

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.position = position;
        holder.textView.setText(note.getDetiels());
        holder.checkBox.setVisibility(note.isCheckBox() == true? View.VISIBLE: View.INVISIBLE);
        holder.layout.setBackgroundColor(note.getColor());
    }//end of onBindViewHolder

    @Override
    public int getItemCount() {
        return notes.size();
    }//end of getItemCount

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        int position;
        TextView textView;
        CheckBox checkBox;
        View layout;
        public NoteViewHolder(@NonNull View itemView, ItemClickListener mItemClickListener, ItemClickLongListener mItemClickLongListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView_note_show);
            checkBox = itemView.findViewById(R.id.checkBox_note_show);
            layout =  itemView.findViewById(R.id.linearLayout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onClickItem(position);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mItemClickLongListener.onClickLongItem(position);
                    return true;
                }
            });

            checkBox.setOnClickListener(new View.OnClickListener() {
                      @Override
                public void onClick(View v) {
                    if (isClicked){
                        layout.setBackgroundColor(v.getContext().getColor(R.color.green));
                    }else {
                        layout.setBackgroundColor(notes.get(position).getColor());
                    }
                    isClicked = !isClicked;
                }
            });
        }
    }
}//end of getItemConut
