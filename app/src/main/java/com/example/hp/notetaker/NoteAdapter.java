package com.example.hp.notetaker;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HP on 9/24/2017.
 */

public class NoteAdapter extends ArrayAdapter<Note> {
    public NoteAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Note> notes) {
        super(context, resource, notes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        if(convertView == null){
            convertView  = LayoutInflater.from(getContext()).inflate(R.layout.item_note, null);
        }
        Note note  = getItem(position);

        if (note != null){
            TextView title = (TextView) convertView.findViewById(R.id.List_note_title);
            TextView date = (TextView) convertView.findViewById(R.id.List_note_date);
            TextView content = (TextView) convertView.findViewById(R.id.List_note_content);

            title.setText(note.getnTitle());
            date.setText(note.getDateTimeFormatted(getContext()));
            if(note.getnContent().length()>50){
                content.setText(note.getnContent().substring(0,50));
            }else{
                content.setText(note.getnContent());
            }
        }
        return convertView;
    }
}
