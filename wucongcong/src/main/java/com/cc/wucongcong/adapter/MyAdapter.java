package com.cc.wucongcong.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.wucongcong.R;
import com.cc.wucongcong.entity.Data;
import com.cc.wucongcong.utils.PicUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/4.
 */
public class MyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    List<Data.DataEntity> datas;
    private List<String> list = new ArrayList<>();
    private Context context;

    public MyAdapter(Context context, List<Data.DataEntity> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (datas != null) {
            return datas.size();
        }
        return 0;
    }

    public void setList(List<Data.DataEntity> datas) {
        this.datas = datas;
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
       final ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            view = inflater.inflate(R.layout.listview_items, null);
            vh.iv_headid = (ImageView) view.findViewById(R.id.iv_headid);
            vh.tv_titleid = (TextView) view.findViewById(R.id.tv_titleid);
            vh.tv_timeid = (TextView) view.findViewById(R.id.tv_timeid);
            vh.tv_nameid = (TextView) view.findViewById(R.id.tv_nameid);
            vh.tv_contentid = (TextView) view.findViewById(R.id.tv_contentid);
            vh.iv_coverid = (ImageView) view.findViewById(R.id.iv_coverid);
            vh.tv_booknameid = (TextView) view.findViewById(R.id.tv_booknameid);
            vh.tv_bookauthorid = (TextView) view.findViewById(R.id.tv_bookauthorid);
            vh.tv_typeid = (TextView) view.findViewById(R.id.tv_typeid);
            vh.tv_comcount = (TextView) view.findViewById(R.id.tv_comcount);
            vh.tv_praid = (TextView) view.findViewById(R.id.tv_pracount);
            vh.cb_prasid = (CheckBox) view.findViewById(R.id.cb_prasid);
            vh.iv_transid=(ImageView) view.findViewById(R.id.iv_transid);
            view.setTag(vh);
        } else {
            view = convertView;
            vh = (ViewHolder) view.getTag();
        }
        BitmapUtils bitmapUtils = new BitmapUtils(context);
        String avatar = datas.get(position).getUser_info().getAvatar();
        bitmapUtils.display(vh.iv_headid,avatar, new BitmapLoadCallBack<ImageView>() {
            @Override
            public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
                Bitmap aa= PicUtil.getRoundedCornerBitmap(bitmap,2);
                vh.iv_headid.setImageBitmap(aa);
            }
            @Override
            public void onLoadFailed(ImageView container, String uri, Drawable drawable) {
            }
        });
//
//        new AsyncTask<String, Void, byte[]>() {
//            @Override
//            protected byte[] doInBackground(String... params) {
//                return DownImage.down(params[0]);
//            }
//
//            @Override
//            protected void onPostExecute(byte[] bytes) {
//                Bitmap bm= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                Bitmap aa=PicUtil.getRoundedCornerBitmap(bm,2);
//                vh.iv_headid.setImageBitmap(aa);
//
//            }
//        }.execute(avatar);

        String time = datas.get(position).getCreate_time();
        Long c = Long.parseLong(time);
        long current=System.currentTimeMillis()/1000;
        long i=current-c;
        if ((i / 60) < 60) {
            long b = i  / 60;
            vh.tv_timeid.setText(b + "分钟前");
        } else if ((i / 60 / 60) < 24) {
            long b = i  / 60 / 60;
            vh.tv_timeid.setText(b + "小时前");
        } else {
            long b = i / 60 / 60 /24;
            vh.tv_timeid.setText(b + "天前");
        }
        vh.tv_nameid.setText(datas.get(position).getUser_info().getName());
        vh.tv_contentid.setText(datas.get(position).getBook_title());

        bitmapUtils.display(vh.iv_coverid, datas.get(position).getBook_info().getCover());
        vh.tv_booknameid.setText(datas.get(position).getBook_info().getTitle());
        vh.tv_bookauthorid.setText(datas.get(position).getBook_info().getAuthor());
        switch (position % 3) {
            case 0:
                vh.tv_titleid.setText("发布了一本漂流书");
                vh.tv_typeid.setBackgroundColor(Color.rgb(48,192,140));
                vh.tv_typeid.setText("漂\r\n流\r\n书");
                break;
            case 1:
                vh.tv_titleid.setText("发布了一心愿单");
                vh.tv_typeid.setBackgroundColor(Color.rgb(50, 189, 242));
                vh.tv_typeid.setText("心\r\n愿\r\n单");
                break;
            case 2:
                vh.tv_titleid.setText("发布了读书笔记");
                vh.tv_typeid.setBackgroundColor(Color.rgb(225,219,219));
                vh.tv_typeid.setText(" ");
                break;
        }
        vh.iv_transid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("转发界面");
                builder.setMessage("你确定要转发吗");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context,"转发成功",Toast.LENGTH_LONG).show();
                    }
                });
                builder.create().show();
            }
        });
        vh.tv_comcount.setText(datas.get(position).getComment_count());
        vh.tv_praid.setText(datas.get(position).getDigg_count());
        vh.cb_prasid.setTag(R.id.cb_prasid, position);
        vh.cb_prasid.setChecked(list.contains(position + ""));
        vh.cb_prasid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = (int) buttonView.getTag(R.id.cb_prasid);
                if (isChecked) {
                    list.add(position + "");
                } else {
                    list.remove(position + "");
                }
            }
        });
        return view;
    }

    private final class ViewHolder {
        private ImageView iv_headid;//用户头像
        private TextView tv_nameid;//用户姓名
        private TextView tv_titleid;//用户发布的内容标题
        private TextView tv_contentid;//用户发布的内容
        private TextView tv_timeid;//发布时间
        private ImageView iv_coverid;//书的封面
        private TextView tv_booknameid;//书名
        private TextView tv_bookauthorid;//书的作者
        private TextView tv_typeid;//用户发布的内容的类型
        private TextView tv_comcount;//评论数量
        private TextView tv_praid;//评论数量
        private CheckBox cb_prasid;
        private ImageView iv_transid;

    }

}
