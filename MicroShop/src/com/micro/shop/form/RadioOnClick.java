package com.micro.shop.form;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.micro.shop.entity.SearchResult;
import com.micro.shop.fragment.AdressFragment;
import com.micro.shop.fragment.SearchMainFragment;

/**
 * Created by 95 on 2015/4/30.
 */
public class RadioOnClick implements DialogInterface.OnClickListener {
    private int index;
    private Context context;
    private String[] areas = new String[]{"驾车","公交", "步行"};
    private SearchResult rs;
    private double latitude;
    private double longitude;
    SearchMainFragment mainFragment;

    public SearchResult getRs() {
        return rs;
    }

    public void setRs(SearchResult rs) {
        this.rs = rs;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public RadioOnClick(int index,Context context,SearchResult rs,SearchMainFragment mainFragment) {
        this.index = index;
        this.context=context;
        this.rs=rs;
        this.mainFragment=mainFragment;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public void onClick(DialogInterface dialog, int whichButton){
        setIndex(whichButton);
        mainFragment.changeWayFragment(1);
        mainFragment.adressFragment.showWay(whichButton,rs,latitude,longitude);
        dialog.dismiss();
    }
}
