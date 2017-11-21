package com.androidprojects.sunilsharma.yogafitness.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidprojects.sunilsharma.yogafitness.Interface.ItemClickListener;
import com.androidprojects.sunilsharma.yogafitness.Model.Exercise;
import com.androidprojects.sunilsharma.yogafitness.R;
import com.androidprojects.sunilsharma.yogafitness.ViewExercise;

import java.util.List;

/**
 * Created by sunil sharma on 11/18/2017.
 */

class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public ImageView image;
    public TextView name;


    public ItemClickListener itemClickListener;

    public RecyclerViewHolder(View itemView)
    {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.ex_image);
        name = (TextView) itemView.findViewById(R.id.ex_name);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view)
    {
        itemClickListener.onClick(view , getAdapterPosition());
    }
}


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>
{
    List<Exercise> exerciseList;
    public Context context;


    public RecyclerViewAdapter(List<Exercise> exerciseList, Context context)
    {
        this.exerciseList = exerciseList;
        this.context = context;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_exercise , parent , false);

        return new RecyclerViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position)
    {
        holder.image.setImageResource(exerciseList.get(position).getImage_id());
        holder.name.setText(exerciseList.get(position).getName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position)
            {
                /** Now Here We have to Call The New Activity */
                Intent intent = new Intent(context , ViewExercise.class);
                intent.putExtra("image_id" , exerciseList.get(position).getImage_id());
                intent.putExtra("name" , exerciseList.get(position).getName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount()
    {
        return exerciseList.size();
    }
}
