package acbili.shui.com.base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import acbili.shui.com.utils.StringUtils;

/**
 * 作者： max_Shui on 2016/10/1.
 * 邮箱：shuicz0505@qq.com
 */

public  abstract class BaseHttp<T> {
    public static String mURL="http://www.tngou.net";//所有网络请求的头!
    /**
     * 获取url的请求头
     * @return
     */
    public String getUrlHeader()
    {
        return mURL;
    }
    /**
     * 获取数据
     * @param position 从那一页开始返回
     * @return
     */
    public T getData(int position)
    {
        String data=null;//先从缓存中读取数据
        if (StringUtils.isEmpty(data)) {//如果数据为空
            try {
                data=get(position);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (data!=null) {//如果数据不为空
            T result=parseData(data);//解析成需要的类型
            return result;//返回解析后的数据
        }
        return null;//没有 返回空

    }

    public abstract String getKey();
    /**
     * 设置网络链接参数
     * @return
     */
    public abstract String getParams();
    public abstract T parseData(String data);
    /**
     * 获取数据的方式    子类可重写
     * @return  获取方式
     */
    //需要获取行数的
    public String getRow()
    {
        return "";
    }
    public  String get(int position) throws Exception {
        String Url;
        if (position!=0) {
            if (getPage()!=0)
            {
                Url=getUrlHeader()+getKey()+getParams()+"&page="+position;
            }
            else {
                Url = getUrlHeader() + getKey() + getParams() + "?page=" + position;
                if (!StringUtils.isEmpty( getRow() )) {
                    Url = getUrlHeader() + getKey() + getParams() + "?page=" + position + "&" + getRow();
                }
            }
        }
        else {
            Url=getUrlHeader()+getKey()+getParams();
        }
        String str= null;
        URL url = new URL(Url);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setReadTimeout(5000);//将读超时设置为指定的超时，以毫秒为单位。用一个非零值指定在建立到资源的连接后从 Input 流读入时的超时时间。如果在数据可读取之前超时期满，则会引发一个 java.net.SocketTimeoutException。
        con.setConnectTimeout(5000);// 设置链接超时的时间
        con.setDoInput(true);//指示应用程序要从 URL 连接读取数据。
        // 设置请求的头
        /////////////////////新加的
        con.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
        con.setRequestMethod("GET");//设置请求方式
        if(con.getResponseCode() == 200){//当请求成功时，接收数据（状态码“200”为成功连接的意思“ok”）
            InputStream is = con.getInputStream();
            str = formatIsToString(is);
        }

        return str;
    }
    public static String formatIsToString(InputStream is)throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1;
        try {
            while( (len=is.read(buf)) != -1){
                baos.write(buf, 0, len);
            }
            baos.flush();
            baos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(baos.toByteArray(),"utf-8");
    }
    protected int getPage()
    {
        return 0;
    }
}
