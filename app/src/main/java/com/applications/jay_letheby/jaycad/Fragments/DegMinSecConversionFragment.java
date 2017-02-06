package com.applications.jay_letheby.jaycad.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.applications.jay_letheby.jaycad.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DegMinSecConversionFragment.DegMinSecConversionFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DegMinSecConversionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DegMinSecConversionFragment extends Fragment implements View.OnClickListener{
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

    private EditText decimalDegreesTxt;

    private TextView degreesTxtView;
    private TextView  minutesTxtView;
    private TextView  secondsTxtView;

    private Angle conversionAngle;
    private DataInputChecker dataInputChecker;
    private InputFilterMinMax inputFilterMinMax;

    private DegMinSecConversionFragmentInteractionListener mListener;

    public DegMinSecConversionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DegMinSecConversionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DegMinSecConversionFragment newInstance(String param1, String param2) {
        DegMinSecConversionFragment fragment = new DegMinSecConversionFragment();
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

        View view = inflater.inflate(R.layout.fragment_deg_min_sec_conversion, container, false);


        // initlise buttons
        mainMenuBtn = (Button)view.findViewById(R.id.mainMenuBtn);
        clearBtn = (Button)view.findViewById(R.id.clearBtn);
        convertBtn = (Button)view.findViewById(R.id.convertBtn);

        // initialise EditText fields
        decimalDegreesTxt = (EditText) view.findViewById(R.id.decimalDegreesTxt);

        // initalise TextView boxes
        degreesTxtView = (TextView) view.findViewById(R.id.degreeTxtView);
        minutesTxtView= (TextView) view.findViewById(R.id.degreesMinTxtView);
        secondsTxtView = (TextView) view.findViewById(R.id.degreesSecondsTxtView);

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

    public void convertAngle () {

        String decimalDegrees = decimalDegreesTxt.getText().toString().trim();

        String []decimalArray = {decimalDegrees};

       if (decimalDegrees.length() > 0) {
            //Check for non-numerical data
            if (dataInputChecker.isNotNumericData(decimalArray, "Double")) {
                // SHow error message because non-numeric data entered
                nonNumericalDataEntered();
            } else {
                // Data is numerical, Convert to degrees, minutes seconds
                conversionAngle = new Angle();
                conversionAngle.setDecimalAngle(Double.parseDouble(decimalDegrees));
                degreesTxtView.setText(conversionAngle.getDegrees() + "");
                minutesTxtView.setText(conversionAngle.getMinutes() + "");
                secondsTxtView.setText(conversionAngle.getDecimalSeconds() + "");

            }
       } else {
           // Show no data entered message
           noDataEntered();
       }
    }

    public void clearTextFields (){
        // CLear all fields
        degreesTxtView.setText("");
        secondsTxtView.setText("");
        minutesTxtView.setText("");

        decimalDegreesTxt.setText("");
    }

    public void noDataEntered (){

        // Alert message for no data entered
        AlertDialog.Builder a_builder = new AlertDialog.Builder(getActivity());
        a_builder.setMessage(R.string.dialog_no_data_message_angle_conversion_decimal)
                .setTitle(R.string.dialog_no_data_title_angle_conversion_decimal)
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
            mListener.onDegMinSecConversionFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DegMinSecConversionFragmentInteractionListener) {
            mListener = (DegMinSecConversionFragmentInteractionListener) context;
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
    public interface DegMinSecConversionFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDegMinSecConversionFragmentInteraction(Uri uri);
    }
}
