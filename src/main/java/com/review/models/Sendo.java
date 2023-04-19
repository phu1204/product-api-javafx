package com.review.models;


import org.jsoup.*;
import org.json.*;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Sendo {
    private String url ;
    private exception ex=new exception();
    public List<Product> getProductsByQuerySendo(String q) throws IOException,RuntimeException {
        url = "https://searchlist-api.sendo.vn/web/products?page=1&size=10&sortType=rank&q=";
        List<Product> productList = new ArrayList<>();
        Product product;
        Connection.Response res = Jsoup.connect(url+ex.removespecialcharac(q)).method(Connection.Method.GET).header("referer","https://www.sendo.vn/").ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONObject(doc.text()).getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++) {
                product = new Product();
                product.setproductID(String.valueOf(jsonArray.getJSONObject(i).getLong("id")));
                product.setProductName(jsonArray.getJSONObject(i).getString("name"));
                product.setImageUrl(jsonArray.getJSONObject(i).getString("image"));
                product.setPrice(jsonArray.getJSONObject(i).getInt("default_price_max"));
                product.setPrice_sale(jsonArray.getJSONObject(i).getInt("sale_price_max"));
                product.setRating_average((float) ex.getradting(jsonArray.getJSONObject(i).getJSONObject("rated"),"star"));
                String [] n=jsonArray.getJSONObject(i).getString("category_path").split(".html");
                product.setPart(n[0]);
                productList.add(product);
            }
        } catch (JSONException e) {

        }
        return productList;
    }
    public ProductDetail getDetailProductSendo(String part) throws IOException,RuntimeException {
        ProductDetail ProductDetail = new ProductDetail();;
        url = "https://detail-api.sendo.vn/full/"+part;
        Connection.Response res = Jsoup.connect(url).method(Connection.Method.GET).header("referer","https://www.sendo.vn/").ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONObject json= new JSONObject(doc.text()).getJSONObject("data");
        try {
            JSONArray jsonArray= json.getJSONArray("media");
            ProductDetail.setRating_average(json.getJSONObject("rating_info").getFloat("percent_star"));
            ProductDetail.setReview_count(json.getJSONObject("rating_info").getInt("total_rated"));
            ProductDetail.setDescription(ex.getchecknull(json,"short_description"));
            List<String>a=new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++) {
               if(jsonArray.getJSONObject(i).getString("type").equals("image")){
                   a.add(jsonArray.getJSONObject(i).getString("image"));
               }
            }
            ProductDetail.setImagesUrl(a);

        } catch (JSONException e) {

        }
        return ProductDetail;
    }
    public List<Rate> getRatesByQuerySendo(String id, int page) throws IOException,RuntimeException {
        url = "https://ratingapi.sendo.vn/product/"+id+"/rating?limit=10&star=all&page="+page;
        List<Rate> ReviewList = new ArrayList<>();
        Rate rate;
        Connection.Response res = Jsoup.connect(url).method(Connection.Method.GET).ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONArray jsonArray= null;
        JSONArray jsonArrayImages= null;

        try {
            jsonArray = new JSONObject(doc.text()).getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++) {
                rate = new Rate();
                List<String> a = new ArrayList<>();
                jsonArrayImages = jsonArray.getJSONObject(i).getJSONArray("images");
                for(int j=0;j<jsonArrayImages.length();j++){
                    a.add(jsonArrayImages.get(j).toString());
                }
                rate.setImageUrl(a);
                rate.setDate("");
                rate.setRating(jsonArray.getJSONObject(i).getInt("star"));
                rate.setUsername(jsonArray.getJSONObject(i).getString("user_name"));
                rate.setUserImageUrl(ex.getImage(jsonArray.getJSONObject(i),"avatar"));
                rate.setComment(jsonArray.getJSONObject(i).getString("comment"));
                rate.setImageUrl(ex.getListImagebyString(jsonArray.getJSONObject(i),"images",""));
                ReviewList.add(rate);
            }
        } catch (JSONException e) {

        }
        return ReviewList;
    }

    public static void main(String[] args) throws IOException {
        Sendo sendo = new Sendo();
        List<Product> productList = new ArrayList<>();
        productList = sendo.getProductsByQuerySendo("ipad");
      List<Rate> productListReviews = new ArrayList<>();
      productListReviews = sendo.getRatesByQuerySendo("23066374",2);
        System.out.println("Hello");
    }
}