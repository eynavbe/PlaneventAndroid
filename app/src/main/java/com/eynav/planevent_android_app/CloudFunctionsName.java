package com.eynav.planevent_android_app;

public class CloudFunctionsName {
    String name;
    int age;
    public CloudFunctionsName(String name, int age){
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CloudFunctionsName{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
