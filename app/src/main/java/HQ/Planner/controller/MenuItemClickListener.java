//package HQ.Planner.controller;
//
//import android.content.Context;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.Toolbar;
//import android.view.MenuItem;
//
//import HQ.Planner.MainActivity;
//import HQ.Planner.view.fragments.HomeFragment;
//
//public class MenuItemClickListener
//        implements Toolbar.OnMenuItemClickListener {
//
//    Context activity;
//    Fragment fragment;
//
//
//    public MenuItemClickListener(Context activity) {
//        this.activity = activity;
//    }
//
//    public MenuItemClickListener(Fragment activity) {
//        this.fragment = activity;
//    }
//
//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        if (activity != null) {
//            if (activity instanceof MainActivity)
//                ((MainActivity) activity).toolbarActions(item);
//        }
//        else if (fragment!=null){
//            if (fragment instanceof HomeFragment){
//
//            }
//        }
//        return true;
//    }
//}
