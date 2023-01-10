package com.eynav.planevent_android_app;

import androidx.annotation.NonNull;

import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloudFunctions {
    FirebaseFunctions mFunctions = FirebaseFunctions.getInstance();

    public void readHallsDataFromFirebaseCloud(String type){
        Log.e("------","-----");
        readHallsDataFromFirebase(type)
                .addOnCompleteListener(new OnCompleteListener<List<Hall>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Hall>> task) {
                        System.out.println(task.toString());
                        System.out.println(task.getResult().toString());
                        if (task.isSuccessful()) {
                            for (int i = 0; i < task.getResult().size(); i++) {
                                System.out.println(task.getResult().get(i));
                            }
                            Log.e("demo",""+task.getResult());
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
    private Task<List<Hall>> readHallsDataFromFirebase(String type) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        System.out.println(type);
        data.put("collection1", type);

        return mFunctions
                .getHttpsCallable("readDataFromFirebase")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, List<Hall>>() {
                    @Override
                    public List<Hall> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        System.out.println(task.getResult().getData());
                        ArrayList<HashMap<String,Object>> result = (ArrayList<HashMap<String,Object>>) task.getResult().getData();
                        System.out.println((String) result.get(0).get("hallName"));
                        System.out.println((String) result.get(0).get("hallArea"));
                        System.out.println((String) result.get(0).get("phoneNum"));
                        System.out.println((String) result.get(0).get("email"));
                        System.out.println((String) result.get(0).get("maxHallPeople"));
                        List<Hall> hallArrayList = new ArrayList<>();
                        for (int i = 0; i < result.size(); i++) {
                            String hallName = (String) result.get(i).get("hallName");
                            String hallArea = (String) result.get(i).get("hallArea");
                            String phoneNum = (String) result.get(i).get("phoneNum");
                            String email = (String) result.get(i).get("email");
                            String maxHallPeople = (String) result.get(i).get("maxHallPeople");
                            Hall hall = new Hall(hallName,hallArea,maxHallPeople,email,phoneNum);
                            hallArrayList.add(hall);
                            }
//                        System.out.println(result.get(0).getEmail());
//                        HashMap<String,Object> result = (HashMap<String, Object>) task.getResult().getData();
//                        System.out.println(result);
                        //                        System.out.println((String) result.get("name"));
//                        System.out.println((int) result.get("age"));
//                        CloudFunctionsName o = new CloudFunctionsName("nnn", 10);
//                        System.out.println(o.getAge());
//                        System.out.println(o.getName());

                        return hallArrayList;
                    }
                });
    }
    public void writeDataToFirebaseCloud(String text){
        writeDataToFirebase(text)
                .addOnCompleteListener(new OnCompleteListener<CloudFunctionsName>() {
                    @Override
                    public void onComplete(@NonNull Task<CloudFunctionsName> task) {
                        System.out.println(task.toString());
                        System.out.println(task.getResult().toString());
                        if (task.isSuccessful()) {
                            Log.e("demo",""+task.getResult());
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
    private Task<CloudFunctionsName> writeDataToFirebase(String text) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("name", text);
        data.put("age", 10);

        return mFunctions
                .getHttpsCallable("writeDataToFirebase")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, CloudFunctionsName>() {
                    @Override
                    public CloudFunctionsName then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        System.out.println(task.getResult().getData());
                        HashMap<String,Object> result = (HashMap<String, Object>) task.getResult().getData();
                        System.out.println((String) result.get("name"));
//                        System.out.println((int) result.get("age"));
                        CloudFunctionsName o = new CloudFunctionsName((String) result.get("name"), 10);
                        System.out.println(o.getAge());
                        System.out.println(o.getName());

                        return o;
                    }
                });
    }
}