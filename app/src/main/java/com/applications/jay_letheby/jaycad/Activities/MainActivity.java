package com.applications.jay_letheby.jaycad.Activities;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;

import com.applications.jay_letheby.jaycad.Fragments.ConversionsMenuFragment;
import com.applications.jay_letheby.jaycad.Fragments.MainFragment;
import com.applications.jay_letheby.jaycad.R;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentInteractionListener, ConversionsMenuFragment.ConversionsMenuInteractionListener {

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

        ConversionsMenuFragment conversionsMenuFragment = new ConversionsMenuFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, conversionsMenuFragment).addToBackStack(null).commit();
    }

    @Override
    public void onMainFragmentInteraction(Uri uri) {

    }

    @Override
    public void onConversionsMenuInteraction(Uri uri) {

    }
}
