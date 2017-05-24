package com.example.piyush.indoornevigateion.view;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.piyush.indoornevigateion.R;

import java.util.ArrayList;


public class DestinationList extends ListActivity implements AdapterView.OnItemClickListener {

    ArrayList<String> listItem;
    ListView destinationList;
    String itemSelected;
    int position,flag;
    TextView text;
    String source;
    private String longitude;
    private String latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.destination_list);
            destinationList=getListView();
            Bundle b = getIntent().getExtras();

            flag= b.getInt("source");
            if(flag==1) {
                latitude = String.valueOf(b.getDouble("latitude"));
                longitude = String.valueOf(b.getDouble("longitude"));
                listItem=b.getStringArrayList("listItem");
            }
            else{
                listItem=b.getStringArrayList("listItem");
                position=b.getInt("position");
                source = listItem.get(position);
            }

            Log.e("destination list","after building");





            text=(TextView)findViewById(R.id.select);
            if(flag==1){
                text.setText("Select Source");
//                GetDestination getDestination =new GetDestination();
//                Log.e("Destination list","after getdestination oject");
               // listItem= getDestination.retrieveDestination(latitude, longitude);
            }
            else
            {
                text.setText("Select Destination");
                listItem.remove(position);
            }


            for(int i=0;i<listItem.size();i++){
                Log.e("List item",listItem.get(i));
            }



            ArrayAdapter<String> adapter = new ArrayAdapter<String>(DestinationList.this, android.R.layout.simple_list_item_1,android.R.id.text1, listItem);
            Log.e("destination list  ", "alll izzzz welll");
            destinationList.setAdapter(adapter);
            Log.e("destination list","all is wellllllllll 12");
            destinationList.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("destinationlist","write untill here");

        Intent i=null;
        if(flag==1){
            i= new Intent(DestinationList.this,DestinationList.class);
            i.putExtra("listItem",listItem);
            i.putExtra("position", position);
            startActivity(i);

        }
        else {
            Log.e("destinationList", listItem.get(position));
            String itemSelected =listItem.get(position);
            i = new Intent(DestinationList.this, MapView.class);
            i.putExtra("destination", itemSelected);
            i.putExtra("source",source);
            startActivity(i);
        }
    }
}
