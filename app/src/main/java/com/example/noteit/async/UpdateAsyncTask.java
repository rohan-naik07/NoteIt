package com.example.noteit.async;

import android.os.AsyncTask;

import com.example.noteit.DAO.NoteDao;
import com.example.noteit.Note;

public class UpdateAsyncTask extends AsyncTask<Note,Void,Void> { // does the operation of inserting notes in database in backgroung as a seperate thread
   NoteDao noteDao;

    public UpdateAsyncTask(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDao.update(notes);
        return null;
    }
}
