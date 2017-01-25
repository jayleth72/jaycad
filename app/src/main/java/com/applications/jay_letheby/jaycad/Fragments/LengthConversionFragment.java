package com.applications.jay_letheby.jaycad.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.applications.jay_letheby.jaycad.R;

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

    private EditText convertToTxt;

    private TextView converToTxtView;
    private TextView lengthStackTxtView;
    private TextView runningTotalTxtView;

    private Spinner lengthConversionSpinner;

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
        convertToTxt = (EditText)view.findViewById(R.id.convertFrom);

        // Initialise TextViews
        converToTxtView = (TextView)view.findViewById(R.id.resultTxtView);
        lengthStackTxtView = (TextView)view.findViewById(R.id.resultStackTxtView);
        runningTotalTxtView = (TextView)view.findViewById(R.id.runningTotalTxtView);

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

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {

        Button chosenBtn = (Button)view;

        if (chosenBtn == convertBtn){
            // convert units
            convertMeasurement();
        } else if (chosenBtn == clearConvertFromBtn)
             convertToTxt.setText("");
          else if (chosenBtn == clearConvertToBtn)
             converToTxtView.setText("");
           else if (chosenBtn == clearStackBtn)
             lengthStackTxtView.setText("");
    }

    public void convertMeasurement() {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
