package com.appsbl.dmarion.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 12-11-2016.
 */

public class CheckNewsModel {


    /**
     * status : success
     * message :
     * setting_info : {"last_updated_id":"5005","last_updated_on":"Nov 08, 2016 08:00"}
     */

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    /**
     * last_updated_id : 5005
     * last_updated_on : Nov 08, 2016 08:00
     */

    @SerializedName("setting_info")
    private SettingInfoBean setting_info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SettingInfoBean getSetting_info() {
        return setting_info;
    }

    public void setSetting_info(SettingInfoBean setting_info) {
        this.setting_info = setting_info;
    }

    public static class SettingInfoBean {
        @SerializedName("last_updated_id")
        private String last_updated_id;
        @SerializedName("last_updated_on")
        private String last_updated_on;

        public String getLast_updated_id() {
            return last_updated_id;
        }

        public void setLast_updated_id(String last_updated_id) {
            this.last_updated_id = last_updated_id;
        }

        public String getLast_updated_on() {
            return last_updated_on;
        }

        public void setLast_updated_on(String last_updated_on) {
            this.last_updated_on = last_updated_on;
        }
    }
}
