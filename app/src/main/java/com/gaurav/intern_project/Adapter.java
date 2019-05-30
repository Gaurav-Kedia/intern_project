package com.gaurav.intern_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Viewholder> {

    ArrayList<String> list;
    Context context;

    SharedPreferences SP;
    SharedPreferences.Editor editor;

    public Adapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
        SP = PreferenceManager.getDefaultSharedPreferences(this.context);
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.workshop_items, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(final Viewholder viewholder, final int position) {
        viewholder.textview.setText(list.get(position));

        boolean web = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("web", false);
        if ((list.get(position)).equalsIgnoreCase("Web Development") && web) {
            viewholder.btn.setText("Applied");
        }

        boolean android = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("android", false);
        if (list.get(position).equalsIgnoreCase("Android Development") && android) {
            viewholder.btn.setText("Applied");
        }

        boolean pp = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("pp", false);
        if (list.get(position).equalsIgnoreCase("Programming with python") && pp) {
            viewholder.btn.setText("Applied");
        }

        viewholder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = SP.edit();
                if (position == 0) {
                    editor.putBoolean("web", true);
                    viewholder.btn.setText("Applied");
                    editor.commit();
                } else if (position == 1) {
                    editor.putBoolean("android", true);
                    viewholder.btn.setText("Applied");
                    editor.commit();
                } else if (position == 2) {
                    editor.putBoolean("pp", true);
                    viewholder.btn.setText("Applied");
                    editor.commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView textview;
        Button btn;

        public Viewholder(View itemView) {
            super(itemView);

            textview = itemView.findViewById(R.id.workshop_name);
            btn = itemView.findViewById(R.id.apply_button);
        }
    }
}
