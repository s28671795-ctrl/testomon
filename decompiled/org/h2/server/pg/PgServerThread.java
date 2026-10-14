/*
 * Decompiled with CFR 0.152.
 */
package org.h2.server.pg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.regex.Pattern;
import org.h2.command.Command;
import org.h2.command.CommandInterface;
import org.h2.engine.ConnectionInfo;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.Engine;
import org.h2.engine.SessionLocal;
import org.h2.engine.SysProperties;
import org.h2.expression.ParameterInterface;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;
import org.h2.schema.Schema;
import org.h2.server.pg.PgServer;
import org.h2.table.Column;
import org.h2.table.Table;
import org.h2.util.DateTimeUtils;
import org.h2.util.HasSQL;
import org.h2.util.MathUtils;
import org.h2.util.NetUtils;
import org.h2.util.NetworkConnectionInfo;
import org.h2.util.ScriptReader;
import org.h2.util.StringUtils;
import org.h2.util.TimeZoneProvider;
import org.h2.util.Utils;
import org.h2.util.Utils10;
import org.h2.value.CaseInsensitiveMap;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueArray;
import org.h2.value.ValueBigint;
import org.h2.value.ValueDate;
import org.h2.value.ValueDecfloat;
import org.h2.value.ValueDouble;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;
import org.h2.value.ValueNumeric;
import org.h2.value.ValueReal;
import org.h2.value.ValueSmallint;
import org.h2.value.ValueTime;
import org.h2.value.ValueTimeTimeZone;
import org.h2.value.ValueTimestamp;
import org.h2.value.ValueTimestampTimeZone;
import org.h2.value.ValueVarbinary;
import org.h2.value.ValueVarchar;

