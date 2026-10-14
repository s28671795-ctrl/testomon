/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.AbstractList;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.jooq.Catalog;
import org.jooq.Check;
import org.jooq.Comment;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Constraint;
import org.jooq.DataType;
import org.jooq.Delete;
import org.jooq.Domain;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Insert;
import org.jooq.Merge;
import org.jooq.Meta;
import org.jooq.Name;
import org.jooq.Named;
import org.jooq.Nullability;
import org.jooq.OrderField;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Sequence;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.Table;
import org.jooq.TableElement;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.Update;
import org.jooq.conf.InterpreterNameLookupCaseSensitivity;
import org.jooq.conf.InterpreterSearchSchema;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataDefinitionException;
import org.jooq.impl.AbstractMeta;
import org.jooq.impl.AbstractName;
import org.jooq.impl.AlterDomainImpl;
import org.jooq.impl.AlterIndexImpl;
import org.jooq.impl.AlterSchemaImpl;
import org.jooq.impl.AlterSequenceImpl;
import org.jooq.impl.AlterTableImpl;
import org.jooq.impl.AlterViewImpl;
import org.jooq.impl.CatalogImpl;
import org.jooq.impl.CheckImpl;
import org.jooq.impl.CommentOnImpl;
import org.jooq.impl.ConstraintImpl;
import org.jooq.impl.ConstraintType;
import org.jooq.impl.Convert;
import org.jooq.impl.CreateDomainImpl;
import org.jooq.impl.CreateIndexImpl;
import org.jooq.impl.CreateSchemaImpl;
import org.jooq.impl.CreateSequenceImpl;
import org.jooq.impl.CreateTableImpl;
import org.jooq.impl.CreateViewImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultParseContext;
import org.jooq.impl.DomainImpl;
import org.jooq.impl.DropDomainImpl;
import org.jooq.impl.DropIndexImpl;
import org.jooq.impl.DropSchemaImpl;
import org.jooq.impl.DropSequenceImpl;
import org.jooq.impl.DropTableImpl;
import org.jooq.impl.DropViewImpl;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.FieldsTrait;
import org.jooq.impl.IndexImpl;
import org.jooq.impl.QOM;
import org.jooq.impl.ReferenceImpl;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SchemaImpl;
import org.jooq.impl.SequenceImpl;
import org.jooq.impl.SetCommand;
import org.jooq.impl.SetSchema;
import org.jooq.impl.TableImpl;
import org.jooq.impl.Tools;
import org.jooq.impl.TruncateImpl;
import org.jooq.impl.UniqueKeyImpl;
import org.jooq.impl.UnqualifiedName;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

final class Interpreter {
    private static final JooqLogger log = JooqLogger.getLogger(Interpreter.class);
    private final Configuration configuration;
    private final InterpreterNameLookupCaseSensitivity caseSensitivity;
    private final Locale locale;
    private final Map<Name, MutableCatalog> catalogs = new LinkedHashMap<Name, MutableCatalog>();
    private final MutableCatalog defaultCatalog;
    private final MutableSchema defaultSchema;
    private MutableSchema currentSchema;
    private boolean delayForeignKeyDeclarations;
    private final Deque<DelayedForeignKey> delayedForeignKeyDeclarations;
    private final Map<Name, MutableCatalog.InterpretedCatalog> interpretedCatalogs = new HashMap<Name, MutableCatalog.InterpretedCatalog>();
    private final Map<Name, MutableSchema.InterpretedSchema> interpretedSchemas = new HashMap<Name, MutableSchema.InterpretedSchema>();
    private final Map<Name, MutableTable.InterpretedTable> interpretedTables = new HashMap<Name, MutableTable.InterpretedTable>();
    private final Map<Name, UniqueKeyImpl<Record>> interpretedUniqueKeys = new HashMap<Name, UniqueKeyImpl<Record>>();
    private final Map<Name, ReferenceImpl<Record, ?>> interpretedForeignKeys = new HashMap();
    private final Map<Name, Index> interpretedIndexes = new HashMap<Name, Index>();
    private final Map<Name, MutableDomain.InterpretedDomain> interpretedDomains = new HashMap<Name, MutableDomain.InterpretedDomain>();
    private final Map<Name, MutableSequence.InterpretedSequence> interpretedSequences = new HashMap<Name, MutableSequence.InterpretedSequence>();

    Interpreter(Configuration configuration) {
        this.configuration = configuration;
        this.delayForeignKeyDeclarations = Boolean.TRUE.equals(configuration.settings().isInterpreterDelayForeignKeyDeclarations());
        this.delayedForeignKeyDeclarations = new ArrayDeque<DelayedForeignKey>();
        this.caseSensitivity = Interpreter.caseSensitivity(configuration);
        this.locale = SettingsTools.interpreterLocale(configuration.settings());
        this.defaultCatalog = new MutableCatalog(AbstractName.NO_NAME);
        this.catalogs.put(this.defaultCatalog.name(), this.defaultCatalog);
        this.defaultSchema = new MutableSchema(AbstractName.NO_NAME, this.defaultCatalog);
    }

    final Meta meta() {
        this.applyDelayedForeignKeys();
        return new AbstractMeta(this.configuration){

            @Override
            final List<Catalog> getCatalogs0() throws DataAccessException {
                return Tools.map(Interpreter.this.catalogs.values(), c -> c.interpretedCatalog());
            }
        };
    }

    final void accept(Query query) {
        this.invalidateCaches();
        if (log.isDebugEnabled()) {
            log.debug(query);
        }
        if (query instanceof CreateSchemaImpl) {
            CreateSchemaImpl q = (CreateSchemaImpl)query;
            this.accept0(q);
        } else if (query instanceof AlterSchemaImpl) {
            AlterSchemaImpl q = (AlterSchemaImpl)query;
            this.accept0(q);
        } else if (query instanceof DropSchemaImpl) {
            DropSchemaImpl q = (DropSchemaImpl)query;
            this.accept0(q);
        } else if (query instanceof CreateTableImpl) {
            CreateTableImpl q = (CreateTableImpl)query;
            this.accept0(q);
        } else if (query instanceof AlterTableImpl) {
            AlterTableImpl q = (AlterTableImpl)query;
            this.accept0(q);
        } else if (query instanceof DropTableImpl) {
            DropTableImpl q = (DropTableImpl)query;
            this.accept0(q);
        } else if (query instanceof TruncateImpl) {
            TruncateImpl q = (TruncateImpl)query;
            this.accept0(q);
        } else if (query instanceof CreateViewImpl) {
            CreateViewImpl q = (CreateViewImpl)query;
            this.accept0(q);
        } else if (query instanceof AlterViewImpl) {
            AlterViewImpl q = (AlterViewImpl)query;
            this.accept0(q);
        } else if (query instanceof DropViewImpl) {
            DropViewImpl q = (DropViewImpl)query;
            this.accept0(q);
        } else if (query instanceof CreateSequenceImpl) {
            CreateSequenceImpl q = (CreateSequenceImpl)query;
            this.accept0(q);
        } else if (query instanceof AlterSequenceImpl) {
            AlterSequenceImpl q = (AlterSequenceImpl)query;
            this.accept0(q);
        } else if (query instanceof DropSequenceImpl) {
            DropSequenceImpl q = (DropSequenceImpl)query;
            this.accept0(q);
        } else if (query instanceof CreateIndexImpl) {
            CreateIndexImpl q = (CreateIndexImpl)query;
            this.accept0(q);
        } else if (query instanceof AlterIndexImpl) {
            AlterIndexImpl q = (AlterIndexImpl)query;
            this.accept0(q);
        } else if (query instanceof DropIndexImpl) {
            DropIndexImpl q = (DropIndexImpl)query;
            this.accept0(q);
        } else if (query instanceof CreateDomainImpl) {
            CreateDomainImpl q = (CreateDomainImpl)query;
            this.accept0(q);
        } else if (query instanceof AlterDomainImpl) {
            AlterDomainImpl q = (AlterDomainImpl)query;
            this.accept0(q);
        } else if (query instanceof DropDomainImpl) {
            DropDomainImpl q = (DropDomainImpl)query;
            this.accept0(q);
        } else if (query instanceof CommentOnImpl) {
            CommentOnImpl q = (CommentOnImpl)query;
            this.accept0(q);
        } else if (query instanceof SetSchema) {
            SetSchema q = (SetSchema)query;
            this.accept0(q);
        } else if (!(query instanceof Select || query instanceof Update || query instanceof Insert || query instanceof Delete || query instanceof Merge)) {
            if (query instanceof SetCommand) {
                SetCommand q = (SetCommand)query;
                this.accept0(q);
            } else if (!(query instanceof DefaultParseContext.IgnoreQuery)) {
                throw Interpreter.unsupportedQuery(query);
            }
        }
    }

    private final void invalidateCaches() {
        this.interpretedCatalogs.clear();
        this.interpretedSchemas.clear();
        this.interpretedTables.clear();
        this.interpretedUniqueKeys.clear();
        this.interpretedForeignKeys.clear();
        this.interpretedIndexes.clear();
        this.interpretedSequences.clear();
    }

