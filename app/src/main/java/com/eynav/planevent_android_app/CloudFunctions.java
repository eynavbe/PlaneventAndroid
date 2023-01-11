package com.eynav.planevent_android_app;

import androidx.annotation.NonNull;

import android.util.Log;

import com.eynav.planevent_android_app.ui.edit.Product;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloudFunctions {
    FirebaseFunctions mFunctions = FirebaseFunctions.getInstance();

//    public void readHallsDataFromFirebaseCloud(String collection1, RecyclerView rvListedHalls, String emailClient, Context context){
//        readHallsDataFromFirebase(collection1)
//                .addOnCompleteListener(new OnCompleteListener<List<Hall>>() {
//                    @Override
//                    public void onComplete(@NonNull Task<List<Hall>> task) {
//                        System.out.println(task.toString());
//                        System.out.println(task.getResult().toString());
//                        if (task.isSuccessful()) {
//                            for (int i = 0; i < task.getResult().size(); i++) {
//                                System.out.println(task.getResult().get(i));
//                            }
//                            rvListedHalls.setLayoutManager(new LinearLayoutManager(context));
//                            HallsAdapter hallAdapter = new HallsAdapter(context,task.getResult(), emailClient);
//                            rvListedHalls.setAdapter(hallAdapter);
//                        }else{
//                            Exception e = task.getException();
//                            if (e instanceof FirebaseFunctionsException) {
//                                FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
//                                FirebaseFunctionsException.Code code = ffe.getCode();
//                                Object details = ffe.getDetails();
//                                System.out.println(details.toString());
//                            }
//                        }
//                    }
//                });
//    }



    public Task<List<String>> readClientsDataFromFirebase(String collection1) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        System.out.println(collection1);
        data.put("collection1", collection1);

        return mFunctions
                .getHttpsCallable("readDataFromFirebase")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, List<String>>() {
                    @Override
                    public List<String> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        System.out.println(task.getResult().getData());
                        ArrayList<HashMap<String,Object>> result = (ArrayList<HashMap<String,Object>>) task.getResult().getData();
                        System.out.println((String) result.get(0).get("emailClient"));
                        List<String> clientList = new ArrayList<>();
                        for (int i = 0; i < result.size(); i++) {
                            String emailClient = (String) result.get(i).get("emailClient");
//                            String hallArea = (String) result.get(i).get("hallArea");
//                            String phoneNum = (String) result.get(i).get("phoneNum");
//                            String email = (String) result.get(i).get("email");
//                            String maxHallPeople = (String) result.get(i).get("maxHallPeople");
//                            Hall hall = new Hall(hallName,hallArea,maxHallPeople,email,phoneNum);
                            clientList.add(emailClient);
                        }
                        return clientList;
                    }
                });
    }


    public Task<List<Product>> readHallProductDataFromFirebase(String collection1, String doc1, String collection2) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        System.out.println(collection1);



        data.put("collection1", collection1);
        data.put("doc1", doc1);
        data.put("collection2", collection2);

        return mFunctions
                .getHttpsCallable("readDataFromFirebase")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, List<Product>>() {
                    @Override
                    public List<Product> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        System.out.println(task.getResult().getData());
                        ArrayList<HashMap<String,Object>> result = (ArrayList<HashMap<String,Object>>) task.getResult().getData();

                        List<Product> productArrayList = new ArrayList<>();
                        for (int i = 0; i < result.size(); i++) {
                            System.out.println(result.get(i));
                            if (result.get(i).get("name") != null){
                                String name = String.valueOf(result.get(i).get("name"));
                                String image = String.valueOf(result.get(i).get("image"));
                                Long price = ((Integer)(result.get(i).get("price"))).longValue();
                                Boolean inPrice = (Boolean)(result.get(i).get("inPrice"));
                                Product product = new Product(name,price,inPrice,0L,image,false);
                                productArrayList.add(product);
                            }

                        }
                        return productArrayList;
                    }
                });
    }




    public Task<CountHallEdit> readCountHallEditDataFromFirebase(String collection1, String doc1, String collection2) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        System.out.println(collection1);
        data.put("collection1", collection1);
        data.put("doc1", doc1);
        data.put("collection2", collection2);
        return mFunctions
                .getHttpsCallable("readDataFromFirebase")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, CountHallEdit>() {
                    @Override
                    public CountHallEdit then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        System.out.println(task.getResult().getData());
                        ArrayList<HashMap<String,Object>> result = (ArrayList<HashMap<String,Object>>) task.getResult().getData();
                        CountHallEdit countHallEdit = null;
//                        List<CountHallEdit> productArrayList = new ArrayList<>();
                        for (int i = 0; i < result.size(); i++) {

                            if (result.get(i).get("chooseCountAll") != null) {

                                String chooseCountAll = String.valueOf(result.get(i).get("chooseCountAll"));
                                String chooseFromAll = String.valueOf(result.get(i).get("chooseFromAll"));

                                 countHallEdit = new CountHallEdit(chooseCountAll,chooseFromAll);
                            }
                        }
                        return countHallEdit;
                    }
                });
    }





    public Task<List<Product>> readClientChoiceTypeDataFromFirebase(String collection1, String doc1, String collection2, String doc2, String collection3) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        System.out.println(collection1);
        data.put("collection1", collection1);
        data.put("doc1", doc1);
        data.put("collection2", collection2);
        data.put("doc2", doc2);
        data.put("collection3", collection3);
        return mFunctions
                .getHttpsCallable("readDataFromFirebase")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, List<Product>>() {
                    @Override
                    public List<Product> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        System.out.println(task.getResult().getData());
                        ArrayList<HashMap<String,Object>> result = (ArrayList<HashMap<String,Object>>) task.getResult().getData();

                        List<Product> productArrayList = new ArrayList<>();
                        for (int i = 0; i < result.size(); i++) {

                            if (result.get(i).get("name") != null) {


                                String name = String.valueOf(result.get(i).get("name"));
                                String image = String.valueOf(result.get(i).get("image"));
                                Long price = ((Integer) (result.get(i).get("price"))).longValue();
                                Boolean inPrice = (Boolean) (result.get(i).get("inPrice"));
                                Boolean chooseThis = (Boolean) (result.get(i).get("chooseThis"));
                                Long priceClient = ((Integer) (result.get(i).get("priceClient"))).longValue();
                                Product product = new Product(name, price, inPrice, priceClient, image, chooseThis);
                                productArrayList.add(product);
                            }
                        }
                        return productArrayList;
                    }
                });
    }






    public Task<List<Hall>> readHallsDataFromFirebase(String collection1) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        System.out.println(collection1);
        data.put("collection1", collection1);

        return mFunctions
                .getHttpsCallable("readDataFromFirebase")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, List<Hall>>() {
                    @Override
                    public List<Hall> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
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
                        return hallArrayList;
                    }
                });
    }

    public Task<List<Event>> readEventsDataFromFirebase(String collection1, String doc1, String collection2) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
