package com.lucky.filesync;

import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);

    private MainActivity mainActivity = null;

    @Before
    public void setUp() throws Exception {
        mainActivity = rule.getActivity();

    }

    @Test
    public void testLaunch(){
        View ll = mainActivity.findViewById(R.id.linlay);
        View selected = mainActivity.findViewById(R.id.selected);
        View load = mainActivity.findViewById(R.id.loading);

        assertNotNull(selected);
        assertNotNull(load);
        assertNotNull(ll);

    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }
}