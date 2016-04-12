package net.prismen.prismen.prismen;

/**
 * Created by iri on 17.12.15.
 */


        import java.util.ArrayList;
        import java.util.HashMap;

        import android.app.Activity;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseExpandableListAdapter;
        import android.widget.CheckedTextView;
        import android.widget.ImageButton;
        import android.widget.TextView;

        import net.prismen.prismen.R;
        import net.prismen.prismen.data.Prisma;
        import net.prismen.prismen.data.Seite;
        import net.prismen.prismen.data.db.PrismenDatabase;
        import net.prismen.prismen.data.db.SeitenTbl;

@SuppressWarnings("unchecked")
public class PrismenExpandableListAdapter extends BaseExpandableListAdapter {

    private PrismenDatabase prismenDb;

    public ArrayList<Prisma> groupItem;
    public ArrayList<Seite> tempChild;
    // index 0 id, index 1 frage, index 2 antwort
    public HashMap<Integer,ArrayList<Seite>> childItem;
    public LayoutInflater minflater;
    public Activity activity;

    private Context ctx;

    private PrismenseitePresenter presenter;



    public PrismenExpandableListAdapter(ArrayList<Prisma> prismenList,
                                        Context context, PrismenseitePresenter presenter) {
        this.groupItem = prismenList;
        this.childItem = new HashMap<Integer, ArrayList<Seite>>();
        this.ctx = context;
        this.presenter = presenter;
    }

    public void setInflater(LayoutInflater mInflater, Activity act) {
        this.minflater = mInflater;
        activity = act;
    }


    @Override
    public Seite getChild(int groupPosition, int childPosition) {
        return childItem.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        Log.i("getChildView","Methode aufgerufen");
        tempChild = childItem.get(groupPosition);
        Seite elementChildList = tempChild.get(childPosition);
        if (convertView == null) {
          convertView = minflater.inflate(R.layout.seiten_list_item, null);
        }
        TextView textViewAntwort = (TextView) convertView.findViewById(R.id.text_antwort_item);
        textViewAntwort.setText(elementChildList.getAntwort());
        TextView textViewFrage = (TextView) convertView.findViewById(R.id.text_frage_item);
        String frage = elementChildList.getFrage();
        if (frage != null){
            textViewFrage.setText(frage);
        }
        else{
            textViewFrage.setText("");
        }
        return convertView;
    }


    /**
     * Override method getChildrenCount
     *  + populate child item hier because this method called before expand
     *  otherwise false child count prevents from expanding
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        String id_prisma = groupItem.get(groupPosition).getId();
        ArrayList<Seite> oneChild = presenter.loadSeitenForPrisma(id_prisma);
        childItem.put(groupPosition, oneChild);
        return oneChild.size();
    }

    @Override
    public Prisma getGroup(int groupPosition) {
        return groupItem.get(groupPosition);
    }

    public void deleteGroup(int groupPosition){
        groupItem.remove(groupPosition);
        childItem.remove(groupPosition);
    }

    public void deleteChild(int groupPosition,int childPosition){
        ArrayList<Seite> childList = childItem.get(groupPosition);
        childList.remove(childPosition);
    }

    public void editGroup(int groupPosition, String newName){
        Prisma gi = groupItem.get(groupPosition);
        gi.setName(newName);
    }

    public void editChild(int groupPosition, int childPostition, String frage,String antwort){
        Seite chi = childItem.get(groupPosition).get(childPostition);
        if (frage==null){
            chi.setTyp(0);
            Log.i("*******************","editChild frage null");
        }
        else {
            chi.setTyp(1);
            Log.i("**********************","editChild frage NOT null");
        }
        chi.setFrage(frage);
        chi.setAntwort(antwort);

    }

    public void addGroup(Prisma pr){
        groupItem.add(pr);
    }

    @Override
    public int getGroupCount() {
        return groupItem.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);

        if (childItem.get(groupPosition).size() > 0){
            childItem.remove(groupPosition);
        }
    }
/*
    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }
*/
    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = minflater.inflate(R.layout.prismen_list_item, null);
        }
        ImageButton image_bt = (ImageButton)convertView.findViewById(R.id.bt_add_seite);
        image_bt.setFocusable(false);
        // save the group position as tag in the button
        image_bt.setTag(groupPosition);

        CheckedTextView chText = (CheckedTextView)convertView.findViewById(R.id.prisma_item_title);
        Prisma gi = groupItem.get(groupPosition);
        chText.setText(gi.getName());
        //((CheckedTextView) convertView).setText(groupItem.get(groupPosition));
        //((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}