package ph.aranzazushrine.aranzazuph;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ph.aranzazushrine.aranzazuph.Auth.LoginActivity;
import ph.aranzazushrine.aranzazuph.Fragments.BibleFragment;
import ph.aranzazushrine.aranzazuph.Fragments.MapsFragment;
import ph.aranzazushrine.aranzazuph.Fragments.MenuFragment;
import ph.aranzazushrine.aranzazuph.Fragments.NewsFragment;
import ph.aranzazushrine.aranzazuph.Fragments.ResourcesFragment;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    FirebaseUser currentUser;
    BottomNavigationView bottomNavigationView;
    NewsFragment newsFragment = new NewsFragment();
    MapsFragment mapsFragment = new MapsFragment();
    BibleFragment bibleFragment = new BibleFragment();
    ResourcesFragment resourcesFragment = new ResourcesFragment();
    MenuFragment menuFragment = new MenuFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_news);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_news:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.frameContainer, newsFragment).commit();
                return true;

            case R.id.menu_events:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.frameContainer, mapsFragment).commit();
                return true;

            case R.id.menu_bible:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.frameContainer, bibleFragment).commit();
                return true;

            case R.id.menu_resources:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.frameContainer, resourcesFragment).commit();
                return true;

            case R.id.menu_menu:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.frameContainer, menuFragment).commit();
                return true;
        }
        return false;
    }
}
