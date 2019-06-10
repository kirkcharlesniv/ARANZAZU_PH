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

import java.util.List;

import ph.aranzazushrine.aranzazuph.Models.MenuItems;
import ph.aranzazushrine.aranzazuph.R;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {
    private static MenuAdapter.ClickListener clickListener;
    private Context mContext;
    private List<MenuItems> mData;

    public MenuAdapter(Context mContext, List<MenuItems> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public void setOnItemClickListener(MenuAdapter.ClickListener clickListener) {
        MenuAdapter.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MenuAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_menu, viewGroup, false);
        return new MenuAdapter.MyViewHolder(v);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.menuTitle.setText(mData.get(i).getTitle());
        myViewHolder.menuImage.setImageResource(mData.get(i).getImage());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView menuImage;
        private TextView menuTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            menuImage = itemView.findViewById(R.id.menuImage);
            menuTitle = itemView.findViewById(R.id.menuTitle);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
