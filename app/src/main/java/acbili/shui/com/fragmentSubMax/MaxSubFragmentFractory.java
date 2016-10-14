package acbili.shui.com.fragmentSubMax;

import java.util.HashMap;

import acbili.shui.com.base.BaseFragment;

public class MaxSubFragmentFractory
{
	private static HashMap<Integer, BaseFragment> mFragmentMap=new HashMap<Integer, BaseFragment>();
	public static BaseFragment GetFragment(int postion) {
		BaseFragment fragment=mFragmentMap.get(postion);
		if (fragment==null) {
			switch (postion) {
			case 0:
				fragment=new FragmentTopic();
				break;
			case 1:
				fragment=new FragmentCook();
				break;
			case 2:
				fragment=new FragmentLore();
				break;
			case 3:
				fragment=new FragmentPlus();
				break;
			default:
				break;
			}
			mFragmentMap.put(postion, fragment);
		}
		return fragment;
	}
}
