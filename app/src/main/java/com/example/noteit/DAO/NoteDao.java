package com.example.noteit.DAO;

import com.example.noteit.Note;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes ORDER by `priority rating`")
    LiveData<List<Note>> getNotes();

    @Insert
    long[] insert(Note... notes);
    @Delete
    int delete(Note... notes);
    @Update
    int update(Note... notes);

}
