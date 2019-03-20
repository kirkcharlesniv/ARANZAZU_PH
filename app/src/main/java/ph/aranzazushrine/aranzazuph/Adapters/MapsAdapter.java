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

import ph.aranzazushrine.aranzazuph.Models.Maps;
import ph.aranzazushrine.aranzazuph.R;

public class MapsAdapter extends RecyclerView.Adapter<MapsAdapter.MyViewHolder> {
    private Context mContext;
    private List<Maps> mData;
    private static ClickListener clickListener;

    public MapsAdapter(Context mContext, List<Maps> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        MapsAdapter.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_maps, viewGroup, false);

        return new MyViewHolder(v);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));

        Glide.with(mContext)
                .load(mData.get(i).getmURL())
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(myViewHolder.mapImage);

        myViewHolder.mapTitle.setText(mData.get(i).getmTitle());
        myViewHolder.mapDesc.setText(mData.get(i).getmDesc());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mapImage;
        private TextView mapTitle, mapDesc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            mapImage = itemView.findViewById(R.id.mapImage);
            mapTitle = itemView.findViewById(R.id.mapTitle);
            mapDesc = itemView.findViewById(R.id.mapDesc);

        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

}
