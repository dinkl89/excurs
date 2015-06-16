package com.kleshch.excurs;

public class ExcursionListItem {

    private String name, image;
    private int itemId;

    public ExcursionListItem (int itemId, String name, String image){
        this.itemId = itemId;
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getItemId() {
        return itemId;
    }

    public String getImage() {
        return image;
    }
}
