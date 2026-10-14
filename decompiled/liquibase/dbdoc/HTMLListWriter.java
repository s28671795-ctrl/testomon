/*
 * Decompiled with CFR 0.152.
 */
package liquibase.dbdoc;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.SortedSet;
import liquibase.GlobalConfiguration;
import liquibase.dbdoc.DBDocUtil;
import liquibase.resource.OpenOptions;
import liquibase.resource.Resource;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

public class HTMLListWriter {
    private final Resource outputDir;
    private final String directory;
    private final String filename;
    private final String title;

    public HTMLListWriter(String title, String filename, String subdir, Resource outputDir) {
        this.title = title;
        this.outputDir = outputDir;
        this.filename = filename;
        this.directory = subdir;
    }

    public void writeHTML(SortedSet objects) throws IOException {
        try (OutputStreamWriter fileWriter = new OutputStreamWriter(this.outputDir.resolve(this.filename).openOutputStream(new OpenOptions()), GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue());){
            ((Writer)fileWriter).append("<HTML>\n<HEAD><meta charset=\"utf-8\"/>\n<TITLE>\n");
            ((Writer)fileWriter).append(this.title);
            ((Writer)fileWriter).append("\n</TITLE>\n<LINK REL =\"stylesheet\" TYPE=\"text/css\" HREF=\"stylesheet.css\" TITLE=\"Style\">\n</HEAD>\n<BODY BGCOLOR=\"white\">\n<FONT size=\"+1\" CLASS=\"FrameHeadingFont\">\n<B>");
            ((Writer)fileWriter).append(this.title);
            ((Writer)fileWriter).append("</B></FONT>\n<BR>\n<TABLE BORDER=\"0\" WIDTH=\"100%\" SUMMARY=\"\"><TR>\n<TD NOWRAP><FONT CLASS=\"FrameItemFont\">");
            String currentSchema = null;
            if (objects.size() > 0 && objects.first().getClass() == Table.class) {
                currentSchema = ((Table)objects.first()).getAttribute("schema", new Schema()).toString();
                ((Writer)fileWriter).append("<div class='schema-name'>" + currentSchema + "</i></b></div>");
            }
            for (Object object : objects) {
                if (object.getClass() == Table.class) {
                    String tableSchema = ((Table)object).getAttribute("schema", new Schema()).toString();
                    if (!tableSchema.equals(currentSchema)) {
                        currentSchema = tableSchema;
                        ((Writer)fileWriter).append("<p>");
                        ((Writer)fileWriter).append("<b><i>" + currentSchema + "</i></b><br>");
                    }
                    ((Writer)fileWriter).append("<A HREF=\"");
                    ((Writer)fileWriter).append(this.directory + System.getProperty("file.separator") + tableSchema);
                } else {
                    ((Writer)fileWriter).append("<A HREF=\"");
                    ((Writer)fileWriter).append(this.directory);
                }
                ((Writer)fileWriter).append("/");
                ((Writer)fileWriter).append(DBDocUtil.toFileName(object.toString().endsWith(".xml") ? object.toString() : object.toString().toLowerCase()));
                ((Writer)fileWriter).append(this.getTargetExtension());
                ((Writer)fileWriter).append("\" target=\"objectFrame\">");
                ((Writer)fileWriter).append(StringUtil.escapeHtml(object.toString()));
                ((Writer)fileWriter).append("</A><BR>\n");
            }
            ((Writer)fileWriter).append("</FONT></TD>\n</TR>\n</TABLE>\n\n</BODY>\n</HTML>");
        }
    }

    public String getTargetExtension() {
        return ".html";
    }
}

