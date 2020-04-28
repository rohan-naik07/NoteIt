package com.example.noteit.DAO;

import android.content.Context;

import com.example.noteit.Note;
import com.example.noteit.async.DeleteAsyncTask;
import com.example.noteit.async.InsertAsyncTask;
import com.example.noteit.async.UpdateAsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NoteRepository {

    NoteDatabase noteDatabase;

    public NoteRepository(Context context) {
         noteDatabase=NoteDatabase.getInstance(context);
    }

    public void insertNoteTask(Note note)
    {
     new InsertAsyncTask(noteDatabase.getNoteDao()).execute(note);
    }
    public void updateNote(Note note)
    {
      new UpdateAsyncTask(noteDatabase.getNoteDao()).execute(note);
    }
    public void deleteNote(Note note)
    {
        new DeleteAsyncTask(noteDatabase.getNoteDao()).execute(note);
    }
    public LiveData<List<Note>> retrieveNotesTask(){
        return noteDatabase.getNoteDao().getNotes(); // fetch note queries from database
    }
}