//        System.out.println(type);
        data.put("collection1", collection1);
        data.put("doc1", doc1);
        data.put("collection2", collection2);

        return mFunctions
                .getHttpsCallable("readDataFromFirebase")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, List<Event>>() {
                    @Override
                    public List<Event> then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        System.out.println(task.getResult().getData());
                        ArrayList<HashMap<String,Object>> result = (ArrayList<HashMap<String,Object>>) task.getResult().getData();
                        List<Event> eventArrayList = new ArrayList<>();
                        if (result != null){
                            for (int i = 0; i < result.size(); i++) {
                                Integer countInvited = (Integer) result.get(i).get("countInvited");
                                String dateEvent = (String) result.get(i).get("dateEvent");
                                String emailClient1 = (String) result.get(i).get("emailClient1");
                                String emailClient2 = (String) result.get(i).get("emailClient2");
                                String hourEvent = (String) result.get(i).get("hourEvent");
                                String lastNameEvent = (String) result.get(i).get("lastNameEvent");
                                Integer price = (Integer) result.get(i).get("price");

                                String typeEvent = (String) result.get(i).get("typeEvent");
                                if (countInvited == null){
                                    countInvited = 0;
                                }
                                if (price == null){
                                    price = 0;
                                }
                                Event event = new Event(emailClient1,emailClient2,countInvited.longValue(),price.longValue(),typeEvent,dateEvent,lastNameEvent,hourEvent);
                                System.out.println(event);
                                eventArrayList.add(event);
                            }
                        }

                        return eventArrayList;
                    }
                });
    }

    public Task<String> writeUserToFirebase(String collection1, String doc1, String emailClient) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("collection1", collection1);
        data.put("doc1", doc1);
        data.put("emailClient", emailClient);

        return mFunctions
                .getHttpsCallable("writeUserToFirebase")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        System.out.println(task.getResult().getData());
                        HashMap<String,Object> result = (HashMap<String, Object>) task.getResult().getData();
                        System.out.println((String) result.get("emailClient"));
