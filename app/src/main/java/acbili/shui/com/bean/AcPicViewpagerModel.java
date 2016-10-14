package acbili.shui.com.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者： max_Shui on 2016/9/28.
 * 邮箱：shuicz0505@qq.com
 */

public class AcPicViewpagerModel implements Serializable {
    private static final long serialVersionUID = -6530518559610566297L;
    public int id;
        public int  galleryclass ;//          图片分类
        public String title     ;//          标题
        public String img     ;//          图库封面
        public int count     ;//          访问数
        public int rcount     ;//           回复数
        public int fcount     ;//          收藏数
        public int size     ;//      图片多少张
        public ArrayList<picture> list;//图片集合
        public  class picture implements Serializable
        {
            private static final long serialVersionUID = 8983542389850735444L;
            public int id;
            public int gallery; //图片库
            public String src; //图片地址
        }

    }
