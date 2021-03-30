package com.example.billsplit;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home_fragment extends Fragment {

    private View  groupFragmentView;
    private ListView listview;
    private ArrayAdapter<String> arrayAdp;
    private ArrayList<String> listofgroups = new ArrayList<>();

    private DatabaseReference groupref;

    public home_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        groupFragmentView = inflater.inflate(R.layout.fragment_home_fragment, container, false);


        groupref = FirebaseDatabase.getInstance().getReference().child("Groups");

        intitializeFields();

        DisplayGroups();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String curgroupName = parent.getItemAtPosition(position).toString();

                Intent groupIntent = new Intent(getContext(), groupExpenseActivity.class);
                groupIntent.putExtra("groupName", curgroupName);
                startActivity(groupIntent);

            }
        });

        return groupFragmentView;
    }

    private void DisplayGroups() {
        groupref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Set<String> grps = new HashSet<>();
                Iterator ite = snapshot.getChildren().iterator();
                while(ite.hasNext()){
                    grps.add(((DataSnapshot)ite.next()).getKey());
                }
                listofgroups.clear();
                listofgroups.addAll(grps);
                arrayAdp.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void intitializeFields() {
        listview = (ListView) groupFragmentView.findViewById(R.id.grouplist);
        arrayAdp = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listofgroups);
        listview.setAdapter(arrayAdp);

    }


}