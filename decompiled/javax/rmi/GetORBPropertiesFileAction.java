/*
 * Decompiled with CFR 0.152.
 */
package javax.rmi;

import java.io.File;
import java.io.FileInputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;

class GetORBPropertiesFileAction
implements PrivilegedAction<Properties> {
    private boolean debug = false;

    private String getSystemProperty(final String name) {
        String propValue = AccessController.doPrivileged(new PrivilegedAction<String>(){

            @Override
            public String run() {
                return System.getProperty(name);
            }
        });
        return propValue;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void getPropertiesFromFile(Properties props, String fileName) {
        block6: {
            try {
                File file = new File(fileName);
                if (!file.exists()) {
                    return;
                }
                FileInputStream in = new FileInputStream(file);
                try {
                    props.load(in);
                }
                finally {
                    in.close();
                }
            }
            catch (Exception exc) {
                if (!this.debug) break block6;
                System.out.println("ORB properties file " + fileName + " not found: " + exc);
            }
        }
    }

    @Override
    public Properties run() {
        Properties defaults = new Properties();
        String javaHome = this.getSystemProperty("java.home");
        String fileName = javaHome + File.separator + "lib" + File.separator + "orb.properties";
        this.getPropertiesFromFile(defaults, fileName);
        Properties results = new Properties(defaults);
        String userHome = this.getSystemProperty("user.home");
        fileName = userHome + File.separator + "orb.properties";
        this.getPropertiesFromFile(results, fileName);
        return results;
    }
}

