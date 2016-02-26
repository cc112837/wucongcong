package com.cc.wucongcong;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.cc.wucongcong.adapter.MyAdapter;
import com.cc.wucongcong.adapter.MyPagerAdapter;
import com.cc.wucongcong.entity.Data;
import com.cc.wucongcong.utils.MyHttpUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PullToRefreshListView lv_showid;
    private ViewPager vp_headid;
    private List<ImageView> imagelist;
    private MyAdapter adapter;//listview的
    private boolean isShowing = true;//判断当前图片轮询的viewpager是否正在显示


    private List<Data.DataEntity> datalist;
    private List<Data.DataEntity> entities;

    /**
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();//初始化界面控件
    }

    private void init() {

        lv_showid = ((PullToRefreshListView) findViewById(R.id.lv_showid));
        datalist = new LinkedList<>();
        ListView listView = lv_showid.getRefreshableView();
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.head_item, null);
        vp_headid = ((ViewPager) view.findViewById(R.id.vp_headid));

        listView.addHeaderView(view);//给listview添加头部
        MyHttpUtils.handData(handler, 3);
        aboutViewPager();//关于图片轮循的一些操作

    }

    private void aboutViewPager() {
        imagelist = new ArrayList<>();
        int images[] = {R.mipmap.e, R.mipmap.f, R.mipmap.g, R.mipmap.h};
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setMaxWidth(new DisplayMetrics().widthPixels);
            imageView.setImageResource(images[i]);
            imagelist.add(imageView);

        }
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(imagelist);
        vp_headid.setAdapter(myPagerAdapter);
        vp_headid.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % images.length);
        handler.sendEmptyMessageDelayed(6, 3000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        isShowing = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isShowing=true;
        handler.sendEmptyMessageDelayed(6, 3000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    adapter.setList(entities);
                    adapter.notifyDataSetChanged();
                    lv_showid.onRefreshComplete();
                    break;
                case 2:
                    adapter.setList(entities);
                    adapter.notifyDataSetChanged();
                    lv_showid.onRefreshComplete();
                    break;
                case 3:
                    Data data = ((Data) msg.obj);
                    entities = data.getData();
                    datalist.addAll(entities);
                    adapter = new MyAdapter(MainActivity.this, datalist);
                    lv_showid.setAdapter(adapter);
                    lv_showid.setMode(PullToRefreshBase.Mode.BOTH);
                    lv_showid.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                        @Override
                        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                            Log.i("刷新开始啦", "down 下拉开始");
                            handler.sendEmptyMessageDelayed(2, 3000);
                        }
                        @Override
                        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                            Log.i("刷新开始啦", "down 上拉开始");
                            handler.sendEmptyMessageDelayed(1, 3000);
                        }
                    });
                    break;
                case 6:
                    if (isShowing) {
                        vp_headid.setCurrentItem(vp_headid.getCurrentItem() + 1);
                        sendEmptyMessageDelayed(6, 3000);
                    }
                    break;
            }
        }
    };
}
