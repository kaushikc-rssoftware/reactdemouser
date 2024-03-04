package com.example.merchantdemo.dataClass;

public class ReportData {
    String invoiceNumber;
    String customerName;
    String amount;
    int status;

    public ReportData(String invoiceNumber, String customerName, String amount, int status) {
        this.invoiceNumber = invoiceNumber;
        this.customerName = customerName;
        this.amount = amount;
        this.status = status;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReportData{" +
                "invoiceNumber='" + invoiceNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", amount='" + amount + '\'' +
                ", status='" + status + '\'' +
                '}';
    }


}

