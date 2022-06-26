package com.ardeveloper.plut.utils.bt;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.Nullable;

import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnections;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;

public class btconnect extends BluetoothConnections {
    public btconnect() {
    }

    @Nullable
    public static BluetoothConnection selectFirstPaired() {
        com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections printers = new com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections();
        BluetoothConnection[] bluetoothPrinters = printers.getList();
        if (bluetoothPrinters != null && bluetoothPrinters.length > 0) {
            BluetoothConnection[] var2 = bluetoothPrinters;
            int var3 = bluetoothPrinters.length;
            int var4 = 0;

            while(var4 < var3) {
                BluetoothConnection printer = var2[var4];

                try {
                    return printer.connect();
                } catch (EscPosConnectionException var7) {
                    var7.printStackTrace();
                    ++var4;
                }
            }
        }

        return null;
    }

    @SuppressLint({"MissingPermission"})
    @Nullable
    public BluetoothConnection[] getList() {
        BluetoothConnection[] bluetoothDevicesList = super.getList();
        if (bluetoothDevicesList == null) {
            return null;
        } else {
            int i = 0;
            BluetoothConnection[] printersTmp = new BluetoothConnection[bluetoothDevicesList.length];
            BluetoothConnection[] bluetoothPrinters = bluetoothDevicesList;
            int var5 = bluetoothDevicesList.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                BluetoothConnection bluetoothConnection = bluetoothPrinters[var6];
                BluetoothDevice device = bluetoothConnection.getDevice();
                int majDeviceCl = device.getBluetoothClass().getMajorDeviceClass();
                int deviceCl = device.getBluetoothClass().getDeviceClass();
//                if (majDeviceCl == 1536 && (deviceCl == 1664 || deviceCl == 1536)) {
//                    printersTmp[i++] = new BluetoothConnection(device);
//                }
                printersTmp[i++] = new BluetoothConnection(device);
            }

            bluetoothPrinters = new BluetoothConnection[i];
            System.arraycopy(printersTmp, 0, bluetoothPrinters, 0, i);
            return bluetoothPrinters;
        }
    }
}