public final class PgServerThread
implements Runnable {
    private static final boolean INTEGER_DATE_TYPES = false;
    private static final Pattern SHOULD_QUOTE = Pattern.compile(".*[\",\\\\{}].*");
    private final PgServer server;
    private Socket socket;
    private SessionLocal session;
    private boolean stop;
    private DataInputStream dataInRaw;
    private DataInputStream dataIn;
    private OutputStream out;
    private int messageType;
    private ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
    private DataOutputStream dataOut;
    private Thread thread;
    private boolean initDone;
    private String userName;
    private String databaseName;
    private int processId;
    private final int secret;
    private CommandInterface activeRequest;
    private String clientEncoding = SysProperties.PG_DEFAULT_CLIENT_ENCODING;
    private String dateStyle = "ISO, MDY";
    private TimeZoneProvider timeZone = DateTimeUtils.getTimeZone();
    private final HashMap<String, Prepared> prepared = new CaseInsensitiveMap<Prepared>();
    private final HashMap<String, Portal> portals = new CaseInsensitiveMap<Portal>();
    private static final int[] POWERS10 = new int[]{1, 10, 100, 1000, 10000};
    private static final int MAX_GROUP_SCALE = 4;
    private static final int MAX_GROUP_SIZE = POWERS10[4];
    private static final short NUMERIC_POSITIVE = 0;
    private static final short NUMERIC_NEGATIVE = 16384;
    private static final short NUMERIC_NAN = -16384;
    private static final BigInteger NUMERIC_CHUNK_MULTIPLIER = BigInteger.valueOf(10000L);

    private static String pgTimeZone(String string) {
        if (string.startsWith("GMT+")) {
            return PgServerThread.convertTimeZone(string, "GMT-");
        }
        if (string.startsWith("GMT-")) {
            return PgServerThread.convertTimeZone(string, "GMT+");
        }
        if (string.startsWith("UTC+")) {
            return PgServerThread.convertTimeZone(string, "UTC-");
        }
        if (string.startsWith("UTC-")) {
            return PgServerThread.convertTimeZone(string, "UTC+");
        }
        return string;
    }

    private static String convertTimeZone(String string, String string2) {
        int n = string.length();
        return new StringBuilder(n).append(string2).append(string, 4, n).toString();
    }

    PgServerThread(Socket socket, PgServer pgServer) {
        this.server = pgServer;
        this.socket = socket;
        this.secret = (int)MathUtils.secureRandomLong();
    }

    @Override
    public void run() {
        try {
            this.server.trace("Connect");
            InputStream inputStream2 = this.socket.getInputStream();
            this.out = this.socket.getOutputStream();
            this.dataInRaw = new DataInputStream(inputStream2);
            while (!this.stop) {
                this.process();
                this.out.flush();
            }
        }
        catch (EOFException eOFException) {
        }
        catch (Exception exception) {
            this.server.traceError(exception);
        }
        finally {
            this.server.trace("Disconnect");
            this.close();
        }
    }

    private String readString() throws IOException {
        int n;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while ((n = this.dataIn.read()) > 0) {
            byteArrayOutputStream.write(n);
        }
        return Utils10.byteArrayOutputStreamToString(byteArrayOutputStream, this.getEncoding());
    }

    private int readInt() throws IOException {
        return this.dataIn.readInt();
    }

    private short readShort() throws IOException {
        return this.dataIn.readShort();
    }

    private byte readByte() throws IOException {
        return this.dataIn.readByte();
    }

    private void readFully(byte[] byArray) throws IOException {
        this.dataIn.readFully(byArray);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void process() throws IOException {
        int n;
        if (this.initDone) {
            n = this.dataInRaw.read();
            if (n < 0) {
                this.stop = true;
                return;
            }
        } else {
            n = 0;
        }
        int n2 = this.dataInRaw.readInt();
        byte[] byArray = Utils.newBytes(n2 -= 4);
        this.dataInRaw.readFully(byArray, 0, n2);
        this.dataIn = new DataInputStream(new ByteArrayInputStream(byArray, 0, n2));
        switch (n) {
            case 0: {
                String string;
                this.server.trace("Init");
                int n3 = this.readInt();
                if (n3 == 80877102) {
                    this.server.trace("CancelRequest");
                    int n4 = this.readInt();
                    int n5 = this.readInt();
                    PgServerThread pgServerThread = this.server.getThread(n4);
                    if (pgServerThread != null && n5 == pgServerThread.secret) {
                        pgServerThread.cancelRequest();
                    } else {
                        this.server.trace("Invalid CancelRequest: pid=" + n4 + ", key=" + n5);
                    }
                    this.close();
                    break;
                }
                if (n3 == 80877103) {
                    this.server.trace("SSLRequest");
                    this.out.write(78);
                    break;
                }
                this.server.trace("StartupMessage");
                this.server.trace(" version " + n3 + " (" + (n3 >> 16) + "." + (n3 & 0xFF) + ")");
                while (!(string = this.readString()).isEmpty()) {
                    String string2 = this.readString();
                    switch (string) {
                        case "user": {
                            this.userName = string2;
                            break;
                        }
                        case "database": {
                            this.databaseName = this.server.checkKeyAndGetDatabaseName(string2);
                            break;
                        }
                        case "client_encoding": {
                            int n6 = string2.length();
                            if (n6 >= 2 && string2.charAt(0) == '\'' && string2.charAt(n6 - 1) == '\'') {
                                string2 = string2.substring(1, n6 - 1);
                            }
                            this.clientEncoding = string2;
                            break;
                        }
                        case "DateStyle": {
                            if (string2.indexOf(44) < 0) {
                                string2 = string2 + ", MDY";
                            }
                            this.dateStyle = string2;
                            break;
                        }
                        case "TimeZone": {
                            try {
                                this.timeZone = TimeZoneProvider.ofId(PgServerThread.pgTimeZone(string2));
                                break;
                            }
                            catch (Exception exception) {
                                this.server.trace("Unknown TimeZone: " + string2);
                            }
                        }
                    }
                    this.server.trace(" param " + string + "=" + string2);
                }
                this.sendAuthenticationCleartextPassword();
                this.initDone = true;
                break;
            }
            case 112: {
                this.server.trace("PasswordMessage");
                String string = this.readString();
                try {
                    Properties properties = new Properties();
                    properties.put("MODE", "PostgreSQL");
                    properties.put("DATABASE_TO_LOWER", "TRUE");
                    properties.put("DEFAULT_NULL_ORDERING", "HIGH");
                    String string3 = "jdbc:h2:" + this.databaseName;
                    ConnectionInfo connectionInfo = new ConnectionInfo(string3, properties, this.userName, string);
                    String string4 = this.server.getBaseDir();
                    if (string4 == null) {
                        string4 = SysProperties.getBaseDir();
                    }
                    if (string4 != null) {
                        connectionInfo.setBaseDir(string4);
                    }
                    if (this.server.getIfExists()) {
                        connectionInfo.setProperty("FORBID_CREATION", "TRUE");
                    }
                    connectionInfo.setNetworkConnectionInfo(new NetworkConnectionInfo(NetUtils.ipToShortForm(new StringBuilder("pg://"), this.socket.getLocalAddress().getAddress(), true).append(':').append(this.socket.getLocalPort()).toString(), this.socket.getInetAddress().getAddress(), this.socket.getPort(), null));
                    this.session = Engine.createSession(connectionInfo);
                    this.initDb();
                    this.sendAuthenticationOk();
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    this.stop = true;
                }
                break;
            }
            case 80: {
                this.server.trace("Parse");
                Prepared prepared = new Prepared();
                prepared.name = this.readString();
                prepared.sql = this.getSQL(this.readString());
                int n7 = this.readShort();
                int[] nArray = null;
                if (n7 > 0) {
                    nArray = new int[n7];
                    for (int i = 0; i < n7; ++i) {
                        nArray[i] = this.readInt();
                    }
                }
                try {
                    prepared.prep = this.session.prepareLocal(prepared.sql);
                    ArrayList<? extends ParameterInterface> arrayList = prepared.prep.getParameters();
                    int n8 = arrayList.size();
                    prepared.paramType = new int[n8];
                    for (int i = 0; i < n8; ++i) {
                        int n9;
                        if (i < n7 && nArray[i] != 0) {
                            n9 = nArray[i];
                            this.server.checkType(n9);
                        } else {
                            n9 = PgServer.convertType(arrayList.get(i).getType());
                        }
                        prepared.paramType[i] = n9;
                    }
                    this.prepared.put(prepared.name, prepared);
                    this.sendParseComplete();
                }
                catch (Exception exception) {
                    this.sendErrorResponse(exception);
                }
                break;
            }
            case 66: {
                int n10;
                int n11;
                this.server.trace("Bind");
                Portal portal = new Portal();
                portal.name = this.readString();
                String string = this.readString();
                Prepared prepared = this.prepared.get(string);
                if (prepared == null) {
                    this.sendErrorResponse("Prepared not found");
                    break;
                }
                portal.prep = prepared;
                this.portals.put(portal.name, portal);
                int n12 = this.readShort();
                int[] nArray = new int[n12];
                for (n11 = 0; n11 < n12; ++n11) {
                    nArray[n11] = this.readShort();
                }
                n11 = this.readShort();
                try {
                    ArrayList<? extends ParameterInterface> arrayList = prepared.prep.getParameters();
                    for (n10 = 0; n10 < n11; ++n10) {
                        this.setParameter(arrayList, prepared.paramType[n10], n10, nArray);
                    }
                }
                catch (Exception exception) {
                    this.sendErrorResponse(exception);
                    break;
                }
                int n13 = this.readShort();
                portal.resultColumnFormat = new int[n13];
                for (n10 = 0; n10 < n13; ++n10) {
                    portal.resultColumnFormat[n10] = this.readShort();
                }
                this.sendBindComplete();
                break;
            }
            case 67: {
                char c = (char)this.readByte();
                String string = this.readString();
                this.server.trace("Close");
                if (c == 'S') {
                    Prepared prepared = this.prepared.remove(string);
                    if (prepared != null) {
                        prepared.close();
                    }
                } else if (c == 'P') {
                    Portal portal = this.portals.remove(string);
                    if (portal != null) {
                        portal.prep.closeResult();
                    }
                } else {
                    this.server.trace("expected S or P, got " + c);
                    this.sendErrorResponse("expected S or P");
                    break;
                }
                this.sendCloseComplete();
                break;
            }
            case 68: {
                char c = (char)this.readByte();
                String string = this.readString();
                this.server.trace("Describe");
                if (c == 'S') {
                    Prepared prepared = this.prepared.get(string);
                    if (prepared == null) {
                        this.sendErrorResponse("Prepared not found: " + string);
                        break;
                    }
                    try {
                        this.sendParameterDescription(prepared.prep.getParameters(), prepared.paramType);
                        this.sendRowDescription(prepared.prep.getMetaData(), null);
                    }
                    catch (Exception exception) {
                        this.sendErrorResponse(exception);
                    }
                    break;
                }
                if (c == 'P') {
                    Portal portal = this.portals.get(string);
                    if (portal == null) {
                        this.sendErrorResponse("Portal not found: " + string);
                        break;
                    }
                    CommandInterface commandInterface = portal.prep.prep;
                    try {
                        this.sendRowDescription(commandInterface.getMetaData(), portal.resultColumnFormat);
                    }
                    catch (Exception exception) {
                        this.sendErrorResponse(exception);
                    }
                    break;
                }
                this.server.trace("expected S or P, got " + c);
                this.sendErrorResponse("expected S or P");
                break;
            }
            case 69: {
                String string = this.readString();
                this.server.trace("Execute");
                Portal portal = this.portals.get(string);
                if (portal == null) {
                    this.sendErrorResponse("Portal not found: " + string);
                    break;
                }
                int n14 = this.readInt();
                Prepared prepared = portal.prep;
                CommandInterface commandInterface = prepared.prep;
                this.server.trace(prepared.sql);
                try {
                    this.setActiveRequest(commandInterface);
                    if (commandInterface.isQuery()) {
                        this.executeQuery(prepared, commandInterface, portal.resultColumnFormat, n14);
                        break;
                    }
                    this.sendCommandComplete(commandInterface, commandInterface.executeUpdate(null).getUpdateCount());
                    break;
                }
                catch (Exception exception) {
                    this.sendErrorOrCancelResponse(exception);
                    break;
                }
                finally {
                    this.setActiveRequest(null);
                }
            }
            case 83: {
                this.server.trace("Sync");
                this.sendReadyForQuery();
                break;
            }
            case 81: {
                String string;
                this.server.trace("Query");
                String string5 = this.readString();
                ScriptReader scriptReader = new ScriptReader(new StringReader(string5));
                while ((string = scriptReader.readStatement()) != null) {
                    string = this.getSQL(string);
                    try {
                        Command command = this.session.prepareLocal(string);
                        Throwable throwable = null;
                        try {
                            this.setActiveRequest(command);
                            if (command.isQuery()) {
                                ResultInterface resultInterface = command.executeQuery(0L, false);
                                Throwable throwable2 = null;
                                try {
                                    this.sendRowDescription(resultInterface, null);
                                    while (resultInterface.next()) {
                                        this.sendDataRow(resultInterface, null);
                                    }
                                    this.sendCommandComplete(command, 0L);
                                    continue;
                                }
                                catch (Throwable throwable3) {
                                    throwable2 = throwable3;
                                    throw throwable3;
                                }
                                finally {
                                    if (resultInterface == null) continue;
                                    if (throwable2 != null) {
                                        try {
                                            resultInterface.close();
                                        }
                                        catch (Throwable throwable4) {
                                            throwable2.addSuppressed(throwable4);
                                        }
                                        continue;
                                    }
                                    resultInterface.close();
                                    continue;
                                }
                            }
                            this.sendCommandComplete(command, command.executeUpdate(null).getUpdateCount());
                        }
                        catch (Throwable throwable5) {
                            throwable = throwable5;
                            throw throwable5;
                        }
                        finally {
                            if (command == null) continue;
                            if (throwable != null) {
                                try {
                                    command.close();
                                }
                                catch (Throwable throwable6) {
                                    throwable.addSuppressed(throwable6);
                                }
                                continue;
                            }
                            command.close();
                        }
                    }
                    catch (Exception exception) {
                        this.sendErrorOrCancelResponse(exception);
                        break;
                    }
                    finally {
                        this.setActiveRequest(null);
                    }
                }
                this.sendReadyForQuery();
                break;
            }
            case 88: {
                this.server.trace("Terminate");
                this.close();
                break;
            }
            default: {
                this.server.trace("Unsupported: " + n + " (" + (char)n + ")");
            }
        }
    }

    private void executeQuery(Prepared prepared, CommandInterface commandInterface, int[] nArray, int n) throws Exception {
        ResultInterface resultInterface = prepared.result;
        if (resultInterface == null) {
            resultInterface = commandInterface.executeQuery(0L, false);
        }
        try {
            if (n == 0) {
                while (resultInterface.next()) {
                    this.sendDataRow(resultInterface, nArray);
                }
            } else {
                while (n > 0 && resultInterface.next()) {
                    this.sendDataRow(resultInterface, nArray);
                    --n;
                }
                if (resultInterface.hasNext()) {
                    prepared.result = resultInterface;
                    this.sendCommandSuspended();
                    return;
                }
            }
            prepared.closeResult();
            this.sendCommandComplete(commandInterface, 0L);
        }
        catch (Exception exception) {
            prepared.closeResult();
            throw exception;
        }
    }

    private String getSQL(String string) {
        String string2 = StringUtils.toLowerEnglish(string);
        if (string2.startsWith("show max_identifier_length")) {
            string = "CALL 63";
        } else if (string2.startsWith("set client_encoding to")) {
            string = "set DATESTYLE ISO";
        }
        if (this.server.getTrace()) {
            this.server.trace(string + ";");
        }
        return string;
    }

    private void sendCommandComplete(CommandInterface commandInterface, long l) throws IOException {
        this.startMessage(67);
        switch (commandInterface.getCommandType()) {
            case 61: {
                this.writeStringPart("INSERT 0 ");
                this.writeString(Long.toString(l));
                break;
            }
            case 68: {
                this.writeStringPart("UPDATE ");
                this.writeString(Long.toString(l));
                break;
            }
            case 58: {
                this.writeStringPart("DELETE ");
                this.writeString(Long.toString(l));
                break;
            }
            case 57: 
            case 66: {
                this.writeString("SELECT");
                break;
            }
            case 83: {
                this.writeString("BEGIN");
                break;
            }
            default: {
                this.server.trace("check CommandComplete tag for command " + commandInterface);
                this.writeStringPart("UPDATE ");
                this.writeString(Long.toString(l));
            }
        }
        this.sendMessage();
    }

    private void sendCommandSuspended() throws IOException {
        this.startMessage(115);
        this.sendMessage();
    }

    private void sendDataRow(ResultInterface resultInterface, int[] nArray) throws IOException {
        int n = resultInterface.getVisibleColumnCount();
        this.startMessage(68);
        this.writeShort(n);
        Value[] valueArray = resultInterface.currentRow();
        for (int i = 0; i < n; ++i) {
            int n2 = PgServer.convertType(resultInterface.getColumnType(i));
            boolean bl = PgServerThread.formatAsText(n2, nArray, i);
            this.writeDataColumn(valueArray[i], n2, bl);
        }
        this.sendMessage();
    }

    private static long toPostgreDays(long l) {
        return DateTimeUtils.absoluteDayFromDateValue(l) - 10957L;
    }

    private void writeDataColumn(Value value, int n, boolean bl) throws IOException {
        if (value == ValueNull.INSTANCE) {
            this.writeInt(-1);
            return;
        }
        if (bl) {
            switch (n) {
                case 16: {
                    this.writeInt(1);
                    this.dataOut.writeByte(value.getBoolean() ? 116 : 102);
                    break;
                }
                case 17: {
                    int n2;
                    int n3;
                    byte[] byArray = value.getBytesNoCopy();
                    int n4 = n3 = byArray.length;
                    for (int i = 0; i < n3; ++i) {
                        n2 = byArray[i];
                        if (n2 < 32 || n2 > 126) {
                            n4 += 3;
                            continue;
                        }
                        if (n2 != 92) continue;
                        ++n4;
                    }
                    byte[] byArray2 = new byte[n4];
                    int n5 = 0;
                    for (n2 = 0; n2 < n3; ++n2) {
                        byte by = byArray[n2];
                        if (by < 32 || by > 126) {
                            byArray2[n5++] = 92;
                            byArray2[n5++] = (byte)((by >>> 6 & 3) + 48);
                            byArray2[n5++] = (byte)((by >>> 3 & 7) + 48);
                            byArray2[n5++] = (byte)((by & 7) + 48);
                            continue;
                        }
                        if (by == 92) {
                            byArray2[n5++] = 92;
                            byArray2[n5++] = 92;
                            continue;
                        }
                        byArray2[n5++] = by;
                    }
                    this.writeInt(byArray2.length);
                    this.write(byArray2);
                    break;
                }
                case 1005: 
                case 1007: 
                case 1015: {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byteArrayOutputStream.write(123);
                    Value[] valueArray = ((ValueArray)value).getList();
                    Charset charset = this.getEncoding();
                    for (int i = 0; i < valueArray.length; ++i) {
                        String string;
                        if (i > 0) {
                            byteArrayOutputStream.write(44);
                        }
                        if (SHOULD_QUOTE.matcher(string = valueArray[i].getString()).matches()) {
                            ArrayList<String> arrayList = new ArrayList<String>();
                            for (String string2 : string.split("\\\\")) {
                                arrayList.add(string2.replace("\"", "\\\""));
                            }
                            string = "\"" + String.join((CharSequence)"\\\\", arrayList) + "\"";
                        }
                        byteArrayOutputStream.write(string.getBytes(charset));
                    }
                    byteArrayOutputStream.write(125);
                    this.writeInt(byteArrayOutputStream.size());
                    this.write(byteArrayOutputStream);
                    break;
                }
                default: {
                    byte[] byArray = value.getString().getBytes(this.getEncoding());
                    this.writeInt(byArray.length);
                    this.write(byArray);
                    break;
                }
            }
        } else {
            switch (n) {
                case 16: {
                    this.writeInt(1);
                    this.dataOut.writeByte(value.getBoolean() ? 1 : 0);
                    break;
                }
                case 21: {
                    this.writeInt(2);
                    this.writeShort(value.getShort());
                    break;
                }
                case 23: {
                    this.writeInt(4);
                    this.writeInt(value.getInt());
                    break;
                }
                case 20: {
                    this.writeInt(8);
                    this.dataOut.writeLong(value.getLong());
                    break;
                }
                case 700: {
                    this.writeInt(4);
                    this.dataOut.writeFloat(value.getFloat());
                    break;
                }
                case 701: {
                    this.writeInt(8);
                    this.dataOut.writeDouble(value.getDouble());
                    break;
                }
                case 1700: {
                    this.writeNumericBinary(value.getBigDecimal());
                    break;
                }
                case 17: {
                    byte[] byArray = value.getBytesNoCopy();
                    this.writeInt(byArray.length);
                    this.write(byArray);
                    break;
                }
                case 1082: {
                    this.writeInt(4);
                    this.writeInt((int)PgServerThread.toPostgreDays(((ValueDate)value).getDateValue()));
                    break;
                }
                case 1083: {
                    this.writeTimeBinary(((ValueTime)value).getNanos(), 8);
                    break;
                }
                case 1266: {
                    ValueTimeTimeZone valueTimeTimeZone = (ValueTimeTimeZone)value;
                    long l = valueTimeTimeZone.getNanos();
                    this.writeTimeBinary(l, 12);
                    this.dataOut.writeInt(-valueTimeTimeZone.getTimeZoneOffsetSeconds());
                    break;
                }
                case 1114: {
                    ValueTimestamp valueTimestamp = (ValueTimestamp)value;
                    long l = PgServerThread.toPostgreDays(valueTimestamp.getDateValue()) * 86400L;
                    long l2 = valueTimestamp.getTimeNanos();
                    this.writeTimestampBinary(l, l2);
                    break;
                }
                case 1184: {
                    ValueTimestampTimeZone valueTimestampTimeZone = (ValueTimestampTimeZone)value;
                    long l = PgServerThread.toPostgreDays(valueTimestampTimeZone.getDateValue()) * 86400L;
                    long l3 = valueTimestampTimeZone.getTimeNanos() - (long)valueTimestampTimeZone.getTimeZoneOffsetSeconds() * 1000000000L;
                    if (l3 < 0L) {
                        --l;
                        l3 += 86400000000000L;
                    }
                    this.writeTimestampBinary(l, l3);
                    break;
                }
                default: {
                    throw new IllegalStateException("output binary format is undefined");
                }
            }
        }
    }

    private static int divide(BigInteger[] bigIntegerArray, int n) {
        BigInteger[] bigIntegerArray2 = bigIntegerArray[0].divideAndRemainder(BigInteger.valueOf(n));
        bigIntegerArray[0] = bigIntegerArray2[0];
        return bigIntegerArray2[1].intValue();
    }

    private void writeNumericBinary(BigDecimal bigDecimal) throws IOException {
        int n;
        int n2;
        int n3 = 0;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int n4 = bigDecimal.scale();
        int n5 = bigDecimal.signum();
        if (n5 != 0) {
            BigInteger[] bigIntegerArray = new BigInteger[]{null};
            if (n4 < 0) {
                bigIntegerArray[0] = bigDecimal.setScale(0).unscaledValue();
                n4 = 0;
            } else {
                bigIntegerArray[0] = bigDecimal.unscaledValue();
            }
            if (n5 < 0) {
                bigIntegerArray[0] = bigIntegerArray[0].negate();
            }
            n3 = -n4 / 4 - 1;
            n2 = 0;
            int n6 = n4 % 4;
            if (n6 > 0 && (n2 = PgServerThread.divide(bigIntegerArray, POWERS10[n6]) * POWERS10[4 - n6]) != 0) {
                --n3;
            }
            if (n2 == 0) {
                while ((n2 = PgServerThread.divide(bigIntegerArray, MAX_GROUP_SIZE)) == 0) {
                    ++n3;
                }
            }
            arrayList.add(n2);
            while (bigIntegerArray[0].signum() != 0) {
                arrayList.add(PgServerThread.divide(bigIntegerArray, MAX_GROUP_SIZE));
            }
        }
        if ((n = arrayList.size()) + n3 > Short.MAX_VALUE || n4 > Short.MAX_VALUE) {
            throw DbException.get(22003, bigDecimal.toString());
        }
        this.writeInt(8 + n * 2);
        this.writeShort(n);
        this.writeShort(n + n3);
        this.writeShort(n5 < 0 ? 16384 : 0);
        this.writeShort(n4);
        for (n2 = n - 1; n2 >= 0; --n2) {
            this.writeShort((Integer)arrayList.get(n2));
        }
    }

    private void writeTimeBinary(long l, int n) throws IOException {
        this.writeInt(n);
        l = Double.doubleToLongBits((double)l * 1.0E-9);
        this.dataOut.writeLong(l);
    }

    private void writeTimestampBinary(long l, long l2) throws IOException {
        this.writeInt(8);
        l = Double.doubleToLongBits((double)l + (double)l2 * 1.0E-9);
        this.dataOut.writeLong(l);
    }

    private Charset getEncoding() {
        if ("UNICODE".equals(this.clientEncoding)) {
            return StandardCharsets.UTF_8;
        }
        return Charset.forName(this.clientEncoding);
    }

    private void setParameter(ArrayList<? extends ParameterInterface> arrayList, int n, int n2, int[] nArray) throws IOException {
        Value value;
        boolean bl = true;
        if (nArray.length == 1) {
            bl = nArray[0] == 0;
        } else if (n2 < nArray.length) {
            bl = nArray[n2] == 0;
        }
        int n3 = this.readInt();
        if (n3 == -1) {
            value = ValueNull.INSTANCE;
        } else if (bl) {
            byte[] byArray = Utils.newBytes(n3);
            this.readFully(byArray);
            String string = new String(byArray, this.getEncoding());
            switch (n) {
                case 1082: {
                    int n4 = string.indexOf(32);
                    if (n4 <= 0) break;
                    string = string.substring(0, n4);
                    break;
                }
                case 1083: {
                    int n5 = string.indexOf(43);
                    if (n5 <= 0) {
                        n5 = string.indexOf(45);
                    }
                    if (n5 <= 0) break;
                    string = string.substring(0, n5);
                    break;
                }
            }
            value = ValueVarchar.get(string, this.session);
        } else {
            switch (n) {
                case 21: {
                    PgServerThread.checkParamLength(2, n3);
                    value = ValueSmallint.get(this.readShort());
                    break;
                }
                case 23: {
                    PgServerThread.checkParamLength(4, n3);
                    value = ValueInteger.get(this.readInt());
                    break;
                }
                case 20: {
                    PgServerThread.checkParamLength(8, n3);
                    value = ValueBigint.get(this.dataIn.readLong());
                    break;
                }
                case 700: {
                    PgServerThread.checkParamLength(4, n3);
                    value = ValueReal.get(this.dataIn.readFloat());
                    break;
                }
                case 701: {
                    PgServerThread.checkParamLength(8, n3);
                    value = ValueDouble.get(this.dataIn.readDouble());
                    break;
                }
                case 17: {
                    byte[] byArray = Utils.newBytes(n3);
                    this.readFully(byArray);
                    value = ValueVarbinary.getNoCopy(byArray);
                    break;
                }
                case 1700: {
                    value = this.readNumericBinary(n3);
                    break;
                }
                default: {
                    this.server.trace("Binary format for type: " + n + " is unsupported");
                    byte[] byArray = Utils.newBytes(n3);
                    this.readFully(byArray);
                    value = ValueVarchar.get(new String(byArray, this.getEncoding()), this.session);
                }
            }
        }
        arrayList.get(n2).setValue(value, true);
    }

    private static void checkParamLength(int n, int n2) {
        if (n != n2) {
            throw DbException.getInvalidValueException("paramLen", n2);
        }
    }

    private Value readNumericBinary(int n) throws IOException {
        if (n < 8) {
            throw DbException.getInvalidValueException("numeric binary length", n);
        }
        int n2 = this.readShort();
        short s2 = this.readShort();
        short s3 = this.readShort();
        short s4 = this.readShort();
        if (n2 * 2 + 8 != n) {
            throw DbException.getInvalidValueException("numeric binary length", n);
        }
        if (s3 == -16384) {
            return ValueDecfloat.NAN;
        }
        if (s3 != 0 && s3 != 16384) {
            throw DbException.getInvalidValueException("numeric sign", s3);
        }
        if ((s4 & 0x3FFF) != s4) {
            throw DbException.getInvalidValueException("numeric scale", s4);
        }
        if (n2 == 0) {
            return s4 == 0 ? ValueNumeric.ZERO : ValueNumeric.get(new BigDecimal(BigInteger.ZERO, s4));
        }
        BigInteger bigInteger = BigInteger.ZERO;
        for (int i = 0; i < n2; ++i) {
            short s5 = this.readShort();
            if (s5 < 0 || s5 > 9999) {
                throw DbException.getInvalidValueException("numeric chunk", s5);
            }
            bigInteger = bigInteger.multiply(NUMERIC_CHUNK_MULTIPLIER).add(BigInteger.valueOf(s5));
        }
        if (s3 != 0) {
            bigInteger = bigInteger.negate();
        }
        return ValueNumeric.get(new BigDecimal(bigInteger, (n2 - s2 - 1) * 4).setScale(s4));
    }

    private void sendErrorOrCancelResponse(Exception exception) throws IOException {
        if (exception instanceof DbException && ((DbException)exception).getErrorCode() == 57014) {
            this.sendCancelQueryResponse();
        } else {
            this.sendErrorResponse(exception);
        }
    }

    private void sendErrorResponse(Exception exception) throws IOException {
        SQLException sQLException = DbException.toSQLException(exception);
        this.server.traceError(sQLException);
        this.startMessage(69);
        this.write(83);
        this.writeString("ERROR");
        this.write(67);
        this.writeString(sQLException.getSQLState());
        this.write(77);
        this.writeString(sQLException.getMessage());
        this.write(68);
        this.writeString(sQLException.toString());
        this.write(0);
        this.sendMessage();
    }

    private void sendCancelQueryResponse() throws IOException {
        this.server.trace("CancelSuccessResponse");
        this.startMessage(69);
        this.write(83);
        this.writeString("ERROR");
        this.write(67);
        this.writeString("57014");
        this.write(77);
        this.writeString("canceling statement due to user request");
        this.write(0);
        this.sendMessage();
    }

    private void sendParameterDescription(ArrayList<? extends ParameterInterface> arrayList, int[] nArray) throws Exception {
        int n = arrayList.size();
        this.startMessage(116);
        this.writeShort(n);
        for (int i = 0; i < n; ++i) {
            int n2 = nArray != null && nArray[i] != 0 ? nArray[i] : 1043;
            this.server.checkType(n2);
            this.writeInt(n2);
        }
        this.sendMessage();
    }

    private void sendNoData() throws IOException {
        this.startMessage(110);
        this.sendMessage();
    }

    private void sendRowDescription(ResultInterface resultInterface, int[] nArray) throws IOException {
        if (resultInterface == null) {
            this.sendNoData();
        } else {
            int n;
            int n2 = resultInterface.getVisibleColumnCount();
            int[] nArray2 = new int[n2];
            int[] nArray3 = new int[n2];
            int[] nArray4 = new int[n2];
            int[] nArray5 = new int[n2];
            String[] stringArray = new String[n2];
            Database database = this.session.getDatabase();
            for (n = 0; n < n2; ++n) {
                HasSQL hasSQL;
                String string = resultInterface.getColumnName(n);
                Schema schema = database.findSchema(resultInterface.getSchemaName(n));
                if (schema != null && (hasSQL = schema.findTableOrView(this.session, resultInterface.getTableName(n))) != null) {
                    nArray2[n] = ((DbObject)hasSQL).getId();
                    Column column = ((Table)hasSQL).findColumn(string);
                    if (column != null) {
                        nArray3[n] = column.getColumnId() + 1;
                    }
                }
                stringArray[n] = string;
                hasSQL = resultInterface.getColumnType(n);
                int n3 = PgServer.convertType((TypeInfo)hasSQL);
                nArray5[n] = ((TypeInfo)hasSQL).getDisplaySize();
                if (((TypeInfo)hasSQL).getValueType() != 0) {
                    this.server.checkType(n3);
                }
                nArray4[n] = n3;
            }
            this.startMessage(84);
            this.writeShort(n2);
            for (n = 0; n < n2; ++n) {
                this.writeString(StringUtils.toLowerEnglish(stringArray[n]));
                this.writeInt(nArray2[n]);
                this.writeShort(nArray3[n]);
                this.writeInt(nArray4[n]);
                this.writeShort(PgServerThread.getTypeSize(nArray4[n], nArray5[n]));
                this.writeInt(-1);
                this.writeShort(PgServerThread.formatAsText(nArray4[n], nArray, n) ? 0 : 1);
            }
            this.sendMessage();
        }
    }

    private static boolean formatAsText(int n, int[] nArray, int n2) {
        boolean bl = true;
        if (nArray != null && nArray.length > 0) {
            if (nArray.length == 1) {
                bl = nArray[0] == 0;
            } else if (n2 < nArray.length) {
                bl = nArray[n2] == 0;
            }
        }
        return bl;
    }

    private static int getTypeSize(int n, int n2) {
        switch (n) {
            case 16: {
                return 1;
            }
            case 1043: {
                return Math.max(255, n2 + 10);
            }
        }
        return n2 + 4;
    }

    private void sendErrorResponse(String string) throws IOException {
        this.server.trace("Exception: " + string);
        this.startMessage(69);
        this.write(83);
        this.writeString("ERROR");
        this.write(67);
        this.writeString("08P01");
        this.write(77);
        this.writeString(string);
        this.sendMessage();
    }

    private void sendParseComplete() throws IOException {
        this.startMessage(49);
        this.sendMessage();
    }

    private void sendBindComplete() throws IOException {
        this.startMessage(50);
        this.sendMessage();
    }

    private void sendCloseComplete() throws IOException {
        this.startMessage(51);
        this.sendMessage();
    }

    private void initDb() {
        this.session.setTimeZone(this.timeZone);
        Object object = this.session.prepareLocal("set search_path = public, pg_catalog");
        Object object2 = null;
        try {
            object.executeUpdate(null);
        }
        catch (Throwable throwable) {
            object2 = throwable;
            throw throwable;
        }
        finally {
            if (object != null) {
                if (object2 != null) {
                    try {
                        object.close();
                    }
                    catch (Throwable throwable) {
                        ((Throwable)object2).addSuppressed(throwable);
                    }
                } else {
                    object.close();
                }
            }
        }
        object = this.server.getTypeSet();
        if (((HashSet)object).isEmpty()) {
            object2 = this.session.prepareLocal("select oid from pg_catalog.pg_type");
            Throwable throwable = null;
            try (ResultInterface resultInterface = object2.executeQuery(0L, false);){
                while (resultInterface.next()) {
                    ((HashSet)object).add(resultInterface.currentRow()[0].getInt());
                }
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                if (object2 != null) {
                    if (throwable != null) {
                        try {
                            object2.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    } else {
                        object2.close();
                    }
                }
            }
        }
    }

    void close() {
        for (Prepared prepared : this.prepared.values()) {
            prepared.close();
        }
        try {
            this.stop = true;
            try {
                this.session.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (this.socket != null) {
                this.socket.close();
            }
            this.server.trace("Close");
        }
        catch (Exception exception) {
            this.server.traceError(exception);
        }
        this.session = null;
        this.socket = null;
        this.server.remove(this);
    }

    private void sendAuthenticationCleartextPassword() throws IOException {
        this.startMessage(82);
        this.writeInt(3);
        this.sendMessage();
    }

    private void sendAuthenticationOk() throws IOException {
        this.startMessage(82);
        this.writeInt(0);
        this.sendMessage();
        this.sendParameterStatus("client_encoding", this.clientEncoding);
        this.sendParameterStatus("DateStyle", this.dateStyle);
        this.sendParameterStatus("is_superuser", "off");
        this.sendParameterStatus("server_encoding", "SQL_ASCII");
        this.sendParameterStatus("server_version", "8.2.23");
        this.sendParameterStatus("session_authorization", this.userName);
        this.sendParameterStatus("standard_conforming_strings", "off");
        this.sendParameterStatus("TimeZone", PgServerThread.pgTimeZone(this.timeZone.getId()));
        String string = "off";
        this.sendParameterStatus("integer_datetimes", string);
        this.sendBackendKeyData();
        this.sendReadyForQuery();
    }

    private void sendReadyForQuery() throws IOException {
        this.startMessage(90);
        this.write((byte)(this.session.getAutoCommit() ? 73 : 84));
        this.sendMessage();
    }

    private void sendBackendKeyData() throws IOException {
        this.startMessage(75);
        this.writeInt(this.processId);
        this.writeInt(this.secret);
        this.sendMessage();
    }

    private void writeString(String string) throws IOException {
        this.writeStringPart(string);
        this.write(0);
    }

    private void writeStringPart(String string) throws IOException {
        this.write(string.getBytes(this.getEncoding()));
    }

    private void writeInt(int n) throws IOException {
        this.dataOut.writeInt(n);
    }

    private void writeShort(int n) throws IOException {
        this.dataOut.writeShort(n);
    }

    private void write(byte[] byArray) throws IOException {
        this.dataOut.write(byArray);
    }

    private void write(ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        byteArrayOutputStream.writeTo(this.dataOut);
    }

    private void write(int n) throws IOException {
        this.dataOut.write(n);
    }

    private void startMessage(int n) {
        this.messageType = n;
        if (this.outBuffer.size() <= 65536) {
            this.outBuffer.reset();
        } else {
            this.outBuffer = new ByteArrayOutputStream();
        }
        this.dataOut = new DataOutputStream(this.outBuffer);
    }

    private void sendMessage() throws IOException {
        this.dataOut.flush();
        this.dataOut = new DataOutputStream(this.out);
        this.write(this.messageType);
        this.writeInt(this.outBuffer.size() + 4);
        this.write(this.outBuffer);
        this.dataOut.flush();
    }

    private void sendParameterStatus(String string, String string2) throws IOException {
        this.startMessage(83);
        this.writeString(string);
        this.writeString(string2);
        this.sendMessage();
    }

    void setThread(Thread thread2) {
        this.thread = thread2;
    }

    Thread getThread() {
        return this.thread;
    }

    void setProcessId(int n) {
        this.processId = n;
    }

    int getProcessId() {
        return this.processId;
    }

    private synchronized void setActiveRequest(CommandInterface commandInterface) {
        this.activeRequest = commandInterface;
    }

    private synchronized void cancelRequest() {
        if (this.activeRequest != null) {
            this.activeRequest.cancel();
            this.activeRequest = null;
        }
    }

    static class Portal {
        String name;
        int[] resultColumnFormat;
        Prepared prep;

        Portal() {
        }
    }

    static class Prepared {
        String name;
        String sql;
        CommandInterface prep;
        ResultInterface result;
        int[] paramType;

        Prepared() {
        }

        void close() {
            try {
                this.closeResult();
                this.prep.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }

        void closeResult() {
            ResultInterface resultInterface = this.result;
            if (resultInterface != null) {
                this.result = null;
                resultInterface.close();
            }
        }
    }
}

