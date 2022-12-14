package com.eynav.planevent_android_app.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.eynav.planevent_android_app.R;
import com.eynav.planevent_android_app.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    SharedPreferences shareType;
    String typePage;
    TextView tvWelcome;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("הבית");

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvWelcome = view.findViewById(R.id.tvWelcome);
        shareType = getContext().getSharedPreferences("type", MODE_PRIVATE);
        typePage = shareType.getString("type", "default if empty");
        if (typePage.equals("Hall")){
            String text1 = "ברוכים הבאים לאפליקציה"+"\n\n";
            String text2 = "תוכלו לשים את האולם והשירותים שלהם באפליקציה"+"\n\n";
            String text3 = "הלקוחות שלהם יוכלו לבחור מה הם רוצים באירוע שלהם בנוחות ובאופן מסודר במקום אחד ולהפיק אירוע הכולל כל מה שצריך באפליקציה."+"\n\n";
            String text4 = "תוכלו לבצע שיתופי פעולה עם עסקים קטנים וכך גם הם יוכלו להנות בעקיפין מהאפליקציה ואתם תהנו מההסכם שלכם עם העסקים. "+"\n\n";
            String text5 = text1+text2+text3+text4;
            tvWelcome.setText(text5);
        }
        if (typePage.equals("Client")) {
            String text1="ברוכים הבאים לאפליקציה"+"\n\n";
            String text2 = "זה המקום לאירגון האירוע שלכם"+"\n\n";
            String text3 = "אחרי סגירה עם בעל האולם אירוע תקבלו גישה לבחור מה האירוע שלהם יכלול גם מבחינת תפריט וגם מבחינת הדברים הנלווים לאירוע שהאולם מציע."+"\n\n";
            String text5 = text1+text2+text3;
            tvWelcome.setText(text5);
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}