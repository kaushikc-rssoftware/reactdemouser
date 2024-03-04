package com.example.dfiapp.dataClass;

public class InvoiceItemData {
    String invoiceItems;
    String invoiceQuantity;
    String invoicePrice;
    String invoiceTax;
    String invoiceAmount;

    public InvoiceItemData(String invoiceItems, String invoiceQuantity, String invoicePrice, String invoiceTax, String invoiceAmount) {
        this.invoiceItems = invoiceItems;
        this.invoiceQuantity = invoiceQuantity;
        this.invoicePrice = invoicePrice;
        this.invoiceTax = invoiceTax;
        this.invoiceAmount = invoiceAmount;
    }

    public String getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(String invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public String getInvoiceQuantity() {
        return invoiceQuantity;
    }

    public void setInvoiceQuantity(String invoiceQuantity) {
        this.invoiceQuantity = invoiceQuantity;
    }

    public String getInvoicePrice() {
        return invoicePrice;
    }

    public void setInvoicePrice(String invoicePrice) {
        this.invoicePrice = invoicePrice;
    }

    public String getInvoiceTax() {
        return invoiceTax;
    }

    public void setInvoiceTax(String invoiceTax) {
        this.invoiceTax = invoiceTax;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

}
