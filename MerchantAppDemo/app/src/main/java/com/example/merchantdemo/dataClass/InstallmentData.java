package com.example.merchantdemo.dataClass;

public class InstallmentData {

    String transactionId;
    String transactionAmount;
    String paymentMethod;
    String paymentDate;
    String paymentTime;

    public InstallmentData(String transactionId, String transactionAmount, String paymentMethod, String paymentDate, String paymentTime) {
        this.transactionId = transactionId;
        this.transactionAmount = transactionAmount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.paymentTime = paymentTime;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    @Override
    public String toString() {
        return "InstallmentData{" +
                "transactionId='" + transactionId + '\'' +
                ", transactionAmount='" + transactionAmount + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }

}
