package io.mile.mileio.types;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Trip implements Parcelable {
    private final Date whenStarted;
    private final Date whenEnded;
    private final Driver driver;
    private final Car car;
    private final ArrayList<LatLng> path;
    private final double distance; // meters

    public Trip(Date whenStarted, Date whenEnded, Driver driver, Car car, ArrayList<LatLng> path, double distance) {
        this.whenStarted = whenStarted;
        this.whenEnded = whenEnded;
        this.driver = driver;
        this.car = car;
        this.path = path;
        this.distance = distance;
    }

    protected Trip(Parcel in) {
        whenStarted = (Date) in.readSerializable();
        whenEnded = (Date) in.readSerializable();
        driver = in.readParcelable(Driver.class.getClassLoader());
        car = in.readParcelable(Car.class.getClassLoader());
        path = in.createTypedArrayList(LatLng.CREATOR);
        distance = in.readDouble();
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    public Date getWhenStarted() {
        return whenStarted;
    }

    public Date getWhenEnded() {
        return whenEnded;
    }

    public Driver getDriver() {
        return driver;
    }

    public Car getCar() {
        return car;
    }

    public ArrayList<LatLng> getPath() {
        return path;
    }

    public double getDistance() {
        return distance;
    }

    @Exclude
    @Override
    public int describeContents() {
        return 0;
    }

    @Exclude
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(whenStarted);
        dest.writeSerializable(whenEnded);
        dest.writeParcelable(driver, 0);
        dest.writeParcelable(car, 0);
        dest.writeTypedList(path);
        dest.writeDouble(distance);
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("whenStarted", whenStarted.getTime());
        result.put("whenEnded", whenEnded.getTime());

        result.put("driver", driver.getUid());
        result.put("car", car.getUid());

        result.put("path", path);
        result.put("distance", distance);

        return result;
    }
}
