package com.moonsted.pilors.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moonsted.pilors.AppConstants;
import com.moonsted.pilors.R;

public class EmptyLayout extends LinearLayout {

    private Context context;
    private View rootView;
    private LayoutInflater inflater;
    private onRefreshListner onRefreshListner;
    private ImageView emptyImage;
    private TextView emptyTextView,emptyButton;

    public EmptyLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public interface  onRefreshListner
    {
        void onRefresh();
    }
    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.layout_empty_view, this, true);

        emptyImage = (ImageView) rootView.findViewById(R.id.emptyImage);
        emptyTextView = (TextView) rootView.findViewById(R.id.emptyTextView);
        emptyButton = (TextView) rootView.findViewById(R.id.emptyButton);

        // emptyTextView.setTypeface(LibFile.getRegularFont(context));
    }

    public void setType(int emptyType, final onRefreshListner onRefreshListner) {
        switch (emptyType) {
            case AppConstants.NO_BARBER:

                this.onRefreshListner=onRefreshListner;
                emptyImage.setImageResource(R.drawable.ic_explore);
                emptyTextView.setText(R.string.txt_no_barber_found);
                emptyButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRefreshListner.onRefresh();
                    }
                });
                break;

            case AppConstants.NO_BOOKING:

                this.onRefreshListner=onRefreshListner;
                emptyImage.setImageResource(R.drawable.ic_action_booking);
                emptyTextView.setText(R.string.txt_no_booking_found);
                emptyButton.setVisibility(GONE);
                break;

            case AppConstants.NO_RATING:

                this.onRefreshListner=onRefreshListner;
                emptyImage.setImageResource(R.drawable.star_for_ratings);
                emptyTextView.setText(R.string.txt_no_rating_found);
                emptyButton.setVisibility(GONE);
                break;

            case AppConstants.NO_HISTORY:

                this.onRefreshListner=onRefreshListner;
                emptyImage.setImageResource(R.drawable.ic_app_logo_white);
                emptyTextView.setText(R.string.txt_no_history_found);
                emptyButton.setVisibility(GONE);
                break;

          /*  case MyConstants.NO_DOCTOR:
                emptyImage.setImageResource(R.drawable.ic_doctor);
                emptyTextView.setText("No Doctors");
                break;

            case MyConstants.NO_PHARMACY:
                emptyImage.setImageResource(R.drawable.ic_maps_pin_drop);
                emptyTextView.setText(context.getString(R.string.no_pharmacy_found));
                break;

            case MyConstants.NO_APPOINTMENT:
                emptyImage.setImageResource(R.drawable.ic_add_appointment);
                emptyImage.setColorFilter(R.color.colorAccent, PorterDuff.Mode.SRC_ATOP);
                emptyTextView.setText("No Appointments");
                break;

            case MyConstants.NO_MEDICINE:
                emptyImage.setImageResource(R.drawable.ic_medicine);
                emptyTextView.setText("No Medicines");
                break;

            case MyConstants.NO_DATA_FOUND:
                emptyImage.setImageResource(R.drawable.ic_gesture_black_36dp);
                emptyImage.setColorFilter(ContextCompat.getColor(context, R.color.blue), PorterDuff.Mode.SRC_ATOP);
                emptyTextView.setText("No Data Found");
                break;*/
        }
    }
}