//                        System.out.println((int) result.get("age"));
//                        CloudFunctionsName o = new CloudFunctionsName((String) result.get("name"), 10);
//                        System.out.println(o.getAge());
//                        System.out.println(o.getName());

                        return (String) result.get("emailClient");
                    }
                });
    }





    public Task<String> deleteDataFromFirebase(String collection1, String doc1,String collection2, String doc2) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("collection1", collection1);
        data.put("doc1", doc1);
        data.put("collection2", collection2);
        data.put("doc2", doc2);
        return mFunctions
                .getHttpsCallable("deleteDataFromFirebase")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        System.out.println(task.getResult().getData());
                        HashMap<String,Object> result = (HashMap<String, Object>) task.getResult().getData();
                        System.out.println((String) result.get("delete"));
//                        System.out.println((int) result.get("age"));
//                        CloudFunctionsName o = new CloudFunctionsName((String) result.get("name"), 10);
//                        System.out.println(o.getAge());
//                        System.out.println(o.getName());

                        return (String) result.get("delete");
                    }
                });
    }

    public Task<Product> writeHallProductToFirebase(String collection1, String doc1, String collection2, String doc2, String name, Long price, String image, Boolean inPrice, Long priceClient, Boolean chooseThis) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("collection1", collection1);
        data.put("doc1", doc1);
        data.put("collection2", collection2);
        data.put("doc2", doc2);
        data.put("name", name);
        data.put("price", price);
        data.put("image", image);
        data.put("inPrice", inPrice);
        data.put("priceClient", priceClient);
        data.put("chooseThis", chooseThis);

        return mFunctions
                .getHttpsCallable("writeHallProductToFirebase")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, Product>() {
                    @Override
                    public Product then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        System.out.println(task.getResult().getData());
                        HashMap<String,Object> result = (HashMap<String, Object>) task.getResult().getData();
//                        System.out.println((int) result.get("age"));
                        String name = String.valueOf(result.get("name"));
                        String image = String.valueOf(result.get("image"));
                        Long price = ((Integer) (result.get("price"))).longValue();
                        Boolean inPrice = (Boolean) (result.get("inPrice"));
                        Boolean chooseThis = (Boolean) (result.get("chooseThis"));
                        Long priceClient = ((Integer) (result.get("priceClient"))).longValue();
                        Product product = new Product(name, price, inPrice, priceClient, image, chooseThis);
                        //                        System.out.println(o.getAge());
//                        System.out.println(o.getName());

                        return product;
                    }
                });
    }



    public Task<Product> writeClientChoiceTypeToFirebase(String collection1, String doc1, String collection2, String doc2, String collection3, String doc3, String name, Long price, String image, Boolean inPrice, Long priceClient, Boolean chooseThis) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("collection1", collection1);
        data.put("doc1", doc1);
        data.put("collection2", collection2);
        data.put("doc2", doc2);
        data.put("collection3", collection3);
        data.put("doc3", doc3);
        data.put("name", name);
        data.put("price", price);
        data.put("image", image);
        data.put("inPrice", inPrice);
        data.put("priceClient", priceClient);
        data.put("chooseThis", chooseThis);

        return mFunctions
                .getHttpsCallable("writeClientChoiceTypeToFirebase")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, Product>() {
                    @Override
                    public Product then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        System.out.println(task.getResult().getData());
                        HashMap<String,Object> result = (HashMap<String, Object>) task.getResult().getData();
//                        System.out.println((int) result.get("age"));
                        String name = String.valueOf(result.get("name"));
                        String image = String.valueOf(result.get("image"));
                        Long price = ((Integer) (result.get("price"))).longValue();
                        Boolean inPrice = (Boolean) (result.get("inPrice"));
                        Boolean chooseThis = (Boolean) (result.get("chooseThis"));
                        Long priceClient = ((Integer) (result.get("priceClient"))).longValue();
                        Product product = new Product(name, price, inPrice, priceClient, image, chooseThis);
                        //                        System.out.println(o.getAge());
//                        System.out.println(o.getName());

                        return product;
                    }
                });
    }
    public Task<CountHallEdit> writeCountHallEditToFirebase(String collection1, String doc1, String collection2, String doc2, String chooseCountAll, String chooseFromAll) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("collection1", collection1);
        data.put("doc1", doc1);
        data.put("collection2", collection2);
        data.put("doc2", doc2);
        data.put("chooseCountAll", chooseCountAll);
        data.put("chooseFromAll", chooseFromAll);

        return mFunctions
                .getHttpsCallable("writeCountHallEditToFirebase")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, CountHallEdit>() {
                    @Override
                    public CountHallEdit then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        System.out.println(task.getResult().getData());
                        HashMap<String,Object> result = (HashMap<String, Object>) task.getResult().getData();
//                        System.out.println((int) result.get("age"));
                        String chooseCountAll = String.valueOf(result.get("chooseCountAll"));
                        String chooseFromAll = String.valueOf(result.get("chooseFromAll"));
                        CountHallEdit countHallEdit = new CountHallEdit(chooseCountAll, chooseFromAll);
                        //                        System.out.println(o.getAge());
//                        System.out.println(o.getName());

                        return countHallEdit;
                    }
                });
    }


    public Task<Event> writeEventToFirebase(String collection1, String doc1,String collection2, String doc2, String emailClient1,String emailClient2,long countInvited,long price,String typeEvent,String dateEvent ,String lastNameEvent,String hourEvent) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("collection1", collection1);
        data.put("doc1", doc1);
        data.put("collection2", collection2);
        data.put("doc2", doc2);
        data.put("emailClient1", emailClient1);
        data.put("emailClient2", emailClient2);
        data.put("countInvited", countInvited);
        data.put("price", price);
        data.put("typeEvent", typeEvent);
        data.put("dateEvent", dateEvent);
        data.put("lastNameEvent", lastNameEvent);
        data.put("hourEvent", hourEvent);


        return mFunctions
                .getHttpsCallable("writeEventToFirebase")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, Event>() {
                    @Override
                    public Event then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        System.out.println(task.getResult().getData());
                        HashMap<String,Object> result = (HashMap<String, Object>) task.getResult().getData();
//                        System.out.println((int) result.get("age"));
                        String emailClient1 = (String) result.get("emailClient1");
                        String emailClient2 = (String) result.get("emailClient2");
                        Integer countInvited = (Integer) result.get("countInvited");
                        Integer price = (Integer) result.get("price");
                        String typeEvent = (String) result.get("typeEvent");
                        String dateEvent = (String) result.get("dateEvent");
                        String lastNameEvent = (String) result.get("lastNameEvent");
                        String hourEvent = (String) result.get("hourEvent");
                        Event event = new Event(emailClient1,emailClient2,countInvited.longValue(),price.longValue(),typeEvent,dateEvent,lastNameEvent,hourEvent);

//                        System.out.println(o.getAge());
//                        System.out.println(o.getName());

                        return event;
                    }
                });
    }

    public Task<Hall> writeHallToFirebase(String collection1, String doc1, String hallName, String hallArea, String maxHallPeople, String phoneNum, String email) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("collection1", collection1);
        data.put("doc1", doc1);
        data.put("hallName", hallName);
        data.put("hallArea", hallArea);
        data.put("maxHallPeople", maxHallPeople);
        data.put("phoneNum", phoneNum);
        data.put("email", email);

        return mFunctions
                .getHttpsCallable("writeHallToFirebase")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, Hall>() {
                    @Override
                    public Hall then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        System.out.println(task.getResult().getData());
                        HashMap<String,Object> result = (HashMap<String, Object>) task.getResult().getData();
//                        System.out.println((int) result.get("age"));
                        String hallName = (String) result.get("hallName");
                        String hallArea = (String) result.get("hallArea");
                        String phoneNum = (String) result.get("phoneNum");
                        String email = (String) result.get("email");
                        String maxHallPeople = (String) result.get("maxHallPeople");
                        Hall hall = new Hall(hallName,hallArea,maxHallPeople,email,phoneNum);
//                        System.out.println(o.getAge());
//                        System.out.println(o.getName());

                        return hall;
                    }
                });
    }






    public void writeDataToFirebaseCloud(String text){
        writeDataToFirebase(text)
                .addOnCompleteListener(new OnCompleteListener<CountHallEdit>() {
                    @Override
                    public void onComplete(@NonNull Task<CountHallEdit> task) {
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
    private Task<CountHallEdit> writeDataToFirebase(String text) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("name", text);
        data.put("age", 10);

        return mFunctions
                .getHttpsCallable("writeDataToFirebase")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, CountHallEdit>() {
                    @Override
                    public CountHallEdit then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        System.out.println(task.getResult().getData());
                        HashMap<String,Object> result = (HashMap<String, Object>) task.getResult().getData();
                        System.out.println((String) result.get("name"));
//                        System.out.println((int) result.get("age"));
//                        CountHallEdit o = new CountHallEdit((String) result.get("name"), 10);
//                        System.out.println(o.getAge());
//                        System.out.println(o.getName());

                        return null;
                    }
                });
    }
}