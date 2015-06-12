package com.kleshch.excurs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;


public class MainActivity extends ActionBarActivity implements IFace {

    FragmentManager manager= getFragmentManager();
    final String[] tempExcursionsList = {"Часовня во имя Святителя и Чудотворца Николая", "Экскурсия2", "Экскурсия3", "Экскурсия4", "Экскурсия5",
            "Экскурсия1", "Экскурсия2", "Экскурсия3", "Экскурсия4", "Экскурсия5",
            "Экскурсия1", "Экскурсия2", "Экскурсия3", "Экскурсия4", "Экскурсия5"};
    Drawer.Result drawerResult;
    public static ImageLoader loader = ImageLoader.getInstance();
    private PointsList pointsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();

        File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new FIFOLimitedMemoryCache(2*1024*1024))
                .diskCache(new UnlimitedDiscCache(cacheDir))
                .defaultDisplayImageOptions(options)
                .build();
        loader.init(configuration);

        pointsList = PointsList.getInstance(this);

        manager.beginTransaction()
                .replace(R.id.fragment_container, new FragmentHome())
                .addToBackStack("home")
                .commit();

        drawerResult = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_excursions).withIcon(R.drawable.ic_action_view_as_list).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_registration).withIcon(R.drawable.ic_action_labels).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawer_news).withIcon(R.drawable.ic_action_email).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.drawer_current_events).withIcon(R.drawable.ic_action_event).withIdentifier(4),
                        new PrimaryDrawerItem().withName(R.string.drawer_settings).withIcon(R.drawable.ic_action_settings).withIdentifier(5),
                        new PrimaryDrawerItem().withName(R.string.drawer_exit).withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(6)
                )
                .withSelectedItem(-1)
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        switch (iDrawerItem.getIdentifier()) {
                            case 1:
                                //get excursions list from back-end
                                getExcursionsList();
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                            case 4:
                                break;
                            case 5:
                                break;
                            case 6:
                                onExit();
                                break;
                        }
                    }
                })
                .build();
    }

    @Override
    public void getExcursionsList() {
        FragmentExcursionsList f = new FragmentExcursionsList();
        Bundle bundle = new Bundle();
        bundle.putStringArray("excursions", tempExcursionsList);

        f.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.fragment_container, f, "Excursions")
                .addToBackStack("Excursions")
                .commit();
    }

    @Override
    public void nextPoint(int id) {
        FragmentFollowToPoint point = new FragmentFollowToPoint();

        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        point.setArguments(bundle);

        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        manager.beginTransaction()
                .replace(R.id.fragment_container, point)
                .addToBackStack("follow_to_point")
                .commit();
    }

    @Override
    public void beginExcursion(int id) {

        pointsList.refreshList(id);
        int pointId = 0;

        FragmentFollowToPoint point = new FragmentFollowToPoint();

        Bundle bundle = new Bundle();
        bundle.putInt("id", pointId);
        point.setArguments(bundle);

        manager.beginTransaction()
                .replace(R.id.fragment_container, point)
                .addToBackStack("follow_to_point")
                .commit();
    }

    public void onUserSelectValue(int num, String str){
        Log.d("111", "You press excursion number " + num + ", action " + str);

        if(str.equals(getResources().getStringArray(R.array.dialog_items)[0])){
            Bundle bundle = new Bundle();
            bundle.putInt("id", num);

            FragmentBriefing briefing = new FragmentBriefing();
            briefing.setArguments(bundle);

            manager.beginTransaction()
                    .replace(R.id.fragment_container, briefing)
                    .addToBackStack("info")
                    .commit();
        } else {
            beginExcursion(num);
        }
    }

    public void beginExcursion() {
        //Start quest fragment

    }

    void onExit(){
        super.onBackPressed();
    }

    @Override
    public void onBackPressed(){

            if (drawerResult.isDrawerOpen()) {
                drawerResult.closeDrawer();
            } else {
                if (manager.getBackStackEntryCount()<2) {
                    drawerResult.openDrawer();
                } else {
                    manager.popBackStack();
                }
            }

    }
}
