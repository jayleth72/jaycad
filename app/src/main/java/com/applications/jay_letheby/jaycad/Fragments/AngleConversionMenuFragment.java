package com.applications.jay_letheby.jaycad.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.applications.jay_letheby.jaycad.Activities.MainActivity;
import com.applications.jay_letheby.jaycad.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AngleConversionMenuFragment.AngleConversionMenuFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AngleConversionMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AngleConversionMenuFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button mainMenuBtn;
    private Button decimalConversionBtn;
    private Button degMinSecConversionBtn;

    private AngleConversionMenuFragmentInteractionListener mListener;

    public AngleConversionMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AngleConversionMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AngleConversionMenuFragment newInstance(String param1, String param2) {
        AngleConversionMenuFragment fragment = new AngleConversionMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_angle_conversion_menu, container, false);
        mainMenuBtn = (Button)view.findViewById(R.id.mainMenuBtn);
        decimalConversionBtn = (Button)view.findViewById(R.id.decimalConversionsBtn);
        degMinSecConversionBtn = (Button)view.findViewById(R.id.degMinSecConversionBtn);

        mainMenuBtn.setOnClickListener(this);
        decimalConversionBtn.setOnClickListener(this);
        degMinSecConversionBtn.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {

        MainActivity mainActivity = (MainActivity)getActivity();
        Button chosenBtn = (Button)view;

        // Load Fragment according to which button is pressed in the Main Menu
        mainActivity.loadFragment(chosenBtn.getText().toString().toLowerCase());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAngleConversionMenuFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AngleConversionMenuFragmentInteractionListener) {
            mListener = (AngleConversionMenuFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface AngleConversionMenuFragmentInteractionListener {
        // TODO: Update argument type and name
        void onAngleConversionMenuFragmentInteraction(Uri uri);
    }
}
