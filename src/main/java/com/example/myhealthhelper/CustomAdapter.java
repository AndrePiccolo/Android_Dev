package com.example.myhealthhelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<ExerciseData> list;

    public CustomAdapter(List<ExerciseData> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView totalTime;
        private final TextView totalDistance;
        private final TextView totalCalories;
        private final TextView totalSteps;
        private final TextView totalWalking;
        private final TextView totalRunning;
        private final TextView date;

        public ViewHolder(View view) {
            super(view);

            totalTime = view.findViewById(R.id.txtTotalTime);
            totalDistance = view.findViewById(R.id.txtDistance);
            totalCalories = view.findViewById(R.id.txtTotalCalories);
            totalSteps = view.findViewById(R.id.txtTotalSteps);
            totalWalking = view.findViewById(R.id.txtTotalWalking);
            totalRunning = view.findViewById(R.id.txtTotalRunning);
            date = view.findViewById(R.id.txtExerciseDate);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View listItem= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exercise_layout,
                viewGroup, false);

        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String totalTime = list.get(position).time + " Total Time";
        viewHolder.totalTime.setText(totalTime);
        String totalDistance = list.get(position).distance + " KM";
        viewHolder.totalDistance.setText(totalDistance);
        String totalCalories = list.get(position).calories + " Calories";
        viewHolder.totalCalories.setText(totalCalories);
        String totalSteps = list.get(position).steps + " Steps";
        viewHolder.totalSteps.setText(totalSteps);
        String totalWalking = list.get(position).walking + " Walking";
        viewHolder.totalWalking.setText(totalWalking);
        String totalRunning = list.get(position).running + " Running";
        viewHolder.totalRunning.setText(totalRunning);
        viewHolder.date.setText(list.get(position).date);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}