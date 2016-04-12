package net.prismen.prismen.start;

public interface StartseiteContract {
    interface View{
        void startVerwaltenSeite();
        void startLernenVorbereitenSeite();
    }
    interface Presenter{
        public void onClickVerwalten();
        public void onClickLernen();
    }
}