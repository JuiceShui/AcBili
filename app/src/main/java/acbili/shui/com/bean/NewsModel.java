package acbili.shui.com.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者： max_Shui on 2016/9/24.
 * 邮箱：shuicz0505@qq.com
 * 首页新闻的model
 */

public class NewsModel implements Serializable {
    private static final long serialVersionUID = 6852789669158358235L;
    public boolean status;
    public ArrayList<NewsContent> tngou;
    public int total;
    public  class  NewsContent implements  Serializable
    {
        public static final long serialVersionUID = 2197092315143258228L;
        public  int id;
        public String title;//资讯标题
        public int topclass;//一级分类
        public String img;//图片
        public String description;//描述
        public String keywords;//关键字
        public String message;//资讯内容
        public int count ;//访问次数
        public int fcount;//收藏数
        public int rcount;//评论读数
        public String fromname;//信息来源
        public String fromurl;//来源链接



        @Override
        public String toString() {
            return "NewsContent{" +
                    "title='" + title + '\'' +
                    ", topclass=" + topclass +
                    ", img='" + img + '\'' +
                    ", description='" + description + '\'' +
                    ", keywords='" + keywords + '\'' +
                    ", message='" + message + '\'' +
                    ", count=" + count +
                    ", fcount=" + fcount +
                    ", rcount=" + rcount +
                    ", fromname='" + fromname + '\'' +
                    ", fromurl='" + fromurl + '\'' +

                    '}';
        }
    }
    @Override
    public String toString() {
        return "NewsModel{" +
                "status=" + status +
                ", tngou=" + tngou +
                ", total=" + total +
                '}';
    }
}
