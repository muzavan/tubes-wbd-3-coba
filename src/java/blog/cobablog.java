/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blog;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author M. Reza Irvanda
 */
@WebService(serviceName = "cobablog")
public class cobablog {

    public static final String AUTH_TOKEN = "Jeqg6K2cgLB509zZQ2EMxr07qezh2CfActE23SoA";
    public static Boolean selesaiRetrieve = false;
    public static DataSnapshot data = null;
    public static final String LINK_FIREBASE= "https://simple-blog-19.firebaseio.com/";
    public static Firebase fb = new Firebase(LINK_FIREBASE);
    /**
     * Web service operation
     */
    @WebMethod(operationName = "addPost")
    public Boolean addPost(@WebParam(name = "judul") String judul, @WebParam(name = "konten") String konten, @WebParam(name = "tanggal") String tanggal, @WebParam(name = "author") String author) {
        //TODO write your implementation code here:
        Map<String,Object> mp = new HashMap<String,Object>();
        
        mp.put("judul", judul);
        mp.put("konten", konten);
        mp.put("tanggal", tanggal);
        mp.put("author", author);
        mp.put("published",0);
        mp.put("deleted",0);
        mp.put("komentar","-");
        fb.child("posts").push().setValue(mp);
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "listPost")
    public String listPost() {
        try {
            //TODO write your implementation code here:
            String linkString = LINK_FIREBASE+"posts.json";
            URL link = new URL(linkString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(link.openStream()));
            
            String s ="";
            String tmp;
            while((tmp=reader.readLine())!=null){
                s+=tmp;
            }
            return s;
            //System.out.println(array.get(0));
        } catch (MalformedURLException ex) {
            Logger.getLogger(cobablog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(cobablog.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "editPost")
    public Boolean editPost(@WebParam(name = "idPost") String idPost, @WebParam(name = "judul") String judul, @WebParam(name = "konten") String konten, @WebParam(name = "tanggal") Date tanggal) {
        //TODO write your implementation code here:
        Map<String,Object> mp = new HashMap<String,Object>();
        
        mp.put("judul", judul);
        mp.put("konten", konten);
        mp.put("tanggal", tanggal);
        fb.child("posts").child(idPost).updateChildren(mp);
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "deletePost")
    public Boolean deletePost(@WebParam(name = "idPost") String idPost) {
        //TODO write your implementation code here:
        Map<String,Object> mp = new HashMap<>();
        fb.child("posts").child(idPost).setValue(mp);
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "publishPost")
    public Boolean publishPost(@WebParam(name = "idPost") String idPost) {
        //TODO write your implementation code here:
        Map<String,Object> mp = new HashMap<String,Object>();
        mp.put("published",1);
        fb.child("posts").child(idPost).updateChildren(mp);
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addUser")
    public Boolean addUser(@WebParam(name = "nama") String nama, @WebParam(name = "email") String email, @WebParam(name = "role") String role, @WebParam(name = "password") String password) {
        //TODO write your implementation code here:
        Map<String,Object> mp = new HashMap<String,Object>();
        mp.put("nama", nama);
        mp.put("email", email);
        mp.put("role",role);
        mp.put("password", password);
        fb.child("users").push().setValue(mp);
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "listUser")
    public String listUser() {
        //TODO write your implementation code here:
        try {
            //TODO write your implementation code here:
            String linkString = LINK_FIREBASE+"/users.json";
            URL link = new URL(linkString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(link.openStream()));
            
            String s ="";
            String tmp;
            while((tmp=reader.readLine())!=null){
                s+=tmp;
            }
            return s;
            //System.out.println(array.get(0));
        } catch (MalformedURLException ex) {
            Logger.getLogger(cobablog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(cobablog.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "editUser")
    public Boolean editUser(@WebParam(name = "idUser") String idUser, @WebParam(name = "nama") String nama, @WebParam(name = "role") String role, @WebParam(name = "email") String email, @WebParam(name = "password") String password) {
        //TODO write your implementation code here:
        Map<String,Object> mp = new HashMap<String,Object>();
        
        mp.put("nama", nama);
        mp.put("email", email);
        mp.put("role", role);
        mp.put("password",password);
        fb.child("users").child(idUser).updateChildren(mp);
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "deleteUser")
    public Boolean deleteUser(@WebParam(name = "idUser") String idUser) {
        //TODO write your implementation code here:
        fb.child("users").child(idUser).setValue(null);
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addComment")
    public Boolean addComment(@WebParam(name = "nama") String nama, @WebParam(name = "email") String email, @WebParam(name = "konten") String konten, @WebParam(name = "idPost") String idPost) {
        //TODO write your implementation code here:
        Map<String,Object> mp = new HashMap<String,Object>();  
        mp.put("nama", nama);
        mp.put("email", email);
        mp.put("tanggal",new Date());
        mp.put("konten", konten);
        fb.child(idPost).child("komentar").push().setValue(mp);
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "listComment")
    public String listComment(@WebParam(name = "idPost") String idPost) {
        try {
            //TODO write your implementation code here:
            String linkString = LINK_FIREBASE+"posts/"+idPost+"/komentar.json";
            URL link = new URL(linkString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(link.openStream()));
            
            String s ="";
            String tmp;
            while((tmp=reader.readLine())!=null){
                s+=tmp;
            }
            return s;
            //System.out.println(array.get(0));
        } catch (MalformedURLException ex) {
            Logger.getLogger(cobablog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(cobablog.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "deleteComment")
    public Boolean deleteComment(@WebParam(name = "id") String id,@WebParam(name = "idPost") String idPost) {
        //TODO write your implementation code here:
        fb.child(idPost).child("komentar").child(id).setValue(null);
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "dummyTest")
    public List search(@WebParam(name = "query") String query) {
        //belum tahu caranya
        return null;
    }

}
