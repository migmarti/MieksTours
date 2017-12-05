package com.example.miek.miekstours.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MMART on 12/5/2017.
 */
public class Requests implements Parcelable {

    String requestId;
    String hostId;
    String travelerId;
    String startDate;
    String endDate;
    String comment;
    String status = "0";

    public Requests(String jsonString) {
        try {
            JSONObject jObject = new JSONObject(jsonString);
            this.requestId = jObject.getString("RequestId");
            this.hostId = jObject.getString("RequestId");
            this.travelerId = jObject.getString("RequestId");
            this.startDate = jObject.getString("StartDate");
            this.endDate = jObject.getString("EndDate");
            this.comment = jObject.getString("Comment");
            this.status = jObject.getString("StatusId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected Requests(Parcel in) {
        requestId = in.readString();
        hostId = in.readString();
        travelerId = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        comment = in.readString();
        status = in.readString();
    }

    public static final Creator<Requests> CREATOR = new Creator<Requests>() {
        @Override
        public Requests createFromParcel(Parcel in) {
            return new Requests(in);
        }

        @Override
        public Requests[] newArray(int size) {
            return new Requests[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(requestId);
        dest.writeString(hostId);
        dest.writeString(travelerId);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(comment);
        dest.writeString(status);
    }

    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getTravelerId() {
        return travelerId;
    }
    public void setTravelerId(String travelerId) {
        this.travelerId = travelerId;
    }
    public String getHostId() {
        return hostId;
    }
    public void setHostId(String hostId) {
        this.hostId = hostId;
    }
    public String getRequestId() {
        return requestId;
    }
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
