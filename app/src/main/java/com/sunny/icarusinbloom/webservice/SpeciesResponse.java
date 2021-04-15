package com.sunny.icarusinbloom.webservice;

public class SpeciesResponse {
    private DataResponse data;

    public SpeciesInfo getPlantInfo(){
        int minPe=-1, maxPe=-1, minTemp=-1;
        if(data.growth.minimum_precipitation!=null){
            minPe=data.growth.minimum_precipitation.mm;
        }
        if(data.growth.maximum_precipitation!=null){
            maxPe=data.growth.maximum_precipitation.mm;
        }
        if(data.growth.minimum_temperature!=null){
            minTemp=data.growth.minimum_temperature.deg_c;
        }
        return new SpeciesInfo(data.id,data.scientific_name,data.edible, data.specifications.growth_habit,
                data.specifications.growth_rate,data.growth.soil_nutriments,
                minPe,maxPe,minTemp);
    }

    private class DataResponse{
        private int id;
        private String scientific_name;
        private boolean edible;
        private Specifications specifications;
        private GrowthInfo growth;

        private class Specifications{
            private String growth_habit;
            private String growth_rate;
        }

        private class GrowthInfo{
            private int soil_nutriments;
            private MinPe minimum_precipitation;
            private MaxPe maximum_precipitation;
            private MinTemp minimum_temperature;

            private class MinPe{
                private int mm;
            }
            private class MaxPe{
                private int mm;
            }
            private class MinTemp{
                private int deg_c;
            }
        }
    }
}
