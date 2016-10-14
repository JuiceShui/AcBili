package acbili.shui.com.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者： max_Shui on 2016/10/11.
 * 邮箱：shuicz0505@qq.com
 * 药品的详细信息
 */

public class AcDrugDetailModel implements Serializable {
    private static final long serialVersionUID = -1109286295789550005L;
    public String name;//名称
    public String img;//图片
    public String message;//内容
    public int count;//访问次数
    public int fcount;//收藏数
    public int rcount;//评论读数
    public String keywords;
    public String description;
    public float price;//价格
    public String tag; //标签
    public String type 	;//类型
    public ArrayList<code> codes;
    public ArrayList<number> numbers;

    /**
     * 条形码
     */
    public class code implements  Serializable
    {
        private static final long serialVersionUID = 7815400130433884304L;
        public String code;
        public int drug;
        public String factory;
        public int id;
    }

    /**
     * 国药准字
     */
    public class number implements Serializable
    {
        private static final long serialVersionUID = -7287411410851235407L;
        public String code;
        public int drug;
        public String factory;
        public int id;
    }
}
