package com.example.study;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.ViewHolder>{

    private ArrayList<recylcer> data;
    private Context context;

    public recyclerAdapter(Context context,ArrayList<recylcer> list) {
        this.data = list;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView label;
        TextView title,author,price;
        RatingBar courseRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.Title);
            courseRating = itemView.findViewById(R.id.CourseRating);
            label = itemView.findViewById(R.id.banner);
            author = itemView.findViewById(R.id.AuthorName);
            price = itemView.findViewById(R.id.Price);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(),VideoPlayer.class);
                    context.startActivity(i);
                }
            });
        }
    }

    @NonNull
    @Override
    public recyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(recyclerAdapter.ViewHolder holder, int i) {
        holder.itemView.setTag(data.get(i));
        holder.label.setImageURI(Uri.parse(data.get(i).getLabel()));
        holder.title.setText(data.get(i).getTitle());
        holder.courseRating.setRating(data.get(i).getCourseRating());
        try{
            holder.author.setText(data.get(i).getAuthor());
            holder.price.setText(data.get(i).getPrice());
        }catch (Exception e){
            Log.d("Error",e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
