package com.lucky.filesync;

import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.*;

public class MyRegisterTest {

    @Rule
    public ActivityTestRule<MyRegister> rule  = new  ActivityTestRule<>(MyRegister.class);

    private MyRegister myRegister = null;


    @Before
    public void setUp() throws Exception {
        myRegister = rule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        myRegister = null;
    }

    @Test
    public void testLaunch(){
        View register = myRegister.findViewById(R.id.registerbut);
        View username = myRegister.findViewById(R.id.username);
        View name = myRegister.findViewById(R.id.name);
        View password = myRegister.findViewById(R.id.password);
        View email = myRegister.findViewById(R.id.email);

        assertNotNull(email);
        assertNotNull(password);
        assertNotNull(name);
        assertNotNull(username);
        assertNotNull(register);

    }

}