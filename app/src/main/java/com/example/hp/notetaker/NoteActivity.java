package com.example.hp.notetaker;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.tapadoo.alerter.Alerter;

import java.io.IOException;
import java.util.Calendar;

import static java.lang.Thread.sleep;

public class NoteActivity extends AppCompatActivity  {

    private EditText nEtTitle;
    private EditText nEtContent;
    private String mNoteFileName;
    private Note mLoadedNote;
    Typeface titlefont;
    Toolbar toolbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);

        nEtTitle = (EditText) findViewById(R.id.note_et_title);
        nEtContent = (EditText) findViewById(R.id.note_et_content);
        mNoteFileName = getIntent().getStringExtra("NOTE_FILE");
        if(mNoteFileName !=  null && !mNoteFileName.isEmpty()){
            mLoadedNote = Utilities.getNoteByName(this, mNoteFileName);

           if(mLoadedNote != null){
               nEtTitle.setText(mLoadedNote.getnTitle());
               nEtContent.setText(mLoadedNote.getnContent());
           }

        }

        nEtContent.setSelection(0);

        titlefont = Typeface.createFromAsset(this.getAssets(), "fonts/fontmain.ttf");
        nEtTitle.setTypeface(titlefont);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_note_save:

                try {
                    saveNote();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;
            case R.id.action_note_color:
                if(nEtContent.getText().toString().trim().isEmpty()){
                    //Toast.makeText(this, "Please enter some Content.", Toast.LENGTH_SHORT).show();
                    //StyleableToast.makeText(this, "Please enter some Content.", R.style.notemaintoast).show();
                    Alerter.create(this)
                            .setTitle("Please enter some Content.")
                            .setBackgroundColorRes(R.color.alert_default_error_background)
                            .setIcon(R.mipmap.deldel)
                            .enableSwipeToDismiss()
                            .enableProgress(true)
                            .setProgressColorInt(getResources().getColor(R.color.googlered))
                            .setDuration(3000)
                            .show();
                    break;
                }
                    Alerter.create(this)
                            .setText("Sharing Note Content...")
                            .enableSwipeToDismiss()
                            .setBackgroundColorRes(R.color.googleblue)
                            .setIcon(R.drawable.share24dp)
                          //  .setTextTypeface(Typeface.createFromAsset(getAssets(), "fontmain.ttf"))
                            .show();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                  intent.setType("text/plain");
                  intent.putExtra(Intent.EXTRA_SUBJECT,   "" + nEtTitle.getText());
                  intent.putExtra(Intent.EXTRA_TEXT, ""+nEtContent.getText());
                  startActivity(intent.createChooser(intent, "Share Note Content via..."));
                     break;

            case R.id.action_note_delete:
                deleteNote();
                break;
        }

        return true;
    }




    private void saveNote() throws IOException {
        Note note;

        /*if (nEtContent.getText().toString().equals("") || nEtTitle.getText().toString().equals("")){
            Toast.makeText(this, "dismiss", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }*/
    /*    if (nEtContent.getText().toString().equals(mLoadedNote.getnContent()) || nEtTitle.getText().toString().equals(mLoadedNote.getnTitle())){
            Toast.makeText(this, "No changes made!", Toast.LENGTH_SHORT).show();
            return;
        }*/
        if(nEtTitle.getText().toString().trim().isEmpty()){
//            if (nEtContent.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter a Title.", Toast.LENGTH_SHORT).show();
           /* }else{
                nEtTitle.setText(nEtContent.getText());
                nEtContent.setText("");
                if(mLoadedNote  == null){
                    note  = new Note(System.currentTimeMillis(), nEtTitle.getText().toString(),
                            nEtContent.getText().toString());
                }else{
                    note  = new Note(mLoadedNote.getnDateTime(), nEtTitle.getText().toString(),
                            nEtContent.getText().toString());
                }

                if(  Utilities.saveNote(this, note)){
                    StyleableToast.makeText(this, "Your Note is Saved!", R.style.notemaintoast).show();


                }else {
                    Toast.makeText(this, "Could not save the note, Please make sure you have enough space on your device.", Toast.LENGTH_SHORT).show();
                }
                finish();

            }*/
           // StyleableToast.makeText(this, "Please enter a Title.", R.style.notemaintoast).show();
            return;
        }

        if(nEtContent.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Please enter some Content.", Toast.LENGTH_SHORT).show();
         //   StyleableToast.makeText(this, "Please enter some Content.", R.style.notemaintoast).show();

            return;
        }


        if(mLoadedNote  == null){
            note  = new Note(System.currentTimeMillis(), nEtTitle.getText().toString(),
                    nEtContent.getText().toString());
        }else{
            note  = new Note(mLoadedNote.getnDateTime(), nEtTitle.getText().toString(),
                    nEtContent.getText().toString());
        }

        if(  Utilities.saveNote(this, note)){

            StyleableToast.makeText(this, "Your Note is Saved!", R.style.notemaintoast).show();


        }else {

            Toast.makeText(this, "Could not save the note, Please make sure you have enough space on your device.", Toast.LENGTH_SHORT).show();
        }

        finish();



    }
    private void deleteNote() {
        if(mLoadedNote == null){
            //finish();
             Alerter.create(this)
                    .setTitle("Delete?")
                    .setBackgroundColorRes(R.color.alert_default_error_background)
                    .setIcon(R.mipmap.deldel)
                    .enableInfiniteDuration(true)
                    .show();


            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("Delete")
                    .setIcon(R.drawable.note700px)
                    // .setMessage("Are you sure to delete " +  nEtTitle.getText().toString()+"?")
                    .setMessage("Are you sure you don't want to save this note.")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Utilities.deleteNote(getApplicationContext(), Calendar.getInstance() +Utilities.FILE_EXTENSION);
                            Toast.makeText(NoteActivity.this, "The note was deleted.", Toast.LENGTH_SHORT).show();
                            Alerter.clearCurrent(NoteActivity.this);
                            finish();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Alerter.clearCurrent(NoteActivity.this);
                        }
                    })
                    .setCancelable(false);
            dialog.show();
        }else{
            Alerter.create(this)
                    .setTitle("Delete?")
                    .setBackgroundColorRes(R.color.alert_default_error_background)
                    .setIcon(R.mipmap.deldel)
                    .enableInfiniteDuration(true)
                    .show();

            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("Delete")
                    .setIcon(R.drawable.note700px)
                   // .setMessage("Are you sure to delete " +  nEtTitle.getText().toString()+"?")
                    .setMessage("Are you sure you want to delete this note?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Utilities.deleteNote(getApplicationContext(),mLoadedNote.getnDateTime()+Utilities.FILE_EXTENSION);
                            Toast.makeText(NoteActivity.this, nEtTitle.getText().toString()+" is deleted.", Toast.LENGTH_SHORT).show();
                            Alerter.clearCurrent(NoteActivity.this);
                            finish();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Alerter.clearCurrent(NoteActivity.this);
                        }
                    })
                    .setCancelable(false);
            dialog.show();





        }

    }
    public void onBackPressed() {
        if (nEtTitle.getText().toString().trim().isEmpty() == false || nEtContent.getText().toString().trim().isEmpty() == false){
            Alerter.create(this)
                    .setTitle("Unsaved Changes.")
                    .setBackgroundColorRes(R.color.alerter_default_success_background)
                    .setIcon(R.mipmap.save3)
                    .enableInfiniteDuration(true)
                    .show();
            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("Save Changes?")
                    .setIcon(R.drawable.note700px)
                    // .setMessage("Are you sure to delete " +  nEtTitle.getText().toString()+"?")
                    .setMessage("Do you want to save changes to the note?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                saveNote();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(NoteActivity.this, nEtTitle.getText().toString()+" is saved.", Toast.LENGTH_SHORT).show();
                            Alerter.clearCurrent(NoteActivity.this);
                            finish();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Alerter.clearCurrent(NoteActivity.this);
                            finish();
                        }
                    })
                    .setCancelable(true);
            dialog.show();
        }else{
            finish();
        }
    }
}
