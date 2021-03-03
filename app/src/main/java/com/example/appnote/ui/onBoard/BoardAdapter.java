package com.example.appnote.ui.onBoard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.appnote.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private String[] titles = new String [] {"Kyrgyzstan", "Information", "Sights"};
    private String[] desc = new String [] {"Welcome to Kyrgyzstan, my friend!",
            "The app contains a lot of useful information",
            "The app will show you all the sights"};
    private int [] images ={R.raw.anim1, R.raw.anim2,R.raw.anim3,};


    public interface OnStartCkickLictener {
        void onClick();
    }

    private OnStartCkickLictener onStartCkickLictener;

    public BoardAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // работает с вью элементами
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() { // определяем кол-во свайпов
        return 3;
    }

    public void setOnStartCkickLictener (OnStartCkickLictener onStartCkickLictener){
        this.onStartCkickLictener = onStartCkickLictener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private TextView textDesc;
        private ImageView imageView;
        private LottieAnimationView lottieAnimationView;


        private Button btnStart;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc = itemView.findViewById(R.id.textDesc);
            lottieAnimationView = itemView.findViewById(R.id.lottieView);
            btnStart = itemView.findViewById(R.id.btnStart);

            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStartCkickLictener.onClick();
                }
            });
        }

        public void bind(int position) {
            textTitle.setText(titles[position]); // метод для того что показывал разный текст в каждом свайпе
            textDesc.setText(desc[position]);
            lottieAnimationView.setAnimation(images[position]);

            btnStart.setVisibility(View.GONE); // кнопка старт показывает только на 3 странице view pager
            if (position ==2)
                btnStart.setVisibility(View.VISIBLE);
        }
    }
}
