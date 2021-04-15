package com.sunny.icarusinbloom.webservice;

import java.util.ArrayList;

public class SpeciesSearchResponse {
    private ArrayList<DataObjects> data;

    public int getSpeciesId(){
        if(data!=null)
            return data.get(0).id;
        else
            return -1;
    }

    private class DataObjects{
        private int id;
    }
}
