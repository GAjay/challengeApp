package com.cambium.challenge.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

/**
 * A models class of project deitails
 * @author Ajay Kumar Maheshwari
 */

public class ProjectDetails implements Parcelable {

    @SerializedName("s.no")
    private Integer sNo;
    @SerializedName("amt.pledged")
    private String amtPledged;
    @SerializedName("blurb")
    private String blurb;
    @SerializedName("by")
    private String by;
    @SerializedName("country")
    private String country;
    @SerializedName("currency")
    private String currency;
    @SerializedName("end.time")
    private String endTime;
    @SerializedName("location")
    private String location;
    @SerializedName("percentage.funded")
    private float percentageFunded;
    @SerializedName("num.backers")
    private String numBackers;
    @SerializedName("state")
    private String state;
    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private String type;
    @SerializedName("url")
    private String url;

    protected ProjectDetails(Parcel in) {
        amtPledged = in.readString();
        blurb = in.readString();
        by = in.readString();
        country = in.readString();
        currency = in.readString();
        endTime = in.readString();
        location = in.readString();
        numBackers = in.readString();
        state = in.readString();
        title = in.readString();
        type = in.readString();
        url = in.readString();
    }

    public static final Creator<ProjectDetails> CREATOR = new Creator<ProjectDetails>() {
        @Override
        public ProjectDetails createFromParcel(Parcel in) {
            return new ProjectDetails(in);
        }

        @Override
        public ProjectDetails[] newArray(int size) {
            return new ProjectDetails[size];
        }
    };
    public String getAmtPledged() {
        return amtPledged;
    }



    public String getBlurb() {
        return blurb;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getCountry() {
        return country;
    }

    public String getCurrency() {
        return currency;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }

    public float getPercentageFunded() {
        return percentageFunded;
    }

    public String getNumBackers() {
        return numBackers;
    }

    public String getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(amtPledged);
        parcel.writeString(blurb);
        parcel.writeString(by);
        parcel.writeString(country);
        parcel.writeString(currency);
        parcel.writeString(endTime);
        parcel.writeString(location);
        parcel.writeString(numBackers);
        parcel.writeString(state);
        parcel.writeString(title);
        parcel.writeString(type);
        parcel.writeString(url);
    }

    /*Comparator for sorting the list by project asc list*/
    public static Comparator<ProjectDetails> aTozCompare = new Comparator<ProjectDetails>() {
        @Override
        public int compare(ProjectDetails pD1, ProjectDetails pD2) {
            String title1= pD1.getTitle().toLowerCase();
            String title2= pD2.getTitle().toLowerCase();

            return title1.compareTo(title2);
        }
    };

    /*Comparator for sorting the list by project desc title*/
    public static Comparator<ProjectDetails> zToaCompare = new Comparator<ProjectDetails>() {
        @Override
        public int compare(ProjectDetails pD1, ProjectDetails pD2) {
            String title1= pD1.getTitle().toLowerCase();
            String title2= pD2.getTitle().toLowerCase();

            return title2.compareTo(title1);
        }
    };

    /*Comparator for sorting the list by project asc time*/
    public static Comparator<ProjectDetails> timeAsc = new Comparator<ProjectDetails>() {
        @Override
        public int compare(ProjectDetails pD1, ProjectDetails pD2) {
            String title1= pD1.getEndTime();
            String title2= pD2.getEndTime();

            return title1.compareTo(title2);
        }
    };

    /*Comparator for sorting the list by project desc time*/
    public static Comparator<ProjectDetails> timeDesc = new Comparator<ProjectDetails>() {
        @Override
        public int compare(ProjectDetails pD1, ProjectDetails pD2) {
            String title1= pD1.getEndTime();
            String title2= pD2.getEndTime();

            return title2.compareTo(title1);
        }
    };


}
