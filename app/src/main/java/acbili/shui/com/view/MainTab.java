package acbili.shui.com.view;
import acbili.shui.com.acbili.R;
import acbili.shui.com.fragment.ExploreFragment;
import acbili.shui.com.fragment.MaxFragment;
import acbili.shui.com.fragment.MeFragment;
import acbili.shui.com.fragment.NewsFragment;

public enum MainTab {

	NEWS(0, R.string.main_tab_name_news, R.drawable.tab_icon_new,
			MaxFragment.class),

	TWEET(1, R.string.main_tab_name_tweet, R.drawable.tab_icon_tweet,
			NewsFragment.class),

	EXPLORE(2, R.string.main_tab_name_explore, R.drawable.tab_icon_explore,
			ExploreFragment.class),
			
	ME(3, R.string.main_tab_name_my, R.drawable.tab_icon_me,
			MeFragment.class);

	private int idx;
	private int resName;
	private int resIcon;
	private Class<?> clz;

	private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
		this.idx = idx;
		this.resName = resName;
		this.resIcon = resIcon;
		this.clz = clz;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getResName() {
		return resName;
	}

	public void setResName(int resName) {
		this.resName = resName;
	}

	public int getResIcon() {
		return resIcon;
	}

	public void setResIcon(int resIcon) {
		this.resIcon = resIcon;
	}

	public Class<?> getClz() {
		return clz;
	}

	public void setClz(Class<?> clz) {
		this.clz = clz;
	}
}
