package com.example.hp.notetaker;

import android.content.Context;
import android.content.Intent;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.tapadoo.alerter.Alerter;

import java.io.IOException;
import java.util.ArrayList;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class MainActivity extends AppCompatActivity {

    private ListView listViewNotes;
    LinearLayout quicknote;
    Button buttonSave, dismiss;
    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout bottomSheetLayout;
    EditText quickTitle, quickCont;
    TextView expandbs;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
    private ActionBarDrawerToggle drawerToggle;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        quicknote = (LinearLayout) findViewById(R.id.quicknote);

        listViewNotes = (ListView)findViewById(R.id.main_listview_notes);
        bottomSheetLayout = (LinearLayout) findViewById(R.id.bottomsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setHideable(false);

        buttonSave = (Button)findViewById(R.id.quicksave);
        dismiss = (Button)findViewById(R.id.quickdismiss);
        expandbs = (TextView)findViewById(R.id.expand);
        quickTitle = (EditText) findViewById(R.id.quick_title);
        quickCont = (EditText) findViewById(R.id.quick_content);
        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
       // setSupportActionBar(findViewById(R.id.toolbar)
        // This will display an Up icon (<-), we will replace it with hamburger later
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveQuick();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickTitle.setText("");
                quickCont.setText("");
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        expandbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                    closeOptionsMenu();
                }

                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

        });
        quicknote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NoteActivity.class));
            }
        });
        //closeKeyboard();
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    /*public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        fragmentClass = null;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
               // fragmentClass = FirstFragment.class;
                Toast.makeText(this, "First", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_second_fragment:
              //  fragmentClass = SecondFragment.class;
                Toast.makeText(this, "Second", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_third_fragment:
              //  fragmentClass = ThirdFragment.class;
                Toast.makeText(this, "Third", Toast.LENGTH_SHORT).show();
                break;
            /*default:
                fragmentClass = FirstFragment.class;
        }*/
 /*
        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
        */



    /*\public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
    private void saveQuick() throws IOException {

        Note note;
        SharedPreferences sharedpref = this.getSharedPreferences("app", Context.MODE_PRIVATE);
        int x = sharedpref.getInt("NoQuicknote",0);
        SharedPreferences.Editor editor = sharedpref.edit();
        x = x+1;
        editor.putInt("NoQuicknote", x);
        editor.apply();


        quickTitle.setText("Quick Note " + x);

        if(quickTitle.getText().toString().trim().isEmpty()){

            Toast.makeText(this, "Please enter a Title.", Toast.LENGTH_SHORT).show();

            return;
        }

        if(quickCont.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Please enter some Content.", Toast.LENGTH_SHORT).show();

            return;
        }


        note  = new Note(System.currentTimeMillis(), quickTitle.getText().toString(),
                    quickCont.getText().toString());


        if(  Utilities.saveNote(this, note)){

            StyleableToast.makeText(this, "Your Note is Saved!", R.style.notemaintoast).show();


        }else {

            Toast.makeText(this, "Could not save the note, Please make sure you have enough space on your device.", Toast.LENGTH_SHORT).show();
        }

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        quickTitle.setText("");
        quickCont.setText("");

        onResume();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main , menu);
        return true;
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_main_new_note:
                startActivity(new Intent(this, NoteActivity.class));
                break;
            case R.id.action_main_new_about:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                        .setTitle("About")
                        .setMessage("This program is made by Rajat Badaria for Syntax Error 2023 Hackathon.")
                        .setNegativeButton("Back", null)
                        .setCancelable(false);
                dialog.show();
              Alerter.create(this)
                      .setTitle("About")
                      .setText("This program is made by Rajat Badaria. Thank you for using my program.")
                      .setBackgroundColorRes(R.color.darkbg)
                      .setContentGravity(1)
                      .setIcon(R.mipmap.noteonly400)
                      .setDuration(5000)
                      .enableSwipeToDismiss()
                      //.setIconColorFilter(Color.WHITE)
                      .show();

        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        listViewNotes.setAdapter(null);


        ArrayList<Note> notes = Utilities.getAllSavedNotes(this);


        if(notes == null|| notes.size()==0){

            Alerter.create(this)
                    .setTitle("Note Taker")
                    .setText("No notes saved,Click to create one")
                    .setBackgroundColorRes(R.color.alerter_default_success_background)
                    .setDuration(2000)
                    .setIcon(R.mipmap.noteonly400)
                    .enableSwipeToDismiss()
                    .setProgressColorRes(R.color.colorPrimaryDark)
                    .enableProgress(true)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getApplicationContext(), NoteActivity.class));
                        }
                    })
                    .show();

            StyleableToast.makeText(this, "You have no notes saved!", R.style.notemaintoast).show();
            //Toast.makeText(this, "You have no notes saved!", Toast.LENGTH_SHORT).show();

            return;
        }else {
            NoteAdapter na =  new NoteAdapter(this, R.layout.item_note, notes);
            listViewNotes.setAdapter(na);

            listViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String fileName = ((Note)listViewNotes.getItemAtPosition(i)).getnDateTime()
                            +Utilities.FILE_EXTENSION;

                    Intent viewNoteIntent = new Intent(getApplicationContext(), NoteActivity.class);
                    viewNoteIntent.putExtra("NOTE_FILE", fileName);
                    startActivity(viewNoteIntent);


                }
            });

        }
    }

  /*  private void closeKeyboard() {
        // this will give us the view
        // which is currently focus
        // in this layout
        View view = this.getCurrentFocus();

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager

                    = (InputMethodManager)
                    getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }*/

    }
