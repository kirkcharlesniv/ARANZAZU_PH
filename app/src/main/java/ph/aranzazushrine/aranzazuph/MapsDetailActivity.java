package ph.aranzazushrine.aranzazuph;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

public class MapsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_detail);

        ImageView detailImage = findViewById(R.id.mapImage);
        TextView detailTitle = findViewById(R.id.mapTitle);
        TextView detailDesc = findViewById(R.id.mapDesc);

        Intent intent = getIntent();
        String mapImage = intent.getStringExtra("image");
        String mapTitle = intent.getStringExtra("title");
        String mapDesc = intent.getStringExtra("desc");
        String mapDirections = intent.getStringExtra("directions");
        String mapContacts = intent.getStringExtra("contacts");

        RequestOptions requestOptions = new RequestOptions();
        Glide.with(this)
                .load(mapImage)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(detailImage);

        detailTitle.setText(mapTitle);
        detailDesc.setText(mapDesc);
    }
}
