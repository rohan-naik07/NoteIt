package com.example.noteit;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.noteit.DAO.NoteRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.os.Build.VERSION_CODES.N;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class Note_adapter extends RecyclerView.Adapter<Note_adapter.NoteViewHolder> {
    private Context mCtx;
    private List<Note> NoteList;
    private OnNoteListener onNoteListener;
    MainActivity mainActivity;
    NoteRepository noteRepository;


    public Note_adapter(Context mCtx, List<Note> noteList,OnNoteListener onNoteListener) {
        this.mCtx = mCtx;
        NoteList = noteList;
        this.onNoteListener=onNoteListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(mCtx);
        View view=layoutInflater.inflate(R.layout.card_viewlayout,null);
        return new NoteViewHolder(view,onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteViewHolder holder, final int position) {
         final Note note=NoteList.get(position);

         holder.title.setText(note.getTitle());
        try{
            String month = note.getTimestamp().substring(0, 2);
            month = Utility.getMonthFromNumber(month);
            String year = note.getTimestamp().substring(3);
            String timestamp = month + " " + year;
            holder.timestamp.setText(timestamp);

        }catch (NullPointerException e){
            Log.e(TAG, "onBindViewHolder: Null Pointer: " + e.getMessage() );
        }
         if(note.getImage()!=0)
         {
             holder.imageViewhigh.setVisibility(View.GONE);
             holder.imageViewlow.setVisibility(View.VISIBLE);
         }
         else {
             holder.imageViewhigh.setVisibility(View.VISIBLE);
             holder.imageViewlow.setVisibility(View.GONE);
         }
         holder.delete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                  noteRepository=new NoteRepository(mCtx);
                  NoteList.remove(note);
                  notifyItemRemoved(position);
                  noteRepository.deleteNote(note);
             }
         });

    }

    @Override
    public int getItemCount() {
        return NoteList.size();
    }
    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener { // each view has on note listener attached to it
        View imageViewhigh;
        View imageViewlow;
        TextView title;
        ImageButton delete;
        TextView timestamp;

        OnNoteListener onNoteListener;

        public NoteViewHolder(@NonNull View itemView,OnNoteListener onNoteListener) {
            super(itemView);
            imageViewhigh=itemView.findViewById(R.id.high_priority);
            imageViewlow=itemView.findViewById(R.id.low_priority);
            title=itemView.findViewById(R.id.txtViewTitle);
            delete=itemView.findViewById(R.id.delView);
            timestamp=itemView.findViewById(R.id.timestamp);

            this.onNoteListener=onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
