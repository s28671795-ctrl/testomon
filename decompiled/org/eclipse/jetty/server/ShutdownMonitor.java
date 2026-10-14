/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.component.Destroyable;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.util.thread.ShutdownThread;

public class ShutdownMonitor {
    private final AutoLock.WithCondition _lock = new AutoLock.WithCondition();
    private final Set<LifeCycle> _lifeCycles = new LinkedHashSet<LifeCycle>();
    private boolean debug = System.getProperty("DEBUG") != null;
    private final String host = System.getProperty("STOP.HOST", "127.0.0.1");
    private int port = Integer.getInteger("STOP.PORT", -1);
    private String key = System.getProperty("STOP.KEY", null);
    private boolean exitVm = Boolean.valueOf(System.getProperty("STOP.EXIT", "true"));
    private boolean alive;

    public static ShutdownMonitor getInstance() {
        return Holder.instance;
    }

    protected static void reset() {
        Holder.instance = new ShutdownMonitor();
    }

    public static void register(LifeCycle ... lifeCycles) {
        ShutdownMonitor.getInstance().addLifeCycles(lifeCycles);
    }

    public static void deregister(LifeCycle lifeCycle) {
        ShutdownMonitor.getInstance().removeLifeCycle(lifeCycle);
    }

    public static boolean isRegistered(LifeCycle lifeCycle) {
        return ShutdownMonitor.getInstance().containsLifeCycle(lifeCycle);
    }

    private ShutdownMonitor() {
    }

    private void addLifeCycles(LifeCycle ... lifeCycles) {
        try (AutoLock.WithCondition l = this._lock.lock();){
            this._lifeCycles.addAll(Arrays.asList(lifeCycles));
        }
    }

    private void removeLifeCycle(LifeCycle lifeCycle) {
        try (AutoLock.WithCondition l = this._lock.lock();){
            this._lifeCycles.remove(lifeCycle);
        }
    }

    private boolean containsLifeCycle(LifeCycle lifeCycle) {
        try (AutoLock.WithCondition l = this._lock.lock();){
            boolean bl = this._lifeCycles.contains(lifeCycle);
            return bl;
        }
    }

    private void debug(String format, Object ... args2) {
        if (this.debug) {
            System.err.printf("[ShutdownMonitor] " + format + "%n", args2);
        }
    }

    private void debug(Throwable t2) {
        if (this.debug) {
            t2.printStackTrace(System.err);
        }
    }

    public String getKey() {
        try (AutoLock.WithCondition l = this._lock.lock();){
            String string = this.key;
            return string;
        }
    }

    public int getPort() {
        try (AutoLock.WithCondition l = this._lock.lock();){
            int n = this.port;
            return n;
        }
    }

    public boolean isExitVm() {
        try (AutoLock.WithCondition l = this._lock.lock();){
            boolean bl = this.exitVm;
            return bl;
        }
    }

    public void setDebug(boolean flag) {
        this.debug = flag;
    }

    public void setExitVm(boolean exitVm) {
        try (AutoLock.WithCondition l = this._lock.lock();){
            if (this.alive) {
                throw new IllegalStateException("ShutdownMonitor already started");
            }
            this.exitVm = exitVm;
        }
    }

    public void setKey(String key) {
        try (AutoLock.WithCondition l = this._lock.lock();){
            if (this.alive) {
                throw new IllegalStateException("ShutdownMonitor already started");
            }
            this.key = key;
        }
    }

    public void setPort(int port) {
        try (AutoLock.WithCondition l = this._lock.lock();){
            if (this.alive) {
                throw new IllegalStateException("ShutdownMonitor already started");
            }
            this.port = port;
        }
    }

    public void start() throws Exception {
        try (AutoLock.WithCondition l = this._lock.lock();){
            if (this.alive) {
                this.debug("Already started", new Object[0]);
                return;
            }
            ServerSocket serverSocket = this.listen();
            if (serverSocket != null) {
                this.alive = true;
                Thread thread2 = new Thread(new ShutdownMonitorRunnable(serverSocket));
                thread2.setDaemon(true);
                thread2.setName("ShutdownMonitor");
                thread2.start();
            }
        }
    }

    private void stop() {
        try (AutoLock.WithCondition l = this._lock.lock();){
            this.alive = false;
            l.signalAll();
        }
    }

    public void await() throws InterruptedException {
        try (AutoLock.WithCondition l = this._lock.lock();){
            while (this.alive) {
                l.await();
            }
        }
    }

