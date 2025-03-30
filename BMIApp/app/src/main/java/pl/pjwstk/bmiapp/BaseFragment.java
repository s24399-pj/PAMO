package pl.pjwstk.bmiapp;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Bazowa klasa dla wszystkich fragmentów w aplikacji
 * Zawiera wspólną logikę, np. naprawianie układu po przywróceniu fragmentu
 */
public abstract class BaseFragment extends Fragment {

    protected View rootView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Napraw układ za każdym razem, gdy fragment jest wznowiony
        fixLayout();
    }

    /**
     * Metoda naprawiająca układ fragmentu - powinna być dostosowana w klasach pochodnych
     */
    protected void fixLayout() {
        // Domyślna implementacja tylko zresetuje układ
        if (rootView != null) {
            rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    // Usuwamy listener, aby uniknąć wielokrotnego wywoływania
                    rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    // Odśwież układ - requestLayout wymusha ponowne obliczenie layoutu
                    rootView.requestLayout();
                }
            });
        }
    }
}