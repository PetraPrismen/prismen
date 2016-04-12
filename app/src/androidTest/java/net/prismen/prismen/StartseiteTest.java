package net.prismen.prismen;


import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import net.prismen.prismen.start.Startseite;
import net.prismen.prismen.themen.Themenseite;

import java.lang.Exception;

/**
 * Created by iri on 07.12.15.
 */
public class StartseiteTest  extends ActivityInstrumentationTestCase2<Startseite>{

    private Solo solo;

    public StartseiteTest(){
        super(Startseite.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

    public void testVerwaltenSeiteAnzeigen() throws Exception{
        solo.clickOnButton("Verwalten");
        solo.assertCurrentActivity("Themenseite erwartet", Themenseite.class);
    }

}