    protected boolean isAlive() {
        try (AutoLock.WithCondition l = this._lock.lock();){
            boolean bl = this.alive;
            return bl;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ServerSocket listen() {
        ServerSocket serverSocket;
        int port = this.getPort();
        if (port < 0) {
            this.debug("Not enabled (port < 0): %d", port);
            return null;
        }
        String key = this.getKey();
        try {
            ServerSocket serverSocket2 = new ServerSocket();
            try {
                serverSocket2.setReuseAddress(true);
                serverSocket2.bind(new InetSocketAddress(InetAddress.getByName(this.host), port));
            }
            catch (Throwable e) {
                IO.close(serverSocket2);
                throw e;
            }
            if (port == 0) {
                port = serverSocket2.getLocalPort();
                System.out.printf("STOP.PORT=%d%n", port);
                this.setPort(port);
            }
            if (key == null) {
                key = Long.toString((long)(9.223372036854776E18 * Math.random() + (double)this.hashCode() + (double)System.currentTimeMillis()), 36);
                System.out.printf("STOP.KEY=%s%n", key);
                this.setKey(key);
            }
            serverSocket = serverSocket2;
        }
        catch (Throwable x) {
            ServerSocket serverSocket3;
            try {
                this.debug(x);
                System.err.println("Error binding ShutdownMonitor to port " + port + ": " + x.toString());
                serverSocket3 = null;
            }
            catch (Throwable throwable) {
                this.debug("STOP.PORT=%d", port);
                this.debug("STOP.KEY=%s", key);
                this.debug("STOP.EXIT=%b", this.exitVm);
                throw throwable;
            }
            this.debug("STOP.PORT=%d", port);
            this.debug("STOP.KEY=%s", key);
            this.debug("STOP.EXIT=%b", this.exitVm);
            return serverSocket3;
        }
        this.debug("STOP.PORT=%d", port);
        this.debug("STOP.KEY=%s", key);
        this.debug("STOP.EXIT=%b", this.exitVm);
        return serverSocket;
    }

    public String toString() {
        return String.format("%s[port=%d,alive=%b]", this.getClass().getName(), this.getPort(), this.isAlive());
    }

    private static class Holder {
        static ShutdownMonitor instance = new ShutdownMonitor();

        private Holder() {
        }
    }

    private class ShutdownMonitorRunnable
    implements Runnable {
        private final ServerSocket serverSocket;

        private ShutdownMonitorRunnable(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            String key;
            ShutdownMonitor.this.debug("Started", new Object[0]);
            try {
                key = ShutdownMonitor.this.getKey();
                while (true) {
                    try {}
                    catch (Throwable x) {
                        ShutdownMonitor.this.debug(x);
                        continue;
                    }
                    break;
                }
            }
            catch (Throwable x) {
                ShutdownMonitor.this.debug(x);
                return;
            }
            finally {
                IO.close(this.serverSocket);
                ShutdownMonitor.this.stop();
                ShutdownMonitor.this.debug("Stopped", new Object[0]);
            }
            while (true) {
                Socket socket = this.serverSocket.accept();
                try {
                    LineNumberReader reader = new LineNumberReader(new InputStreamReader(socket.getInputStream()));
                    String receivedKey = reader.readLine();
                    if (!key.equals(receivedKey)) {
                        ShutdownMonitor.this.debug("Ignoring command with incorrect key: %s", receivedKey);
                        continue;
                    }
                    String cmd = reader.readLine();
                    ShutdownMonitor.this.debug("command=%s", cmd);
                    OutputStream out = socket.getOutputStream();
                    boolean exitVm = ShutdownMonitor.this.isExitVm();
                    if ("stop".equalsIgnoreCase(cmd)) {
                        ShutdownMonitor.this.debug("Performing stop command", new Object[0]);
                        this.stopLifeCycles(ShutdownThread::isRegistered, exitVm);
                        ShutdownMonitor.this.debug("Informing client that we are stopped", new Object[0]);
                        this.informClient(out, "Stopped\r\n");
                        if (!exitVm) {
                            return;
                        }
                        ShutdownMonitor.this.debug("Killing JVM", new Object[0]);
                        System.exit(0);
                        continue;
                    }
                    if ("forcestop".equalsIgnoreCase(cmd)) {
                        ShutdownMonitor.this.debug("Performing forced stop command", new Object[0]);
                        this.stopLifeCycles(l -> true, exitVm);
                        ShutdownMonitor.this.debug("Informing client that we are stopped", new Object[0]);
                        this.informClient(out, "Stopped\r\n");
                        if (!exitVm) {
                            return;
                        }
                        ShutdownMonitor.this.debug("Killing JVM", new Object[0]);
                        System.exit(0);
                        continue;
                    }
                    if ("stopexit".equalsIgnoreCase(cmd)) {
                        ShutdownMonitor.this.debug("Performing stop and exit commands", new Object[0]);
                        this.stopLifeCycles(ShutdownThread::isRegistered, true);
                        ShutdownMonitor.this.debug("Informing client that we are stopped", new Object[0]);
                        this.informClient(out, "Stopped\r\n");
                        ShutdownMonitor.this.debug("Killing JVM", new Object[0]);
                        System.exit(0);
                        continue;
                    }
                    if ("exit".equalsIgnoreCase(cmd)) {
                        ShutdownMonitor.this.debug("Killing JVM", new Object[0]);
                        System.exit(0);
                        continue;
                    }
                    if ("status".equalsIgnoreCase(cmd)) {
                        this.informClient(out, "OK\r\n");
                        continue;
                    }
                    if (!"pid".equalsIgnoreCase(cmd)) continue;
                    this.informClient(out, Long.toString(ProcessHandle.current().pid()));
                    continue;
                }
                finally {
                    if (socket == null) continue;
                    socket.close();
                    continue;
                }
                break;
            }
        }

        private void informClient(OutputStream out, String message) throws IOException {
            out.write(message.getBytes(StandardCharsets.UTF_8));
            out.flush();
        }

        private void stopLifeCycles(Predicate<LifeCycle> predicate, boolean destroy) {
            ArrayList<LifeCycle> lifeCycles;
            try (AutoLock.WithCondition l = ShutdownMonitor.this._lock.lock();){
                lifeCycles = new ArrayList<LifeCycle>(ShutdownMonitor.this._lifeCycles);
            }
            for (LifeCycle l : lifeCycles) {
                try {
                    if (l.isStarted() && predicate.test(l)) {
                        l.stop();
                    }
                    if (!(l instanceof Destroyable) || !destroy) continue;
                    ((Destroyable)((Object)l)).destroy();
                }
                catch (Throwable x) {
                    ShutdownMonitor.this.debug(x);
                }
            }
        }
    }
}

