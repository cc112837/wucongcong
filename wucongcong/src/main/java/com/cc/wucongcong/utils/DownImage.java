package com.cc.wucongcong.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2015/11/6.
 */
public class DownImage {
    public static byte[] down(String str){
        try {
            URL url=new URL(str);
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            if(con.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStream is=con.getInputStream();
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                byte[] b=new byte[1024];
                int len=0;
                while((len=is.read(b))!=-1){
                    baos.write(b,0,len);
                }
                return  baos.toByteArray();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
