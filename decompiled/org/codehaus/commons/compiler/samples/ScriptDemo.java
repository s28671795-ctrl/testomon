/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.samples;

import java.util.Arrays;
import org.codehaus.commons.compiler.CompilerFactoryFactory;
import org.codehaus.commons.compiler.IScriptEvaluator;
import org.codehaus.commons.compiler.samples.DemoBase;

public final class ScriptDemo
extends DemoBase {
    public static void main(String[] args2) throws Exception {
        String arg;
        int i;
        Class<Void> returnType = Void.TYPE;
        String[] parameterNames = new String[]{};
        Class[] parameterTypes = new Class[]{};
        Class[] thrownExceptions = new Class[]{};
        String[] defaultImports = new String[]{};
        for (i = 0; i < args2.length && (arg = args2[i]).startsWith("-"); ++i) {
            if ("-rt".equals(arg)) {
                returnType = DemoBase.stringToType(args2[++i]);
                continue;
            }
            if ("-pn".equals(arg)) {
                parameterNames = DemoBase.explode(args2[++i]);
                continue;
            }
            if ("-pt".equals(arg)) {
                parameterTypes = DemoBase.stringToTypes(args2[++i]);
                continue;
            }
            if ("-te".equals(arg)) {
                thrownExceptions = DemoBase.stringToTypes(args2[++i]);
                continue;
            }
            if ("-di".equals(arg)) {
                defaultImports = DemoBase.explode(args2[++i]);
                continue;
            }
            if ("-help".equals(arg)) {
                System.err.println("Usage:");
                System.err.println("  ScriptDemo { <option> } <script> { <parameter-value> }");
                System.err.println("Valid options are");
                System.err.println(" -rt <return-type>                            (default: void)");
                System.err.println(" -pn <comma-separated-parameter-names>        (default: none)");
                System.err.println(" -pt <comma-separated-parameter-types>        (default: none)");
                System.err.println(" -te <comma-separated-thrown-exception-types> (default: none)");
                System.err.println(" -di <comma-separated-default-imports>        (default: none)");
                System.err.println(" -help");
                System.err.println("The number of parameter names, types and values must be identical.");
                System.exit(0);
                continue;
            }
            System.err.println("Invalid command line option \"" + arg + "\"; try \"-help\".");
            System.exit(1);
        }
        if (i == args2.length) {
            System.err.println("Script missing on command line; try \"-help\".");
            System.exit(1);
        }
        String script = args2[i++];
        if (parameterTypes.length != parameterNames.length) {
            System.err.println("Parameter type count and parameter name count do not match; try \"-help\".");
            System.exit(1);
        }
        if (args2.length - i != parameterNames.length) {
            System.err.println("Argument and parameter count do not match; try \"-help\".");
            System.exit(1);
        }
        Object[] arguments = new Object[parameterNames.length];
        for (int j = 0; j < parameterNames.length; ++j) {
            arguments[j] = DemoBase.createObject(parameterTypes[j], args2[i + j]);
        }
        IScriptEvaluator se = CompilerFactoryFactory.getDefaultCompilerFactory(ScriptDemo.class.getClassLoader()).newScriptEvaluator();
        se.setReturnType(returnType);
        se.setDefaultImports(defaultImports);
        se.setParameters(parameterNames, parameterTypes);
        se.setThrownExceptions(thrownExceptions);
        se.cook(script);
        Object res = se.evaluate(arguments);
        System.out.println("Result = " + (res instanceof Object[] ? Arrays.toString((Object[])res) : String.valueOf(res)));
    }

    private ScriptDemo() {
    }
}

