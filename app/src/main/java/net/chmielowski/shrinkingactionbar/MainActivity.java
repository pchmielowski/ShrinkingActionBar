package net.chmielowski.shrinkingactionbar;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public class MainActivity extends AppCompatActivity {

    private ViewGroup contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ViewPager pager = findViewById(R.id.layout);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(final int position) {
                switch (position) {
                    case 0:
                        return new RecyclerFragment();
                    case 1:
                        return new RecyclerFragment();
                    default:
                        throw new RuntimeException();
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        });

        contentLayout = findViewById(R.id.layout);
        final AppBarLayout appBarLayout = findViewById(R.id.appBar);
        final View bottom = findViewById(R.id.bottom);
        final View container = findViewById(R.id.main_activity_container);
        final ViewTreeObserver observer = container.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bottom.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                final int screenHeight = container.getHeight() - bottom.getHeight();

                appBarLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        adjustHeight(contentLayout, screenHeight - appBarLayout.getBottom());
                    }
                });
                appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        adjustHeight(contentLayout, screenHeight - appBarLayout.getBottom());
                    }
                });
            }

        });


        findViewById(R.id.expand)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        appBarLayout.setExpanded(true);
                    }
                });

        findViewById(R.id.collapse)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        appBarLayout.setExpanded(false);
                    }
                });
    }


    private static void adjustHeight(final ViewGroup layout, final int newHeight) {
        final ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.height = newHeight;
        layout.setLayoutParams(params);
    }
}
