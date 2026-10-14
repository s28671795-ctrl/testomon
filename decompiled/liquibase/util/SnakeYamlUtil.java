/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import liquibase.Scope;
import org.yaml.snakeyaml.LoaderOptions;

public class SnakeYamlUtil {
    private static boolean showErrorMessage = true;

    private SnakeYamlUtil() {
    }

    public static void setCodePointLimitSafely(LoaderOptions loaderOptions, int codePointLimit) {
        SnakeYamlUtil.safelyCallNewSnakeYamlMethod(() -> loaderOptions.setCodePointLimit(codePointLimit));
    }

    public static void setProcessCommentsSafely(LoaderOptions loaderOptions, boolean enable) {
        SnakeYamlUtil.safelyCallNewSnakeYamlMethod(() -> loaderOptions.setProcessComments(enable));
    }

    public static void setDefaultTagInspector(LoaderOptions loaderOptions) {
        SnakeYamlUtil.safelyCallNewSnakeYamlMethod(() -> loaderOptions.setTagInspector(tag -> true));
    }

    private static void safelyCallNewSnakeYamlMethod(Runnable code) {
        block2: {
            try {
                code.run();
            }
            catch (BootstrapMethodError | NoSuchMethodError e) {
                if (!showErrorMessage) break block2;
                showErrorMessage = false;
                Scope.getCurrentScope().getLog(SnakeYamlUtil.class).warning("Failed to perform a method call for SnakeYaml because the version of SnakeYaml being used is too old. Consider upgrading to a SnakeYaml version equal to or newer than 1.32, by downloading and installing a newer version of Liquibase (which includes a newer version of SnakeYaml). Loading particularly large JSON and YAML documents (like snapshots) in Liquibase may fail if SnakeYaml is not upgraded.");
                Scope.getCurrentScope().getLog(SnakeYamlUtil.class).fine("Missing Snakeyaml feature: ", e);
            }
        }
    }
}

