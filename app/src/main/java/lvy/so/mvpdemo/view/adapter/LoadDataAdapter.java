package lvy.so.mvpdemo.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lvy.so.mvpdemo.R;
import lvy.so.mvpdemo.model.DataEntity;

/**
 * @author gping  email: gping.vip@gmail.com
 * @date Created by 2016/4/25.14:38
 * @filename LoadDataAdapter.class
 * @description
 * @TODO
 */
public class LoadDataAdapter extends RecyclerView.Adapter<LoadDataAdapter.LoadDataViewHolder> {

    private Context mContext;
    private List<DataEntity> mList;
    private LayoutInflater inflater;

    public LoadDataAdapter(Context context, List<DataEntity> list) {
        this.mContext = context;
        this.mList = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public LoadDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LoadDataViewHolder(inflater.inflate(R.layout.item_loaddata, parent, false));
    }

    @Override
    public void onBindViewHolder(LoadDataViewHolder holder, final int position) {
        int red = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);
        holder.ivShowPhoto.setBackgroundColor(Color.argb(204, red, green, blue));
        Glide.with(mContext).load(mList.get(position).getUrl()).centerCrop().into(holder.ivShowPhoto);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemRecycleViewListener != null) {
                    itemRecycleViewListener.onItemRecycleViewListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class LoadDataViewHolder extends RecyclerView.ViewHolder {
//        @Bind(R.id.iv_showPhoto)
        ImageView ivShowPhoto;
        CardView cardView;
        public LoadDataViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(mContext,itemView);
            ivShowPhoto = (ImageView) itemView.findViewById(R.id.iv_showPhoto);
            cardView = (CardView) itemView.findViewById(R.id.item_card_view);
        }
    }

    private OnItemRecycleViewListener itemRecycleViewListener;
    public void setOnItemRecycleViewListener(OnItemRecycleViewListener onItemRecycleViewListener) {
         this.itemRecycleViewListener = onItemRecycleViewListener;
    }
    public interface  OnItemRecycleViewListener{
        void onItemRecycleViewListener(int position);
    }

}
