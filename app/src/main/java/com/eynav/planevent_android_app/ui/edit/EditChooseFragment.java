package com.eynav.planevent_android_app.ui.edit;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eynav.planevent_android_app.Loading;
import com.eynav.planevent_android_app.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EditChooseFragment extends Fragment {
    SharedPreferences shareType;
    String typePage;
    String name;
    SharedPreferences shareHall;
    SharedPreferences shareName;

    String hallName;
    LinearLayout llListHallEdit;
    ImageButton imImageProductEdit;
    boolean test = true;
    List<Product> products = new ArrayList<>();
    Activity activity;
    Uri downloadUrl;
    ImageView imHallEditPlusChooseCountAll, imHallEditMinusChooseCountAll;
    TextView tvHallEditChooseCountAll;
    ImageView imHallEditPlusChooseFromAll, imHallEditMinusChooseFromAll;
    TextView tvHallEditChooseFromAll;
    String value;
    ArrayList<String> namesProduct = new ArrayList<>();
    TextView tvClientChooseChoosingFewThings;
    RecyclerView rvListClientChoose;
    String emailClient;
    String from = "";
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public EditChooseFragment(){

    }
    public EditChooseFragment(String from){
        this.from = from;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareType = getContext().getSharedPreferences("type", MODE_PRIVATE);
        typePage = shareType.getString("type", "default if empty");
        shareName = getContext().getSharedPreferences("name", MODE_PRIVATE);
        name = shareName.getString("name", "default if empty");
        shareHall = getContext().getSharedPreferences("hall", MODE_PRIVATE);
        hallName = shareHall.getString("hall", "default if empty");
        SharedPreferences shareName = getContext().getSharedPreferences("emailClient", MODE_PRIVATE);
         emailClient = shareName.getString("emailClient", "default if empty");
        activity = getActivity();
        if (typePage.equals("Hall")) {
            if (from.equals("")) {
                ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("עריכה");
                return inflater.inflate(R.layout.fragment_hall_edit, container, false);

            }else {
//                ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("בחירות לקוח");
                return inflater.inflate(R.layout.fragment_client_choose, container, false);
            }

        } else {
            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("בחירות");
            return inflater.inflate(R.layout.fragment_client_choose, container, false);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (typePage.equals("Hall")) {
            if (from.equals("")) {
                TextView tvNameHallEdit = view.findViewById(R.id.tvNameHallEdit);
                value = getArguments().getString("menuEdit");
                String text = "עריכת " + value;
                tvNameHallEdit.setText(text);
                imHallEditPlusChooseCountAll = view.findViewById(R.id.imHallEditPlusChooseCountAll);
                imHallEditMinusChooseCountAll = view.findViewById(R.id.imHallEditMinusChooseCountAll);
                tvHallEditChooseCountAll = view.findViewById(R.id.tvHallEditChooseCountAll);
                imHallEditPlusChooseFromAll = view.findViewById(R.id.imHallEditPlusChooseFromAll);
                imHallEditMinusChooseFromAll = view.findViewById(R.id.imHallEditMinusChooseFromAll);
                tvHallEditChooseFromAll = view.findViewById(R.id.tvHallEditChooseFromAll);
                imHallEditPlusChooseCountAll.setOnClickListener(l -> {
                    tvHallEditChooseCountAll.setText(String.valueOf(Integer.parseInt(tvHallEditChooseCountAll.getText().toString()) + 1));
                });
                imHallEditMinusChooseCountAll.setOnClickListener(l -> {
                    if (Integer.parseInt(tvHallEditChooseCountAll.getText().toString()) > 0) {
                        tvHallEditChooseCountAll.setText(String.valueOf(Integer.parseInt(tvHallEditChooseCountAll.getText().toString()) - 1));
                    }
                });
                imHallEditPlusChooseFromAll.setOnClickListener(l -> {
                    tvHallEditChooseFromAll.setText(String.valueOf(Integer.parseInt(tvHallEditChooseFromAll.getText().toString()) + 1));

                });
                imHallEditMinusChooseFromAll.setOnClickListener(l -> {
                    if (Integer.parseInt(tvHallEditChooseFromAll.getText().toString()) > 0) {
                        tvHallEditChooseFromAll.setText(String.valueOf(Integer.parseInt(tvHallEditChooseFromAll.getText().toString()) - 1));
                    }

                });
                Button btnAddToListHallEdit;
                Button btnSaveListHallEdit;
                llListHallEdit = view.findViewById(R.id.llListHallEdit);
                btnAddToListHallEdit = view.findViewById(R.id.btnAddToListHallEdit);
                btnSaveListHallEdit = view.findViewById(R.id.btnSaveListHallEdit);
                readCountHallEditInFirebase(value, llListHallEdit);
                btnAddToListHallEdit.setOnClickListener(l -> {
                    addNewBoxView(llListHallEdit);
                });
                btnSaveListHallEdit.setOnClickListener(l -> {
                    List<ImageView> imageViewList = new ArrayList<>();
                    for (int i = 0; i < llListHallEdit.getChildCount(); i++) {
                        test = true;
                        View oneBoxHallEdit = llListHallEdit.getChildAt(i);
                        EditText etNameProductEdit = oneBoxHallEdit.findViewById(R.id.etNameProductEdit);
                        ImageView imImageProductEdit = oneBoxHallEdit.findViewById(R.id.imImageProductEdit);
                        EditText etPriceProductEdit = oneBoxHallEdit.findViewById(R.id.etPriceProductEdit);
                        CheckBox cbInProductEdit = oneBoxHallEdit.findViewById(R.id.cbInProductEdit);
                        Product product = new Product();
                        if (!etNameProductEdit.getText().toString().equals("")) {
                            product.setName(etNameProductEdit.getText().toString());
                        } else {
                            test = false;
                        }
                        if (!etPriceProductEdit.getText().toString().equals("")) {
                            product.setPrice(Long.parseLong(etPriceProductEdit.getText().toString()));
                        } else {
                            test = false;
                        }
                        if (cbInProductEdit.isChecked()) {
                            product.setInPrice(true);
                        } else {
                            product.setInPrice(false);
                        }
                        if (test) {
                            products.add(product);
                            imageViewList.add(imImageProductEdit);
                        }
                    }
                    if (products.size() == 0) {
                        Toast.makeText(getContext(), "תוסיף קודם נתונים", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadMethod(imageViewList, 0);
                    }
                });
            }else {
                TextView tvClientChooseName = view.findViewById(R.id.tvClientChooseName);
                value = getArguments().getString("menuEdit");
                String text = "בחירת " + value;
                tvClientChooseName.setText(text);
                TextView tvClientChooseChoosingFewThings= view.findViewById(R.id.tvClientChooseChoosingFewThings);
                TextView tvClientChooseInAdditionThere= view.findViewById(R.id.tvClientChooseInAdditionThere);
                Button btnSaveClientChoose = view.findViewById(R.id.btnSaveClientChoose);
                tvClientChooseChoosingFewThings.setVisibility(View.GONE);
                tvClientChooseInAdditionThere.setVisibility(View.GONE);
                btnSaveClientChoose.setVisibility(View.GONE);
                readListOfProductsFromClientIfHaveElseFromClient(value,hallName,from);
                rvListClientChoose = view.findViewById(R.id.rvListClientChoose);
                rvListClientChoose.setLayoutManager(new LinearLayoutManager(getContext()));
                ProductAdapter productAdapter = new ProductAdapter(getContext(), products, 0 , 0);
                rvListClientChoose.setAdapter(productAdapter);
            }
        }
        if (typePage.equals("Client")) {
            TextView tvClientChooseName = view.findViewById(R.id.tvClientChooseName);
            value = getArguments().getString("menuEdit");
            String text = "בחירת " + value;
            tvClientChooseName.setText(text);
            tvClientChooseChoosingFewThings = view.findViewById(R.id.tvClientChooseChoosingFewThings);
            readDataOfProductsFromClientIfHaveElseFromHall(value,hallName);
            rvListClientChoose = view.findViewById(R.id.rvListClientChoose);
            Button btnSaveClientChoose = view.findViewById(R.id.btnSaveClientChoose);
            rvListClientChoose.setLayoutManager(new LinearLayoutManager(getContext()));
            ProductAdapter productAdapter = new ProductAdapter(getContext(), products, 0 , 0);
            rvListClientChoose.setAdapter(productAdapter);
            btnSaveClientChoose.setOnClickListener(l -> {
                ArrayList<Product> productWithChooseOrNoClient = (ArrayList<Product>) productAdapter.getData();
                saveInFirebaseClient(productWithChooseOrNoClient, 0);
            });
        }
    }

    private void uploadMethod(List<ImageView> imageViewList, int i) {
        ImageView imImage = imageViewList.get(i);
        Loading loadingdialog = new Loading(activity);
        loadingdialog.startLoadingdialog();
        String date = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Calendar.getInstance().getTime()) + String.valueOf(i);
        StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("hall").child("/" + name).child("/" + date + ".jpg");
        imageRef.putBytes(getByteArray(imImage))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                downloadUrl = uri;
                                products.get(i).setImage(downloadUrl.toString());
                                if ( i < products.size()-1){
                                    loadingdialog.dismissdialog();
                                    uploadMethod(imageViewList, i+1);
                                }else {
                                    saveCountHallEditInFirebase(value, tvHallEditChooseCountAll.getText().toString(), tvHallEditChooseFromAll.getText().toString());
                                }
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        //and displaying error message
                        Toast.makeText(getActivity(), exception.getCause().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });

    }

    public byte[] getByteArray(ImageView imageView) {
        // Get the data from an ImageView as bytes
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;
    }

    private void readDataOfProductsFromClientIfHaveElseFromHall(String value, String nameHall) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("hall").document(nameHall).collection(value).document("count")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                if (document.getId().equals("count")) {
                                    String chooseCountAll = String.valueOf(document.getData().get("chooseCountAll"));
                                    String chooseFromAll = String.valueOf(document.getData().get("chooseFromAll"));
                                    String text = "נא לבחור "+chooseFromAll+" מתוך "+chooseCountAll+" שכלולים במחיר.";
                                    tvClientChooseChoosingFewThings.setText(text);
                                    readListOfProductsFromClientIfHaveElseFromClient(value,nameHall,chooseFromAll,chooseFromAll);
                                }
                            }
                        }
                    }
                });
    }
    private void readListOfProductsFromClientIfHaveElseFromClient(String value, String nameHall, String chooseFromAll, String fromAll){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("client").document(emailClient).collection(hallName).document("בחירות").collection(value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = String.valueOf(document.getData().get("name"));
                                String image = String.valueOf(document.getData().get("image"));
                                Long price = (Long)(document.getData().get("price"));
                                Boolean inPrice = (Boolean)(document.getData().get("inPrice"));
                                Boolean chooseThis = (Boolean)(document.getData().get("chooseThis"));
                                Long priceClient = (Long)(document.getData().get("priceClient"));
                                Product product = new Product(name,price,inPrice,priceClient,image,chooseThis);
                                products.add(product);
                            }
                            rvListClientChoose.setLayoutManager(new LinearLayoutManager(getContext()));
                            ProductAdapter productAdapter = new ProductAdapter(getContext(), products,Integer.parseInt(chooseFromAll),Integer.parseInt(fromAll));
                            rvListClientChoose.setAdapter(productAdapter);
                            if (products.size() == 0){
                                readListOfProductsFromClientIfHaveElseFromHall(value,nameHall,chooseFromAll,chooseFromAll);
                            }
                        } else {
                            readListOfProductsFromClientIfHaveElseFromHall(value,nameHall,chooseFromAll,chooseFromAll);
                        }
                    }}).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        readListOfProductsFromClientIfHaveElseFromHall(value,nameHall,chooseFromAll,chooseFromAll);
                    }
                });
    }
    private void readListOfProductsFromClientIfHaveElseFromClient(String value, String nameHall, String emailClient){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("client").document(emailClient).collection(hallName).document("בחירות").collection(value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = String.valueOf(document.getData().get("name"));
                                String image = String.valueOf(document.getData().get("image"));
                                Long price = (Long)(document.getData().get("price"));
                                Boolean inPrice = (Boolean)(document.getData().get("inPrice"));
                                Boolean chooseThis = (Boolean)(document.getData().get("chooseThis"));
                                Long priceClient = (Long)(document.getData().get("priceClient"));
                                Product product = new Product(name,price,inPrice,priceClient,image,chooseThis);

                                if (chooseThis != null && chooseThis){
                                    products.add(product);
                                }
                            }
                            rvListClientChoose.setLayoutManager(new LinearLayoutManager(getContext()));
                            ProductAdapter productAdapter = new ProductAdapter(getContext(), products);
                            rvListClientChoose.setAdapter(productAdapter);

                        } else {
                        }
                    }}).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }
    private void readListOfProductsFromClientIfHaveElseFromHall(String value, String nameHall, String chooseFromAll, String fromAll){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("hall").document(nameHall).collection(value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getId());
                                if (!document.getId().equals("count")){
                                    String name = String.valueOf(document.getData().get("name"));
                                    String image = String.valueOf(document.getData().get("image"));
                                    Long price = (Long)(document.getData().get("price"));
                                    Boolean inPrice = (Boolean)(document.getData().get("inPrice"));
                                    Product product = new Product(name,price,inPrice,0L,image,false);
                                    products.add(product);
                                }
                            }
                            rvListClientChoose.setLayoutManager(new LinearLayoutManager(getContext()));
                            ProductAdapter productAdapter = new ProductAdapter(getContext(), products,Integer.parseInt(chooseFromAll),Integer.parseInt(fromAll));
                            rvListClientChoose.setAdapter(productAdapter);
                        } else {
                            System.out.println("Error getting documents.");
                        }
                    }});
    }

    private void saveInFirebaseClient(ArrayList<Product> productWithChooseOrNoClient, int i) {
        Loading loadingdialog = new Loading(activity);
        loadingdialog.startLoadingdialog();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Product product = productWithChooseOrNoClient.get(i);
        Map<String, Object> product1 = new HashMap<>();
        product1.put("name", product.getName());
        product1.put("price", product.getPrice());
        product1.put("image", product.getImage());
        product1.put("inPrice", product.isInPrice());
        product1.put("priceClient", product.getPriceClient());
        product1.put("chooseThis", product.isChooseThis());
        namesProduct.remove(product.getName());
        db.collection("client").document(emailClient).collection(hallName).document("בחירות").collection(value).document(product.getName())
                .set(product1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loadingdialog.dismissdialog();
                        if (productWithChooseOrNoClient.size() - 1 > i) {
                            saveInFirebaseClient(productWithChooseOrNoClient, i + 1);
                        }else {
                            Toast.makeText(getContext(), "הבחירה נשמרה", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document");
                        System.out.println(e.toString());
                    }
                });


    }
    private void readCountHallEditInFirebase(String value,LinearLayout llListHallEdit){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        System.out.println(name);
        System.out.println(value);
        db.collection("hall").document(name).collection(value).document("count")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (document.getId().equals("count")) {
                            String chooseCountAll = String.valueOf(document.getData().get("chooseCountAll"));
                            String chooseFromAll = String.valueOf(document.getData().get("chooseFromAll"));
                            tvHallEditChooseCountAll.setText(chooseCountAll);
                            tvHallEditChooseFromAll.setText(chooseFromAll);
                            readListHallEditInFirebase(value, llListHallEdit);
                        }
                    }
                }
            }
        });
    }
        private void readListHallEditInFirebase(String value,LinearLayout llListHallEdit){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("hall").document(name).collection(value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (!document.getId().equals("count")){
                                    String name = String.valueOf(document.getData().get("name"));
                                    namesProduct.add(name);
                                    String image = String.valueOf(document.getData().get("image"));
                                    Long price = (Long)(document.getData().get("price"));
                                    Boolean inPrice = (Boolean)(document.getData().get("inPrice"));
                                    Product product = new Product(name,price,inPrice,0L,image,false);
                                    addNewBoxView(llListHallEdit,product);
                                }
                            }
                            addNewBoxView(llListHallEdit);
                        } else {
                            System.out.println("Error getting documents.");
                        }
                    }});
    }

    private void saveCountHallEditInFirebase(String value, String numChooseCountAll, String numChooseFromAll) {
        Loading loadingdialog = new Loading(activity);
        loadingdialog.startLoadingdialog();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> countChoose = new HashMap<>();
        countChoose.put("chooseCountAll", numChooseCountAll);
        countChoose.put("chooseFromAll", numChooseFromAll);
        db.collection("hall").document(name).collection(value).document("count")
                .set(countChoose)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loadingdialog.dismissdialog();
                        saveListHallEditInFirebase(value, 0);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document");
                    }
                });
    }



    private void saveListHallEditInFirebase(String value, int i) {
        Loading loadingdialog = new Loading(activity);
        loadingdialog.startLoadingdialog();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Product product = products.get(i);
        Map<String, Object> product1 = new HashMap<>();
        product1.put("name", product.getName());
        product1.put("price", product.getPrice());
        product1.put("image", product.getImage());
        product1.put("inPrice", product.isInPrice());
        namesProduct.remove(product.getName());
        db.collection("hall").document(name).collection(value).document(product.getName())
                .set(product1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loadingdialog.dismissdialog();
                        if (products.size() - 1 > i) {
                            saveListHallEditInFirebase(value, i + 1);
                        }else {
                            if (namesProduct.size() > 0){
                                saveDeleteProduct(0);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document");
                    }
                });
    }
    public void saveDeleteProduct(int i){
        for (int j = 0; j < namesProduct.size(); j++) {
            System.out.println(namesProduct.get(j));
        }
        Loading loadingdialog = new Loading(activity);
        loadingdialog.startLoadingdialog();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("hall").document(name).collection(value).document(namesProduct.get(i))
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loadingdialog.dismissdialog();
                        if (namesProduct.size() - 1 > i) {
                            saveDeleteProduct( i + 1);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("error",e.getMessage());
                    }
                });
    }


    private void addNewBoxView(LinearLayout llListHallEdit, Product product) {
        View boxViewEdit = getLayoutInflater().inflate(R.layout.hall_edit_card_view, null, false);
        EditText etNameProductEdit = boxViewEdit.findViewById(R.id.etNameProductEdit);
        ImageView imgDeleteProductEdi = boxViewEdit.findViewById(R.id.imgDeleteProductEdit);
        EditText etPriceProductEdit = boxViewEdit.findViewById(R.id.etPriceProductEdit);
        imImageProductEdit = boxViewEdit.findViewById(R.id.imImageProductEdit);
        CheckBox cbInProductEdit = boxViewEdit.findViewById(R.id.cbInProductEdit);
        etNameProductEdit.setText(product.getName());
        etPriceProductEdit.setText(String.valueOf(product.getPrice()));
        cbInProductEdit.setChecked(product.isInPrice());
        if (product.getImage() != null){
            Glide.with(activity)
                    .load(Uri.parse(product.getImage())) // the uri you got from Firebase
                    .centerCrop()
                    .override(200,200)
                    .into(imImageProductEdit); //Your imageView variable
        }

        imgDeleteProductEdi.setOnClickListener(l ->{
                removeBoxView(boxViewEdit);
        });
        imImageProductEdit.setOnClickListener(l ->{
            imImageProductEdit = boxViewEdit.findViewById(R.id.imImageProductEdit);
            imageBoxView(boxViewEdit);
        });
        llListHallEdit.addView(boxViewEdit);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        imImageProductEdit.setImageURI(uri);
    }

    private void imageBoxView(View boxViewEdit) {
        ImageButton imImageProductEdit = boxViewEdit.findViewById(R.id.imImageProductEdit);
        ImagePicker.with(this)
                .maxResultSize(200,200)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    private void removeBoxView(View boxViewEdit) {
        llListHallEdit.removeView(boxViewEdit);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}