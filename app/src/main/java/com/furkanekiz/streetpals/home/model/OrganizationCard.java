
package com.furkanekiz.streetpals.home.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OrganizationCard implements Parcelable {

    public static final Creator<OrganizationCard> CREATOR = new Creator<OrganizationCard>() {
        @Override
        public OrganizationCard createFromParcel(Parcel in) {
            return new OrganizationCard(in);
        }

        @Override
        public OrganizationCard[] newArray(int size) {
            return new OrganizationCard[size];
        }
    };
    private static final String TAG = OrganizationCard.class.getSimpleName();
    private String title;
    private String desc;
    private String poster;
    private String url;
    private String director;
    private String year;
    private Type type;

    public OrganizationCard() {
    }

    private OrganizationCard(Parcel in) {
        title = in.readString();
        desc = in.readString();
        poster = in.readString();
        director = in.readString();
        year = in.readString();
        url = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(poster);
        dest.writeString(url);
        dest.writeString(year);
        dest.writeString(director);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public enum Type {
        ORGANIZATION;

        Type() {
        }
    }
}
