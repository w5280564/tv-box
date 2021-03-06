package com.bosan.audiorecordbybluetooth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bosan.audiorecordbybluetooth.base.Constants;
import com.bosan.audiorecordbybluetooth.base.HttpSender;
import com.bosan.audiorecordbybluetooth.base.VolumeChangeObserver;
import com.bosan.audiorecordbybluetooth.bean.mainBean;
import com.bosan.audiorecordbybluetooth.bean.versionBean;
import com.bosan.audiorecordbybluetooth.utils.PcmToWavUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class MainActivity2 extends Activity {

    private String FILE_NAME;

    private AudioRecord audioRecord = null;
    private int recordBufsize = 0;
    private boolean isRecording = false;

    private Button startRecordBtn;
    private Button stopRecordBtn;

    private Thread recordingThread;
    private MyReceiver receiver;
    private AudioManager mAudioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        initReceiver();

        FILE_NAME = Environment.getDataDirectory().getPath() + File.separator + System.currentTimeMillis() + "test.pcm";


        File myfile = getApplicationContext().getExternalCacheDir();

        try {
            File file = File.createTempFile("audio_", ".pcm", myfile);
            FILE_NAME = file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("FILE_NAME--->", FILE_NAME);
        startRecordBtn = (Button) findViewById(R.id.start_record_btn);
        stopRecordBtn = (Button) findViewById(R.id.stop_record_btn);
        createAudioRecord();
        Button characters_btn = (Button) findViewById(R.id.characters_btn);
        characters_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity2.this, WebViewActivity.class);
//                startActivity(intent);
                if (versionBean != null){
                    String homePage = versionBean.getData().getHomePage();
                    WebViewActivity.startActivity(MainActivity2.this,homePage);
                }
            }
        });

        getCheckVersion();
        VolumeChangeObserver volumeChangeObserver = new VolumeChangeObserver(this);
    }

    private void initReceiver() {
        receiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.VOICE_KEY_DOWN_ACTION_DOWN);
        intentFilter.addAction(Constants.VOICE_KEY_DOWN_ACTION_UP);
        registerReceiver(receiver, intentFilter);
    }

    public static File getFilesystemRoot(String path) {
        File cache = Environment.getDownloadCacheDirectory();
        if (path.startsWith(cache.getPath())) {
            return cache;
        }
        File external = Environment.getExternalStorageDirectory();
        if (path.startsWith(external.getPath())) {
            return external;
        }
        throw new IllegalArgumentException(
                "Cannot determine filesystem root for " + path);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void createAudioRecord() {
        recordBufsize = AudioRecord
                .getMinBufferSize(44100,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                44100,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                recordBufsize);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        Log.e("keyCode2", "--------" + keyCode + "--------");
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("keyCode", "--------" + keyCode + "--------"+event.toString());
        switch (keyCode) {
            case KeyEvent.KEYCODE_0:
                Log.e("main", "--------0--------");
                break;
            case KeyEvent.KEYCODE_1:
                Log.e("main", "--------1--------");
                break;
            case KeyEvent.KEYCODE_2:
                Log.e("main", "--------2--------");
                break;
            case KeyEvent.KEYCODE_3:
                Log.e("main", "--------3--------");
                break;
            case KeyEvent.KEYCODE_4:
                Log.e("main", "--------4--------");
                break;
            case KeyEvent.KEYCODE_5:
                Log.e("main", "--------5--------");
                break;
            case KeyEvent.KEYCODE_6:
                Log.e("main", "--------6--------");
                break;
            case KeyEvent.KEYCODE_7:
                Log.e("main", "--------7--------");
                break;
            case KeyEvent.KEYCODE_8:
                Log.e("main", "--------8--------");
                break;
            case KeyEvent.KEYCODE_9:
                Log.e("main", "--------9--------");
                break;
            case KeyEvent.KEYCODE_DPAD_UP://??????
                Log.e("main", "--------??????--------");
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN://??????
                Log.e("main", "--------??????--------");
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT://??????
                Log.e("main", "--------??????--------");
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT://??????
                Log.e("main", "--------??????--------");
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:

            case KeyEvent.KEYCODE_ENTER://??????
                Log.e("main", "--------??????--------");
                break;
            case KeyEvent.KEYCODE_BACK://??????
                Log.e("main", "--------??????--------");
                break;
            case KeyEvent.KEYCODE_HOME://Home
                Log.e("main", "--------Home--------");
                break;
            case KeyEvent.KEYCODE_MENU://??????
                Log.e("main", "--------??????--------");
                break;
            case 166:
                Log.e("main", "--------?????????--------");
                break;
            case 167:
                Log.e("main", "--------?????????--------");
                break;
            default:
                Log.e("main", "--------??????--------");
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void startRecord() {
        if (isRecording) {
            return;
        }
        isRecording = true;
        audioRecord.startRecording();
        Log.i("audioRecordTest", "????????????");
        recordingThread = new Thread(() -> {
            byte data[] = new byte[recordBufsize];
            File file = new File(FILE_NAME);
            FileOutputStream os = null;
            try {
                if (!file.exists()) {
                    file.createNewFile();
                    Log.i("audioRecordTest", "??????????????????->" + FILE_NAME);
                }
                os = new FileOutputStream(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int read;
            if (os != null) {
                while (isRecording) {
                    read = audioRecord.read(data, 0, recordBufsize);
                    if (AudioRecord.ERROR_INVALID_OPERATION != read) {
                        try {
                            os.write(data);
                            Log.i("audioRecordTest", "???????????????->" + read);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        recordingThread.start();
    }

    private void stopRecord() {
        isRecording = false;
        if (audioRecord != null) {
            audioRecord.stop();
            Log.i("audioRecordTest", "????????????");
//            audioRecord.release();
//            audioRecord = null;
//            recordingThread = null;
        }
        String FILE_NAME_WAW = "";
        File myfile = getApplicationContext().getExternalCacheDir();

        try {
            File file = File.createTempFile("audio_", ".wav", myfile);
            FILE_NAME_WAW = file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PcmToWavUtil pcmToWavUtil = new PcmToWavUtil(44100,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        pcmToWavUtil.pcmToWav(FILE_NAME, FILE_NAME_WAW);
        Log.d("FILE_NAME--->2", FILE_NAME_WAW);
        String fileName = "test_" + System.currentTimeMillis() + ".wav";
        File uploadFile = new File(FILE_NAME_WAW);
        Log.d("?????????", uploadFile.getName());
        uploadVoiceFile(uploadFile);
//        new UploadFileUtils(getApplicationContext(), fileName, FILE_NAME_WAW).asyncUploadFile(new UploadFileUtils.UploadCallBack() {
//            @Override
//            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//
//            }
//
//            @Override
//            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
//                Log.d("FILE_NAME--->3", UploadFileUtils.baseOssUrl + fileName);
//            }
//
//            @Override
//            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
//
//            }
//        });
    }


    private void uploadVoiceFile(File file) {
        HashMap<String, String> baseMap = new HashMap<>();
        baseMap.put("device_id", getDeviceId());
        HttpSender sender = new HttpSender(Constants.POST_VOICE_FILE, baseMap,
                new HttpSender.OnHttpResListener() {
                    @Override
                    public void onComplete(JSONObject json_root, int code, String msg) {
                        if (code == Constants.REQUEST_SUCCESS_CODE) {
                            mainBean mainBean = new Gson().fromJson(json_root.toJSONString(), mainBean.class);
                            if (mainBean.getData() != null) {
                                String question = mainBean.getData().getQuestion();
                                String answer = mainBean.getData().getAnswer();
                                String command = mainBean.getData().getCommand();
                                String format = String.format("?????????%1$s ----- ?????????%2$s", question, answer);
                                Toast.makeText(MainActivity2.this, format, Toast.LENGTH_LONG).show();

                                if (TextUtils.isEmpty(command)) {
                                    return;
                                }

                                if (command.equals(Constants.KEYCODE_VOLUME_UP)) {////????????????
                                    mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_PLAY_SOUND);
                                }else if(command.equals(Constants.KEYCODE_VOLUME_DOWN)){//????????????
                                    mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_PLAY_SOUND);
                                }else if(command.equals(Constants.KEYCODE_CHANNEL_UP)){//?????????

                                }else if(command.equals(Constants.KEYCODE_CHANNEL_DOWN)){//?????????

                                }else if(command.equals(Constants.KEYCODE_MEDIA_PAUSE)){//????????????

                                }
                            }
                        } else {

                        }
                    }
                });
        sender.sendPostFile(file);
    }


    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, final Intent intent) {
            String action = intent.getAction();
            int keyCode = intent.getIntExtra("keyCode", 0);
            if (action.equals(Constants.VOICE_KEY_DOWN_ACTION_DOWN)) {//?????????????????? ?????????????????????
                Log.i("audioRecordTest", "????????????keyCode>>>" + keyCode);
                startRecord();
            } else if (intent.getAction().equals(Constants.VOICE_KEY_DOWN_ACTION_UP)) {//?????????????????? ??????????????????
                Log.i("audioRecordTest", "????????????keyCode>>>" + keyCode);
                stopRecord();
            }
        }
    }


    @SuppressLint("MissingPermission")
    public String getDeviceId() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    versionBean versionBean;

    /**
     * ????????????
     */
    private void getCheckVersion(){
        String versionName = getVersionName(this);
        HashMap<String, String> baseMap = new HashMap<>();
        baseMap.put("version", versionName);
        HttpSender sender = new HttpSender(Constants.GET_CHECK_VERSION, baseMap,new HttpSender.OnHttpResListener() {
                    @Override
                    public void onComplete(JSONObject json_root, int code, String msg) {
                        if (code == Constants.REQUEST_SUCCESS_CODE) {
                             versionBean = new Gson().fromJson(json_root.toJSONString(), versionBean.class);
                            if (versionBean != null) {

                            }
                        }
                    }
                });
        sender.sendGet();
    }


    /**
     * ??????????????????
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}