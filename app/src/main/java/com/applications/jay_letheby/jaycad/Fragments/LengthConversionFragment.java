package com.applications.jay_letheby.jaycad.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.renderscript.Double2;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.applications.jay_letheby.jaycad.Activities.MainActivity;
import com.applications.jay_letheby.jaycad.HelperClasses.Converter;
import com.applications.jay_letheby.jaycad.R;
import com.applications.jay_letheby.jaycad.HelperClasses.DataInputChecker;
import com.applications.jay_letheby.jaycad.HelperClasses.InputFilterMinMax;
import com.applications.jay_letheby.jaycad.HelperClasses.DecimalUtils;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LengthConversionFragment.LengthConversionFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LengthConversionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LengthConversionFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button clearConvertToBtn;
    private Button clearConvertFromBtn;
    private Button clearStackBtn;
    private Button convertBtn;
    private Button mainMenuBtn;

    private EditText convertFromTxt;
    private EditText inchTxt;
    private EditText fractionInchTxt;

    private TextView convertUnitTxtView;
    private TextView inchTxtView;
    private TextView inchFractionTxtView;
    private TextView converToTxtView;
    private TextView lengthStackTxtView;

    private TextView runningTotalTxtView;
    private TextView converToLabelTxtView;
    private TextView stackLabelTxtView;
    private TextView runningTotalLabelTxtView;

    private Spinner lengthConversionSpinner;

    // Keeps running total of measurements in the stack
    private double runningTotal = 0;

    // Helper class objects
    private DataInputChecker dataInputChecker;
    private Converter converter;

    private LengthConversionFragmentInteractionListener mListener;

    public LengthConversionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LengthConversionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LengthConversionFragment newInstance(String param1, String param2) {
        LengthConversionFragment fragment = new LengthConversionFragment();
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

        View view = inflater.inflate(R.layout.fragment_length_conversion, container, false);

        // Initailise buttons
        convertBtn = (Button)view.findViewById(R.id.convertBtn);
        clearConvertFromBtn = (Button)view.findViewById(R.id.clearConvertFromBtn);
        clearConvertToBtn = (Button)view.findViewById(R.id.clearResultBtn);
        clearStackBtn = (Button)view.findViewById(R.id.clearStackBtn);
        mainMenuBtn = (Button)view.findViewById(R.id.mainMenuBtn);

        // Initialise EditTextField
        convertFromTxt = (EditText)view.findViewById(R.id.convertFrom);
        inchTxt = (EditText)view.findViewById(R.id.inchTxt);
        fractionInchTxt = (EditText)view.findViewById(R.id.inchesFractionsTxt);

        // Initialise TextViews
        convertUnitTxtView = (TextView)view.findViewById(R.id.convertUnitTxtView);
        inchTxtView = (TextView)view.findViewById(R.id.inchTxtView);
        inchFractionTxtView = (TextView)view.findViewById(R.id.fractionTxtView);
        converToTxtView = (TextView)view.findViewById(R.id.resultTxtView);
        lengthStackTxtView = (TextView)view.findViewById(R.id.resultStackTxtView);
        runningTotalTxtView = (TextView)view.findViewById(R.id.runningTotalTxtView);
        converToLabelTxtView = (TextView)view.findViewById(R.id.converToLabel);
        stackLabelTxtView = (TextView)view.findViewById(R.id.stackLabelTxtView);
        runningTotalLabelTxtView = (TextView)view.findViewById(R.id.runTotalLabelTxtView);

        // Initialise spinner
        lengthConversionSpinner = (Spinner)view.findViewById(R.id.units1Spinner);
        lengthConversionSpinner.setOnItemSelectedListener(this);

        // Create adapter for spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.length_units_array, android.R.layout.simple_spinner_dropdown_item);

        // Drop down layout style - list view with radio button
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        lengthConversionSpinner.setAdapter(adapter);

        mainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Return to the main menu
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.loadMainMenuScreen();
            }
        });

        // Set Listeners for buttons
        convertBtn.setOnClickListener(this);
        clearConvertFromBtn.setOnClickListener(this);
        clearConvertToBtn.setOnClickListener(this);
        clearStackBtn.setOnClickListener(this);

        // initalise helper class objects
        dataInputChecker = new DataInputChecker();
        converter = new Converter();

        // Set default position of spinner as "feet to metres"
        lengthConversionSpinner.setSelection(0);
        // Show inch fields as they are default
        showInchFields();

        // Limit the input to 2 digits for inch and Fraction fields
        inchTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
        fractionInchTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});

        // Limit the number range to 0-11 for inchTxt fields
        inchTxt.setFilters(new InputFilter[]{new InputFilterMinMax("0", "11")});

        // Limit the number range to 0-15 for fractionInch fields
        fractionInchTxt.setFilters(new InputFilter[]{new InputFilterMinMax("0", "15")});

        // Set Length Stack to be scrollable
        lengthStackTxtView.setMovementMethod(new ScrollingMovementMethod());

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {

        Button chosenBtn = (Button)view;

        if (chosenBtn == convertBtn){
            // convert units
            convertMeasurement();
        } else if (chosenBtn == clearConvertFromBtn) {
            convertFromTxt.setText("");
            inchTxt.setText("");
            fractionInchTxt.setText("");
        }
          else if (chosenBtn == clearConvertToBtn)
             converToTxtView.setText("");
          else if (chosenBtn == clearStackBtn) {
            lengthStackTxtView.setText("");
            runningTotalTxtView.setText("");
        }
    }

    public void convertMeasurement() {
        // perform conversion according to action chosen from dropdown
        int convertAction = lengthConversionSpinner.getSelectedItemPosition();
        // Will hold result of conversion
        String result = "";
        String convertMeasure = "";
        String inchMeasure = "";
        String fractionalInchMeasure = "";

        // Check data input
        if (isDataInputOK(convertAction)){
            // Get Input data
            convertMeasure = convertFromTxt.getText().toString();
            inchMeasure = inchTxt.getText().toString();
            fractionalInchMeasure = fractionInchTxt.getText().toString();

            // Convert no input to zeros
            convertMeasure = (convertMeasure.length() > 0) ? convertMeasure : "0";
            inchMeasure = (inchMeasure.length() > 0) ? inchMeasure : "0";
            fractionalInchMeasure = (fractionalInchMeasure.length() > 0) ? fractionalInchMeasure : "0";

            switch (convertAction) {
                case 0:
                    // Feet to Meters
                    result = converter.lengthConverter(Converter.LengthConversionOperation.FEET_TO_METRES,
                                Double.parseDouble(convertFeetToDecimalFeet(convertMeasure, inchMeasure, fractionalInchMeasure)));
                    addToStack(roundResult(result, 3));
                    keepRunningTotal(result, 0);
                    break;
                case 1:
                    // Metres to feet
                    result = converter.lengthConverter(Converter.LengthConversionOperation.METRES_TO_FEET, Double.parseDouble(convertMeasure));
                    keepRunningTotal(result, 1);
                    result = convertDecimalFeet(result);
                    addToStack(result);
                    break;
                case 2:
                    // Links to Metres
                    result = converter.lengthConverter(Converter.LengthConversionOperation.LINKS_TO_METRES, Double.parseDouble(convertMeasure));
                    addToStack(roundResult(result, 3));
                    keepRunningTotal(result, 2);
                    break;
                case 3:
                    // Metres to Links
                    result = converter.lengthConverter(Converter.LengthConversionOperation.METRES_TO_LINKS, Double.parseDouble(convertMeasure));
                    addToStack(roundResult(result, 3));
                    keepRunningTotal(result, 3);
                    break;
                default:
                    // error
                    // this should never get chosen but put error message here just in case
                    // display error message
                    break;
            }

            // Display result
            if (convertAction == 1)
                converToTxtView.setText(result);
            else
                converToTxtView.setText(roundResult(result, 3));

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        clearAll();

        switch(position) {
            case 0:
                // Feet to Meters
                convertUnitTxtView.setText("FEET");
                convertFromTxt.setInputType(InputType.TYPE_CLASS_NUMBER);
                showInchFields();
                clearInputFields();
                changeResultsLabels("METRES");
                break;
            case 1:
                 // Metres to feet
                convertUnitTxtView.setText("METRES");
                // Allow decimal input from metres
                convertFromTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                // Hide Inch fields until selected from dropdown
                hideInchFields();
                clearInputFields();
                changeResultsLabels("FEET");
                break;
            case 2:
                // Links to Mtres
                convertUnitTxtView.setText("LINKS");
                // Allow decimal input from Links
                convertFromTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                // Hide Inch fields until selected from dropdown
                hideInchFields();
                clearInputFields();
                changeResultsLabels("METRES");
                break;
            case 3:
                // Metres to Links
                convertUnitTxtView.setText("METRES");
                // Allow decimal input from metres
                convertFromTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                // Hide Inch fields until selected from dropdown
                hideInchFields();
                clearInputFields();
                changeResultsLabels("LINKS");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public boolean isDataInputOK (int convertAction) {
        // Checks for no data entered and non-numerical data

        // String Array for checking for no data entered
        String [] conversionDataInput = {convertFromTxt.getText().toString(), inchTxt.getText().toString(), fractionInchTxt.getText().toString()};

        // Check that data has been entered
        if (dataInputChecker.allInputFieldsEmpty(conversionDataInput)){
            // show alert dialog if all fields are empty
            noDataEntered();
            return false;
        }

        // Check that data is Numerical

        String [] conversionDataDoubleInput = {convertFromTxt.getText().toString()};

        // Check for integer data when converting feet to metress otherwise checking for double type input
        if (convertAction == 0) {
            // Check for integer data when converting feet to metres
            if (dataInputChecker.isNotNumericData(conversionDataInput, "Integer")) {
                nonNumericalDataEntered();
                return false;
            }
        } else {
            // Check for double type data
            if (dataInputChecker.isNotNumericData(conversionDataDoubleInput, "Double")) {
                nonNumericalDataEntered();
                return false;
            }
        }

        // there is data and is numerical
        return true;
    }

    public void clearAll () {
        // Clears all EditText Fields and Results, Length Stack
        // and Running Stack Text Views

        convertFromTxt.setText("");
        inchTxt.setText("");
        fractionInchTxt.setText("");

        lengthStackTxtView.setText("");
        runningTotalTxtView.setText("");
        converToTxtView.setText("");

        runningTotal = 0.0;
    }

    public void noDataEntered (){

        // Alert message for no data entered
        AlertDialog.Builder a_builder = new AlertDialog.Builder(getActivity());
        a_builder.setMessage(R.string.dialog_no_data_conversion_message)
                .setTitle(R.string.dialog_no_data_conversion_title)
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

    public void clearInputFields() {
        // Clear input fields only
        convertFromTxt.setText("");
        inchTxt.setText("");
        fractionInchTxt.setText("");
    }


    public void hideInchFields (){
        // Hide Inc and Fraction fields and field lables
        inchTxtView.setVisibility(View.INVISIBLE);
        inchFractionTxtView.setVisibility(View.INVISIBLE);
        inchTxt.setVisibility(View.INVISIBLE);
        fractionInchTxt.setVisibility(View.INVISIBLE);

        inchTxt.setEnabled(false);
        fractionInchTxt.setEnabled(false);

    }

    public void showInchFields () {
         // Show Inch and Fraction fields and field lables
        convertUnitTxtView.setText("FEET");
        convertFromTxt.setInputType(InputType.TYPE_CLASS_NUMBER);

        inchTxtView.setVisibility(View.VISIBLE);
        inchFractionTxtView.setVisibility(View.VISIBLE);
        inchTxt.setVisibility(View.VISIBLE);
        fractionInchTxt.setVisibility(View.VISIBLE);

        inchTxt.setEnabled(true);
        fractionInchTxt.setEnabled(true);

    }

    public void addToStack (String result) {
        // Add converted measurement to stack

        lengthStackTxtView.append("\n" + result);
    }

    public void keepRunningTotal (String result, int convertAction) {

        String roundedNumber = "";

        try {
            runningTotal += Double.parseDouble(result);

            if (convertAction == 1) {
                // metres to feet

                runningTotalTxtView.setText(convertDecimalFeet(runningTotal + ""));

            } else {
                roundedNumber = roundResult(runningTotal + "", 3);
                runningTotalTxtView.setText(roundedNumber);
            }

        }
        catch (NumberFormatException e) {
            // TODO : put error message here
        }
    }

    public void changeResultsLabels (String measurement) {
        // change labels according to conversion chosen
        converToLabelTxtView.setText(measurement);
        stackLabelTxtView.setText("LENGTH STACK" + " (" + measurement + ")");
        runningTotalLabelTxtView.setText("RUNNING TOTAL" + " (" + measurement + ")");
    }

    public String convertFeetToDecimalFeet(String feetMeasure, String inch, String fractionInch) {
        // converts feet, inches and inch fractions to decimal feet
        double feetNumber = 0.0;
        double inchNumber = 0.0;
        double fractionInchNumber = 0.0;
        double decimalFeet = 0.0;

        try {
            feetNumber = Double.parseDouble(feetMeasure);
            inchNumber =  Double.parseDouble(inch);
            fractionInchNumber = Double.parseDouble(fractionInch);

            // 12 inches to a foot and 16 fractionInches to an Inch
            decimalFeet = feetNumber + (inchNumber * 1/12) + (fractionInchNumber * 1/184);

        }
        catch (NumberFormatException e) {
            nonNumericalDataEntered();
        }

        return Double.toString(decimalFeet);

    }

    public String convertDecimalFeet(String theResult) {
        double fractionalPart = 0.0;
        double integralPart = 0.0;
        int feetPart = 0;
        int inchPart = 0;
        int inchFractionPart = 0;
        Double calc = 0.0;
        String formattedFeetMeasure = "";

        try {
            fractionalPart = Double.parseDouble(theResult) % 1;
            integralPart = Double.parseDouble(theResult) - fractionalPart;
            feetPart = (int) integralPart;

            // Get inches
            calc = fractionalPart / (0.08333);

            fractionalPart = calc % 1;
            integralPart = calc - fractionalPart;
            inchPart = (int)integralPart;

            // Get inch fractions
            calc = fractionalPart / 0.0625;
            fractionalPart = calc % 1;
            integralPart = calc - fractionalPart;
            inchFractionPart = (int)integralPart;

        }
        catch (NumberFormatException e) {
            // TODO : put error message here

        }

        if (feetPart == 0)
            formattedFeetMeasure = "0ft ";
        else
            formattedFeetMeasure = feetPart + "ft ";

        if (inchPart == 0)
            formattedFeetMeasure += " 0\"";
        else
            formattedFeetMeasure += inchPart + "\" ";

        if (inchFractionPart > 0)
            formattedFeetMeasure +=  inchFractionPart + "/16";

        return formattedFeetMeasure;
    }

    public String roundResult (String result, int roundTo) {
        // Round the result to set number of figures
        Double roundedNumber = 0.0;
         try {
             roundedNumber = Double.parseDouble(result);
             roundedNumber = DecimalUtils.round(roundedNumber, roundTo);
         }
         catch (NumberFormatException e){
             // TODO : put error message here

         }
        return roundedNumber + "";
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onLengthConversionFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LengthConversionFragmentInteractionListener) {
            mListener = (LengthConversionFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement LengthConversionFragmentInteractionListener");
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
    public interface LengthConversionFragmentInteractionListener {
        // TODO: Update argument type and name
        void onLengthConversionFragmentInteraction(Uri uri);
    }
}
