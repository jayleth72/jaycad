package com.applications.jay_letheby.jaycad.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.applications.jay_letheby.jaycad.Activities.MainActivity;
import com.applications.jay_letheby.jaycad.R;
import com.applications.jay_letheby.jaycad.HelperClasses.Angle;
import com.applications.jay_letheby.jaycad.HelperClasses.DataInputChecker;
import com.applications.jay_letheby.jaycad.HelperClasses.InputFilterMinMax;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AngleAddSubtractFragment.AngleAddSubtractFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AngleAddSubtractFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AngleAddSubtractFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Buttons
    private Button clear1Btn;
    private Button clear2Btn;
    private Button clearResulsBtn;
    private Button addBtn;
    private Button subtractBtn;
    private Button mainMenuBtn;

    // Edit Text fields
    private EditText degrees1Txt;
    private EditText minutes1Txt;
    private EditText seconds1Txt;
    private EditText degrees2Txt;
    private EditText minutes2Txt;
    private EditText seconds2Txt;

    // Text View Fields
    private TextView resultsTxtView;

    // Varaibles to hold user input data
    private String degrees1;
    private String minutes1;
    private String seconds1;
    private String degrees2;
    private String minutes2;
    private String seconds2;

    // Angle objects to hold user input and perform Angle math operations
    Angle angle1;
    Angle angle2;
    DataInputChecker inputChecker;

    private AngleAddSubtractFragmentInteractionListener mListener;

    public AngleAddSubtractFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AngleAddSubtractFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AngleAddSubtractFragment newInstance(String param1, String param2) {
        AngleAddSubtractFragment fragment = new AngleAddSubtractFragment();
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

        View view = inflater.inflate(R.layout.fragment_angle_add_subtract, container, false);

        // Button initialisation
        clear1Btn = (Button)view.findViewById(R.id.clear1Btn);
        clear2Btn = (Button)view.findViewById(R.id.clear2Btn);
        clearResulsBtn = (Button)view.findViewById(R.id.clear3Btn);
        addBtn = (Button)view.findViewById(R.id.addBtn);
        subtractBtn = (Button)view.findViewById(R.id.subtractBtn);
        mainMenuBtn = (Button)view.findViewById(R.id.mainMenuBtn);

        // Edit Text initialisation
        degrees1Txt = (EditText)view.findViewById(R.id.degrees1Txt);
        minutes1Txt = (EditText)view.findViewById(R.id.minutes1Txt);
        seconds1Txt = (EditText)view.findViewById(R.id.seconds1Txt);
        degrees2Txt = (EditText)view.findViewById(R.id.degrees2Txt);
        minutes2Txt = (EditText)view.findViewById(R.id.minutes2Txt);
        seconds2Txt = (EditText)view.findViewById(R.id.seconds2Txt);

        // Limit the number of digits to enter
        degrees1Txt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(3)});
        minutes1Txt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
        seconds1Txt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});

        degrees2Txt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(3)});
        minutes2Txt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
        seconds2Txt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});

        // Limit the number range to 0-59 for minutes and seconds fields
        minutes1Txt.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});
        seconds1Txt.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});
        minutes2Txt.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});
        seconds2Txt.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});


        // TextView initialisation
        resultsTxtView = (TextView)view.findViewById(R.id.resultTxtView);

        // Set Listeners
        clear1Btn.setOnClickListener(this);
        clear2Btn.setOnClickListener(this);
        clearResulsBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        subtractBtn.setOnClickListener(this);
        mainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Load Main Menu Screen
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.loadMainMenuScreen();
            }
        });

        // Initalise helper objects
        inputChecker = new DataInputChecker();
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {

        Button chosenBtn = (Button)view;

        // determine action according to button pressed
        if (chosenBtn == clear1Btn){

            clearAngle1Fields();

        } else if (chosenBtn == clear2Btn) {

            clearAngle2Fields();

        } else if (chosenBtn == clearResulsBtn) {

            clearResults();

        } else if (chosenBtn == addBtn | chosenBtn == subtractBtn) {

            getUserInput();

            String angle1InputFields[] = {degrees1, minutes1, seconds1};
            String angle2InputFields[] = {degrees2, minutes2, seconds2};

            // Check that data has been entered
            if (inputChecker.allInputFieldsEmpty(angle1InputFields) && inputChecker.allInputFieldsEmpty(angle2InputFields)){
                // show alert dialog if all fields are empty
                noDataEntered();
                return;
            }
            // Check that data is Numerical
            if(inputChecker.isNotNumericData(angle1InputFields, "Integer") | inputChecker.isNotNumericData(angle2InputFields, "Double")){
                // show alert if data entered is not correct
                nonNumericalDataEntered();
                return;
            }

            // Data is numerical, Convert to decimal
            // Assign zero to empty fields
            degrees1 = (degrees1.length() > 0) ? degrees1 : "0";
            minutes1 = (minutes1.length() > 0) ? minutes1 : "0";
            seconds1 = (seconds1.length() > 0) ? seconds1 : "0";

            degrees2 = (degrees2.length() > 0) ? degrees2 : "0";
            minutes2 = (minutes2.length() > 0) ? minutes2 : "0";
            seconds2 = (seconds2.length() > 0) ? seconds2 : "0";

            angle1 = new Angle();
            angle2 = new Angle();
            angle1.setAllValues(degrees1, minutes1, seconds1);
            angle2.setAllValues(degrees2, minutes2, seconds2);

            // We have passed all data validation checks so perform math operation on angles
            if (chosenBtn == addBtn) {
                // angular addition
                resultsTxtView.setText(angle1.addAngle(angle2));

            } else if (chosenBtn == subtractBtn) {
                // angular subtraction
                resultsTxtView.setText(angle1.subtractAngle(angle2));
            }

        }

    }

    public void clearAngle1Fields () {

        degrees1Txt.setText("");
        minutes1Txt.setText("");
        seconds1Txt.setText("");
    }

    public void clearAngle2Fields () {

        degrees2Txt.setText("");
        minutes2Txt.setText("");
        seconds2Txt.setText("");
    }

    public void clearResults () {

        resultsTxtView.setText("");
    }

    public void getUserInput (){
        // initalise global variables to hold user input

        degrees1 = degrees1Txt.getText().toString().trim();
        minutes1 = minutes1Txt.getText().toString().trim();
        seconds1 = seconds1Txt.getText().toString().trim();

        degrees2 = degrees2Txt.getText().toString();
        minutes2 = minutes2Txt.getText().toString();
        seconds2 = seconds2Txt.getText().toString();

    }

    public void noDataEntered (){

        // Alert message for no data entered
        AlertDialog.Builder a_builder = new AlertDialog.Builder(getActivity());
        a_builder.setMessage(R.string.dialog_no_data_angle_add_subtract_message)
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
            mListener.onAngleAddSubtractFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AngleAddSubtractFragmentInteractionListener) {
            mListener = (AngleAddSubtractFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AngleAddSubtractFragmentInteractionListener");
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
    public interface AngleAddSubtractFragmentInteractionListener {

        void onAngleAddSubtractFragmentInteraction(Uri uri);
    }
}
