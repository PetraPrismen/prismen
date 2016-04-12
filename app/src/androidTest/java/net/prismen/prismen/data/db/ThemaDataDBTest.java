package net.prismen.prismen.data.db;

import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import net.prismen.prismen.data.Thema;

import java.util.ArrayList;



/**
 * Created by iri on 04.04.16.
 */
public class ThemaDataDBTest extends AndroidTestCase{

    private ThemaDataDB themaData;

    @Override
    public void setUp() throws Exception{
        super.setUp();
        RenamingDelegatingContext ctx = new RenamingDelegatingContext(getContext(),"test_");
        themaData = new ThemaDataDB(ctx);
    }


    public void testInsertThema(){
        Thema th = new Thema("thema_1");
        try{
            themaData.insertThema(th);
            ArrayList<Thema> all_themen = themaData.getAllThemen();
            Thema th1 = all_themen.get(0);
            assertEquals(th.getId(),th1.getId());
            assertEquals(th.getName(),th1.getName());
        }
        catch(Exception e){

        }
    }



    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

}