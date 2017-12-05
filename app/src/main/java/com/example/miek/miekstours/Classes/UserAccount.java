package com.example.miek.miekstours.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MMART on 11/10/2017.
 */
public class UserAccount implements Parcelable {
    String id;
    String email;
    String password;
    String firstName;
    String lastName;
    String dob;
    String description;
    String location;
    String startDate;
    String endDate;
    int hostingStatus = 0;
    double rate = 0.0;
    double latitude = 0.0;
    double longitude = 0.0;
    ArrayList<String> interests = new ArrayList<String>();
    //
    public UserAccount() {

    }
    public UserAccount(String id, String email, String password, String firstName, String lastName, String dob,
                       String location, String description, int hostingStatus, double rate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.description = description;
        this.location = location;
        this.hostingStatus = hostingStatus;
        this.rate = rate;
    }
    public UserAccount(String jsonString, DatabaseHandler db) {
        try {
            JSONObject jObject = new JSONObject(jsonString);
            this.id = jObject.getString(db.KEY_USERID);
            this.email = jObject.getString(db.KEY_EMAIL);
            this.password = jObject.getString(db.KEY_PASSWORD);
            this.firstName = jObject.getString(db.KEY_FIRSTNAME);
            this.lastName = jObject.getString(db.KEY_LASTNAME);
            this.dob = jObject.getString(db.KEY_DOB);
            this.description = jObject.getString(db.KEY_DESCRIPTION);
            this.location = jObject.getString(db.KEY_LOCATION);
            this.hostingStatus = jObject.getInt(db.KEY_HOSTING);
            this.latitude = jObject.getDouble(db.KEY_LAT);
            this.longitude = jObject.getDouble(db.KEY_LONG);
            this.startDate = jObject.getString("startDate");
            this.endDate = jObject.getString("endDate");
            this.rate = jObject.getDouble(db.KEY_RATE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public UserAccount(Parcel in) {
        id = in.readString();
        email = in.readString();
        password = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        dob = in.readString();
        description = in.readString();
        hostingStatus = in.readInt();
        rate = in.readDouble();
        location = in.readString();
        startDate = in.readString();
        endDate = in.readString();
    }

    public static final Creator<UserAccount> CREATOR = new Creator<UserAccount>() {
        @Override
        public UserAccount createFromParcel(Parcel in) {
            return new UserAccount(in);
        }

        @Override
        public UserAccount[] newArray(int size) {
            return new UserAccount[size];
        }
    };

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getHostingStatus() {
        return hostingStatus;
    }
    public void setHostingStatus(int hostingStatus) {
        this.hostingStatus = hostingStatus;
    }
    public double getRate() {
        return rate;
    }
    public void setRate(double rate) {
        this.rate = rate;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitud) {
        this.longitude = longitud;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public ArrayList<String> getInterests() {
        return this.interests;
    }
    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(dob);
        dest.writeString(description);
        dest.writeInt(hostingStatus);
        dest.writeDouble(rate);
        dest.writeString(location);
        dest.writeString(startDate);
        dest.writeString(endDate);
    }
}
