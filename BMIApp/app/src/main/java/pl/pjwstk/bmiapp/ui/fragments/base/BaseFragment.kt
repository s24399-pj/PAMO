package pl.pjwstk.bmiapp.ui.fragments.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class BaseFragment extends Fragment {

    View rootView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fixLayout();
    }

    public void refreshLayout() {
        fixLayout();
    }

    protected void fixLayout() {
        if (rootView != null) {
            rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    rootView.requestLayout();
                }
            });
        }
    }
}