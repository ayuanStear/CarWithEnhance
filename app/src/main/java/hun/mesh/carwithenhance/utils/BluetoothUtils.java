package hun.mesh.carwithenhance.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public class BluetoothUtils {
    public static void disconnectBluetoothDevice(Context context, String str) {
        final BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            XLog.e("BluetoothAdapter is null, can't disconnect bluetooth!");
            return;
        }
        try {
            final BluetoothDevice remoteDevice = defaultAdapter.getRemoteDevice(str);
            defaultAdapter.getProfileProxy(context, new BluetoothProfile.ServiceListener() { // from class: hun.mesh.carwithenhance.utils.BluetoothUtils.1
                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                    try {
                        Method method = bluetoothProfile.getClass().getMethod("disconnect", BluetoothDevice.class);
                        method.setAccessible(true);
                        method.invoke(bluetoothProfile, remoteDevice);
                        defaultAdapter.closeProfileProxy(i, bluetoothProfile);
                    } catch (Exception e) {
                        XLog.e("断开 HEADSET Profile 失败: ", e);
                    }
                }

                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceDisconnected(int i) {
                }
            }, 1);
            defaultAdapter.getProfileProxy(context, new BluetoothProfile.ServiceListener() { // from class: hun.mesh.carwithenhance.utils.BluetoothUtils.2
                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                    try {
                        Method method = bluetoothProfile.getClass().getMethod("disconnect", BluetoothDevice.class);
                        method.setAccessible(true);
                        method.invoke(bluetoothProfile, remoteDevice);
                        defaultAdapter.closeProfileProxy(i, bluetoothProfile);
                    } catch (Exception e) {
                        XLog.e("断开 A2DP Profile 失败: ", e);
                    }
                }

                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public void onServiceDisconnected(int i) {
                }
            }, 2);
        } catch (IllegalArgumentException unused) {
            XLog.e("Invalid Bluetooth MAC address: " + str);
        }
    }
}
