package com.applications.jay_letheby.jaycad.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.applications.jay_letheby.jaycad.Activities.MainActivity;
import com.applications.jay_letheby.jaycad.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AngleConversionFragment.AngleConversionInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AngleConversionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AngleConversionFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button convertBtn;
    private Button clearBtn;
    private Button mainMenuBtn;

    private EditText degreesTxt;
    private EditText degreesMinutesTxt;
    private EditText degreesSecondTxt;
    private EditText degreesDecimalTxt;

    private AngleConversionInteractionListener mListener;

    public AngleConversionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AngleConversionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AngleConversionFragment newInstance(String param1, String param2) {
        AngleConversionFragment fragment = new AngleConversionFragment();
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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_angle_conversion, container, false);

        // Initialise Buttons
        convertBtn = (Button)view.findViewById(R.id.convertBtn);
        clearBtn = (Button)view.findViewById(R.id.clearBtn);
        mainMenuBtn = (Button)view.findViewById(R.id.mainMenuBtn);

        //Initilise TextFields
        degreesTxt = (EditText)view.findViewById(R.id.degreeTxt);
        degreesMinutesTxt = (EditText)view.findViewById(R.id.degreesMinTxt);
        degreesSecondTxt = (EditText)view.findViewById(R.id.degreesSecondsTxt);
        degreesDecimalTxt = (EditText)view.findViewById(R.id.decimalDegreesTxt);

        mainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Return to the main menu
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.loadMainMenuScreen();
            }
        });

        convertBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        Button chosenBtn = (Button)view;

        // Load Fragment according to which button is pressed in the Main Menu
        if (chosenBtn == convertBtn){
            convertAngle();
        } else if (clearBtn == clearBtn){
            clearTextFields();
        }

    }

    public void convertAngle(){

        String degrees = degreesTxt.getText().toString().trim();
        String minutes = degreesMinutesTxt.getText().toString().trim();
        String seconds = degreesSecondTxt.getText().toString().trim();
        String decimalDegrees = degreesDecimalTxt.getText().toString().trim();

        if (degrees.length() > 0 | minutes.length() > 0 | seconds.length() > 0) {
            convertDegMinSecToDecimal(degrees, minutes, seconds);
        }else if (decimalDegrees.length() > 0) {
            convertDecimalToDegMinSec(decimalDegrees);
        }
    }


    public void convertDegMinSecToDecimal (String degrees, String minutes, String seconds){

        // Check data entered is numbers just in case
        try{
            double deg = Double.parseDouble(degrees);
            double min = Double.parseDouble(minutes);
            double sec = Double.parseDouble(seconds);

            double decimalDegrees = deg + (min/60) + (sec/3600);
            degreesDecimalTxt.setText((decimalDegrees + ""));

        } catch (NumberFormatException e) {
            // not an double!
            degreesDecimalTxt.setText("0");
        }

    }

    public void convertDecimalToDegMinSec(String decimalDegrees) {

        // Check data entered is numbers just in case
        try{
            // Get the whole degrees value from the decimal value
            double decimalValue = Double.parseDouble(decimalDegrees);
            int degrees = (int)decimalValue;
            degreesTxt.setText(degrees + "");

            // Get the whole minutes value from the decimal value
            double calcMinutesValue = ((decimalValue - (double)degrees) * 60 );
            int minutes = (int)calcMinutesValue;
            degreesMinutesTxt.setText(minutes + "");

            // Calculate seconds
            double seconds = ((calcMinutesValue - (double)minutes)) * 60;
            degreesSecondTxt.setText(seconds + "");

        } catch (NumberFormatException e) {
            // not an double!
            degreesDecimalTxt.setText("0");
        }

    }

    public void clearTextFields (){

        degreesTxt.setText("");
        degreesMinutesTxt.setText("");
        degreesSecondTxt.setText("");
        degreesDecimalTxt.setText("");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAngleConversionInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AngleConversionInteractionListener) {
            mListener = (AngleConversionInteractionListener) context;
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
    public interface AngleConversionInteractionListener {
        // TODO: Update argument type and name
        void onAngleConversionInteraction(Uri uri);
    }
}
