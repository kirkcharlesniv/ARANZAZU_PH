package ph.aranzazushrine.aranzazuph;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ph.aranzazushrine.aranzazuph.Adapters.IntroViewPager;
import ph.aranzazushrine.aranzazuph.Auth.LoginActivity;
import ph.aranzazushrine.aranzazuph.Auth.RegisterActivity;
import ph.aranzazushrine.aranzazuph.Models.ScreenItem;

public class IntroActivity extends AppCompatActivity {
    IntroViewPager introViewPagerAdapter;
    TabLayout tabIndicator;
    Button buttonNext, buttonGetStarted;
    int position = 0;
    Animation buttonAnimation;
    private ViewPager screenPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (restorePrefData()) {
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);

            finish();
        }
        setContentView(R.layout.activity_intro);

        buttonNext = findViewById(R.id.button_next);
        buttonGetStarted = findViewById(R.id.button_get_started);
        tabIndicator = findViewById(R.id.tabIndicator);
        buttonAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);

        final List<ScreenItem> list = new ArrayList<>();
        list.add(new ScreenItem("News", "Always Updated.\nAlways Blessed.", R.drawable.ic_library_books));
        list.add(new ScreenItem("Map", "Where would you like to go?\nParish & MSK Location.", R.drawable.ic_pin_drop));
        list.add(new ScreenItem("Resources", "Reservations and Save Trees.\nPaperless Forms.", R.drawable.ic_create_new_folder));
        list.add(new ScreenItem("Bible", "Integrated Bible.\nAccessible Armor.", R.drawable.ic_import_contacts));
        list.add(new ScreenItem("Events", "Don't miss any events.\nSee you there!", R.drawable.ic_date_range));

        screenPager = findViewById(R.id.screen_pager);
        introViewPagerAdapter = new IntroViewPager(this, list);
        screenPager.setAdapter(introViewPagerAdapter);

        tabIndicator.setupWithViewPager(screenPager);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < list.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if (position == list.size() - 1) {
                    loadLastScreen();
                }
            }
        });

        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == list.size() - 1) {
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        buttonGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivity = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerActivity);

                savePrefsData();
                finish();
            }
        });
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroOpenedBefore = pref.getBoolean("isIntroOpened", false);
        return isIntroOpenedBefore;
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.commit();
    }

    private void loadLastScreen() {
        buttonNext.setVisibility(View.INVISIBLE);
        buttonGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);

        buttonGetStarted.setAnimation(buttonAnimation);
    }
}
