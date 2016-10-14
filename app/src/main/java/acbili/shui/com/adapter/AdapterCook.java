package acbili.shui.com.adapter;


import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;

import acbili.shui.com.base.BaseHolder;
import acbili.shui.com.base.BaseHttp;
import acbili.shui.com.base.MyBaseAdapter;
import acbili.shui.com.bean.CookModel;
import acbili.shui.com.holder.HolderJokerNormal;

public class AdapterCook extends MyBaseAdapter<CookModel.CookDetail>
{
	private ProtocolCook protocol;
	private ArrayList<CookModel.CookDetail> mData;
	private CookModel model;
	public AdapterCook(ArrayList<CookModel.CookDetail> data)
	{
		super(data);
		protocol=new ProtocolCook();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<CookModel.CookDetail> onLoadMore() {
		// TODO Auto-generated method stub
		//ProtocolJoker protocol = new ProtocolJoker();
		// 下一页数据的位置就等于当前集合的大小
		//ArrayList<JokeContent> moreData = protocol.getData(((getCount()-1)/20)+1);
		mData=protocol.getData( ((getCount()-1)/20)+1   );
		return mData;
	}

	@Override
	public BaseHolder<CookModel.CookDetail> getNormalHolder(int position) {
		// TODO Auto-generated method stub
		return new HolderJokerNormal();
	}

	@Override
	public BaseHolder<CookModel.CookDetail> getSpecialHolder(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean CheckType(CookModel.CookDetail t) {
		// TODO Auto-generated method stub
		return false;
	}
	private class ProtocolCook extends BaseHttp<ArrayList<CookModel.CookDetail>>
	{

		@Override
		public String getKey() {
			return "/api/cook/list";
		}

		@Override
		public String getParams() {
			return "";
		}

		@Override
		public ArrayList<CookModel.CookDetail> parseData(String data) {
			if (!TextUtils.isEmpty( data ))
			{
				model=new Gson().fromJson( data,CookModel.class );
				return  model.tngou;
			}
			return null;
		}
	}
}
