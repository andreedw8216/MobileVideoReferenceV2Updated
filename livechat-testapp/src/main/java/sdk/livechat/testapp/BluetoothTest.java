package sdk.livechat.testapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.media.AudioManager;

import java.util.List;

import sdk.livechat.Log;

public class BluetoothTest {

    public void start( Context context )
    {
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        AudioManager audMan = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if( !audMan.isBluetoothScoAvailableOffCall() )
        {
            Log.e( "No Sco" );
            return;
        }

        boolean ret = btAdapter.getProfileProxy(context, new BluetoothProfile.ServiceListener() {
            @Override
            public void onServiceConnected(int profile, BluetoothProfile proxy)
            {
                BluetoothHeadset btHeadset = (BluetoothHeadset) proxy;
                List<BluetoothDevice> list = btHeadset.getConnectedDevices();
                if( list.isEmpty() )
                {
                    Log.i("BT list is empty"  );
                    return;
                }

                Log.i( list.size() +  " BT Devices found" );

                BluetoothDevice btDev = list.get(0);
            }

            @Override
            public void onServiceDisconnected(int profile) {

            }
        }, BluetoothProfile.HEADSET);

        if( ret )
            return;
    }

}
