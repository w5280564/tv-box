package com.bosan.audiorecordbybluetooth.base;

public class Constants {
    public static final String VOICE_KEY_DOWN_ACTION_DOWN = "net.sunniwell.action.VOICE_KEY_PRESS_DOWN";
    public static final String VOICE_KEY_DOWN_ACTION_UP = "net.sunniwell.action.VOICE_KEY_PRESS_UP";
    public static final String PUBLIC_KEY = "d4cf4238a0b943820dcc509a6f75847a";
    public static final String PRIVATE_SECRET = "38c8edde3f61a0411512d3b1866f0647";
    public static final String SERVICE = "https://ar.gstai.com";
    public static final String POST_VOICE_FILE = SERVICE+"/api/project/iflytek/send-msg";//上传语音文件接口
    public static final String POST_VOICE_FILE_ROUTE = "/api/project/iflytek/send-msg";
    public static final int REQUEST_SUCCESS_CODE = 200;
    public static final String GET_CHECK_VERSION = SERVICE + "/api/project/iflytek/check-version";//版本更新


    public static final String KEYCODE_VOLUME_UP = "KEYCODE_VOLUME_UP";//调大音量
    public static final String KEYCODE_VOLUME_DOWN = "KEYCODE_VOLUME_DOWN";//调小音量
    public static final String KEYCODE_CHANNEL_UP = "KEYCODE_CHANNEL_UP";//频道加
    public static final String KEYCODE_CHANNEL_DOWN = "KEYCODE_CHANNEL_DOWN";//频道减
    public static final String KEYCODE_MEDIA_PAUSE = "KEYCODE_MEDIA_PAUSE";//暂停播放

}