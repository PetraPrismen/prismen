package net.prismen.prismen;


import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import net.prismen.prismen.prismen.PrismenVerwaltenSeite;

import java.lang.Exception;

/**
 * Created by iri on 28.12.15.
 */
public class PrismenVerwaltenSeiteTest  extends ActivityInstrumentationTestCase2<PrismenVerwaltenSeite>{

    private Solo solo;

    public PrismenVerwaltenSeiteTest(){
        super(PrismenVerwaltenSeite.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

    public void testNeuesPrismaErstellen() throws Exception{

    }

}