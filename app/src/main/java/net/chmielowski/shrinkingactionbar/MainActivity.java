package net.chmielowski.shrinkingactionbar;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ViewGroup layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
                return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(android.R.layout.simple_list_item_1, parent, false)
                ) {
                };
            }

            @Override
            public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
                ((TextView) holder.itemView.findViewById(android.R.id.text1)).setText(String.valueOf(position));
            }

            @Override
            public int getItemCount() {
                return 20;
            }
        });


        layout = findViewById(R.id.layout);
        final AppBarLayout appBarLayout = findViewById(R.id.appBar);
        final View bottom = findViewById(R.id.bottom);
        final ViewTreeObserver observer = bottom.getViewTreeObserver();
        final int h = getResources().getDisplayMetrics().heightPixels - getStatusBarHeight();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bottom.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Log.d("pchm", "getResources().getDisplayMetrics().heightPixels = " + h + " bottom.getHeight() = " + bottom.getHeight());
                final int screenHeight = h - bottom.getHeight();

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


    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    private static void adjustHeight(final ViewGroup layout, final int newHeight) {
        final ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.height = newHeight;
        layout.setLayoutParams(params);
    }
}
