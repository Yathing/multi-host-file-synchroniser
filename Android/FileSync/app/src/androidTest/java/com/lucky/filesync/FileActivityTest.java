package com.lucky.filesync;

import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.*;

public class FileActivityTest {


    @Rule
    public ActivityTestRule<FileActivity> rule  = new  ActivityTestRule<>(FileActivity.class);

    private FileActivity fileActivity = null;


    @Before
    public void setUp() throws Exception {
        fileActivity = rule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        fileActivity = null;
    }

    @Test
    public void testLaunch(){
        View rec = fileActivity.findViewById(R.id.recycle);
        View down = fileActivity.findViewById(R.id.downloading);

        assertNotNull(rec);
        assertNotNull(down);

    }

}