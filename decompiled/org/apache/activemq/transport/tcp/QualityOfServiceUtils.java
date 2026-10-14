/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.tcp;

import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class QualityOfServiceUtils {
    private static final int MAX_DIFF_SERV = 63;
    private static final int MIN_DIFF_SERV = 0;
    private static final Map<String, Integer> DIFF_SERV_NAMES = new HashMap<String, Integer>();
    private static final int MAX_TOS = 255;
    private static final int MIN_TOS = 0;

    public static int getDSCP(String value) throws IllegalArgumentException {
        int intValue = -1;
        if (DIFF_SERV_NAMES.containsKey(value)) {
            intValue = DIFF_SERV_NAMES.get(value);
        } else {
            try {
                intValue = Integer.parseInt(value);
                if (intValue > 63 || intValue < 0) {
                    throw new IllegalArgumentException("Differentiated Services value: " + intValue + " not in legal range [" + 0 + ", " + 63 + "].");
                }
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException("No such Differentiated Services name: " + value);
            }
        }
        return QualityOfServiceUtils.adjustDSCPForECN(intValue);
    }

    public static int getToS(int value) throws IllegalArgumentException {
        if (value > 255 || value < 0) {
            throw new IllegalArgumentException("Type of Service value: " + value + " not in legal range [" + 0 + ", " + 255 + ".");
        }
        return value;
    }

    private static int adjustDSCPForECN(int dscp) throws IllegalArgumentException {
        Socket socket = new Socket();
        try {
            int systemTrafficClass = socket.getTrafficClass();
            return dscp << 2 | systemTrafficClass & 3;
        }
        catch (SocketException e) {
            throw new IllegalArgumentException("Setting Differentiated Services not supported: " + e);
        }
    }

    static {
        DIFF_SERV_NAMES.put("CS0", 0);
        DIFF_SERV_NAMES.put("CS1", 8);
        DIFF_SERV_NAMES.put("CS2", 16);
        DIFF_SERV_NAMES.put("CS3", 24);
        DIFF_SERV_NAMES.put("CS4", 32);
        DIFF_SERV_NAMES.put("CS5", 40);
        DIFF_SERV_NAMES.put("CS6", 48);
        DIFF_SERV_NAMES.put("CS7", 56);
        DIFF_SERV_NAMES.put("AF11", 10);
        DIFF_SERV_NAMES.put("AF12", 12);
        DIFF_SERV_NAMES.put("AF13", 14);
        DIFF_SERV_NAMES.put("AF21", 18);
        DIFF_SERV_NAMES.put("AF22", 20);
        DIFF_SERV_NAMES.put("AF23", 22);
        DIFF_SERV_NAMES.put("AF31", 26);
        DIFF_SERV_NAMES.put("AF32", 28);
        DIFF_SERV_NAMES.put("AF33", 30);
        DIFF_SERV_NAMES.put("AF41", 34);
        DIFF_SERV_NAMES.put("AF42", 36);
        DIFF_SERV_NAMES.put("AF43", 38);
        DIFF_SERV_NAMES.put("EF", 46);
    }
}

