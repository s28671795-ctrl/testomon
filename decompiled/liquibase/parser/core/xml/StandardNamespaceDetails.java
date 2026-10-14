/*
 * Decompiled with CFR 0.152.
 */
package liquibase.parser.core.xml;

import java.util.regex.Pattern;
import liquibase.parser.LiquibaseParser;
import liquibase.parser.NamespaceDetails;
import liquibase.parser.core.xml.XMLChangeLogSAXParser;
import liquibase.serializer.LiquibaseSerializer;
import liquibase.serializer.core.xml.XMLChangeLogSerializer;

public class StandardNamespaceDetails
implements NamespaceDetails {
    public static final String GENERIC_EXTENSION_XSD = "http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd";
    private static final String STANDARD_XSD_URL_REGEX = "http://www.liquibase.org/xml/ns/dbchangelog/(dbchangelog-[\\w\\.]+.xsd)";
    private static final Pattern STANDARD_XSD_URL_PATTERN = Pattern.compile("http://www.liquibase.org/xml/ns/dbchangelog/(dbchangelog-[\\w\\.]+.xsd)");
    private static final String OLD_STANDARD_XSD_URL_REGEX = "http://www.liquibase.org/xml/ns/migrator/(dbchangelog-[\\w\\.]+.xsd)";
    private static final Pattern OLD_STANDARD_XSD_URL_PATTERN = Pattern.compile("http://www.liquibase.org/xml/ns/migrator/(dbchangelog-[\\w\\.]+.xsd)");

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean supports(LiquibaseSerializer serializer, String namespaceOrUrl) {
        return serializer instanceof XMLChangeLogSerializer;
    }

    @Override
    public boolean supports(LiquibaseParser parser, String namespaceOrUrl) {
        return parser instanceof XMLChangeLogSAXParser;
    }

    @Override
    public String getShortName(String namespaceOrUrl) {
        if (namespaceOrUrl.equals("http://www.liquibase.org/xml/ns/dbchangelog")) {
            return "";
        }
        return "ext";
    }

    @Override
    public String[] getNamespaces() {
        return new String[]{"http://www.liquibase.org/xml/ns/dbchangelog", "http://www.liquibase.org/xml/ns/dbchangelog-ext"};
    }

    @Override
    public String getSchemaUrl(String namespaceOrUrl) {
        if (namespaceOrUrl.equals("http://www.liquibase.org/xml/ns/dbchangelog")) {
            return "http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd";
        }
        return GENERIC_EXTENSION_XSD;
    }
}

