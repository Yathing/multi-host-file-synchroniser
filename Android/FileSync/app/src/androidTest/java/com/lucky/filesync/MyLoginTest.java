package com.lucky.filesync;

import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.*;

public class MyLoginTest {

    @Rule
    public ActivityTestRule<MyLogin> rule  = new  ActivityTestRule<>(MyLogin.class);

    private MyLogin myLogin = null;


    @Before
    public void setUp() throws Exception {
        myLogin = rule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        myLogin = null;
    }

    @Test
    public void testLaunch(){
        View email = myLogin.findViewById(R.id.emailView);
        View password = myLogin.findViewById(R.id.passwordView);

        assertNotNull(email);
        assertNotNull(password);

    }

}