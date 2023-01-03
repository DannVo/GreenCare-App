package com.example.iotapplication.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iotapplication.R;
import com.example.iotapplication.SampleModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<SampleModel> sampleholder;
    private Context context;
    private plantOnClick selectPlant;
//    private OnTodoListener onTodoListener;


    public ItemAdapter(@NonNull Context context, @NonNull List<SampleModel> sampleholder, plantOnClick selectPlant) {
        this.sampleholder = sampleholder;
        this.selectPlant = selectPlant;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.single_sample_degisn,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.img.setImageResource(sampleholder.get(position).getImage());
        Bitmap mbitmap=((BitmapDrawable) holder.img.getResources().getDrawable(sampleholder.get(position).getImage())).getBitmap();
        Bitmap imageRounded=Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas=new Canvas(imageRounded);
        Paint mpaint=new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 30, 30, mpaint); // Round Image Corner 100 100 100 100
        holder.img.setImageBitmap(imageRounded);

        holder.header.setText(sampleholder.get(position).getHeader());;
        holder.desc.setText(sampleholder.get(position).getDesc());;
    }

    @Override
    public int getItemCount() {
        return sampleholder.size();
    }

    public interface plantOnClick{
        void plantOnClick(SampleModel sampleModel);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView header, desc;


        public ItemViewHolder(View itemView){
            super(itemView);
            img = itemView.findViewById(R.id.img1);
            header = itemView.findViewById(R.id.t1);
            desc = itemView.findViewById(R.id.t2);

//            ImageView mimageView=(ImageView) findViewById(R.id.imageView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectPlant.plantOnClick(sampleholder.get(getAdapterPosition()));
                }
            });

        }
    }


}