    private final void accept0(CreateSchemaImpl query) {
        Schema schema = query.$schema();
        if (this.getSchema(schema, false) != null) {
            if (!query.$ifNotExists()) {
                throw Interpreter.alreadyExists(schema);
            }
            return;
        }
        this.getSchema(schema, true);
    }

    private final void accept0(AlterSchemaImpl query) {
        Schema schema = query.$schema();
        MutableSchema oldSchema = this.getSchema(schema);
        if (oldSchema == null) {
            if (!query.$ifExists()) {
                throw Interpreter.notExists(schema);
            }
            return;
        }
        if (query.$renameTo() != null) {
            Schema renameTo = query.$renameTo();
            if (this.getSchema(renameTo, false) != null) {
                throw Interpreter.alreadyExists(renameTo);
            }
            oldSchema.name((UnqualifiedName)renameTo.getUnqualifiedName());
            return;
        }
        throw Interpreter.unsupportedQuery(query);
    }

    private final void accept0(DropSchemaImpl query) {
        Schema schema = query.$schema();
        MutableSchema mutableSchema = this.getSchema(schema);
        if (mutableSchema == null) {
            if (!query.$ifExists()) {
                throw Interpreter.notExists(schema);
            }
            return;
        }
        if (!mutableSchema.isEmpty() && query.$cascade() != QOM.Cascade.CASCADE) {
            throw Interpreter.schemaNotEmpty(schema);
        }
        mutableSchema.catalog.schemas.remove(mutableSchema);
    }

    private final void accept0(CreateTableImpl query) {
        Table<?> table = query.$table();
        MutableSchema schema = this.getSchema(table.getSchema(), true);
        MutableTable existing = schema.table(table);
        if (existing != null) {
            if (!query.$ifNotExists()) {
                throw Interpreter.alreadyExists(table, existing);
            }
            return;
        }
        MutableTable mt = this.newTable(table, schema, query.$columns(), query.$select(), query.$comment(), query.$temporary() ? TableOptions.temporaryTable(query.$onCommit()) : TableOptions.table());
        for (Constraint constraint : query.$constraints()) {
            this.addConstraint(query, (ConstraintImpl)constraint, mt);
        }
        for (Index index : query.$indexes()) {
            IndexImpl impl = (IndexImpl)index;
            mt.indexes.add(new MutableIndex((UnqualifiedName)impl.getUnqualifiedName(), mt, mt.sortFields(Arrays.asList(impl.$fields())), impl.$unique(), impl.$where()));
        }
    }

    private final void addForeignKey(MutableTable mt, ConstraintImpl impl) {
        if (this.delayForeignKeyDeclarations) {
            this.delayForeignKey(mt, impl);
        } else {
            this.addForeignKey0(mt, impl);
        }
    }

    private final void delayForeignKey(MutableTable mt, ConstraintImpl impl) {
        this.delayedForeignKeyDeclarations.add(new DelayedForeignKey(mt, impl));
    }

    private final void applyDelayedForeignKeys() {
        Iterator<DelayedForeignKey> it = this.delayedForeignKeyDeclarations.iterator();
        while (it.hasNext()) {
            DelayedForeignKey key = it.next();
            this.addForeignKey0(key.table, key.constraint);
            it.remove();
        }
    }

    private final void addForeignKey0(MutableTable mt, ConstraintImpl impl) {
        MutableSchema ms = this.getSchema(impl.$referencesTable().getSchema());
        if (ms == null) {
            throw Interpreter.notExists(impl.$referencesTable().getSchema());
        }
        MutableTable mrf = ms.table(impl.$referencesTable());
        MutableUniqueKey mu = null;
        if (mrf == null) {
            throw Interpreter.notExists(impl.$referencesTable());
        }
        List<MutableField> mfs = mt.fields(impl.$foreignKey(), true);
        List mrfs = mrf.fields(impl.$references(), true);
        if (!mrfs.isEmpty()) {
            mu = mrf.uniqueKey(mrfs);
        } else if (mrf.primaryKey != null && mrf.primaryKey.fields.size() == mfs.size()) {
            mu = mrf.primaryKey;
            mrfs = mu.fields;
        }
        if (mu == null) {
            throw Interpreter.primaryKeyNotExists(impl.$referencesTable());
        }
        mt.foreignKeys.add(new MutableForeignKey((UnqualifiedName)impl.getUnqualifiedName(), mt, mfs, mu, mrfs, impl.$onDelete(), impl.$onUpdate(), impl.$enforced()));
    }

    private final void drop(List<MutableTable> tables, MutableTable table, QOM.Cascade cascade) {
        boolean[] blArray;
        if (cascade == QOM.Cascade.CASCADE) {
            boolean[] blArray2 = new boolean[1];
            blArray = blArray2;
            blArray2[0] = false;
        } else {
            boolean[] blArray3 = new boolean[2];
            blArray3[0] = true;
            blArray = blArray3;
            blArray3[1] = false;
        }
        for (boolean check2 : blArray) {
            if (table.primaryKey != null) {
                this.cascade(table.primaryKey, null, check2 ? QOM.Cascade.RESTRICT : QOM.Cascade.CASCADE);
            }
            this.cascade(table.uniqueKeys, null, check2);
        }
        Iterator<MutableTable> it = tables.iterator();
        while (it.hasNext()) {
            if (!it.next().nameEquals(table.name())) continue;
            it.remove();
            break;
        }
    }

    private final void dropColumns(MutableTable table, List<MutableField> fields, QOM.Cascade cascade) {
        boolean[] blArray;
        Iterator<MutableIndex> it1 = table.indexes.iterator();
        if (cascade == QOM.Cascade.CASCADE) {
            boolean[] blArray2 = new boolean[1];
            blArray = blArray2;
            blArray2[0] = false;
        } else {
            boolean[] blArray3 = new boolean[2];
            blArray3[0] = true;
            blArray = blArray3;
            blArray3[1] = false;
        }
        for (boolean check2 : blArray) {
            if (table.primaryKey != null && Tools.anyMatch(table.primaryKey.fields, t1 -> fields.contains(t1))) {
                this.cascade(table.primaryKey, fields, check2 ? QOM.Cascade.RESTRICT : QOM.Cascade.CASCADE);
                if (!check2) {
                    table.primaryKey = null;
                }
            }
            this.cascade(table.uniqueKeys, fields, check2);
        }
        this.cascade(table.foreignKeys, fields, false);
        block1: while (it1.hasNext()) {
            Object object = it1.next().fields.iterator();
            while (object.hasNext()) {
                MutableSortField msf = (MutableSortField)object.next();
                if (!fields.contains(msf.field)) continue;
                it1.remove();
                continue block1;
            }
        }
        table.fields.removeAll(fields);
    }

    private final void cascade(List<? extends MutableKey> keys2, List<MutableField> fields, boolean check2) {
        Iterator<? extends MutableKey> it2 = keys2.iterator();
        while (it2.hasNext()) {
            MutableKey key = it2.next();
            if (fields != null && !Tools.anyMatch(key.fields, t1 -> fields.contains(t1))) continue;
            if (key instanceof MutableUniqueKey) {
                MutableUniqueKey k = (MutableUniqueKey)key;
                this.cascade(k, fields, check2 ? QOM.Cascade.RESTRICT : QOM.Cascade.CASCADE);
            }
            if (check2) continue;
            it2.remove();
        }
    }

