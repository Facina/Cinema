package com.example.android.cinemusp;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



public class Movie implements Serializable{


    private String mName;

    @Override
    public String toString() {
        return "Movie{" +
                "mName='" + mName + '\'' +
                ", mSinopse='" + mSinopse + '\'' +
                ", mImgLink='" + mImgLink + '\'' +
                ", mRating='" + mRating + '\'' +
                ", mDuration='" + mDuration + '\'' +
                ", mMainActor='" + mMainActor + '\'' +
                ", mDirector='" + mDirector + '\'' +
                ", mtrailer='" + mtrailer + '\'' +
                ", mReleaseDate='" + mReleaseDate + '\'' +
                '}';
    }

    private String mSinopse;
    private String mImgLink;
    private String mRating;

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    private String mType;


    Movie(String serialized){


    }


    private String mDuration;
    private String mMainActor;

    public String getmMainActor() {
        return mMainActor;
    }

    public void setmMainActor(String mMainActor) {
        this.mMainActor = mMainActor;
    }

    public String getmDirector() {
        return mDirector;
    }

    public void setmDirector(String mDirector) {
        this.mDirector = mDirector;
    }

    public String getMtrailer() {
        return mtrailer;
    }

    public void setMtrailer(String mtrailer) {
        this.mtrailer = mtrailer;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    private String mDirector;
    private String mtrailer;
    private String mReleaseDate;


    Movie(String name, String sinopse, String imgLink, String rating,String duration){
        mImgLink = imgLink;
        mSinopse = sinopse;
        mName = name;
        mRating = rating;
        mDuration = duration;

    }

    Movie(String name, String sinopse, String imgLink, String rating,String duration,String release,String director ,String actor,String trailer,String type){
        mImgLink = imgLink;
        mSinopse = sinopse;
        mName = name;
        mRating = rating;
        mDuration = duration;
        mDirector = director;
        mReleaseDate = release;
        mtrailer = trailer;
        mMainActor = actor;
        mType = type;

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSinopse() {
        return mSinopse;
    }

    public void setSinopse(String sinopse) {
        mSinopse = sinopse;
    }

    public String getImgLink() {
        return mImgLink;
    }

    public void setImgLink(String imgLink) {
        mImgLink = imgLink;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }





}
