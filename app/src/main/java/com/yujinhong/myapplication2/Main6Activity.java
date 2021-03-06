package com.yujinhong.myapplication2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.kakao.util.helper.log.Logger;
import com.yujinhong.myapplication2.ui.add.AddFragment;
import com.yujinhong.myapplication2.ui.home.HomeFragment;
import com.yujinhong.myapplication2.ui.my.MyFragment;
import com.yujinhong.myapplication2.ui.notifications.NotificationsFragment;
import com.yujinhong.myapplication2.ui.search.SearchFragment;
import com.yujinhong.myapplication2.ui.share.ShareFragment;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Main6Activity extends AppCompatActivity {
    static public TextView title;
    static public ActionBarDrawerToggle mDrawerToggle;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main6);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        final BottomNavigationView navViewBottom = (BottomNavigationView) findViewById(R.id.nav_view_bottom);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        NavigationUI.setupWithNavController(navViewBottom, navController);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));

        View viewActionBar = getLayoutInflater().inflate(R.layout.abs_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        getSupportActionBar().setCustomView(viewActionBar, params);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable= getResources().getDrawable(R.mipmap.ic_launcher);
        Bitmap bitmap = getBitmapFromDrawable(drawable);
        final Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));

//        getSupportActionBar().setHomeAsUpIndicator(newdrawable);
        toolbar.setNavigationIcon(newdrawable);
        title = viewActionBar.findViewById(R.id.actionbar_textview);
        title.setTextColor(Color.BLACK);
        title.setText("홈");
//
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText()=="디자인 의뢰 글쓰기") {
                    onBackPressed();
                    getSupportActionBar().setHomeAsUpIndicator(newdrawable);

                } else {
                    if(drawer.isDrawerOpen(GravityCompat.START)){
                        drawer.closeDrawer(GravityCompat.START);
                    }else {
                        drawer.openDrawer(GravityCompat.START);
                    }
                }
            }
        });
        mDrawerToggle.setDrawerIndicatorEnabled(true);


    }

    @NonNull
    public static Bitmap getBitmapFromDrawable(@NonNull Drawable drawable) {
        final Bitmap bmp = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bmp);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bmp;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main6, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
