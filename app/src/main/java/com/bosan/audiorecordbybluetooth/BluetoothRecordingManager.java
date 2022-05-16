package com.bosan.audiorecordbybluetooth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

public class BluetoothRecordingManager {

    private static int count = 0;
        private static int TIMEOUT = 30000;
        private static int COUNTDOWN_INTERVAL = 1000;
        private static final int MAX_ATTEPTS_TO_CONNECT = 3;

        /**
         * This method check for bluetooh settings (bluetooth flag and bluetooth is
         * on or off) and decide wheather to record from bluetooth headset or phone
         * mic. If settings are not correct then start's recording using phone mic.
         *
         * @param context
         * @param BluetoothRecording :- Interface object
         * @param resume             :- Pass through
         */
        public static void checkAndRecord(final Context context, final OnBluetoothRecording BluetoothRecording, boolean resume) {

            if (getBluetoothFlag() && isBluetoothON()) {
                Log.d("start ble recording"," calling");
                startBluetoothRecording(BluetoothRecording, resume, context);
            } else {
                Log.d("start ble recording"," calling2222");
                // If Bluetooth is OFF Show Toast else Dont Show
                if (getBluetoothFlag() && !isBluetoothON()) {
                    Toast.makeText(context, "bluetooth_off", Toast.LENGTH_LONG).show();
                    // false because recording not started
                    BluetoothRecording.onStartRecording(resume, false);
                } else {
                    // false because recording not started
                    BluetoothRecording.onStartRecording(resume, false);
                }
            }
        }
    public interface OnBluetoothRecording {

        void onStartRecording(boolean state,boolean bluetoothFlag);
        void onCancelRecording();
    }

        /**
         * Connect to bluetooth headset and start recording if headset failed to
         * connect then records normally using phone mic.
         *
         * @param BluetoothRecording
         * @param resume
         * @param context
         */
        private static void startBluetoothRecording(final OnBluetoothRecording BluetoothRecording, final boolean resume, final Context context) {
            final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            //audioManager.setBluetoothScoOn(true);

            final CountDownTimer timer = getTimer(BluetoothRecording, audioManager, resume);
            Log.d("inside","startBlue rec");

            final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    int state = intent.getIntExtra(AudioManager.EXTRA_SCO_AUDIO_STATE, -1);
                    Log.d("inside onRcv","state="+state);
                    if (AudioManager.SCO_AUDIO_STATE_CONNECTED == state ) {
                        // cancel Timer
                        timer.cancel();
                        Log.d("inside onRcv","Unregister rcvr");
                        context.unregisterReceiver(this);
                        Log.d("inside onRcv","Connected Record from bluetooth");
                        // pass through and true because
                        // recording from bluetooth so set 8000kHz
                        BluetoothRecording.onStartRecording(resume, true);
                    }
                }
            };

            Log.d("calling","registr broadcast rcvr");
            context.registerReceiver(broadcastReceiver, new IntentFilter(AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED));
            // Start the timer

            try {
                // Android 2.2 onwards supports BT SCO for non-voice call use case
                // Check the Android version whether it supports or not.
                if(audioManager.isBluetoothScoAvailableOffCall()){
                    if(audioManager.isBluetoothScoOn()){
                        Log.d("SCO","stopped SCO");
                        // audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                        audioManager.stopBluetoothSco();
                        timer.start();
                        Log.d("Starting sco","start");
                        audioManager.startBluetoothSco();
                    }else {
                        timer.start();
                        Log.d("Starting sco","start");
                        audioManager.startBluetoothSco();
                    }
                }else {
                    Log.d("Sco","Not availiable");
                }
            } catch (Exception e) {
                Log.d("sco els starbletoothSCO"," "+e);
                timer.cancel();
                context.unregisterReceiver(broadcastReceiver);
                BluetoothRecording.onStartRecording(resume, false);
            }
        }

        /**
         * set the Timeout
         *
         * @param BluetoothRecording
         * @param audioManager
         * @param resume
         * @return
         */
        private static CountDownTimer getTimer(final OnBluetoothRecording BluetoothRecording, final AudioManager audioManager, final boolean resume) {
            return new CountDownTimer(TIMEOUT, COUNTDOWN_INTERVAL) {
                @Override
                public void onTick(long millisUntilFinished) {
                    // Do Nothing
                }

                @Override
                public void onFinish() {
                    // stopBluetoothSCO() and start Normal Recording
                    audioManager.stopBluetoothSco();

                    // false because recording button is already clicked but still not recording.
                    BluetoothRecording.onStartRecording(resume, false);
                }
            };
        }

        /**
         * Return's the bluetooth state
         *
         * @return
         */
        private static boolean isBluetoothON() {
    /*BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (bluetoothAdapter != null) {
        return bluetoothAdapter.isEnabled();
    } else {
        return false;
    }*/
            return true;
        }

        /**
         * Return's the bluetoothFlag state
         *
         * @return
         */
        private static boolean getBluetoothFlag() {

            return true;
        }
}