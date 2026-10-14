/*
 * Decompiled with CFR 0.152.
 */
package de.onvif.soap;

import de.onvif.soap.SOAP;
import de.onvif.soap.devices.ImagingDevices;
import de.onvif.soap.devices.InitialDevices;
import de.onvif.soap.devices.MediaDevices;
import de.onvif.soap.devices.PtzDevices;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.Random;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import javax.xml.soap.SOAPException;
import org.onvif.ver10.schema.Capabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnvifDevice {
    private static final Logger log = LoggerFactory.getLogger(OnvifDevice.class);
    private final String HOST_IP;
    private String originalIp;
    private boolean isProxy;
    private final String username;
    private final String password;
    private String nonce;
    private String utcTime;
    private final String serverDeviceUri;
    private String serverPtzUri;
    private String serverMediaUri;
    private String serverImagingUri;
    private String serverEventsUri;
    private final SOAP soap;
    private final InitialDevices initialDevices;
    private final PtzDevices ptzDevices;
    private final MediaDevices mediaDevices;
    private final ImagingDevices imagingDevices;

    public OnvifDevice(String hostIp, String user, String password) throws ConnectException, SOAPException {
        this.HOST_IP = hostIp;
        if (!this.isOnline()) {
            throw new ConnectException("Host not available.");
        }
        this.serverDeviceUri = "http://" + this.HOST_IP + "/onvif/device_service";
        this.username = user;
        this.password = password;
        this.soap = new SOAP(this);
        this.initialDevices = new InitialDevices(this);
        this.ptzDevices = new PtzDevices(this);
        this.mediaDevices = new MediaDevices(this);
        this.imagingDevices = new ImagingDevices(this);
        this.init();
    }

    public OnvifDevice(String hostIp) throws ConnectException, SOAPException {
        this(hostIp, null, null);
    }

    private boolean isOnline() {
        return this.performSocketConnection(this.getIpFromHost(), this.getPortFromHost());
    }

    private String getIpFromHost() {
        return this.HOST_IP.contains(":") ? this.HOST_IP.substring(0, this.HOST_IP.indexOf(58)) : this.HOST_IP;
    }

    private String getPortFromHost() {
        return this.HOST_IP.contains(":") ? this.HOST_IP.substring(this.HOST_IP.indexOf(58) + 1) : "80";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean performSocketConnection(String ip2, String port) {
        Socket socket = null;
        try {
            InetSocketAddress sockaddr = new InetSocketAddress(ip2, Integer.parseInt(port));
            socket = new Socket();
            socket.connect(sockaddr, 5000);
            this.closeSocketQuietly(socket);
        }
        catch (IOException | NumberFormatException e) {
            boolean bl = false;
            return bl;
        }
        finally {
            this.closeSocketQuietly(socket);
        }
        return true;
    }

    private void closeSocketQuietly(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    protected void init() throws ConnectException, SOAPException {
        Capabilities capabilities = this.getDevices().getCapabilities();
        if (capabilities == null) {
            throw new ConnectException("Capabilities not reachable.");
        }
        this.processDeviceUri(capabilities);
        this.checkIfDeviceIsProxy();
        this.initializeUri(capabilities);
    }

    private void processDeviceUri(Capabilities capabilities) {
        String localDeviceUri = capabilities.getDevice().getXAddr();
        if (localDeviceUri.startsWith("http://")) {
            this.extractOriginalIp(localDeviceUri);
        } else {
            log.error("Unknown/Not implemented local protocol!");
        }
    }

    private void extractOriginalIp(String localDeviceUri) {
        this.originalIp = localDeviceUri.replace("http://", "");
        this.originalIp = this.originalIp.substring(0, this.originalIp.indexOf(47));
    }

    private void checkIfDeviceIsProxy() {
        if (!this.originalIp.equals(this.HOST_IP)) {
            this.isProxy = true;
        }
    }

    private void initializeUri(Capabilities capabilities) {
        if (capabilities.getMedia() != null) {
            this.serverMediaUri = this.getServerUri(capabilities.getMedia().getXAddr());
            this.serverEventsUri = this.getServerUri(capabilities.getEvents().getXAddr());
        }
        this.serverPtzUri = this.getServerUri(capabilities.getPTZ().getXAddr());
        this.serverImagingUri = this.getServerUri(capabilities.getImaging().getXAddr());
    }

    private String getServerUri(String original) {
        return this.replaceLocalIpWithProxyIp(this.checkUriFormat(original));
    }

    private String checkUriFormat(String original) {
        if (original.startsWith("http:///")) {
            original = original.replace("http:///", "http://" + this.HOST_IP);
        }
        return original;
    }

    public String replaceLocalIpWithProxyIp(String original) {
        if (original.startsWith("http:///")) {
            original = original.replace("http:///", "http://" + this.HOST_IP);
        }
        if (this.isProxy) {
            return original.replace(this.originalIp, this.HOST_IP);
        }
        return original;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEncryptedPassword() {
        return this.encryptPassword();
    }

    public String encryptPassword() {
        byte[] encryptedRaw;
        String nonce = this.getNonce();
        String timestamp = this.getUTCTime();
        String beforeEncryption = nonce + timestamp + this.password;
        try {
            encryptedRaw = OnvifDevice.sha1(beforeEncryption);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return Base64.getEncoder().encodeToString(encryptedRaw);
    }

    private static byte[] sha1(String s2) throws NoSuchAlgorithmException {
        MessageDigest SHA12 = MessageDigest.getInstance("SHA1");
        SHA12.reset();
        SHA12.update(s2.getBytes());
        return SHA12.digest();
    }

    private String getNonce() {
        return Optional.ofNullable(this.nonce).orElse(this.createNonce());
    }

    public String getEncryptedNonce() {
        return Optional.of(this.getNonce()).map(String::getBytes).map(Base64.getEncoder()::encodeToString).orElseThrow(() -> new IllegalStateException("Nonce is null"));
    }

    public String createNonce() {
        Random generator = new Random();
        this.nonce = String.valueOf(generator.nextInt());
        return this.nonce;
    }

    public String getLastUTCTime() {
        return this.utcTime;
    }

    public String getUTCTime() {
        String utcTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d'T'HH:mm:ss'Z'");
        sdf.setTimeZone(new SimpleTimeZone(2, "UTC"));
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        this.utcTime = utcTime = sdf.format(cal.getTime());
        return utcTime;
    }

    public SOAP getSoap() {
        return this.soap;
    }

    public InitialDevices getDevices() {
        return this.initialDevices;
    }

    public PtzDevices getPtz() {
        return this.ptzDevices;
    }

    public MediaDevices getMedia() {
        return this.mediaDevices;
    }

    public ImagingDevices getImaging() {
        return this.imagingDevices;
    }

    public String getDeviceUri() {
        return this.serverDeviceUri;
    }

    protected String getPtzUri() {
        return this.serverPtzUri;
    }

    protected String getMediaUri() {
        return this.serverMediaUri;
    }

    protected String getImagingUri() {
        return this.serverImagingUri;
    }

    protected String getEventsUri() {
        return this.serverEventsUri;
    }

    public Date getDate() {
        return this.initialDevices.getDate();
    }

    public String getName() {
        return this.initialDevices.getDeviceInformation().getModel();
    }

    public String getHostname() {
        return this.initialDevices.getHostname();
    }

    public String reboot() throws ConnectException, SOAPException {
        return this.initialDevices.reboot();
    }
}

