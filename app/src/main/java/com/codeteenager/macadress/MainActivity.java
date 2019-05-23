package com.codeteenager.macadress;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.net.NetworkInterface;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.get_mac_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, getWifiMacAddress(MainActivity.this), Toast.LENGTH_LONG).show();
            }
        });
    }

    public String getWifiMacAddress(Context context) {
        String ret = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String interfaceName = "wlan0";
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                NetworkInterface intf = null;
                while (interfaces.hasMoreElements()) {
                    intf = interfaces.nextElement();
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) {
                        continue;
                    }
                    byte[] mac = intf.getHardwareAddress();
                    if (mac != null) {
                        StringBuilder buf = new StringBuilder();
                        for (byte aMac : mac) {
                            buf.append(String.format("%02X:", aMac));
                        }
                        if (buf.length() > 0) {
                            buf.deleteCharAt(buf.length() - 1);
                        }
                        ret = buf.toString();
                    }
                    break;
                }
            } else {
                WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                if (wifi != null) {
                    WifiInfo wifiInfo = wifi.getConnectionInfo();
                    if (wifiInfo != null) {
                        ret = wifiInfo.getMacAddress();
                    }
                }
            }
            return ret;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ret;
    }
}
