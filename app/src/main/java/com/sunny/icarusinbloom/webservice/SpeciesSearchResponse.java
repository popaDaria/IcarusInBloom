package com.sunny.icarusinbloom.webservice;

import java.util.ArrayList;

public class SpeciesSearchResponse {
    private ArrayList<DataObjects> data;

    public int getSpeciesId(){
        if(data!=null) {
            if (data.size() != 0) {
                return data.get(0).id;
            }
        }
        return -1;
    }

    private class DataObjects{
        private int id;
    }
}
