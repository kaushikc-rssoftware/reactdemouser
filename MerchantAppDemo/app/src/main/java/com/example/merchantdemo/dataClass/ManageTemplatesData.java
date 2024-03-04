package com.example.merchantdemo.dataClass;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class ManageTemplatesData implements Parcelable {
    String templateName;
    String merchantName;
    String merchantlogo;
    String templateHeader;
    String templateFooter;
    HashMap<String, String> customField;


    public ManageTemplatesData(String templateName, String merchantName, String merchantlogo, String templateHeader, String templateFooter, HashMap<String, String> customField) {
        this.templateName = templateName;
        this.merchantName = merchantName;
        this.merchantlogo = merchantlogo;
        this.templateHeader = templateHeader;
        this.templateFooter = templateFooter;
        this.customField = customField;
    }


//    protected ManageTemplatesData(Parcel in) {
//        templateName = in.readString();
//        merchantName = in.readString();
//        merchantlogo = in.readString();
//        templateHeader = in.readString();
//        templateFooter = in.readString();
//        templateFooter = in.readString();
//    }

//    public static final Creator<ManageTemplatesData> CREATOR = new Creator<ManageTemplatesData>() {
//        @Override
//        public ManageTemplatesData createFromParcel(Parcel in) {
//            return new ManageTemplatesData(in);
//        }
//
//        @Override
//        public ManageTemplatesData[] newArray(int size) {
//            return new ManageTemplatesData[size];
//        }
//    };

    protected ManageTemplatesData(Parcel in) {
        templateName = in.readString();
        merchantName = in.readString();
        merchantlogo = in.readString();
        templateHeader = in.readString();
        templateFooter = in.readString();
        int size = in.readInt();
        customField = new HashMap<>();
        for (int i = 0; i < size; i++) {
            String key = in.readString();
            String value = in.readString();
            customField.put(key, value);
        }
    }

    public static final Creator<ManageTemplatesData> CREATOR = new Creator<ManageTemplatesData>() {
        @Override
        public ManageTemplatesData createFromParcel(Parcel in) {
            return new ManageTemplatesData(in);
        }

        @Override
        public ManageTemplatesData[] newArray(int size) {
            return new ManageTemplatesData[size];
        }
    };

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantlogo() {
        return merchantlogo;
    }

    public void setMerchantlogo(String merchantlogo) {
        this.merchantlogo = merchantlogo;
    }

    public String getTemplateHeader() {
        return templateHeader;
    }

    public void setTemplateHeader(String templateHeader) {
        this.templateHeader = templateHeader;
    }

    public String getTemplateFooter() {
        return templateFooter;
    }

    public void setTemplateFooter(String templateFooter) {
        this.templateFooter = templateFooter;
    }

    public HashMap<String, String> getCustomField() {
        return customField;
    }

    public void setCustomField(HashMap<String, String> customField) {
        this.customField = customField;
    }

    @Override
    public String toString() {
        return "ManageTemplatesData{" +
                "templateName='" + templateName + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", merchantlogo='" + merchantlogo + '\'' +
                ", templateHeader='" + templateHeader + '\'' +
                ", templateFooter='" + templateFooter + '\'' +
                ", customField=" + customField +
                '}';
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(templateName);
        parcel.writeString(merchantName);
        parcel.writeString(merchantlogo);
        parcel.writeString(templateHeader);
        parcel.writeString(templateFooter);
        parcel.writeInt(customField.size());
        for (Map.Entry<String, String> entry : customField.entrySet()) {
            parcel.writeString(entry.getKey());
            parcel.writeString(entry.getValue());
        }
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(@NonNull Parcel parcel, int i) {
//        parcel.writeString(templateName);
//        parcel.writeString(merchantName);
//        parcel.writeString(merchantlogo);
//        parcel.writeString(templateHeader);
//        parcel.writeString(templateFooter);
//    }
}
