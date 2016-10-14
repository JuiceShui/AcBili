package acbili.shui.com.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者： max_Shui on 2016/9/27.
 * 邮箱：shuicz0505@qq.com
 * 首页滚动的图片model
 */

public class HeaderMoldel implements Serializable {
    private static final long serialVersionUID = -887231006866075201L;
    public boolean status;
    public int total;
    public ArrayList<ListViewHeader> tngou;
    public  class ListViewHeader implements  Serializable
    {
        private static final long serialVersionUID = -3701082727040237432L;
        public int id;
        public int  galleryclass ;//          图片分类
        public String title     ;//          标题
        public String img     ;//          图库封面
        public int count     ;//          访问数
        public int rcount     ;//           回复数
        public int fcount     ;//          收藏数
        public int size     ;//      图片多少张
    }

}
