package com.example.mleroux2017.freestuff.Fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mleroux2017.freestuff.Fragment.AnnonceFragment.OnListFragmentInteractionListener;
import com.example.mleroux2017.freestuff.Objects.Annonce;
import com.example.mleroux2017.freestuff.R;

import java.util.List;

public class MyAnnonceRecyclerViewAdapter extends RecyclerView.Adapter<MyAnnonceRecyclerViewAdapter.ViewHolder> {

    private final List<Annonce> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyAnnonceRecyclerViewAdapter(List<Annonce> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_annonce, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mEtatView.setText(mValues.get(position).getEtatArticle());
        holder.mContentView.setText(mValues.get(position).getTitre());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mEtatView;
        public final TextView mContentView;
        public Annonce mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView)view.findViewById(R.id.item_titre);
            mEtatView = (TextView)view.findViewById(R.id.item_etat);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
