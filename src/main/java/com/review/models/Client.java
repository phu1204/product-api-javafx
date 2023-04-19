package com.review.models;

import com.review.Encryptions.AES;
import com.review.Encryptions.RSA;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Client {
    private static int destPort ;
    private String hostname;
    private DatagramSocket socket;
    private DatagramPacket dpreceive, dpsend;
    private InetAddress add;
    private Scanner stdIn;
    private List<Product> listdata;
    private List<Rate> ListDataReviews;
    private ProductDetail productDetail;
    private static RSA rsa=new RSA();
    private static AES aes=new AES();
    public Client() throws IOException {
        this.destPort = 1234;
        this.hostname = GetIpServer();
        this.add = InetAddress.getByName(hostname);
        this.socket = new DatagramSocket();
        this.stdIn = new Scanner(System.in);
    }
    public String GetIpServer() throws IOException{
        String api="https://api-generator.retool.com/ed49kC/ipserver/1";
        Document doc= Jsoup.connect(api)
                .ignoreContentType(true).ignoreHttpErrors(true)
                .header("Content-Type","application/json")
                .method(Connection.Method.GET).execute().parse();
        JSONObject jso=new JSONObject(doc.text());
        return jso.getString("Ip");
    }
    public void SearchProduct(String query) throws IOException,ClassNotFoundException{
        query = "search#"+query;
        query=aes.encrypt(query);
        byte[] data = query.getBytes();
        dpsend = new DatagramPacket(data, data.length, add, destPort);
        socket.send(dpsend);
    }
    public void SearchProductSendo(String query) throws IOException,ClassNotFoundException{
        query = "searchSendo#"+query;
        query=aes.encrypt(query);
        byte[] data = query.getBytes();
        dpsend = new DatagramPacket(data, data.length, add, destPort);
        socket.send(dpsend);
    }
    public void SearchProductShopee(String query) throws IOException,ClassNotFoundException{
        query = "searchShopee#"+query;
        query=aes.encrypt(query);
        byte[] data = query.getBytes();
        dpsend = new DatagramPacket(data, data.length, add, destPort);
        socket.send(dpsend);
    }
    public void SearchProductLazada(String query) throws IOException,ClassNotFoundException{
        query = "searchLazada#"+query;
        query=aes.encrypt(query);
        byte[] data = query.getBytes();
        dpsend = new DatagramPacket(data, data.length, add, destPort);
        socket.send(dpsend);
    }
    public void GetDetailProduct(String IDProduct) throws IOException,ClassNotFoundException{
        IDProduct = "clickDetail#"+IDProduct;
        IDProduct=aes.encrypt(IDProduct);
        byte[] data = IDProduct.getBytes();
        dpsend = new DatagramPacket(data, data.length, add, destPort);
        socket.send(dpsend);
    }
    public void GetDetailProductLazada(String IDProduct) throws IOException,ClassNotFoundException{
        IDProduct = "clickDetailLazada#"+IDProduct;
        IDProduct=aes.encrypt(IDProduct);
        byte[] data = IDProduct.getBytes();
        dpsend = new DatagramPacket(data, data.length, add, destPort);
        socket.send(dpsend);
    }
    public void GetDetailProductShopee(String IDProduct,String IDShop) throws IOException,ClassNotFoundException{
        IDProduct = "clickDetailShopee#"+IDProduct+"#"+IDShop;
        IDProduct=aes.encrypt(IDProduct);
        byte[] data = IDProduct.getBytes();
        dpsend = new DatagramPacket(data, data.length, add, destPort);
        socket.send(dpsend);
    }
    public void GetDetailProductSendo(String IDProduct) throws IOException,ClassNotFoundException{
        IDProduct = "clickDetailSendo#"+IDProduct;
        IDProduct=aes.encrypt(IDProduct);
        byte[] data = IDProduct.getBytes();
        dpsend = new DatagramPacket(data, data.length, add, destPort);
        socket.send(dpsend);
    }
    public void GetReviewProduct(String IDProduct,int page) throws IOException,ClassNotFoundException{
        IDProduct = "ReviewProduct#"+IDProduct+"#"+page;
        IDProduct=aes.encrypt(IDProduct);
        byte[] data = IDProduct.getBytes();
        dpsend = new DatagramPacket(data, data.length, add, destPort);
        socket.send(dpsend);
    }
    public void GetReviewProductShopee(String IDProduct,String IDShop,int page) throws IOException,ClassNotFoundException{
        IDProduct = "ReviewProductShopee#"+IDProduct+"#"+IDShop+"#"+page;
        IDProduct=aes.encrypt(IDProduct);
        byte[] data = IDProduct.getBytes();
        dpsend = new DatagramPacket(data, data.length, add, destPort);
        socket.send(dpsend);
    }
    public void GetReviewProductSendo(String IDProduct,int page) throws IOException,ClassNotFoundException{
        IDProduct = "ReviewProductSendo#"+IDProduct+"#"+page;
        IDProduct=aes.encrypt(IDProduct);
        byte[] data = IDProduct.getBytes();
        dpsend = new DatagramPacket(data, data.length, add, destPort);
        socket.send(dpsend);
    }
    public void GetReviewProductLazada(String ID, int page) throws IOException,ClassNotFoundException{
        ID = "ReviewProductLazada#"+ID+"#"+page;
        ID=aes.encrypt(ID);
        byte[] data = ID.getBytes();
        dpsend = new DatagramPacket(data, data.length, add, destPort);
        socket.send(dpsend);
    }
    public void disconnect() throws IOException,ClassNotFoundException{
        String data = "bye";
        data=aes.encrypt(data);
        byte[] data2 = data.getBytes();
        dpsend = new DatagramPacket(data2, data2.length, add, destPort);
        socket.send(dpsend);
        socket.close();
    }

    public List<Product> ReceiveList() throws IOException,ClassNotFoundException{
            dpreceive = new DatagramPacket(new byte[100*1024],100*1024);
            socket.receive(dpreceive);
            listdata=aes.decryptList(new String(dpreceive.getData(),0,dpreceive.getLength()));
            return listdata;
    }
    public List<Rate> ReceiveListReviews() throws IOException,ClassNotFoundException{
        dpreceive = new DatagramPacket(new byte[100*1024],100*1024);
        socket.receive(dpreceive);
        ListDataReviews = aes.decryptListReviews(new String(dpreceive.getData(),0,dpreceive.getLength()));
        return ListDataReviews;
    }
    public ProductDetail ReceiveProductDetail() throws IOException,ClassNotFoundException{
        dpreceive = new DatagramPacket(new byte[100*1024],100*1024);
        socket.receive(dpreceive);
        productDetail = aes.decryptProductDetail(new String(dpreceive.getData(),0,dpreceive.getLength()));
        return productDetail;
    }
    public void ConnectClient() throws IOException,NoSuchAlgorithmException,InvalidKeySpecException {
        traodoikey(socket, dpsend, add);
//        while (true) {
//            System.out.print("Client input: \n");
//            String tmp = stdIn.nextLine();
//            tmp=aes.encrypt(tmp);
//            byte[] data = tmp.getBytes();
//            dpsend = new DatagramPacket(data, data.length, add, destPort);
//            socket.send(dpsend);
//            if (tmp.equals("bye")) {
//                System.out.println("Client socket closed");
//                stdIn.close();
//                socket.close();
//                break;
//            }
//            //Get response from server
//            dpreceive = new DatagramPacket(new byte[4096], 4096);
//            socket.receive(dpreceive);
//            try {
//                tmp = new String(dpreceive.getData(), 0, dpreceive.getLength());
//                listdata = aes.decryptList(tmp);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//            if (!listdata.isEmpty()) System.out.println(listdata.get(0).getProductName());
//            else System.out.println("fall");
//        }
    }
    private static void traodoikey(DatagramSocket socket,DatagramPacket dpsend,InetAddress add) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String tmp=""+socket.getLocalPort();
        byte[] data = tmp.getBytes();
        dpsend = new DatagramPacket(data, data.length, add, destPort);
        socket.send(dpsend);
        DatagramPacket dpreceive = new DatagramPacket(new byte[4096], 4096);
        socket.receive(dpreceive);
        rsa.keyp = dpreceive.getData();
        aes.createkey();
        tmp=rsa.mahoa(aes.getPassword()+";"+aes.getSalt());
        data = tmp.getBytes();
        dpsend = new DatagramPacket(data, data.length, add, destPort);
        socket.send(dpsend);
    }
    public static void main(String args[]){
    }
}
