package com.cc.wucongcong.utils;

import android.os.Handler;

import com.cc.wucongcong.entity.Data;
import com.cc.wucongcong.inter.Inter;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


/**

 */
public class MyHttpUtils extends HttpUtils implements Inter{
    private static MyHttpUtils httpUtils=new MyHttpUtils();
    public  static void sendData(HttpRequest.HttpMethod method,String url,RequestParams params,RequestCallBack requestCallBack){
        if(method== HttpRequest.HttpMethod.GET){
             httpUtils.send(method, url, requestCallBack);
        }
        else if (method== HttpRequest.HttpMethod.POST){
            httpUtils.send(method,url,params,requestCallBack);
        }
    }
  public static void handData(Handler handler,int what){
      RequestParams params = new RequestParams();
      params.addBodyParameter("uid", "9");
      params.addBodyParameter("lib_id", "1");
      params.addBodyParameter("lng", "116.403206");
      params.addBodyParameter("lat", "39.914555");
      params.addBodyParameter("page","1");
     sendData(HttpRequest.HttpMethod.POST,Inter.firstURI ,params, new MyCallBack(new Data(), handler, what));
  }
}
