/*
 * Decompiled with CFR 0.152.
 */
package liquibase.integration.commandline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.integration.commandline.CommandLineUtils;
import liquibase.util.LiquibaseUtil;
import liquibase.util.StringUtil;

public class Banner {
    private String version = LiquibaseUtil.getBuildVersionInfo();
    private String build;
    private String built = LiquibaseUtil.getBuildTime();
    private String path;
    private String licensee;
    private String licenseEndDate;

    public Banner() {
        this.build = LiquibaseUtil.getBuildNumber();
    }

    public String toString() {
        StringBuilder banner = new StringBuilder();
        if (GlobalConfiguration.SHOW_BANNER.getCurrentValue().booleanValue()) {
            Class<CommandLineUtils> commandLinUtilsClass = CommandLineUtils.class;
            InputStream inputStream2 = commandLinUtilsClass.getResourceAsStream("/liquibase/banner.txt");
            try {
                banner.append(Banner.readFromInputStream(inputStream2));
            }
            catch (IOException e) {
                Scope.getCurrentScope().getLog(commandLinUtilsClass).fine("Unable to locate banner file.");
            }
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        ResourceBundle coreBundle = ResourceBundle.getBundle("liquibase/i18n/liquibase-core");
        banner.append(String.format(coreBundle.getString("starting.liquibase.at.timestamp"), dateFormat.format(calendar.getTime())));
        if (StringUtil.isNotEmpty(this.version) && StringUtil.isNotEmpty(this.built)) {
            this.version = this.version + " #" + this.build;
            banner.append(String.format(coreBundle.getString("liquibase.version.builddate"), this.version, this.built));
        }
        return banner.toString();
    }

    private static String readFromInputStream(InputStream inputStream2) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream2));){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                resultStringBuilder.append(line + "\n");
            }
        }
        return resultStringBuilder.toString();
    }

    public String getVersion() {
        return this.version;
    }

    public String getBuild() {
        return this.build;
    }

    public String getBuilt() {
        return this.built;
    }

    public String getPath() {
        return this.path;
    }

    public String getLicensee() {
        return this.licensee;
    }

    public String getLicenseEndDate() {
        return this.licenseEndDate;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public void setBuilt(String built) {
        this.built = built;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setLicensee(String licensee) {
        this.licensee = licensee;
    }

    public void setLicenseEndDate(String licenseEndDate) {
        this.licenseEndDate = licenseEndDate;
    }
}

