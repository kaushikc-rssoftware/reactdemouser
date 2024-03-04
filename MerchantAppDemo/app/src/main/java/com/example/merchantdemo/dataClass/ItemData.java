package com.example.merchantdemo.dataClass;

public class ItemData {
    String itemName;
    String itemQuantity;
    String itemPrice;

    public ItemData(String itemName, String itemQuantity, String itemPrice, String itemAmountPrice) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.itemAmountPrice = itemAmountPrice;
    }

    String itemAmountPrice;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemAmountPrice() {
        return itemAmountPrice;
    }

    public void setItemAmountPrice(String itemAmountPrice) {
        this.itemAmountPrice = itemAmountPrice;
    }

    @Override
    public String toString() {
        return "ItemData{" +
                "itemName='" + itemName + '\'' +
                ", itemQuantity='" + itemQuantity + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                ", itemAmountPrice='" + itemAmountPrice + '\'' +
                '}';
    }
}
