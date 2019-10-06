package com.babbangona.barcodescannerproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class msaResponseT {

        @SerializedName("staff_id")
        @Expose
        private String staffId;
        @SerializedName("fullname")
        @Expose
        private String fullname;
        @SerializedName("template")
        @Expose
        private String template;
        @SerializedName("sync_time")
        @Expose
        private String syncTime;

        public String getStaffId() {
            return staffId;
        }

        public void setStaffId(String staffId) {
            this.staffId = staffId;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public String getSyncTime() {
            return syncTime;
        }

        public void setSyncTime(String syncTime) {
            this.syncTime = syncTime;
        }
}
