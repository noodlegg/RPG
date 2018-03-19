package com.example.sword.rpg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.view.KeyEvent.*;

/**
 * Created by s152706 on 14-Mar-18.
 */

public class PartyName extends AppCompatActivity  {
    Button save;
    ArrayList<String> addArray = new ArrayList<String>();
    EditText txt;
    ListView show;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_name);

        txt = (EditText) findViewById(R.id.nameField);
        show = (ListView) findViewById(R.id.nameList);
        adapter = new ArrayAdapter<String>(PartyName.this, android.R.layout.simple_list_item_1, addArray);
        show.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        txt.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == ACTION_DOWN) && (keyCode == KEYCODE_ENTER)) {
                    String getInput = txt.getText().toString();
                    if (getInput == null || getInput.trim().equals("")) {
                        Toast.makeText(getBaseContext(), "Input Field is empty", Toast.LENGTH_LONG).show();
                    } else {
                        addArray.add(getInput);
                        adapter.notifyDataSetChanged();
                        ((EditText) findViewById(R.id.nameField)).setText(" ");
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });



        show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                addArray.remove(position);
                adapter.notifyDataSetChanged();
            }

        });
    }
}

