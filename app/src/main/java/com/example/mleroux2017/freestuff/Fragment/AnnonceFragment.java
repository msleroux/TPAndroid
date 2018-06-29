package com.example.mleroux2017.freestuff.Fragment;

import android.app.LauncherActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mleroux2017.freestuff.Objects.Annonce;
import com.example.mleroux2017.freestuff.R;
import com.example.mleroux2017.freestuff.dummy.DummyContent;
import com.google.common.collect.ObjectArrays;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AnnonceFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String LIST_ITEM ="liste item";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<Annonce> listItems;
      /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AnnonceFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AnnonceFragment newInstance(List<Annonce> listeResults) {
        AnnonceFragment fragment = new AnnonceFragment();
        Bundle args = new Bundle();

        args.putParcelable(LIST_ITEM, Parcels.wrap(listeResults));
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            listItems = Parcels.unwrap(getArguments().getParcelable(LIST_ITEM));
        }
        View view = inflater.inflate(R.layout.fragment_annonce_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            Log.i("DC", "onCreateView: "+listItems);
            recyclerView.setAdapter(new MyAnnonceRecyclerViewAdapter(listItems, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Annonce item);
    }


}
