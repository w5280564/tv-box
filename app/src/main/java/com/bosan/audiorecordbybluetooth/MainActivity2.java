package com.bosan.audiorecordbybluetooth;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.hardware.input.InputManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;



import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends Activity {

    private     String FILE_NAME  ;

    private AudioRecord audioRecord = null;
    private int recordBufsize = 0;
    private boolean isRecording = false;

    private Button startRecordBtn;
    private Button stopRecordBtn;

    private Thread recordingThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

      FILE_NAME=  Environment.getDataDirectory().getPath() + File.separator + System.currentTimeMillis()+"test.pcm";




            File myfile = getApplicationContext().getExternalCacheDir();

        try {
            File file = File.createTempFile("audio_", ".pcm", myfile);
            FILE_NAME=file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("FILE_NAME--->",FILE_NAME);
        startRecordBtn = (Button) findViewById(R.id.start_record_btn);
        stopRecordBtn = (Button) findViewById(R.id.stop_record_btn);
        createAudioRecord();
        startRecordBtn.setOnClickListener(v -> {
            startRecord();
        });
        stopRecordBtn.setOnClickListener(v -> {
            stopRecord();
        });


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
        Log.e("keyCode", "--------" + keyCode + "--------");
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
            case KeyEvent.KEYCODE_DPAD_UP://向上
                Log.e("main", "--------向上--------");
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN://向下
                Log.e("main", "--------向下--------");
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT://向左
                Log.e("main", "--------向左--------");
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT://向右
                Log.e("main", "--------向右--------");
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:

            case KeyEvent.KEYCODE_ENTER://确定
                Log.e("main", "--------确定--------");
                break;
            case KeyEvent.KEYCODE_BACK://返回
                Log.e("main", "--------返回--------");
                break;
            case KeyEvent.KEYCODE_HOME://Home
                Log.e("main", "--------Home--------");
                break;
            case KeyEvent.KEYCODE_MENU://菜单
                Log.e("main", "--------菜单--------");
                break;
            case 166:
                Log.e("main", "--------节目加--------");

                break;
            case 167:
                Log.e("main", "--------节目减--------");

                break;
            default:
                Log.e("main", "--------未知--------");

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
        Log.i("audioRecordTest", "开始录音");
        recordingThread = new Thread(() -> {
            byte data[] = new byte[recordBufsize];
            File file = new File(FILE_NAME);
            FileOutputStream os = null;
            try {
                if (!file.exists()) {
                    file.createNewFile();
                    Log.i("audioRecordTest", "创建录音文件->" + FILE_NAME);
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
                            Log.i("audioRecordTest", "写录音数据->" + read);
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
            Log.i("audioRecordTest", "停止录音");
            audioRecord.release();
            audioRecord = null;
            recordingThread = null;
        }
        String FILE_NAME_WAW="";
        File myfile = getApplicationContext().getExternalCacheDir();

        try {
            File file = File.createTempFile("audio_", ".wav", myfile);
            FILE_NAME_WAW=file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PcmToWavUtil pcmToWavUtil=new PcmToWavUtil(44100,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        pcmToWavUtil.pcmToWav(FILE_NAME,FILE_NAME_WAW);
        Log.d("FILE_NAME--->2",FILE_NAME_WAW);
        String fileName="test_"+System.currentTimeMillis()+"wav";
        new UploadFileUtils(getApplicationContext(), fileName, FILE_NAME_WAW).asyncUploadFile(new UploadFileUtils.UploadCallBack() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {

            }

            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("FILE_NAME--->3",UploadFileUtils.baseOssUrl+fileName);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {

            }
        });
    }


 
}