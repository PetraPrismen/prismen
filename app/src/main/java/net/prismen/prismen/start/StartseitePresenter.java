package net.prismen.prismen.start;

/**
 * Created by iri on 26.02.16.
 */
public class StartseitePresenter implements StartseiteContract.Presenter{
    private StartseiteContract.View view;

    public StartseitePresenter(StartseiteContract.View view){
        this.view = view;
    }

    @Override
    public void onClickVerwalten(){
        view.startVerwaltenSeite();
    }

    @Override
    public void onClickLernen() {
        view.startLernenVorbereitenSeite();
    }
}
