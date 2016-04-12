package net.prismen.prismen.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.prismen.prismen.data.Thema;

import java.util.ArrayList;

/**
 * Created by iri on 20.03.16.
 */
/**
 * Adapter class for the list of Themen
 * extends ArrayAdapter
 */
public class MyThemenArrayAdapter extends ArrayAdapter<Thema> {


    Context myCtxt;
    int  layoutResId;
    ArrayList<Thema> themen;

    public MyThemenArrayAdapter(Context ctx, int layoutResId, ArrayList<Thema> themenList){
        super(ctx,layoutResId,themenList);
        this.layoutResId = layoutResId;
        this.myCtxt = ctx;
        this.themen = themenList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) myCtxt).getLayoutInflater();
            convertView = inflater.inflate(layoutResId, parent, false);
        }

        // get object item based on the position
        Thema thema = themen.get(position);


        // get the TextView and then set the text (item name) and tag (item ID) values
        // TODO show lektionen nb
        TextView textViewItem1 = (TextView) convertView.findViewById(android.R.id.text1);
        //TextView textViewItem2 = (TextView) convertView.findViewById(android.R.id.text2);
        textViewItem1.setText(thema.getName());
        //textViewItem2.setText(thema.getNb_lektionen() + " " + getString(R.string.lektionen));
        return convertView;
    }

    public void addThema(Thema th){
        this.themen.add(th);
        notifyDataSetChanged();
    }

    public void updateThema(int position,String newName){
        Thema th_change = this.themen.get(position);
        th_change.setName(newName);
        notifyDataSetChanged();
    }

    public Thema getThema(int position){
        return themen.get(position);
    }

    public void removeThema(int position){
        themen.remove(position);
        notifyDataSetChanged();
    }

}

