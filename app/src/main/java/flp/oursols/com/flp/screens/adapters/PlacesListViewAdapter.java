package flp.oursols.com.flp.screens.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import flp.oursols.com.flp.R;
import flp.oursols.com.flp.models.Place;

public class PlacesListViewAdapter extends ArrayAdapter<Place> {

    Context mContext;
    ArrayList<Place> mPlacesList;
    public PlacesListViewAdapter(Context context, ArrayList<Place> placesList){
        super(context, R.layout.places_listview_item, placesList);
        mContext = context;
        mPlacesList = new ArrayList<>();
        mPlacesList.addAll(placesList);
    }

    @Override
    public void addAll(Collection<? extends Place> collection) {
        super.addAll(collection);
        mPlacesList.addAll(collection);
    }

    @Override
    public int getCount() {
        return mPlacesList.size();
    }

    @Override
    public Place getItem(int position) {
        return mPlacesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {


            LayoutInflater inflater;
            inflater = ( LayoutInflater )mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.places_listview_item, null, false);
            holder = new ViewHolder();

            holder.tv = (TextView) convertView.findViewById(R.id.textViewPlaceItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv.setText(mPlacesList.get(position).name);
        return convertView;
    }

    private class ViewHolder
    {
        TextView tv;
    }
}
