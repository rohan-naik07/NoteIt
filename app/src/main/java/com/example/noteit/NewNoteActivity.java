package com.example.noteit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.noteit.DAO.NoteRepository;

public class NewNoteActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, View.OnClickListener, TextWatcher {
     private EditText editText;
     private LinedEditText linedEditText;
     private  TextView title;
     private boolean isNewNote;
     private Note note;
     private Note finalNote;
     private GestureDetector gestureDetector; // object to create double taps
     private static final int EDIT_MODE_ENABLED=1;
     private static final int EDIT_MODE_DISABLED=0;
     private int mode;
     private RelativeLayout check,back;
     private ImageButton checkButton,backButton;
     private NoteRepository noteRepository;
     private ImageButton highpr,lowpr;
     private int priority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
          title=findViewById(R.id.note_title);
          linedEditText=findViewById(R.id.note_text);
          editText=findViewById(R.id.note_edit);
          check=findViewById(R.id.checkButtoncontainer);
          back=findViewById(R.id.backButtoncontainer);
          checkButton=findViewById(R.id.CheckButton);
          backButton=findViewById(R.id.backButton);
          highpr=findViewById(R.id.high_pr);
          lowpr=findViewById(R.id.low_pr);
          noteRepository=new NoteRepository(this);
          priority=1;
          setListeners();
          if(!getIncomingIntent()) // not a new note
          {
              edit_note();
              SetFixedMode();
              disableInteraction();
          }
          else {new_note(); SetEditMode();}


    }
    private void SetEditMode()
    {

        check.setVisibility(View.VISIBLE);
        checkButton.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
        back.setVisibility(View.GONE);
        backButton.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        mode=EDIT_MODE_ENABLED;
        enableInteraction();
    }
    private void SetFixedMode()
    {

        check.setVisibility(View.GONE);
        checkButton.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        mode=EDIT_MODE_DISABLED;
         disableInteraction();
        String temp= linedEditText.getText().toString();
        temp=temp.replace("\n","");
        temp=temp.replace(" ","");
        if(temp.length()>0)
        {
            finalNote.setTitle(editText.getText().toString());
            finalNote.setContent(linedEditText.getText().toString());
            finalNote.setTimestamp(Utility.getCurrentTimeStamp());
            finalNote.setImage(priority);
            if(!finalNote.getContent().equals(note.getContent())|| !finalNote.getTitle().equals(note.getTitle()))
            {
                saveChanges();
            }
        }

    }
    private boolean getIncomingIntent()
    {

        if(getIntent().hasExtra("selected_note"))
        {   note=getIntent().getParcelableExtra("selected_note");
           finalNote=new Note();
           finalNote.setImage(note.getImage());
           finalNote.setTimestamp(note.getTimestamp());
           finalNote.setTitle(note.getTitle());
           finalNote.setContent(note.getContent());
           finalNote.setId(note.getId());
            mode=EDIT_MODE_ENABLED;
            isNewNote=false;
            return false;
        }
        mode=EDIT_MODE_ENABLED;
        isNewNote=true;
        return true;
    }
    private void saveChanges()
    {
        if(isNewNote)
        {
          saveNewNote();
        }
        else
        {
         updateNote();
        }
    }
    private void updateNote()
    {
        noteRepository.updateNote(finalNote);
    }
    private void saveNewNote()
    {
       noteRepository.insertNoteTask(finalNote);

    }

    private void setListeners()
    {
        linedEditText.setOnTouchListener(this);
        gestureDetector=new GestureDetector(this,this);
        title.setOnClickListener(this);
        checkButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        editText.addTextChangedListener(this);
        highpr.setOnClickListener(this);
        lowpr.setOnClickListener(this);
    }
    private void new_note()
    {

        title.setHint("Add Titla");
        editText.setText("Add Note");

        note=new Note();
        finalNote=new Note();
        note.setTitle("Note Title");
        finalNote.setTitle("Note Title");

    }
    private void edit_note()
    {
        title.setText(note.getTitle());
        linedEditText.setText(note.getContent());
        editText.setText(note.getTitle());
        if(note.getImage()==0)
        {
            lowpr.setVisibility(View.GONE);
            highpr.setVisibility(View.VISIBLE);
        }
        else
        {
            lowpr.setVisibility(View.VISIBLE);
            highpr.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        SetEditMode();
        enableInteraction();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
    private void enableInteraction()
    {
        //linedEditText.setOnKeyListener((View.OnKeyListener) new EditText(this).getKeyListener());
        linedEditText.setFocusable(true);
        linedEditText.setFocusableInTouchMode(true);
        linedEditText.setCursorVisible(true);
        linedEditText.requestFocus();
    }
    private void disableInteraction()
    {
        linedEditText.setOnKeyListener(null);
        linedEditText.setFocusable(false);
        linedEditText.setFocusableInTouchMode(false);
        linedEditText.setCursorVisible(false);
        linedEditText.clearFocus();
    }

    @Override
    public void onClick(View v) {
         switch (v.getId())
         {
             case R.id.CheckButton:{
                    SetFixedMode();
                    disableInteraction();
                    break;
             }
             case R.id.note_edit:{
                  SetEditMode();
                  editText.requestFocus();
                  editText.setSelection(editText.length()); // cursor goes to end of title string
                  enableInteraction();
                  break;
             }
             case R.id.backButton:{
                  finish();
                  break;
             }
             case R.id.low_pr:{
                  if(highpr.getVisibility()==View.GONE && mode==EDIT_MODE_ENABLED)
                  {
                      priority=0;
                      highpr.setVisibility(View.VISIBLE);
                      lowpr.setVisibility(View.GONE);
                  }
                  else priority=1;

                  break;
             }
             case R.id.high_pr:{
                 if(lowpr.getVisibility()==View.GONE && mode==EDIT_MODE_ENABLED)
                 {
                     priority=1;
                     highpr.setVisibility(View.GONE);
                     lowpr.setVisibility(View.VISIBLE);
                 }
                 else priority=0;

                 break;
             }
         }
    }

    @Override
    public void onBackPressed() {
        if(mode==EDIT_MODE_ENABLED)
        {
            onClick(checkButton);
        }
        else
        {
            super.onBackPressed();
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        title.setText(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
