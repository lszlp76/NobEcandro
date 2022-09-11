package com.lszlp.nobec.ui.nearPharmacy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class JSONParserPlaceID {

    private HashMap<String,String> parseJsonObject(JSONObject object){
        HashMap<String,String> dataList = new HashMap<>();
        try {
            // get phone Number
            String phoneNumber = object.getString("formatted_phone_number");
            //get name
            String name= object.getString("name");
            dataList.put("formatted_phone_number",phoneNumber);
dataList.put("name",name);
    } catch (JSONException e) {
        e.printStackTrace();
    }
    //return hashmap
        return dataList;
    }

    private List<HashMap<String,String>> parseJSONArray (JSONArray jsonArray){
        List<HashMap<String, String>> dataList = new ArrayList<>();
        for (int  i = 0; i<jsonArray.length();i++) {
            try {
                HashMap<String,String>  data = parseJsonObject((JSONObject) jsonArray.get(i));
                //Add data in hasmap list
                dataList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return  dataList;
    }
    public List<HashMap<String,String>> parseResult ( JSONObject object) {
        JSONArray jsonArray = null ;
        try {
            jsonArray = object.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parseJSONArray(jsonArray);
    }



}
public class JSONParser {

    private HashMap<String,String> parseJsonObject(JSONObject object){
        HashMap <String,String> dataList = new HashMap<>();

        try {
            // get name from object
            String name = object.getString("name");
            // get latitud from object
            String latitude= object.getJSONObject("geometry").getJSONObject("location").getString("lat");
            // get longitude from object
            String longitude= object.getJSONObject("geometry").getJSONObject("location").getString("lng");
//get placeID
            String placeID = object.getString("place_id");
            //put all value in hashmap
            dataList.put("name",name);
            dataList.put("lat",latitude);
            dataList.put("lng",longitude);
            dataList.put("place_id",placeID);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return hashmap
        return dataList;

    }
    private List<HashMap<String,String>> parseJSONArray (JSONArray jsonArray){
        List<HashMap<String, String>> dataList = new ArrayList<>();
        for (int  i = 0; i<jsonArray.length();i++) {
            try {
                HashMap<String,String>  data = parseJsonObject((JSONObject) jsonArray.get(i));
                //Add data in hasmap list
                dataList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
return  dataList;
    }
    public List<HashMap<String,String>> parseResult ( JSONObject object) {
        JSONArray jsonArray = null ;
        try {
            jsonArray = object.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
return parseJSONArray(jsonArray);
    }
}
