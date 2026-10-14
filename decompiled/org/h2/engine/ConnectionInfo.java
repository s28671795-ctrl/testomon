/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import org.h2.command.dml.SetTypes;
import org.h2.engine.DbSettings;
import org.h2.engine.SysProperties;
import org.h2.message.DbException;
import org.h2.security.SHA256;
import org.h2.store.fs.FileUtils;
import org.h2.store.fs.encrypt.FilePathEncrypt;
import org.h2.store.fs.rec.FilePathRec;
import org.h2.util.IOUtils;
import org.h2.util.NetworkConnectionInfo;
import org.h2.util.SortedProperties;
import org.h2.util.StringUtils;
import org.h2.util.TimeZoneProvider;
import org.h2.util.Utils;

public class ConnectionInfo
implements Cloneable {
    private static final HashSet<String> KNOWN_SETTINGS;
    private static final HashSet<String> IGNORED_BY_PARSER;
    private Properties prop = new Properties();
    private String originalURL;
    private String url;
    private String user;
    private byte[] filePasswordHash;
    private byte[] fileEncryptionKey;
    private byte[] userPasswordHash;
    private TimeZoneProvider timeZone;
    private String name;
    private String nameNormalized;
    private boolean remote;
    private boolean ssl;
    private boolean persistent;
    private boolean unnamed;
    private NetworkConnectionInfo networkConnectionInfo;

    public ConnectionInfo(String string) {
        this.name = string;
        this.url = "jdbc:h2:" + string;
        this.parseName();
    }

    public ConnectionInfo(String string, Properties properties, String string2, Object object) {
        this.originalURL = this.url = (string = ConnectionInfo.remapURL(string));
        if (!string.startsWith("jdbc:h2:")) {
            throw this.getFormatException();
        }
        if (properties != null) {
            this.readProperties(properties);
        }
        if (string2 != null) {
            this.prop.put("USER", string2);
        }
        if (object != null) {
            this.prop.put("PASSWORD", object);
        }
        this.readSettingsFromURL();
        Object object2 = this.prop.remove("TIME ZONE");
        if (object2 != null) {
            this.timeZone = TimeZoneProvider.ofId(object2.toString());
        }
        this.setUserName(this.removeProperty("USER", ""));
        this.name = this.url.substring("jdbc:h2:".length());
        this.parseName();
        this.convertPasswords();
        String string3 = this.removeProperty("RECOVER_TEST", null);
        if (string3 != null) {
            FilePathRec.register();
            try {
                Utils.callStaticMethod("org.h2.store.RecoverTester.init", string3);
            }
            catch (Exception exception) {
                throw DbException.convert(exception);
            }
            this.name = "rec:" + this.name;
        }
    }

    private static boolean isKnownSetting(String string) {
        return KNOWN_SETTINGS.contains(string);
    }

    public static boolean isIgnoredByParser(String string) {
        return IGNORED_BY_PARSER.contains(string);
    }

    public ConnectionInfo clone() throws CloneNotSupportedException {
        ConnectionInfo connectionInfo = (ConnectionInfo)super.clone();
        connectionInfo.prop = (Properties)this.prop.clone();
        connectionInfo.filePasswordHash = Utils.cloneByteArray(this.filePasswordHash);
        connectionInfo.fileEncryptionKey = Utils.cloneByteArray(this.fileEncryptionKey);
        connectionInfo.userPasswordHash = Utils.cloneByteArray(this.userPasswordHash);
        return connectionInfo;
    }

    private void parseName() {
        if (".".equals(this.name)) {
            this.name = "mem:";
        }
        if (this.name.startsWith("tcp:")) {
            this.remote = true;
            this.name = this.name.substring("tcp:".length());
        } else if (this.name.startsWith("ssl:")) {
            this.remote = true;
            this.ssl = true;
            this.name = this.name.substring("ssl:".length());
        } else if (this.name.startsWith("mem:")) {
            this.persistent = false;
            if ("mem:".equals(this.name)) {
                this.unnamed = true;
            }
        } else if (this.name.startsWith("file:")) {
            this.name = this.name.substring("file:".length());
            this.persistent = true;
        } else {
            this.persistent = true;
        }
        if (this.persistent && !this.remote) {
            this.name = IOUtils.nameSeparatorsToNative(this.name);
        }
    }

    public void setBaseDir(String string) {
        if (this.persistent) {
            String string2;
            String string3 = FileUtils.unwrap(FileUtils.toRealPath(string));
            boolean bl = FileUtils.isAbsolute(this.name);
            String string4 = null;
            if (string.endsWith(File.separator)) {
                string = string.substring(0, string.length() - 1);
            }
            if (bl) {
                string2 = this.name;
            } else {
                string2 = FileUtils.unwrap(this.name);
                string4 = this.name.substring(0, this.name.length() - string2.length());
                string2 = string + File.separatorChar + string2;
            }
            String string5 = FileUtils.unwrap(FileUtils.toRealPath(string2));
            if (string5.equals(string3) || !string5.startsWith(string3)) {
                throw DbException.get(90028, string5 + " outside " + string3);
            }
            if (!string3.endsWith("/") && !string3.endsWith("\\") && string5.charAt(string3.length()) != '/') {
                throw DbException.get(90028, string5 + " outside " + string3);
            }
            if (!bl) {
                this.name = string4 + string + File.separatorChar + FileUtils.unwrap(this.name);
            }
        }
    }

    public boolean isRemote() {
        return this.remote;
    }

    public boolean isPersistent() {
        return this.persistent;
    }

    boolean isUnnamedInMemory() {
        return this.unnamed;
    }

    private void readProperties(Properties properties) {
        Object[] objectArray = properties.keySet().toArray();
        DbSettings dbSettings = null;
        for (Object object : objectArray) {
            String string = StringUtils.toUpperEnglish(object.toString());
            if (this.prop.containsKey(string)) {
                throw DbException.get(90066, string);
            }
            Object object2 = properties.get(object);
            if (ConnectionInfo.isKnownSetting(string)) {
                this.prop.put(string, object2);
                continue;
            }
            if (dbSettings == null) {
                dbSettings = this.getDbSettings();
            }
            if (!dbSettings.containsKey(string)) continue;
            this.prop.put(string, object2);
        }
    }

    private void readSettingsFromURL() {
        DbSettings dbSettings = DbSettings.DEFAULT;
        int n = this.url.indexOf(59);
        if (n >= 0) {
            String[] stringArray;
            String string = this.url.substring(n + 1);
            this.url = this.url.substring(0, n);
            String string2 = null;
            for (String string3 : stringArray = StringUtils.arraySplit(string, ';', false)) {
                if (string3.isEmpty()) continue;
                int n2 = string3.indexOf(61);
                if (n2 < 0) {
                    throw this.getFormatException();
                }
                String string4 = string3.substring(n2 + 1);
                String string5 = string3.substring(0, n2);
                if (ConnectionInfo.isKnownSetting(string5 = StringUtils.toUpperEnglish(string5)) || dbSettings.containsKey(string5)) {
                    String string6 = this.prop.getProperty(string5);
                    if (string6 != null && !string6.equals(string4)) {
                        throw DbException.get(90066, string5);
                    }
                    this.prop.setProperty(string5, string4);
                    continue;
                }
                string2 = string5;
            }
            if (string2 != null && !Utils.parseBoolean(this.prop.getProperty("IGNORE_UNKNOWN_SETTINGS"), false, false)) {
                throw DbException.get(90113, string2);
            }
        }
    }

    private void preservePasswordForAuthentication(Object object) {
        if ((!this.isRemote() || this.isSSL()) && this.prop.containsKey("AUTHREALM") && object != null) {
            this.prop.put("AUTHZPWD", object instanceof char[] ? new String((char[])object) : object);
        }
    }

    private char[] removePassword() {
        Object object = this.prop.remove("PASSWORD");
        this.preservePasswordForAuthentication(object);
        if (object == null) {
            return new char[0];
        }
        if (object instanceof char[]) {
            return (char[])object;
        }
        return object.toString().toCharArray();
    }

    private void convertPasswords() {
        char[] cArray = this.removePassword();
        boolean bl = this.removeProperty("PASSWORD_HASH", false);
        if (this.getProperty("CIPHER", null) != null) {
            int n = -1;
            int n2 = cArray.length;
            for (int i = 0; i < n2; ++i) {
                if (cArray[i] != ' ') continue;
                n = i;
                break;
            }
            if (n < 0) {
                throw DbException.get(90050);
            }
            char[] cArray2 = Arrays.copyOfRange(cArray, n + 1, cArray.length);
            char[] cArray3 = Arrays.copyOf(cArray, n);
            Arrays.fill(cArray, '\u0000');
            cArray = cArray2;
            this.fileEncryptionKey = FilePathEncrypt.getPasswordBytes(cArray3);
            this.filePasswordHash = ConnectionInfo.hashPassword(bl, "file", cArray3);
        }
        this.userPasswordHash = ConnectionInfo.hashPassword(bl, this.user, cArray);
    }

    private static byte[] hashPassword(boolean bl, String string, char[] cArray) {
        if (bl) {
            return StringUtils.convertHexToBytes(new String(cArray));
        }
        if (string.isEmpty() && cArray.length == 0) {
            return new byte[0];
        }
        return SHA256.getKeyPasswordHash(string, cArray);
    }

    public boolean getProperty(String string, boolean bl) {
        return Utils.parseBoolean(this.getProperty(string, null), bl, false);
    }

    public boolean removeProperty(String string, boolean bl) {
        return Utils.parseBoolean(this.removeProperty(string, null), bl, false);
    }

    String removeProperty(String string, String string2) {
        if (SysProperties.CHECK && !ConnectionInfo.isKnownSetting(string)) {
            throw DbException.getInternalError(string);
        }
        Object object = this.prop.remove(string);
        return object == null ? string2 : object.toString();
    }

    public String getName() {
        if (!this.persistent) {
            return this.name;
        }
        if (this.nameNormalized == null) {
            if (!(FileUtils.isAbsolute(this.name) || this.name.contains("./") || this.name.contains(".\\") || this.name.contains(":/") || this.name.contains(":\\"))) {
                throw DbException.get(90011, this.originalURL);
            }
            String string = ".mv.db";
            String string2 = FileUtils.toRealPath(this.name + string);
            String string3 = FileUtils.getName(string2);
            if (string3.length() < string.length() + 1) {
                throw DbException.get(90138, this.name);
            }
            this.nameNormalized = string2.substring(0, string2.length() - string.length());
        }
        return this.nameNormalized;
    }

    public byte[] getFilePasswordHash() {
        return this.filePasswordHash;
    }

    byte[] getFileEncryptionKey() {
        return this.fileEncryptionKey;
    }

    public String getUserName() {
        return this.user;
    }

    byte[] getUserPasswordHash() {
        return this.userPasswordHash;
    }

    String[] getKeys() {
        return this.prop.keySet().toArray(new String[this.prop.size()]);
    }

    String getProperty(String string) {
        Object object = this.prop.get(string);
        if (!(object instanceof String)) {
            return null;
        }
        return object.toString();
    }

    int getProperty(String string, int n) {
        if (SysProperties.CHECK && !ConnectionInfo.isKnownSetting(string)) {
            throw DbException.getInternalError(string);
        }
        String string2 = this.getProperty(string);
        return string2 == null ? n : Integer.parseInt(string2);
    }

    public String getProperty(String string, String string2) {
        if (SysProperties.CHECK && !ConnectionInfo.isKnownSetting(string)) {
            throw DbException.getInternalError(string);
        }
        String string3 = this.getProperty(string);
        return string3 == null ? string2 : string3;
    }

    String getProperty(int n, String string) {
        String string2 = SetTypes.getTypeName(n);
        String string3 = this.getProperty(string2);
        return string3 == null ? string : string3;
    }

    int getIntProperty(int n, int n2) {
        String string = SetTypes.getTypeName(n);
        String string2 = this.getProperty(string, null);
        try {
            return string2 == null ? n2 : Integer.decode(string2);
        }
        catch (NumberFormatException numberFormatException) {
            return n2;
        }
    }

    boolean isSSL() {
        return this.ssl;
    }

    public void setUserName(String string) {
        this.user = StringUtils.toUpperEnglish(string);
    }

    public void setUserPasswordHash(byte[] byArray) {
        this.userPasswordHash = byArray;
    }

    public void setFilePasswordHash(byte[] byArray) {
        this.filePasswordHash = byArray;
    }

    public void setFileEncryptionKey(byte[] byArray) {
        this.fileEncryptionKey = byArray;
    }

    public void setProperty(String string, String string2) {
        if (string2 != null) {
            this.prop.setProperty(string, string2);
        }
    }

    public String getURL() {
        return this.url;
    }

    public String getOriginalURL() {
        return this.originalURL;
    }

    public void setOriginalURL(String string) {
        this.originalURL = string;
    }

    public TimeZoneProvider getTimeZone() {
        return this.timeZone;
    }

    DbException getFormatException() {
        return DbException.get(90046, "jdbc:h2:{ {.|mem:}[name] | [file:]fileName | {tcp|ssl}:[//]server[:port][,server2[:port]]/name }[;key=value...]", this.url);
    }

    public void setServerKey(String string) {
        this.remote = true;
        this.persistent = false;
        this.name = string;
    }

    public NetworkConnectionInfo getNetworkConnectionInfo() {
        return this.networkConnectionInfo;
    }

    public void setNetworkConnectionInfo(NetworkConnectionInfo networkConnectionInfo) {
        this.networkConnectionInfo = networkConnectionInfo;
    }

    public DbSettings getDbSettings() {
        DbSettings dbSettings = DbSettings.DEFAULT;
        HashMap<String, String> hashMap = new HashMap<String, String>(64);
        for (Object object : this.prop.keySet()) {
            String string = object.toString();
            if (ConnectionInfo.isKnownSetting(string) || !dbSettings.containsKey(string)) continue;
            hashMap.put(string, this.prop.getProperty(string));
        }
        return DbSettings.getInstance(hashMap);
    }

    private static String remapURL(String string) {
        String string2 = SysProperties.URL_MAP;
        if (string2 != null && !string2.isEmpty()) {
            try {
                SortedProperties sortedProperties = SortedProperties.loadProperties(string2);
                String string3 = sortedProperties.getProperty(string);
                if (string3 == null) {
                    sortedProperties.put(string, "");
                    sortedProperties.store(string2);
                } else if (!(string3 = string3.trim()).isEmpty()) {
                    return string3;
                }
            }
            catch (IOException iOException) {
                throw DbException.convert(iOException);
            }
        }
        return string;
    }

    public void cleanAuthenticationInfo() {
        this.removeProperty("AUTHREALM", false);
        this.removeProperty("AUTHZPWD", false);
    }

    static {
        String[] stringArray = new String[]{"ACCESS_MODE_DATA", "AUTO_RECONNECT", "AUTO_SERVER", "AUTO_SERVER_PORT", "CACHE_TYPE", "DB_CLOSE_ON_EXIT", "FILE_LOCK", "JMX", "NETWORK_TIMEOUT", "OLD_INFORMATION_SCHEMA", "OPEN_NEW", "PAGE_SIZE", "RECOVER"};
        String[] stringArray2 = new String[]{"AUTHREALM", "AUTHZPWD", "AUTOCOMMIT", "CIPHER", "CREATE", "FORBID_CREATION", "IGNORE_UNKNOWN_SETTINGS", "IFEXISTS", "INIT", "NO_UPGRADE", "PASSWORD", "PASSWORD_HASH", "RECOVER_TEST", "USER"};
        HashSet<String> hashSet = new HashSet<String>(128);
        hashSet.addAll(SetTypes.getTypes());
        for (String string : stringArray) {
            if (hashSet.add(string)) continue;
            throw DbException.getInternalError(string);
        }
        for (String string : stringArray2) {
            if (hashSet.add(string)) continue;
            throw DbException.getInternalError(string);
        }
        KNOWN_SETTINGS = hashSet;
        stringArray2 = new String[]{"ASSERT", "BINARY_COLLATION", "DB_CLOSE_ON_EXIT", "PAGE_STORE", "UUID_COLLATION"};
        hashSet = new HashSet(32);
        for (String string : stringArray) {
            hashSet.add(string);
        }
        for (String string : stringArray2) {
            hashSet.add(string);
        }
        IGNORED_BY_PARSER = hashSet;
    }
}

