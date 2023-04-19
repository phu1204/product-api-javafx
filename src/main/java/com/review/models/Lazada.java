package com.review.models;


import org.jsoup.*;
import org.json.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class Lazada {
    private String url ;
    private exception ex=new exception();
    private HashMap<String,ProductDetail> listproductdetail=new HashMap<>();

    public Lazada() throws IOException {
    }

    public List<Product> getProductsByQueryLazada(String q) throws IOException,RuntimeException {
        url = "https://www.lazada.vn/catalog/?_keyori=ss&ajax=true&q=";
        List<Product> productList = new ArrayList<>();
        Product product;
        Connection.Response res = Jsoup.connect(url+ex.removespecialcharac(q)).cookie("x5sec",getCookie()).method(Connection.Method.GET).ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONObject(doc.text()).getJSONObject("mods").getJSONArray("listItems");
            for(int i = 0; i < jsonArray.length(); i++) {
                product = new Product();
                product.setproductID(String.valueOf(jsonArray.getJSONObject(i).getInt("itemId")));
                product.setProductName(jsonArray.getJSONObject(i).getString("name"));
                product.setImageUrl(jsonArray.getJSONObject(i).getString("image"));
                product.setRating_average(jsonArray.getJSONObject(i).getFloat("ratingScore"));
                product.setPrice(Long.parseLong(jsonArray.getJSONObject(i).getString("originalPrice").replace(".00","")));
                product.setPrice_sale(Long.parseLong(jsonArray.getJSONObject(i).getString("price").replace(".00","")));
                productList.add(product);
            }
        } catch (JSONException e) {
        }
        return productList;
    }
    public String getCookie(){
        File f=new File("cookie.txt");
        String cookie=null;
        String url= "https://my.lazada.vn/pdp/review/getReviewList?itemId=911354757&pageSize=5&filter=0&sort=0&pageNo=1";
        try {
            Connection.Response re = Jsoup.connect("https://www.lazada.vn/products/tui-10-chiec-khau-trang-hinh-cua-khau-trang-y-te-thiet-ke-thoi-trang-khang-khuanchong-tia-uvkhong-gay-dau-tai-i2039435757-s9519293441.html?spm=a2o4n.home.just4u.1.19056afeZVm19M&&scm=1007.17519.162103.0&pvid=920240d1-5fb2-4406-a6bf-9b43d5893aaa&search=0&clickTrackInfo=tctags%3A1215065286%3Btcsceneid%3AHPJFY%3Bbuyernid%3A686555bf-8fbd-4527-e2ea-341799b2f15b%3Btcboost%3A0%3Bpvid%3A920240d1-5fb2-4406-a6bf-9b43d5893aaa%3Bchannel_id%3A0000%3Bmt%3Ai2i%3Bitem_id%3A2039435757%3Bself_ab_id%3A162103%3Bself_app_id%3A7519%3Blayer_buckets%3A955.7333_5437.25236_6059.28889%3Bpos%3A0%3B").ignoreContentType(true).followRedirects(true).method(Connection.Method.GET).execute();
            cookie=re.cookie("x5sec");
            if(cookie==null){
                Scanner sc=new Scanner(f);
                cookie=sc.nextLine();
            }
            else {
                FileWriter fr=new FileWriter("cookie.txt");
            fr.write(cookie);}
        } catch (IOException ex) {
            System.out.println(ex);
        }
            return cookie;
    }
    public ProductDetail getDetailProduct(String ID)throws IOException,RuntimeException {
        url = "https://www.lazada.vn/catalog/?_keyori=ss&ajax=true&q=";
        Connection.Response res = Jsoup.connect(url+ID).cookie("x5sec",getCookie()).method(Connection.Method.GET).ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONArray jsonArray= null;
        ProductDetail pdd=new ProductDetail();
        try {
            jsonArray = new JSONObject(doc.text()).getJSONObject("mods").getJSONArray("listItems");
            for(int i = 0; i < jsonArray.length(); i++) {
                //get productDetail
                Float c=Float.parseFloat(jsonArray.getJSONObject(i).getString("ratingScore"));
                pdd.setRating_average(c);
                String v=jsonArray.getJSONObject(i).getString("review");
                pdd.setReview_count(Integer.parseInt(v==""? "0":v));
                String urldes="https:"+jsonArray.getJSONObject(i).getString("itemUrl");
                res = Jsoup.connect(urldes).method(Connection.Method.GET).ignoreContentType(true).followRedirects(true).execute();
                doc=res.parse();
                pdd.setDescription(doc.getElementsByAttribute("content").first().attributes().get("content"));
                pdd.setImagesUrl(ex.getListImage(jsonArray.getJSONObject(i),"thumbs","image"));
            }
        } catch (JSONException e) {
        }
        return pdd;
    }
    public List<Rate> getRatesByQueryLazada(String id,int page) throws IOException,RuntimeException {
        url = "https://my.lazada.vn/pdp/review/getReviewList?itemId="+id+"&pageSize=10&sort=0&pageNo="+page;
        List<Rate> ReviewList = new ArrayList<>();
        Rate rate;
        Connection.Response res = Jsoup.connect(url).cookie("x5sec",getCookie()).method(Connection.Method.GET).ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONObject(doc.text()).getJSONObject("model").getJSONArray("items");
            for(int i = 0; i < jsonArray.length(); i++) {
                rate = new Rate();
                rate.setRating(jsonArray.getJSONObject(i).getInt("rating"));
                rate.setUsername(jsonArray.getJSONObject(i).getString("buyerName"));
                rate.setComment(jsonArray.getJSONObject(i).getString("reviewContent"));
                rate.setImageUrl(ex.getListImage(jsonArray.getJSONObject(i),"images","url"));
                rate.setUserImageUrl(ex.getImage(jsonArray.getJSONObject(i),"avatar"));
                ReviewList.add(rate);
            }
        } catch (JSONException e) {
        }
        return ReviewList;
    }

    public static void main(String[] args) throws IOException {
        Lazada lazada = new Lazada();
        List<Product> productList = new ArrayList<>();
        productList = lazada.getProductsByQueryLazada("iphone");
 //       List<Rate> productListReviews = new ArrayList<>()
        // productListReviews = lazada.getRatesByQueryLazada("2016216850");
        System.out.println("hello");
    }
}