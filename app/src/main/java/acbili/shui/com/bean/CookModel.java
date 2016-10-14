package acbili.shui.com.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者： max_Shui on 2016/9/27.
 * 邮箱：shuicz0505@qq.com
 * 菜谱的model
 */

public class CookModel implements Serializable {
    private static final long serialVersionUID = 3073540126533401226L;
    public boolean status;
    public int total;
    public ArrayList<CookDetail> tngou;
    public static class CookDetail implements  Serializable
    {
        private static final long serialVersionUID = 5514933903753709497L;
        public int id;
        public String name;//名称
        public String  food;//食物
        public String img;//图片
        public String images;//图片,
        public String description;//描述
        public String keywords;//关键字
        public String message;//资讯内容
        public int count ;//访问次数
        public int fcount;//收藏数
        public int rcount;//评论读数
    }
}
