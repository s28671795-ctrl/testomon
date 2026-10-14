/*
 * Decompiled with CFR 0.152.
 */
package com.license4j.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PowerShellUtils {
    private static final long SHUTDOWN_DELAY = 10L;
    private static final TimeUnit SHUTDOWN_UNIT = TimeUnit.MINUTES;
    private static Process powerShellProcess;
    private static BufferedWriter writer;
    private static BufferedReader reader;
    private static final ScheduledExecutorService scheduler;
    private static ScheduledFuture<?> shutdownTask;
    private static final Object lock;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void startPowerShell() {
        Object object = lock;
        synchronized (object) {
            if (powerShellProcess != null && powerShellProcess.isAlive()) {
                return;
            }
            try {
                ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe", "-Command", "-");
                processBuilder.redirectErrorStream(true);
                powerShellProcess = processBuilder.start();
                writer = new BufferedWriter(new OutputStreamWriter(powerShellProcess.getOutputStream()));
                reader = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));
                while (reader.ready()) {
                    reader.readLine();
                }
                PowerShellUtils.resetShutdownTimer();
            }
            catch (IOException e) {
                throw new RuntimeException("Failed to start PowerShell", e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void resetShutdownTimer() {
        Object object = lock;
        synchronized (object) {
            if (shutdownTask != null) {
                shutdownTask.cancel(false);
            }
            shutdownTask = scheduler.schedule(PowerShellUtils::shutdown, 10L, SHUTDOWN_UNIT);
        }
    }

    public static String execCommand(String command) {
        PowerShellUtils.startPowerShell();
        Object object = lock;
        synchronized (object) {
            try {
                String line;
                writer.write(command);
                writer.newLine();
                writer.write("echo __END__");
                writer.newLine();
                writer.flush();
                StringBuilder output = new StringBuilder();
                while ((line = reader.readLine()) != null && !line.equals("__END__")) {
                    output.append(line).append(System.lineSeparator());
                }
                PowerShellUtils.resetShutdownTimer();
                return output.toString().trim();
            }
            catch (IOException e) {
                throw new RuntimeException("Error executing PowerShell command", e);
            }
        }
    }

    public static Map<String, String> queryAsList(String command) {
        return PowerShellUtils.execCommand(command).trim().lines().map(it -> Arrays.stream(it.split(":", 2)).collect(Collectors.toList())).filter(it -> !it.isEmpty() && it.getFirst() != null && !((String)it.getFirst()).isEmpty()).collect(Collectors.toMap(it -> ((String)it.getFirst()).trim(), it -> it.size() > 1 ? ((String)it.get(1)).trim() : "unknown", (a, b) -> a));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void shutdown() {
        Object object = lock;
        synchronized (object) {
            if (shutdownTask != null) {
                shutdownTask.cancel(false);
            }
            if (powerShellProcess != null) {
                try {
                    if (writer != null) {
                        writer.write("exit");
                        writer.newLine();
                        writer.flush();
                        writer.close();
                    }
                    if (reader != null) {
                        reader.close();
                    }
                    powerShellProcess.destroy();
                }
                catch (IOException e) {
                    throw new RuntimeException("Error closing PowerShell process", e);
                }
                finally {
                    powerShellProcess = null;
                    writer = null;
                    reader = null;
                }
            }
        }
    }

    static {
        scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t2 = new Thread(r);
            t2.setDaemon(true);
            return t2;
        });
        lock = new Object();
        Runtime.getRuntime().addShutdownHook(new Thread(PowerShellUtils::shutdown));
    }
}

