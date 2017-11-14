package com.example.miek.miekstours.Classes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MMART on 11/10/2017.
 */
public class UserAccount {
    String id;
    String email;
    String password;
    //
    public UserAccount() {

    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public JSONObject toJSON(){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("email", getEmail());
            jsonObject.put("password", getPassword());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
