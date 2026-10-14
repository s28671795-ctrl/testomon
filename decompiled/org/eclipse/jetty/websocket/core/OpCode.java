/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core;

public final class OpCode {
    public static final byte CONTINUATION = 0;
    public static final byte TEXT = 1;
    public static final byte BINARY = 2;
    public static final byte CLOSE = 8;
    public static final byte PING = 9;
    public static final byte PONG = 10;
    public static final byte UNDEFINED = -1;

    public static byte getOpCode(byte firstByte) {
        return (byte)(firstByte & 0xF);
    }

    public static boolean isControlFrame(byte opcode) {
        switch (opcode) {
            case 8: 
            case 9: 
            case 10: {
                return true;
            }
        }
        return false;
    }

    public static boolean isDataFrame(byte opcode) {
        switch (opcode) {
            case 0: 
            case 1: 
            case 2: {
                return true;
            }
        }
        return false;
    }

    public static boolean isKnown(byte opcode) {
        switch (opcode) {
            case 0: 
            case 1: 
            case 2: 
            case 8: 
            case 9: 
            case 10: {
                return true;
            }
        }
        return false;
    }

    public static String name(byte opcode) {
        switch (opcode) {
            case -1: {
                return "NO-OP";
            }
            case 0: {
                return "CONTINUATION";
            }
            case 1: {
                return "TEXT";
            }
            case 2: {
                return "BINARY";
            }
            case 8: {
                return "CLOSE";
            }
            case 9: {
                return "PING";
            }
            case 10: {
                return "PONG";
            }
        }
        return "NON-SPEC[" + opcode + "]";
    }
}

