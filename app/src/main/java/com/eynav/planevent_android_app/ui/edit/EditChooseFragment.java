package com.eynav.planevent_android_app.ui.edit;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eynav.planevent_android_app.R;
import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class EditChooseFragment extends Fragment {

    SharedPreferences shareType;
    String typePage;
    LinearLayout llListHallEdit;
    ImageButton imImageProductEdit;
    boolean test = true;
    List<Product> products = new ArrayList<>();
    ActivityResultLauncher<Intent> launcher;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        shareType = getContext().getSharedPreferences("type", MODE_PRIVATE);
        typePage = shareType.getString("type", "default if empty");
        if (typePage.equals("Hall")) {

            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("עריכה");
            return inflater.inflate(R.layout.fragment_hall_edit, container, false);

        }else {
            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("בחירות");

            return inflater.inflate(R.layout.fragment_client_choose, container, false);

        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (typePage.equals("Hall")) {
            TextView tvNameHallEdit = view.findViewById(R.id.tvNameHallEdit);

            String value = getArguments().getString("menuEdit");
            String text = "עריכת " + value;
            tvNameHallEdit.setText(text);
            launcher=
                    registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(ActivityResult result)->{
                        if(result.getResultCode()==RESULT_OK){
                            Uri uri=result.getData().getData();
                            imImageProductEdit.setImageURI(uri);
                            // Use the uri to load the image
                        }else if(result.getResultCode()== ImagePicker.RESULT_ERROR){
                            // Use ImagePicker.Companion.getError(result.getData()) to show an error
                        }
                    });

            ImageView imHallEditPlusChooseCountAll,imHallEditMinusChooseCountAll;
            TextView tvHallEditChooseCountAll;
            ImageView imHallEditPlusChooseFromAll, imHallEditMinusChooseFromAll;
            TextView tvHallEditChooseFromAll;
            imHallEditPlusChooseCountAll = view.findViewById(R.id.imHallEditPlusChooseCountAll);
            imHallEditMinusChooseCountAll = view.findViewById(R.id.imHallEditMinusChooseCountAll);
            tvHallEditChooseCountAll = view.findViewById(R.id.tvHallEditChooseCountAll);
            imHallEditPlusChooseFromAll = view.findViewById(R.id.imHallEditPlusChooseFromAll);
            imHallEditMinusChooseFromAll = view.findViewById(R.id.imHallEditMinusChooseFromAll);
            tvHallEditChooseFromAll = view.findViewById(R.id.tvHallEditChooseFromAll);
            imHallEditPlusChooseCountAll.setOnClickListener(l ->{
                tvHallEditChooseCountAll.setText(String.valueOf(Integer.parseInt(tvHallEditChooseCountAll.getText().toString()) + 1));
            });
            imHallEditMinusChooseCountAll.setOnClickListener(l ->{
                if (Integer.parseInt(tvHallEditChooseCountAll.getText().toString())  > 0) {
                    tvHallEditChooseCountAll.setText(String.valueOf(Integer.parseInt(tvHallEditChooseCountAll.getText().toString()) - 1));
                }
            });
            imHallEditPlusChooseFromAll.setOnClickListener(l ->{
                tvHallEditChooseFromAll.setText(String.valueOf(Integer.parseInt(tvHallEditChooseFromAll.getText().toString()) + 1));

            });
            imHallEditMinusChooseFromAll.setOnClickListener(l ->{
                if (Integer.parseInt(tvHallEditChooseFromAll.getText().toString())  > 0){
                    tvHallEditChooseFromAll.setText(String.valueOf(Integer.parseInt(tvHallEditChooseFromAll.getText().toString()) - 1));
                }

            });
            Button btnAddToListHallEdit;
            Button btnSaveListHallEdit;
            llListHallEdit = view.findViewById(R.id.llListHallEdit);
            btnAddToListHallEdit = view.findViewById(R.id.btnAddToListHallEdit);
            btnSaveListHallEdit = view.findViewById(R.id.btnSaveListHallEdit);

            readListHallEditInFirebase(llListHallEdit);

            btnAddToListHallEdit.setOnClickListener(l ->{
                addNewBoxView(llListHallEdit);
            });
            List<Product> products = new ArrayList<>();
            btnSaveListHallEdit.setOnClickListener(l ->{
                for (int i = 0; i < llListHallEdit.getChildCount(); i++) {
                    test = true;
                    View oneBoxHallEdit = llListHallEdit.getChildAt(i);
                    EditText etNameProductEdit = oneBoxHallEdit.findViewById(R.id.etNameProductEdit);
                    ImageView imgDeleteProductEdi = oneBoxHallEdit.findViewById(R.id.imgDeleteProductEdit);
                    EditText etPriceProductEdit = oneBoxHallEdit.findViewById(R.id.etPriceProductEdit);
                    Product product = new Product();

                    if (!etNameProductEdit.getText().toString().equals("")){
                        product.setName(etNameProductEdit.getText().toString());
                    }else {
                        test = false;
                    }
                    if (!etPriceProductEdit.getText().toString().equals("")){
                        product.setPrice(Integer.parseInt(etPriceProductEdit.getText().toString()));
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
                    saveListHallEditInFirebase(products);
                }

            });



        }
        if (typePage.equals("Client")) {
            TextView tvClientChooseName = view.findViewById(R.id.tvClientChooseName);

            String value = getArguments().getString("menuEdit");
            String text = "בחירת " + value;
            tvClientChooseName.setText(text);
            readDataOfProductsFromClientIfHaveElseFromHall();
            products.add(new Product("שם מוצר",10,"image",false));
            RecyclerView rvListClientChoose = view.findViewById(R.id.rvListClientChoose);
            Button btnSaveClientChoose = view.findViewById(R.id.btnSaveClientChoose);

            rvListClientChoose.setLayoutManager(new LinearLayoutManager(getContext()));
            ProductAdapter productAdapter = new ProductAdapter(getContext(), products);
            rvListClientChoose.setAdapter(productAdapter);
            btnSaveClientChoose.setOnClickListener(l ->{
                ArrayList<Product> productWithChooseOrNoClient = (ArrayList<Product>) productAdapter.getData();
//                for (int i = 0; i < values.size(); i++) {
//                    System.out.println(values.get(i));
//                    if (values.get(i).isChooseThis()){
//                        productsChoose.add(values.get(i));
//                    }
//                }
                saveInFirebaseClient(productWithChooseOrNoClient);
            });


        }
    }

    private void readListHallEditInFirebase(LinearLayout llListHallEdit) {
        View boxViewEdit = getLayoutInflater().inflate(R.layout.hall_edit_card_view, null, false);
        EditText etNameProductEdit = boxViewEdit.findViewById(R.id.etNameProductEdit);
        ImageView imgDeleteProductEdi = boxViewEdit.findViewById(R.id.imgDeleteProductEdit);
        EditText etPriceProductEdit = boxViewEdit.findViewById(R.id.etPriceProductEdit);
        imImageProductEdit = boxViewEdit.findViewById(R.id.imImageProductEdit);

//        TODO
//        etNameProductEdit.setText();
//        etPriceProductEdit.setText();
//        imgDeleteProductEdi.set

        imgDeleteProductEdi.setOnClickListener(l ->{
            removeBoxView(boxViewEdit);
        });
        imImageProductEdit.setOnClickListener(l ->{
            imImageProductEdit = boxViewEdit.findViewById(R.id.imImageProductEdit);
            imageBoxView(boxViewEdit);

        });
        llListHallEdit.addView(boxViewEdit);
    }

    private void readDataOfProductsFromClientIfHaveElseFromHall() {
        //        TODO
    }

    private void saveInFirebaseClient(ArrayList<Product> productWithChooseOrNoClient) {
//        TODO
        Toast.makeText(getContext(), "הבחירה נשמרה", Toast.LENGTH_SHORT).show();

    }

    private void saveListHallEditInFirebase(List<Product> products) {
        //        TODO
        Toast.makeText(getContext(), "נשמר", Toast.LENGTH_SHORT).show();


    }

    private void addNewBoxView(LinearLayout llListHallEdit) {
        View boxViewEdit = getLayoutInflater().inflate(R.layout.hall_edit_card_view, null, false);
        EditText etNameProductEdit = boxViewEdit.findViewById(R.id.etNameProductEdit);
        ImageView imgDeleteProductEdi = boxViewEdit.findViewById(R.id.imgDeleteProductEdit);
        EditText etPriceProductEdit = boxViewEdit.findViewById(R.id.etPriceProductEdit);
        imImageProductEdit = boxViewEdit.findViewById(R.id.imImageProductEdit);

        imgDeleteProductEdi.setOnClickListener(l ->{
            removeBoxView(boxViewEdit);
        });
        imImageProductEdit.setOnClickListener(l ->{
            imImageProductEdit = boxViewEdit.findViewById(R.id.imImageProductEdit);
            imageBoxView(boxViewEdit);

        });
        llListHallEdit.addView(boxViewEdit);
    }


    private void imageBoxView(View boxViewEdit) {
        ImageButton imImageProductEdit = boxViewEdit.findViewById(R.id.imImageProductEdit);


        ImagePicker.Companion.with(getActivity())
                .crop()
                .maxResultSize(512,512,true)
                .provider(ImageProvider.BOTH) //Or bothCameraGallery()
                .createIntentFromDialog(new Function1(){
                    public Object invoke(Object var1){
                        this.invoke((Intent)var1);
                        return Unit.INSTANCE;
                    }
                    public final void invoke(@NotNull Intent it){
                        Intrinsics.checkNotNullParameter(it,"it");
                        launcher.launch(it);
                    }
                });

    }

    private void removeBoxView(View boxViewEdit) {
        llListHallEdit.removeView(boxViewEdit);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}