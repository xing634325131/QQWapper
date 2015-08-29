package com.gugutian.qqwapper;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.gugutian.qqwapper.widget.SlidingMenu;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private SlidingMenu mMainSm;
    private ImageView mPictureIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findWidget();
        initWidget();
        setListener();
    }

    private void findWidget() {
        mMainSm = (SlidingMenu) findViewById(R.id.main_sm);
        mPictureIv = (ImageView) findViewById(R.id.picture_iv);
    }

    private void initWidget() {

    }

    private void setListener() {
        mPictureIv.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.picture_iv:
                if(mMainSm.isLeftMenuShown()) {
                    mMainSm.hideLeftMenu();
                } else {
                    mMainSm.showLeftMenu();
                }
                break;
        }
    }
}
