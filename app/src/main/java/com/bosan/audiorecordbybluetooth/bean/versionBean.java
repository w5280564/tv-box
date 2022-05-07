package com.bosan.audiorecordbybluetooth.bean;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class versionBean {

    @SerializedName("code")
    private Integer code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private DataDTO data;
    @SerializedName("runTime")
    private String runTime;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @SerializedName("type")
        private Integer type;
        @SerializedName("url")
        private String url;
        @SerializedName("homePage")
        private String homePage;
    }
}
