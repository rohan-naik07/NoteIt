package com.example.noteit;

import android.content.Intent;
import android.os.Bundle;

import com.example.noteit.DAO.NoteRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Note_adapter.OnNoteListener,FloatingActionButton.OnClickListener {
    RecyclerView recyclerView;
    Note_adapter noteAdapter;
    List<Note> NoteList=new ArrayList<>();
    FloatingActionButton floatingActionButton;
    TextView noofnotes;
    Integer no;
    String number;
    NoteRepository noteRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.floating);
        floatingActionButton.setOnClickListener(this);
        noofnotes=findViewById(R.id.noofnotes);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
         initrecyclerview();
         noteRepository=new NoteRepository(this);
          retrieveNotes();
    }
    private void initrecyclerview()
    {
        noteAdapter=new Note_adapter(MainActivity.this,NoteList,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(noteAdapter);
    }
    private void retrieveNotes(){
        noteRepository.retrieveNotesTask().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                 if(NoteList.size()>0)
                 {
                     NoteList.clear();
                 }
                 if(notes!=null)
                 {
                     NoteList.addAll(notes);
                     no=NoteList.size();
                     number=no.toString();
                     noofnotes.setText(number + " "+ "Notes");
                 }
                 noteAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onNoteClick(int position) {
        Intent intent=new Intent(this,NewNoteActivity.class);
        intent.putExtra("selected_note",NoteList.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,NewNoteActivity.class);
        //intent.putExtra("selected_note",NoteList.get(position));
        startActivity(intent);
    }
}


