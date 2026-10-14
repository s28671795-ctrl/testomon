/*
 * Decompiled with CFR 0.152.
 */
package liquibase.dbdoc;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.database.Database;
import liquibase.dbdoc.HTMLWriter;
import liquibase.exception.DatabaseException;
import liquibase.exception.DatabaseHistoryException;
import liquibase.resource.PathHandlerFactory;
import liquibase.resource.Resource;
import liquibase.structure.core.Column;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Table;

public class TableWriter
extends HTMLWriter {
    public TableWriter(Resource rootOutputDir, Database database) {
        super(rootOutputDir.resolve("tables"), database);
    }

    @Override
    protected String createTitle(Object object) {
        return "Changes affecting table \"" + object.toString() + "\"";
    }

    @Override
    protected void writeCustomHTML(Writer fileWriter, Object object, List<Change> changes, Database database) throws IOException {
        Table table = (Table)object;
        this.writeTableRemarks(fileWriter, table);
        this.writeColumns(fileWriter, table);
        this.writeTableIndexes(fileWriter, table);
        this.writeTableForeignKeys(fileWriter, table);
    }

    public void writeHTML(Object object, List<Change> ranChanges, List<Change> changesToRun, String changeLog, String schema) throws DatabaseHistoryException, IOException, DatabaseException {
        this.outputDir = Scope.getCurrentScope().getSingleton(PathHandlerFactory.class).getResource(this.baseOutputDir.getPath() + System.getProperty("file.separator") + schema);
        super.writeHTML(object, ranChanges, changesToRun, changeLog);
    }

    private void writeColumns(Writer fileWriter, Table table) throws IOException {
        ArrayList<List<String>> cells = new ArrayList<List<String>>();
        for (Column column : table.getColumns()) {
            String remarks = column.getRemarks();
            cells.add(Arrays.asList(column.getType().toString(), column.isNullable() != false ? "NULL" : "NOT NULL", "<A HREF=\"../columns/" + (table.getSchema().getName() != null ? table.getSchema().getName().toLowerCase() + "." : "") + table.getName().toLowerCase() + "." + column.getName().toLowerCase() + ".html\">" + column.getName() + "</A>", remarks != null ? remarks : ""));
        }
        this.writeTable("Current Columns", cells, fileWriter);
    }

    private void writeTableRemarks(Writer fileWriter, Table table) throws IOException {
        String tableRemarks = table.getRemarks();
        if (tableRemarks != null && !tableRemarks.isEmpty()) {
            ArrayList<List<String>> cells = new ArrayList<List<String>>();
            cells.add(Collections.singletonList(tableRemarks));
            this.writeTable("Table Description", cells, fileWriter);
        }
    }

    private void writeTableIndexes(Writer fileWriter, Table table) throws IOException {
        ArrayList<List<String>> cells = new ArrayList<List<String>>();
        PrimaryKey primaryKey = table.getPrimaryKey();
        if (!table.getIndexes().isEmpty()) {
            for (Index index : table.getIndexes()) {
                cells.add(Arrays.asList((primaryKey != null && primaryKey.getBackingIndex() == index ? "Primary Key " : (index.isUnique() != false ? "Unique " : "Non-Unique ")) + (index.getClustered() == null ? "" : (index.getClustered() != false ? "Clustered" : "Non-Clustered")), index.getName(), index.getColumnNames().replace(index.getRelation().getName() + ".", "")));
            }
            this.writeTable("Current Table Indexes", cells, fileWriter);
        }
    }

    private void writeTableForeignKeys(Writer fileWriter, Table table) throws IOException {
        ArrayList<List<String>> cells = new ArrayList<List<String>>();
        if (!table.getOutgoingForeignKeys().isEmpty()) {
            for (ForeignKey outgoingForeignKey : table.getOutgoingForeignKeys()) {
                cells.add(Arrays.asList(outgoingForeignKey.getName(), outgoingForeignKey.getForeignKeyColumns().toString().replace(table.getName() + ".", "").replaceAll("[\\[\\]]", ""), outgoingForeignKey.getPrimaryKeyTable().toString(), outgoingForeignKey.getPrimaryKeyColumns().toString().replace(outgoingForeignKey.getPrimaryKeyTable().toString() + ".", "").replaceAll("[\\[\\]]", "")));
            }
            this.writeTable("Current Table Foreign Keys", cells, fileWriter);
        }
    }
}

