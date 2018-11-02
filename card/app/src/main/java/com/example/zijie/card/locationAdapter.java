package com.example.zijie.card;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.utils.DistanceUtil;

import java.math.BigDecimal;
import java.util.List;

public class locationAdapter extends ArrayAdapter<PoiInfo> {
    private Context context;
    private int id;

    public locationAdapter(@NonNull Context context, int resource, @NonNull List<PoiInfo> objects) {
        super(context, resource, objects);
        this.context =context;
        id = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PoiInfo poiInfo = getItem(position);
        View view = LayoutInflater.from(context).inflate(id,parent,false);
        double distance = DistanceUtil.getDistance(poiInfo.location,new LatLng(31.285815,121.473989));
        BigDecimal bigDecimal = new BigDecimal(distance/1000);
        TextView text = view.findViewById(R.id.bankText);
        text.setText(poiInfo.address);
        TextView title = view.findViewById(R.id.bankTitle);
        if(distance>=1000)
        {
            title.setText(poiInfo.name+"     距离"+bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP)+"km");
        }
        else
        title.setText(poiInfo.name+"     距离"+(int)distance+"m");

        return view;
    }

}
