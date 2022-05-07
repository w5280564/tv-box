package com.bosan.audiorecordbybluetooth.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class mainBean {
    private Integer code;
    private String msg;
    private DataDTO data;
    private String runTime;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        private String question;
        private String answer;
        private String command;
    }
}
