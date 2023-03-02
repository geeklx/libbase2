package com.geek.libbase.baserecycleview

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction

//vp_home.offscreenPageLimit = tempList.size
////        vp_home.offscreenPageLimit = 0
//val titlesString: List<String> = ArrayList()
//fenleiViewPagerAdapter1 = SlbBaseFragmentViewPagerAdapterlan(
//childFragmentManager,
//requireActivity(),
//titlesString,
//fgs,
//FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
//)
//fenleiViewPagerAdapter1!!.clear(vp_home)
//vp_home.adapter = fenleiViewPagerAdapter1
////
//requireActivity().window.decorView.postDelayed({
//    val msgIntent = Intent()
//    msgIntent.action = "ShouyeFragment"
//    msgIntent.putExtra("dibu", "1")
//    LocalBroadcastManagers.getInstance(activity).sendBroadcast(msgIntent)
//    //
////            vp_home.offscreenPageLimit = tempList.size
//}, 3100)
//}
//
//private var fenleiViewPagerAdapter1: SlbBaseFragmentViewPagerAdapterlan? = null

class SlbBaseFragmentViewPagerAdapterlan(
    private val fm: FragmentManager,
    private val mContext: Context,
    private val titles: List<String>,
    private val fragmentList: List<Fragment>,
    behavior: Int
) : FragmentPagerAdapter(
    fm, behavior
) {
    override fun getItem(i: Int): Fragment {
        return fragmentList[i]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (titles.size <= 0) {
            return null
        }
        return titles[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        fm.beginTransaction().remove((`object` as Fragment?)!!)
    }

    override fun getItemPosition(`object`: Any): Int {
        // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
        return POSITION_NONE
    }


    private var mCurTransaction: FragmentTransaction? = null

    /**
     * 清除缓存fragment
     *
     * @param container ViewPager
     */
    fun clear(container: ViewGroup) {
        if (mCurTransaction == null) {
            mCurTransaction = fm.beginTransaction()
        }
        for (i in 0 until fragmentList.size) {
            val itemId = getItemId(i)
            val name = makeFragmentName(container.id, itemId)
            val fragment = fm.findFragmentByTag(name)
            if (fragment != null) {
                //根据ID删除fragment，
                mCurTransaction!!.remove(fragment)
            }
        }
        mCurTransaction!!.commitNowAllowingStateLoss()
    }

    /**
     * 父类的该方法是私有的，故重新定义
     *
     * @param viewId
     * @param id
     * @return
     */
    private fun makeFragmentName(viewId: Int, id: Long): String {
        return "android:switcher:$viewId:$id"
    }


}