/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.intf.cli;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import org.jasypt.commons.CommonUtils;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;

final class CLIUtils {
    static void showEnvironment(boolean verbose) {
        if (verbose) {
            System.out.println("\n----ENVIRONMENT-----------------\n");
            System.out.println("Runtime: " + System.getProperty("java.vm.vendor") + " " + System.getProperty("java.vm.name") + " " + System.getProperty("java.vm.version") + " ");
            System.out.println("\n");
        }
    }

    static void showArgumentDescription(Properties argumentValues, boolean verbose) {
        if (verbose) {
            System.out.println("\n----ARGUMENTS-------------------\n");
            for (Map.Entry<Object, Object> entry : argumentValues.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            System.out.println("\n");
        }
    }

    static void showOutput(String output, boolean verbose) {
        if (verbose) {
            System.out.println("\n----OUTPUT----------------------\n");
            System.out.println(output);
            System.out.println("\n");
        } else {
            System.out.println(output);
        }
    }

    static void showError(Throwable t2, boolean verbose) {
        if (verbose) {
            System.err.println("\n----ERROR-----------------------\n");
            if (t2 instanceof EncryptionOperationNotPossibleException) {
                System.err.println("Operation not possible (Bad input or parameters)");
            } else if (t2.getMessage() != null) {
                System.err.println(t2.getMessage());
            } else {
                System.err.println(t2.getClass().getName());
            }
            System.err.println("\n");
        } else {
            System.err.print("ERROR: ");
            if (t2 instanceof EncryptionOperationNotPossibleException) {
                System.err.println("Operation not possible (Bad input or parameters)");
            } else if (t2.getMessage() != null) {
                System.err.println(t2.getMessage());
            } else {
                System.err.println(t2.getClass().getName());
            }
        }
    }

    static boolean getVerbosity(String[] args2) {
        for (int i = 0; i < args2.length; ++i) {
            String key = CommonUtils.substringBefore(args2[i], "=");
            String value = CommonUtils.substringAfter(args2[i], "=");
            if (CommonUtils.isEmpty(key) || CommonUtils.isEmpty(value) || !"verbose".equals(key)) continue;
            Boolean verbosity = CommonUtils.getStandardBooleanValue(value);
            return verbosity != null ? verbosity : false;
        }
        return true;
    }

    static Properties getArgumentValues(String appName, String[] args2, String[][] requiredArgNames, String[][] optionalArgNames) {
        int i;
        int i2;
        HashSet<String> argNames = new HashSet<String>();
        for (i2 = 0; i2 < requiredArgNames.length; ++i2) {
            argNames.addAll(Arrays.asList(requiredArgNames[i2]));
        }
        for (i2 = 0; i2 < optionalArgNames.length; ++i2) {
            argNames.addAll(Arrays.asList(optionalArgNames[i2]));
        }
        Properties argumentValues = new Properties();
        for (i = 0; i < args2.length; ++i) {
            String key = CommonUtils.substringBefore(args2[i], "=");
            String value = CommonUtils.substringAfter(args2[i], "=");
            if (CommonUtils.isEmpty(key) || CommonUtils.isEmpty(value)) {
                throw new IllegalArgumentException("Bad argument: " + args2[i]);
            }
            if (argNames.contains(key)) {
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    argumentValues.setProperty(key, value.substring(1, value.length() - 1));
                    continue;
                }
                argumentValues.setProperty(key, value);
                continue;
            }
            throw new IllegalArgumentException("Bad argument: " + args2[i]);
        }
        for (i = 0; i < requiredArgNames.length; ++i) {
            boolean found = false;
            for (int j = 0; j < requiredArgNames[i].length; ++j) {
                if (!argumentValues.containsKey(requiredArgNames[i][j])) continue;
                found = true;
            }
            if (found) continue;
            CLIUtils.showUsageAndExit(appName, requiredArgNames, optionalArgNames);
        }
        return argumentValues;
    }

    static void showUsageAndExit(String appName, String[][] requiredArgNames, String[][] optionalArgNames) {
        int j;
        int i;
        System.err.println("\nUSAGE: " + appName + " [ARGUMENTS]\n");
        System.err.println("  * Arguments must apply to format:\n");
        System.err.println("      \"arg1=value1 arg2=value2 arg3=value3 ...\"");
        System.err.println();
        System.err.println("  * Required arguments:\n");
        for (i = 0; i < requiredArgNames.length; ++i) {
            System.err.print("      ");
            if (requiredArgNames[i].length == 1) {
                System.err.print(requiredArgNames[i][0]);
            } else {
                System.err.print("(");
                for (j = 0; j < requiredArgNames[i].length; ++j) {
                    if (j > 0) {
                        System.err.print(" | ");
                    }
                    System.err.print(requiredArgNames[i][j]);
                }
                System.err.print(")");
            }
            System.err.println();
        }
        System.err.println();
        System.err.println("  * Optional arguments:\n");
        for (i = 0; i < optionalArgNames.length; ++i) {
            System.err.print("      ");
            if (optionalArgNames[i].length == 1) {
                System.err.print(optionalArgNames[i][0]);
            } else {
                System.err.print("(");
                for (j = 0; j < optionalArgNames[i].length; ++j) {
                    if (j > 0) {
                        System.err.print(" | ");
                    }
                    System.err.print(optionalArgNames[i][j]);
                }
                System.err.print(")");
            }
            System.err.println();
        }
        System.exit(1);
    }

    private CLIUtils() {
    }
}

