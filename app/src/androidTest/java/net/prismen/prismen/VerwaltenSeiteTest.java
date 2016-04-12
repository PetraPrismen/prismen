package net.prismen.prismen;


import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import net.prismen.prismen.themen.Themenseite;

import java.lang.Exception;

/**
 * Created by iri on 11.12.15.
 */
public class VerwaltenSeiteTest  extends ActivityInstrumentationTestCase2<Themenseite>{

    private Solo solo;

    public VerwaltenSeiteTest(){
        super(Themenseite.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

    public void testNeuesThemaErstellen() throws Exception{

    }

}