package com.eynav.planevent_android_app.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.eynav.planevent_android_app.CloudFunctions;
import com.eynav.planevent_android_app.Event;
import com.eynav.planevent_android_app.R;
import com.eynav.planevent_android_app.databinding.FragmentHomeBinding;
import com.eynav.planevent_android_app.ui.edit.EditChooseFragment;
import com.eynav.planevent_android_app.ui.event.AccountSummary;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctionsException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

public class HomeFragment extends Fragment {

    SharedPreferences shareType;
    String typePage;
    TextView tvWelcome;
    FloatingActionButton fabEditProfile;
    RelativeLayout stopperClient;
    CountdownView mCvCountdownView;
    TextView textViewContent;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("הבית");

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvWelcome = view.findViewById(R.id.tvWelcome);
        fabEditProfile = view.findViewById(R.id.fabEditProfile);
        stopperClient = view.findViewById(R.id.stopperClient);
         mCvCountdownView = view.findViewById(R.id.mycountdown);
         textViewContent = view.findViewById(R.id.textViewContent);
        shareType = getContext().getSharedPreferences("type", MODE_PRIVATE);
        typePage = shareType.getString("type", "default if empty");
        if (typePage.equals("Hall")){
            String text1 = "ברוכים הבאים ל planevent"+"\n\n";
            String text2 = "כאן תוכלו לפרסם מה האולם שלכם מציע כדי לשדרג את אירוע ולהפוך אותו לייחודי."+"\n\n";
            String text3 = "הלקוחות שלכם יראו את השירותים הנוספים שאתם מציעים ויכלו לבחור מה הם רוצים באירוע שלהם בנוחות מירבית."+"\n\n";
            String text4 = "אנחנו ממליצים ותומכים בשיתופי פעולה עם עסקים קטנים כדי שתוכלו להרחיב את ההיצע שלכם תוך חיזוק הכלכלה הישראלית."+"\n\n";
            String text5 = text1+text2+text3+text4;
            tvWelcome.setText(text5);
            fabEditProfile.setVisibility(View.VISIBLE);
        }
        if (typePage.equals("Client")) {
            String text1="ברוכים הבאים ל planevent"+"\n\n";
            String text2 = "פה המקום לתכנן את האירוע שלכם באולם שבחרתם."+"\n\n";
            String text3 = "לאחר סגירת המקום עם בעל האולם תוכלו לבחור פה בנוחות מתוך מגוון השירותים שיש לאולם להציע."+"\n\n";
            String text4="החל בתפריט ועד לפרטים הכי קטנים שיהפכו את האירוע שלכם לבלתי נשכח."+"\n\n";
            String text5 = text1+text2+text3+text4;
            tvWelcome.setText(text5);
            stopperClient.setVisibility(View.VISIBLE);
            getDateEvent();
        }
        fabEditProfile.setOnClickListener(l ->{
            Fragment myFragment = new ProfileFragment();
            AppCompatActivity activity = (AppCompatActivity) getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();

        });

    }

    private void getDateEvent() {
        SharedPreferences shareName = getContext().getSharedPreferences("emailClient", MODE_PRIVATE);
        String emailClient = shareName.getString("emailClient", "default if empty");
        SharedPreferences shareName1 = getContext().getSharedPreferences("hall", MODE_PRIVATE);
        String nameUser = shareName1.getString("hall", "default if empty");
        System.out.println("=========");
        System.out.println(emailClient);
        System.out.println(nameUser);
        new CloudFunctions().readEventsDataFromFirebase("hall", nameUser, "events")
                .addOnCompleteListener(new OnCompleteListener<List<Event>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Event>> task) {
                        System.out.println(task.toString());
                        System.out.println(task.getResult().toString());
                        if (task.isSuccessful()) {
                            for (int i = 0; i < task.getResult().size(); i++) {
                                System.out.println(task.getResult().get(i));
                                if (task.getResult().get(i).getEmailClient1().equals(emailClient)){
                                    Event event = new Event(task.getResult().get(i).getEmailClient1(),task.getResult().get(i).getEmailClient2(),task.getResult().get(i).getCountInvited(),task.getResult().get(i).getPrice(),task.getResult().get(i).getTypeEvent(),task.getResult().get(i).getDateEvent(),task.getResult().get(i).getLastNameEvent(),task.getResult().get(i).getHourEvent());
                                    System.out.println(event);
                                    setDateIn(event);

                                }
                                if (task.getResult().get(i).getEmailClient2().equals(emailClient)){
                                    Event event = new Event(task.getResult().get(i).getEmailClient1(),task.getResult().get(i).getEmailClient2(),task.getResult().get(i).getCountInvited(),task.getResult().get(i).getPrice(),task.getResult().get(i).getTypeEvent(),task.getResult().get(i).getDateEvent(),task.getResult().get(i).getLastNameEvent(),task.getResult().get(i).getHourEvent());
                                    System.out.println(event);

                                    setDateIn(event);
                                }
                            }


                        }else{


                            Exception e = task.getException();
                            if (e instanceof FirebaseFunctionsException) {
                                FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                                FirebaseFunctionsException.Code code = ffe.getCode();
                                Object details = ffe.getDetails();
                                System.out.println(details.toString());
                            }
                        }
                    }
                });
    }

    private void setDateIn(Event event) {
        StringBuilder date11 = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date1 = event.getDateEvent();
        System.out.println("________________");
        System.out.println(date1);
        String[] parts = date1.split("/");
        for (int i = 0; i < parts.length; i++) {
            if (i == 0 || i==1){
                if (parts[i].length() == 1){
                    date11.append("0").append(parts[i]).append("-");
                }else {
                    date11.append(parts[i]).append("-");
                }
            }else {
                date11.append(parts[i]);
            }
        }
        date11.append(" 00:00:00");
        for(String part: parts) {

            System.out.println(part);
        }
        String countDate = date11.toString();

//        String countDate = "01-01-2024 00:00:00";
        Date now = new Date();
        textViewContent.setText(event.getDateEvent());

        try {
            //Formatting from String to Date
            Date date = sdf.parse(countDate);
            long currentTime = now.getTime();
            long newYearDate = date.getTime();
            long countDownToNewYear = newYearDate - currentTime;
            mCvCountdownView.start(countDownToNewYear);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}