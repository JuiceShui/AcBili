package acbili.shui.com.adapter;


import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;

import acbili.shui.com.base.BaseHolder;
import acbili.shui.com.base.BaseHttp;
import acbili.shui.com.base.MyBaseAdapter;
import acbili.shui.com.bean.NewsModel;
import acbili.shui.com.holder.HolderTopNews;

public class AdapterTopNews extends MyBaseAdapter<NewsModel.NewsContent>
{	private  Activity activity;
	private NewsModel newsModel;
	private ProtocolTopNews protocolTopNews;
	private ArrayList<NewsModel.NewsContent> arrayList;
	public AdapterTopNews(ArrayList<NewsModel.NewsContent> data)
	{
		super(data);
		protocolTopNews=new ProtocolTopNews();
		//this.activity=activity;
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<NewsModel.NewsContent> onLoadMore() {
		arrayList=protocolTopNews.getData( ((getCount()-1)/20)+1 );
		return arrayList;
	}

	@Override
	public BaseHolder<NewsModel.NewsContent> getNormalHolder(int position) {
		// TODO Auto-generated method stub

		return new HolderTopNews();
	}

	@Override
	public BaseHolder<NewsModel.NewsContent> getSpecialHolder(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean CheckType(NewsModel.NewsContent t) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean hasMore() {
		// TODO Auto-generated method stub
		return true;
	}
	private class ProtocolTopNews extends BaseHttp<ArrayList<NewsModel.NewsContent>> {
		@Override
		public String getKey() {
			// TODO Auto-generated method stub
			return "/api/top/list";
		}

		@Override
		public String getParams() {
			// TODO Auto-generated method stub
			return "";
		}

		@Override
		public ArrayList<NewsModel.NewsContent> parseData(String data) {
			if (!TextUtils.isEmpty( data )) {
				 newsModel = new Gson().fromJson( data, NewsModel.class );
			}

			return newsModel.tngou;
		}


	}
	}
