package com.example.hp.notetaker;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by HP on 9/23/2017.
 */

public class Note implements Serializable {
    private  long nDateTime;
    private String nTitle;
    private String nContent;

    public Note(long nDateTime, String nTitle, String nContent) {
        this.nDateTime = nDateTime;
        this.nTitle = nTitle;
        this.nContent = nContent;
    }

    public void setnDateTime(long nDateTime) {
        this.nDateTime = nDateTime;
    }

    public void setnTitle(String nTitle) {
        this.nTitle = nTitle;
    }

    public void setnContent(String nContent) {
        this.nContent = nContent;
    }

    public long getnDateTime() {
        return nDateTime;
    }

    public String getnTitle() {
        return nTitle;
    }

    public String getnContent() {
        return nContent;
    }

    public String getDateTimeFormatted(Context context){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", context.getResources().getConfiguration().locale);
        sdf.setTimeZone(TimeZone.getDefault());
        return  sdf.format(new Date(nDateTime));
    }
}
