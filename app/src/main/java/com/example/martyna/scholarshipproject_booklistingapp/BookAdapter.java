package com.example.martyna.scholarshipproject_booklistingapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {

    /**
     * ViewHolder for fields of the Book.
     */
    static class ViewHolder {
        private ImageView posterImageView;
        private TextView titleTextView;
        private TextView authorTextView;
        private TextView dateTextView;
    }

    /**
     * @param context The current context. Used to inflate the layout file.
     * @param books   A List of Book objects to display in a list.
     */

    public BookAdapter(Activity context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    /**
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        final Book currentBook = getItem(position);
        ViewHolder holder;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

            holder = new ViewHolder();
            holder.titleTextView = (TextView) listItemView.findViewById(R.id.book_title);
            holder.authorTextView = (TextView) listItemView.findViewById(R.id.book_author);
            holder.dateTextView = (TextView) listItemView.findViewById(R.id.book_date);
            holder.posterImageView = (ImageView) listItemView.findViewById(R.id.book_image);
            listItemView.setTag(holder);

        } else {
            holder = (ViewHolder) listItemView.getTag();
        }

        holder.titleTextView.setText(currentBook.getTitle());
        holder.authorTextView.setText(currentBook.getAuthor());
        holder.dateTextView.setText(currentBook.getDate());

        Picasso.with(getContext()).setLoggingEnabled(true);

        Picasso.with(getContext())
                .load(currentBook.getImageUrl())
                .placeholder(R.drawable.my_placeholder)
                .error(R.drawable.my_placeholder_error)
                .into(holder.posterImageView);

        return listItemView;
    }
}
