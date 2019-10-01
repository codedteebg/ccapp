package com.babbangona.barcodescannerproject.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "msaT", primaryKeys = "staff_id")
public class msaT {

    @ColumnInfo(name = "staff_id")
    @NonNull
    private String staff_id;

    @ColumnInfo(name ="fullname")
    @NonNull
    private String fullname;

    @ColumnInfo(name = "facetemplate")
    @NonNull
    private String facetemplate;

    public msaT(String staff_id, String fullname, String facetemplate){
        this.staff_id = staff_id;
        this.fullname = fullname;
        this.facetemplate = facetemplate;
    }

    @NonNull
    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(@NonNull String staff_id) {
        this.staff_id = staff_id;
    }

    @NonNull
    public String getFullname() {
        return fullname;
    }

    public void setFullname(@NonNull String fullname) {
        this.fullname = fullname;
    }

    @NonNull
    public String getFacetemplate() {
        return facetemplate;
    }

    public void setFacetemplate(@NonNull String facetemplate) {
        this.facetemplate = facetemplate;
    }

}
