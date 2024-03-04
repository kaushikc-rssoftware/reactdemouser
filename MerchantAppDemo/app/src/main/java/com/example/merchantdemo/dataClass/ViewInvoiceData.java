package com.example.merchantdemo.dataClass;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ViewInvoiceData {
    String merchantName;
    String merchantHeader;
    String merchantLogo;
    String customerName;
    String customerAddress;
    String customerContactNo;
    String customerEmail;
    String invoiceNo;
    String invoiceDate;
    List<ItemData> itemData;
    String subtotal;
    String discount;
    String discountValue;
    String tax;
    String taxValue;
    String totalValue;
    String footer;
    LinkedHashMap<String,String> customField;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantHeader() {
        return merchantHeader;
    }

    public void setMerchantHeader(String merchantHeader) {
        this.merchantHeader = merchantHeader;
    }

    public String getMerchantLogo() {
        return merchantLogo;
    }

    public void setMerchantLogo(String merchantLogo) {
        this.merchantLogo = merchantLogo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerContactNo() {
        return customerContactNo;
    }

    public void setCustomerContactNo(String customerContactNo) {
        this.customerContactNo = customerContactNo;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public List<ItemData> getItemData() {
        return itemData;
    }

    public void setItemData(List<ItemData> itemData) {
        this.itemData = itemData;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(String discountValue) {
        this.discountValue = discountValue;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(String taxValue) {
        this.taxValue = taxValue;
    }

    public String getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(String totalValue) {
        this.totalValue = totalValue;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public LinkedHashMap<String, String> getCustomField() {
        return customField;
    }

    public void setCustomField(LinkedHashMap<String, String> customField) {
        this.customField = customField;
    }

    @Override
    public String toString() {
        return "ViewInvoiceData{" +
                "merchantName='" + merchantName + '\'' +
                ", merchantHeader='" + merchantHeader + '\'' +
                ", merchantLogo='" + merchantLogo + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", customerContactNo='" + customerContactNo + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", invoiceNo='" + invoiceNo + '\'' +
                ", invoiceDate='" + invoiceDate + '\'' +
                ", itemData=" + itemData +
                ", subtotal='" + subtotal + '\'' +
                ", discount='" + discount + '\'' +
                ", discountValue='" + discountValue + '\'' +
                ", tax='" + tax + '\'' +
                ", taxValue='" + taxValue + '\'' +
                ", totalValue='" + totalValue + '\'' +
                ", footer='" + footer + '\'' +
                ", customField=" + customField +
                '}';
    }

    public void clearData() {
        merchantName = "";
        merchantHeader = "";
        merchantLogo = "";
        customerName = "";
        customerAddress = "";
        customerContactNo = "";
        customerEmail = "";
        invoiceNo = "";
        invoiceDate = "";
        itemData = new ArrayList<>(); // or clear existing list if needed
        subtotal = "";
        discount = "";
        discountValue = "";
        tax = "";
        taxValue = "";
        totalValue = "";
        footer = "";
        customField = new LinkedHashMap<>(); // or clear existing map if needed
    }
}
