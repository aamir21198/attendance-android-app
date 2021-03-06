package io.github.djunicode.attendanceapp.TeacherSide.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.djunicode.attendanceapp.R;
import io.github.djunicode.attendanceapp.TeacherSide.Models.Student;


public class PickerAdapter extends RecyclerView.Adapter<PickerAdapter.PickerViewHolder>{
    private static final String TAG = "PickerAdapter";

    private ArrayList<Student> studentList;
    private PickerViewHolder.OnMarkedPresent mOnMarkedPresent;

    public void setAllStatus(boolean status){
        for (Student student : studentList) student.setPresent(status);
        notifyDataSetChanged();
    }

    public PickerAdapter(PickerViewHolder.OnMarkedPresent onMarkedPresent, ArrayList<Student> studentList) {
        this.studentList = studentList;
        this.mOnMarkedPresent = onMarkedPresent;
        Log.d(TAG, "PickerAdapter: " + studentList.get(0).getStudentName());
    }

    @NonNull
    @Override
    public PickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e(TAG, "onCreateViewHolder: created");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record,parent,false);
        return new PickerViewHolder(view,mOnMarkedPresent);
    }

    @Override
    public void onBindViewHolder(@NonNull PickerViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.nameTV.setText(student.getStudentName());
        holder.sapidTV.setText(student.getSapID());
        holder.presentStatus.setChecked(student.isPresent());
        holder.setStudent(student);
    }

    @Override
    public int getItemCount() {
        return ((studentList != null || studentList.size() != 0) ? studentList.size() : 0);
    }

    public void updateList(ArrayList<Student> newList){
        studentList = newList;
        notifyDataSetChanged();
    }

    public static class PickerViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "PickerViewHolder";

        CheckBox presentStatus;
        TextView nameTV;
        TextView sapidTV;
        View.OnClickListener listener;
        Student student;
        public interface OnMarkedPresent{
            void onMarkedPresent(boolean isIncreased);
        }
        OnMarkedPresent onMarkedPresent;

        public PickerViewHolder(final View view, final OnMarkedPresent onMarkedPresent){
            super(view);
            this.nameTV = view.findViewById(R.id.name_tv);
            this.sapidTV = view.findViewById(R.id.sapid_tv);
            this.presentStatus = view.findViewById(R.id.checkBox);
            this.listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v != presentStatus) presentStatus.toggle();
                    student.setPresent(presentStatus.isChecked());
                    onMarkedPresent.onMarkedPresent(presentStatus.isChecked());
                }
            };
            view.setOnClickListener(listener);
            presentStatus.setOnClickListener(listener);
        }

        public void setStudent(Student student){
            this.student = student;
        }
    }
}
