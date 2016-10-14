package acbili.shui.com.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者： max_Shui on 2016/9/28.
 * 邮箱：shuicz0505@qq.com
 * 健康知识的分类mode
 */

public class LoreGridModel implements Serializable{
    private static final long serialVersionUID = 6839034637002626638L;
    public boolean statu;
    public ArrayList<LoreGrid> tngou;
    public  class LoreGrid implements Serializable{
        private static final long serialVersionUID = -819858720200098008L;
        public int id;
        public String name;
        public String title;
        public String keywords;
        public String description;
        public int seq;//排序 从0。。。。10开始
    }

}
