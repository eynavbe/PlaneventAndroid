package com.eynav.planevent_android_app.ui.edit;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eynav.planevent_android_app.R;
import com.eynav.planevent_android_app.ui.event.EventsAdapter;

import java.util.ArrayList;
import java.util.List;

public class EditFragment extends Fragment  {

    SharedPreferences shareType;
    String typePage;
    Spinner spHallEditChooseFromAll,spHallEditChooseCountAll;
    String chooseCountAll = "";
    LinearLayout llListHallEdit;
    boolean test = true;
    List<Product> products = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        shareType = getContext().getSharedPreferences("type", MODE_PRIVATE);
        typePage = shareType.getString("type", "default if empty");
        if (typePage.equals("Hall")) {
            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("עריכה");
            return inflater.inflate(R.layout.fragment_hall_edit, container, false);

        }else {
            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("סיכום");
            return inflater.inflate(R.layout.fragment_client_summary, container, false);

        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (typePage.equals("Hall")) {
            spHallEditChooseFromAll = view.findViewById(R.id.spHallEditChooseFromAll);
//            spHallEditChooseCountAll = view.findViewById(R.id.spHallEditChooseCountAll);

            Button btnAddToListHallEdit;
            Button btnSaveListHallEdit;
            llListHallEdit = view.findViewById(R.id.llListHallEdit);
            btnAddToListHallEdit = view.findViewById(R.id.btnAddToListHallEdit);
            btnSaveListHallEdit = view.findViewById(R.id.btnSaveListHallEdit);
            btnAddToListHallEdit.setOnClickListener(l ->{
                addNewBoxView(llListHallEdit);
            });
            List<Product> products = new ArrayList<>();
            btnSaveListHallEdit.setOnClickListener(l ->{
                for (int i = 0; i < llListHallEdit.getChildCount(); i++) {
                    View oneBoxHallEdit = llListHallEdit.getChildAt(i);
                    EditText etNameProductEdit = oneBoxHallEdit.findViewById(R.id.etNameProductEdit);
                    ImageView imgDeleteProductEdi = oneBoxHallEdit.findViewById(R.id.imgDeleteProductEdit);
                    AppCompatSpinner etPriceProductEdit = oneBoxHallEdit.findViewById(R.id.etPriceProductEdit);
                    Product product = new Product();

                    if (!etNameProductEdit.getText().toString().equals("")){
                        product.setName(etNameProductEdit.getText().toString());
                    }else {
                        test = false;
                    }
                    if (test){
                        products.add(product);
                    }
                }
                if (products.size() == 0){
                    Toast.makeText(getContext(), "תוסיף קודם נתונים", Toast.LENGTH_SHORT).show();
                }else {
                    saveListHallEditInFirebase();
                }

            });



        }
        if (typePage.equals("Client")) {

            RecyclerView rvListClientChoose;
            rvListClientChoose = view.findViewById(R.id.rvListClientChoose);
            rvListClientChoose.setLayoutManager(new LinearLayoutManager(getContext()));
            ProductAdapter productAdapter = new ProductAdapter(getContext(), products);
            rvListClientChoose.setAdapter(productAdapter);

        }
    }

    private void saveListHallEditInFirebase() {
        Toast.makeText(getContext(), "נשמר", Toast.LENGTH_SHORT).show();


    }

    private void addNewBoxView(LinearLayout llListHallEdit) {
        View boxViewEdit = getLayoutInflater().inflate(R.layout.hall_edit_card_view, null, false);
        EditText etNameProductEdit = boxViewEdit.findViewById(R.id.etNameProductEdit);
        ImageView imgDeleteProductEdi = boxViewEdit.findViewById(R.id.imgDeleteProductEdit);
        AppCompatSpinner etPriceProductEdit = boxViewEdit.findViewById(R.id.etPriceProductEdit);
        imgDeleteProductEdi.setOnClickListener(l ->{
            removeBoxView(boxViewEdit);
        });
        llListHallEdit.addView(boxViewEdit);
    }
    private void removeBoxView(View boxViewEdit) {
        llListHallEdit.removeView(boxViewEdit);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}