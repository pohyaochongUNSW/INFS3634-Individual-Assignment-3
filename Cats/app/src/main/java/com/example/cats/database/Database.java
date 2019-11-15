package com.example.cats.database;

import com.example.cats.models.Cat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// This database is use to temporary store search result
public class Database {

    public static ArrayList<Cat> catSearch = new ArrayList<Cat>();

    // Search cat by id in list
    public static Cat getCatById (String id){
        for(int i = 0; i < catSearch.size(); i++){
            if(catSearch.get(i).getBreeds().get(0).getId().equals(id)){
                return catSearch.get(i);
            }
        }

        return null;
    }

    // Convert json string from api and add them to arraylist
    public static void addCatSearch(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Cat>>(){}.getType();
        List<Cat> catList = gson.fromJson(json, listType);
        for(int i = 0; i < catList.size(); i++){
            Cat cat = catList.get(i);
            String catId = cat.getBreeds().get(0).getId();
            if(checkDuplicate(catSearch, catId)){
                catSearch.add(cat);
            }
        }
    }

    // Filter search duplicate result
    private static boolean checkDuplicate(ArrayList<Cat> catList, String id){
        for(int i = 0; i < catList.size(); i++){
            if(catList.get(i).getBreeds().get(0).getId().equals(id)){
                return false;
            }
        }

        return true;
    }
}