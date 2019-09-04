package com.example.placesapiclient;

class PlaceItem {
    String placeid;
    String primaryTxt;
    String secondaryTxt;

    public String getPlaceid() {
        return placeid;
    }

    public void setPlaceid(String placeid) {
        this.placeid = placeid;
    }

    public String getPrimaryTxt() {
        return primaryTxt;
    }

    public void setPrimaryTxt(String primaryTxt) {
        this.primaryTxt = primaryTxt;
    }

    public String getSecondaryTxt() {
        return secondaryTxt;
    }

    public void setSecondaryTxt(String secondaryTxt) {
        this.secondaryTxt = secondaryTxt;
    }

    public PlaceItem(String placeid, String primaryTxt, String secondaryTxt) {
        this.placeid = placeid;
        this.primaryTxt = primaryTxt;
        this.secondaryTxt = secondaryTxt;
    }
}
