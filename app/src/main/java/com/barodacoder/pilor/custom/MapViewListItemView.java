package com.barodacoder.pilor.custom;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.barodacoder.pilor.R;
import com.google.android.gms.maps.MapView;

/**
 * Created by Vaibhavi on 5/6/2017.
 */

public class MapViewListItemView extends LinearLayout {

    protected MapView mMapView;

    public MapViewListItemView(Context context) {
        this(context, null);
    }

    public MapViewListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setupView();
    }

    private void setupView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_cutter, this);
        mMapView = (MapView) view.findViewById(R.id.map);

        setOrientation(VERTICAL);
    }

    public void mapViewOnCreate(Bundle savedInstanceState) {
        if (mMapView != null) {
            mMapView.onCreate(savedInstanceState);
        }
    }

    public void mapViewOnResume() {
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    public void mapViewOnPause() {
        if (mMapView != null) {
            mMapView.onPause();
        }
    }

    public void mapViewOnDestroy() {
        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }

    public void mapViewOnLowMemory() {
        if (mMapView != null) {
            mMapView.onLowMemory();
        }
    }

    public void mapViewOnSaveInstanceState(Bundle outState) {
        if (mMapView != null) {
            mMapView.onSaveInstanceState(outState);
        }
    }
}