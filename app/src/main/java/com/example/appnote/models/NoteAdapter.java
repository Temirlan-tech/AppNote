package com.example.appnote.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnote.App;
import com.example.appnote.R;
import com.example.appnote.OnItemClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    public ArrayList<Note> list;
    public OnItemClickListener click;


    public NoteAdapter() { // добавляем данные в recycler view
        list = new ArrayList<>();
        list.addAll(App.getDatabase().noteDao().getAll());
    }

    public void setClick(OnItemClickListener click) {
        this.click = click;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(Note note){
            list.add(0, note);
            notifyItemInserted(0);
    }

    public void sortData (List<Note> sort){ // метод для сортировки
        list.clear();
        list.addAll(sort);
        notifyDataSetChanged();
    }

    public void delete(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public Note getItem(int position) { // метод для того что бы получить из листа нужный нам элемент
        return list.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder { // для оптимизации списка которого не видно на экране

        private TextView textTitle;
        private TextView txtDate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.onClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    click.onLongClick(getAdapterPosition());
                    return false;
                }
            });
            textTitle = itemView.findViewById(R.id.textTitle);
            txtDate = itemView.findViewById(R.id.txtDate);
        }

        public void bind(Note note) { // один элемент из листа
                textTitle.setText(note.getTitle());
                long currentTimeMillis = note.getCreatedAt();
                DateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm");
                Calendar now = Calendar.getInstance();
                TimeZone timeZone = now.getTimeZone();
                dateFormat.setTimeZone(timeZone);
                Date date = new Date(currentTimeMillis);
                String currentTime = dateFormat.format(date);
                txtDate.setText(( currentTime));
        }
    }
}
