/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.intf.cli;

import java.util.Properties;
import org.jasypt.intf.cli.CLIUtils;
import org.jasypt.intf.service.JasyptStatelessService;

public final class JasyptPBEStringDecryptionCLI {
    private static final String[][] VALID_REQUIRED_ARGUMENTS = new String[][]{{"input"}, {"password"}};
    private static final String[][] VALID_OPTIONAL_ARGUMENTS = new String[][]{{"verbose"}, {"algorithm"}, {"keyObtentionIterations"}, {"saltGeneratorClassName"}, {"providerName"}, {"providerClassName"}, {"stringOutputType"}, {"ivGeneratorClassName"}};

    public static void main(String[] args2) {
        boolean verbose = CLIUtils.getVerbosity(args2);
        try {
            String applicationName = null;
            String[] arguments = null;
            if (args2[0] == null || args2[0].indexOf("=") != -1) {
                applicationName = JasyptPBEStringDecryptionCLI.class.getName();
                arguments = args2;
            } else {
                applicationName = args2[0];
                arguments = new String[args2.length - 1];
                System.arraycopy(args2, 1, arguments, 0, args2.length - 1);
            }
            Properties argumentValues = CLIUtils.getArgumentValues(applicationName, arguments, VALID_REQUIRED_ARGUMENTS, VALID_OPTIONAL_ARGUMENTS);
            CLIUtils.showEnvironment(verbose);
            JasyptStatelessService service2 = new JasyptStatelessService();
            String input = argumentValues.getProperty("input");
            CLIUtils.showArgumentDescription(argumentValues, verbose);
            String result = service2.decrypt(input, argumentValues.getProperty("password"), null, null, argumentValues.getProperty("algorithm"), null, null, argumentValues.getProperty("keyObtentionIterations"), null, null, argumentValues.getProperty("saltGeneratorClassName"), null, null, argumentValues.getProperty("providerName"), null, null, argumentValues.getProperty("providerClassName"), null, null, argumentValues.getProperty("stringOutputType"), null, null, argumentValues.getProperty("ivGeneratorClassName"), null, null);
            CLIUtils.showOutput(result, verbose);
        }
        catch (Throwable t2) {
            CLIUtils.showError(t2, verbose);
        }
    }

    private JasyptPBEStringDecryptionCLI() {
    }
}

