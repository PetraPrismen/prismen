package net.prismen.prismen.start;

import junit.framework.TestCase;
import junit.framework.TestResult;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import static org.junit.Assert.*;

/**
 * Created by iri on 26.02.16.
 */
@RunWith(MockitoJUnitRunner.class)


public class StartseitePresenterTest extends TestCase {
    @Mock
    private StartseiteContract.View view;
    private StartseitePresenter presenter;

    @Before
    public void setUp() throws Exception{
        presenter = new StartseitePresenter(view);
    }

    @Test
    public void shouldShowVerwaltenSeiteWhenChlickVerwalten() throws Exception{
        presenter.onClickVerwalten();
        verify(view).startVerwaltenSeite();
    }

    @Test
    public void shouldShowLernenVorbereitenSeiteWhenChlickLernen() throws Exception{
        presenter.onClickLernen();
        verify(view).startLernenVorbereitenSeite();
    }
}