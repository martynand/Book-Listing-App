package com.example.martyna.scholarshipproject_booklistingapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
    /**
     * Book title
     */
    private String mTitle;
    /**
     * Book author
     */
    private String mAuthor;
    /**
     * Publish date
     */
    private String mDate;
    /**
     * Image URL
     */
    private String mImageUrl;
    /**
     * Book URL
     */
    private String mBookUrl;

    /**
     * Create a new Book object from five inputs
     *
     * @param title    is the title of the book
     * @param author   is the author of the book
     * @param date     is the date of the book
     * @param imageUrl is the image URL of the book
     * @param bookUrl  is the URL of the book
     */
    public Book(String title, String author, String date, String imageUrl, String bookUrl) {
        mTitle = title;
        mAuthor = author;
        mDate = date;
        mImageUrl = imageUrl;
        mBookUrl = bookUrl;
    }

    protected Book(Parcel in) {
        mTitle = in.readString();
        mAuthor = in.readString();
        mDate = in.readString();
        mImageUrl = in.readString();
        mBookUrl = in.readString();
    }

    /**
     * Get the title of the book
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Get the author of the book
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * Get the date of the book
     */
    public String getDate() {
        return mDate;
    }

    /**
     * Get the image URL of the book
     */
    public String getImageUrl() {
        return mImageUrl;
    }

    /**
     * Get the URL of the book
     */
    public String getBookUrl() {
        return mBookUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mAuthor);
        dest.writeString(mDate);
        dest.writeString(mImageUrl);
        dest.writeString(mBookUrl);
    }

}
