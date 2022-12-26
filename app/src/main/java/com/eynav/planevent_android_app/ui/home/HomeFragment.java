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
            String text1 = "ברוכים הבאים ל planevent"+"\n\n";
            String text2 = "כאן תוכלו לפרסם מה האולם שלכם מציע כדי לשדרג את אירוע ולהפוך אותו לייחודי."+"\n\n";
            String text3 = "הלקוחות שלכם יראו את השירותים הנוספים שאתם מציעים ויכלו לבחור מה הם רוצים באירוע שלהם בנוחות מירבית."+"\n\n";
            String text4 = "אנחנו ממליצים ותומכים בשיתופי פעולה עם עסקים קטנים כדי שתוכלו להרחיב את ההיצע שלכם תוך חיזוק הכלכלה הישראלית."+"\n\n";
            String text5 = text1+text2+text3+text4;
            tvWelcome.setText(text5);
        }
        if (typePage.equals("Client")) {
            String text1="ברוכים הבאים ל planevent"+"\n\n";
            String text2 = "פה המקום לתכנן את האירוע שלכם באולם שבחרתם."+"\n\n";
            String text3 = "לאחר סגירת המקום עם בעל האולם תוכלו לבחור פה בנוחות מתוך מגוון השירותים שיש לאולם להציע."+"\n\n";
            String text4="החל בתפריט ועד לפרטים הכי קטנים שיהפכו את האירוע שלכם לבלתי נשכח."+"\n\n";
            String text5 = text1+text2+text3+text4;
            tvWelcome.setText(text5);
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}