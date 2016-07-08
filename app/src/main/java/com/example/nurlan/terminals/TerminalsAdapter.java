package com.example.nurlan.terminals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TerminalsAdapter extends BaseAdapter implements View.OnClickListener {

    final String LOG_TAG = "nb_log";
    Activity activity;
    public ArrayList<Point> rData = new ArrayList<Point>();
    private static LayoutInflater inflater = null;
    private Context mContext;

    public TerminalsAdapter(Activity a, ArrayList<Point> adapN, Context context) {
        this.mContext = context;
        activity = a;
        rData = adapN;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d(LOG_TAG, "я здесь - public NoteAdapter (Activity a, ArrayList<String> adapN, ArrayList<String> adapD, Context context)");
    }

    @Override
    public int getCount() {
        Log.d(LOG_TAG, "Вызов функции getCount");
        return rData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onClick(View v) {

    }

    public static class ViewHolder {
        public TextView text;
        public ImageView image;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {
            //****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.term_list, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.text = (TextView) vi.findViewById(R.id.textView8);
            holder.image = (ImageView) vi.findViewById(R.id.imageView);

            /************ Set holder with LayoutInflater ************/

            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        Point item = rData.get(position);
        Log.d(LOG_TAG, rData + "Note item = rData.get(position);");
        Context context = parent.getContext();
        holder.text.setText(item.getPoint_name());
        holder.image.setImageResource(R.drawable.google_maps);

        /******** Set Item Click Listner for LayoutInflater for each row ***********/

        vi.setOnClickListener(new OnItemClickListener(position, item));

        return vi;
    }

    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;
        private Point n;

        OnItemClickListener(int position, Point note) {
            mPosition = position;
            n = note;
        }

        @Override
        public void onClick(View arg0) {
            Intent myIntent = new Intent(mContext, OneTerminalActivity.class);
            Log.d("QWERTY", "Отправка " + n.toString());

            myIntent.putExtra("note_text", n.getPoint_name());
            myIntent.putExtra("point_lat", String.valueOf(n.getPoint_lat()));
            myIntent.putExtra("point_long", String.valueOf(n.getPoint_longt()));
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(myIntent);
        }

    }
}

