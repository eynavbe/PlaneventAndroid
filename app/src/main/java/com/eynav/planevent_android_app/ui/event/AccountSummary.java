package com.eynav.planevent_android_app.ui.event;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eynav.planevent_android_app.Event;
import com.eynav.planevent_android_app.R;
import com.eynav.planevent_android_app.ui.edit.EditFragment;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class AccountSummary extends Fragment {

    Event event;
    TextView tvNameAccountSummary;
    LinearLayout llListAccountSummary;
    Button btnAccountSummaryDetail;
    SharedPreferences shareName;
    String nameUser;
    String type;
    Long priceAll = 0L;
    public AccountSummary(Event event, String type) {
        this.event = event;
        this.type = type;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        shareName = getContext().getSharedPreferences("name", MODE_PRIVATE);
        nameUser = shareName.getString("name", "default if empty");
        String text = event.getTypeEvent() +" - "+ event.getLastNameEvent();

        ((AppCompatActivity) getContext()).getSupportActionBar().setTitle(text);
        return inflater.inflate(R.layout.fragment_account_summary, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNameAccountSummary = view.findViewById(R.id.tvNameAccountSummary);
        String text = event.getTypeEvent() +" - "+ event.getLastNameEvent();
        tvNameAccountSummary.setText(text);
        llListAccountSummary = view.findViewById(R.id.llListAccountSummary);
        btnAccountSummaryDetail = view.findViewById(R.id.btnAccountSummaryDetail);
        if (type.equals("client")){
            btnAccountSummaryDetail.setVisibility(View.GONE);
        }
        addNewBoxView(llListAccountSummary,"מחיר בסיסי : ",String.valueOf(event.getPrice()));
        priceAll+=event.getPrice();
        readChooseClientFromFirebase(event, llListAccountSummary, 0);

        btnAccountSummaryDetail.setOnClickListener(l ->{

//            String date = event.getDateEvent();
//            date = date.replaceAll("/","");
//            String eventname = event.getLastNameEvent()+date;
            Fragment myFragment = new EditFragment(event.getEmailClient1());
            AppCompatActivity activity = (AppCompatActivity) getContext();

            activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();

        });
    }

    private void readChooseClientFromFirebase(Event event, LinearLayout llListAccountSummary, int i) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        System.out.println(nameUser);
        System.out.println(event.getEmailClient1());
        String [] list = {"סלטים","בופה","אטרקציות","מנות ראשונות","מנות עיקריות","קינוחים","שונות","עיצוב האולם"};
        db.collection("client").document(event.getEmailClient1()).collection(nameUser).document("בחירות").collection(list[i]).whereGreaterThan("priceClient",0)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Long price = 0L;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("lllll","lllll");
                                System.out.println(document.getId());
//                                    String name = String.valueOf(document.getData().get("name"));
//                                    namesProduct.add(name);
//                                    String image = String.valueOf(document.getData().get("image"));
                                    Long priceClient = (Long)(document.getData().get("priceClient"));
//                                    Boolean inPrice = (Boolean)(document.getData().get("inPrice"));
//                                    Product product = new Product(name,price,inPrice,0L,image,false);
                                        System.out.println(document.getData());
                                price += priceClient;

                            }
                            if ( task.getResult().size() > 0){
                                priceAll+=price;
                                String text = "תוספת על ה" + list[i] +" : ";
                                addNewBoxView(llListAccountSummary,text,String.valueOf(price));

                            }

                            if (list.length-1 > i){
                                readChooseClientFromFirebase(event, llListAccountSummary, i+1);
                            }else {
                                addNewBoxView(llListAccountSummary,"------------- ","");

                                addNewBoxView(llListAccountSummary,"מחיר סכ'ה : ",String.valueOf(priceAll));

                            }

//                            addNewBoxView(llListAccountSummary);
                        } else {
                            readChooseClientFromFirebase(event, llListAccountSummary, i+1);

                            System.out.println("Error getting documents.");
                        }
                    }}).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        readChooseClientFromFirebase(event, llListAccountSummary, i+1);

                    }
                });
    }

    private void addNewBoxView(LinearLayout llListAccountSummary, String text, String price) {
        View boxViewEdit = getLayoutInflater().inflate(R.layout.account_summary_card_view, null, false);
        TextView tvTypeProductAccountSummary = boxViewEdit.findViewById(R.id.tvTypeProductAccountSummary);
        TextView tvPriceProductAccountSummary = boxViewEdit.findViewById(R.id.tvPriceProductAccountSummary);
        tvPriceProductAccountSummary.setText(price);
        tvTypeProductAccountSummary.setText(text);

        llListAccountSummary.addView(boxViewEdit);
    }


}