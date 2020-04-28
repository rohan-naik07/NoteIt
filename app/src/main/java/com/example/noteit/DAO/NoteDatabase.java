package com.example.noteit.DAO;

import android.content.Context;

import com.example.noteit.Note;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Note.class},version = 1) // adding schema dependency helps to create a json database file
public abstract class NoteDatabase extends RoomDatabase {

 public static final String DATABASE_NAME="notes_db";
   private static NoteDatabase instance;
   static NoteDatabase getInstance(final Context context){
        if(instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class,DATABASE_NAME).build();
        }
        return instance;
   }
   public abstract NoteDao getNoteDao();
}
