package com.example.martyna.scholarshipproject_booklistingapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    /**
     * Tag for log messages
     */
    public static final String LOG_TAG = MainActivity.class.getName();

    /**
     * URL for books data from Google Books API
     */
    private static final String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?maxResults=30&orderBy=newest&q=";

    /**
     * Constant value for the book loader ID
     */
    private static final int BOOK_LOADER_ID = 1;

    /**
     * Adapter for the list of books
     */
    private BookAdapter mBookAdapter;

    /**
     * SearchView that takes the query
     */
    private SearchView searchView;

    /**
     * List of books
     */
    private ListView booksListView;

    /**
     * Value for search query
     */
    private String mQuery;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    /**
     * ProgressBar that is displayed when the data is loaded
     */
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link SearchView} in the layout
        searchView = (SearchView) findViewById(R.id.search_view);

        // Find a reference to the {@link ListView} in the layout
        booksListView = (ListView) findViewById(R.id.books_list);

        // Create a new adapter that takes an empty list of books as input
        mBookAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        booksListView.setAdapter(mBookAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected book.
        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Book currentBook = mBookAdapter.getItem(position);
                Uri bookUri = Uri.parse(currentBook.getBookUrl());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, bookUri);
                startActivity(webIntent);
            }
        });

        // Find the reference to the progress bar in a layout
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        // Find the reference to the empty text view in a layout and set empty view
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        booksListView.setEmptyView(mEmptyStateTextView);


        if (isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);

        } else {
            mProgressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (isConnected()) {
                    booksListView.setVisibility(View.INVISIBLE);
                    mEmptyStateTextView.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.VISIBLE);
                    mQuery = searchView.getQuery().toString();
                    mQuery = mQuery.replace(" ", "+");
                    Log.v(LOG_TAG, mQuery);
                    getLoaderManager().restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                    searchView.clearFocus();
                } else {
                    booksListView.setVisibility(View.INVISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                    mEmptyStateTextView.setVisibility(View.VISIBLE);
                    mEmptyStateTextView.setText(R.string.no_internet);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    // Helper method to check network connection
    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        String requestUrl = "";
        if (mQuery != null && mQuery != "") {
            requestUrl = BOOK_REQUEST_URL + mQuery;
        } else {
            String defaultQuery = "android";
            requestUrl = BOOK_REQUEST_URL + defaultQuery;
        }
        return new BookLoader(this, requestUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        mEmptyStateTextView.setText(R.string.no_books);
        mProgressBar.setVisibility(View.GONE);
        mBookAdapter.clear();

        if (books != null && !books.isEmpty()) {
            mBookAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mBookAdapter.clear();
    }

}