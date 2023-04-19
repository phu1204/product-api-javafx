package com.review.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Shopee {
    private String url ;
    private exception ex=new exception();

    public Shopee() throws IOException {
    }

    public List<Product> getProductsByQueryShopee(String q) throws IOException,RuntimeException {
        url = "https://shopee.vn/api/v4/search/search_items?by=relevancy&keyword="+ex.removespecialcharac(q)+"&limit=10&newest=0&order=desc";
        List<Product> productList = new ArrayList<>();
        Product product;
        Connection.Response res = Jsoup.connect(url)
                .method(Connection.Method.GET)
                .header("af-ac-enc-dat", "hello")
                .ignoreContentType(true)
                .execute();
        Document doc = res.parse();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONObject(doc.text()).getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                product = new Product();
                product.setproductID(String.valueOf(jsonArray.getJSONObject(i).getJSONObject("item_basic").getLong("itemid")));
                product.setIdshop(String.valueOf(jsonArray.getJSONObject(i).getJSONObject("item_basic").getLong("shopid")));
                product.setProductName(jsonArray.getJSONObject(i).getJSONObject("item_basic").getString("name"));
                product.setImageUrl("https://cf.shopee.vn/file/"+jsonArray.getJSONObject(i).getJSONObject("item_basic").getString("image"));
                product.setPrice(jsonArray.getJSONObject(i).getJSONObject("item_basic").getLong("price_max_before_discount")/100000);
                product.setPrice_sale(jsonArray.getJSONObject(i).getJSONObject("item_basic").getLong("price")/100000);
                product.setRating_average(jsonArray.getJSONObject(i).getJSONObject("item_basic").getJSONObject("item_rating").getFloat("rating_star"));
                productList.add(product);
            }
        } catch (JSONException e) {

        }
        return productList;
    }
    public ProductDetail getDetailProduct(String itemid,String shopid) throws IOException,RuntimeException {
        ProductDetail ProductDetail = new ProductDetail();;
        String itemid1 = itemid;
        String shopid1 = shopid;
        url = "https://shopee.vn/api/v4/item/get?itemid="+itemid1+"&shopid="+shopid1;
        Connection.Response res = Jsoup.connect(url).header("af-ac-enc-dat", "hello").method(Connection.Method.GET).ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONObject json= new JSONObject(doc.text()).getJSONObject("data");
        JSONArray jsonArray= null;
        try {
            jsonArray = json.getJSONArray("tier_variations");
            ProductDetail.setRating_average(json.getJSONObject("item_rating").getFloat("rating_star"));
            ProductDetail.setReview_count(Integer.parseInt(ex.getIntbyString(json.getJSONObject("item_rating").get("rating_count").toString())[0]));
            ProductDetail.setDescription(json.getString("description"));
            ProductDetail.setImagesUrl(ex.getListImagebyString(jsonArray.getJSONObject(0),"images","https://cf.shopee.vn/file/"));
        } catch (JSONException e) {

        }
        return ProductDetail;
    }
    public List<Rate> getRatesByQuery(String itemid,String shopid, int page) throws IOException,RuntimeException {
        url = "https://shopee.vn/api/v2/item/get_ratings?flag=1&itemid="+itemid+"&limit=10&offset="+(10*(page-1))+"&shopid="+shopid;
        List<Rate> ReviewList = new ArrayList<>();
        Rate rate;
        Connection.Response res = Jsoup.connect(url).header("af-ac-enc-dat", "hello").method(Connection.Method.GET).ignoreContentType(true).execute();
        Document doc =res.parse();
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONObject(doc.text()).getJSONObject("data").getJSONArray("ratings");
            for(int i = 0; i < jsonArray.length(); i++) {
                rate = new Rate();
                rate.setRating(jsonArray.getJSONObject(i).getInt("rating_star"));
                rate.setUsername(jsonArray.getJSONObject(i).getString("author_username"));
                rate.setUserImageUrl("https://cf.shopee.vn/file/"+ex.getImage(jsonArray.getJSONObject(i),"author_portrait"));
                rate.setComment(jsonArray.getJSONObject(i).getString("comment"));
                rate.setImageUrl(ex.getListImagebyString(jsonArray.getJSONObject(i),"images","https://cf.shopee.vn/file/"));
                ReviewList.add(rate);
            }
        } catch (JSONException e) {

        }
        return ReviewList;
    }

    public static void main(String[] args) throws IOException {
        Shopee shopee = new Shopee();
       List<Product> list=shopee.getProductsByQueryShopee("ipad");;
//         List<Product> productList = new ArrayList<>();
//       productList = tiki.getProductsByQuery("iphone");
        List<Rate> productListReviews = new ArrayList<>();
        productListReviews = shopee.getRatesByQuery("5600084939","88201679",1);
        ProductDetail pđ=shopee.getDetailProduct("5600084939","88201679");
        System.out.println("list.get(1).getComment()");
    }
}
