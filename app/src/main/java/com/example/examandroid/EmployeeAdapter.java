package com.example.examandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter{

    Activity activity;
    List<Employee> listEmployee;
    Context mContext;

    public EmployeeAdapter(Context mContext, Activity activity, List<Employee> list){
        this.mContext = mContext;
        this.activity = activity;
        this.listEmployee = list;
    }
    public void ReloadData(List<Employee> list) {
        this.listEmployee = list;
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = activity.getLayoutInflater().inflate(R.layout.activitty_item, parent, false);
        EmployeeHoldeer holder = new EmployeeHoldeer(viewItem);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EmployeeHoldeer vh = (EmployeeHoldeer) holder;
        Employee model = listEmployee.get(position);
        vh.tv_name.setText(model.name+ "");
        vh.tv_desi.setText(model.desi);
        vh.tv_salary.setText(String.valueOf(model.salary));

        ((EmployeeHoldeer) holder).relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToDetail(model);
            }
        });
    }

    private void onClickGoToDetail(Employee employee){
        Intent intent = new Intent(mContext, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", employee);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return listEmployee.size();
    }

    public class EmployeeHoldeer extends RecyclerView.ViewHolder{
        TextView tv_name, tv_desi, tv_salary;
        LinearLayout relativeLayout;
        public EmployeeHoldeer(@NonNull View itemView){
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.layout_item);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_desi = itemView.findViewById(R.id.tv_desi);
            tv_salary = itemView.findViewById(R.id.tv_salary);
        }
    }
}
