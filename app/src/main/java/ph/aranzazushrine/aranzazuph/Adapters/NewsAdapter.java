package ph.aranzazushrine.aranzazuph.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import ph.aranzazushrine.aranzazuph.Models.News;
import ph.aranzazushrine.aranzazuph.R;
import ph.aranzazushrine.aranzazuph.Utils.DateUtils;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private Context mContext;
    private List<News> news;
    private OnItemClickListener onItemClickListener;

    public NewsAdapter(Context mContext, List<News> news) {
        this.mContext = mContext;
        this.news = news;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_news, viewGroup, false);
        return new MyViewHolder(view, onItemClickListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        News model = news.get(i);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));

        Glide.with(mContext)
                .load(model.getMedia())
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(myViewHolder.newsImage);

        myViewHolder.newsHeader.setText(model.getTitle());
        myViewHolder.newsDesc.setText(model.getExcerpt());
        myViewHolder.newsTime.setText(DateUtils.DateToTimeFormat(model.getDate()));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView newsImage;
        TextView newsHeader, newsDesc, newsTime;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            newsImage = itemView.findViewById(R.id.newsImage);
            newsHeader = itemView.findViewById(R.id.newsHeader);
            newsDesc = itemView.findViewById(R.id.detailDesc);
            newsTime = itemView.findViewById(R.id.detailTime);

            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
