
package com.barmej.note.ui.Adpters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.barmej.note.Listener.ItemClickListener;
import com.barmej.note.Listener.ItemClickLongListener;
import com.barmej.note.R;
import com.barmej.note.data.Note;
import com.barmej.note.databinding.NoteCardBinding;

import java.util.List;

public class NoteAdpter extends RecyclerView.Adapter<NoteAdpter.NoteViewHolder> {
    private List<Note> notes;
    private ItemClickListener mOnClickListener;
    private ItemClickLongListener mOnClickLongListener;


    public NoteAdpter(List<Note> notes, ItemClickListener mItemClickListener, ItemClickLongListener mItemClickLongListener) {
        this.notes = notes;
        this.mOnClickListener = mItemClickListener;
        this.mOnClickLongListener = mItemClickLongListener;
    }//end of NoteAdpter

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NoteCardBinding binding = NoteCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        NoteViewHolder viewHolder = new NoteViewHolder(binding, mOnClickListener, mOnClickLongListener);
        return viewHolder;
    }//end of NoteViewHolder

    @SuppressLint({"RecyclerView"})
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.bind(note);

    }//end of onBindViewHolder

    @Override
    public int getItemCount() {
        return notes.size();
    }//end of getItemCount

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        NoteCardBinding binding;

        @SuppressLint("ResourceAsColor")
        public NoteViewHolder(@NonNull NoteCardBinding binding, ItemClickListener mItemClickListener, ItemClickLongListener mItemClickLongListener) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onClickItem(notes.get(getAdapterPosition()).getId());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mItemClickLongListener.onClickLongItem(notes.get(getAdapterPosition()));
                    return true;
                }
            });

            binding.checkBoxNoteShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){        @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        binding.linearLayout.setBackgroundColor(compoundButton.getContext().getColor(R.color.green));
                    }else {
                        binding.linearLayout.setBackgroundColor(binding.getNote().getColor());
                    }

                    if (getAdapterPosition() != -1) {
                        notes.get(getAdapterPosition()).setClicked(b);
                        mItemClickListener.onUpdateNote(notes.get(getAdapterPosition()));
                    }
                }
            });



        }

        public void bind(Note note) {
            binding.setNote(note);
        }
    }
}//end of NoteAdpter
