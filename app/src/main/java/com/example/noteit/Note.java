package com.example.noteit;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes")
public class Note implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name="timestamp")
    private String timestamp;
    @ColumnInfo(name="priority rating")
    private int image;
    @ColumnInfo(name="content")
    private String content;

    @Ignore
    public Note() {

    }

    public Note(String title, String timestamp,int image,String content) {
        this.title = title;
        this.timestamp = timestamp;
        this.image = image;
        this.content=content;
    }

    protected Note(Parcel in) {
        id = in.readInt();
        title = in.readString();
        timestamp = in.readString();
        image = in.readInt();
        content = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", image=" + image +
                ", content='" + content + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }



   public String getContent()
   {
       return content;
   }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(timestamp);
        dest.writeInt(image);
        dest.writeString(content);
    }
}
