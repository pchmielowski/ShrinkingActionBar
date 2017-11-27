package net.chmielowski.shrinkingactionbar;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    private ViewGroup layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        layout = findViewById(R.id.layout);
        final AppBarLayout appBarLayout = findViewById(R.id.appBar);
        final int screenHeight = getResources().getDisplayMetrics().heightPixels;

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
        appBarLayout.post(new Runnable() {
            @Override
            public void run() {
                adjustHeight(layout, screenHeight - appBarLayout.getBottom());
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                adjustHeight(layout, screenHeight - appBarLayout.getBottom());
            }
        });
    }

    private static void adjustHeight(final ViewGroup layout, final int newHeight) {
        final ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.height = newHeight;
        layout.setLayoutParams(params);
    }
}
