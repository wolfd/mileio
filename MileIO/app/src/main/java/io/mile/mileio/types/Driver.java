package io.mile.mileio.types;

import android.os.Parcel;
import android.os.Parcelable;

public class Driver implements Parcelable {
    private String displayName;
    private String email;
    private String uid;

    public Driver(String displayName, String email, String uid) {
        this.displayName = displayName;
        this.email = email;
        this.uid = uid;
    }

    protected Driver(Parcel in) {
        displayName = in.readString();
        email = in.readString();
        uid = in.readString();
    }

    public static final Creator<Driver> CREATOR = new Creator<Driver>() {
        @Override
        public Driver createFromParcel(Parcel in) {
            return new Driver(in);
        }

        @Override
        public Driver[] newArray(int size) {
            return new Driver[size];
        }
    };

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayName);
        dest.writeString(email);
        dest.writeString(uid);
    }
}
