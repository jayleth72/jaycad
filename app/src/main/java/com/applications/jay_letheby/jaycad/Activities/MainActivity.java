package com.applications.jay_letheby.jaycad.Activities;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;

import com.applications.jay_letheby.jaycad.Fragments.AboutFragment;
import com.applications.jay_letheby.jaycad.Fragments.AngleAddSubtractFragment;
import com.applications.jay_letheby.jaycad.Fragments.AngleConversionFragment;
import com.applications.jay_letheby.jaycad.Fragments.BakeryFinderFragment;
import com.applications.jay_letheby.jaycad.Fragments.ConversionsMenuFragment;
import com.applications.jay_letheby.jaycad.Fragments.MainFragment;
import com.applications.jay_letheby.jaycad.R;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentInteractionListener, ConversionsMenuFragment.ConversionsMenuInteractionListener,
        AngleConversionFragment.AngleConversionInteractionListener, AngleAddSubtractFragment.AngleAddSubtractFragmentInteractionListener,
        BakeryFinderFragment.BakeryFinderFragmentInteractionListener, AboutFragment.AboutFragmentInteractionListener
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

    public void loadConversionsMenuScreen() {
        // Load Conversion Menu Screem
        ConversionsMenuFragment conversionsMenuFragment = new ConversionsMenuFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, conversionsMenuFragment).addToBackStack(null).commit();
    }

    public void loadAngleConversionScreen() {
        // Load Angle Conversion screen
        AngleConversionFragment angleConversionFragment = new AngleConversionFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, angleConversionFragment).addToBackStack(null).commit();
    }

    public void loadAngleAddSubtractScreen() {
        // Load Angle Add and Subtract Screen
        AngleAddSubtractFragment angleAddSubtractFragment = new AngleAddSubtractFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, angleAddSubtractFragment).addToBackStack(null).commit();
    }

    public void loadBakeryFinderScreen() {
        // Load Bakery Finder Screen
        BakeryFinderFragment bakeryFinderFragment = new BakeryFinderFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, bakeryFinderFragment).addToBackStack(null).commit();
    }

    public void loadAboutScreen(){
        // Load About Screen
        AboutFragment aboutFragment = new AboutFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, aboutFragment).addToBackStack(null).commit();
    }

    public void loadMainMenuScreen() {
        // return to main menu
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){

            getSupportFragmentManager().popBackStack();
            return;
        }

        super.onBackPressed();
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
}
