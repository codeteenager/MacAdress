# MacAdress
Android各种机型获取mac地址的兼容方案

```java
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
```
