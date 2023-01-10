package com.eynav.planevent_android_app;

import androidx.annotation.NonNull;

import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

public class CloudFunctions {
    FirebaseFunctions mFunctions = FirebaseFunctions.getInstance();


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