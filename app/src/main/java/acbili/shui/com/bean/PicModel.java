package acbili.shui.com.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者： max_Shui on 2016/9/26.
 * 邮箱：shuicz0505@qq.com
 * 图片的model结合
 */

public class PicModel implements Serializable {
    private static final long serialVersionUID = 1438955433982307234L;
    public  boolean status;
    public  int total;
    public ArrayList<PicDetail> tngou;
    public  class PicDetail implements Serializable
    {
        //图片的model
        private static final long serialVersionUID = 3773220192929955614L;
        public int id;
        public int  galleryclass ;//          图片分类
        public String title     ;//          标题
        public String img     ;//          图库封面
        public int count     ;//          访问数
        public int rcount     ;//           回复数
        public int fcount     ;//          收藏数
        public int size     ;//      图片多少张

        @Override
        public String toString() {
            return "PicDetail{" +
                    "id=" + id +
                    ", galleryclass=" + galleryclass +
                    ", title='" + title + '\'' +
                    ", img='" + img + '\'' +
                    ", count=" + count +
                    ", rcount=" + rcount +
                    ", fcount=" + fcount +
                    ", size=" + size +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PicModel{" +
                "status=" + status +
                ", total=" + total +
                ", tngou=" + tngou +
                '}';
    }
}
