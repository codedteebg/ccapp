package com.babbangona.barcodescannerproject.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class syncTransportResponse {

        @SerializedName("hsf_id")
        @Expose
        private String hsfId;
        @SerializedName("sync_flag")
        @Expose
        private Integer syncFlag;
        @SerializedName("sync_time")
        @Expose
        private String syncTime;

        public String getHsfId() {
            return hsfId;
        }

        public void setHsfId(String hsfId) {
            this.hsfId = hsfId;
        }

        public Integer getSyncFlag() {
            return syncFlag;
        }

        public void setSyncFlag(Integer syncFlag) {
            this.syncFlag = syncFlag;
        }

        public String getSyncTime() {
            return syncTime;
        }

        public void setSyncTime(String syncTime) {
            this.syncTime = syncTime;
        }


}
