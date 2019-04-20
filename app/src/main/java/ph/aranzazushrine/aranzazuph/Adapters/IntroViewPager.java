package ph.aranzazushrine.aranzazuph.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ph.aranzazushrine.aranzazuph.Models.ScreenItem;
import ph.aranzazushrine.aranzazuph.R;

public class IntroViewPager extends PagerAdapter {
    Context context;
    List<ScreenItem> listScreen;

    public IntroViewPager(Context context, List<ScreenItem> listScreen) {
        this.context = context;
        this.listScreen = listScreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.intro_layout_screen, null);

        ImageView imageSlide = layoutScreen.findViewById(R.id.intro_image);
        TextView header = layoutScreen.findViewById(R.id.intro_header);
        TextView desc = layoutScreen.findViewById(R.id.intro_desc);

        header.setText(listScreen.get(position).getTitle());
        desc.setText(listScreen.get(position).getDescription());
        imageSlide.setImageResource(listScreen.get(position).getImage());

        container.addView(layoutScreen);
        return layoutScreen;
    }

    @Override
    public int getCount() {
        return listScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
