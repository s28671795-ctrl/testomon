/*
 * Decompiled with CFR 0.152.
 */
package com.license4j.util;

public class NtpMessage {
    private byte leapIndicator = 0;
    private byte versionNumber = (byte)3;
    private byte mode = 0;
    private short stratum = 0;
    private byte pollInterval = 0;
    private byte precision = 0;
    private double rootDelay = 0.0;
    private double rootDispersion = 0.0;
    private final byte[] referenceIdentifier = new byte[]{0, 0, 0, 0};
    private double referenceTimestamp = 0.0;
    private double originateTimestamp = 0.0;
    public double receiveTimestamp = 0.0;
    private double transmitTimestamp = 0.0;

    public NtpMessage(byte[] array) {
        this.leapIndicator = (byte)(array[0] >> 6 & 3);
        this.versionNumber = (byte)(array[0] >> 3 & 7);
        this.mode = (byte)(array[0] & 7);
        this.stratum = NtpMessage.convertByteToShort(array[1]);
        this.pollInterval = array[2];
        this.precision = array[3];
        this.rootDelay = NtpMessage.parseDouble(array, 4);
        this.rootDispersion = NtpMessage.parseDouble(array, 8);
        System.arraycopy(array, 12, this.referenceIdentifier, 0, this.referenceIdentifier.length);
        this.referenceTimestamp = NtpMessage.parseDouble(array, 16);
        this.originateTimestamp = NtpMessage.parseDouble(array, 24);
        this.receiveTimestamp = NtpMessage.parseDouble(array, 32);
        this.transmitTimestamp = NtpMessage.parseDouble(array, 40);
    }

    public NtpMessage() {
        this.mode = (byte)3;
        this.transmitTimestamp = (double)System.currentTimeMillis() / 1000.0 + 2.2089888E9;
    }

    public byte[] toByteArray() {
        byte[] byteArray = new byte[48];
        byteArray[0] = (byte)(this.leapIndicator << 6 | this.versionNumber << 3 | this.mode);
        byteArray[1] = (byte)this.stratum;
        byteArray[2] = this.pollInterval;
        byteArray[3] = this.precision;
        this.fillByteArray(byteArray, this.rootDelay, 4);
        this.fillByteArray(byteArray, this.rootDispersion, 8);
        System.arraycopy(this.referenceIdentifier, 0, byteArray, 12, this.referenceIdentifier.length);
        NtpMessage.encodeTimestamp(byteArray, 16, this.referenceTimestamp);
        NtpMessage.encodeTimestamp(byteArray, 24, this.originateTimestamp);
        NtpMessage.encodeTimestamp(byteArray, 32, this.receiveTimestamp);
        NtpMessage.encodeTimestamp(byteArray, 40, this.transmitTimestamp);
        return byteArray;
    }

    private static short convertByteToShort(byte value) {
        return (value & 0x80) == 128 ? (short)(128 + (value & 0x7F)) : (short)value;
    }

    private static double parseDouble(byte[] byteArray, int offset) {
        double value = 0.0;
        for (int i = 0; i < 8; ++i) {
            value += (double)NtpMessage.convertByteToShort(byteArray[offset + i]) * Math.pow(2.0, 3 - i << 3);
        }
        return value;
    }

    private void fillByteArray(byte[] byteArray, double value, int offset) {
        long convertedValue = (long)(value * 65536.0);
        byteArray[offset] = (byte)(convertedValue >> 24 & 0xFFL);
        byteArray[offset + 1] = (byte)(convertedValue >> 16 & 0xFFL);
        byteArray[offset + 2] = (byte)(convertedValue >> 8 & 0xFFL);
        byteArray[offset + 3] = (byte)(convertedValue & 0xFFL);
    }

    public static void encodeTimestamp(byte[] array, int pointer, double timestamp) {
        for (int i = 0; i < 8; ++i) {
            double factor = Math.pow(2.0, 3 - i << 3);
            array[pointer + i] = (byte)(timestamp / factor);
            timestamp -= (double)NtpMessage.convertByteToShort(array[pointer + i]) * factor;
        }
        array[7] = (byte)(Math.random() * 255.0);
    }
}

