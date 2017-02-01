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
 * {@link AreaConversionFragment.AreaConversionFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AreaConversionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AreaConversionFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{
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
    private EditText roodTxt;
    private EditText perchTxt;

    private TextView convertUnitTxtView;
    private TextView roodTxtView;
    private TextView perchTxtView;
    private TextView converToTxtView;
    private TextView areaStackTxtView;

    private TextView runningTotalTxtView;
    private TextView converToLabelTxtView;
    private TextView stackLabelTxtView;
    private TextView runningTotalLabelTxtView;

    private Spinner areaConversionSpinner;

    // Keeps running total of measurements in the stack
    private double runningTotal = 0;

    // Helper class objects
    private DataInputChecker dataInputChecker;
    private Converter converter;


    private AreaConversionFragmentInteractionListener mListener;

    public AreaConversionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AreaConversionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AreaConversionFragment newInstance(String param1, String param2) {
        AreaConversionFragment fragment = new AreaConversionFragment();
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

        View view = inflater.inflate(R.layout.fragment_area_conversion, container, false);

        // Initailise buttons
        mainMenuBtn = (Button)view.findViewById(R.id.mainMenuBtn);
        convertBtn = (Button)view.findViewById(R.id.convertBtn);
        clearConvertFromBtn = (Button)view.findViewById(R.id.clearConvertFromBtn);
        clearConvertToBtn = (Button)view.findViewById(R.id.clearResultBtn);
        clearStackBtn = (Button)view.findViewById(R.id.clearStackBtn);

        // Initialise EditTextField
        convertFromTxt = (EditText)view.findViewById(R.id.convertFrom);
        roodTxt = (EditText)view.findViewById(R.id.roodTxt);
        perchTxt = (EditText)view.findViewById(R.id.perchTxt);

        // Initialise TextViews
        convertUnitTxtView = (TextView)view.findViewById(R.id.convertUnitTxtView);
        roodTxtView = (TextView)view.findViewById(R.id.roodTxtView);
        perchTxtView = (TextView)view.findViewById(R.id.perchTxtView);
        converToTxtView = (TextView)view.findViewById(R.id.resultTxtView);
        areaStackTxtView = (TextView)view.findViewById(R.id.resultStackTxtView);
        runningTotalTxtView = (TextView)view.findViewById(R.id.runningTotalTxtView);
        converToLabelTxtView = (TextView)view.findViewById(R.id.converToLabel);
        stackLabelTxtView = (TextView)view.findViewById(R.id.stackLabelTxtView);
        runningTotalLabelTxtView = (TextView)view.findViewById(R.id.runTotalLabelTxtView);

        // Initialise spinner
        areaConversionSpinner = (Spinner)view.findViewById(R.id.units2Spinner);
        areaConversionSpinner.setOnItemSelectedListener(this);

        // Create adapter for spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.area_units_array, android.R.layout.simple_spinner_dropdown_item);

        // Drop down layout style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        areaConversionSpinner.setAdapter(adapter);

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

        // Set default position of spinner as "Acres to Hectares"
        areaConversionSpinner.setSelection(0);

        // Limit the input to 2 digits for inch and Fraction fields
        roodTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});
        perchTxt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});

        // Limit the number range to 0-11 for roodTxt fields
        roodTxt.setFilters(new InputFilter[]{new InputFilterMinMax("0", "3")});

        // Limit the number range to 0-15 for perch fields
        perchTxt.setFilters(new InputFilter[]{new InputFilterMinMax("0", "39")});

        // Set Length Stack to be scrollable
        areaStackTxtView.setMovementMethod(new ScrollingMovementMethod());

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {
        Button chosenBtn = (Button)view;

        if (chosenBtn == convertBtn){
            // convert units
            //convertMeasurement();
        } else if (chosenBtn == clearConvertFromBtn) {
            convertFromTxt.setText("");
            roodTxt.setText("");
            perchTxt.setText("");
        }
        else if (chosenBtn == clearConvertToBtn)
            converToTxtView.setText("");
        else if (chosenBtn == clearStackBtn) {
            areaStackTxtView.setText("");
            runningTotalTxtView.setText("");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        clearAll();

        switch(position) {
            case 0:
                // Acres to Hectares
                convertUnitTxtView.setText("ACRES");
                convertFromTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                hideRoodPerchFields();
                clearInputFields();
                changeResultsLabels("HECTARES");
                break;
            case 1:
                // Hectares to Acres
                convertUnitTxtView.setText("HECTARES");
                // Allow decimal input from metres
                convertFromTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                // Hide Inch fields until selected from dropdown
                hideRoodPerchFields();
                clearInputFields();
                changeResultsLabels("ACRES");
                break;
            case 2:
                // Acres roods & perches to Hectares
                convertUnitTxtView.setText("ACRES");
                // Allow decimal input from Links
                convertFromTxt.setInputType(InputType.TYPE_CLASS_NUMBER );

                showRoodPerchFields();
                clearInputFields();
                changeResultsLabels("HECTARES");
                break;
            case 3:
                // Hectares to Acres, Roods and Perches
                convertUnitTxtView.setText("HECTARES");
                // Allow decimal input from metres
                convertFromTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                // Hide Inch fields until selected from dropdown
                hideRoodPerchFields();
                clearInputFields();
                changeResultsLabels("ACRES, ROODS & PERCHES");
                break;
        }
    }

    public void hideRoodPerchFields (){
        // Hide Rood and Perches fields and field lables
        roodTxtView.setVisibility(View.INVISIBLE);
        perchTxtView.setVisibility(View.INVISIBLE);
        roodTxt.setVisibility(View.INVISIBLE);
        perchTxt.setVisibility(View.INVISIBLE);

        roodTxt.setEnabled(false);
        perchTxt.setEnabled(false);

    }

    public void showRoodPerchFields () {
        // Show Rood and Perches fields and field lables
        convertUnitTxtView.setText("ACRES");
        convertFromTxt.setInputType(InputType.TYPE_CLASS_NUMBER);

        roodTxtView.setVisibility(View.VISIBLE);
        perchTxtView.setVisibility(View.VISIBLE);
        roodTxt.setVisibility(View.VISIBLE);
        perchTxt.setVisibility(View.VISIBLE);

        roodTxt.setEnabled(true);
        perchTxt.setEnabled(true);

    }

    public void clearInputFields() {
        // Clear input fields only
        convertFromTxt.setText("");
        roodTxt.setText("");
        perchTxt.setText("");
    }

    public void changeResultsLabels (String measurement) {
        // change labels according to conversion chosen
        converToLabelTxtView.setText(measurement);
        stackLabelTxtView.setText("AREA STACK" + " (" + measurement + ")");
        runningTotalLabelTxtView.setText("RUNNING TOTAL" + " (" + measurement + ")");
    }

    public void clearAll () {
        // Clears all EditText Fields and Results, Length Stack
        // and Running Stack Text Views

        convertFromTxt.setText("");
        roodTxt.setText("");
        perchTxt.setText("");

        areaStackTxtView.setText("");
        runningTotalTxtView.setText("");
        converToTxtView.setText("");

        runningTotal = 0.0;
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAreaConversionFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AreaConversionFragmentInteractionListener) {
            mListener = (AreaConversionFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AreaConversionFragmentInteractionListener");
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
    public interface AreaConversionFragmentInteractionListener {
        // TODO: Update argument type and name
        void onAreaConversionFragmentInteraction(Uri uri);
    }
}
