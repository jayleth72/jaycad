package com.applications.jay_letheby.jaycad.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.applications.jay_letheby.jaycad.Activities.MainActivity;
import com.applications.jay_letheby.jaycad.HelperClasses.Angle;
import com.applications.jay_letheby.jaycad.HelperClasses.DataInputChecker;
import com.applications.jay_letheby.jaycad.HelperClasses.InputFilterMinMax;
import com.applications.jay_letheby.jaycad.HelperClasses.CustomTextWatcher;
import com.applications.jay_letheby.jaycad.HelperClasses.DoubleRangeInputFilter;
import com.applications.jay_letheby.jaycad.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DecimalAngleConversionFragment.DecimalAngleConversionFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DecimalAngleConversionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DecimalAngleConversionFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button mainMenuBtn;
    private Button convertBtn;
    private Button clearBtn;

    private EditText degreesTxt;
    private EditText minutesTxt;
    private EditText secondsTxt;

    private TextView decimalDegTxtView;

    private Angle conversionAngle;
    private DataInputChecker dataInputChecker;
    private InputFilterMinMax inputFilterMinMax;

    private DecimalAngleConversionFragmentInteractionListener mListener;

    public DecimalAngleConversionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DecimalAngleConversionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DecimalAngleConversionFragment newInstance(String param1, String param2) {
        DecimalAngleConversionFragment fragment = new DecimalAngleConversionFragment();
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

        View view = inflater.inflate(R.layout.fragment_decimal_angle_conversion, container, false);

        // initlise buttons
        mainMenuBtn = (Button)view.findViewById(R.id.mainMenuBtn);
        clearBtn = (Button)view.findViewById(R.id.clearBtn);
        convertBtn = (Button)view.findViewById(R.id.convertBtn);

        // initalise EditText boxes
        degreesTxt = (EditText)view.findViewById(R.id.degreeTxt);
        minutesTxt= (EditText)view.findViewById(R.id.degreesMinTxt);
        secondsTxt = (EditText)view.findViewById(R.id.degreesSecondsTxt);

        // initialise TextView results
        decimalDegTxtView = (TextView)view.findViewById(R.id.decimalAngleResultTxtView);

        // Set Button listeners
        mainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Return to the main menu
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.loadMainMenuScreen();
            }
        });

        clearBtn.setOnClickListener(this);
        convertBtn.setOnClickListener(this);

        dataInputChecker = new DataInputChecker();

        // Limit the number of digits to enter
        degreesTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(3)});
        minutesTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});


        // Limit the number range to 0-59 for minutes and seconds fields
        minutesTxt.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});
        secondsTxt.setFilters(new DoubleRangeInputFilter[]{new DoubleRangeInputFilter(0.0, 59.9)});

        //Limit Seconds to two figures before decimal place and one after decimal place
        secondsTxt.addTextChangedListener(new CustomTextWatcher(secondsTxt));

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {

        Button chosenBtn = (Button)view;

        // Load Fragment according to which button is pressed
        if (chosenBtn == convertBtn){
            convertAngle();
        } else if (clearBtn == clearBtn){
            clearTextFields();
        }

    }

    public void convertAngle() {
        // Convert Angle to decimal angle
        decimalDegTxtView.setText("");

        String degrees = degreesTxt.getText().toString().trim();
        String minutes = minutesTxt.getText().toString().trim();
        String seconds = secondsTxt.getText().toString().trim();

        String []degMin = {degrees, minutes};
        String []secondsArray = {seconds};

        // Check for degrees minutes and seconds input
        if (degrees.length() > 0 | minutes.length() > 0 | seconds.length() > 0) {
            // Convert to decimal

            //Check for non-numerical data
            if (dataInputChecker.isNotNumericData(degMin, "Integer") | dataInputChecker.isNotNumericData(secondsArray, "Double")){
                // SHow error message because non-numeric data entered
                nonNumericalDataEntered();
            } else {
                // Data is numerical, Convert to decimal
                // Assign zero to empty fields
                degrees = (degrees.length() > 0) ? degrees : "0";
                minutes = (minutes.length() > 0) ? minutes : "0";
                seconds = (seconds.length() > 0) ? seconds : "0";

                conversionAngle = new Angle();
                conversionAngle.setAllValues(degrees, minutes, seconds);
                decimalDegTxtView.setText(conversionAngle.getDecimalAngle() + "");
            }

        } else {
            // Show no data entered message
            noDataEntered();
        }
    }

    public void clearTextFields (){
        // CLear all fields
        degreesTxt.setText("");
        secondsTxt.setText("");
        minutesTxt.setText("");

        decimalDegTxtView.setText("");
    }

    public void noDataEntered (){

        // Alert message for no data entered
        AlertDialog.Builder a_builder = new AlertDialog.Builder(getActivity());
        a_builder.setMessage(R.string.dialog_no_data_message_angle_conversion)
                .setTitle(R.string.dialog_no_data_title_angle_conversion)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked OK button
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.show();
    }

    public void nonNumericalDataEntered (){

        // Alert message for no data entered
        AlertDialog.Builder a_builder = new AlertDialog.Builder(getActivity());
        a_builder.setMessage(R.string.dialog_non_numerical_data_message)
                .setTitle(R.string.dialog_non_numerical_data_title)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked OK button
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDecimalAngleConversionFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DecimalAngleConversionFragmentInteractionListener) {
            mListener = (DecimalAngleConversionFragmentInteractionListener) context;
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
    public interface DecimalAngleConversionFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDecimalAngleConversionFragmentInteraction(Uri uri);
    }
}
