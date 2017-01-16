package com.applications.jay_letheby.jaycad.Activities;

import android.net.Uri;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

import com.applications.jay_letheby.jaycad.Fragments.AboutFragment;
import com.applications.jay_letheby.jaycad.Fragments.AngleAddSubtractFragment;
import com.applications.jay_letheby.jaycad.Fragments.AngleConversionFragment;
import com.applications.jay_letheby.jaycad.Fragments.AreaConversionFragment;
import com.applications.jay_letheby.jaycad.Fragments.BakeryFinderFragment;
import com.applications.jay_letheby.jaycad.Fragments.ConversionsMenuFragment;
import com.applications.jay_letheby.jaycad.Fragments.LengthConversionFragment;
import com.applications.jay_letheby.jaycad.Fragments.MainFragment;
import com.applications.jay_letheby.jaycad.R;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentInteractionListener, ConversionsMenuFragment.ConversionsMenuInteractionListener,
        AngleConversionFragment.AngleConversionInteractionListener, AngleAddSubtractFragment.AngleAddSubtractFragmentInteractionListener,
        BakeryFinderFragment.BakeryFinderFragmentInteractionListener, AboutFragment.AboutFragmentInteractionListener,
        LengthConversionFragment.LengthConversionFragmentInteractionListener, AreaConversionFragment.AreaConversionFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new MainFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }

    public void loadFragment(String chosenFragment){
        // Load Fragment according to which button is pressed in Main Menu and Conversions Menu
        Fragment fragmentToLoad;

        switch (chosenFragment)
        {
            case "conversions":
                ConversionsMenuFragment conversionsMenuFragment = new ConversionsMenuFragment();
                fragmentToLoad  = conversionsMenuFragment;
                break;
            case "angle conversion":
                AngleConversionFragment angleConversionFragment = new AngleConversionFragment();
                fragmentToLoad  = angleConversionFragment;
                break;
            case "angle add/subtract":
                AngleAddSubtractFragment angleAddSubtractFragment = new AngleAddSubtractFragment();
                fragmentToLoad  = angleAddSubtractFragment;
                break;
            case "bakery finder":
                BakeryFinderFragment bakeryFinderFragment = new BakeryFinderFragment();
                fragmentToLoad  = bakeryFinderFragment;
                break;
            case "about":
                AboutFragment aboutFragment = new AboutFragment();
                fragmentToLoad  = aboutFragment;
                break;
            case "length conversion":
                LengthConversionFragment lengthConversionFragment = new LengthConversionFragment();
                fragmentToLoad  = lengthConversionFragment;
                break;
            case "area conversion":
                AreaConversionFragment areaConversionFragment = new AreaConversionFragment();
                fragmentToLoad  = areaConversionFragment;
                break;
            case "main menu":
                loadMainMenuScreen();
                return;
            default:
                fragmentToLoad  = null;
        }

        if (fragmentToLoad != null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentToLoad ).addToBackStack(null).commit();
    }

    public void loadMainMenuScreen() {
        // return to main menu

        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();

        while(backStackCount > 0){

            getSupportFragmentManager().popBackStack();
            backStackCount--;
        }

    }

    @Override
    public void onMainFragmentInteraction(Uri uri) {

    }

    @Override
    public void onConversionsMenuInteraction(Uri uri) {

    }

    @Override
    public void onAngleConversionInteraction(Uri uri) {

    }

    @Override
    public void onAngleAddSubtractFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBakeryFinderFragmentInteraction(Uri uri) {

    }

    @Override
    public void onAboutFragmentInteraction(Uri uri) {

    }

    @Override
    public void onLengthConversionFragmentInteraction(Uri uri) {

    }

    @Override
    public void onAreaConversionFragmentInteraction(Uri uri) {

    }
}
