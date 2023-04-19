package com.review.models;
import org.json.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class exception {
    private  String[] urlDefault = {"//tiki.vn/assets/img/avatar.png",""};
    private String dirPath = "https://img.upanh.tv/2022/11/29/an-danh.png";
    private String dirPathNull = "https://img.upanh.tv/2022/11/29/an-danh.png";

    public Boolean checkUrl(String Url){
        for (int i=0;i< urlDefault.length;i++){
            if(Url.equals(urlDefault[i])){
                return true;
            }
        }
        return false;
    }
    public double getradting(JSONObject js, String s){
        double f;
        try{
            f= js.getFloat(s);
        }
        catch (JSONException e){
            f=1.0*0;
        }
        return f;
    }
    public String getImage(JSONObject js,String s){
        String image;
        try{
            image=js.getString(s);
            if(checkUrl(image) || image==null){
                image=dirPath;
            }
        }
        catch(JSONException e){
            image=dirPath;
        }
        return image;
    }
    public List<String> getListImage(JSONObject js,String s,String a){
        List<String> listimage=new ArrayList<>();
        try{
            JSONArray jsa=js.getJSONArray(s);
                for(int i=0;i<jsa.length();i++)
                    listimage.add(jsa.getJSONObject(i).getString(a));
        }
        catch (JSONException e){
            return listimage;
        }
        return listimage;
    }
    public List<String> getListImagebyString(JSONObject js,String s,String a){
        List<String> listimage=new ArrayList<>();
        try{
            String[] n=getImagebyString(js.get(s).toString());
            for(int i=0;i<n.length;i++) {
                if(!checkUrl(n[i])) {
                    listimage.add(a + n[i]);
                }
            }
        }
        catch (JSONException e){
            return listimage;
        }
        return listimage;
    }
    public String getchecknull(JSONObject js,String s){
        String a;
        try{
            a=js.getString(s);
        }
        catch (JSONException e){
            a="";
        }
        return a;
    }
    public String[] getIntbyString(String s){
        return s.replaceAll("[^0-9a-zA-Z,-]","").split(",");
    }
    public String[] getImagebyString(String s){
        return s.replaceAll("[^0-9a-zA-Z,_./:-]","").split(",");
    }
    public String removespecialcharac(String s){return s.replaceAll("[!'/\"@#$%&*()_+=|<>?{}\\[\\]~-]","");}
}
