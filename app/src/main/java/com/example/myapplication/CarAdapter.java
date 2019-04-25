

package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CarAdapter extends FirestoreRecyclerAdapter<CarInfo, CarAdapter.CarHolder> {


    public CarAdapter(@NonNull FirestoreRecyclerOptions<CarInfo> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull CarHolder holder, int position, @NonNull CarInfo model) {
        holder.textViewDistance.setText(model.getDistance());
        holder.textViewGpsDistance.setText(model.getGpsDistance());
        holder.textViewStartDate.setText((CharSequence) model.getStartDate());
        holder.textViewEndDate.setText((CharSequence) model.getEndDate());
        holder.textViewOrigin.setText(model.getOrigin());
        holder.textViewDestination.setText(model.getDestination());
        holder.textViewPurpose.setText(model.getPurpose());
        holder.textViewAmount.setText(model.getAmount());


    }

    @NonNull
    @Override
    public CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item,
                parent, false);

        return new CarHolder(v);
    }
    public void delteItem(int position ){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class CarHolder extends RecyclerView.ViewHolder {
        TextView textViewDistance;
        TextView textViewGpsDistance;
        TextView textViewStartDate;
        TextView textViewEndDate;
        TextView textViewOrigin;
        TextView textViewDestination;
        TextView textViewPurpose;
        TextView textViewAmount;

        public CarHolder(@NonNull View itemView) {
            super(itemView);
            textViewDistance = itemView.findViewById(R.id.text_view_distance);
            textViewGpsDistance = itemView.findViewById(R.id.text_view_gpsDistance);
            textViewStartDate = itemView.findViewById(R.id.text_view_startDate);
            textViewEndDate = itemView.findViewById(R.id.text_view_endDate);
            textViewOrigin = itemView.findViewById(R.id.text_view_origin);
            textViewDestination = itemView.findViewById(R.id.text_view_destination);
            textViewPurpose = itemView.findViewById(R.id.text_view_purpose);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);

        }

    }

}









