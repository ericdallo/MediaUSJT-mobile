package com.mediausjt.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.mediausjt.R;

public class FragmentUtil {

    public static boolean isLogic = true;
    public static boolean isEditGrade = false;
    private static FragmentManager manager;
    private static Fragment logicFragment;
    private static Fragment newGradeFragment;

    public static void showLogic() {
        hideFrag(newGradeFragment);
        showFrag(logicFragment);
        isLogic = true;
    }

    public static void showNewGrade() {
        hideFrag(logicFragment);
        showFrag(newGradeFragment);
        isLogic = false;
    }

    private static void showFrag(Fragment fragment) {
        FragmentTransaction ft = manager.beginTransaction();
        if(isLogic){
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        }else{
            ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        }
        ft.show(fragment);
        ft.commit();
    }

    private static void hideFrag(Fragment fragment) {
        FragmentTransaction ft = manager.beginTransaction();
        if(isLogic){
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        }else{
            ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
        ft.hide(fragment);
        ft.commit();
    }

    public static void setFragmentManager(FragmentManager manager) {
        FragmentUtil.manager = manager;

        logicFragment = manager.findFragmentById(R.id.logic_frag);
        newGradeFragment = manager.findFragmentById(R.id.new_grade_frag);
    }

    public static View getRootView() {
        return logicFragment.getView();
    }

}
