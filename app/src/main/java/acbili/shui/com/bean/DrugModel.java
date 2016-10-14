package acbili.shui.com.bean;

import java.io.Serializable;
import java.util.ArrayList;

import acbili.shui.com.base.BaseRecyclerViewAdapter;

/**
 * 作者： max_Shui on 2016/9/28.
 * 邮箱：shuicz0505@qq.com
 * 药品model
 */

public class DrugModel implements Serializable {
    private static final long serialVersionUID = 2840270046377600808L;
    public boolean status;
    public ArrayList<Drug> tngou;
    public  class  Drug implements Serializable,BaseRecyclerViewAdapter.Item{
        private static final long serialVersionUID = -3463718087334543431L;
        public int id;
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

        @Override
        public int getItemType() {
            return 2;
        }
    }

}
