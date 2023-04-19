package com.review.models;


import org.jsoup.*;
import org.json.*;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Tiki {
    private String url ;
    private exception ex=new exception();

    public Tiki() throws IOException {
    }

    public List<Product> getProductsByQuery(String q) throws IOException,RuntimeException {
        url = "https://tiki.vn/api/v2/products?limit=10&q=";
        List<Product> productList = new ArrayList<>();
        Product product;
        Connection.Response res = Jsoup.connect(url+ex.removespecialcharac(q)).method(Connection.Method.GET).ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONObject(doc.text()).getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++) {
                product = new Product();
                product.setproductID(String.valueOf(jsonArray.getJSONObject(i).getInt("id")));
                product.setProductName(jsonArray.getJSONObject(i).getString("name"));
                product.setImageUrl(jsonArray.getJSONObject(i).getString("thumbnail_url"));
                product.setPrice(jsonArray.getJSONObject(i).getInt("original_price"));
                product.setPrice_sale(jsonArray.getJSONObject(i).getInt("price"));
                product.setRating_average(jsonArray.getJSONObject(i).getFloat("rating_average"));
                productList.add(product);
            }
        } catch (JSONException e) {

        }
        return productList;
    }
    public ProductDetail getDetailProduct(String ID) throws IOException,RuntimeException {
        ProductDetail ProductDetail = new ProductDetail();;
        url = "https://api.tiki.vn/product-detail/api/v1/products/";
        Connection.Response res = Jsoup.connect(url+ID).method(Connection.Method.GET).ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONObject json= new JSONObject(doc.text());
        try {
            ProductDetail.setRating_average(json.getFloat("rating_average"));
            ProductDetail.setReview_count(json.getInt("review_count"));
            ProductDetail.setDescription(json.getString("description"));
            ProductDetail.setImagesUrl(ex.getListImage(json,"images","base_url"));

        } catch (JSONException e) {

        }
        return ProductDetail;
    }
    public List<Rate> getRatesByQuery(String id,int page) throws IOException,RuntimeException {
        url = "https://tiki.vn/api/v2/reviews?limit=10&page="+page+"&product_id="+id;
        List<Rate> ReviewList = new ArrayList<>();
        Rate rate;
        Connection.Response res = Jsoup.connect(url).method(Connection.Method.GET).ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONObject(doc.text()).getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++) {
                rate = new Rate();
//                rate.setDate(jsonArray.getJSONObject(i).getJSONObject("created_by").getString("created_time"));
                rate.setRating(jsonArray.getJSONObject(i).getInt("rating"));
                rate.setUsername(jsonArray.getJSONObject(i).getJSONObject("created_by").getString("full_name"));
                rate.setUserImageUrl(ex.getImage(jsonArray.getJSONObject(i).getJSONObject("created_by"),"avatar_url"));
                rate.setComment(jsonArray.getJSONObject(i).getString("content"));
                rate.setImageUrl(ex.getListImage(jsonArray.getJSONObject(i),"images","full_path"));
                ReviewList.add(rate);
            }
        } catch (JSONException e) {

        }
        return ReviewList;
    }

    public static void main(String[] args) throws IOException {
        Tiki tiki = new Tiki();
        ProductDetail productDetail = new ProductDetail();
        productDetail = tiki.getDetailProduct("13362558");
          List<Product> productList = new ArrayList<>();
        productList = tiki.getProductsByQuery("iphone");
        List<Rate> productListReviews = new ArrayList<>();
        productListReviews = tiki.getRatesByQuery("184061913",1);
        System.out.println("Hello");
    }
}