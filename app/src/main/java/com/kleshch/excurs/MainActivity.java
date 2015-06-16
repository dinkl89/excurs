package com.kleshch.excurs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

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
import java.util.Locale;


public class MainActivity extends ActionBarActivity implements IFace {

    private FragmentManager manager= getFragmentManager();
    private Drawer.Result drawerResult;
    public static ImageLoader loader = ImageLoader.getInstance();
    private PointsList pointsList;
    private ProgressDialog dialog;

    @Override
    public void showDialog() {
        dialog = ProgressDialog.show(this, null, null, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dialog != null){
            hideDialog();
        }
    }

    @Override
    public void hideDialog() {
        dialog.dismiss();
    }

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
                .showImageForEmptyUri(android.R.drawable.ic_menu_close_clear_cancel)
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
                .setCustomAnimations(R.animator.slide_in, R.animator.slide_out, R.animator.pop_enter, R.animator.pop_exit)
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
    public boolean isEng() {
        return Locale.getDefault().getDisplayLanguage().equals("English");
    }

    @Override
    public void getExcursionsList() {
        FragmentExcursionsList f = new FragmentExcursionsList();

        manager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in, R.animator.slide_out, R.animator.pop_enter, R.animator.pop_exit)
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
                .setCustomAnimations(R.animator.slide_in, R.animator.slide_out, R.animator.pop_enter, R.animator.pop_exit)
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
                .setCustomAnimations(R.animator.slide_in, R.animator.slide_out, R.animator.pop_enter, R.animator.pop_exit)
                .replace(R.id.fragment_container, point)
                .addToBackStack("follow_to_point")
                .commit();
    }

    public void onUserSelectValue(int num){

        Bundle bundle = new Bundle();
        bundle.putInt("id", num);

        FragmentBriefing briefing = new FragmentBriefing();
        briefing.setArguments(bundle);

        manager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in, R.animator.slide_out, R.animator.pop_enter, R.animator.pop_exit)
                .replace(R.id.fragment_container, briefing)
                .addToBackStack("info")
                .commit();
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
