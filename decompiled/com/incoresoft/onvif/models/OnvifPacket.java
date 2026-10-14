/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.models;

import java.util.ArrayList;
import java.util.List;

public class OnvifPacket {
    public static final String TAG = OnvifPacket.class.getSimpleName();
    private String name;
    private String timestamp;
    private byte[] data;

    public OnvifPacket() {
        this("", new byte[0]);
    }

    public OnvifPacket(String name) {
        this(name, new byte[0]);
    }

    public OnvifPacket(String name, byte[] data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public static byte[] asciiToBytes(String ascii) {
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        ascii = ascii.replace("\\r", "\\0d");
        ascii = ascii.replace("\\n", "\\0a");
        for (int i = 0; i < ascii.length(); ++i) {
            int val1 = OnvifPacket.iAt(ascii, i);
            if (val1 < 32 || val1 > 126) continue;
            if (val1 == 92) {
                int val2 = OnvifPacket.iAt(ascii, i + 1);
                int val3 = OnvifPacket.iAt(ascii, i + 2);
                if (val2 <= -1 || val3 <= -1) continue;
                char c1 = ascii.charAt(i + 1);
                char c2 = ascii.charAt(i + 2);
                try {
                    val2 = Integer.parseInt("" + c1 + c2, 16) & 0xFF;
                    val3 = 0;
                    bytes.add((byte)(val2 + val3));
                }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                i += 2;
                continue;
            }
            val1 = OnvifPacket.iAt(ascii, i);
            bytes.add((byte)val1);
        }
        return OnvifPacket.toByteArray(bytes);
    }

    private static int iAt(String s2, int index) {
        if (index < s2.length()) {
            return s2.charAt(index) & 0xFF;
        }
        return -1;
    }

    private static byte[] toByteArray(List<Byte> list) {
        byte[] ret = new byte[list.size()];
        for (int i = 0; i < ret.length; ++i) {
            ret[i] = list.get(i);
        }
        return ret;
    }

    public String toAscii(byte[] data) {
        StringBuilder returnString = new StringBuilder();
        for (byte item : data) {
            if (item == 10) {
                returnString.append("\\n");
                continue;
            }
            if (item == 13) {
                returnString.append("\\r");
                continue;
            }
            if (item >= 32 && item <= 126) {
                returnString.append((char)item);
                continue;
            }
            Object hex = Integer.toHexString(item & 0xFF);
            if (((String)hex).length() == 1) {
                hex = "0" + (String)hex;
            }
            returnString.append("\\").append((String)hex);
        }
        return returnString.toString();
    }
}

