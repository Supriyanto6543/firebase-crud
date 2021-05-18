package com.diary.app.dashboard;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.diary.app.databinding.AdapterDiaryBinding;
import com.diary.app.databinding.EditDiaryBinding;
import com.diary.app.model.ModelDiary;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AdapterDiary extends RecyclerView.Adapter<AdapterDiary.AdapterHolder> {

    Context context;
    ArrayList<ModelDiary> arrayList;
    AdapterDiaryBinding binding;
    String keyId = "";
    String date;
    UpdateDiary diary;

    public AdapterDiary(Context context, ArrayList<ModelDiary> arrayList){
        this.context = context;
        this.arrayList = arrayList;
        diary = (UpdateDiary) context;
    }


    @NonNull
    @Override
    public AdapterDiary.AdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new AdapterHolder(AdapterDiaryBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDiary.AdapterHolder holder, int position) {
        ModelDiary modelDiary = arrayList.get(position);
        String title = modelDiary.getTitle();
        String description = modelDiary.getDescription();
        String date = modelDiary.getDate();

        holder.binding.title.setText(title);
        holder.binding.description.setText(description);
        holder.binding.date.setText(date);

        holder.binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDiary();
            }
        });

        holder.binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelDiary modelDiary1 = arrayList.get(position);
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                EditDiaryBinding binding = EditDiaryBinding.inflate(layoutInflater);
                editDiary(modelDiary1, binding);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class AdapterHolder extends RecyclerView.ViewHolder{

        AdapterDiaryBinding binding;

        public AdapterHolder(@NonNull AdapterDiaryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    private void editDiary(ModelDiary md, EditDiaryBinding editBinding){
        Dialog dialog = new Dialog(context);
        View view = editBinding.getRoot();
        dialog.setContentView(view);
        dialog.show();

        editBinding.title.setText(md.getTitle());
        editBinding.description.setText(md.getDescription());
        keyId = md.getUserid();

        editBinding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
                Calendar calendar = Calendar.getInstance();

                date = format.format(calendar.getTime());

                String title = editBinding.title.getText().toString();
                String description = editBinding.description.getText().toString();

                ModelDiary mdEdit = new ModelDiary(title, description, date, keyId);
                diary.UpdateDiary(mdEdit);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("editBerhasil", "EditBerhasil");
                        dialog.dismiss();
                    }
                }, 200);
            }
        });

    }

    private void deleteDiary(){

    }
}
