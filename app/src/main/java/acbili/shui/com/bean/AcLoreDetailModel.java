package acbili.shui.com.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者： max_Shui on 2016/10/6.
 * 邮箱：shuicz0505@qq.com
 */

public class AcLoreDetailModel implements Serializable{
    private static final long serialVersionUID = 7637146388907642833L;
    public boolean status;
    public ArrayList<LoreDetail> tngou;
    public static class  LoreDetail  implements Serializable{
        private static final long serialVersionUID = 7396579012597660524L;
        public long id;
        public String title;//资讯标题
        public int infoclass;//分类
        public String img;//图片
        public String description;//描述
        public String keywords;//关键字
        public String message;
        public int count ;//访问次数
        public int fcount;//收藏数
        public int rcount;//评论读数
        //public long time;
    }

}
