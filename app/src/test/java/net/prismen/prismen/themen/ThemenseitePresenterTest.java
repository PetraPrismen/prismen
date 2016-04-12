package net.prismen.prismen.themen;

import android.test.mock.MockContext;

import net.prismen.prismen.data.Thema;
import net.prismen.prismen.data.ThemaDataContract;
import net.prismen.prismen.data.db.ThemaDataDB;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by iri on 02.03.16.
 */
public class ThemenseitePresenterTest {

    private static ArrayList<Thema> THEMEN = new ArrayList<Thema>();


    @Mock
    private ThemaDataContract thDAO;
    @Mock
    private ThemenseiteContract.View view;

    private MockContext ctx;
    private ThemenseitePresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        thDAO = mock(ThemaDataDB.class);
        presenter = new ThemenseitePresenter(ctx,view);
        THEMEN.add(new Thema("Thema 1"));
        THEMEN.add(new Thema("Thema 2"));
        THEMEN.add(new Thema("Thema 3"));
    }

    @Test
    public void loadThemenIntoView() throws Exception{
        when(thDAO.getAllThemen()).thenReturn(THEMEN);
        presenter.loadThemen();
        verify(view).showThemen(THEMEN);
    }

}