package com.example.noteit.async;

import android.os.AsyncTask;

import com.example.noteit.DAO.NoteDao;
import com.example.noteit.Note;

public class InsertAsyncTask extends AsyncTask<Note,Void,Void> { // does the operation of inserting notes in database in backgroung as a seperate thread
   NoteDao noteDao;

    public InsertAsyncTask(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDao.insert(notes);
        return null;
    }
}
