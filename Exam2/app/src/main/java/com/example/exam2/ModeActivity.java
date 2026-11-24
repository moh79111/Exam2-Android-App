package com.example.exam2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ModeActivity extends AppCompatActivity {
    private static final int MENU_LIGHT = 1;
    private static final int MENU_NIGHT = 2;
    private static final int MENU_SYSTEM = 3;

    private static final String[] TAB_TITLES = {"First", "Second", "Third"};

    private ViewPager2 viewPager;
    private TabLayoutMediator tabLayoutMediator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Modes");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new TabsAdapter(this));

        TabLayout tabs = findViewById(R.id.tabs);
        tabLayoutMediator = new TabLayoutMediator(tabs, viewPager,
                (tab, position) -> tab.setText(TAB_TITLES[position]));
        tabLayoutMediator.attach();

        findViewById(R.id.setMode).setOnClickListener(this::showModePopup);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (viewPager.getCurrentItem() != 0) {
                    viewPager.setCurrentItem(0, true);
                } else {
                    // Returning to MainActivity lets its double-back Snackbar run.
                    finish();
                }
            }
        });
    }

    private void showModePopup(View anchor) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        popupMenu.getMenu().add(0, MENU_LIGHT, 0, "Light").setCheckable(true);
        popupMenu.getMenu().add(0, MENU_NIGHT, 1, "Night").setCheckable(true);
        popupMenu.getMenu().add(0, MENU_SYSTEM, 2, "System").setCheckable(true);
        popupMenu.getMenu().setGroupCheckable(0, true, true);

        int currentMode = getSharedPreferences("settings", MODE_PRIVATE)
                .getInt("mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        int checkedItem = currentMode == AppCompatDelegate.MODE_NIGHT_NO
                ? MENU_LIGHT
                : currentMode == AppCompatDelegate.MODE_NIGHT_YES
                ? MENU_NIGHT
                : MENU_SYSTEM;
        popupMenu.getMenu().findItem(checkedItem).setChecked(true);

        popupMenu.setOnMenuItemClickListener(this::onModeSelected);
        popupMenu.show();
    }

    private boolean onModeSelected(MenuItem item) {
        int selectedMode;
        if (item.getItemId() == MENU_LIGHT) {
            selectedMode = AppCompatDelegate.MODE_NIGHT_NO;
        } else if (item.getItemId() == MENU_NIGHT) {
            selectedMode = AppCompatDelegate.MODE_NIGHT_YES;
        } else {
            selectedMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
        }

        getSharedPreferences("settings", MODE_PRIVATE)
                .edit()
                .putInt("mode", selectedMode)
                .apply();
        AppCompatDelegate.setDefaultNightMode(selectedMode);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        // The Up button always returns directly to MainActivity.
        finish();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            return onSupportNavigateUp();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (tabLayoutMediator != null) {
            tabLayoutMediator.detach();
        }
        super.onDestroy();
    }
}