    private final void cascade(MutableUniqueKey key, List<MutableField> fields, QOM.Cascade cascade) {
        for (MutableTable mt : this.tables()) {
            Iterator<MutableForeignKey> it = mt.foreignKeys.iterator();
            while (it.hasNext()) {
                MutableForeignKey mfk = it.next();
                if (!mfk.referencedKey.equals(key)) continue;
                if (cascade == QOM.Cascade.CASCADE) {
                    it.remove();
                    continue;
                }
                if (fields == null) {
                    throw new DataDefinitionException("Cannot drop constraint " + String.valueOf(key) + " because other objects depend on it");
                }
                if (fields.size() == 1) {
                    throw new DataDefinitionException("Cannot drop column " + String.valueOf(fields.get(0)) + " because other objects depend on it");
                }
                throw new DataDefinitionException("Cannot drop columns " + String.valueOf(fields) + " because other objects depend on them");
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final void accept0(AlterTableImpl query) {
        Table<?> table = query.$table();
        MutableSchema schema = this.getSchema(table.getSchema());
        MutableTable existing = schema.table(table);
        if (existing == null) {
            if (query.$ifExists()) return;
            throw Interpreter.notExists(table);
        }
        if (!existing.options.type().isTable()) {
            throw Interpreter.objectNotTable(table);
        }
        if (query.$add() != null) {
            for (TableElement tableElement : query.$add()) {
                if (tableElement instanceof Field && Interpreter.find(existing.fields, (Named)((Field)tableElement)) != null) {
                    throw Interpreter.alreadyExists(tableElement);
                }
                if (!(tableElement instanceof Constraint) || tableElement.getUnqualifiedName().empty() || existing.constraint((Constraint)tableElement) == null) continue;
                throw Interpreter.alreadyExists(tableElement);
            }
            if (query.$addFirst()) {
                for (Field field2 : this.assertFields(query, Tools.reverseIterable(query.$add()))) {
                    this.addField(existing, 0, (UnqualifiedName)field2.getUnqualifiedName(), field2.getDataType());
                }
                return;
            } else if (query.$addBefore() != null) {
                int index = Interpreter.indexOrFail(existing.fields, query.$addBefore());
                for (Field<?> f : this.assertFields(query, Tools.reverseIterable(query.$add()))) {
                    this.addField(existing, index, (UnqualifiedName)f.getUnqualifiedName(), f.getDataType());
                }
                return;
            } else if (query.$addAfter() != null) {
                int index = Interpreter.indexOrFail(existing.fields, query.$addAfter()) + 1;
                for (Field<?> f : this.assertFields(query, Tools.reverseIterable(query.$add()))) {
                    this.addField(existing, index, (UnqualifiedName)f.getUnqualifiedName(), f.getDataType());
                }
                return;
            } else {
                for (TableElement fc : query.$add()) {
                    if (fc instanceof Field) {
                        Field f = (Field)fc;
                        this.addField(existing, Integer.MAX_VALUE, (UnqualifiedName)fc.getUnqualifiedName(), f.getDataType());
                        continue;
                    }
                    if (!(fc instanceof ConstraintImpl)) throw Interpreter.unsupportedQuery(query);
                    ConstraintImpl c = (ConstraintImpl)fc;
                    this.addConstraint(query, c, existing);
                }
            }
            return;
        }
        if (query.$addColumn() != null) {
            if (Interpreter.find(existing.fields, query.$addColumn()) != null) {
                if (query.$ifNotExistsColumn()) return;
                throw Interpreter.alreadyExists(query.$addColumn());
            }
            UnqualifiedName name = (UnqualifiedName)query.$addColumn().getUnqualifiedName();
            DataType<?> dataType = query.$addColumnType();
            if (query.$addFirst()) {
                this.addField(existing, 0, name, dataType);
                return;
            } else if (query.$addBefore() != null) {
                this.addField(existing, Interpreter.indexOrFail(existing.fields, query.$addBefore()), name, dataType);
                return;
            } else if (query.$addAfter() != null) {
                this.addField(existing, Interpreter.indexOrFail(existing.fields, query.$addAfter()) + 1, name, dataType);
                return;
            } else {
                this.addField(existing, Integer.MAX_VALUE, name, dataType);
            }
            return;
        }
        if (query.$addConstraint() != null) {
            this.addConstraint(query, (ConstraintImpl)query.$addConstraint(), existing);
            return;
        }
        if (query.$alterColumn() != null) {
            MutableField existingField = Interpreter.find(existing.fields, query.$alterColumn());
            if (existingField == null) {
                if (query.$ifExistsColumn()) return;
                throw Interpreter.notExists(query.$alterColumn());
            }
            if (query.$alterColumnNullability() != null) {
                existingField.type = existingField.type.nullability(query.$alterColumnNullability());
                return;
            } else if (query.$alterColumnType() != null) {
                existingField.type = query.$alterColumnType().nullability(query.$alterColumnType().nullability() == Nullability.DEFAULT ? existingField.type.nullability() : query.$alterColumnType().nullability());
                return;
            } else if (query.$alterColumnDefault() != null) {
                existingField.type = existingField.type.default_(query.$alterColumnDefault());
                return;
            } else {
                if (!query.$alterColumnDropDefault()) throw Interpreter.unsupportedQuery(query);
                existingField.type = existingField.type.default_((Field)null);
            }
            return;
        }
        if (query.$renameTo() != null && Interpreter.checkNotExists(schema, query.$renameTo())) {
            existing.name((UnqualifiedName)query.$renameTo().getUnqualifiedName());
            return;
        }
        if (query.$renameColumn() != null) {
            MutableField mf = Interpreter.find(existing.fields, query.$renameColumn());
            if (mf == null) {
                throw Interpreter.notExists(query.$renameColumn());
            }
            if (Interpreter.find(existing.fields, query.$renameColumnTo()) != null) {
                throw Interpreter.alreadyExists(query.$renameColumnTo());
            }
            mf.name((UnqualifiedName)query.$renameColumnTo().getUnqualifiedName());
            return;
        }
        if (query.$renameConstraint() != null) {
            MutableConstraint mc = existing.constraint(query.$renameConstraint(), true);
            if (existing.constraint(query.$renameConstraintTo()) != null) {
                throw Interpreter.alreadyExists(query.$renameConstraintTo());
            }
            mc.name((UnqualifiedName)query.$renameConstraintTo().getUnqualifiedName());
            return;
        }
        if (query.$alterConstraint() != null) {
            existing.constraint((Constraint)query.$alterConstraint(), (boolean)true).enforced = query.$alterConstraintEnforced();
            return;
        }
        if (query.$dropColumns() != null) {
            List<MutableField> fields = existing.fields(query.$dropColumns().toArray(Tools.EMPTY_FIELD), false);
            if (fields.size() < query.$dropColumns().size() && !query.$ifExistsColumn()) {
                existing.fields(query.$dropColumns().toArray(Tools.EMPTY_FIELD), true);
            }
            this.dropColumns(existing, fields, query.$dropCascade());
            return;
        }
        if (query.$dropConstraint() != null) {
            Object key;
            ConstraintImpl impl = (ConstraintImpl)query.$dropConstraint();
            if (impl.getUnqualifiedName().empty()) {
                if (impl.$foreignKey() != null) {
                    throw new DataDefinitionException("Cannot drop unnamed foreign key");
                }
                if (impl.$check() != null) {
                    throw new DataDefinitionException("Cannot drop unnamed check constraint");
                }
                if (impl.$unique() != null) {
                    Iterator<MutableUniqueKey> iterator2 = existing.uniqueKeys.iterator();
                    while (iterator2.hasNext()) {
                        key = iterator2.next();
                        if (!((MutableKey)key).fieldsEquals(impl.$unique())) continue;
                        this.cascade((MutableUniqueKey)key, null, query.$dropCascade());
                        iterator2.remove();
                        return;
                    }
                }
            } else {
                Iterator<MutableForeignKey> iterator3 = existing.foreignKeys.iterator();
                while (iterator3.hasNext()) {
                    if (!iterator3.next().nameEquals((UnqualifiedName)impl.getUnqualifiedName())) continue;
                    iterator3.remove();
                    return;
                }
                if (query.$dropConstraintType() != ConstraintType.FOREIGN_KEY) {
                    Iterator<MutableUniqueKey> uks = existing.uniqueKeys.iterator();
                    while (uks.hasNext()) {
                        MutableUniqueKey key2 = uks.next();
                        if (!key2.nameEquals((UnqualifiedName)impl.getUnqualifiedName())) continue;
                        this.cascade(key2, null, query.$dropCascade());
                        uks.remove();
                        return;
                    }
                    Iterator<MutableCheck> chks = existing.checks.iterator();
                    while (chks.hasNext()) {
                        MutableCheck check2 = chks.next();
                        if (!check2.nameEquals((UnqualifiedName)impl.getUnqualifiedName())) continue;
                        chks.remove();
                        return;
                    }
                    if (existing.primaryKey != null && existing.primaryKey.nameEquals((UnqualifiedName)impl.getUnqualifiedName())) {
                        this.cascade(existing.primaryKey, null, query.$dropCascade());
                        existing.primaryKey = null;
                        return;
                    }
                }
            }
            Iterator<DelayedForeignKey> iterator4 = this.delayedForeignKeyDeclarations.iterator();
            while (iterator4.hasNext()) {
                key = iterator4.next();
                if (!existing.equals(((DelayedForeignKey)key).table) || !((DelayedForeignKey)key).constraint.getUnqualifiedName().equals(impl.getUnqualifiedName())) continue;
                iterator4.remove();
                return;
            }
            if (query.$ifExistsConstraint()) return;
            throw Interpreter.notExists(query.$dropConstraint());
        }
        if (query.$dropConstraintType() != ConstraintType.PRIMARY_KEY) throw Interpreter.unsupportedQuery(query);
        if (existing.primaryKey == null) throw Interpreter.primaryKeyNotExists(table);
        existing.primaryKey = null;
    }

    private final Iterable<Field<?>> assertFields(final Query query, final Iterable<TableElement> fields) {
        return () -> new Iterator<Field<?>>(){
            final Iterator<TableElement> it;
            {
                this.it = fields.iterator();
            }

            @Override
            public boolean hasNext() {
                return this.it.hasNext();
            }

            @Override
            public Field<?> next() {
                TableElement next = this.it.next();
                if (next instanceof Field) {
                    Field f = (Field)next;
                    return f;
                }
                throw Interpreter.unsupportedQuery(query);
            }

            @Override
            public void remove() {
                this.it.remove();
            }
        };
    }

    private final void addField(MutableTable existing, int index, UnqualifiedName name, DataType<?> dataType) {
        MutableField field2 = new MutableField(name, existing, dataType);
        for (MutableField mf : existing.fields) {
            if (mf.nameEquals(field2.name())) {
                throw Interpreter.columnAlreadyExists(field2.qualifiedName());
            }
            if (!mf.type.identity() || !dataType.identity()) continue;
            throw new DataDefinitionException("Table can only have one identity: " + String.valueOf(mf.qualifiedName()));
        }
        if (index == Integer.MAX_VALUE) {
            existing.fields.add(field2);
        } else {
            existing.fields.add(index, field2);
        }
    }

    private final void addConstraint(Query query, ConstraintImpl impl, MutableTable existing) {
        if (!impl.getUnqualifiedName().empty() && existing.constraint(impl) != null) {
            throw Interpreter.alreadyExists(impl);
        }
        if (impl.$primaryKey() != null) {
            if (existing.primaryKey != null) {
                throw Interpreter.alreadyExists(impl);
            }
            existing.primaryKey = new MutableUniqueKey((UnqualifiedName)impl.getUnqualifiedName(), existing, existing.fields(impl.$primaryKey(), true), impl.$enforced());
        } else if (impl.$unique() != null) {
            existing.uniqueKeys.add(new MutableUniqueKey((UnqualifiedName)impl.getUnqualifiedName(), existing, existing.fields(impl.$unique(), true), impl.$enforced()));
        } else if (impl.$foreignKey() != null) {
            this.addForeignKey(existing, impl);
        } else if (impl.$check() != null) {
            existing.checks.add(new MutableCheck((UnqualifiedName)impl.getUnqualifiedName(), existing, impl.$check(), impl.$enforced()));
        } else {
            throw Interpreter.unsupportedQuery(query);
        }
    }

    private final void accept0(DropTableImpl query) {
        Table<?> table = query.$table();
        MutableSchema schema = this.getSchema(table.getSchema());
        MutableTable existing = schema.table(table);
        if (existing == null) {
            if (!query.$ifExists()) {
                throw Interpreter.notExists(table);
            }
            return;
        }
        if (!existing.options.type().isTable()) {
            throw Interpreter.objectNotTable(table);
        }
        if (query.$temporary() && existing.options.type() != TableOptions.TableType.TEMPORARY) {
            throw Interpreter.objectNotTemporaryTable(table);
        }
        this.drop(schema.tables, existing, query.$cascade());
    }

    private final void accept0(TruncateImpl<?> query) {
        Table<?> table = query.$table();
        MutableSchema schema = this.getSchema(table.getSchema());
        MutableTable existing = schema.table(table);
        if (existing == null) {
            throw Interpreter.notExists(table);
        }
        if (!existing.options.type().isTable()) {
            throw Interpreter.objectNotTable(table);
        }
        if (query.$cascade() != QOM.Cascade.CASCADE && existing.hasReferencingKeys()) {
            throw new DataDefinitionException("Cannot truncate table referenced by other tables. Use CASCADE: " + String.valueOf(table));
        }
    }

    private final void accept0(CreateViewImpl<?> query) {
        Table<?> table = query.$view();
        MutableSchema schema = this.getSchema(table.getSchema(), true);
        MutableTable existing = schema.table(table);
        if (existing != null) {
            if (!existing.options.type().isView()) {
                throw Interpreter.objectNotView(table);
            }
            if (query.$orReplace()) {
                this.drop(schema.tables, existing, QOM.Cascade.RESTRICT);
            } else {
                if (!query.$ifNotExists()) {
                    throw Interpreter.viewAlreadyExists(table);
                }
                return;
            }
        }
        this.newTable(table, schema, query.$fields(), query.$select(), null, TableOptions.view(query.$select()));
    }

    private final void accept0(AlterViewImpl query) {
        Table<?> table = query.$view();
        MutableSchema schema = this.getSchema(table.getSchema());
        MutableTable existing = schema.table(table);
        if (existing == null) {
            if (!query.$ifExists()) {
                throw Interpreter.viewNotExists(table);
            }
            return;
        }
        if (!existing.options.type().isView()) {
            throw Interpreter.objectNotView(table);
        }
        if (query.$renameTo() != null && Interpreter.checkNotExists(schema, query.$renameTo())) {
            existing.name((UnqualifiedName)query.$renameTo().getUnqualifiedName());
        } else if (query.$as() != null) {
            this.initTable(existing, query.$fields(), query.$as(), TableOptions.view(query.$as()));
        } else {
            throw Interpreter.unsupportedQuery(query);
        }
    }

    private final void accept0(DropViewImpl query) {
        Table<?> table = query.$view();
        MutableSchema schema = this.getSchema(table.getSchema());
        MutableTable existing = schema.table(table);
        if (existing == null) {
            if (!query.$ifExists()) {
                throw Interpreter.viewNotExists(table);
            }
            return;
        }
        if (!existing.options.type().isView()) {
            throw Interpreter.objectNotView(table);
        }
        this.drop(schema.tables, existing, QOM.Cascade.RESTRICT);
    }

    private final void accept0(CreateSequenceImpl query) {
        Sequence<?> sequence2 = query.$sequence();
        MutableSchema schema = this.getSchema(sequence2.getSchema(), true);
        MutableSequence existing = schema.sequence(sequence2);
        if (existing != null) {
            if (!query.$ifNotExists()) {
                throw Interpreter.alreadyExists(sequence2);
            }
            return;
        }
        MutableSequence ms = new MutableSequence((UnqualifiedName)sequence2.getUnqualifiedName(), schema);
        ms.startWith = query.$startWith();
        ms.incrementBy = query.$incrementBy();
        ms.minvalue = query.$noMinvalue() ? null : query.$minvalue();
        ms.maxvalue = query.$noMaxvalue() ? null : query.$maxvalue();
        ms.cycle = query.$cycle() == QOM.CycleOption.CYCLE;
        ms.cache = query.$noCache() ? null : query.$cache();
    }

    private final void accept0(AlterSequenceImpl<?> query) {
        Sequence<?> sequence2 = query.$sequence();
        MutableSchema schema = this.getSchema(sequence2.getSchema());
        MutableSequence existing = schema.sequence(sequence2);
        if (existing == null) {
            if (!query.$ifExists()) {
                throw Interpreter.notExists(sequence2);
            }
            return;
        }
        if (query.$renameTo() != null) {
            Sequence<?> renameTo = query.$renameTo();
            if (schema.sequence(renameTo) != null) {
                throw Interpreter.alreadyExists(renameTo);
            }
            existing.name((UnqualifiedName)renameTo.getUnqualifiedName());
        } else {
            boolean seen = false;
            if (query.$startWith() != null && (seen |= true)) {
                existing.startWith = query.$startWith();
            }
            if (query.$incrementBy() != null && (seen |= true)) {
                existing.incrementBy = query.$incrementBy();
            }
            if (query.$minvalue() != null && (seen |= true)) {
                existing.minvalue = query.$minvalue();
            } else if (query.$noMinvalue() && (seen |= true)) {
                existing.minvalue = null;
            }
            if (query.$maxvalue() != null && (seen |= true)) {
                existing.maxvalue = query.$maxvalue();
            } else if (query.$noMaxvalue() && (seen |= true)) {
                existing.maxvalue = null;
            }
            QOM.CycleOption cycle = query.$cycle();
            if (cycle != null && (seen |= true)) {
                boolean bl = existing.cycle = cycle == QOM.CycleOption.CYCLE;
            }
            if (query.$cache() != null && (seen |= true)) {
                existing.cache = query.$cache();
            } else if (query.$noCache() && (seen |= true)) {
                existing.cache = null;
            }
            if (!query.$restart() && query.$restartWith() == null || (seen |= true)) {
                // empty if block
            }
            if (!seen) {
                throw Interpreter.unsupportedQuery(query);
            }
        }
    }

    private final void accept0(DropSequenceImpl query) {
        Sequence<?> sequence2 = query.$sequence();
        MutableSchema schema = this.getSchema(sequence2.getSchema());
        MutableSequence existing = schema.sequence(sequence2);
        if (existing == null) {
            if (!query.$ifExists()) {
                throw Interpreter.notExists(sequence2);
            }
            return;
        }
        schema.sequences.remove(existing);
    }

    private final void accept0(CreateIndexImpl query) {
        Index index = query.$index();
        Table<?> table = query.$table();
        MutableSchema schema = this.getSchema(table.getSchema());
        MutableTable mt = schema.table(table);
        if (mt == null) {
            throw Interpreter.notExists(table);
        }
        MutableIndex existing = Interpreter.find(mt.indexes, (Named)index);
        List<MutableSortField> mtf = mt.sortFields(query.$on());
        if (existing != null) {
            if (!query.$ifNotExists()) {
                throw Interpreter.alreadyExists(index);
            }
            return;
        }
        mt.indexes.add(new MutableIndex((UnqualifiedName)index.getUnqualifiedName(), mt, mtf, query.$unique(), query.$where()));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final void accept0(AlterIndexImpl query) {
        Table<?> table;
        Index index = query.$index();
        MutableIndex existing = this.index(index, table = query.$on() != null ? query.$on() : index.getTable(), query.$ifExists(), true);
        if (existing == null) return;
        if (query.$renameTo() == null) throw Interpreter.unsupportedQuery(query);
        if (this.index(query.$renameTo(), table, false, false) != null) throw Interpreter.alreadyExists(query.$renameTo());
        existing.name((UnqualifiedName)query.$renameTo().getUnqualifiedName());
    }

    private final void accept0(DropIndexImpl query) {
        Table<?> table;
        Index index = query.$index();
        MutableIndex existing = this.index(index, table = query.$on() != null ? query.$on() : index.getTable(), query.$ifExists(), true);
        if (existing != null) {
            existing.table.indexes.remove(existing);
        }
    }

    private final void accept0(CreateDomainImpl<?> query) {
        Domain<?> domain = query.$domain();
        MutableSchema schema = this.getSchema(domain.getSchema(), true);
        MutableDomain existing = schema.domain(domain);
        if (existing != null) {
            if (!query.$ifNotExists()) {
                throw Interpreter.alreadyExists(domain);
            }
            return;
        }
        MutableDomain md = new MutableDomain((UnqualifiedName)domain.getUnqualifiedName(), schema, query.$dataType());
        if (query.$default_() != null) {
            md.dataType = md.dataType.default_(query.$default_());
        }
        if (query.$constraints() != null) {
            for (Constraint constraint : query.$constraints()) {
                if (((ConstraintImpl)constraint).$check() == null) continue;
                md.checks.add(new MutableCheck(constraint));
            }
        }
    }

    private final void accept0(AlterDomainImpl<?> query) {
        Domain<?> domain = query.$domain();
        MutableSchema schema = this.getSchema(domain.getSchema());
        MutableDomain existing = schema.domain(domain);
        if (existing == null) {
            if (!query.$ifExists()) {
                throw Interpreter.notExists(domain);
            }
            return;
        }
        if (query.$addConstraint() != null) {
            Constraint addConstraint = query.$addConstraint();
            if (Interpreter.find(existing.checks, (Named)addConstraint) != null) {
                throw Interpreter.alreadyExists(addConstraint);
            }
            existing.checks.add(new MutableCheck(addConstraint));
        } else if (query.$dropConstraint() != null) {
            Constraint dropConstraint = query.$dropConstraint();
            MutableCheck mc = Interpreter.find(existing.checks, (Named)dropConstraint);
            if (mc == null) {
                if (!query.$dropConstraintIfExists()) {
                    throw Interpreter.notExists(dropConstraint);
                }
                return;
            }
            existing.checks.remove(mc);
        } else if (query.$renameTo() != null) {
            Domain<?> renameTo = query.$renameTo();
            if (schema.domain(renameTo) != null) {
                throw Interpreter.alreadyExists(renameTo);
            }
            existing.name((UnqualifiedName)renameTo.getUnqualifiedName());
        } else if (query.$renameConstraint() != null) {
            Constraint renameConstraint = query.$renameConstraint();
            Constraint renameConstraintTo = query.$renameConstraintTo();
            MutableCheck mc = Interpreter.find(existing.checks, (Named)renameConstraint);
            if (mc == null) {
                if (!query.$renameConstraintIfExists()) {
                    throw Interpreter.notExists(renameConstraint);
                }
                return;
            }
            if (Interpreter.find(existing.checks, (Named)renameConstraintTo) != null) {
                throw Interpreter.alreadyExists(renameConstraintTo);
            }
            mc.name((UnqualifiedName)renameConstraintTo.getUnqualifiedName());
        } else if (query.$setDefault() != null) {
            existing.dataType = existing.dataType.defaultValue(query.$setDefault());
        } else if (query.$dropDefault()) {
            existing.dataType = existing.dataType.defaultValue((Field)null);
        } else {
            throw Interpreter.unsupportedQuery(query);
        }
    }

    private final void accept0(DropDomainImpl query) {
        Domain<?> domain = query.$domain();
        MutableSchema schema = this.getSchema(domain.getSchema());
        MutableDomain existing = schema.domain(domain);
        if (existing == null) {
            if (!query.$ifExists()) {
                throw Interpreter.notExists(domain);
            }
            return;
        }
        if (query.$cascade() != QOM.Cascade.CASCADE && !existing.fields.isEmpty()) {
            throw new DataDefinitionException("Domain " + String.valueOf(domain.getQualifiedName()) + " is still being referenced by fields.");
        }
        ArrayList<MutableField> field2 = new ArrayList<MutableField>(existing.fields);
        for (MutableField mf : field2) {
            this.dropColumns(mf.table, existing.fields, QOM.Cascade.CASCADE);
        }
        schema.domains.remove(existing);
    }

    private final void accept0(CommentOnImpl query) {
        if (query.$table() != null) {
            this.table(query.$table()).comment(query.$comment());
        } else if (query.$field() != null) {
            this.field(query.$field()).comment(query.$comment());
        } else {
            throw Interpreter.unsupportedQuery(query);
        }
    }

    private final void accept0(SetSchema query) {
        MutableSchema schema = this.getSchema(query.$schema());
        if (schema == null) {
            throw Interpreter.notExists(query.$schema());
        }
        this.currentSchema = schema;
    }

    private final void accept0(SetCommand query) {
        if ("foreign_key_checks".equals(query.$name().last().toLowerCase(this.locale))) {
            boolean bl = this.delayForeignKeyDeclarations = Convert.convert(query.$value().getValue(), Boolean.TYPE) == false;
            if (!this.delayForeignKeyDeclarations) {
                this.applyDelayedForeignKeys();
            }
        } else {
            throw Interpreter.unsupportedQuery(query);
        }
    }

    private static final DataDefinitionException unsupportedQuery(Query query) {
        return new DataDefinitionException("Unsupported query: " + query.getSQL());
    }

    private static final DataDefinitionException schemaNotEmpty(Schema schema) {
        return new DataDefinitionException("Schema is not empty: " + String.valueOf(schema.getQualifiedName()));
    }

    private static final DataDefinitionException objectNotTable(Table<?> table) {
        return new DataDefinitionException("Object is not a table: " + String.valueOf(table.getQualifiedName()));
    }

    private static final DataDefinitionException objectNotTemporaryTable(Table<?> table) {
        return new DataDefinitionException("Object is not a temporary table: " + String.valueOf(table.getQualifiedName()));
    }

    private static final DataDefinitionException objectNotView(Table<?> table) {
        return new DataDefinitionException("Object is not a view: " + String.valueOf(table.getQualifiedName()));
    }

    private static final DataDefinitionException viewNotExists(Table<?> view) {
        return new DataDefinitionException("View does not exist: " + String.valueOf(view.getQualifiedName()));
    }

    private static final DataDefinitionException viewAlreadyExists(Table<?> view) {
        return new DataDefinitionException("View already exists: " + String.valueOf(view.getQualifiedName()));
    }

    private static final DataDefinitionException columnAlreadyExists(Name name) {
        return new DataDefinitionException("Column already exists: " + String.valueOf(name));
    }

    private static final DataDefinitionException notExists(Named named) {
        return new DataDefinitionException(named.getClass().getSimpleName() + " does not exist: " + String.valueOf(named.getQualifiedName()));
    }

    private static final DataDefinitionException alreadyExists(Named named) {
        return new DataDefinitionException(named.getClass().getSimpleName() + " already exists: " + String.valueOf(named.getQualifiedName()));
    }

    private static final DataDefinitionException primaryKeyNotExists(Named named) {
        return new DataDefinitionException("Primary key does not exist on table: " + String.valueOf(named));
    }

    private final Iterable<MutableTable> tables() {
        ArrayList<MutableTable> result = new ArrayList<MutableTable>();
        for (MutableCatalog catalog : this.catalogs.values()) {
            for (MutableSchema schema : catalog.schemas) {
                result.addAll(schema.tables);
            }
        }
        return result;
    }

    private final MutableSchema getSchema(Schema input) {
        return this.getSchema(input, false);
    }

    private final MutableSchema getSchema(Schema input, boolean create) {
        Name catalogName;
        if (input == null) {
            return this.currentSchema(create);
        }
        MutableCatalog catalog = this.defaultCatalog;
        if (input.getCatalog() != null && (catalog = this.catalogs.get(catalogName = input.getCatalog().getUnqualifiedName())) == null && create) {
            catalog = new MutableCatalog((UnqualifiedName)catalogName);
            this.catalogs.put(catalogName, catalog);
        }
        if (catalog == null) {
            return null;
        }
        MutableSchema schema = this.defaultSchema;
        schema = Interpreter.find(catalog.schemas, (Named)input);
        if (schema == null && create) {
            schema = new MutableSchema((UnqualifiedName)input.getUnqualifiedName(), catalog);
        }
        return schema;
    }

    private final MutableSchema currentSchema(boolean create) {
        if (this.currentSchema == null) {
            this.currentSchema = this.getInterpreterSearchPathSchema(create);
        }
        return this.currentSchema;
    }

    private final MutableSchema getInterpreterSearchPathSchema(boolean create) {
        List<InterpreterSearchSchema> searchPath = this.configuration.settings().getInterpreterSearchPath();
        if (searchPath.isEmpty()) {
            return this.defaultSchema;
        }
        InterpreterSearchSchema schema = searchPath.get(0);
        return this.getSchema(DSL.schema(DSL.name(schema.getCatalog(), schema.getSchema())), create);
    }

    private final MutableTable newTable(Table<?> table, MutableSchema schema, List<? extends Field<?>> columns, Select<?> select, Comment comment, TableOptions options) {
        return this.initTable(new MutableTable((UnqualifiedName)table.getUnqualifiedName(), schema, comment, options), columns, select, options);
    }

    private final MutableTable initTable(MutableTable t2, List<? extends Field<?>> columns, Select<?> select, TableOptions options) {
        block3: {
            block2: {
                t2.fields.clear();
                t2.options = options;
                if (columns.isEmpty()) break block2;
                for (int i = 0; i < columns.size(); ++i) {
                    this.addField(t2, Integer.MAX_VALUE, (UnqualifiedName)columns.get(i).getUnqualifiedName(), columns.get(i).getDataType());
                }
                break block3;
            }
            if (select == null) break block3;
            for (Field<?> column : FieldsImpl.fieldsRow0((FieldsTrait)((Object)select)).fields()) {
                this.addField(t2, Integer.MAX_VALUE, (UnqualifiedName)column.getUnqualifiedName(), column.getDataType());
            }
        }
        return t2;
    }

    private final MutableTable table(Table<?> table) {
        return this.table(table, true);
    }

    private final MutableTable table(Table<?> table, boolean throwIfNotExists) {
        MutableTable result = this.getSchema(table.getSchema()).table(table);
        if (result == null && throwIfNotExists) {
            throw Interpreter.notExists(table);
        }
        return result;
    }

    private final MutableIndex index(Index index, Table<?> table, boolean ifExists, boolean throwIfNotExists) {
        MutableTable mt = null;
        MutableIndex mi = null;
        if (table != null) {
            MutableSchema ms = this.getSchema(table.getSchema());
            mt = ms.table(table);
        } else {
            for (MutableTable mt1 : this.tables()) {
                mi = Interpreter.find(mt1.indexes, (Named)index);
                if (mi == null) continue;
                mt = mt1;
                MutableSchema ms = mt1.schema;
                break;
            }
        }
        if (mt != null) {
            mi = Interpreter.find(mt.indexes, (Named)index);
        } else if (table != null && throwIfNotExists) {
            throw Interpreter.notExists(table);
        }
        if (mi == null && !ifExists && throwIfNotExists) {
            throw Interpreter.notExists(index);
        }
        return mi;
    }

    private static final boolean checkNotExists(MutableSchema schema, Table<?> table) {
        MutableTable mt = schema.table(table);
        if (mt != null) {
            throw Interpreter.alreadyExists(table, mt);
        }
        return true;
    }

    private static final DataDefinitionException alreadyExists(Table<?> t2, MutableTable mt) {
        if (mt.options.type().isView()) {
            return Interpreter.viewAlreadyExists(t2);
        }
        return Interpreter.alreadyExists(t2);
    }

    private final MutableField field(Field<?> field2) {
        return this.field(field2, true);
    }

    private final MutableField field(Field<?> field2, boolean throwIfNotExists) {
        MutableTable table = this.table(DSL.table(field2.getQualifiedName().qualifier()), throwIfNotExists);
        if (table == null) {
            return null;
        }
        MutableField result = Interpreter.find(table.fields, field2);
        if (result == null && throwIfNotExists) {
            throw Interpreter.notExists(field2);
        }
        return result;
    }

    private static final <M extends MutableNamed> M find(M m4, UnqualifiedName name) {
        if (m4 == null) {
            return null;
        }
        if (m4.nameEquals(name)) {
            return m4;
        }
        return null;
    }

    private static final <M extends MutableNamed> M find(M m4, Named named) {
        return Interpreter.find(m4, (UnqualifiedName)named.getUnqualifiedName());
    }

    private static final <M extends MutableNamed> M find(List<? extends M> list, Named named) {
        UnqualifiedName n = (UnqualifiedName)named.getUnqualifiedName();
        for (MutableNamed m4 : list) {
            if ((m4 = Interpreter.find(m4, n)) == null) continue;
            return (M)m4;
        }
        return null;
    }

    private static final int indexOrFail(List<? extends MutableNamed> list, Named named) {
        int result = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (!list.get(i).nameEquals((UnqualifiedName)named.getUnqualifiedName())) continue;
            result = i;
            break;
        }
        if (result == -1) {
            throw Interpreter.notExists(named);
        }
        return result;
    }

    private static final InterpreterNameLookupCaseSensitivity caseSensitivity(Configuration configuration) {
        InterpreterNameLookupCaseSensitivity result = StringUtils.defaultIfNull(configuration.settings().getInterpreterNameLookupCaseSensitivity(), InterpreterNameLookupCaseSensitivity.DEFAULT);
        if (result == InterpreterNameLookupCaseSensitivity.DEFAULT) {
            switch (StringUtils.defaultIfNull(configuration.settings().getInterpreterDialect(), configuration.family()).family()) {
                case ASE: {
                    return InterpreterNameLookupCaseSensitivity.ALWAYS;
                }
                case AURORA_MYSQL: 
                case MEMSQL: 
                case REDSHIFT: 
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: 
                case SYBASE: 
                case MARIADB: 
                case MYSQL: 
                case SQLITE: {
                    return InterpreterNameLookupCaseSensitivity.NEVER;
                }
            }
            return InterpreterNameLookupCaseSensitivity.WHEN_QUOTED;
        }
        return result;
    }

    public String toString() {
        return this.meta().toString();
    }

    private final class MutableCatalog
    extends MutableNamed {
        List<MutableSchema> schemas;

        MutableCatalog(UnqualifiedName name) {
            super(name, null);
            this.schemas = new MutableNamedList<MutableSchema>();
        }

        @Override
        final void onDrop() {
            this.schemas.clear();
        }

        @Override
        final MutableNamed parent() {
            return null;
        }

        final InterpretedCatalog interpretedCatalog() {
            return Interpreter.this.interpretedCatalogs.computeIfAbsent(this.qualifiedName(), n -> new InterpretedCatalog());
        }

        private final class InterpretedCatalog
        extends CatalogImpl {
            InterpretedCatalog() {
                super(MutableCatalog.this.name(), MutableCatalog.this.comment());
            }

            @Override
            public final List<Schema> getSchemas() {
                return Tools.map(MutableCatalog.this.schemas, s2 -> s2.interpretedSchema());
            }
        }
    }

    private final class MutableSchema
    extends MutableNamed {
        MutableCatalog catalog;
        List<MutableTable> tables;
        List<MutableDomain> domains;
        List<MutableSequence> sequences;

        MutableSchema(UnqualifiedName name, MutableCatalog catalog) {
            super(name);
            this.tables = new MutableNamedList<MutableTable>();
            this.domains = new MutableNamedList<MutableDomain>();
            this.sequences = new MutableNamedList<MutableSequence>();
            this.catalog = catalog;
            this.catalog.schemas.add(this);
        }

        @Override
        final void onDrop() {
            for (MutableTable table : this.tables) {
                for (MutableForeignKey referencingKey : table.referencingKeys()) {
                    referencingKey.table.foreignKeys.remove(referencingKey);
                }
            }
            this.tables.clear();
            this.domains.clear();
            this.sequences.clear();
        }

        @Override
        final MutableNamed parent() {
            return this.catalog;
        }

        final InterpretedSchema interpretedSchema() {
            return Interpreter.this.interpretedSchemas.computeIfAbsent(this.qualifiedName(), n -> new InterpretedSchema(this.catalog.interpretedCatalog()));
        }

        final boolean isEmpty() {
            return this.tables.isEmpty();
        }

        final MutableTable table(Named t2) {
            return Interpreter.find(this.tables, t2);
        }

        final MutableDomain domain(Named d) {
            return Interpreter.find(this.domains, d);
        }

        final MutableSequence sequence(Named s2) {
            return Interpreter.find(this.sequences, s2);
        }

        private final class InterpretedSchema
        extends SchemaImpl {
            InterpretedSchema(MutableCatalog.InterpretedCatalog catalog) {
                super(MutableSchema.this.name(), (Catalog)catalog, MutableSchema.this.comment());
            }

            @Override
            public final List<Table<?>> getTables() {
                return Tools.map(MutableSchema.this.tables, t2 -> t2.interpretedTable());
            }

            @Override
            public final List<Domain<?>> getDomains() {
                return Tools.map(MutableSchema.this.domains, d -> d.interpretedDomain());
            }

            @Override
            public final List<Sequence<?>> getSequences() {
                return Tools.map(MutableSchema.this.sequences, s2 -> s2.interpretedSequence());
            }
        }
    }

    private final class MutableTable
    extends MutableNamed {
        MutableSchema schema;
        List<MutableField> fields;
        MutableUniqueKey primaryKey;
        List<MutableUniqueKey> uniqueKeys;
        List<MutableForeignKey> foreignKeys;
        List<MutableCheck> checks;
        List<MutableIndex> indexes;
        TableOptions options;

        MutableTable(UnqualifiedName name, MutableSchema schema, Comment comment, TableOptions options) {
            super(name, comment);
            this.fields = new MutableNamedList<MutableField>();
            this.uniqueKeys = new MutableNamedList<MutableUniqueKey>();
            this.foreignKeys = new MutableNamedList<MutableForeignKey>();
            this.checks = new MutableNamedList<MutableCheck>();
            this.indexes = new MutableNamedList<MutableIndex>();
            this.schema = schema;
            this.options = options;
            schema.tables.add(this);
        }

        @Override
        final void onDrop() {
            if (this.primaryKey != null) {
                this.primaryKey.onDrop();
            }
            this.uniqueKeys.clear();
            this.foreignKeys.clear();
            this.checks.clear();
            this.indexes.clear();
            this.fields.clear();
        }

        @Override
        final MutableNamed parent() {
            return this.schema;
        }

        final InterpretedTable interpretedTable() {
            return Interpreter.this.interpretedTables.computeIfAbsent(this.qualifiedName(), n -> new InterpretedTable(this.schema.interpretedSchema()));
        }

        boolean hasReferencingKeys() {
            if (this.primaryKey != null && !this.primaryKey.referencingKeys.isEmpty()) {
                return true;
            }
            return Tools.anyMatch(this.uniqueKeys, uk -> !uk.referencingKeys.isEmpty());
        }

        List<MutableForeignKey> referencingKeys() {
            ArrayList<MutableForeignKey> result = new ArrayList<MutableForeignKey>();
            if (this.primaryKey != null) {
                result.addAll(this.primaryKey.referencingKeys);
            }
            for (MutableUniqueKey uk : this.uniqueKeys) {
                result.addAll(uk.referencingKeys);
            }
            return result;
        }

        final MutableConstraint constraint(Constraint constraint, boolean failIfNotFound) {
            MutableConstraint result = Interpreter.find(this.foreignKeys, (Named)constraint);
            if (result != null) {
                return result;
            }
            result = Interpreter.find(this.uniqueKeys, (Named)constraint);
            if (result != null) {
                return result;
            }
            result = Interpreter.find(this.checks, (Named)constraint);
            if (result != null) {
                return result;
            }
            result = Interpreter.find(this.primaryKey, (Named)constraint);
            if (result != null) {
                return result;
            }
            if (failIfNotFound) {
                throw Interpreter.notExists(constraint);
            }
            return null;
        }

        final MutableNamed constraint(Constraint constraint) {
            return this.constraint(constraint, false);
        }

        final List<MutableField> fields(Field<?>[] fs, boolean failIfNotFound) {
            ArrayList<MutableField> result = new ArrayList<MutableField>();
            for (Field<?> f : fs) {
                MutableField mf = Interpreter.find(this.fields, f);
                if (mf != null) {
                    result.add(mf);
                    continue;
                }
                if (!failIfNotFound) continue;
                throw new DataDefinitionException("Field does not exist in table: " + String.valueOf(f.getQualifiedName()));
            }
            return result;
        }

        final List<MutableSortField> sortFields(Collection<? extends OrderField<?>> ofs) {
            return Tools.map(ofs, of -> {
                SortField sf = Tools.sortField(of);
                MutableField mf = Interpreter.find(this.fields, sf.$field());
                if (mf == null) {
                    throw new DataDefinitionException("Field does not exist in table: " + String.valueOf(sf.$field().getQualifiedName()));
                }
                return new MutableSortField(mf, sf.$sortOrder());
            });
        }

        final MutableUniqueKey uniqueKey(List<MutableField> mrfs) {
            HashSet<MutableField> set = new HashSet<MutableField>(mrfs);
            if (this.primaryKey != null && set.equals(new HashSet(this.primaryKey.fields))) {
                return this.primaryKey;
            }
            return Tools.findAny(this.uniqueKeys, mu -> set.equals(new HashSet(mu.fields)));
        }

        private final class InterpretedTable
        extends TableImpl<Record> {
            InterpretedTable(MutableSchema.InterpretedSchema schema) {
                super(MutableTable.this.name(), schema, null, null, null, null, MutableTable.this.comment(), MutableTable.this.options);
                for (MutableField field2 : MutableTable.this.fields) {
                    this.createField(field2.name(), field2.type, field2.comment() != null ? field2.comment().getComment() : null);
                }
            }

            @Override
            public final UniqueKey<Record> getPrimaryKey() {
                return MutableTable.this.primaryKey != null ? MutableTable.this.primaryKey.interpretedKey() : null;
            }

            @Override
            public final List<UniqueKey<Record>> getUniqueKeys() {
                return Tools.map(MutableTable.this.uniqueKeys, uk -> uk.interpretedKey());
            }

            @Override
            public List<ForeignKey<Record, ?>> getReferences() {
                return Tools.map(MutableTable.this.foreignKeys, fk -> fk.interpretedKey());
            }

            @Override
            public List<Check<Record>> getChecks() {
                return Tools.map(MutableTable.this.checks, c -> new CheckImpl<Record>(this, c.name(), c.condition, c.enforced));
            }

            @Override
            public final List<Index> getIndexes() {
                return Tools.map(MutableTable.this.indexes, i -> i.interpretedIndex());
            }
        }
    }

    private final class MutableIndex
    extends MutableNamed {
        MutableTable table;
        List<MutableSortField> fields;
        boolean unique;
        Condition where;

        MutableIndex(UnqualifiedName name, MutableTable table, List<MutableSortField> fields, boolean unique, Condition where) {
            super(name);
            this.table = table;
            this.fields = fields;
            this.unique = unique;
            this.where = where;
        }

        @Override
        final void onDrop() {
        }

        @Override
        final MutableNamed parent() {
            return this.table;
        }

        @Override
        final Name qualifiedName() {
            return super.qualifiedName();
        }

        final Index interpretedIndex() {
            Name qualifiedName = this.qualifiedName();
            Index result = Interpreter.this.interpretedIndexes.get(qualifiedName);
            if (result == null) {
                MutableTable.InterpretedTable t2 = this.table.interpretedTable();
                result = new IndexImpl(this.name(), t2, Tools.map(this.fields, msf -> t2.field(msf.name()).sort(msf.sort), SortField[]::new), this.where, this.unique);
                Interpreter.this.interpretedIndexes.put(qualifiedName, result);
            }
            return result;
        }
    }

    private static class DelayedForeignKey {
        final MutableTable table;
        final ConstraintImpl constraint;

        DelayedForeignKey(MutableTable mt, ConstraintImpl constraint) {
            this.table = mt;
            this.constraint = constraint;
        }
    }

    private final class MutableUniqueKey
    extends MutableKey {
        List<MutableForeignKey> referencingKeys;

        MutableUniqueKey(UnqualifiedName name, MutableTable table, List<MutableField> fields, boolean enforced) {
            super(name, table, fields, enforced);
            this.referencingKeys = new MutableNamedList<MutableForeignKey>();
        }

        @Override
        final void onDrop() {
            this.referencingKeys.clear();
        }

        @Override
        final Name qualifiedName() {
            if (this.name().empty()) {
                return super.qualifiedName().append(this.fields.toString());
            }
            return super.qualifiedName();
        }

        final UniqueKeyImpl<Record> interpretedKey() {
            Name qualifiedName = this.qualifiedName();
            UniqueKeyImpl<Record> result = Interpreter.this.interpretedUniqueKeys.get(qualifiedName);
            if (result == null) {
                MutableTable.InterpretedTable t2 = this.table.interpretedTable();
                result = new UniqueKeyImpl<Record>(t2, this.name(), Tools.map(this.fields, f -> (TableField)t2.field(f.name()), TableField[]::new), this.enforced);
                Interpreter.this.interpretedUniqueKeys.put(qualifiedName, result);
                for (MutableForeignKey referencingKey : this.referencingKeys) {
                    result.references.add(referencingKey.interpretedKey());
                }
            }
            return result;
        }
    }

    private final class MutableForeignKey
    extends MutableKey {
        MutableUniqueKey referencedKey;
        List<MutableField> referencedFields;
        ConstraintImpl.Action onDelete;
        ConstraintImpl.Action onUpdate;

        MutableForeignKey(UnqualifiedName name, MutableTable table, List<MutableField> fields, MutableUniqueKey referencedKey, List<MutableField> referencedFields, ConstraintImpl.Action onDelete, ConstraintImpl.Action onUpdate, boolean enforced) {
            super(name, table, fields, enforced);
            this.referencedKey = referencedKey;
            this.referencedKey.referencingKeys.add(this);
            this.referencedFields = referencedFields;
            this.onDelete = onDelete;
            this.onUpdate = onUpdate;
        }

        @Override
        final void onDrop() {
            this.referencedKey.referencingKeys.remove(this);
        }

        @Override
        final Name qualifiedName() {
            if (this.name().empty()) {
                return super.qualifiedName().append(this.referencedKey.qualifiedName());
            }
            return super.qualifiedName();
        }

        final ForeignKey<Record, ?> interpretedKey() {
            Name qualifiedName = this.qualifiedName();
            ReferenceImpl<Record, Object> result = Interpreter.this.interpretedForeignKeys.get(qualifiedName);
            if (result == null) {
                MutableTable.InterpretedTable t2 = this.table.interpretedTable();
                UniqueKeyImpl<Record> uk = this.referencedKey.interpretedKey();
                result = new ReferenceImpl<Record, Record>(t2, this.name(), Tools.map(this.fields, f -> (TableField)t2.field(f.name()), TableField[]::new), uk, Tools.map(this.referencedFields, f -> (TableField)uk.getTable().field(f.name()), TableField[]::new), this.enforced);
                Interpreter.this.interpretedForeignKeys.put(qualifiedName, result);
            }
            return result;
        }
    }

    private final class MutableSortField
    extends MutableNamed {
        MutableField field;
        SortOrder sort;

        MutableSortField(MutableField field2, SortOrder sort) {
            super(field2.name());
            this.field = field2;
            this.sort = sort;
        }

        @Override
        final void onDrop() {
        }

        @Override
        final MutableNamed parent() {
            return this.field.parent();
        }
    }

    private final class MutableField
    extends MutableNamed {
        MutableTable table;
        DataType<?> type;
        MutableDomain domain;

        MutableField(UnqualifiedName name, MutableTable table, DataType<?> type) {
            super(name);
            this.table = table;
            this.type = type;
            this.domain = table.schema.domain(type);
            if (this.domain != null) {
                this.domain.fields.add(this);
            }
        }

        @Override
        final void onDrop() {
            if (this.domain != null) {
                this.domain.fields.remove(this);
            }
        }

        @Override
        final MutableNamed parent() {
            return this.table;
        }
    }

    private abstract class MutableKey
    extends MutableConstraint {
        List<MutableField> fields;

        MutableKey(UnqualifiedName name, MutableTable table, List<MutableField> fields, boolean enforced) {
            super(name, table, enforced);
            this.fields = fields;
        }

        final boolean fieldsEquals(Field<?>[] f) {
            if (this.fields.size() != f.length) {
                return false;
            }
            return Tools.allMatch(this.fields, (x, i) -> x.nameEquals((UnqualifiedName)f[i].getUnqualifiedName()));
        }
    }

    private abstract class MutableNamed {
        private UnqualifiedName name;
        private String upper;
        private Comment comment;

        MutableNamed(UnqualifiedName name) {
            this(name, null);
        }

        MutableNamed(UnqualifiedName name, Comment comment) {
            this.comment = comment;
            this.name(name);
        }

        Name qualifiedName() {
            MutableNamed parent = this.parent();
            if (parent == null) {
                return this.name;
            }
            return parent.qualifiedName().append(this.name);
        }

        UnqualifiedName name() {
            return this.name;
        }

        void name(UnqualifiedName n) {
            this.name = n;
            this.upper = this.name.last().toUpperCase(Interpreter.this.locale);
        }

        Comment comment() {
            return this.comment;
        }

        void comment(Comment c) {
            this.comment = c;
        }

        boolean nameEquals(UnqualifiedName other) {
            switch (Interpreter.this.caseSensitivity) {
                case ALWAYS: {
                    return this.name.last().equals(other.last());
                }
                case WHEN_QUOTED: {
                    return Tools.normaliseNameCase(Interpreter.this.configuration, this.name.last(), this.name.quoted() == Name.Quoted.QUOTED, Interpreter.this.locale).equals(Tools.normaliseNameCase(Interpreter.this.configuration, other.last(), other.quoted() == Name.Quoted.QUOTED, Interpreter.this.locale));
                }
                case NEVER: {
                    return this.upper.equalsIgnoreCase(other.last().toUpperCase(Interpreter.this.locale));
                }
            }
            throw new IllegalStateException();
        }

        abstract MutableNamed parent();

        abstract void onDrop();

        public String toString() {
            return this.qualifiedName().toString();
        }
    }

    private abstract class MutableConstraint
    extends MutableNamed {
        MutableTable table;
        boolean enforced;

        MutableConstraint(UnqualifiedName name, MutableTable table, boolean enforced) {
            super(name);
            this.table = table;
            this.enforced = enforced;
        }

        @Override
        final MutableNamed parent() {
            return this.table;
        }
    }

    private final class MutableCheck
    extends MutableConstraint {
        Condition condition;

        MutableCheck(Constraint constraint) {
            this((UnqualifiedName)constraint.getUnqualifiedName(), null, ((ConstraintImpl)constraint).$check(), ((ConstraintImpl)constraint).$enforced());
        }

        MutableCheck(UnqualifiedName name, MutableTable table, Condition condition, boolean enforced) {
            super(name, table, enforced);
            this.condition = condition;
        }

        @Override
        final void onDrop() {
        }

        @Override
        final Name qualifiedName() {
            if (this.name().empty()) {
                return super.qualifiedName().append(this.condition.toString());
            }
            return super.qualifiedName();
        }
    }

    private final class MutableSequence
    extends MutableNamed {
        MutableSchema schema;
        Field<? extends Number> startWith;
        Field<? extends Number> incrementBy;
        Field<? extends Number> minvalue;
        Field<? extends Number> maxvalue;
        boolean cycle;
        Field<? extends Number> cache;

        MutableSequence(UnqualifiedName name, MutableSchema schema) {
            super(name);
            this.schema = schema;
            schema.sequences.add(this);
        }

        @Override
        final void onDrop() {
        }

        @Override
        final MutableNamed parent() {
            return this.schema;
        }

        final InterpretedSequence interpretedSequence() {
            return Interpreter.this.interpretedSequences.computeIfAbsent(this.qualifiedName(), n -> new InterpretedSequence(this.schema.interpretedSchema()));
        }

        private final class InterpretedSequence
        extends SequenceImpl<Long> {
            InterpretedSequence(Schema schema) {
                super(MutableSequence.this.name(), schema, SQLDataType.BIGINT, false, MutableSequence.this.startWith, MutableSequence.this.incrementBy, MutableSequence.this.minvalue, MutableSequence.this.maxvalue, MutableSequence.this.cycle, MutableSequence.this.cache);
            }
        }
    }

    private final class MutableDomain
    extends MutableNamed {
        MutableSchema schema;
        DataType<?> dataType;
        List<MutableCheck> checks;
        List<MutableField> fields;

        MutableDomain(UnqualifiedName name, MutableSchema schema, DataType<?> dataType) {
            super(name);
            this.checks = new MutableNamedList<MutableCheck>();
            this.fields = new MutableNamedList<MutableField>();
            this.schema = schema;
            this.dataType = dataType;
            schema.domains.add(this);
        }

        @Override
        final void onDrop() {
            this.schema.domains.remove(this);
        }

        @Override
        final MutableNamed parent() {
            return this.schema;
        }

        final InterpretedDomain interpretedDomain() {
            return Interpreter.this.interpretedDomains.computeIfAbsent(this.qualifiedName(), n -> new InterpretedDomain(this.schema.interpretedSchema()));
        }

        final Check<?>[] interpretedChecks() {
            return Tools.map(this.checks, c -> new CheckImpl(null, c.name(), c.condition, c.enforced), Check[]::new);
        }

        private final class InterpretedDomain
        extends DomainImpl {
            InterpretedDomain(Schema schema) {
                super(schema, MutableDomain.this.name(), MutableDomain.this.dataType, MutableDomain.this.interpretedChecks());
            }
        }
    }

    private final class MutableNamedList<N extends MutableNamed>
    extends AbstractList<N> {
        private final List<N> delegate = new ArrayList<N>();

        private MutableNamedList() {
        }

        @Override
        public N get(int index) {
            return (N)((MutableNamed)this.delegate.get(index));
        }

        @Override
        public int size() {
            return this.delegate.size();
        }

        @Override
        public N set(int index, N element) {
            return (N)((MutableNamed)this.delegate.set(index, element));
        }

        @Override
        public void add(int index, N element) {
            this.delegate.add(index, element);
        }

        @Override
        public N remove(int index) {
            MutableNamed removed = (MutableNamed)this.delegate.remove(index);
            removed.onDrop();
            return (N)removed;
        }
    }
}

