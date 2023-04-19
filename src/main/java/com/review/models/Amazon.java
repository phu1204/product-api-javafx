package com.review.models;


import org.jsoup.*;
import org.json.*;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Amazon {
    private String url ;
    private exception ex=new exception();
    private String key="E7B3149FDA74489EB75EAB3FD69437F7";

    public Amazon() throws IOException {
    }

    public List<Product> getProductsByQueryAmazon(String q) throws IOException,RuntimeException {
        url = "https://api.rainforestapi.com/request?api_key="+key+"&type=search&amazon_domain=amazon.com&search_term=";
        List<Product> productList = new ArrayList<>();
        Product product;
        Connection.Response res = Jsoup.connect(url+q).method(Connection.Method.GET).ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONObject(doc.text()).getJSONArray("search_results");
            for(int i = 0; i < jsonArray.length(); i++) {
                product = new Product();
                product.setproductID(jsonArray.getJSONObject(i).getString("asin"));
                product.setProductName(jsonArray.getJSONObject(i).getString("title"));
                product.setImageUrl(jsonArray.getJSONObject(i).getString("image"));
                product.setPrice(jsonArray.getJSONObject(i).getInt("price"));
                product.setPrice_sale(jsonArray.getJSONObject(i).getInt("price"));
                productList.add(product);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }
    public ProductDetail getDetailProduct(Integer ID) throws IOException,RuntimeException {
        ProductDetail ProductDetail;
        url = "https://api.rainforestapi.com/request?api_key="+key+"&type=product&amazon_domain=amazon.com&asin=";
        Connection.Response res = Jsoup.connect(url+ID).method(Connection.Method.GET).ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONObject json= new JSONObject(doc.text());
        try {
            ProductDetail = new ProductDetail();
            ProductDetail.setRating_average(json.getFloat("rating"));
            ProductDetail.setReview_count(json.getInt("ratings_total"));
            ProductDetail.setDescription(json.getString("description"));
            ProductDetail.setImagesUrl(ex.getListImage(json,"images","link"));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return ProductDetail;
    }
    public List<Rate> getRatesByQuery(Integer id) throws IOException,RuntimeException {
        url = "https://api.rainforestapi.com/request?api_key="+key+"&type=reviews&amazon_domain=amazon.com&asin=";
        List<Rate> ReviewList = new ArrayList<>();
        Rate rate;
        Connection.Response res = Jsoup.connect(url+id).method(Connection.Method.GET).ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONObject(doc.text()).getJSONArray("reviews");
            for(int i = 0; i < jsonArray.length(); i++) {
                rate = new Rate();
                rate.setDate(jsonArray.getJSONObject(i).getJSONObject("date").getString("utc"));
                rate.setRating(jsonArray.getJSONObject(i).getInt("rating"));
                rate.setUserImageUrl(ex.getImage(jsonArray.getJSONObject(i).getJSONObject("profile"),"image"));
                rate.setUsername(jsonArray.getJSONObject(i).getJSONObject("profile").getString("name"));
                rate.setComment(jsonArray.getJSONObject(i).getString("body_html"));
                rate.setImageUrl(ex.getListImage(jsonArray.getJSONObject(i),"images","link"));
                ReviewList.add(rate);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return ReviewList;
    }

    public static void main(String[] args) throws IOException {
        Amazon amazon=new Amazon();
        amazon.getProductsByQueryAmazon("iphone");
        System.out.println("hello");
    }
}