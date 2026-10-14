/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.net.tftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.commons.net.io.FromNetASCIIOutputStream;
import org.apache.commons.net.io.ToNetASCIIInputStream;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPAckPacket;
import org.apache.commons.net.tftp.TFTPDataPacket;
import org.apache.commons.net.tftp.TFTPErrorPacket;
import org.apache.commons.net.tftp.TFTPPacket;
import org.apache.commons.net.tftp.TFTPPacketException;
import org.apache.commons.net.tftp.TFTPReadRequestPacket;
import org.apache.commons.net.tftp.TFTPWriteRequestPacket;

public class TFTPClient
extends TFTP {
    public static final int DEFAULT_MAX_TIMEOUTS = 5;
    private int __maxTimeouts = 5;
    private long totalBytesReceived = 0L;
    private long totalBytesSent = 0L;

    public void setMaxTimeouts(int numTimeouts) {
        this.__maxTimeouts = numTimeouts < 1 ? 1 : numTimeouts;
    }

    public int getMaxTimeouts() {
        return this.__maxTimeouts;
    }

    public long getTotalBytesReceived() {
        return this.totalBytesReceived;
    }

    public long getTotalBytesSent() {
        return this.totalBytesSent;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int receiveFile(String fileName, int mode, OutputStream output, InetAddress host2, int port) throws IOException {
        int bytesRead = 0;
        int lastBlock = 0;
        int block = 1;
        int hostPort = 0;
        int dataLength = 0;
        this.totalBytesReceived = 0L;
        if (mode == 0) {
            output = new FromNetASCIIOutputStream(output);
        }
        TFTPPacket sent = new TFTPReadRequestPacket(host2, port, fileName, mode);
        TFTPAckPacket ack = new TFTPAckPacket(host2, port, 0);
        this.beginBufferedOps();
        boolean justStarted = true;
        try {
            do {
                this.bufferedSend(sent);
                boolean wantReply = true;
                int timeouts = 0;
                block14: do {
                    try {
                        TFTPErrorPacket error;
                        TFTPPacket received = this.bufferedReceive();
                        int recdPort = received.getPort();
                        InetAddress recdAddress = received.getAddress();
                        if (justStarted) {
                            justStarted = false;
                            if (recdPort == port) {
                                error = new TFTPErrorPacket(recdAddress, recdPort, 5, "INCORRECT SOURCE PORT");
                                this.bufferedSend(error);
                                throw new IOException("Incorrect source port (" + recdPort + ") in request reply.");
                            }
                            hostPort = recdPort;
                            ack.setPort(hostPort);
                            if (!host2.equals(recdAddress)) {
                                host2 = recdAddress;
                                ack.setAddress(host2);
                                sent.setAddress(host2);
                            }
                        }
                        if (host2.equals(recdAddress) && recdPort == hostPort) {
                            switch (received.getType()) {
                                case 5: {
                                    error = (TFTPErrorPacket)received;
                                    throw new IOException("Error code " + error.getError() + " received: " + error.getMessage());
                                }
                                case 3: {
                                    TFTPDataPacket data = (TFTPDataPacket)received;
                                    dataLength = data.getDataLength();
                                    lastBlock = data.getBlockNumber();
                                    if (lastBlock == block) {
                                        try {
                                            output.write(data.getData(), data.getDataOffset(), dataLength);
                                        }
                                        catch (IOException e) {
                                            error = new TFTPErrorPacket(host2, hostPort, 3, "File write failed.");
                                            this.bufferedSend(error);
                                            throw e;
                                        }
                                        if (++block > 65535) {
                                            block = 0;
                                        }
                                        wantReply = false;
                                        break;
                                    }
                                    this.discardPackets();
                                    if (lastBlock != (block == 0 ? 65535 : block - 1)) continue block14;
                                    wantReply = false;
                                    break;
                                }
                                default: {
                                    throw new IOException("Received unexpected packet type (" + received.getType() + ")");
                                }
                            }
                            continue;
                        }
                        error = new TFTPErrorPacket(recdAddress, recdPort, 5, "Unexpected host or port.");
                        this.bufferedSend(error);
                    }
                    catch (SocketException e) {
                        if (++timeouts < this.__maxTimeouts) continue;
                        throw new IOException("Connection timed out.");
                    }
                    catch (InterruptedIOException e) {
                        if (++timeouts < this.__maxTimeouts) continue;
                        throw new IOException("Connection timed out.");
                    }
                    catch (TFTPPacketException e) {
                        throw new IOException("Bad packet: " + e.getMessage());
                    }
                } while (wantReply);
                ack.setBlockNumber(lastBlock);
                sent = ack;
                bytesRead += dataLength;
                this.totalBytesReceived += (long)dataLength;
            } while (dataLength == 512);
            this.bufferedSend(sent);
        }
        finally {
            this.endBufferedOps();
        }
        return bytesRead;
    }

    public int receiveFile(String fileName, int mode, OutputStream output, String hostname, int port) throws UnknownHostException, IOException {
        return this.receiveFile(fileName, mode, output, InetAddress.getByName(hostname), port);
    }

    public int receiveFile(String fileName, int mode, OutputStream output, InetAddress host2) throws IOException {
        return this.receiveFile(fileName, mode, output, host2, 69);
    }

    public int receiveFile(String fileName, int mode, OutputStream output, String hostname) throws UnknownHostException, IOException {
        return this.receiveFile(fileName, mode, output, InetAddress.getByName(hostname), 69);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void sendFile(String fileName, int mode, InputStream input, InetAddress host2, int port) throws IOException {
        int block = 0;
        int hostPort = 0;
        boolean justStarted = true;
        boolean lastAckWait = false;
        this.totalBytesSent = 0L;
        if (mode == 0) {
            input = new ToNetASCIIInputStream(input);
        }
        TFTPPacket sent = new TFTPWriteRequestPacket(host2, port, fileName, mode);
        TFTPDataPacket data = new TFTPDataPacket(host2, port, 0, this._sendBuffer, 4, 0);
        this.beginBufferedOps();
        try {
            while (true) {
                this.bufferedSend(sent);
                boolean wantReply = true;
                int timeouts = 0;
                do {
                    try {
                        TFTPPacket received = this.bufferedReceive();
                        InetAddress recdAddress = received.getAddress();
                        int recdPort = received.getPort();
                        if (justStarted) {
                            justStarted = false;
                            if (recdPort == port) {
                                TFTPErrorPacket error = new TFTPErrorPacket(recdAddress, recdPort, 5, "INCORRECT SOURCE PORT");
                                this.bufferedSend(error);
                                throw new IOException("Incorrect source port (" + recdPort + ") in request reply.");
                            }
                            hostPort = recdPort;
                            data.setPort(hostPort);
                            if (!host2.equals(recdAddress)) {
                                host2 = recdAddress;
                                data.setAddress(host2);
                                sent.setAddress(host2);
                            }
                        }
                        if (host2.equals(recdAddress) && recdPort == hostPort) {
                            switch (received.getType()) {
                                case 5: {
                                    TFTPErrorPacket error = (TFTPErrorPacket)received;
                                    throw new IOException("Error code " + error.getError() + " received: " + error.getMessage());
                                }
                                case 4: {
                                    int lastBlock = ((TFTPAckPacket)received).getBlockNumber();
                                    if (lastBlock == block) {
                                        if (++block > 65535) {
                                            block = 0;
                                        }
                                        wantReply = false;
                                        break;
                                    }
                                    this.discardPackets();
                                    break;
                                }
                                default: {
                                    throw new IOException("Received unexpected packet type.");
                                }
                            }
                            continue;
                        }
                        TFTPErrorPacket error = new TFTPErrorPacket(recdAddress, recdPort, 5, "Unexpected host or port.");
                        this.bufferedSend(error);
                    }
                    catch (SocketException e) {
                        if (++timeouts < this.__maxTimeouts) continue;
                        throw new IOException("Connection timed out.");
                    }
                    catch (InterruptedIOException e) {
                        if (++timeouts < this.__maxTimeouts) continue;
                        throw new IOException("Connection timed out.");
                    }
                    catch (TFTPPacketException e) {
                        throw new IOException("Bad packet: " + e.getMessage());
                    }
                } while (wantReply);
                if (lastAckWait) {
                    break;
                }
                int dataLength = 512;
                int offset = 4;
                int totalThisPacket = 0;
                int bytesRead = 0;
                while (dataLength > 0 && (bytesRead = input.read(this._sendBuffer, offset, dataLength)) > 0) {
                    offset += bytesRead;
                    dataLength -= bytesRead;
                    totalThisPacket += bytesRead;
                }
                if (totalThisPacket < 512) {
                    lastAckWait = true;
                }
                data.setBlockNumber(block);
                data.setData(this._sendBuffer, 4, totalThisPacket);
                sent = data;
                this.totalBytesSent += (long)totalThisPacket;
            }
        }
        finally {
            this.endBufferedOps();
        }
    }

    public void sendFile(String fileName, int mode, InputStream input, String hostname, int port) throws UnknownHostException, IOException {
        this.sendFile(fileName, mode, input, InetAddress.getByName(hostname), port);
    }

    public void sendFile(String fileName, int mode, InputStream input, InetAddress host2) throws IOException {
        this.sendFile(fileName, mode, input, host2, 69);
    }

    public void sendFile(String fileName, int mode, InputStream input, String hostname) throws UnknownHostException, IOException {
        this.sendFile(fileName, mode, input, InetAddress.getByName(hostname), 69);
    }
}

