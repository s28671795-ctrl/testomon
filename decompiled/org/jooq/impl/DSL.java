/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import javax.sql.DataSource;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jooq.AggregateFunction;
import org.jooq.AlterDatabaseStep;
import org.jooq.AlterDomainStep;
import org.jooq.AlterIndexOnStep;
import org.jooq.AlterSchemaStep;
import org.jooq.AlterSequenceStep;
import org.jooq.AlterTableStep;
import org.jooq.AlterTypeStep;
import org.jooq.AlterViewStep;
import org.jooq.ArrayAggOrderByStep;
import org.jooq.ArrayRecord;
import org.jooq.AssociativeArrayRecord;
import org.jooq.Asterisk;
import org.jooq.Block;
import org.jooq.CallArgsStep;
import org.jooq.Case;
import org.jooq.CaseConditionStep;
import org.jooq.CaseValueStep;
import org.jooq.Catalog;
import org.jooq.CharacterSet;
import org.jooq.Check;
import org.jooq.CheckReturnValue;
import org.jooq.CloseableDSLContext;
import org.jooq.Collation;
import org.jooq.Comment;
import org.jooq.CommentOnIsStep;
import org.jooq.CommonTableExpression;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.ConstraintEnforcementStep;
import org.jooq.ConstraintForeignKeyReferencesStep1;
import org.jooq.ConstraintForeignKeyReferencesStep10;
import org.jooq.ConstraintForeignKeyReferencesStep11;
import org.jooq.ConstraintForeignKeyReferencesStep12;
import org.jooq.ConstraintForeignKeyReferencesStep13;
import org.jooq.ConstraintForeignKeyReferencesStep14;
import org.jooq.ConstraintForeignKeyReferencesStep15;
import org.jooq.ConstraintForeignKeyReferencesStep16;
import org.jooq.ConstraintForeignKeyReferencesStep17;
import org.jooq.ConstraintForeignKeyReferencesStep18;
import org.jooq.ConstraintForeignKeyReferencesStep19;
import org.jooq.ConstraintForeignKeyReferencesStep2;
import org.jooq.ConstraintForeignKeyReferencesStep20;
import org.jooq.ConstraintForeignKeyReferencesStep21;
import org.jooq.ConstraintForeignKeyReferencesStep22;
import org.jooq.ConstraintForeignKeyReferencesStep3;
import org.jooq.ConstraintForeignKeyReferencesStep4;
import org.jooq.ConstraintForeignKeyReferencesStep5;
import org.jooq.ConstraintForeignKeyReferencesStep6;
import org.jooq.ConstraintForeignKeyReferencesStep7;
import org.jooq.ConstraintForeignKeyReferencesStep8;
import org.jooq.ConstraintForeignKeyReferencesStep9;
import org.jooq.ConstraintForeignKeyReferencesStepN;
import org.jooq.ConstraintTypeStep;
import org.jooq.ContinueWhenStep;
import org.jooq.CreateDatabaseFinalStep;
import org.jooq.CreateDomainAsStep;
import org.jooq.CreateFunctionParametersStep;
import org.jooq.CreateIndexStep;
import org.jooq.CreateProcedureParametersStep;
import org.jooq.CreateSchemaFinalStep;
import org.jooq.CreateSequenceFlagsStep;
import org.jooq.CreateTableElementListStep;
import org.jooq.CreateTriggerEventStep;
import org.jooq.CreateTypeStep;
import org.jooq.CreateViewAsStep;
import org.jooq.DDLQuery;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.DatePart;
import org.jooq.Declaration;
import org.jooq.Delete;
import org.jooq.DeleteUsingStep;
import org.jooq.Domain;
import org.jooq.DropDatabaseFinalStep;
import org.jooq.DropDomainCascadeStep;
import org.jooq.DropIndexOnStep;
import org.jooq.DropSchemaStep;
import org.jooq.DropSequenceFinalStep;
import org.jooq.DropTableStep;
import org.jooq.DropTypeStep;
import org.jooq.DropViewFinalStep;
import org.jooq.ExitWhenStep;
import org.jooq.False;
import org.jooq.Field;
import org.jooq.FieldOrRow;
import org.jooq.ForInStep;
import org.jooq.Geography;
import org.jooq.Geometry;
import org.jooq.GrantOnStep;
import org.jooq.GroupConcatOrderByStep;
import org.jooq.GroupField;
import org.jooq.IfThenStep;
import org.jooq.Index;
import org.jooq.Insert;
import org.jooq.InsertSetStep;
import org.jooq.InsertValuesStep1;
import org.jooq.InsertValuesStep10;
import org.jooq.InsertValuesStep11;
import org.jooq.InsertValuesStep12;
import org.jooq.InsertValuesStep13;
import org.jooq.InsertValuesStep14;
import org.jooq.InsertValuesStep15;
import org.jooq.InsertValuesStep16;
import org.jooq.InsertValuesStep17;
import org.jooq.InsertValuesStep18;
import org.jooq.InsertValuesStep19;
import org.jooq.InsertValuesStep2;
import org.jooq.InsertValuesStep20;
import org.jooq.InsertValuesStep21;
import org.jooq.InsertValuesStep22;
import org.jooq.InsertValuesStep3;
import org.jooq.InsertValuesStep4;
import org.jooq.InsertValuesStep5;
import org.jooq.InsertValuesStep6;
import org.jooq.InsertValuesStep7;
import org.jooq.InsertValuesStep8;
import org.jooq.InsertValuesStep9;
import org.jooq.InsertValuesStepN;
import org.jooq.JSON;
import org.jooq.JSONArrayAggOrderByStep;
import org.jooq.JSONArrayNullStep;
import org.jooq.JSONB;
import org.jooq.JSONEntry;
import org.jooq.JSONEntryValueStep;
import org.jooq.JSONExistsOnStep;
import org.jooq.JSONObjectAggNullStep;
import org.jooq.JSONObjectNullStep;
import org.jooq.JSONTableColumnsFirstStep;
import org.jooq.JSONValueOnStep;
import org.jooq.Keyword;
import org.jooq.Label;
import org.jooq.Link;
import org.jooq.LoopStep;
import org.jooq.Merge;
import org.jooq.MergeKeyStep1;
import org.jooq.MergeKeyStep10;
import org.jooq.MergeKeyStep11;
import org.jooq.MergeKeyStep12;
import org.jooq.MergeKeyStep13;
import org.jooq.MergeKeyStep14;
import org.jooq.MergeKeyStep15;
import org.jooq.MergeKeyStep16;
import org.jooq.MergeKeyStep17;
import org.jooq.MergeKeyStep18;
import org.jooq.MergeKeyStep19;
import org.jooq.MergeKeyStep2;
import org.jooq.MergeKeyStep20;
import org.jooq.MergeKeyStep21;
import org.jooq.MergeKeyStep22;
import org.jooq.MergeKeyStep3;
import org.jooq.MergeKeyStep4;
import org.jooq.MergeKeyStep5;
import org.jooq.MergeKeyStep6;
import org.jooq.MergeKeyStep7;
import org.jooq.MergeKeyStep8;
import org.jooq.MergeKeyStep9;
import org.jooq.MergeKeyStepN;
import org.jooq.MergeUsingStep;
import org.jooq.Name;
import org.jooq.Null;
import org.jooq.Operator;
import org.jooq.OrderField;
import org.jooq.OrderedAggregateFunction;
import org.jooq.OrderedAggregateFunctionOfDeferredType;
import org.jooq.Param;
import org.jooq.ParamMode;
import org.jooq.Parameter;
import org.jooq.Period;
import org.jooq.PlainSQL;
import org.jooq.Privilege;
import org.jooq.Pro;
import org.jooq.QualifiedRecord;
import org.jooq.QuantifiedSelect;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Record11;
import org.jooq.Record12;
import org.jooq.Record13;
import org.jooq.Record14;
import org.jooq.Record15;
import org.jooq.Record16;
import org.jooq.Record17;
import org.jooq.Record18;
import org.jooq.Record19;
import org.jooq.Record2;
import org.jooq.Record20;
import org.jooq.Record21;
import org.jooq.Record22;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Record8;
import org.jooq.Record9;
import org.jooq.RecordType;
import org.jooq.RepeatUntilStep;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.RevokeOnStep;
import org.jooq.Role;
import org.jooq.RollbackToSavepointStep;
import org.jooq.Row;
import org.jooq.Row1;
import org.jooq.Row10;
import org.jooq.Row11;
import org.jooq.Row12;
import org.jooq.Row13;
import org.jooq.Row14;
import org.jooq.Row15;
import org.jooq.Row16;
import org.jooq.Row17;
import org.jooq.Row18;
import org.jooq.Row19;
import org.jooq.Row2;
import org.jooq.Row20;
import org.jooq.Row21;
import org.jooq.Row22;
import org.jooq.Row3;
import org.jooq.Row4;
import org.jooq.Row5;
import org.jooq.Row6;
import org.jooq.Row7;
import org.jooq.Row8;
import org.jooq.Row9;
import org.jooq.RowCountQuery;
import org.jooq.RowN;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.Sequence;
import org.jooq.SignalSetStep;
import org.jooq.Spatial;
import org.jooq.Statement;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.True;
import org.jooq.TruncateIdentityStep;
import org.jooq.Update;
import org.jooq.UpdateSetFirstStep;
import org.jooq.User;
import org.jooq.Variable;
import org.jooq.WindowFromFirstLastStep;
import org.jooq.WindowIgnoreNullsStep;
import org.jooq.WindowOverStep;
import org.jooq.WindowSpecificationExcludeStep;
import org.jooq.WindowSpecificationOrderByStep;
import org.jooq.WindowSpecificationRowsAndStep;
import org.jooq.WindowSpecificationRowsStep;
import org.jooq.WithAsStep;
import org.jooq.WithAsStep1;
import org.jooq.WithAsStep10;
import org.jooq.WithAsStep11;
import org.jooq.WithAsStep12;
import org.jooq.WithAsStep13;
import org.jooq.WithAsStep14;
import org.jooq.WithAsStep15;
import org.jooq.WithAsStep16;
import org.jooq.WithAsStep17;
import org.jooq.WithAsStep18;
import org.jooq.WithAsStep19;
import org.jooq.WithAsStep2;
import org.jooq.WithAsStep20;
import org.jooq.WithAsStep21;
import org.jooq.WithAsStep22;
import org.jooq.WithAsStep3;
import org.jooq.WithAsStep4;
import org.jooq.WithAsStep5;
import org.jooq.WithAsStep6;
import org.jooq.WithAsStep7;
import org.jooq.WithAsStep8;
import org.jooq.WithAsStep9;
import org.jooq.WithStep;
import org.jooq.XML;
import org.jooq.XMLAggOrderByStep;
import org.jooq.XMLAttributes;
import org.jooq.XMLExistsPassingStep;
import org.jooq.XMLQueryPassingStep;
import org.jooq.XMLTablePassingStep;
import org.jooq.conf.ParamType;
import org.jooq.conf.Settings;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.impl.Abs;
import org.jooq.impl.AbstractDataType;
import org.jooq.impl.AbstractParamX;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.Acos;
import org.jooq.impl.Acosh;
import org.jooq.impl.Acoth;
import org.jooq.impl.And;
import org.jooq.impl.AnyValue;
import org.jooq.impl.Array;
import org.jooq.impl.ArrayAgg;
import org.jooq.impl.ArrayAppend;
import org.jooq.impl.ArrayConcat;
import org.jooq.impl.ArrayConstant;
import org.jooq.impl.ArrayGet;
import org.jooq.impl.ArrayOfValues;
import org.jooq.impl.ArrayOverlap;
import org.jooq.impl.ArrayPrepend;
import org.jooq.impl.ArrayQuery;
import org.jooq.impl.ArrayRemove;
import org.jooq.impl.ArrayReplace;
import org.jooq.impl.ArrayTable;
import org.jooq.impl.Ascii;
import org.jooq.impl.Asin;
import org.jooq.impl.Asinh;
import org.jooq.impl.AssociativeArrayConstant;
import org.jooq.impl.AsteriskImpl;
import org.jooq.impl.Atan;
import org.jooq.impl.Atan2;
import org.jooq.impl.Atanh;
import org.jooq.impl.Avg;
import org.jooq.impl.BitAnd;
import org.jooq.impl.BitAndAgg;
import org.jooq.impl.BitCount;
import org.jooq.impl.BitGet;
import org.jooq.impl.BitLength;
import org.jooq.impl.BitNand;
import org.jooq.impl.BitNandAgg;
import org.jooq.impl.BitNor;
import org.jooq.impl.BitNorAgg;
import org.jooq.impl.BitNot;
import org.jooq.impl.BitOr;
import org.jooq.impl.BitOrAgg;
import org.jooq.impl.BitSet;
import org.jooq.impl.BitXNor;
import org.jooq.impl.BitXNorAgg;
import org.jooq.impl.BitXor;
import org.jooq.impl.BitXorAgg;
import org.jooq.impl.BoolAnd;
import org.jooq.impl.BoolOr;
import org.jooq.impl.Cardinality;
import org.jooq.impl.CaseImpl;
import org.jooq.impl.CatalogImpl;
import org.jooq.impl.Ceil;
import org.jooq.impl.CharLength;
import org.jooq.impl.CharacterSetImpl;
import org.jooq.impl.Choose;
import org.jooq.impl.Chr;
import org.jooq.impl.Coalesce;
import org.jooq.impl.CollationImpl;
import org.jooq.impl.Collect;
import org.jooq.impl.CommentImpl;
import org.jooq.impl.Concat;
import org.jooq.impl.ConditionAsField;
import org.jooq.impl.ConnectByIsCycle;
import org.jooq.impl.ConnectByIsLeaf;
import org.jooq.impl.ConnectByRoot;
import org.jooq.impl.ConstraintImpl;
import org.jooq.impl.Continue;
import org.jooq.impl.Convert;
import org.jooq.impl.ConvertDateTime;
import org.jooq.impl.ConvertedDataType;
import org.jooq.impl.Corr;
import org.jooq.impl.Cos;
import org.jooq.impl.Cosh;
import org.jooq.impl.Cot;
import org.jooq.impl.Coth;
import org.jooq.impl.Count;
import org.jooq.impl.CountTable;
import org.jooq.impl.CovarPop;
import org.jooq.impl.CovarSamp;
import org.jooq.impl.Cube;
import org.jooq.impl.CumeDist;
import org.jooq.impl.CurrentCatalog;
import org.jooq.impl.CurrentDate;
import org.jooq.impl.CurrentSchema;
import org.jooq.impl.CurrentTime;
import org.jooq.impl.CurrentTimestamp;
import org.jooq.impl.CurrentUser;
import org.jooq.impl.CustomCondition;
import org.jooq.impl.CustomField;
import org.jooq.impl.CustomQueryPart;
import org.jooq.impl.CustomStatement;
import org.jooq.impl.DataChangeDeltaTable;
import org.jooq.impl.DataTypeProxy;
import org.jooq.impl.DateAdd;
import org.jooq.impl.DateDiff;
import org.jooq.impl.DateOrTime;
import org.jooq.impl.DeclarationImpl;
import org.jooq.impl.Decode;
import org.jooq.impl.Default;
import org.jooq.impl.DefaultAggregateFunction;
import org.jooq.impl.DefaultCloseableConnectionProvider;
import org.jooq.impl.DefaultCloseableDSLContext;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultConnectionFactory;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.Degrees;
import org.jooq.impl.Deleting;
import org.jooq.impl.DenseRank;
import org.jooq.impl.Digits;
import org.jooq.impl.DomainImpl;
import org.jooq.impl.Dual;
import org.jooq.impl.EmptyGroupingSet;
import org.jooq.impl.Euler;
import org.jooq.impl.Excluded;
import org.jooq.impl.Execute;
import org.jooq.impl.Exists;
import org.jooq.impl.Exit;
import org.jooq.impl.Exp;
import org.jooq.impl.Extract;
import org.jooq.impl.FalseCondition;
import org.jooq.impl.FieldCondition;
import org.jooq.impl.FieldFunction;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.FirstValue;
import org.jooq.impl.Floor;
import org.jooq.impl.ForImpl;
import org.jooq.impl.Function;
import org.jooq.impl.Function1;
import org.jooq.impl.FunctionTable;
import org.jooq.impl.GenerateSeries;
import org.jooq.impl.Goto;
import org.jooq.impl.Greatest;
import org.jooq.impl.GroupConcat;
import org.jooq.impl.GroupingSets;
import org.jooq.impl.IfImpl;
import org.jooq.impl.Iif;
import org.jooq.impl.IndexImpl;
import org.jooq.impl.Inserting;
import org.jooq.impl.JSONArray;
import org.jooq.impl.JSONArrayAgg;
import org.jooq.impl.JSONBGetAttribute;
import org.jooq.impl.JSONBGetAttributeAsText;
import org.jooq.impl.JSONBGetElement;
import org.jooq.impl.JSONBGetElementAsText;
import org.jooq.impl.JSONBInsert;
import org.jooq.impl.JSONBKeys;
import org.jooq.impl.JSONBRemove;
import org.jooq.impl.JSONBReplace;
import org.jooq.impl.JSONBSet;
import org.jooq.impl.JSONEntryImpl;
import org.jooq.impl.JSONExists;
import org.jooq.impl.JSONGetAttribute;
import org.jooq.impl.JSONGetAttributeAsText;
import org.jooq.impl.JSONGetElement;
import org.jooq.impl.JSONGetElementAsText;
import org.jooq.impl.JSONInsert;
import org.jooq.impl.JSONKeys;
import org.jooq.impl.JSONObject;
import org.jooq.impl.JSONObjectAgg;
import org.jooq.impl.JSONRemove;
import org.jooq.impl.JSONReplace;
import org.jooq.impl.JSONSet;
import org.jooq.impl.JSONTable;
import org.jooq.impl.JSONValue;
import org.jooq.impl.KeywordImpl;
import org.jooq.impl.LabelImpl;
import org.jooq.impl.Lag;
import org.jooq.impl.LastValue;
import org.jooq.impl.Lateral;
import org.jooq.impl.Lead;
import org.jooq.impl.Least;
import org.jooq.impl.Left;
import org.jooq.impl.LegacyConvertedDataType;
import org.jooq.impl.Level;
import org.jooq.impl.LinkImpl;
import org.jooq.impl.ListAgg;
import org.jooq.impl.Ln;
import org.jooq.impl.Log;
import org.jooq.impl.Log10;
import org.jooq.impl.LoopImpl;
import org.jooq.impl.Lower;
import org.jooq.impl.Lpad;
import org.jooq.impl.Ltrim;
import org.jooq.impl.MapCondition;
import org.jooq.impl.Max;
import org.jooq.impl.Md5;
import org.jooq.impl.Median;
import org.jooq.impl.Min;
import org.jooq.impl.Mode;
import org.jooq.impl.ModeDeferred;
import org.jooq.impl.Multiset;
import org.jooq.impl.MultisetAgg;
import org.jooq.impl.Names;
import org.jooq.impl.NoCondition;
import org.jooq.impl.NoField;
import org.jooq.impl.Not;
import org.jooq.impl.NotField;
import org.jooq.impl.NthValue;
import org.jooq.impl.Ntile;
import org.jooq.impl.NullCondition;
import org.jooq.impl.Nullif;
import org.jooq.impl.Nvl;
import org.jooq.impl.Nvl2;
import org.jooq.impl.OctetLength;
import org.jooq.impl.Or;
import org.jooq.impl.Overlay;
import org.jooq.impl.ParameterImpl;
import org.jooq.impl.PercentRank;
import org.jooq.impl.PeriodImpl;
import org.jooq.impl.Pi;
import org.jooq.impl.Position;
import org.jooq.impl.Power;
import org.jooq.impl.Prior;
import org.jooq.impl.PrivilegeImpl;
import org.jooq.impl.Product;
import org.jooq.impl.QOM;
import org.jooq.impl.QualifiedName;
import org.jooq.impl.QualifiedRecordConstant;
import org.jooq.impl.QuantifiedArray;
import org.jooq.impl.QuantifiedSelectImpl;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.R2DBC;
import org.jooq.impl.Radians;
import org.jooq.impl.Rand;
import org.jooq.impl.Rank;
import org.jooq.impl.RatioToReport;
import org.jooq.impl.RecordCondition;
import org.jooq.impl.RegexpReplace;
import org.jooq.impl.RegrAvgX;
import org.jooq.impl.RegrAvgY;
import org.jooq.impl.RegrCount;
import org.jooq.impl.RegrIntercept;
import org.jooq.impl.RegrR2;
import org.jooq.impl.RegrSlope;
import org.jooq.impl.RegrSxx;
import org.jooq.impl.RegrSxy;
import org.jooq.impl.RegrSyy;
import org.jooq.impl.Repeat;
import org.jooq.impl.Replace;
import org.jooq.impl.ResultImpl;
import org.jooq.impl.Return;
import org.jooq.impl.Reverse;
import org.jooq.impl.Right;
import org.jooq.impl.RoleImpl;
import org.jooq.impl.Rollup;
import org.jooq.impl.Round;
import org.jooq.impl.RowAsField;
import org.jooq.impl.RowImpl1;
import org.jooq.impl.RowImpl10;
import org.jooq.impl.RowImpl11;
import org.jooq.impl.RowImpl12;
import org.jooq.impl.RowImpl13;
import org.jooq.impl.RowImpl14;
import org.jooq.impl.RowImpl15;
import org.jooq.impl.RowImpl16;
import org.jooq.impl.RowImpl17;
import org.jooq.impl.RowImpl18;
import org.jooq.impl.RowImpl19;
import org.jooq.impl.RowImpl2;
import org.jooq.impl.RowImpl20;
import org.jooq.impl.RowImpl21;
import org.jooq.impl.RowImpl22;
import org.jooq.impl.RowImpl3;
import org.jooq.impl.RowImpl4;
import org.jooq.impl.RowImpl5;
import org.jooq.impl.RowImpl6;
import org.jooq.impl.RowImpl7;
import org.jooq.impl.RowImpl8;
import org.jooq.impl.RowImpl9;
import org.jooq.impl.RowImplN;
import org.jooq.impl.RowNumber;
import org.jooq.impl.Rownum;
import org.jooq.impl.RowsFrom;
import org.jooq.impl.Rpad;
import org.jooq.impl.Rtrim;
import org.jooq.impl.SQLCondition;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SQLField;
import org.jooq.impl.SQLImpl;
import org.jooq.impl.SQLStatement;
import org.jooq.impl.SQLTable;
import org.jooq.impl.SchemaImpl;
import org.jooq.impl.SequenceImpl;
import org.jooq.impl.Shl;
import org.jooq.impl.Shr;
import org.jooq.impl.Sign;
import org.jooq.impl.Signal;
import org.jooq.impl.Sin;
import org.jooq.impl.Sinh;
import org.jooq.impl.Space;
import org.jooq.impl.SplitPart;
import org.jooq.impl.Sqrt;
import org.jooq.impl.Square;
import org.jooq.impl.StArea;
import org.jooq.impl.StAsBinary;
import org.jooq.impl.StAsText;
import org.jooq.impl.StCentroid;
import org.jooq.impl.StContains;
import org.jooq.impl.StCrosses;
import org.jooq.impl.StDifference;
import org.jooq.impl.StDisjoint;
import org.jooq.impl.StDistance;
import org.jooq.impl.StEndPoint;
import org.jooq.impl.StEquals;
import org.jooq.impl.StExteriorRing;
import org.jooq.impl.StGeomFromText;
import org.jooq.impl.StGeomFromWKB;
import org.jooq.impl.StGeometryN;
import org.jooq.impl.StGeometryType;
import org.jooq.impl.StInteriorRingN;
import org.jooq.impl.StIntersection;
import org.jooq.impl.StIntersects;
import org.jooq.impl.StIsClosed;
import org.jooq.impl.StIsEmpty;
import org.jooq.impl.StLength;
import org.jooq.impl.StNumGeometries;
import org.jooq.impl.StNumInteriorRings;
import org.jooq.impl.StNumPoints;
import org.jooq.impl.StOverlaps;
import org.jooq.impl.StPointN;
import org.jooq.impl.StSrid;
import org.jooq.impl.StStartPoint;
import org.jooq.impl.StTouches;
import org.jooq.impl.StUnion;
import org.jooq.impl.StWithin;
import org.jooq.impl.StX;
import org.jooq.impl.StY;
import org.jooq.impl.StZ;
import org.jooq.impl.StddevPop;
import org.jooq.impl.StddevSamp;
import org.jooq.impl.Substring;
import org.jooq.impl.SubstringIndex;
import org.jooq.impl.Sum;
import org.jooq.impl.SysConnectByPath;
import org.jooq.impl.TableFieldImpl;
import org.jooq.impl.TableImpl;
import org.jooq.impl.Tan;
import org.jooq.impl.Tanh;
import org.jooq.impl.Tau;
import org.jooq.impl.TimestampDiff;
import org.jooq.impl.ToChar;
import org.jooq.impl.ToDate;
import org.jooq.impl.ToHex;
import org.jooq.impl.ToTimestamp;
import org.jooq.impl.Tools;
import org.jooq.impl.Translate;
import org.jooq.impl.Trim;
import org.jooq.impl.TrueCondition;
import org.jooq.impl.Trunc;
import org.jooq.impl.TruncDate;
import org.jooq.impl.Unique;
import org.jooq.impl.UnqualifiedName;
import org.jooq.impl.Updating;
import org.jooq.impl.Upper;
import org.jooq.impl.UserImpl;
import org.jooq.impl.Uuid;
import org.jooq.impl.Val;
import org.jooq.impl.Values;
import org.jooq.impl.VarPop;
import org.jooq.impl.VarSamp;
import org.jooq.impl.VariableImpl;
import org.jooq.impl.WidthBucket;
import org.jooq.impl.WindowSpecificationImpl;
import org.jooq.impl.WithImpl;
import org.jooq.impl.XMLAgg;
import org.jooq.impl.XMLAttributesImpl;
import org.jooq.impl.XMLComment;
import org.jooq.impl.XMLConcat;
import org.jooq.impl.XMLDocument;
import org.jooq.impl.XMLElement;
import org.jooq.impl.XMLExists;
import org.jooq.impl.XMLForest;
import org.jooq.impl.XMLParse;
import org.jooq.impl.XMLPi;
import org.jooq.impl.XMLQuery;
import org.jooq.impl.XMLSerialize;
import org.jooq.impl.XMLTable;
import org.jooq.impl.Xor;
import org.jooq.tools.StringUtils;
import org.jooq.tools.jdbc.JDBCUtils;
import org.jooq.types.DayToSecond;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;

public class DSL {
    @NotNull
    public static DSLContext using(SQLDialect dialect) {
        return new DefaultDSLContext(dialect, null);
    }

    @NotNull
    public static DSLContext using(SQLDialect dialect, Settings settings) {
        return new DefaultDSLContext(dialect, settings);
    }

    @Blocking
    @NotNull
    public static CloseableDSLContext using(String url2) {
        if (url2.startsWith("r2dbc")) {
            Connection connection = R2DBC.getConnection(url2);
            return new DefaultCloseableDSLContext(new DefaultConnectionFactory(connection, true, false), JDBCUtils.dialect(connection));
        }
        try {
            java.sql.Connection connection = DriverManager.getConnection(url2);
            return new DefaultCloseableDSLContext(new DefaultCloseableConnectionProvider(connection), JDBCUtils.dialect(connection));
        }
        catch (SQLException e) {
            throw Tools.translate("Error when initialising Connection", e);
        }
    }

    @Blocking
    @NotNull
    public static CloseableDSLContext using(String url2, String username, String password) {
        if (url2.startsWith("r2dbc")) {
            Connection connection = R2DBC.getConnection(url2, username, password);
            return new DefaultCloseableDSLContext(new DefaultConnectionFactory(connection, true, false), JDBCUtils.dialect(connection));
        }
        try {
            java.sql.Connection connection = DriverManager.getConnection(url2, username, password);
            return new DefaultCloseableDSLContext(new DefaultCloseableConnectionProvider(connection), JDBCUtils.dialect(connection));
        }
        catch (SQLException e) {
            throw Tools.translate("Error when initialising Connection", e);
        }
    }

    @Blocking
    @NotNull
    public static CloseableDSLContext using(String url2, Properties properties) {
        if (url2.startsWith("r2dbc")) {
            Connection connection = R2DBC.getConnection(url2, properties);
            return new DefaultCloseableDSLContext(new DefaultConnectionFactory(connection, true, false), JDBCUtils.dialect(connection));
        }
        try {
            java.sql.Connection connection = DriverManager.getConnection(url2, properties);
            return new DefaultCloseableDSLContext(new DefaultCloseableConnectionProvider(connection), JDBCUtils.dialect(connection));
        }
        catch (SQLException e) {
            throw Tools.translate("Error when initialising Connection", e);
        }
    }

    @NotNull
    public static DSLContext using(java.sql.Connection connection) {
        return new DefaultDSLContext(connection, JDBCUtils.dialect(connection), null);
    }

    @NotNull
    public static DSLContext using(java.sql.Connection connection, SQLDialect dialect) {
        return new DefaultDSLContext(connection, dialect, null);
    }

    @NotNull
    public static DSLContext using(java.sql.Connection connection, Settings settings) {
        return new DefaultDSLContext(connection, JDBCUtils.dialect(connection), settings);
    }

    @NotNull
    public static DSLContext using(java.sql.Connection connection, SQLDialect dialect, Settings settings) {
        return new DefaultDSLContext(connection, dialect, settings);
    }

    @NotNull
    public static DSLContext using(DataSource datasource, SQLDialect dialect) {
        return new DefaultDSLContext(datasource, dialect);
    }

    @NotNull
    public static DSLContext using(DataSource datasource, SQLDialect dialect, Settings settings) {
        return new DefaultDSLContext(datasource, dialect, settings);
    }

    @NotNull
    public static DSLContext using(ConnectionProvider connectionProvider, SQLDialect dialect) {
        return new DefaultDSLContext(connectionProvider, dialect);
    }

    @NotNull
    public static DSLContext using(ConnectionProvider connectionProvider, SQLDialect dialect, Settings settings) {
        return new DefaultDSLContext(connectionProvider, dialect, settings);
    }

    @NotNull
    public static DSLContext using(ConnectionFactory connectionFactory) {
        return new DefaultDSLContext(connectionFactory, JDBCUtils.dialect(connectionFactory));
    }

    @NotNull
    public static DSLContext using(ConnectionFactory connectionFactory, SQLDialect dialect) {
        return new DefaultDSLContext(connectionFactory, dialect);
    }

    @NotNull
    public static DSLContext using(ConnectionFactory connectionFactory, SQLDialect dialect, Settings settings) {
        return new DefaultDSLContext(connectionFactory, dialect, settings);
    }

    @NotNull
    public static DSLContext using(Connection connection) {
        return new DefaultDSLContext(new DefaultConnectionFactory(connection), JDBCUtils.dialect(connection));
    }

    @NotNull
    public static DSLContext using(Connection connection, SQLDialect dialect) {
        return new DefaultDSLContext(new DefaultConnectionFactory(connection), dialect);
    }

    @NotNull
    public static DSLContext using(Connection connection, SQLDialect dialect, Settings settings) {
        return new DefaultDSLContext(new DefaultConnectionFactory(connection), dialect, settings);
    }

    @NotNull
    public static DSLContext using(Configuration configuration) {
        return new DefaultDSLContext(configuration);
    }

    @Support
    @NotNull
    public static WithAsStep with(String alias) {
        return new WithImpl(null, false).with(alias);
    }

    @Support
    @NotNull
    public static WithAsStep with(String alias, String ... fieldAliases) {
        return new WithImpl(null, false).with(alias, fieldAliases);
    }

    @Support
    @NotNull
    public static WithAsStep with(String alias, Collection<String> fieldAliases) {
        return new WithImpl(null, false).with(alias, (Collection)fieldAliases);
    }

    @Support
    @NotNull
    public static WithAsStep with(Name alias) {
        return new WithImpl(null, false).with(alias);
    }

    @Support
    @NotNull
    public static WithAsStep with(Name alias, Name ... fieldAliases) {
        return new WithImpl(null, false).with(alias, fieldAliases);
    }

    @Support
    @NotNull
    public static WithAsStep with(Name alias, Collection<? extends Name> fieldAliases) {
        return new WithImpl(null, false).with(alias, (Collection)fieldAliases);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support
    @NotNull
    public static WithAsStep with(String alias, java.util.function.Function<? super Field<?>, ? extends String> fieldNameFunction) {
        return new WithImpl(null, false).with(alias, (java.util.function.Function)fieldNameFunction);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep1 with(String alias, String fieldAlias1) {
        return new WithImpl(null, false).with(alias, fieldAlias1);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep2 with(String alias, String fieldAlias1, String fieldAlias2) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep3 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep4 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep5 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep6 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep7 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep8 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep9 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep10 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep11 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep12 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep13 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep14 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep15 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep16 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep17 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep18 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep19 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep20 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep21 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20, String fieldAlias21) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep22 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20, String fieldAlias21, String fieldAlias22) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21, fieldAlias22);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep1 with(Name alias, Name fieldAlias1) {
        return new WithImpl(null, false).with(alias, fieldAlias1);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep2 with(Name alias, Name fieldAlias1, Name fieldAlias2) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep3 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep4 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep5 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep6 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep7 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep8 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep9 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep10 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep11 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep12 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep13 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep14 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep15 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep16 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep17 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep18 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep19 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep20 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19, Name fieldAlias20) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep21 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19, Name fieldAlias20, Name fieldAlias21) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static WithAsStep22 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19, Name fieldAlias20, Name fieldAlias21, Name fieldAlias22) {
        return new WithImpl(null, false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21, fieldAlias22);
    }

    @Support
    @NotNull
    public static WithStep with(CommonTableExpression<?> ... tables) {
        return new WithImpl(null, false).with((CommonTableExpression[])tables);
    }

    @Support
    @NotNull
    public static WithStep with(Collection<? extends CommonTableExpression<?>> tables) {
        return new WithImpl(null, false).with((Collection)tables);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep withRecursive(String alias) {
        return new WithImpl(null, true).with(alias);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep withRecursive(String alias, String ... fieldAliases) {
        return new WithImpl(null, true).with(alias, fieldAliases);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep withRecursive(String alias, Collection<String> fieldAliases) {
        return new WithImpl(null, true).with(alias, (Collection)fieldAliases);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep withRecursive(Name alias) {
        return new WithImpl(null, true).with(alias);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep withRecursive(Name alias, Name ... fieldAliases) {
        return new WithImpl(null, true).with(alias, fieldAliases);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep withRecursive(Name alias, Collection<? extends Name> fieldAliases) {
        return new WithImpl(null, true).with(alias, (Collection)fieldAliases);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep withRecursive(String alias, java.util.function.Function<? super Field<?>, ? extends String> fieldNameFunction) {
        return new WithImpl(null, true).with(alias, (java.util.function.Function)fieldNameFunction);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep1 withRecursive(String alias, String fieldAlias1) {
        return new WithImpl(null, true).with(alias, fieldAlias1);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep2 withRecursive(String alias, String fieldAlias1, String fieldAlias2) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep3 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep4 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep5 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep6 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep7 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep8 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep9 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep10 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep11 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep12 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep13 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep14 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep15 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep16 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep17 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep18 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep19 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep20 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep21 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20, String fieldAlias21) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep22 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20, String fieldAlias21, String fieldAlias22) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21, fieldAlias22);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep1 withRecursive(Name alias, Name fieldAlias1) {
        return new WithImpl(null, true).with(alias, fieldAlias1);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep2 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep3 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep4 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep5 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep6 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep7 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep8 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep9 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep10 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep11 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep12 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep13 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep14 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep15 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep16 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep17 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep18 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep19 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep20 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19, Name fieldAlias20) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep21 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19, Name fieldAlias20, Name fieldAlias21) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithAsStep22 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19, Name fieldAlias20, Name fieldAlias21, Name fieldAlias22) {
        return new WithImpl(null, true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21, fieldAlias22);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithStep withRecursive(CommonTableExpression<?> ... tables) {
        return new WithImpl(null, true).with((CommonTableExpression[])tables);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WithStep withRecursive(Collection<? extends CommonTableExpression<?>> tables) {
        return new WithImpl(null, true).with((Collection)tables);
    }

    @Support
    @NotNull
    public static <R extends Record> SelectWhereStep<R> selectFrom(TableLike<R> table) {
        return DSL.dsl().selectFrom(table);
    }

    @Support
    @NotNull
    public static SelectWhereStep<Record> selectFrom(Name table) {
        return DSL.dsl().selectFrom(table);
    }

    @Support
    @PlainSQL
    @NotNull
    public static SelectWhereStep<Record> selectFrom(SQL sql) {
        return DSL.dsl().selectFrom(sql);
    }

    @Support
    @PlainSQL
    @NotNull
    public static SelectWhereStep<Record> selectFrom(String sql) {
        return DSL.dsl().selectFrom(sql);
    }

    @Support
    @PlainSQL
    @NotNull
    public static SelectWhereStep<Record> selectFrom(String sql, Object ... bindings) {
        return DSL.dsl().selectFrom(sql, bindings);
    }

    @Support
    @PlainSQL
    @NotNull
    public static SelectWhereStep<Record> selectFrom(String sql, QueryPart ... parts) {
        return DSL.dsl().selectFrom(sql, parts);
    }

    @Support
    @NotNull
    public static SelectSelectStep<Record> select(Collection<? extends SelectFieldOrAsterisk> fields) {
        return DSL.dsl().select(fields);
    }

    @Support
    @NotNull
    public static SelectSelectStep<Record> select(SelectFieldOrAsterisk ... fields) {
        return DSL.dsl().select(fields);
    }

    @Support
    @NotNull
    public static <T1> SelectSelectStep<Record1<T1>> select(SelectField<T1> field1) {
        return DSL.select(new SelectField[]{field1});
    }

    @Support
    @NotNull
    public static <T1, T2> SelectSelectStep<Record2<T1, T2>> select(SelectField<T1> field1, SelectField<T2> field2) {
        return DSL.select(new SelectField[]{field1, field2});
    }

    @Support
    @NotNull
    public static <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3) {
        return DSL.select(new SelectField[]{field1, field2, field3});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4) {
        return DSL.select(new SelectField[]{field1, field2, field3, field4});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5) {
        return DSL.select(new SelectField[]{field1, field2, field3, field4, field5});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6) {
        return DSL.select(new SelectField[]{field1, field2, field3, field4, field5, field6});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7) {
        return DSL.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8) {
        return DSL.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9) {
        return DSL.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10) {
        return DSL.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11) {
        return DSL.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12) {
        return DSL.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13) {
        return DSL.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14) {
        return DSL.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15) {
        return DSL.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16) {
        return DSL.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17) {
        return DSL.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18) {
        return DSL.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19) {
        return DSL.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20) {
        return DSL.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21) {
        return DSL.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21, SelectField<T22> field22) {
        return DSL.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    @Support
    @NotNull
    public static SelectSelectStep<Record> selectDistinct(Collection<? extends SelectFieldOrAsterisk> fields) {
        return DSL.dsl().selectDistinct(fields);
    }

    @Support
    @NotNull
    public static SelectSelectStep<Record> selectDistinct(SelectFieldOrAsterisk ... fields) {
        return DSL.dsl().selectDistinct(fields);
    }

    @Support
    @NotNull
    public static <T1> SelectSelectStep<Record1<T1>> selectDistinct(SelectField<T1> field1) {
        return DSL.selectDistinct(new SelectField[]{field1});
    }

    @Support
    @NotNull
    public static <T1, T2> SelectSelectStep<Record2<T1, T2>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2) {
        return DSL.selectDistinct(new SelectField[]{field1, field2});
    }

    @Support
    @NotNull
    public static <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3, field4});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21, SelectField<T22> field22) {
        return DSL.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    @Support
    @NotNull
    public static SelectSelectStep<Record1<Integer>> selectZero() {
        return DSL.dsl().selectZero();
    }

    @Support
    @NotNull
    public static SelectSelectStep<Record1<Integer>> selectOne() {
        return DSL.dsl().selectOne();
    }

    @Support
    @NotNull
    public static SelectSelectStep<Record1<Integer>> selectCount() {
        return DSL.dsl().selectCount();
    }

    @Support
    @NotNull
    public static <R extends Record> InsertSetStep<R> insertInto(Table<R> into) {
        return DSL.dsl().insertInto(into);
    }

    @Support
    @NotNull
    public static <R extends Record, T1> InsertValuesStep1<R, T1> insertInto(Table<R> into, Field<T1> field1) {
        return (InsertValuesStep1)((Object)DSL.insertInto(into, new Field[]{field1}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2> InsertValuesStep2<R, T1, T2> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2) {
        return (InsertValuesStep2)((Object)DSL.insertInto(into, new Field[]{field1, field2}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3> InsertValuesStep3<R, T1, T2, T3> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3) {
        return (InsertValuesStep3)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3, T4> InsertValuesStep4<R, T1, T2, T3, T4> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
        return (InsertValuesStep4)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3, field4}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5> InsertValuesStep5<R, T1, T2, T3, T4, T5> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
        return (InsertValuesStep5)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3, field4, field5}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6> InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
        return (InsertValuesStep6)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3, field4, field5, field6}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7> InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
        return (InsertValuesStep7)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3, field4, field5, field6, field7}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
        return (InsertValuesStep8)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3, field4, field5, field6, field7, field8}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> InsertValuesStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
        return (InsertValuesStep9)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> InsertValuesStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
        return (InsertValuesStep10)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> InsertValuesStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
        return (InsertValuesStep11)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> InsertValuesStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
        return (InsertValuesStep12)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
        return (InsertValuesStep13)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> InsertValuesStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
        return (InsertValuesStep14)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> InsertValuesStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
        return (InsertValuesStep15)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> InsertValuesStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
        return (InsertValuesStep16)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> InsertValuesStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
        return (InsertValuesStep17)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> InsertValuesStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
        return (InsertValuesStep18)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> InsertValuesStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
        return (InsertValuesStep19)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> InsertValuesStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
        return (InsertValuesStep20)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> InsertValuesStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
        return (InsertValuesStep21)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21}));
    }

    @Support
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> InsertValuesStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
        return (InsertValuesStep22)((Object)DSL.insertInto(into, new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22}));
    }

    @Support
    @NotNull
    public static <R extends Record> InsertValuesStepN<R> insertInto(Table<R> into, Field<?> ... fields) {
        return DSL.dsl().insertInto(into, fields);
    }

    @Support
    @NotNull
    public static <R extends Record> InsertValuesStepN<R> insertInto(Table<R> into, Collection<? extends Field<?>> fields) {
        return DSL.dsl().insertInto(into, fields);
    }

    @Support
    @NotNull
    public static <R extends Record> UpdateSetFirstStep<R> update(Table<R> table) {
        return DSL.dsl().update(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record> MergeUsingStep<R> mergeInto(Table<R> table) {
        return DSL.dsl().mergeInto(table);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1> MergeKeyStep1<R, T1> mergeInto(Table<R> table, Field<T1> field1) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2> MergeKeyStep2<R, T1, T2> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3> MergeKeyStep3<R, T1, T2, T3> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3, T4> MergeKeyStep4<R, T1, T2, T3, T4> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5> MergeKeyStep5<R, T1, T2, T3, T4, T5> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6> MergeKeyStep6<R, T1, T2, T3, T4, T5, T6> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7> MergeKeyStep7<R, T1, T2, T3, T4, T5, T6, T7> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> MergeKeyStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> MergeKeyStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> MergeKeyStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> MergeKeyStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> MergeKeyStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> MergeKeyStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> MergeKeyStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> MergeKeyStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> MergeKeyStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> MergeKeyStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> MergeKeyStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> MergeKeyStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> MergeKeyStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> MergeKeyStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> MergeKeyStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
        return DSL.using(new DefaultConfiguration()).mergeInto(table, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static <R extends Record> MergeKeyStepN<R> mergeInto(Table<R> table, Field<?> ... fields) {
        return DSL.dsl().mergeInto(table, fields);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static <R extends Record> MergeKeyStepN<R> mergeInto(Table<R> table, Collection<? extends Field<?>> fields) {
        return DSL.dsl().mergeInto(table, fields);
    }

    @Support
    @NotNull
    public static <R extends Record> DeleteUsingStep<R> deleteFrom(Table<R> table) {
        return DSL.dsl().deleteFrom(table);
    }

    @Support
    @NotNull
    public static <R extends Record> DeleteUsingStep<R> delete(Table<R> table) {
        return DSL.dsl().deleteFrom(table);
    }

    @Support
    @NotNull
    public static Comment comment(String comment) {
        return StringUtils.isEmpty(comment) ? CommentImpl.NO_COMMENT : new CommentImpl(comment);
    }

    @Support
    @NotNull
    public static ConstraintTypeStep constraint() {
        return new ConstraintImpl();
    }

    @Support
    @NotNull
    public static ConstraintTypeStep constraint(Name name) {
        return new ConstraintImpl(name);
    }

    @Support
    @NotNull
    public static ConstraintTypeStep constraint(String name) {
        return DSL.constraint(DSL.name(name));
    }

    @Support
    @NotNull
    public static ConstraintEnforcementStep primaryKey(String ... fields) {
        return DSL.constraint().primaryKey(fields);
    }

    @Support
    @NotNull
    public static ConstraintEnforcementStep primaryKey(Name ... fields) {
        return DSL.constraint().primaryKey(fields);
    }

    @Support
    @NotNull
    public static ConstraintEnforcementStep primaryKey(Field<?> ... fields) {
        return DSL.constraint().primaryKey(fields);
    }

    @Support
    @NotNull
    public static ConstraintEnforcementStep primaryKey(Collection<? extends Field<?>> fields) {
        return DSL.constraint().primaryKey(fields);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStepN foreignKey(String ... fields) {
        return DSL.constraint().foreignKey(fields);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStepN foreignKey(Name ... fields) {
        return DSL.constraint().foreignKey(fields);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStepN foreignKey(Field<?> ... fields) {
        return DSL.constraint().foreignKey(fields);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStepN foreignKey(Collection<? extends Field<?>> fields) {
        return DSL.constraint().foreignKey(fields);
    }

    @Support
    @NotNull
    public static <T1> ConstraintForeignKeyReferencesStep1<T1> foreignKey(Field<T1> field1) {
        return DSL.constraint().foreignKey(field1);
    }

    @Support
    @NotNull
    public static <T1, T2> ConstraintForeignKeyReferencesStep2<T1, T2> foreignKey(Field<T1> field1, Field<T2> field2) {
        return DSL.constraint().foreignKey(field1, field2);
    }

    @Support
    @NotNull
    public static <T1, T2, T3> ConstraintForeignKeyReferencesStep3<T1, T2, T3> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
        return DSL.constraint().foreignKey(field1, field2, field3);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4> ConstraintForeignKeyReferencesStep4<T1, T2, T3, T4> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5> ConstraintForeignKeyReferencesStep5<T1, T2, T3, T4, T5> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6> ConstraintForeignKeyReferencesStep6<T1, T2, T3, T4, T5, T6> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7> ConstraintForeignKeyReferencesStep7<T1, T2, T3, T4, T5, T6, T7> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8> ConstraintForeignKeyReferencesStep8<T1, T2, T3, T4, T5, T6, T7, T8> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> ConstraintForeignKeyReferencesStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ConstraintForeignKeyReferencesStep10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ConstraintForeignKeyReferencesStep11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ConstraintForeignKeyReferencesStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> ConstraintForeignKeyReferencesStep13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> ConstraintForeignKeyReferencesStep14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> ConstraintForeignKeyReferencesStep15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> ConstraintForeignKeyReferencesStep16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> ConstraintForeignKeyReferencesStep17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> ConstraintForeignKeyReferencesStep18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> ConstraintForeignKeyReferencesStep19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> ConstraintForeignKeyReferencesStep20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> ConstraintForeignKeyReferencesStep21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> ConstraintForeignKeyReferencesStep22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep1<?> foreignKey(Name field1) {
        return DSL.constraint().foreignKey(field1);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep2<?, ?> foreignKey(Name field1, Name field2) {
        return DSL.constraint().foreignKey(field1, field2);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep3<?, ?, ?> foreignKey(Name field1, Name field2, Name field3) {
        return DSL.constraint().foreignKey(field1, field2, field3);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep4<?, ?, ?, ?> foreignKey(Name field1, Name field2, Name field3, Name field4) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep5<?, ?, ?, ?, ?> foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep6<?, ?, ?, ?, ?, ?> foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep7<?, ?, ?, ?, ?, ?, ?> foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep8<?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep9<?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep10<?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep11<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep12<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep13<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep14<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep15<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep16<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep17<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16, Name field17) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep18<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16, Name field17, Name field18) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep19<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16, Name field17, Name field18, Name field19) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep20<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16, Name field17, Name field18, Name field19, Name field20) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep21<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16, Name field17, Name field18, Name field19, Name field20, Name field21) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep22<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16, Name field17, Name field18, Name field19, Name field20, Name field21, Name field22) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep1<?> foreignKey(String field1) {
        return DSL.constraint().foreignKey(field1);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep2<?, ?> foreignKey(String field1, String field2) {
        return DSL.constraint().foreignKey(field1, field2);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep3<?, ?, ?> foreignKey(String field1, String field2, String field3) {
        return DSL.constraint().foreignKey(field1, field2, field3);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep4<?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep5<?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep6<?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep7<?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep8<?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep9<?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep10<?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep11<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep12<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep13<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep14<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep15<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep16<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep17<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep18<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep19<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep20<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19, String field20) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep21<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19, String field20, String field21) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
    }

    @Support
    @NotNull
    public static ConstraintForeignKeyReferencesStep22<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19, String field20, String field21, String field22) {
        return DSL.constraint().foreignKey(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
    }

    @Support
    @NotNull
    public static ConstraintEnforcementStep unique(String ... fields) {
        return DSL.constraint().unique(fields);
    }

    @Support
    @NotNull
    public static ConstraintEnforcementStep unique(Name ... fields) {
        return DSL.constraint().unique(fields);
    }

    @Support
    @NotNull
    public static ConstraintEnforcementStep unique(Field<?> ... fields) {
        return DSL.constraint().unique(fields);
    }

    @Support
    @NotNull
    public static ConstraintEnforcementStep unique(Collection<? extends Field<?>> fields) {
        return DSL.constraint().unique(fields);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0_19, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static ConstraintEnforcementStep check(Condition condition) {
        return DSL.constraint().check(condition);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static AlterDatabaseStep alterDatabase(String database) {
        return DSL.dsl().alterDatabase(database);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static AlterDatabaseStep alterDatabase(Name database) {
        return DSL.dsl().alterDatabase(database);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static AlterDatabaseStep alterDatabase(Catalog database) {
        return DSL.dsl().alterDatabase(database);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static AlterDatabaseStep alterDatabaseIfExists(String database) {
        return DSL.dsl().alterDatabaseIfExists(database);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static AlterDatabaseStep alterDatabaseIfExists(Name database) {
        return DSL.dsl().alterDatabaseIfExists(database);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static AlterDatabaseStep alterDatabaseIfExists(Catalog database) {
        return DSL.dsl().alterDatabaseIfExists(database);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static <T> AlterDomainStep<T> alterDomain(String domain) {
        return DSL.dsl().alterDomain(domain);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static <T> AlterDomainStep<T> alterDomain(Name domain) {
        return DSL.dsl().alterDomain(domain);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static <T> AlterDomainStep<T> alterDomain(Domain<T> domain) {
        return DSL.dsl().alterDomain(domain);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static <T> AlterDomainStep<T> alterDomainIfExists(String domain) {
        return DSL.dsl().alterDomainIfExists(domain);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static <T> AlterDomainStep<T> alterDomainIfExists(Name domain) {
        return DSL.dsl().alterDomainIfExists(domain);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static <T> AlterDomainStep<T> alterDomainIfExists(Domain<T> domain) {
        return DSL.dsl().alterDomainIfExists(domain);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2016})
    @NotNull
    @CheckReturnValue
    public static AlterIndexOnStep alterIndex(String index) {
        return DSL.dsl().alterIndex(index);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2016})
    @NotNull
    @CheckReturnValue
    public static AlterIndexOnStep alterIndex(Name index) {
        return DSL.dsl().alterIndex(index);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2016})
    @NotNull
    @CheckReturnValue
    public static AlterIndexOnStep alterIndex(Index index) {
        return DSL.dsl().alterIndex(index);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2016})
    @NotNull
    @CheckReturnValue
    public static AlterIndexOnStep alterIndexIfExists(String index) {
        return DSL.dsl().alterIndexIfExists(index);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2016})
    @NotNull
    @CheckReturnValue
    public static AlterIndexOnStep alterIndexIfExists(Name index) {
        return DSL.dsl().alterIndexIfExists(index);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2016})
    @NotNull
    @CheckReturnValue
    public static AlterIndexOnStep alterIndexIfExists(Index index) {
        return DSL.dsl().alterIndexIfExists(index);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public static AlterSchemaStep alterSchema(String schema) {
        return DSL.dsl().alterSchema(schema);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public static AlterSchemaStep alterSchema(Name schema) {
        return DSL.dsl().alterSchema(schema);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public static AlterSchemaStep alterSchema(Schema schema) {
        return DSL.dsl().alterSchema(schema);
    }

    @Support(value={SQLDialect.H2, SQLDialect.HANA, SQLDialect.SNOWFLAKE})
    @NotNull
    @CheckReturnValue
    public static AlterSchemaStep alterSchemaIfExists(String schema) {
        return DSL.dsl().alterSchemaIfExists(schema);
    }

    @Support(value={SQLDialect.H2, SQLDialect.HANA, SQLDialect.SNOWFLAKE})
    @NotNull
    @CheckReturnValue
    public static AlterSchemaStep alterSchemaIfExists(Name schema) {
        return DSL.dsl().alterSchemaIfExists(schema);
    }

    @Support(value={SQLDialect.H2, SQLDialect.HANA, SQLDialect.SNOWFLAKE})
    @NotNull
    @CheckReturnValue
    public static AlterSchemaStep alterSchemaIfExists(Schema schema) {
        return DSL.dsl().alterSchemaIfExists(schema);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static AlterSequenceStep<Number> alterSequence(String sequence2) {
        return DSL.dsl().alterSequence(sequence2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static AlterSequenceStep<Number> alterSequence(Name sequence2) {
        return DSL.dsl().alterSequence(sequence2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static <T extends Number> AlterSequenceStep<T> alterSequence(Sequence<T> sequence2) {
        return DSL.dsl().alterSequence(sequence2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static AlterSequenceStep<Number> alterSequenceIfExists(String sequence2) {
        return DSL.dsl().alterSequenceIfExists(sequence2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static AlterSequenceStep<Number> alterSequenceIfExists(Name sequence2) {
        return DSL.dsl().alterSequenceIfExists(sequence2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static <T extends Number> AlterSequenceStep<T> alterSequenceIfExists(Sequence<T> sequence2) {
        return DSL.dsl().alterSequenceIfExists(sequence2);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public static AlterTypeStep alterType(String type) {
        return DSL.dsl().alterType(type);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public static AlterTypeStep alterType(Name type) {
        return DSL.dsl().alterType(type);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static AlterViewStep alterView(String view) {
        return DSL.dsl().alterView(view);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static AlterViewStep alterView(Name view) {
        return DSL.dsl().alterView(view);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static AlterViewStep alterView(Table<?> view) {
        return DSL.dsl().alterView(view);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static AlterViewStep alterViewIfExists(String view) {
        return DSL.dsl().alterViewIfExists(view);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static AlterViewStep alterViewIfExists(Name view) {
        return DSL.dsl().alterViewIfExists(view);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static AlterViewStep alterViewIfExists(Table<?> view) {
        return DSL.dsl().alterViewIfExists(view);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static AlterViewStep alterView(Table<?> view, Field<?> ... fields) {
        return DSL.dsl().alterView(view, fields);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static AlterViewStep alterView(Table<?> view, Collection<? extends Field<?>> fields) {
        return DSL.dsl().alterView(view, fields);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CommentOnIsStep commentOnTable(String table) {
        return DSL.dsl().commentOnTable(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CommentOnIsStep commentOnTable(Name table) {
        return DSL.dsl().commentOnTable(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CommentOnIsStep commentOnTable(Table<?> table) {
        return DSL.dsl().commentOnTable(table);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CommentOnIsStep commentOnView(String view) {
        return DSL.dsl().commentOnView(view);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CommentOnIsStep commentOnView(Name view) {
        return DSL.dsl().commentOnView(view);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CommentOnIsStep commentOnView(Table<?> view) {
        return DSL.dsl().commentOnView(view);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CommentOnIsStep commentOnColumn(String field2) {
        return DSL.dsl().commentOnColumn(field2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CommentOnIsStep commentOnColumn(Name field2) {
        return DSL.dsl().commentOnColumn(field2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CommentOnIsStep commentOnColumn(Field<?> field2) {
        return DSL.dsl().commentOnColumn(field2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateDatabaseFinalStep createDatabase(String database) {
        return DSL.dsl().createDatabase(database);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateDatabaseFinalStep createDatabase(Name database) {
        return DSL.dsl().createDatabase(database);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateDatabaseFinalStep createDatabase(Catalog database) {
        return DSL.dsl().createDatabase(database);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateDatabaseFinalStep createDatabaseIfNotExists(String database) {
        return DSL.dsl().createDatabaseIfNotExists(database);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateDatabaseFinalStep createDatabaseIfNotExists(Name database) {
        return DSL.dsl().createDatabaseIfNotExists(database);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateDatabaseFinalStep createDatabaseIfNotExists(Catalog database) {
        return DSL.dsl().createDatabaseIfNotExists(database);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateDomainAsStep createDomain(String domain) {
        return DSL.dsl().createDomain(domain);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateDomainAsStep createDomain(Name domain) {
        return DSL.dsl().createDomain(domain);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateDomainAsStep createDomain(Domain<?> domain) {
        return DSL.dsl().createDomain(domain);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateDomainAsStep createDomainIfNotExists(String domain) {
        return DSL.dsl().createDomainIfNotExists(domain);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateDomainAsStep createDomainIfNotExists(Name domain) {
        return DSL.dsl().createDomainIfNotExists(domain);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateDomainAsStep createDomainIfNotExists(Domain<?> domain) {
        return DSL.dsl().createDomainIfNotExists(domain);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public static CreateFunctionParametersStep createFunction(String function) {
        return DSL.dsl().createFunction(function);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public static CreateFunctionParametersStep createFunction(Name function) {
        return DSL.dsl().createFunction(function);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public static CreateFunctionParametersStep createOrReplaceFunction(String function) {
        return DSL.dsl().createOrReplaceFunction(function);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public static CreateFunctionParametersStep createOrReplaceFunction(Name function) {
        return DSL.dsl().createOrReplaceFunction(function);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateIndexStep createIndex(String index) {
        return DSL.dsl().createIndex(index);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateIndexStep createIndex(Name index) {
        return DSL.dsl().createIndex(index);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateIndexStep createIndex(Index index) {
        return DSL.dsl().createIndex(index);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateIndexStep createIndex() {
        return DSL.dsl().createIndex();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateIndexStep createIndexIfNotExists(String index) {
        return DSL.dsl().createIndexIfNotExists(index);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateIndexStep createIndexIfNotExists(Name index) {
        return DSL.dsl().createIndexIfNotExists(index);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateIndexStep createIndexIfNotExists(Index index) {
        return DSL.dsl().createIndexIfNotExists(index);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateIndexStep createIndexIfNotExists() {
        return DSL.dsl().createIndexIfNotExists();
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateIndexStep createUniqueIndex(String index) {
        return DSL.dsl().createUniqueIndex(index);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateIndexStep createUniqueIndex(Name index) {
        return DSL.dsl().createUniqueIndex(index);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateIndexStep createUniqueIndex(Index index) {
        return DSL.dsl().createUniqueIndex(index);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateIndexStep createUniqueIndex() {
        return DSL.dsl().createUniqueIndex();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateIndexStep createUniqueIndexIfNotExists(String index) {
        return DSL.dsl().createUniqueIndexIfNotExists(index);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateIndexStep createUniqueIndexIfNotExists(Name index) {
        return DSL.dsl().createUniqueIndexIfNotExists(index);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateIndexStep createUniqueIndexIfNotExists(Index index) {
        return DSL.dsl().createUniqueIndexIfNotExists(index);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateIndexStep createUniqueIndexIfNotExists() {
        return DSL.dsl().createUniqueIndexIfNotExists();
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_11, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public static CreateProcedureParametersStep createProcedure(String procedure) {
        return DSL.dsl().createProcedure(procedure);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_11, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public static CreateProcedureParametersStep createProcedure(Name procedure) {
        return DSL.dsl().createProcedure(procedure);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_11, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public static CreateProcedureParametersStep createOrReplaceProcedure(String procedure) {
        return DSL.dsl().createOrReplaceProcedure(procedure);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_11, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public static CreateProcedureParametersStep createOrReplaceProcedure(Name procedure) {
        return DSL.dsl().createOrReplaceProcedure(procedure);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static CreateTableElementListStep createTable(String table) {
        return DSL.dsl().createTable(table);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static CreateTableElementListStep createTable(Name table) {
        return DSL.dsl().createTable(table);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static CreateTableElementListStep createTable(Table<?> table) {
        return DSL.dsl().createTable(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateTableElementListStep createTableIfNotExists(String table) {
        return DSL.dsl().createTableIfNotExists(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateTableElementListStep createTableIfNotExists(Name table) {
        return DSL.dsl().createTableIfNotExists(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateTableElementListStep createTableIfNotExists(Table<?> table) {
        return DSL.dsl().createTableIfNotExists(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateTableElementListStep createTemporaryTable(String table) {
        return DSL.dsl().createTemporaryTable(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateTableElementListStep createTemporaryTable(Name table) {
        return DSL.dsl().createTemporaryTable(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateTableElementListStep createTemporaryTable(Table<?> table) {
        return DSL.dsl().createTemporaryTable(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateTableElementListStep createTemporaryTableIfNotExists(String table) {
        return DSL.dsl().createTemporaryTableIfNotExists(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateTableElementListStep createTemporaryTableIfNotExists(Name table) {
        return DSL.dsl().createTemporaryTableIfNotExists(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateTableElementListStep createTemporaryTableIfNotExists(Table<?> table) {
        return DSL.dsl().createTemporaryTableIfNotExists(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateTableElementListStep createGlobalTemporaryTable(String table) {
        return DSL.dsl().createGlobalTemporaryTable(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateTableElementListStep createGlobalTemporaryTable(Name table) {
        return DSL.dsl().createGlobalTemporaryTable(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateTableElementListStep createGlobalTemporaryTable(Table<?> table) {
        return DSL.dsl().createGlobalTemporaryTable(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateTableElementListStep createGlobalTemporaryTableIfNotExists(String table) {
        return DSL.dsl().createGlobalTemporaryTableIfNotExists(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateTableElementListStep createGlobalTemporaryTableIfNotExists(Name table) {
        return DSL.dsl().createGlobalTemporaryTableIfNotExists(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateTableElementListStep createGlobalTemporaryTableIfNotExists(Table<?> table) {
        return DSL.dsl().createGlobalTemporaryTableIfNotExists(table);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public static CreateTriggerEventStep createTrigger(String trigger) {
        return DSL.dsl().createTrigger(trigger);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public static CreateTriggerEventStep createTrigger(Name trigger) {
        return DSL.dsl().createTrigger(trigger);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public static CreateTriggerEventStep createOrReplaceTrigger(String trigger) {
        return DSL.dsl().createOrReplaceTrigger(trigger);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public static CreateTriggerEventStep createOrReplaceTrigger(Name trigger) {
        return DSL.dsl().createOrReplaceTrigger(trigger);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateSchemaFinalStep createSchema(String schema) {
        return DSL.dsl().createSchema(schema);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateSchemaFinalStep createSchema(Name schema) {
        return DSL.dsl().createSchema(schema);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateSchemaFinalStep createSchema(Schema schema) {
        return DSL.dsl().createSchema(schema);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateSchemaFinalStep createSchemaIfNotExists(String schema) {
        return DSL.dsl().createSchemaIfNotExists(schema);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateSchemaFinalStep createSchemaIfNotExists(Name schema) {
        return DSL.dsl().createSchemaIfNotExists(schema);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateSchemaFinalStep createSchemaIfNotExists(Schema schema) {
        return DSL.dsl().createSchemaIfNotExists(schema);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateSequenceFlagsStep createSequence(String sequence2) {
        return DSL.dsl().createSequence(sequence2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateSequenceFlagsStep createSequence(Name sequence2) {
        return DSL.dsl().createSequence(sequence2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateSequenceFlagsStep createSequence(Sequence<?> sequence2) {
        return DSL.dsl().createSequence(sequence2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateSequenceFlagsStep createSequenceIfNotExists(String sequence2) {
        return DSL.dsl().createSequenceIfNotExists(sequence2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateSequenceFlagsStep createSequenceIfNotExists(Name sequence2) {
        return DSL.dsl().createSequenceIfNotExists(sequence2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static CreateSequenceFlagsStep createSequenceIfNotExists(Sequence<?> sequence2) {
        return DSL.dsl().createSequenceIfNotExists(sequence2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropDatabaseFinalStep dropDatabase(String database) {
        return DSL.dsl().dropDatabase(database);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropDatabaseFinalStep dropDatabase(Name database) {
        return DSL.dsl().dropDatabase(database);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropDatabaseFinalStep dropDatabase(Catalog database) {
        return DSL.dsl().dropDatabase(database);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropDatabaseFinalStep dropDatabaseIfExists(String database) {
        return DSL.dsl().dropDatabaseIfExists(database);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropDatabaseFinalStep dropDatabaseIfExists(Name database) {
        return DSL.dsl().dropDatabaseIfExists(database);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropDatabaseFinalStep dropDatabaseIfExists(Catalog database) {
        return DSL.dsl().dropDatabaseIfExists(database);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropDomainCascadeStep dropDomain(String domain) {
        return DSL.dsl().dropDomain(domain);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropDomainCascadeStep dropDomain(Name domain) {
        return DSL.dsl().dropDomain(domain);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropDomainCascadeStep dropDomain(Domain<?> domain) {
        return DSL.dsl().dropDomain(domain);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropDomainCascadeStep dropDomainIfExists(String domain) {
        return DSL.dsl().dropDomainIfExists(domain);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropDomainCascadeStep dropDomainIfExists(Name domain) {
        return DSL.dsl().dropDomainIfExists(domain);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropDomainCascadeStep dropDomainIfExists(Domain<?> domain) {
        return DSL.dsl().dropDomainIfExists(domain);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public static DDLQuery dropFunction(String function) {
        return DSL.dsl().dropFunction(function);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public static DDLQuery dropFunction(Name function) {
        return DSL.dsl().dropFunction(function);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public static DDLQuery dropFunctionIfExists(String function) {
        return DSL.dsl().dropFunctionIfExists(function);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public static DDLQuery dropFunctionIfExists(Name function) {
        return DSL.dsl().dropFunctionIfExists(function);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropIndexOnStep dropIndex(String index) {
        return DSL.dsl().dropIndex(index);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropIndexOnStep dropIndex(Name index) {
        return DSL.dsl().dropIndex(index);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropIndexOnStep dropIndex(Index index) {
        return DSL.dsl().dropIndex(index);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropIndexOnStep dropIndexIfExists(String index) {
        return DSL.dsl().dropIndexIfExists(index);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropIndexOnStep dropIndexIfExists(Name index) {
        return DSL.dsl().dropIndexIfExists(index);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropIndexOnStep dropIndexIfExists(Index index) {
        return DSL.dsl().dropIndexIfExists(index);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public static DDLQuery dropProcedure(String procedure) {
        return DSL.dsl().dropProcedure(procedure);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public static DDLQuery dropProcedure(Name procedure) {
        return DSL.dsl().dropProcedure(procedure);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public static DDLQuery dropProcedureIfExists(String procedure) {
        return DSL.dsl().dropProcedureIfExists(procedure);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public static DDLQuery dropProcedureIfExists(Name procedure) {
        return DSL.dsl().dropProcedureIfExists(procedure);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropSchemaStep dropSchema(String schema) {
        return DSL.dsl().dropSchema(schema);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropSchemaStep dropSchema(Name schema) {
        return DSL.dsl().dropSchema(schema);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropSchemaStep dropSchema(Schema schema) {
        return DSL.dsl().dropSchema(schema);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropSchemaStep dropSchemaIfExists(String schema) {
        return DSL.dsl().dropSchemaIfExists(schema);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropSchemaStep dropSchemaIfExists(Name schema) {
        return DSL.dsl().dropSchemaIfExists(schema);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropSchemaStep dropSchemaIfExists(Schema schema) {
        return DSL.dsl().dropSchemaIfExists(schema);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropSequenceFinalStep dropSequence(String sequence2) {
        return DSL.dsl().dropSequence(sequence2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropSequenceFinalStep dropSequence(Name sequence2) {
        return DSL.dsl().dropSequence(sequence2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropSequenceFinalStep dropSequence(Sequence<?> sequence2) {
        return DSL.dsl().dropSequence(sequence2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropSequenceFinalStep dropSequenceIfExists(String sequence2) {
        return DSL.dsl().dropSequenceIfExists(sequence2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropSequenceFinalStep dropSequenceIfExists(Name sequence2) {
        return DSL.dsl().dropSequenceIfExists(sequence2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropSequenceFinalStep dropSequenceIfExists(Sequence<?> sequence2) {
        return DSL.dsl().dropSequenceIfExists(sequence2);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static DropTableStep dropTable(String table) {
        return DSL.dsl().dropTable(table);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static DropTableStep dropTable(Name table) {
        return DSL.dsl().dropTable(table);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static DropTableStep dropTable(Table<?> table) {
        return DSL.dsl().dropTable(table);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropTableStep dropTableIfExists(String table) {
        return DSL.dsl().dropTableIfExists(table);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropTableStep dropTableIfExists(Name table) {
        return DSL.dsl().dropTableIfExists(table);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropTableStep dropTableIfExists(Table<?> table) {
        return DSL.dsl().dropTableIfExists(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropTableStep dropTemporaryTable(String table) {
        return DSL.dsl().dropTemporaryTable(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropTableStep dropTemporaryTable(Name table) {
        return DSL.dsl().dropTemporaryTable(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropTableStep dropTemporaryTable(Table<?> table) {
        return DSL.dsl().dropTemporaryTable(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropTableStep dropTemporaryTableIfExists(String table) {
        return DSL.dsl().dropTemporaryTableIfExists(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropTableStep dropTemporaryTableIfExists(Name table) {
        return DSL.dsl().dropTemporaryTableIfExists(table);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropTableStep dropTemporaryTableIfExists(Table<?> table) {
        return DSL.dsl().dropTemporaryTableIfExists(table);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public static DDLQuery dropTrigger(String trigger) {
        return DSL.dsl().dropTrigger(trigger);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public static DDLQuery dropTrigger(Name trigger) {
        return DSL.dsl().dropTrigger(trigger);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public static DDLQuery dropTriggerIfExists(String trigger) {
        return DSL.dsl().dropTriggerIfExists(trigger);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public static DDLQuery dropTriggerIfExists(Name trigger) {
        return DSL.dsl().dropTriggerIfExists(trigger);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropViewFinalStep dropView(String view) {
        return DSL.dsl().dropView(view);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropViewFinalStep dropView(Name view) {
        return DSL.dsl().dropView(view);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropViewFinalStep dropView(Table<?> view) {
        return DSL.dsl().dropView(view);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropViewFinalStep dropViewIfExists(String view) {
        return DSL.dsl().dropViewIfExists(view);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropViewFinalStep dropViewIfExists(Name view) {
        return DSL.dsl().dropViewIfExists(view);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static DropViewFinalStep dropViewIfExists(Table<?> view) {
        return DSL.dsl().dropViewIfExists(view);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static GrantOnStep grant(Privilege privileges) {
        return DSL.dsl().grant(privileges);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static GrantOnStep grant(Privilege ... privileges) {
        return DSL.dsl().grant(privileges);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static GrantOnStep grant(Collection<? extends Privilege> privileges) {
        return DSL.dsl().grant(privileges);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static RevokeOnStep revoke(Privilege privileges) {
        return DSL.dsl().revoke(privileges);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static RevokeOnStep revoke(Privilege ... privileges) {
        return DSL.dsl().revoke(privileges);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static RevokeOnStep revoke(Collection<? extends Privilege> privileges) {
        return DSL.dsl().revoke(privileges);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB_22, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static RevokeOnStep revokeGrantOptionFor(Privilege privileges) {
        return DSL.dsl().revokeGrantOptionFor(privileges);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB_22, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static RevokeOnStep revokeGrantOptionFor(Privilege ... privileges) {
        return DSL.dsl().revokeGrantOptionFor(privileges);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB_22, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static RevokeOnStep revokeGrantOptionFor(Collection<? extends Privilege> privileges) {
        return DSL.dsl().revokeGrantOptionFor(privileges);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static RowCountQuery set(String name, Param<?> value) {
        return DSL.dsl().set(name, value);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static RowCountQuery set(Name name, Param<?> value) {
        return DSL.dsl().set(name, value);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static RowCountQuery setLocal(String name, Param<?> value) {
        return DSL.dsl().setLocal(name, value);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static RowCountQuery setLocal(Name name, Param<?> value) {
        return DSL.dsl().setLocal(name, value);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    @CheckReturnValue
    public static RowCountQuery setCatalog(String catalog) {
        return DSL.dsl().setCatalog(catalog);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    @CheckReturnValue
    public static RowCountQuery setCatalog(Name catalog) {
        return DSL.dsl().setCatalog(catalog);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    @CheckReturnValue
    public static RowCountQuery setCatalog(Catalog catalog) {
        return DSL.dsl().setCatalog(catalog);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static RowCountQuery setSchema(String schema) {
        return DSL.dsl().setSchema(schema);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static RowCountQuery setSchema(Name schema) {
        return DSL.dsl().setSchema(schema);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static RowCountQuery setSchema(Schema schema) {
        return DSL.dsl().setSchema(schema);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static TruncateIdentityStep<Record> truncate(String table) {
        return DSL.dsl().truncate(table);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static TruncateIdentityStep<Record> truncate(Name table) {
        return DSL.dsl().truncate(table);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static <R extends Record> TruncateIdentityStep<R> truncate(Table<R> table) {
        return DSL.dsl().truncate(table);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static TruncateIdentityStep<Record> truncateTable(String table) {
        return DSL.dsl().truncateTable(table);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static TruncateIdentityStep<Record> truncateTable(Name table) {
        return DSL.dsl().truncateTable(table);
    }

    @Support
    @NotNull
    @CheckReturnValue
    public static <R extends Record> TruncateIdentityStep<R> truncateTable(Table<R> table) {
        return DSL.dsl().truncateTable(table);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public static CallArgsStep call(String procedure) {
        return DSL.dsl().call(procedure);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public static CallArgsStep call(Name procedure) {
        return DSL.dsl().call(procedure);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static Query startTransaction() {
        return DSL.dsl().startTransaction();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static Query savepoint(String name) {
        return DSL.dsl().savepoint(name);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static Query savepoint(Name name) {
        return DSL.dsl().savepoint(name);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static Query releaseSavepoint(String name) {
        return DSL.dsl().releaseSavepoint(name);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static Query releaseSavepoint(Name name) {
        return DSL.dsl().releaseSavepoint(name);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static Query commit() {
        return DSL.dsl().commit();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public static RollbackToSavepointStep rollback() {
        return DSL.dsl().rollback();
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateViewAsStep<Record> createView(String view, String ... fields) {
        return DSL.dsl().createView(view, fields);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateViewAsStep<Record> createView(Name view, Name ... fields) {
        return DSL.dsl().createView(view, fields);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateViewAsStep<Record> createView(Table<?> view, Field<?> ... fields) {
        return DSL.dsl().createView(view, fields);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateViewAsStep<Record> createView(String view, java.util.function.Function<? super Field<?>, ? extends String> fieldNameFunction) {
        return DSL.dsl().createView(view, fieldNameFunction);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateViewAsStep<Record> createView(Name view, java.util.function.Function<? super Field<?>, ? extends Name> fieldNameFunction) {
        return DSL.dsl().createView(view, fieldNameFunction);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateViewAsStep<Record> createView(Table<?> view, java.util.function.Function<? super Field<?>, ? extends Field<?>> fieldNameFunction) {
        return DSL.dsl().createView(view, fieldNameFunction);
    }

    @Support(value={SQLDialect.ASE_16_0, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB_21, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateViewAsStep<Record> createOrReplaceView(String view, String ... fields) {
        return DSL.dsl().createOrReplaceView(view, fields);
    }

    @Support(value={SQLDialect.ASE_16_0, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB_21, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateViewAsStep<Record> createOrReplaceView(Name view, Name ... fields) {
        return DSL.dsl().createOrReplaceView(view, fields);
    }

    @Support(value={SQLDialect.ASE_16_0, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB_21, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateViewAsStep<Record> createOrReplaceView(Table<?> view, Field<?> ... fields) {
        return DSL.dsl().createOrReplaceView(view, fields);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ASE_16_0, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB_21, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateViewAsStep<Record> createOrReplaceView(String view, java.util.function.Function<? super Field<?>, ? extends String> fieldNameFunction) {
        return DSL.dsl().createOrReplaceView(view, fieldNameFunction);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ASE_16_0, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB_21, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateViewAsStep<Record> createOrReplaceView(Name view, java.util.function.Function<? super Field<?>, ? extends Name> fieldNameFunction) {
        return DSL.dsl().createOrReplaceView(view, fieldNameFunction);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ASE_16_0, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB_21, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateViewAsStep<Record> createOrReplaceView(Table<?> view, java.util.function.Function<? super Field<?>, ? extends Field<?>> fieldNameFunction) {
        return DSL.dsl().createOrReplaceView(view, fieldNameFunction);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateViewAsStep<Record> createViewIfNotExists(String view, String ... fields) {
        return DSL.dsl().createViewIfNotExists(view, fields);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateViewAsStep<Record> createViewIfNotExists(Name view, Name ... fields) {
        return DSL.dsl().createViewIfNotExists(view, fields);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateViewAsStep<Record> createViewIfNotExists(Table<?> view, Field<?> ... fields) {
        return DSL.dsl().createViewIfNotExists(view, fields);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateViewAsStep<Record> createViewIfNotExists(String view, java.util.function.Function<? super Field<?>, ? extends String> fieldNameFunction) {
        return DSL.dsl().createViewIfNotExists(view, fieldNameFunction);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateViewAsStep<Record> createViewIfNotExists(Name view, java.util.function.Function<? super Field<?>, ? extends Name> fieldNameFunction) {
        return DSL.dsl().createViewIfNotExists(view, fieldNameFunction);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateViewAsStep<Record> createViewIfNotExists(Table<?> view, java.util.function.Function<? super Field<?>, ? extends Field<?>> fieldNameFunction) {
        return DSL.dsl().createViewIfNotExists(view, fieldNameFunction);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateTypeStep createType(String type) {
        return DSL.dsl().createType(type);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static CreateTypeStep createType(Name type) {
        return DSL.dsl().createType(type);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static DropTypeStep dropType(String type) {
        return DSL.dsl().dropType(type);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static DropTypeStep dropType(Name type) {
        return DSL.dsl().dropType(type);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static DropTypeStep dropType(String ... type) {
        return DSL.dsl().dropType(type);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static DropTypeStep dropType(Name ... type) {
        return DSL.dsl().dropType(type);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static DropTypeStep dropType(Collection<?> type) {
        return DSL.dsl().dropType(type);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static DropTypeStep dropTypeIfExists(String type) {
        return DSL.dsl().dropTypeIfExists(type);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static DropTypeStep dropTypeIfExists(Name type) {
        return DSL.dsl().dropTypeIfExists(type);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static DropTypeStep dropTypeIfExists(String ... type) {
        return DSL.dsl().dropTypeIfExists(type);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static DropTypeStep dropTypeIfExists(Name ... type) {
        return DSL.dsl().dropTypeIfExists(type);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static DropTypeStep dropTypeIfExists(Collection<?> type) {
        return DSL.dsl().dropTypeIfExists(type);
    }

    @Support
    @NotNull
    public static AlterTableStep alterTable(String table) {
        return DSL.dsl().alterTable(table);
    }

    @Support
    @NotNull
    public static AlterTableStep alterTable(Name table) {
        return DSL.dsl().alterTable(table);
    }

    @Support
    @NotNull
    public static AlterTableStep alterTable(Table<?> table) {
        return DSL.dsl().alterTable(table);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.IGNITE, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static AlterTableStep alterTableIfExists(String table) {
        return DSL.dsl().alterTableIfExists(table);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.IGNITE, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static AlterTableStep alterTableIfExists(Name table) {
        return DSL.dsl().alterTableIfExists(table);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.IGNITE, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static AlterTableStep alterTableIfExists(Table<?> table) {
        return DSL.dsl().alterTableIfExists(table);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record> QuantifiedSelect<R> all(Select<R> select) {
        return new QuantifiedSelectImpl<R>(QOM.Quantifier.ALL, select);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> QuantifiedSelect<Record1<T>> all(T ... array) {
        return array instanceof Field[] ? DSL.all((Field[])array) : new QuantifiedArray(QOM.Quantifier.ALL, DSL.val(array));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> QuantifiedSelect<Record1<T>> all(Field<T[]> array) {
        return new QuantifiedArray(QOM.Quantifier.ALL, array);
    }

    @Support
    @SafeVarargs
    @NotNull
    public static <T> QuantifiedSelect<Record1<T>> all(Field<T> ... fields) {
        return new QuantifiedArray(QOM.Quantifier.ALL, new Array<T>(Arrays.asList(fields)));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record> QuantifiedSelect<R> any(Select<R> select) {
        return new QuantifiedSelectImpl<R>(QOM.Quantifier.ANY, select);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> QuantifiedSelect<Record1<T>> any(T ... array) {
        return array instanceof Field[] ? DSL.any((Field[])array) : new QuantifiedArray(QOM.Quantifier.ANY, DSL.val(array));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> QuantifiedSelect<Record1<T>> any(Field<T[]> array) {
        return new QuantifiedArray(QOM.Quantifier.ANY, array);
    }

    @Support
    @SafeVarargs
    @NotNull
    public static <T> QuantifiedSelect<Record1<T>> any(Field<T> ... fields) {
        return new QuantifiedArray(QOM.Quantifier.ANY, new Array<T>(Arrays.asList(fields)));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER})
    @NotNull
    public static Collation collation(String collation) {
        return DSL.collation(DSL.name(collation));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER})
    @NotNull
    public static Collation collation(Name collation) {
        return new CollationImpl(collation);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @NotNull
    public static CharacterSet characterSet(String characterSet) {
        return DSL.characterSet(DSL.name(characterSet));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @NotNull
    public static CharacterSet characterSet(Name characterSet) {
        return new CharacterSetImpl(characterSet);
    }

    @PlainSQL
    @Support
    @NotNull
    public static Privilege privilege(String privilege) {
        return DSL.privilege(DSL.keyword(privilege));
    }

    @Support
    @NotNull
    static Privilege privilege(Keyword privilege) {
        return new PrivilegeImpl(privilege);
    }

    @Support
    @NotNull
    public static User user(String name) {
        return DSL.user(DSL.name(name));
    }

    @Support
    @NotNull
    public static User user(Name name) {
        return new UserImpl(name);
    }

    @Support
    @NotNull
    public static Role role(String name) {
        return DSL.role(DSL.name(name));
    }

    @Support
    @NotNull
    public static Role role(Name name) {
        return new RoleImpl(name);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2})
    @NotNull
    public static <R extends Record> Table<R> oldTable(Update<R> query) {
        return new DataChangeDeltaTable<R>(QOM.ResultOption.OLD, query);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.POSTGRES})
    @NotNull
    public static <R extends Record> Table<R> oldTable(Delete<R> query) {
        return new DataChangeDeltaTable<R>(QOM.ResultOption.OLD, query);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.H2})
    @NotNull
    public static <R extends Record> Table<R> oldTable(Merge<R> query) {
        return new DataChangeDeltaTable<R>(QOM.ResultOption.OLD, query);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.H2})
    @NotNull
    public static <R extends Record> Table<R> newTable(Insert<R> query) {
        return new DataChangeDeltaTable<R>(QOM.ResultOption.NEW, query);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.H2})
    @NotNull
    public static <R extends Record> Table<R> newTable(Update<R> query) {
        return new DataChangeDeltaTable<R>(QOM.ResultOption.NEW, query);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.H2})
    @NotNull
    public static <R extends Record> Table<R> newTable(Merge<R> query) {
        return new DataChangeDeltaTable<R>(QOM.ResultOption.NEW, query);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.POSTGRES})
    @NotNull
    public static <R extends Record> Table<R> finalTable(Insert<R> query) {
        return new DataChangeDeltaTable<R>(QOM.ResultOption.FINAL, query);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.POSTGRES})
    @NotNull
    public static <R extends Record> Table<R> finalTable(Update<R> query) {
        return new DataChangeDeltaTable<R>(QOM.ResultOption.FINAL, query);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.H2})
    @NotNull
    public static <R extends Record> Table<R> finalTable(Merge<R> query) {
        return new DataChangeDeltaTable<R>(QOM.ResultOption.FINAL, query);
    }

    @Support
    @NotNull
    public static <R extends Record> Table<R> table(Select<R> select) {
        return select.asTable();
    }

    @Support
    @NotNull
    public static <R extends Record> Table<R> table(Result<R> result) {
        return DSL.values0(Tools.map(result, r -> r.valuesRow(), Row[]::new)).as(DSL.name("v"), Tools.map(result.fields(), f -> f.getUnqualifiedName(), Name[]::new));
    }

    @Support
    @NotNull
    public static <R extends Record> Table<R> table(R record) {
        return DSL.table(new Record[]{record});
    }

    @Support
    @NotNull
    public static <R extends Record> Table<R> table(R ... records) {
        if (records == null || records.length == 0) {
            return new Dual();
        }
        ResultImpl result = new ResultImpl(Tools.configuration(records[0]), (AbstractRow)records[0].fieldsRow());
        result.addAll(Arrays.asList(records));
        return DSL.table(result);
    }

    @Support
    @NotNull
    public static Table<?> table(Collection<?> list) {
        return DSL.unnest(list);
    }

    @Support
    @NotNull
    public static Table<?> table(Object[] array) {
        return DSL.unnest(array);
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public static Table<?> table(ArrayRecord<?> array) {
        return DSL.unnest(array);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE})
    @NotNull
    public static Table<?> table(Field<?> cursor) {
        return DSL.unnest(cursor);
    }

    @Support
    @NotNull
    public static Table<?> unnest(Collection<?> list) {
        return DSL.unnest(list.toArray());
    }

    @Support
    @NotNull
    public static Table<?> unnest(Object[] array) {
        boolean notEmpty;
        boolean bl = notEmpty = !Tools.isEmpty(array);
        if (notEmpty && array[0] instanceof Field) {
            return new ArrayOfValues(Tools.fieldsArray(array));
        }
        if (notEmpty && array.getClass() == Object[].class) {
            return DSL.unnest0(DSL.val(Tools.mostSpecificArray(array)));
        }
        return DSL.unnest0(DSL.val(array));
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public static Table<?> unnest(ArrayRecord<?> array) {
        return DSL.unnest0(DSL.val(array));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE})
    @NotNull
    public static Table<?> unnest(Field<?> cursor) {
        return DSL.unnest0(cursor);
    }

    private static Table<?> unnest0(Field<?> cursor) {
        if (cursor == null) {
            throw new IllegalArgumentException();
        }
        if (cursor.getType() == Result.class) {
            return new FunctionTable(cursor);
        }
        if (ArrayConstant.class.isAssignableFrom(cursor.getClass())) {
            return new ArrayTable(cursor);
        }
        if (ArrayRecord.class.isAssignableFrom(cursor.getDataType().getType())) {
            return new ArrayTable(cursor);
        }
        if (cursor.getType().isArray() && cursor.getType() != byte[].class) {
            return new ArrayTable(cursor);
        }
        throw new SQLDialectNotSupportedException("Converting arbitrary types into array tables is currently not supported");
    }

    @Support
    @NotNull
    public static Table<Record> dual() {
        return new Dual(true);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Table<Record1<Integer>> generateSeries(int from, int to) {
        return DSL.generateSeries(DSL.val(from), DSL.val(to));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Table<Record1<Integer>> generateSeries(int from, Field<Integer> to) {
        return DSL.generateSeries(DSL.val(from), Tools.nullSafe(to));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Table<Record1<Integer>> generateSeries(Field<Integer> from, int to) {
        return new GenerateSeries(Tools.nullSafe(from), DSL.val(to));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Table<Record1<Integer>> generateSeries(Field<Integer> from, Field<Integer> to) {
        return new GenerateSeries(Tools.nullSafe(from), Tools.nullSafe(to));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Table<Record1<Integer>> generateSeries(int from, int to, int step) {
        return DSL.generateSeries(DSL.val(from), DSL.val(to), DSL.val(step));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Table<Record1<Integer>> generateSeries(int from, Field<Integer> to, int step) {
        return DSL.generateSeries(DSL.val(from), Tools.nullSafe(to), DSL.val(step));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Table<Record1<Integer>> generateSeries(Field<Integer> from, int to, int step) {
        return new GenerateSeries(Tools.nullSafe(from), DSL.val(to), DSL.val(step));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Table<Record1<Integer>> generateSeries(Field<Integer> from, Field<Integer> to, int step) {
        return new GenerateSeries(Tools.nullSafe(from), Tools.nullSafe(to), DSL.val(step));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Table<Record1<Integer>> generateSeries(int from, int to, Field<Integer> step) {
        return DSL.generateSeries(DSL.val(from), DSL.val(to), Tools.nullSafe(step));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Table<Record1<Integer>> generateSeries(int from, Field<Integer> to, Field<Integer> step) {
        return DSL.generateSeries(DSL.val(from), Tools.nullSafe(to), Tools.nullSafe(step));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Table<Record1<Integer>> generateSeries(Field<Integer> from, int to, Field<Integer> step) {
        return new GenerateSeries(Tools.nullSafe(from), DSL.val(to), Tools.nullSafe(step));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Table<Record1<Integer>> generateSeries(Field<Integer> from, Field<Integer> to, Field<Integer> step) {
        return new GenerateSeries(Tools.nullSafe(from), Tools.nullSafe(to), Tools.nullSafe(step));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.FIREBIRD_4_0, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_9_3, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record> Table<R> lateral(TableLike<R> table) {
        return new Lateral<R>(table.asTable());
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES})
    @NotNull
    public static Table<Record> rowsFrom(Table<?> ... tables) {
        return new RowsFrom(tables);
    }

    @Support
    @NotNull
    public static Keyword keyword(String keyword) {
        return new KeywordImpl(keyword);
    }

    @Support
    @NotNull
    public static Name name(String unqualifiedName) {
        return new UnqualifiedName(unqualifiedName);
    }

    @Support
    @NotNull
    public static Name name(String ... qualifiedName) {
        if (qualifiedName == null || qualifiedName.length != 1) {
            return new QualifiedName(qualifiedName);
        }
        return new UnqualifiedName(qualifiedName[0]);
    }

    @Support
    @NotNull
    public static Name name(Name ... nameParts) {
        return new QualifiedName(nameParts);
    }

    @Support
    @NotNull
    public static Name name(Collection<String> qualifiedName) {
        return DSL.name(qualifiedName.toArray(Tools.EMPTY_STRING));
    }

    @Support
    @NotNull
    public static Name quotedName(String unqualifiedName) {
        return new UnqualifiedName(unqualifiedName, Name.Quoted.QUOTED);
    }

    @Support
    @NotNull
    public static Name quotedName(String ... qualifiedName) {
        if (qualifiedName == null || qualifiedName.length != 1) {
            return new QualifiedName(qualifiedName, Name.Quoted.QUOTED);
        }
        return new UnqualifiedName(qualifiedName[0], Name.Quoted.QUOTED);
    }

    @Support
    @NotNull
    public static Name quotedName(Collection<String> qualifiedName) {
        return DSL.quotedName(qualifiedName.toArray(Tools.EMPTY_STRING));
    }

    @Support
    @NotNull
    public static Name unquotedName(String unqualifiedName) {
        return new UnqualifiedName(unqualifiedName, Name.Quoted.UNQUOTED);
    }

    @Support
    @NotNull
    public static Name unquotedName(String ... qualifiedName) {
        if (qualifiedName == null || qualifiedName.length != 1) {
            return new QualifiedName(qualifiedName, Name.Quoted.UNQUOTED);
        }
        return new UnqualifiedName(qualifiedName[0], Name.Quoted.UNQUOTED);
    }

    @Support
    @NotNull
    public static Name unquotedName(Collection<String> qualifiedName) {
        return DSL.unquotedName(qualifiedName.toArray(Tools.EMPTY_STRING));
    }

    @Support
    @NotNull
    public static Name systemName(String unqualifiedName) {
        return new UnqualifiedName(unqualifiedName, Name.Quoted.SYSTEM);
    }

    @Support
    @NotNull
    public static Name systemName(String ... qualifiedName) {
        if (qualifiedName == null || qualifiedName.length != 1) {
            return new QualifiedName(qualifiedName, Name.Quoted.SYSTEM);
        }
        return new UnqualifiedName(qualifiedName[0], Name.Quoted.SYSTEM);
    }

    @Support
    @NotNull
    public static Name systemName(Collection<String> qualifiedName) {
        return DSL.systemName(qualifiedName.toArray(Tools.EMPTY_STRING));
    }

    @Support
    @NotNull
    public static QueryPart list(QueryPart ... parts) {
        return DSL.list(Arrays.asList(parts));
    }

    @Support
    @NotNull
    public static QueryPart list(Collection<? extends QueryPart> parts) {
        return new QueryPartList<QueryPart>((Iterable<? extends QueryPart>)parts);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<Object> defaultValue() {
        return DSL.default_();
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static <T> Field<T> defaultValue(Class<T> type) {
        return DSL.default_(type);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static <T> Field<T> defaultValue(DataType<T> type) {
        return DSL.default_(type);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static <T> Field<T> defaultValue(Field<T> field2) {
        return DSL.default_(field2);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<Object> default_() {
        return DSL.default_(Object.class);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static <T> Field<T> default_(Class<T> type) {
        return DSL.default_(DSL.getDataType(type));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static <T> Field<T> default_(DataType<T> type) {
        return new Default<T>(type);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static <T> Field<T> default_(Field<T> field2) {
        return DSL.default_(field2.getDataType());
    }

    @Support
    @NotNull
    public static Field<?> noField() {
        return NoField.INSTANCE;
    }

    @Support
    @NotNull
    public static <T> Field<T> noField(Class<T> type) {
        return DSL.noField(DSL.getDataType(type));
    }

    @Support
    @NotNull
    public static <T> Field<T> noField(DataType<T> type) {
        return new NoField<T>(type);
    }

    @Support
    @NotNull
    public static <T> Field<T> noField(Field<T> field2) {
        return DSL.noField(field2.getDataType());
    }

    @Support
    @NotNull
    public static Catalog catalog(String name) {
        return DSL.catalog(DSL.name(name));
    }

    @Support
    @NotNull
    public static Catalog catalog(Name name) {
        return new CatalogImpl(name);
    }

    @Support
    @NotNull
    public static Schema schema(String name) {
        return DSL.schema(DSL.name(name));
    }

    @Support
    @NotNull
    public static Schema schema(Name name) {
        return new SchemaImpl(name);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Sequence<BigInteger> sequence(Name name) {
        return DSL.sequence(name, BigInteger.class);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Sequence<T> sequence(Name name, Class<T> type) {
        return DSL.sequence(name, DSL.getDataType(type));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Sequence<T> sequence(Name name, DataType<T> type) {
        return new SequenceImpl<T>(name.unqualifiedName(), name.qualified() ? DSL.schema(name.qualifier()) : null, type, false);
    }

    @Support
    @NotNull
    public static Table<Record> table(Name name) {
        return new TableImpl<Record>(name);
    }

    @Support
    @NotNull
    public static Table<Record> table(Name name, Comment comment) {
        return new TableImpl<Record>(name, null, null, null, comment);
    }

    @Support
    @NotNull
    public static Field<Object> field(Name name) {
        return DSL.field(name, Object.class);
    }

    @Support
    @NotNull
    public static <T> Field<T> field(Name name, Class<T> type) {
        return DSL.field(name, DSL.getDataType(type));
    }

    @Support
    @NotNull
    public static <T> Field<T> field(Name name, DataType<T> type) {
        return DSL.field(name, type, null);
    }

    @Support
    @NotNull
    public static <T> Field<T> field(Name name, DataType<T> type, Comment comment) {
        return new TableFieldImpl(name, type, comment);
    }

    @Support
    @NotNull
    public static Index index(Name name) {
        return new IndexImpl(name);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE12C, SQLDialect.SQLSERVER2016})
    @NotNull
    @Pro
    public static Period<Timestamp> period(Name name) {
        return DSL.period(name, SQLDataType.TIMESTAMP);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE12C, SQLDialect.SQLSERVER2016})
    @NotNull
    @Pro
    public static <T> Period<T> period(Name name, Class<T> type) {
        return DSL.period(name, DefaultDataType.getDataType(null, type));
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE12C, SQLDialect.SQLSERVER2016})
    @NotNull
    @Pro
    public static <T> Period<T> period(Name name, DataType<T> type) {
        return new PeriodImpl<T>(name, type);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE12C, SQLDialect.SQLSERVER2016})
    @NotNull
    @Pro
    public static Period<Timestamp> systemTime() {
        return DSL.systemTime(SQLDataType.TIMESTAMP);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE12C, SQLDialect.SQLSERVER2016})
    @NotNull
    @Pro
    public static <T> Period<T> systemTime(Class<T> type) {
        return DSL.systemTime(DefaultDataType.getDataType(null, type));
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE12C, SQLDialect.SQLSERVER2016})
    @NotNull
    @Pro
    public static <T> Period<T> systemTime(DataType<T> type) {
        return DSL.period(Names.N_SYSTEM_TIME, type);
    }

    @Support
    @NotNull
    public static <T> Parameter<T> in(String name, DataType<T> type) {
        return DSL.in(DSL.name(name), type);
    }

    @Support
    @NotNull
    public static <T> Parameter<T> in(Name name, DataType<T> type) {
        return new ParameterImpl<T>(ParamMode.IN, name, type);
    }

    @Support
    @NotNull
    public static <T> Parameter<T> inOut(String name, DataType<T> type) {
        return DSL.inOut(DSL.name(name), type);
    }

    @Support
    @NotNull
    public static <T> Parameter<T> inOut(Name name, DataType<T> type) {
        return new ParameterImpl<T>(ParamMode.INOUT, name, type);
    }

    @Support
    @NotNull
    public static <T> Parameter<T> out(String name, DataType<T> type) {
        return DSL.out(DSL.name(name), type);
    }

    @Support
    @NotNull
    public static <T> Parameter<T> out(Name name, DataType<T> type) {
        return new ParameterImpl<T>(ParamMode.OUT, name, type);
    }

    @Support
    @NotNull
    public static Queries queries(Query ... queries) {
        return DSL.queries(Arrays.asList(queries));
    }

    @Support
    @NotNull
    public static Queries queries(Collection<? extends Query> queries) {
        return DSL.using(new DefaultConfiguration()).queries(queries);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Block begin(Statement ... statements) {
        return DSL.begin(Arrays.asList(statements));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Block begin(Collection<? extends Statement> statements) {
        return DSL.using(new DefaultConfiguration()).begin(statements);
    }

    @Support
    @PlainSQL
    @NotNull
    @Pro
    public static Statement statement(String sql) {
        return DSL.statement(DSL.sql(sql));
    }

    @Support
    @PlainSQL
    @NotNull
    @Pro
    public static Statement statement(String sql, Object ... bindings) {
        return DSL.statement(DSL.sql(sql, bindings));
    }

    @Support
    @PlainSQL
    @NotNull
    @Pro
    public static Statement statement(String sql, QueryPart ... parts) {
        return DSL.statement(DSL.sql(sql, parts));
    }

    @Support
    @PlainSQL
    @NotNull
    @Pro
    public static Statement statement(SQL sql) {
        return new SQLStatement(sql);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static Block statements(Statement ... statements) {
        return DSL.statements(Arrays.asList(statements));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static Block statements(Collection<? extends Statement> statements) {
        return DSL.using(new DefaultConfiguration()).statements(statements);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static <T> Variable<T> var(String name, DataType<T> type) {
        return DSL.var(DSL.name(name), type);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static <T> Variable<T> var(Name name, DataType<T> type) {
        return new VariableImpl<T>(name, type);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static <T> Variable<T> variable(String name, DataType<T> type) {
        return DSL.var(name, type);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static <T> Variable<T> variable(Name name, DataType<T> type) {
        return DSL.var(name, type);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static <T> Declaration<T> declare(Variable<T> variable) {
        return new DeclarationImpl<T>(variable, null);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static Statement declare(Variable<?> ... variables) {
        return DSL.declare(Arrays.asList(variables));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static Statement declare(Collection<? extends Variable<?>> variables) {
        return DSL.statements(Tools.map(variables, v -> DSL.declare(v)));
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static Statement execute(Query sql) {
        return DSL.execute(sql.getSQL());
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static IfThenStep if_(Condition condition) {
        return new IfImpl(null, condition, null, null);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static LoopStep while_(Condition condition) {
        return new LoopImpl(null, condition, null, false);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static RepeatUntilStep repeat(Statement ... statements) {
        return DSL.repeat(Arrays.asList(statements));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static RepeatUntilStep repeat(Collection<? extends Statement> statements) {
        return new LoopImpl(null, null, statements, true);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB_10_3, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static <T> ForInStep<T> for_(Variable<T> index) {
        return new ForImpl<T>(index, null, null, false, null);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static Statement loop(Statement ... statements) {
        return DSL.loop(Arrays.asList(statements));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static Statement loop(Collection<? extends Statement> statements) {
        return new LoopImpl(null, null, statements, false);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static Label label(String label) {
        return DSL.label(DSL.name(label));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static Label label(Name label) {
        return new LabelImpl(label);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static ExitWhenStep exit() {
        return Exit.INSTANCE;
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static ExitWhenStep exit(Label label) {
        return new Exit(label, null);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static Statement exitWhen(Condition condition) {
        return new Exit(null, condition);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static Statement exitWhen(Field<Boolean> condition) {
        return DSL.exitWhen(DSL.condition(condition));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static ContinueWhenStep continue_() {
        return Continue.INSTANCE;
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static ContinueWhenStep continue_(Label label) {
        return new Continue(label, null);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static Statement continueWhen(Condition condition) {
        return new Continue(null, condition);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static Statement continueWhen(Field<Boolean> condition) {
        return DSL.continueWhen(DSL.condition(condition));
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public static Link link(String name) {
        return DSL.link(name, null);
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public static Link link(String name, Schema schema) {
        return new LinkImpl(name, schema);
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public static Link link(Name name) {
        return new LinkImpl(name);
    }

    @Support
    @PlainSQL
    @NotNull
    public static SQL raw(String sql) {
        return new SQLImpl(sql, true, new Object[0]);
    }

    @Support
    @PlainSQL
    @NotNull
    public static SQL sql(String sql) {
        return DSL.sql(sql, new Object[0]);
    }

    @Support
    @PlainSQL
    @NotNull
    public static SQL sql(String sql, QueryPart ... parts) {
        return DSL.sql(sql, (Object[])parts);
    }

    @Support
    @PlainSQL
    @NotNull
    public static SQL sql(String sql, Object ... bindings) {
        return new SQLImpl(sql, false, bindings);
    }

    @Support
    @PlainSQL
    @NotNull
    public static RowCountQuery query(SQL sql) {
        return DSL.dsl().query(sql);
    }

    @Support
    @PlainSQL
    @NotNull
    public static RowCountQuery query(String sql) {
        return DSL.dsl().query(sql);
    }

    @Support
    @PlainSQL
    @NotNull
    public static RowCountQuery query(String sql, Object ... bindings) {
        return DSL.dsl().query(sql, bindings);
    }

    @Support
    @PlainSQL
    @NotNull
    public static RowCountQuery query(String sql, QueryPart ... parts) {
        return DSL.dsl().query(sql, parts);
    }

    @Support
    @PlainSQL
    @NotNull
    public static ResultQuery<Record> resultQuery(SQL sql) {
        return DSL.dsl().resultQuery(sql);
    }

    @Support
    @PlainSQL
    @NotNull
    public static ResultQuery<Record> resultQuery(String sql) {
        return DSL.dsl().resultQuery(sql);
    }

    @Support
    @PlainSQL
    @NotNull
    public static ResultQuery<Record> resultQuery(String sql, Object ... bindings) {
        return DSL.dsl().resultQuery(sql, bindings);
    }

    @Support
    @PlainSQL
    @NotNull
    public static ResultQuery<Record> resultQuery(String sql, QueryPart ... parts) {
        return DSL.dsl().resultQuery(sql, parts);
    }

    @Support
    @PlainSQL
    @NotNull
    public static Table<Record> table(SQL sql) {
        return new SQLTable(sql);
    }

    @Support
    @PlainSQL
    @NotNull
    public static Table<Record> table(String sql) {
        return DSL.table(sql, new Object[0]);
    }

    @Support
    @PlainSQL
    @NotNull
    public static Table<Record> table(String sql, Object ... bindings) {
        return DSL.table(DSL.sql(sql, bindings));
    }

    @Support
    @PlainSQL
    @NotNull
    public static Table<Record> table(String sql, QueryPart ... parts) {
        return DSL.table(sql, (Object[])parts);
    }

    @Deprecated(forRemoval=true, since="3.10")
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public static Sequence<BigInteger> sequence(String sql) {
        return DSL.sequence(sql, BigInteger.class);
    }

    @Deprecated(forRemoval=true, since="3.10")
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public static <T extends Number> Sequence<T> sequence(String sql, Class<T> type) {
        return DSL.sequence(sql, DSL.getDataType(type));
    }

    @Deprecated(forRemoval=true, since="3.10")
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @PlainSQL
    @NotNull
    public static <T extends Number> Sequence<T> sequence(String sql, DataType<T> type) {
        return new SequenceImpl<T>(sql, null, type, true);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T> value(Class<T> type) {
        return DSL.value(DefaultDataType.getDataType(null, type));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T> value(DataType<T> type) {
        return DSL.field(Names.N_VALUE, type);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Domain<?> domain(String name) {
        return DSL.domain(DSL.name(name));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Domain<?> domain(Name name) {
        return new DomainImpl<Object>(name.qualified() ? DSL.schema(name.qualifier()) : null, name.unqualifiedName(), new DefaultDataType<Object>(null, Object.class, name), new Check[0]);
    }

    @Support
    @PlainSQL
    @NotNull
    public static Field<Object> field(SQL sql) {
        return DSL.field(sql, Object.class);
    }

    @Support
    @PlainSQL
    @NotNull
    public static Field<Object> field(String sql) {
        return DSL.field(sql, new Object[0]);
    }

    @Support
    @PlainSQL
    @NotNull
    public static Field<Object> field(String sql, Object ... bindings) {
        return DSL.field(sql, Object.class, bindings);
    }

    @Support
    @PlainSQL
    @NotNull
    public static <T> Field<T> field(SQL sql, Class<T> type) {
        return DSL.field(sql, DSL.getDataType(type));
    }

    @Support
    @PlainSQL
    @NotNull
    public static <T> Field<T> field(String sql, Class<T> type) {
        return DSL.field(sql, type, new Object[0]);
    }

    @Support
    @PlainSQL
    @NotNull
    public static <T> Field<T> field(String sql, Class<T> type, Object ... bindings) {
        return DSL.field(sql, DSL.getDataType(type), bindings);
    }

    @Support
    @PlainSQL
    @NotNull
    public static <T> Field<T> field(SQL sql, DataType<T> type) {
        return new SQLField<T>(type, sql);
    }

    @Support
    @PlainSQL
    @NotNull
    public static <T> Field<T> field(String sql, DataType<T> type) {
        return DSL.field(sql, type, new Object[0]);
    }

    @Support
    @PlainSQL
    @NotNull
    public static <T> Field<T> field(String sql, DataType<T> type, Object ... bindings) {
        return DSL.field(DSL.sql(sql, bindings), type);
    }

    @Support
    @PlainSQL
    @NotNull
    public static <T> Field<T> field(String sql, DataType<T> type, QueryPart ... parts) {
        return DSL.field(DSL.sql(sql, parts), type);
    }

    @Support
    @PlainSQL
    @NotNull
    public static Field<Object> field(String sql, QueryPart ... parts) {
        return DSL.field(sql, (Object[])parts);
    }

    @Support
    @PlainSQL
    @NotNull
    public static <T> Field<T> field(String sql, Class<T> type, QueryPart ... parts) {
        return DSL.field(sql, DSL.getDataType(type), (Object[])parts);
    }

    @Support
    @PlainSQL
    @NotNull
    public static <T> Field<T> function(String name, Class<T> type, Field<?> ... arguments) {
        return DSL.function(name, DSL.getDataType(type), Tools.nullSafe(arguments));
    }

    @Support
    @PlainSQL
    @NotNull
    public static <T> Field<T> function(String name, DataType<T> type, Field<?> ... arguments) {
        return new Function<T>(name, type, Tools.nullSafe(arguments));
    }

    @Support
    @NotNull
    public static <T> Field<T> function(Name name, Class<T> type, Field<?> ... arguments) {
        return DSL.function(name, DSL.getDataType(type), Tools.nullSafe(arguments));
    }

    @Support
    @NotNull
    public static <T> Field<T> function(Name name, DataType<T> type, Field<?> ... arguments) {
        return new Function<T>(name, type, Tools.nullSafe(arguments));
    }

    @Support
    @NotNull
    static <T> Field<T> function(Name name, DataType<T> type, Field<?> argument) {
        return new Function1<T>(name, type, Tools.nullSafe(argument));
    }

    @Support
    @PlainSQL
    @NotNull
    public static <T> AggregateFunction<T> aggregate(String name, Class<T> type, Field<?> ... arguments) {
        return DSL.aggregate(name, DSL.getDataType(type), Tools.nullSafe(arguments));
    }

    @Support
    @PlainSQL
    @NotNull
    public static <T> AggregateFunction<T> aggregate(String name, DataType<T> type, Field<?> ... arguments) {
        return new DefaultAggregateFunction<T>(name, type, Tools.nullSafe(arguments));
    }

    @Support
    @NotNull
    public static <T> AggregateFunction<T> aggregate(Name name, Class<T> type, Field<?> ... arguments) {
        return DSL.aggregate(name, DSL.getDataType(type), Tools.nullSafe(arguments));
    }

    @Support
    @NotNull
    public static <T> AggregateFunction<T> aggregate(Name name, DataType<T> type, Field<?> ... arguments) {
        return new DefaultAggregateFunction<T>(name, type, Tools.nullSafe(arguments));
    }

    @Support
    @PlainSQL
    @NotNull
    public static <T> AggregateFunction<T> aggregateDistinct(String name, Class<T> type, Field<?> ... arguments) {
        return DSL.aggregateDistinct(name, DSL.getDataType(type), Tools.nullSafe(arguments));
    }

    @Support
    @PlainSQL
    @NotNull
    public static <T> AggregateFunction<T> aggregateDistinct(String name, DataType<T> type, Field<?> ... arguments) {
        return new DefaultAggregateFunction<T>(true, name, type, Tools.nullSafe(arguments));
    }

    @Support
    @NotNull
    public static <T> AggregateFunction<T> aggregateDistinct(Name name, Class<T> type, Field<?> ... arguments) {
        return DSL.aggregateDistinct(name, DSL.getDataType(type), Tools.nullSafe(arguments));
    }

    @Support
    @NotNull
    public static <T> AggregateFunction<T> aggregateDistinct(Name name, DataType<T> type, Field<?> ... arguments) {
        return new DefaultAggregateFunction<T>(true, name, type, Tools.nullSafe(arguments));
    }

    @Support
    @PlainSQL
    @NotNull
    public static Condition condition(SQL sql) {
        return new SQLCondition(sql);
    }

    @Support
    @PlainSQL
    @NotNull
    public static Condition condition(String sql) {
        return DSL.condition(sql, new Object[0]);
    }

    @Support
    @PlainSQL
    @NotNull
    public static Condition condition(String sql, Object ... bindings) {
        return DSL.condition(DSL.sql(sql, bindings));
    }

    @Support
    @PlainSQL
    @NotNull
    public static Condition condition(String sql, QueryPart ... parts) {
        return DSL.condition(sql, (Object[])parts);
    }

    @Support
    @NotNull
    public static Condition condition(Boolean value) {
        return DSL.condition(Tools.field(value));
    }

    @Support
    @NotNull
    public static Condition condition(Map<Field<?>, ?> map) {
        return new MapCondition(map);
    }

    @Support
    @NotNull
    public static Condition condition(Record record) {
        return new RecordCondition(record);
    }

    @Support
    @NotNull
    public static Condition noCondition() {
        return NoCondition.INSTANCE;
    }

    @Support
    @NotNull
    public static True trueCondition() {
        return TrueCondition.INSTANCE;
    }

    @Support
    @NotNull
    public static False falseCondition() {
        return FalseCondition.INSTANCE;
    }

    @Support
    @NotNull
    public static Null nullCondition() {
        return NullCondition.INSTANCE;
    }

    @Support
    @NotNull
    public static Condition and(Condition left, Condition right) {
        return DSL.condition(Operator.AND, left, right);
    }

    @Support
    @NotNull
    public static Condition and(Condition ... conditions) {
        return DSL.condition(Operator.AND, conditions);
    }

    @Support
    @NotNull
    public static Condition and(Collection<? extends Condition> conditions) {
        return DSL.condition(Operator.AND, conditions);
    }

    @Support
    @NotNull
    public static Condition or(Condition left, Condition right) {
        return DSL.condition(Operator.OR, left, right);
    }

    @Support
    @NotNull
    public static Condition or(Condition ... conditions) {
        return DSL.condition(Operator.OR, conditions);
    }

    @Support
    @NotNull
    public static Condition or(Collection<? extends Condition> conditions) {
        return DSL.condition(Operator.OR, conditions);
    }

    @Support
    @NotNull
    public static Condition xor(Condition left, Condition right) {
        return DSL.condition(Operator.XOR, left, right);
    }

    @Support
    @NotNull
    public static Condition xor(Condition ... conditions) {
        return DSL.condition(Operator.XOR, conditions);
    }

    @Support
    @NotNull
    public static Condition xor(Collection<? extends Condition> conditions) {
        return DSL.condition(Operator.XOR, conditions);
    }

    @Support
    @NotNull
    public static Condition condition(Operator operator, Condition left, Condition right) {
        if (left == null || left instanceof NoCondition) {
            return right == null ? DSL.noCondition() : right;
        }
        if (right == null || right instanceof NoCondition) {
            return left;
        }
        if (operator == Operator.AND) {
            return new And(left, right);
        }
        if (operator == Operator.XOR) {
            return new Xor(left, right);
        }
        return new Or(left, right);
    }

    @Support
    @NotNull
    public static Condition condition(Operator operator, Condition ... conditions) {
        return DSL.condition(operator, Arrays.asList(conditions));
    }

    @Support
    @NotNull
    public static Condition condition(Operator operator, Collection<? extends Condition> conditions) {
        Condition result = null;
        for (Condition condition : conditions) {
            if (result == null) {
                result = condition;
                continue;
            }
            result = DSL.condition(operator, result, condition);
        }
        if (result != null) {
            return result;
        }
        if (!conditions.isEmpty()) {
            return DSL.noCondition();
        }
        return operator.identity();
    }

    @Support
    @SafeVarargs
    @NotNull
    public static <T> Field<Integer> field(Field<T> field2, T ... list) {
        return DSL.field(field2, Tools.fieldsArray((Object[])list, field2.getDataType()));
    }

    @Support
    @SafeVarargs
    @NotNull
    public static <T> Field<Integer> field(Field<T> field2, Field<T> ... list) {
        return new FieldFunction<T>(field2, list);
    }

    @Support
    @NotNull
    public static <T> Field<T> field(SelectField<T> field2) {
        Field<Object> field3;
        if (field2 instanceof Field) {
            RowAsField f;
            field3 = f = (RowAsField)field2;
        } else if (field2 instanceof AbstractRow) {
            AbstractRow r = (AbstractRow)field2;
            field3 = r.rf();
        } else if (field2 instanceof AbstractTable) {
            AbstractTable t2 = (AbstractTable)field2;
            field3 = t2.tf();
        } else {
            field3 = DSL.field("{0}", field2.getDataType(), new QueryPart[]{field2});
        }
        return field3;
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static Field<Record> rowField(RowN row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1> Field<Record1<T1>> rowField(Row1<T1> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2> Field<Record2<T1, T2>> rowField(Row2<T1, T2> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3> Field<Record3<T1, T2, T3>> rowField(Row3<T1, T2, T3> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3, T4> Field<Record4<T1, T2, T3, T4>> rowField(Row4<T1, T2, T3, T4> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3, T4, T5> Field<Record5<T1, T2, T3, T4, T5>> rowField(Row5<T1, T2, T3, T4, T5> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3, T4, T5, T6> Field<Record6<T1, T2, T3, T4, T5, T6>> rowField(Row6<T1, T2, T3, T4, T5, T6> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7> Field<Record7<T1, T2, T3, T4, T5, T6, T7>> rowField(Row7<T1, T2, T3, T4, T5, T6, T7> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8> Field<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> rowField(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Field<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> rowField(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Field<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> rowField(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Field<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> rowField(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Field<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> rowField(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Field<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> rowField(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Field<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> rowField(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Field<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> rowField(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Field<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> rowField(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Field<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> rowField(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Field<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> rowField(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Field<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> rowField(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Field<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> rowField(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Field<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> rowField(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row) {
        return new RowAsField(row);
    }

    @Support
    @Deprecated(forRemoval=true, since="3.15")
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Field<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> rowField(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row) {
        return new RowAsField(row);
    }

    @Support
    @NotNull
    public static <T> Field<T> field(Select<? extends Record1<T>> select) {
        if (select == null) {
            return DSL.inline(null);
        }
        return select.asField();
    }

    @Support
    @NotNull
    public static <T> Field<T> if_(Condition condition, T ifTrue, T ifFalse) {
        return DSL.iif0(Names.N_IF, condition, Tools.field(ifTrue), Tools.field(ifFalse));
    }

    @Support
    @NotNull
    public static <T> Field<T> if_(Condition condition, T ifTrue, Field<T> ifFalse) {
        return DSL.iif0(Names.N_IF, condition, Tools.field(ifTrue), Tools.nullSafe(ifFalse));
    }

    @Support
    @NotNull
    public static <T> Field<T> if_(Condition condition, Field<T> ifTrue, T ifFalse) {
        return DSL.iif0(Names.N_IF, condition, Tools.nullSafe(ifTrue), Tools.field(ifFalse));
    }

    @Support
    @NotNull
    public static <T> Field<T> if_(Condition condition, Field<T> ifTrue, Field<T> ifFalse) {
        return DSL.iif0(Names.N_IF, condition, Tools.nullSafe(ifTrue), Tools.nullSafe(ifFalse));
    }

    @Support
    @NotNull
    public static Case choose() {
        return DSL.decode();
    }

    @Support
    @NotNull
    public static <V> CaseValueStep<V> choose(V value) {
        return DSL.decode().value(value);
    }

    @Support
    @NotNull
    public static <V> CaseValueStep<V> choose(Field<V> value) {
        return DSL.decode().value(value);
    }

    @Support
    @NotNull
    public static <T> Field<T> choose(int index, T ... values2) {
        return DSL.choose(DSL.val(index), Tools.fieldsArray(values2));
    }

    @Support
    @SafeVarargs
    @NotNull
    public static <T> Field<T> choose(int index, Field<T> ... values2) {
        return DSL.choose(DSL.val(index), values2);
    }

    @Support
    @NotNull
    public static <T> Field<T> choose(Field<Integer> index, T ... values2) {
        return DSL.choose(index, Tools.fieldsArray(values2));
    }

    @Support
    @SafeVarargs
    @NotNull
    public static <T> Field<T> choose(Field<Integer> index, Field<T> ... values2) {
        return new Choose<T>(index, values2);
    }

    @Support
    @NotNull
    public static Case case_() {
        return DSL.decode();
    }

    @Support
    @NotNull
    public static <V> CaseValueStep<V> case_(V value) {
        return DSL.decode().value(value);
    }

    @Support
    @NotNull
    public static <V> CaseValueStep<V> case_(Field<V> value) {
        return DSL.decode().value(value);
    }

    @Support
    @NotNull
    public static <T> CaseConditionStep<T> when(Condition condition, T result) {
        return DSL.decode().when(condition, result);
    }

    @Support
    @NotNull
    public static <T> CaseConditionStep<T> when(Condition condition, Field<T> result) {
        return DSL.decode().when(condition, result);
    }

    @Support
    @NotNull
    public static <T> CaseConditionStep<T> when(Condition condition, Select<? extends Record1<T>> result) {
        return DSL.decode().when(condition, result);
    }

    @Support
    @NotNull
    public static <T> CaseConditionStep<T> when(Field<Boolean> condition, T result) {
        return DSL.decode().when(condition, result);
    }

    @Support
    @NotNull
    public static <T> CaseConditionStep<T> when(Field<Boolean> condition, Field<T> result) {
        return DSL.decode().when(condition, result);
    }

    @Support
    @NotNull
    public static <T> CaseConditionStep<T> when(Field<Boolean> condition, Select<? extends Record1<T>> result) {
        return DSL.decode().when(condition, result);
    }

    @Support
    @NotNull
    public static Case decode() {
        return new CaseImpl();
    }

    @Support
    @NotNull
    public static <Z, T> Field<Z> decode(T value, T search, Z result) {
        return DSL.decode(value, search, result, new Object[0]);
    }

    @Support
    @NotNull
    public static <Z, T> Field<Z> decode(T value, T search, Z result, Object ... more) {
        return DSL.decode(Tools.field(value), Tools.field(search), Tools.field(result), Tools.fieldsArray(more));
    }

    @Support
    @NotNull
    public static <Z, T> Field<Z> decode(Field<T> value, Field<T> search, Field<Z> result) {
        return DSL.decode(Tools.nullSafe(value), Tools.nullSafe(search), Tools.nullSafe(result), Tools.EMPTY_FIELD);
    }

    @Support
    @NotNull
    public static <Z, T> Field<Z> decode(Field<T> value, Field<T> search, Field<Z> result, Field<?> ... more) {
        return new Decode<T, Z>(Tools.nullSafe(value), Tools.nullSafe(search), Tools.nullSafe(result), Tools.nullSafe(more));
    }

    @Support
    @NotNull
    public static <T> Field<T> coerce(Object value, Field<T> as) {
        return Tools.field(value).coerce(as);
    }

    @Support
    @NotNull
    public static <T> Field<T> coerce(Object value, Class<T> as) {
        return Tools.field(value).coerce(as);
    }

    @Support
    @NotNull
    public static <T> Field<T> coerce(Object value, DataType<T> as) {
        return Tools.field(value).coerce(as);
    }

    @Support
    @NotNull
    public static <T> Field<T> coerce(Field<?> field2, Field<T> as) {
        return Tools.nullSafe(field2).coerce(as);
    }

    @Support
    @NotNull
    public static <T> Field<T> coerce(Field<?> field2, Class<T> as) {
        return Tools.nullSafe(field2).coerce(as);
    }

    @Support
    @NotNull
    public static <T> Field<T> coerce(Field<?> field2, DataType<T> as) {
        return Tools.nullSafe(field2).coerce(as);
    }

    @Support(value={SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T> Field<T> convert(DataType<T> type, Field<?> value, int style) {
        return new ConvertDateTime<T>(type, value, style);
    }

    @Support(value={SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T> Field<T> convert(DataType<T> type, Object value, int style) {
        return DSL.convert(type, DSL.val(value), style);
    }

    @Support
    @NotNull
    public static <T> Field<T> cast(Object value, Field<T> as) {
        return Tools.field(value, as).cast(as);
    }

    @Support
    @NotNull
    public static <T> Field<T> cast(Field<?> field2, Field<T> as) {
        return Tools.nullSafe(field2).cast(as);
    }

    @Support
    @NotNull
    public static <T> Field<T> castNull(Field<T> as) {
        return DSL.inline(null).cast(as);
    }

    @Support
    @NotNull
    public static <T> Field<T> cast(Object value, Class<T> type) {
        return Tools.field(value, type).cast(type);
    }

    @Support
    @NotNull
    public static <T> Field<T> cast(Field<?> field2, Class<T> type) {
        return Tools.nullSafe(field2).cast(type);
    }

    @Support
    @NotNull
    public static <T> Field<T> castNull(DataType<T> type) {
        return DSL.inline(null).cast(type);
    }

    @Support
    @NotNull
    public static <T> Field<T> cast(Object value, DataType<T> type) {
        return Tools.field(value).cast(type);
    }

    @Support
    @NotNull
    public static <T> Field<T> cast(Field<?> field2, DataType<T> type) {
        return Tools.nullSafe(field2).cast(type);
    }

    @Support
    @NotNull
    public static <T> Field<T> castNull(Class<T> type) {
        return DSL.inline(null).cast(type);
    }

    @Support
    @NotNull
    public static <T> Field<T> coalesce(T value, T ... values2) {
        return DSL.coalesce0(Tools.field(value), Tools.fieldsArray(values2));
    }

    @Support
    @NotNull
    public static <T> Field<T> coalesce(Field<T> field2, T value) {
        return DSL.coalesce0(field2, Tools.field(value, field2));
    }

    @Support
    @NotNull
    public static <T> Field<T> coalesce(Field<T> field2, Field<?> ... fields) {
        return DSL.coalesce0(field2, fields);
    }

    static <T> Field<T> coalesce0(Field<T> field2, Field<?> ... fields) {
        return new Coalesce(Tools.nullSafe(Tools.combine(field2, fields)));
    }

    @Support
    @NotNull
    public static <Z> Field<Z> nvl2(Field<?> value, Z valueIfNotNull, Z valueIfNull) {
        return DSL.nvl20(Tools.nullSafe(value), Tools.field(valueIfNotNull), Tools.field(valueIfNull));
    }

    @Support
    @NotNull
    public static <Z> Field<Z> nvl2(Field<?> value, Z valueIfNotNull, Field<Z> valueIfNull) {
        return DSL.nvl20(Tools.nullSafe(value), Tools.field(valueIfNotNull, valueIfNull), Tools.nullSafe(valueIfNull));
    }

    @Support
    @NotNull
    public static <Z> Field<Z> nvl2(Field<?> value, Field<Z> valueIfNotNull, Z valueIfNull) {
        return DSL.nvl20(Tools.nullSafe(value), Tools.nullSafe(valueIfNotNull), Tools.field(valueIfNull, valueIfNotNull));
    }

    @Support
    @NotNull
    public static <Z> Field<Z> nvl2(Field<?> value, Field<Z> valueIfNotNull, Field<Z> valueIfNull) {
        return DSL.nvl20(value, valueIfNotNull, valueIfNull);
    }

    static <Z> Field<Z> nvl20(Field<?> value, Field<Z> valueIfNotNull, Field<Z> valueIfNull) {
        return new Nvl2<Z>(Tools.nullSafe(value), Tools.nullSafe(valueIfNotNull), Tools.nullSafe(valueIfNull));
    }

    @Support
    @NotNull
    public static <T> Field<T> iif(Condition condition, T ifTrue, T ifFalse) {
        return DSL.iif0(Names.N_IIF, condition, Tools.field(ifTrue), Tools.field(ifFalse));
    }

    @Support
    @NotNull
    public static <T> Field<T> iif(Condition condition, T ifTrue, Field<T> ifFalse) {
        return DSL.iif0(Names.N_IIF, condition, Tools.field(ifTrue, ifFalse), Tools.nullSafe(ifFalse));
    }

    @Support
    @NotNull
    public static <T> Field<T> iif(Condition condition, Field<T> ifTrue, T ifFalse) {
        return DSL.iif0(Names.N_IIF, condition, Tools.nullSafe(ifTrue), Tools.field(ifFalse, ifTrue));
    }

    @Support
    @NotNull
    public static <T> Field<T> iif(Condition condition, Field<T> ifTrue, Field<T> ifFalse) {
        return DSL.iif0(Names.N_IIF, condition, ifTrue, ifFalse);
    }

    static <T> Field<T> iif0(Name name, Condition condition, Field<T> ifTrue, Field<T> ifFalse) {
        return new Iif<T>(name, condition, Tools.nullSafe(ifTrue), Tools.nullSafe(ifFalse));
    }

    @Support
    @NotNull
    public static Condition exists(Select<?> query) {
        return new Exists(query);
    }

    @Support
    @NotNull
    public static Condition notExists(Select<?> query) {
        return DSL.not(DSL.exists(query));
    }

    @Support
    @NotNull
    public static Condition not(Condition condition) {
        return new Not(condition);
    }

    @Support
    @NotNull
    public static Field<Boolean> not(Field<Boolean> field2) {
        return new NotField(field2);
    }

    @Support
    @NotNull
    public static Condition unique(Select<?> query) {
        return new Unique(query);
    }

    @Support
    @NotNull
    public static Condition notUnique(Select<?> query) {
        return DSL.not(DSL.unique(query));
    }

    @Support
    @NotNull
    public static <T> Field<T> excluded(Field<T> field2) {
        return new Excluded<T>(field2);
    }

    @Support
    @NotNull
    public static <T extends Number> Field<T> abs(T value) {
        return new Abs<T>(Tools.field(value));
    }

    @Support
    @NotNull
    public static <T extends Number> Field<T> abs(Field<T> value) {
        return new Abs<T>(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> acos(Number value) {
        return new Acos(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> acos(Field<? extends Number> value) {
        return new Acos(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> acosh(Number value) {
        return new Acosh(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> acosh(Field<? extends Number> value) {
        return new Acosh(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> acoth(Number value) {
        return new Acoth(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> acoth(Field<? extends Number> value) {
        return new Acoth(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> asin(Number value) {
        return new Asin(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> asin(Field<? extends Number> value) {
        return new Asin(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> asinh(Number value) {
        return new Asinh(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> asinh(Field<? extends Number> value) {
        return new Asinh(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> atan(Number value) {
        return new Atan(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> atan(Field<? extends Number> value) {
        return new Atan(value);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> atan2(Number x, Number y) {
        return new Atan2(Tools.field(x), Tools.field(y));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> atan2(Number x, Field<? extends Number> y) {
        return new Atan2(Tools.field(x), y);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> atan2(Field<? extends Number> x, Number y) {
        return new Atan2(x, Tools.field(y));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> atan2(Field<? extends Number> x, Field<? extends Number> y) {
        return new Atan2(x, y);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> atanh(Number value) {
        return new Atanh(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> atanh(Field<? extends Number> value) {
        return new Atanh(value);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitAnd(T arg1, T arg2) {
        return new BitAnd<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitAnd(T arg1, Field<T> arg2) {
        return new BitAnd<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitAnd(Field<T> arg1, T arg2) {
        return new BitAnd<T>(arg1, Tools.field(arg2, arg1));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitAnd(Field<T> arg1, Field<T> arg2) {
        return new BitAnd<T>(arg1, arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> bitCount(Number value) {
        return new BitCount(Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> bitCount(Field<? extends Number> value) {
        return new BitCount(value);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitGet(Field<T> value, int bit) {
        return new BitGet<T>(value, Tools.field(bit));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitGet(Field<T> value, Field<? extends Number> bit) {
        return new BitGet<T>(value, bit);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitNand(T arg1, T arg2) {
        return new BitNand<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitNand(T arg1, Field<T> arg2) {
        return new BitNand<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitNand(Field<T> arg1, T arg2) {
        return new BitNand<T>(arg1, Tools.field(arg2, arg1));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitNand(Field<T> arg1, Field<T> arg2) {
        return new BitNand<T>(arg1, arg2);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitNor(T arg1, T arg2) {
        return new BitNor<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitNor(T arg1, Field<T> arg2) {
        return new BitNor<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitNor(Field<T> arg1, T arg2) {
        return new BitNor<T>(arg1, Tools.field(arg2, arg1));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitNor(Field<T> arg1, Field<T> arg2) {
        return new BitNor<T>(arg1, arg2);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitNot(T arg1) {
        return new BitNot<T>(Tools.field(arg1));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitNot(Field<T> arg1) {
        return new BitNot<T>(arg1);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitOr(T arg1, T arg2) {
        return new BitOr<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitOr(T arg1, Field<T> arg2) {
        return new BitOr<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitOr(Field<T> arg1, T arg2) {
        return new BitOr<T>(arg1, Tools.field(arg2, arg1));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitOr(Field<T> arg1, Field<T> arg2) {
        return new BitOr<T>(arg1, arg2);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitSet(Field<T> value, int bit, T newValue) {
        return new BitSet<T>(value, Tools.field(bit), Tools.field(newValue, value));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitSet(Field<T> value, int bit, Field<T> newValue) {
        return new BitSet<T>(value, Tools.field(bit), newValue);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitSet(Field<T> value, Field<? extends Number> bit, T newValue) {
        return new BitSet<T>(value, bit, Tools.field(newValue, value));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitSet(Field<T> value, Field<? extends Number> bit, Field<T> newValue) {
        return new BitSet<T>(value, bit, newValue);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitSet(Field<T> value, int bit) {
        return new BitSet<T>(value, Tools.field(bit));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitSet(Field<T> value, Field<? extends Number> bit) {
        return new BitSet<T>(value, bit);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitXNor(T arg1, T arg2) {
        return new BitXNor<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitXNor(T arg1, Field<T> arg2) {
        return new BitXNor<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitXNor(Field<T> arg1, T arg2) {
        return new BitXNor<T>(arg1, Tools.field(arg2, arg1));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitXNor(Field<T> arg1, Field<T> arg2) {
        return new BitXNor<T>(arg1, arg2);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitXor(T arg1, T arg2) {
        return new BitXor<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitXor(T arg1, Field<T> arg2) {
        return new BitXor<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitXor(Field<T> arg1, T arg2) {
        return new BitXor<T>(arg1, Tools.field(arg2, arg1));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> bitXor(Field<T> arg1, Field<T> arg2) {
        return new BitXor<T>(arg1, arg2);
    }

    @Support
    @NotNull
    public static <T extends Number> Field<T> ceil(T value) {
        return new Ceil<T>(Tools.field(value));
    }

    @Support
    @NotNull
    public static <T extends Number> Field<T> ceil(Field<T> value) {
        return new Ceil<T>(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> cos(Number value) {
        return new Cos(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> cos(Field<? extends Number> value) {
        return new Cos(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> cosh(Number value) {
        return new Cosh(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> cosh(Field<? extends Number> value) {
        return new Cosh(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> cot(Number value) {
        return new Cot(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> cot(Field<? extends Number> value) {
        return new Cot(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> coth(Number value) {
        return new Coth(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> coth(Field<? extends Number> value) {
        return new Coth(value);
    }

    @Support
    @NotNull
    public static Field<BigDecimal> deg(Number radians2) {
        return new Degrees(Tools.field(radians2));
    }

    @Support
    @NotNull
    public static Field<BigDecimal> deg(Field<? extends Number> radians2) {
        return new Degrees(radians2);
    }

    @Support
    @NotNull
    public static Field<BigDecimal> e() {
        return new Euler();
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> exp(Number value) {
        return new Exp(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> exp(Field<? extends Number> value) {
        return new Exp(value);
    }

    @Support
    @NotNull
    public static <T extends Number> Field<T> floor(T value) {
        return new Floor<T>(Tools.field(value));
    }

    @Support
    @NotNull
    public static <T extends Number> Field<T> floor(Field<T> value) {
        return new Floor<T>(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> ln(Number value) {
        return new Ln(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> ln(Field<? extends Number> value) {
        return new Ln(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> log(Number value, int base) {
        return new Log(Tools.field(value), Tools.field(base));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> log(Number value, Field<? extends Number> base) {
        return new Log(Tools.field(value), base);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> log(Field<? extends Number> value, int base) {
        return new Log(value, Tools.field(base));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> log(Field<? extends Number> value, Field<? extends Number> base) {
        return new Log(value, base);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> log10(Number value) {
        return new Log10(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> log10(Field<? extends Number> value) {
        return new Log10(value);
    }

    @Support
    @NotNull
    public static Field<BigDecimal> pi() {
        return new Pi();
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> power(Number base, Number exponent) {
        return new Power(Tools.field(base), Tools.field(exponent));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> power(Number base, Field<? extends Number> exponent) {
        return new Power(Tools.field(base), exponent);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> power(Field<? extends Number> base, Number exponent) {
        return new Power(base, Tools.field(exponent));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> power(Field<? extends Number> base, Field<? extends Number> exponent) {
        return new Power(base, exponent);
    }

    @Support
    @NotNull
    public static Field<BigDecimal> rad(Number degrees2) {
        return new Radians(Tools.field(degrees2));
    }

    @Support
    @NotNull
    public static Field<BigDecimal> rad(Field<? extends Number> degrees2) {
        return new Radians(degrees2);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> rand() {
        return new Rand();
    }

    @Support
    @NotNull
    public static <T extends Number> Field<T> round(T value, int decimals) {
        return new Round<T>(Tools.field(value), Tools.field(decimals));
    }

    @Support
    @NotNull
    public static <T extends Number> Field<T> round(T value, Field<Integer> decimals) {
        return new Round<T>(Tools.field(value), decimals);
    }

    @Support
    @NotNull
    public static <T extends Number> Field<T> round(Field<T> value, int decimals) {
        return new Round<T>(value, Tools.field(decimals));
    }

    @Support
    @NotNull
    public static <T extends Number> Field<T> round(Field<T> value, Field<Integer> decimals) {
        return new Round<T>(value, decimals);
    }

    @Support
    @NotNull
    public static <T extends Number> Field<T> round(T value) {
        return new Round<T>(Tools.field(value));
    }

    @Support
    @NotNull
    public static <T extends Number> Field<T> round(Field<T> value) {
        return new Round<T>(value);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> shl(T value, Number count) {
        return new Shl<T>(Tools.field(value), Tools.field(count));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> shl(T value, Field<? extends Number> count) {
        return new Shl<T>(Tools.field(value), count);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> shl(Field<T> value, Number count) {
        return new Shl<T>(value, Tools.field(count));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> shl(Field<T> value, Field<? extends Number> count) {
        return new Shl<T>(value, count);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> shr(T value, Number count) {
        return new Shr<T>(Tools.field(value), Tools.field(count));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> shr(T value, Field<? extends Number> count) {
        return new Shr<T>(Tools.field(value), count);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> shr(Field<T> value, Number count) {
        return new Shr<T>(value, Tools.field(count));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> shr(Field<T> value, Field<? extends Number> count) {
        return new Shr<T>(value, count);
    }

    @Support
    @NotNull
    public static Field<Integer> sign(Number value) {
        return new Sign(Tools.field(value));
    }

    @Support
    @NotNull
    public static Field<Integer> sign(Field<? extends Number> value) {
        return new Sign(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> sin(Number value) {
        return new Sin(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> sin(Field<? extends Number> value) {
        return new Sin(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> sinh(Number value) {
        return new Sinh(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> sinh(Field<? extends Number> value) {
        return new Sinh(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> sqrt(Number value) {
        return new Sqrt(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> sqrt(Field<? extends Number> value) {
        return new Sqrt(value);
    }

    @Support
    @NotNull
    public static <T extends Number> Field<T> square(T value) {
        return new Square<T>(Tools.field(value));
    }

    @Support
    @NotNull
    public static <T extends Number> Field<T> square(Field<T> value) {
        return new Square<T>(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> tan(Number value) {
        return new Tan(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> tan(Field<? extends Number> value) {
        return new Tan(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> tanh(Number value) {
        return new Tanh(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<BigDecimal> tanh(Field<? extends Number> value) {
        return new Tanh(value);
    }

    @Support
    @NotNull
    public static Field<BigDecimal> tau() {
        return new Tau();
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> trunc(T value, int decimals) {
        return new Trunc<T>(Tools.field(value), Tools.field(decimals));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> trunc(T value, Field<Integer> decimals) {
        return new Trunc<T>(Tools.field(value), decimals);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> trunc(Field<T> value, int decimals) {
        return new Trunc<T>(value, Tools.field(decimals));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> Field<T> trunc(Field<T> value, Field<Integer> decimals) {
        return new Trunc<T>(value, decimals);
    }

    @Support
    @NotNull
    public static <T extends Number> Field<T> widthBucket(Field<T> field2, T low, T high, int buckets) {
        return new WidthBucket<T>(field2, Tools.field(low, field2), Tools.field(high, field2), Tools.field(buckets));
    }

    @Support
    @NotNull
    public static <T extends Number> Field<T> widthBucket(Field<T> field2, Field<T> low, Field<T> high, Field<Integer> buckets) {
        return new WidthBucket<T>(field2, low, high, buckets);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> ascii(String string) {
        return new Ascii(Tools.field(string));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> ascii(Field<String> string) {
        return new Ascii(string);
    }

    @Support
    @NotNull
    public static Field<Integer> bitLength(String string) {
        return new BitLength(Tools.field(string));
    }

    @Support
    @NotNull
    public static Field<Integer> bitLength(Field<String> string) {
        return new BitLength(string);
    }

    @Support
    @NotNull
    public static Field<Integer> charLength(String string) {
        return new CharLength(Tools.field(string));
    }

    @Support
    @NotNull
    public static Field<Integer> charLength(Field<String> string) {
        return new CharLength(string);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> chr(Number value) {
        return new Chr(Tools.field(value));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> chr(Field<? extends Number> value) {
        return new Chr(value);
    }

    @Support
    @NotNull
    public static Field<String> digits(Number value) {
        return new Digits(Tools.field(value));
    }

    @Support
    @NotNull
    public static Field<String> digits(Field<? extends Number> value) {
        return new Digits(value);
    }

    @Support
    @NotNull
    public static Field<String> left(String string, int length) {
        return new Left(Tools.field(string), Tools.field(length));
    }

    @Support
    @NotNull
    public static Field<String> left(String string, Field<? extends Number> length) {
        return new Left(Tools.field(string), length);
    }

    @Support
    @NotNull
    public static Field<String> left(Field<String> string, int length) {
        return new Left(string, Tools.field(length));
    }

    @Support
    @NotNull
    public static Field<String> left(Field<String> string, Field<? extends Number> length) {
        return new Left(string, length);
    }

    @Support
    @NotNull
    public static Field<Integer> length(String string) {
        return DSL.charLength(string);
    }

    @Support
    @NotNull
    public static Field<Integer> length(Field<String> string) {
        return DSL.charLength(string);
    }

    @Support
    @NotNull
    public static Field<String> lower(String string) {
        return new Lower(Tools.field(string));
    }

    @Support
    @NotNull
    public static Field<String> lower(Field<String> string) {
        return new Lower(string);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> lpad(Field<String> string, int length, String character) {
        return new Lpad(string, Tools.field(length), Tools.field(character));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> lpad(Field<String> string, int length, Field<String> character) {
        return new Lpad(string, Tools.field(length), character);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> lpad(Field<String> string, Field<? extends Number> length, String character) {
        return new Lpad(string, length, Tools.field(character));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> lpad(Field<String> string, Field<? extends Number> length, Field<String> character) {
        return new Lpad(string, length, character);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> lpad(Field<String> string, int length) {
        return new Lpad(string, Tools.field(length));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> lpad(Field<String> string, Field<? extends Number> length) {
        return new Lpad(string, length);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> ltrim(String string, String characters) {
        return new Ltrim(Tools.field(string), Tools.field(characters));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> ltrim(String string, Field<String> characters) {
        return new Ltrim(Tools.field(string), characters);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> ltrim(Field<String> string, String characters) {
        return new Ltrim(string, Tools.field(characters));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> ltrim(Field<String> string, Field<String> characters) {
        return new Ltrim(string, characters);
    }

    @Support
    @NotNull
    public static Field<String> ltrim(String string) {
        return new Ltrim(Tools.field(string));
    }

    @Support
    @NotNull
    public static Field<String> ltrim(Field<String> string) {
        return new Ltrim(string);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> md5(String string) {
        return new Md5(Tools.field(string));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> md5(Field<String> string) {
        return new Md5(string);
    }

    @Support
    @NotNull
    public static Field<String> mid(Field<String> string, int startingPosition, int length) {
        return DSL.substring(string, startingPosition, length);
    }

    @Support
    @NotNull
    public static Field<String> mid(Field<String> string, int startingPosition, Field<? extends Number> length) {
        return DSL.substring(string, startingPosition, length);
    }

    @Support
    @NotNull
    public static Field<String> mid(Field<String> string, Field<? extends Number> startingPosition, int length) {
        return DSL.substring(string, startingPosition, length);
    }

    @Support
    @NotNull
    public static Field<String> mid(Field<String> string, Field<? extends Number> startingPosition, Field<? extends Number> length) {
        return DSL.substring(string, startingPosition, length);
    }

    @Support
    @NotNull
    public static Field<String> mid(Field<String> string, int startingPosition) {
        return DSL.substring(string, startingPosition);
    }

    @Support
    @NotNull
    public static Field<String> mid(Field<String> string, Field<? extends Number> startingPosition) {
        return DSL.substring(string, startingPosition);
    }

    @Support
    @NotNull
    public static Field<Integer> octetLength(String string) {
        return new OctetLength(Tools.field(string));
    }

    @Support
    @NotNull
    public static Field<Integer> octetLength(Field<String> string) {
        return new OctetLength(string);
    }

    @Support
    @NotNull
    public static Field<String> overlay(Field<String> in, String placing, Number startIndex, Number length) {
        return new Overlay(in, Tools.field(placing), Tools.field(startIndex), Tools.field(length));
    }

    @Support
    @NotNull
    public static Field<String> overlay(Field<String> in, Field<String> placing, Field<? extends Number> startIndex, Field<? extends Number> length) {
        return new Overlay(in, placing, startIndex, length);
    }

    @Support
    @NotNull
    public static Field<String> overlay(Field<String> in, String placing, Number startIndex) {
        return new Overlay(in, Tools.field(placing), Tools.field(startIndex));
    }

    @Support
    @NotNull
    public static Field<String> overlay(Field<String> in, Field<String> placing, Field<? extends Number> startIndex) {
        return new Overlay(in, placing, startIndex);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> position(String in, String search, int startIndex) {
        return new Position(Tools.field(in), Tools.field(search), Tools.field(startIndex));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> position(String in, String search, Field<? extends Number> startIndex) {
        return new Position(Tools.field(in), Tools.field(search), startIndex);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> position(String in, Field<String> search, int startIndex) {
        return new Position(Tools.field(in), search, Tools.field(startIndex));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> position(String in, Field<String> search, Field<? extends Number> startIndex) {
        return new Position(Tools.field(in), search, startIndex);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> position(Field<String> in, String search, int startIndex) {
        return new Position(in, Tools.field(search), Tools.field(startIndex));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> position(Field<String> in, String search, Field<? extends Number> startIndex) {
        return new Position(in, Tools.field(search), startIndex);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> position(Field<String> in, Field<String> search, int startIndex) {
        return new Position(in, search, Tools.field(startIndex));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> position(Field<String> in, Field<String> search, Field<? extends Number> startIndex) {
        return new Position(in, search, startIndex);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> position(String in, String search) {
        return new Position(Tools.field(in), Tools.field(search));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> position(String in, Field<String> search) {
        return new Position(Tools.field(in), search);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> position(Field<String> in, String search) {
        return new Position(in, Tools.field(search));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> position(Field<String> in, Field<String> search) {
        return new Position(in, search);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> repeat(String string, int count) {
        return new Repeat(Tools.field(string), Tools.field(count));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> repeat(String string, Field<? extends Number> count) {
        return new Repeat(Tools.field(string), count);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> repeat(Field<String> string, int count) {
        return new Repeat(string, Tools.field(count));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> repeat(Field<String> string, Field<? extends Number> count) {
        return new Repeat(string, count);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> replace(Field<String> string, String search, String replace) {
        return new Replace(string, Tools.field(search), Tools.field(replace));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> replace(Field<String> string, String search, Field<String> replace) {
        return new Replace(string, Tools.field(search), replace);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> replace(Field<String> string, Field<String> search, String replace) {
        return new Replace(string, search, Tools.field(replace));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> replace(Field<String> string, Field<String> search, Field<String> replace) {
        return new Replace(string, search, replace);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> replace(Field<String> string, String search) {
        return new Replace(string, Tools.field(search));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> replace(Field<String> string, Field<String> search) {
        return new Replace(string, search);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> reverse(String string) {
        return new Reverse(Tools.field(string));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> reverse(Field<String> string) {
        return new Reverse(string);
    }

    @Support
    @NotNull
    public static Field<String> right(String string, int length) {
        return new Right(Tools.field(string), Tools.field(length));
    }

    @Support
    @NotNull
    public static Field<String> right(String string, Field<? extends Number> length) {
        return new Right(Tools.field(string), length);
    }

    @Support
    @NotNull
    public static Field<String> right(Field<String> string, int length) {
        return new Right(string, Tools.field(length));
    }

    @Support
    @NotNull
    public static Field<String> right(Field<String> string, Field<? extends Number> length) {
        return new Right(string, length);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> rpad(Field<String> string, int length, String character) {
        return new Rpad(string, Tools.field(length), Tools.field(character));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> rpad(Field<String> string, int length, Field<String> character) {
        return new Rpad(string, Tools.field(length), character);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> rpad(Field<String> string, Field<? extends Number> length, String character) {
        return new Rpad(string, length, Tools.field(character));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> rpad(Field<String> string, Field<? extends Number> length, Field<String> character) {
        return new Rpad(string, length, character);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> rpad(Field<String> string, int length) {
        return new Rpad(string, Tools.field(length));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> rpad(Field<String> string, Field<? extends Number> length) {
        return new Rpad(string, length);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<String> rtrim(String string, String characters) {
        return new Rtrim(Tools.field(string), Tools.field(characters));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<String> rtrim(String string, Field<String> characters) {
        return new Rtrim(Tools.field(string), characters);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<String> rtrim(Field<String> string, String characters) {
        return new Rtrim(string, Tools.field(characters));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<String> rtrim(Field<String> string, Field<String> characters) {
        return new Rtrim(string, characters);
    }

    @Support
    @NotNull
    public static Field<String> rtrim(String string) {
        return new Rtrim(Tools.field(string));
    }

    @Support
    @NotNull
    public static Field<String> rtrim(Field<String> string) {
        return new Rtrim(string);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> space(Number count) {
        return new Space(Tools.field(count));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> space(Field<? extends Number> count) {
        return new Space(count);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2022, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> splitPart(Field<String> string, String delimiter, Number n) {
        return new SplitPart(string, Tools.field(delimiter), Tools.field(n));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2022, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> splitPart(Field<String> string, String delimiter, Field<? extends Number> n) {
        return new SplitPart(string, Tools.field(delimiter), n);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2022, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> splitPart(Field<String> string, Field<String> delimiter, Number n) {
        return new SplitPart(string, delimiter, Tools.field(n));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2022, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> splitPart(Field<String> string, Field<String> delimiter, Field<? extends Number> n) {
        return new SplitPart(string, delimiter, n);
    }

    @Support
    @NotNull
    public static Field<String> substring(Field<String> string, int startingPosition, int length) {
        return new Substring(string, Tools.field(startingPosition), Tools.field(length));
    }

    @Support
    @NotNull
    public static Field<String> substring(Field<String> string, int startingPosition, Field<? extends Number> length) {
        return new Substring(string, Tools.field(startingPosition), length);
    }

    @Support
    @NotNull
    public static Field<String> substring(Field<String> string, Field<? extends Number> startingPosition, int length) {
        return new Substring(string, startingPosition, Tools.field(length));
    }

    @Support
    @NotNull
    public static Field<String> substring(Field<String> string, Field<? extends Number> startingPosition, Field<? extends Number> length) {
        return new Substring(string, startingPosition, length);
    }

    @Support
    @NotNull
    public static Field<String> substring(Field<String> string, int startingPosition) {
        return new Substring(string, Tools.field(startingPosition));
    }

    @Support
    @NotNull
    public static Field<String> substring(Field<String> string, Field<? extends Number> startingPosition) {
        return new Substring(string, startingPosition);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.DB2, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.VERTICA})
    @NotNull
    public static Field<String> substringIndex(Field<String> string, String delimiter, int n) {
        return new SubstringIndex(string, Tools.field(delimiter), Tools.field(n));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.DB2, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.VERTICA})
    @NotNull
    public static Field<String> substringIndex(Field<String> string, String delimiter, Field<? extends Number> n) {
        return new SubstringIndex(string, Tools.field(delimiter), n);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.DB2, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.VERTICA})
    @NotNull
    public static Field<String> substringIndex(Field<String> string, Field<String> delimiter, int n) {
        return new SubstringIndex(string, delimiter, Tools.field(n));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.DB2, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.VERTICA})
    @NotNull
    public static Field<String> substringIndex(Field<String> string, Field<String> delimiter, Field<? extends Number> n) {
        return new SubstringIndex(string, delimiter, n);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT})
    @NotNull
    public static Field<String> toChar(Object value, String formatMask) {
        return new ToChar(Tools.field(value), Tools.field(formatMask));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT})
    @NotNull
    public static Field<String> toChar(Object value, Field<String> formatMask) {
        return new ToChar(Tools.field(value), formatMask);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT})
    @NotNull
    public static Field<String> toChar(Field<?> value, String formatMask) {
        return new ToChar(value, Tools.field(formatMask));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT})
    @NotNull
    public static Field<String> toChar(Field<?> value, Field<String> formatMask) {
        return new ToChar(value, formatMask);
    }

    @Support
    @NotNull
    public static Field<String> toChar(Object value) {
        return new ToChar(Tools.field(value));
    }

    @Support
    @NotNull
    public static Field<String> toChar(Field<?> value) {
        return new ToChar(value);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<java.sql.Date> toDate(String value, String formatMask) {
        return new ToDate(Tools.field(value), Tools.field(formatMask));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<java.sql.Date> toDate(String value, Field<String> formatMask) {
        return new ToDate(Tools.field(value), formatMask);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<java.sql.Date> toDate(Field<String> value, String formatMask) {
        return new ToDate(value, Tools.field(formatMask));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<java.sql.Date> toDate(Field<String> value, Field<String> formatMask) {
        return new ToDate(value, formatMask);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> toHex(Number value) {
        return new ToHex(Tools.field(value));
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> toHex(Field<? extends Number> value) {
        return new ToHex(value);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Timestamp> toTimestamp(String value, String formatMask) {
        return new ToTimestamp(Tools.field(value), Tools.field(formatMask));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Timestamp> toTimestamp(String value, Field<String> formatMask) {
        return new ToTimestamp(Tools.field(value), formatMask);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Timestamp> toTimestamp(Field<String> value, String formatMask) {
        return new ToTimestamp(value, Tools.field(formatMask));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Timestamp> toTimestamp(Field<String> value, Field<String> formatMask) {
        return new ToTimestamp(value, formatMask);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2017, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> translate(Field<String> string, String from, String to) {
        return new Translate(string, Tools.field(from), Tools.field(to));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2017, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> translate(Field<String> string, String from, Field<String> to) {
        return new Translate(string, Tools.field(from), to);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2017, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> translate(Field<String> string, Field<String> from, String to) {
        return new Translate(string, from, Tools.field(to));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2017, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> translate(Field<String> string, Field<String> from, Field<String> to) {
        return new Translate(string, from, to);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2017, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<String> trim(String string, String characters) {
        return new Trim(Tools.field(string), Tools.field(characters));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2017, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<String> trim(String string, Field<String> characters) {
        return new Trim(Tools.field(string), characters);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2017, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<String> trim(Field<String> string, String characters) {
        return new Trim(string, Tools.field(characters));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2017, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<String> trim(Field<String> string, Field<String> characters) {
        return new Trim(string, characters);
    }

    @Support
    @NotNull
    public static Field<String> trim(String string) {
        return new Trim(Tools.field(string));
    }

    @Support
    @NotNull
    public static Field<String> trim(Field<String> string) {
        return new Trim(string);
    }

    @Support
    @NotNull
    public static Field<String> upper(String string) {
        return new Upper(Tools.field(string));
    }

    @Support
    @NotNull
    public static Field<String> upper(Field<String> string) {
        return new Upper(string);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_13, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.VERTICA})
    @NotNull
    public static Field<UUID> uuid() {
        return new Uuid();
    }

    @Support
    @NotNull
    public static Field<java.sql.Date> dateAdd(java.sql.Date date, Number interval, DatePart datePart) {
        return new DateAdd<java.sql.Date>(Tools.field(date), Tools.field(interval), datePart);
    }

    @Support
    @NotNull
    public static Field<java.sql.Date> dateAdd(java.sql.Date date, Field<? extends Number> interval, DatePart datePart) {
        return new DateAdd<java.sql.Date>(Tools.field(date), interval, datePart);
    }

    @Support
    @NotNull
    public static Field<java.sql.Date> dateAdd(Field<java.sql.Date> date, Number interval, DatePart datePart) {
        return new DateAdd<java.sql.Date>(date, Tools.field(interval), datePart);
    }

    @Support
    @NotNull
    public static Field<java.sql.Date> dateAdd(Field<java.sql.Date> date, Field<? extends Number> interval, DatePart datePart) {
        return new DateAdd<java.sql.Date>(date, interval, datePart);
    }

    @Support
    @NotNull
    public static Field<java.sql.Date> dateAdd(java.sql.Date date, Number interval) {
        return new DateAdd<java.sql.Date>(Tools.field(date), Tools.field(interval));
    }

    @Support
    @NotNull
    public static Field<java.sql.Date> dateAdd(java.sql.Date date, Field<? extends Number> interval) {
        return new DateAdd<java.sql.Date>(Tools.field(date), interval);
    }

    @Support
    @NotNull
    public static Field<java.sql.Date> dateAdd(Field<java.sql.Date> date, Number interval) {
        return new DateAdd<java.sql.Date>(date, Tools.field(interval));
    }

    @Support
    @NotNull
    public static Field<java.sql.Date> dateAdd(Field<java.sql.Date> date, Field<? extends Number> interval) {
        return new DateAdd<java.sql.Date>(date, interval);
    }

    @Support
    @NotNull
    public static Field<LocalDate> localDateAdd(LocalDate date, Number interval, DatePart datePart) {
        return new DateAdd<LocalDate>(Tools.field(date), Tools.field(interval), datePart);
    }

    @Support
    @NotNull
    public static Field<LocalDate> localDateAdd(LocalDate date, Field<? extends Number> interval, DatePart datePart) {
        return new DateAdd<LocalDate>(Tools.field(date), interval, datePart);
    }

    @Support
    @NotNull
    public static Field<LocalDate> localDateAdd(Field<LocalDate> date, Number interval, DatePart datePart) {
        return new DateAdd<LocalDate>(date, Tools.field(interval), datePart);
    }

    @Support
    @NotNull
    public static Field<LocalDate> localDateAdd(Field<LocalDate> date, Field<? extends Number> interval, DatePart datePart) {
        return new DateAdd<LocalDate>(date, interval, datePart);
    }

    @Support
    @NotNull
    public static Field<LocalDate> localDateAdd(LocalDate date, Number interval) {
        return new DateAdd<LocalDate>(Tools.field(date), Tools.field(interval));
    }

    @Support
    @NotNull
    public static Field<LocalDate> localDateAdd(LocalDate date, Field<? extends Number> interval) {
        return new DateAdd<LocalDate>(Tools.field(date), interval);
    }

    @Support
    @NotNull
    public static Field<LocalDate> localDateAdd(Field<LocalDate> date, Number interval) {
        return new DateAdd<LocalDate>(date, Tools.field(interval));
    }

    @Support
    @NotNull
    public static Field<LocalDate> localDateAdd(Field<LocalDate> date, Field<? extends Number> interval) {
        return new DateAdd<LocalDate>(date, interval);
    }

    @Support
    @NotNull
    public static Field<Timestamp> timestampAdd(Timestamp date, Number interval, DatePart datePart) {
        return new DateAdd<Timestamp>(Tools.field(date), Tools.field(interval), datePart);
    }

    @Support
    @NotNull
    public static Field<Timestamp> timestampAdd(Timestamp date, Field<? extends Number> interval, DatePart datePart) {
        return new DateAdd<Timestamp>(Tools.field(date), interval, datePart);
    }

    @Support
    @NotNull
    public static Field<Timestamp> timestampAdd(Field<Timestamp> date, Number interval, DatePart datePart) {
        return new DateAdd<Timestamp>(date, Tools.field(interval), datePart);
    }

    @Support
    @NotNull
    public static Field<Timestamp> timestampAdd(Field<Timestamp> date, Field<? extends Number> interval, DatePart datePart) {
        return new DateAdd<Timestamp>(date, interval, datePart);
    }

    @Support
    @NotNull
    public static Field<Timestamp> timestampAdd(Timestamp date, Number interval) {
        return new DateAdd<Timestamp>(Tools.field(date), Tools.field(interval));
    }

    @Support
    @NotNull
    public static Field<Timestamp> timestampAdd(Timestamp date, Field<? extends Number> interval) {
        return new DateAdd<Timestamp>(Tools.field(date), interval);
    }

    @Support
    @NotNull
    public static Field<Timestamp> timestampAdd(Field<Timestamp> date, Number interval) {
        return new DateAdd<Timestamp>(date, Tools.field(interval));
    }

    @Support
    @NotNull
    public static Field<Timestamp> timestampAdd(Field<Timestamp> date, Field<? extends Number> interval) {
        return new DateAdd<Timestamp>(date, interval);
    }

    @Support
    @NotNull
    public static Field<LocalDateTime> localDateTimeAdd(LocalDateTime date, Number interval, DatePart datePart) {
        return new DateAdd<LocalDateTime>(Tools.field(date), Tools.field(interval), datePart);
    }

    @Support
    @NotNull
    public static Field<LocalDateTime> localDateTimeAdd(LocalDateTime date, Field<? extends Number> interval, DatePart datePart) {
        return new DateAdd<LocalDateTime>(Tools.field(date), interval, datePart);
    }

    @Support
    @NotNull
    public static Field<LocalDateTime> localDateTimeAdd(Field<LocalDateTime> date, Number interval, DatePart datePart) {
        return new DateAdd<LocalDateTime>(date, Tools.field(interval), datePart);
    }

    @Support
    @NotNull
    public static Field<LocalDateTime> localDateTimeAdd(Field<LocalDateTime> date, Field<? extends Number> interval, DatePart datePart) {
        return new DateAdd<LocalDateTime>(date, interval, datePart);
    }

    @Support
    @NotNull
    public static Field<LocalDateTime> localDateTimeAdd(LocalDateTime date, Number interval) {
        return new DateAdd<LocalDateTime>(Tools.field(date), Tools.field(interval));
    }

    @Support
    @NotNull
    public static Field<LocalDateTime> localDateTimeAdd(LocalDateTime date, Field<? extends Number> interval) {
        return new DateAdd<LocalDateTime>(Tools.field(date), interval);
    }

    @Support
    @NotNull
    public static Field<LocalDateTime> localDateTimeAdd(Field<LocalDateTime> date, Number interval) {
        return new DateAdd<LocalDateTime>(date, Tools.field(interval));
    }

    @Support
    @NotNull
    public static Field<LocalDateTime> localDateTimeAdd(Field<LocalDateTime> date, Field<? extends Number> interval) {
        return new DateAdd<LocalDateTime>(date, interval);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> cardinality(Field<? extends Object[]> array) {
        return new Cardinality(array);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T> arrayGet(Field<T[]> array, int index) {
        return new ArrayGet(array, Tools.field(index));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T> arrayGet(Field<T[]> array, Field<Integer> index) {
        return new ArrayGet(array, index);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayConcat(T[] arg1, T[] arg2) {
        return new ArrayConcat(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayConcat(T[] arg1, Field<T[]> arg2) {
        return new ArrayConcat(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayConcat(Field<T[]> arg1, T[] arg2) {
        return new ArrayConcat(arg1, Tools.field(arg2, arg1));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayConcat(Field<T[]> arg1, Field<T[]> arg2) {
        return new ArrayConcat(arg1, arg2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayAppend(T[] arg1, T arg2) {
        return new ArrayAppend<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayAppend(T[] arg1, Field<T> arg2) {
        return new ArrayAppend<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayAppend(Field<T[]> arg1, T arg2) {
        return new ArrayAppend<T>(arg1, Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayAppend(Field<T[]> arg1, Field<T> arg2) {
        return new ArrayAppend<T>(arg1, arg2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayPrepend(T arg1, T[] arg2) {
        return new ArrayPrepend<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayPrepend(T arg1, Field<T[]> arg2) {
        return new ArrayPrepend<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayPrepend(Field<T> arg1, T[] arg2) {
        return new ArrayPrepend<T>(arg1, Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayPrepend(Field<T> arg1, Field<T[]> arg2) {
        return new ArrayPrepend<T>(arg1, arg2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Condition arrayOverlap(T[] arg1, T[] arg2) {
        return new ArrayOverlap(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Condition arrayOverlap(T[] arg1, Field<T[]> arg2) {
        return new ArrayOverlap(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Condition arrayOverlap(Field<T[]> arg1, T[] arg2) {
        return new ArrayOverlap(arg1, Tools.field(arg2, arg1));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Condition arrayOverlap(Field<T[]> arg1, Field<T[]> arg2) {
        return new ArrayOverlap(arg1, arg2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayRemove(T[] arg1, T arg2) {
        return new ArrayRemove<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayRemove(T[] arg1, Field<T> arg2) {
        return new ArrayRemove<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayRemove(Field<T[]> arg1, T arg2) {
        return new ArrayRemove<T>(arg1, Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayRemove(Field<T[]> arg1, Field<T> arg2) {
        return new ArrayRemove<T>(arg1, arg2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayReplace(T[] arg1, T arg2, T arg3) {
        return new ArrayReplace<T>(Tools.field(arg1), Tools.field(arg2), Tools.field(arg3));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayReplace(Field<T[]> arg1, Field<T> arg2, Field<T> arg3) {
        return new ArrayReplace<T>(arg1, arg2, arg3);
    }

    @Support
    @NotNull
    public static <T> Field<T> nvl(T value, T defaultValue) {
        return new Nvl<T>(Tools.field(value), Tools.field(defaultValue));
    }

    @Support
    @NotNull
    public static <T> Field<T> nvl(T value, Field<T> defaultValue) {
        return new Nvl<T>(Tools.field(value), defaultValue);
    }

    @Support
    @NotNull
    public static <T> Field<T> nvl(Field<T> value, T defaultValue) {
        return new Nvl<T>(value, Tools.field(defaultValue, value));
    }

    @Support
    @NotNull
    public static <T> Field<T> nvl(Field<T> value, Field<T> defaultValue) {
        return new Nvl<T>(value, defaultValue);
    }

    @Support
    @NotNull
    public static <T> Field<T> isnull(T value, T defaultValue) {
        return DSL.nvl(value, defaultValue);
    }

    @Support
    @NotNull
    public static <T> Field<T> isnull(T value, Field<T> defaultValue) {
        return DSL.nvl(value, defaultValue);
    }

    @Support
    @NotNull
    public static <T> Field<T> isnull(Field<T> value, T defaultValue) {
        return DSL.nvl(value, defaultValue);
    }

    @Support
    @NotNull
    public static <T> Field<T> isnull(Field<T> value, Field<T> defaultValue) {
        return DSL.nvl(value, defaultValue);
    }

    @Support
    @NotNull
    public static <T> Field<T> ifnull(T value, T defaultValue) {
        return DSL.nvl(value, defaultValue);
    }

    @Support
    @NotNull
    public static <T> Field<T> ifnull(T value, Field<T> defaultValue) {
        return DSL.nvl(value, defaultValue);
    }

    @Support
    @NotNull
    public static <T> Field<T> ifnull(Field<T> value, T defaultValue) {
        return DSL.nvl(value, defaultValue);
    }

    @Support
    @NotNull
    public static <T> Field<T> ifnull(Field<T> value, Field<T> defaultValue) {
        return DSL.nvl(value, defaultValue);
    }

    @Support
    @NotNull
    public static <T> Field<T> nullif(T value, T other) {
        return new Nullif<T>(Tools.field(value), Tools.field(other));
    }

    @Support
    @NotNull
    public static <T> Field<T> nullif(T value, Field<T> other) {
        return new Nullif<T>(Tools.field(value), other);
    }

    @Support
    @NotNull
    public static <T> Field<T> nullif(Field<T> value, T other) {
        return new Nullif<T>(value, Tools.field(other, value));
    }

    @Support
    @NotNull
    public static <T> Field<T> nullif(Field<T> value, Field<T> other) {
        return new Nullif<T>(value, other);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.INFORMIX, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> currentCatalog() {
        return new CurrentCatalog();
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> currentSchema() {
        return new CurrentSchema();
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> currentUser() {
        return new CurrentUser();
    }

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE})
    @NotNull
    @Pro
    public static Field<Boolean> connectByIsCycle() {
        return new ConnectByIsCycle();
    }

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE})
    @NotNull
    @Pro
    public static Field<Boolean> connectByIsLeaf() {
        return new ConnectByIsLeaf();
    }

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @NotNull
    @Pro
    public static <T> Field<T> connectByRoot(Field<T> field2) {
        return new ConnectByRoot<T>(field2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static Field<Integer> level() {
        return new Level();
    }

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @NotNull
    @Pro
    public static <T> Field<T> prior(Field<T> field2) {
        return new Prior<T>(field2);
    }

    @Support(value={SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE})
    @NotNull
    @Pro
    public static Field<String> sysConnectByPath(Field<?> field2, String separator) {
        return new SysConnectByPath(field2, separator);
    }

    @Support
    @NotNull
    @Pro
    public static Field<Integer> rownum() {
        return new Rownum();
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlcomment(String comment) {
        return new XMLComment(Tools.field(comment));
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlcomment(Field<String> comment) {
        return new XMLComment(comment);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlconcat(Field<?> ... args2) {
        return new XMLConcat(Arrays.asList(args2));
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlconcat(Collection<? extends Field<?>> args2) {
        return new XMLConcat(new QueryPartList((Iterable<? extends Field<?>>)args2));
    }

    @Support(value={SQLDialect.DB2, SQLDialect.TERADATA})
    @NotNull
    @Pro
    public static Field<XML> xmldocument(Field<XML> content) {
        return new XMLDocument(content);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlforest(Field<?> ... fields) {
        return new XMLForest(Arrays.asList(fields));
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlforest(Collection<? extends Field<?>> fields) {
        return new XMLForest(new QueryPartList((Iterable<? extends Field<?>>)fields));
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlpi(String target, Field<?> content) {
        return new XMLPi(DSL.name(target), content);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlpi(Name target, Field<?> content) {
        return new XMLPi(target, content);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlpi(String target) {
        return new XMLPi(DSL.name(target));
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlpi(Name target) {
        return new XMLPi(target);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA})
    @NotNull
    public static <T> Field<T> xmlserializeDocument(XML value, DataType<T> type) {
        return new XMLSerialize<T>(false, Tools.field(value), type);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA})
    @NotNull
    public static <T> Field<T> xmlserializeDocument(Field<XML> value, DataType<T> type) {
        return new XMLSerialize<T>(false, value, type);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA})
    @NotNull
    public static <T> Field<T> xmlserializeContent(XML value, DataType<T> type) {
        return new XMLSerialize<T>(true, Tools.field(value), type);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA})
    @NotNull
    public static <T> Field<T> xmlserializeContent(Field<XML> value, DataType<T> type) {
        return new XMLSerialize<T>(true, value, type);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONArrayNullStep<JSON> jsonArray(Field<?> ... fields) {
        return new JSONArray<JSON>(SQLDataType.JSON, Arrays.asList(fields));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONArrayNullStep<JSON> jsonArray(Collection<? extends Field<?>> fields) {
        return new JSONArray<JSON>(SQLDataType.JSON, new QueryPartList((Iterable<? extends Field<?>>)fields));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONArrayNullStep<JSONB> jsonbArray(Field<?> ... fields) {
        return new JSONArray<JSONB>(SQLDataType.JSONB, Arrays.asList(fields));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONArrayNullStep<JSONB> jsonbArray(Collection<? extends Field<?>> fields) {
        return new JSONArray<JSONB>(SQLDataType.JSONB, new QueryPartList((Iterable<? extends Field<?>>)fields));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectNullStep<JSON> jsonObject(JSONEntry<?> ... entries) {
        return new JSONObject<JSON>(SQLDataType.JSON, Arrays.asList(entries));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectNullStep<JSON> jsonObject(Collection<? extends JSONEntry<?>> entries) {
        return new JSONObject<JSON>(SQLDataType.JSON, new QueryPartList((Iterable<? extends JSONEntry<?>>)entries));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectNullStep<JSONB> jsonbObject(JSONEntry<?> ... entries) {
        return new JSONObject<JSONB>(SQLDataType.JSONB, Arrays.asList(entries));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectNullStep<JSONB> jsonbObject(Collection<? extends JSONEntry<?>> entries) {
        return new JSONObject<JSONB>(SQLDataType.JSONB, new QueryPartList((Iterable<? extends JSONEntry<?>>)entries));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE_3_38, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSON> jsonGetElement(JSON field2, int index) {
        return new JSONGetElement(Tools.field(field2), Tools.field(index));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE_3_38, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSON> jsonGetElement(JSON field2, Field<Integer> index) {
        return new JSONGetElement(Tools.field(field2), index);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE_3_38, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSON> jsonGetElement(Field<JSON> field2, int index) {
        return new JSONGetElement(field2, Tools.field(index));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE_3_38, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSON> jsonGetElement(Field<JSON> field2, Field<Integer> index) {
        return new JSONGetElement(field2, index);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE_3_38, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSONB> jsonbGetElement(JSONB field2, int index) {
        return new JSONBGetElement(Tools.field(field2), Tools.field(index));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE_3_38, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSONB> jsonbGetElement(JSONB field2, Field<Integer> index) {
        return new JSONBGetElement(Tools.field(field2), index);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE_3_38, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSONB> jsonbGetElement(Field<JSONB> field2, int index) {
        return new JSONBGetElement(field2, Tools.field(index));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE_3_38, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSONB> jsonbGetElement(Field<JSONB> field2, Field<Integer> index) {
        return new JSONBGetElement(field2, index);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> jsonGetElementAsText(JSON field2, int index) {
        return new JSONGetElementAsText(Tools.field(field2), Tools.field(index));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> jsonGetElementAsText(JSON field2, Field<Integer> index) {
        return new JSONGetElementAsText(Tools.field(field2), index);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> jsonGetElementAsText(Field<JSON> field2, int index) {
        return new JSONGetElementAsText(field2, Tools.field(index));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> jsonGetElementAsText(Field<JSON> field2, Field<Integer> index) {
        return new JSONGetElementAsText(field2, index);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> jsonbGetElementAsText(JSONB field2, int index) {
        return new JSONBGetElementAsText(Tools.field(field2), Tools.field(index));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> jsonbGetElementAsText(JSONB field2, Field<Integer> index) {
        return new JSONBGetElementAsText(Tools.field(field2), index);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> jsonbGetElementAsText(Field<JSONB> field2, int index) {
        return new JSONBGetElementAsText(field2, Tools.field(index));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> jsonbGetElementAsText(Field<JSONB> field2, Field<Integer> index) {
        return new JSONBGetElementAsText(field2, index);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE_3_38, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSON> jsonGetAttribute(JSON field2, String attribute) {
        return new JSONGetAttribute(Tools.field(field2), Tools.field(attribute));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE_3_38, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSON> jsonGetAttribute(JSON field2, Field<String> attribute) {
        return new JSONGetAttribute(Tools.field(field2), attribute);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE_3_38, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSON> jsonGetAttribute(Field<JSON> field2, String attribute) {
        return new JSONGetAttribute(field2, Tools.field(attribute));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE_3_38, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSON> jsonGetAttribute(Field<JSON> field2, Field<String> attribute) {
        return new JSONGetAttribute(field2, attribute);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE_3_38, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSONB> jsonbGetAttribute(JSONB field2, String attribute) {
        return new JSONBGetAttribute(Tools.field(field2), Tools.field(attribute));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE_3_38, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSONB> jsonbGetAttribute(JSONB field2, Field<String> attribute) {
        return new JSONBGetAttribute(Tools.field(field2), attribute);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE_3_38, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSONB> jsonbGetAttribute(Field<JSONB> field2, String attribute) {
        return new JSONBGetAttribute(field2, Tools.field(attribute));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE_3_38, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSONB> jsonbGetAttribute(Field<JSONB> field2, Field<String> attribute) {
        return new JSONBGetAttribute(field2, attribute);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> jsonGetAttributeAsText(JSON field2, String attribute) {
        return new JSONGetAttributeAsText(Tools.field(field2), Tools.field(attribute));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> jsonGetAttributeAsText(JSON field2, Field<String> attribute) {
        return new JSONGetAttributeAsText(Tools.field(field2), attribute);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> jsonGetAttributeAsText(Field<JSON> field2, String attribute) {
        return new JSONGetAttributeAsText(field2, Tools.field(attribute));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> jsonGetAttributeAsText(Field<JSON> field2, Field<String> attribute) {
        return new JSONGetAttributeAsText(field2, attribute);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> jsonbGetAttributeAsText(JSONB field2, String attribute) {
        return new JSONBGetAttributeAsText(Tools.field(field2), Tools.field(attribute));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> jsonbGetAttributeAsText(JSONB field2, Field<String> attribute) {
        return new JSONBGetAttributeAsText(Tools.field(field2), attribute);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> jsonbGetAttributeAsText(Field<JSONB> field2, String attribute) {
        return new JSONBGetAttributeAsText(field2, Tools.field(attribute));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> jsonbGetAttributeAsText(Field<JSONB> field2, Field<String> attribute) {
        return new JSONBGetAttributeAsText(field2, attribute);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSON> jsonKeys(JSON field2) {
        return new JSONKeys(Tools.field(field2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSON> jsonKeys(Field<JSON> field2) {
        return new JSONKeys(field2);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSONB> jsonbKeys(JSONB field2) {
        return new JSONBKeys(Tools.field(field2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<JSONB> jsonbKeys(Field<JSONB> field2) {
        return new JSONBKeys(field2);
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSON> jsonSet(Field<JSON> field2, String path, Object value) {
        return new JSONSet(field2, Tools.field(path), Tools.field(value));
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSON> jsonSet(Field<JSON> field2, String path, Field<?> value) {
        return new JSONSet(field2, Tools.field(path), value);
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSON> jsonSet(Field<JSON> field2, Field<String> path, Object value) {
        return new JSONSet(field2, path, Tools.field(value));
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSON> jsonSet(Field<JSON> field2, Field<String> path, Field<?> value) {
        return new JSONSet(field2, path, value);
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSONB> jsonbSet(Field<JSONB> field2, String path, Object value) {
        return new JSONBSet(field2, Tools.field(path), Tools.field(value));
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSONB> jsonbSet(Field<JSONB> field2, String path, Field<?> value) {
        return new JSONBSet(field2, Tools.field(path), value);
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSONB> jsonbSet(Field<JSONB> field2, Field<String> path, Object value) {
        return new JSONBSet(field2, path, Tools.field(value));
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSONB> jsonbSet(Field<JSONB> field2, Field<String> path, Field<?> value) {
        return new JSONBSet(field2, path, value);
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSON> jsonInsert(Field<JSON> field2, String path, Object value) {
        return new JSONInsert(field2, Tools.field(path), Tools.field(value));
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSON> jsonInsert(Field<JSON> field2, String path, Field<?> value) {
        return new JSONInsert(field2, Tools.field(path), value);
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSON> jsonInsert(Field<JSON> field2, Field<String> path, Object value) {
        return new JSONInsert(field2, path, Tools.field(value));
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSON> jsonInsert(Field<JSON> field2, Field<String> path, Field<?> value) {
        return new JSONInsert(field2, path, value);
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSONB> jsonbInsert(Field<JSONB> field2, String path, Object value) {
        return new JSONBInsert(field2, Tools.field(path), Tools.field(value));
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSONB> jsonbInsert(Field<JSONB> field2, String path, Field<?> value) {
        return new JSONBInsert(field2, Tools.field(path), value);
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSONB> jsonbInsert(Field<JSONB> field2, Field<String> path, Object value) {
        return new JSONBInsert(field2, path, Tools.field(value));
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSONB> jsonbInsert(Field<JSONB> field2, Field<String> path, Field<?> value) {
        return new JSONBInsert(field2, path, value);
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSON> jsonReplace(Field<JSON> field2, String path, Object value) {
        return new JSONReplace(field2, Tools.field(path), Tools.field(value));
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSON> jsonReplace(Field<JSON> field2, String path, Field<?> value) {
        return new JSONReplace(field2, Tools.field(path), value);
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSON> jsonReplace(Field<JSON> field2, Field<String> path, Object value) {
        return new JSONReplace(field2, path, Tools.field(value));
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSON> jsonReplace(Field<JSON> field2, Field<String> path, Field<?> value) {
        return new JSONReplace(field2, path, value);
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSONB> jsonbReplace(Field<JSONB> field2, String path, Object value) {
        return new JSONBReplace(field2, Tools.field(path), Tools.field(value));
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSONB> jsonbReplace(Field<JSONB> field2, String path, Field<?> value) {
        return new JSONBReplace(field2, Tools.field(path), value);
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSONB> jsonbReplace(Field<JSONB> field2, Field<String> path, Object value) {
        return new JSONBReplace(field2, path, Tools.field(value));
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSONB> jsonbReplace(Field<JSONB> field2, Field<String> path, Field<?> value) {
        return new JSONBReplace(field2, path, value);
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSON> jsonRemove(Field<JSON> field2, String path) {
        return new JSONRemove(field2, Tools.field(path));
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSON> jsonRemove(Field<JSON> field2, Field<String> path) {
        return new JSONRemove(field2, path);
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSONB> jsonbRemove(Field<JSONB> field2, String path) {
        return new JSONBRemove(field2, Tools.field(path));
    }

    @Support(value={SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE21C, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016})
    @NotNull
    public static Field<JSONB> jsonbRemove(Field<JSONB> field2, Field<String> path) {
        return new JSONBRemove(field2, path);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE})
    @NotNull
    @Pro
    public static Condition deleting() {
        return new Deleting();
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE})
    @NotNull
    @Pro
    public static Condition inserting() {
        return new Inserting();
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE})
    @NotNull
    @Pro
    public static Condition updating() {
        return new Updating();
    }

    @Support
    @NotNull
    public static Field<Boolean> field(Condition condition) {
        Field<Boolean> field2;
        if (condition instanceof NoCondition) {
            field2 = DSL.noField(SQLDataType.BOOLEAN);
        } else if (condition instanceof FieldCondition) {
            FieldCondition f = (FieldCondition)condition;
            field2 = f.field;
        } else {
            field2 = new ConditionAsField(condition);
        }
        return field2;
    }

    @Support
    @NotNull
    public static Condition condition(Field<Boolean> field2) {
        Condition condition;
        if (field2 instanceof Condition) {
            condition = (Condition)field2;
        } else if (field2 instanceof NoField) {
            condition = DSL.noCondition();
        } else if (field2 instanceof ConditionAsField) {
            ConditionAsField c = (ConditionAsField)field2;
            condition = c.condition;
        } else {
            condition = new FieldCondition(field2);
        }
        return condition;
    }

    @Support
    @NotNull
    public static <T> AggregateFunction<T> anyValue(Field<T> field2) {
        return new AnyValue<T>(field2);
    }

    @Support
    @NotNull
    public static AggregateFunction<BigDecimal> avg(Field<? extends Number> field2) {
        return new Avg(field2, false);
    }

    @Support
    @NotNull
    public static AggregateFunction<BigDecimal> avgDistinct(Field<? extends Number> field2) {
        return new Avg(field2, true);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> AggregateFunction<T> bitAndAgg(Field<T> value) {
        return new BitAndAgg<T>(value);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> AggregateFunction<T> bitOrAgg(Field<T> value) {
        return new BitOrAgg<T>(value);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> AggregateFunction<T> bitXorAgg(Field<T> value) {
        return new BitXorAgg<T>(value);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> AggregateFunction<T> bitNandAgg(Field<T> value) {
        return new BitNandAgg<T>(value);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> AggregateFunction<T> bitNorAgg(Field<T> value) {
        return new BitNorAgg<T>(value);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T extends Number> AggregateFunction<T> bitXNorAgg(Field<T> value) {
        return new BitXNorAgg<T>(value);
    }

    @Support
    @NotNull
    public static AggregateFunction<Boolean> boolAnd(Field<Boolean> condition) {
        return new BoolAnd(DSL.condition(condition));
    }

    @Support
    @NotNull
    public static AggregateFunction<Boolean> boolAnd(Condition condition) {
        return new BoolAnd(condition);
    }

    @Support
    @NotNull
    public static AggregateFunction<Boolean> boolOr(Field<Boolean> condition) {
        return new BoolOr(DSL.condition(condition));
    }

    @Support
    @NotNull
    public static AggregateFunction<Boolean> boolOr(Condition condition) {
        return new BoolOr(condition);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static AggregateFunction<BigDecimal> corr(Field<? extends Number> y, Field<? extends Number> x) {
        return new Corr(y, x);
    }

    @Support
    @NotNull
    public static AggregateFunction<Integer> count(Field<?> field2) {
        return new Count(field2, false);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static AggregateFunction<Integer> countDistinct(Field<?> field2) {
        return new Count(field2, true);
    }

    @Support
    @NotNull
    public static AggregateFunction<BigDecimal> covarSamp(Field<? extends Number> y, Field<? extends Number> x) {
        return new CovarSamp(y, x);
    }

    @Support
    @NotNull
    public static AggregateFunction<BigDecimal> covarPop(Field<? extends Number> y, Field<? extends Number> x) {
        return new CovarPop(y, x);
    }

    @Support
    @NotNull
    public static AggregateFunction<Boolean> every(Field<Boolean> condition) {
        return DSL.boolAnd(condition);
    }

    @Support
    @NotNull
    public static AggregateFunction<Boolean> every(Condition condition) {
        return DSL.boolAnd(condition);
    }

    @Support
    @NotNull
    public static <T> AggregateFunction<T> max(Field<T> field2) {
        return new Max<T>(field2, false);
    }

    @Support
    @NotNull
    public static <T> AggregateFunction<T> maxDistinct(Field<T> field2) {
        return new Max<T>(field2, true);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2_11, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_4, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static AggregateFunction<BigDecimal> median(Field<? extends Number> field2) {
        return new Median(field2);
    }

    @Support
    @NotNull
    public static <T> AggregateFunction<T> min(Field<T> field2) {
        return new Min<T>(field2, false);
    }

    @Support
    @NotNull
    public static <T> AggregateFunction<T> minDistinct(Field<T> field2) {
        return new Min<T>(field2, true);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static AggregateFunction<BigDecimal> product(Field<? extends Number> field2) {
        return new Product(field2, false);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static AggregateFunction<BigDecimal> productDistinct(Field<? extends Number> field2) {
        return new Product(field2, true);
    }

    @Support
    @NotNull
    public static AggregateFunction<BigDecimal> regrAvgX(Field<? extends Number> y, Field<? extends Number> x) {
        return new RegrAvgX(y, x);
    }

    @Support
    @NotNull
    public static AggregateFunction<BigDecimal> regrAvgY(Field<? extends Number> y, Field<? extends Number> x) {
        return new RegrAvgY(y, x);
    }

    @Support
    @NotNull
    public static AggregateFunction<BigDecimal> regrCount(Field<? extends Number> y, Field<? extends Number> x) {
        return new RegrCount(y, x);
    }

    @Support
    @NotNull
    public static AggregateFunction<BigDecimal> regrIntercept(Field<? extends Number> y, Field<? extends Number> x) {
        return new RegrIntercept(y, x);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static AggregateFunction<BigDecimal> regrR2(Field<? extends Number> y, Field<? extends Number> x) {
        return new RegrR2(y, x);
    }

    @Support
    @NotNull
    public static AggregateFunction<BigDecimal> regrSlope(Field<? extends Number> y, Field<? extends Number> x) {
        return new RegrSlope(y, x);
    }

    @Support
    @NotNull
    public static AggregateFunction<BigDecimal> regrSXX(Field<? extends Number> y, Field<? extends Number> x) {
        return new RegrSxx(y, x);
    }

    @Support
    @NotNull
    public static AggregateFunction<BigDecimal> regrSXY(Field<? extends Number> y, Field<? extends Number> x) {
        return new RegrSxy(y, x);
    }

    @Support
    @NotNull
    public static AggregateFunction<BigDecimal> regrSYY(Field<? extends Number> y, Field<? extends Number> x) {
        return new RegrSyy(y, x);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static AggregateFunction<BigDecimal> stddevPop(Field<? extends Number> field2) {
        return new StddevPop(field2);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2_11, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static AggregateFunction<BigDecimal> stddevSamp(Field<? extends Number> field2) {
        return new StddevSamp(field2);
    }

    @Support
    @NotNull
    public static AggregateFunction<BigDecimal> sum(Field<? extends Number> field2) {
        return new Sum(field2, false);
    }

    @Support
    @NotNull
    public static AggregateFunction<BigDecimal> sumDistinct(Field<? extends Number> field2) {
        return new Sum(field2, true);
    }

    @Support
    @NotNull
    public static AggregateFunction<BigDecimal> varPop(Field<? extends Number> field2) {
        return new VarPop(field2);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2_11, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static AggregateFunction<BigDecimal> varSamp(Field<? extends Number> field2) {
        return new VarSamp(field2);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Statement goto_(Label label) {
        return new Goto(label);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static Statement execute(String sql) {
        return new Execute(Tools.field(sql));
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static Statement execute(Field<String> sql) {
        return new Execute(sql);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static Statement return_(Object value) {
        return new Return(Tools.field(value));
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static Statement return_(Field<?> value) {
        return new Return(value);
    }

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static Statement return_() {
        return new Return();
    }

    @Support(value={SQLDialect.DB2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static SignalSetStep signalSQLState(Object value) {
        return new Signal(Tools.field(value));
    }

    @Support(value={SQLDialect.DB2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @Pro
    public static SignalSetStep signalSQLState(Field<?> value) {
        return new Signal(value);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Double> stArea(Spatial value) {
        return new StArea(Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Double> stArea(Field<? extends Spatial> value) {
        return new StArea(value);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<byte[]> stAsBinary(Spatial value) {
        return new StAsBinary(Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<byte[]> stAsBinary(Field<? extends Spatial> value) {
        return new StAsBinary(value);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<String> stAsText(Spatial value) {
        return new StAsText(Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<String> stAsText(Field<? extends Spatial> value) {
        return new StAsText(value);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stCentroid(Geometry value) {
        return new StCentroid(Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stCentroid(Field<Geometry> value) {
        return new StCentroid(value);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stContains(T arg1, T arg2) {
        return new StContains<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stContains(T arg1, Field<T> arg2) {
        return new StContains<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stContains(Field<T> arg1, T arg2) {
        return new StContains<T>(arg1, Tools.field(arg2, arg1));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stContains(Field<T> arg1, Field<T> arg2) {
        return new StContains<T>(arg1, arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Condition stCrosses(Geometry arg1, Geometry arg2) {
        return new StCrosses(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Condition stCrosses(Geometry arg1, Field<Geometry> arg2) {
        return new StCrosses(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Condition stCrosses(Field<Geometry> arg1, Geometry arg2) {
        return new StCrosses(arg1, Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Condition stCrosses(Field<Geometry> arg1, Field<Geometry> arg2) {
        return new StCrosses(arg1, arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stDifference(T arg1, T arg2) {
        return new StDifference<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stDifference(T arg1, Field<T> arg2) {
        return new StDifference<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stDifference(Field<T> arg1, T arg2) {
        return new StDifference<T>(arg1, Tools.field(arg2, arg1));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stDifference(Field<T> arg1, Field<T> arg2) {
        return new StDifference<T>(arg1, arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stDisjoint(T arg1, T arg2) {
        return new StDisjoint<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stDisjoint(T arg1, Field<T> arg2) {
        return new StDisjoint<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stDisjoint(Field<T> arg1, T arg2) {
        return new StDisjoint<T>(arg1, Tools.field(arg2, arg1));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stDisjoint(Field<T> arg1, Field<T> arg2) {
        return new StDisjoint<T>(arg1, arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<Double> stDistance(T arg1, T arg2) {
        return new StDistance<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<Double> stDistance(T arg1, Field<T> arg2) {
        return new StDistance<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<Double> stDistance(Field<T> arg1, T arg2) {
        return new StDistance<T>(arg1, Tools.field(arg2, arg1));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<Double> stDistance(Field<T> arg1, Field<T> arg2) {
        return new StDistance<T>(arg1, arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stEndPoint(T value) {
        return new StEndPoint<T>(Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stEndPoint(Field<T> value) {
        return new StEndPoint<T>(value);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stEquals(T arg1, T arg2) {
        return new StEquals<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stEquals(T arg1, Field<T> arg2) {
        return new StEquals<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stEquals(Field<T> arg1, T arg2) {
        return new StEquals<T>(arg1, Tools.field(arg2, arg1));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stEquals(Field<T> arg1, Field<T> arg2) {
        return new StEquals<T>(arg1, arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stExteriorRing(Geometry value) {
        return new StExteriorRing(Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stExteriorRing(Field<Geometry> value) {
        return new StExteriorRing(value);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stGeometryN(T value, int position) {
        return new StGeometryN<T>(Tools.field(value), Tools.field(position));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stGeometryN(T value, Field<? extends Number> position) {
        return new StGeometryN<T>(Tools.field(value), position);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stGeometryN(Field<T> value, int position) {
        return new StGeometryN<T>(value, Tools.field(position));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stGeometryN(Field<T> value, Field<? extends Number> position) {
        return new StGeometryN<T>(value, position);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<String> stGeometryType(Spatial value) {
        return new StGeometryType(Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<String> stGeometryType(Field<? extends Spatial> value) {
        return new StGeometryType(value);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stGeomFromText(String text, int srid) {
        return new StGeomFromText(Tools.field(text), Tools.field(srid));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stGeomFromText(String text, Field<? extends Number> srid) {
        return new StGeomFromText(Tools.field(text), srid);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stGeomFromText(Field<String> text, int srid) {
        return new StGeomFromText(text, Tools.field(srid));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stGeomFromText(Field<String> text, Field<? extends Number> srid) {
        return new StGeomFromText(text, srid);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stGeomFromText(String text) {
        return new StGeomFromText(Tools.field(text));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stGeomFromText(Field<String> text) {
        return new StGeomFromText(text);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stGeomFromWKB(byte[] wkb, int srid) {
        return new StGeomFromWKB(Tools.field(wkb), Tools.field(srid));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stGeomFromWKB(byte[] wkb, Field<? extends Number> srid) {
        return new StGeomFromWKB(Tools.field(wkb), srid);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stGeomFromWKB(Field<byte[]> wkb, int srid) {
        return new StGeomFromWKB(wkb, Tools.field(srid));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stGeomFromWKB(Field<byte[]> wkb, Field<? extends Number> srid) {
        return new StGeomFromWKB(wkb, srid);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stGeomFromWKB(byte[] wkb) {
        return new StGeomFromWKB(Tools.field(wkb));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stGeomFromWKB(Field<byte[]> wkb) {
        return new StGeomFromWKB(wkb);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stInteriorRingN(Geometry value, int position) {
        return new StInteriorRingN(Tools.field(value), Tools.field(position));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stInteriorRingN(Geometry value, Field<? extends Number> position) {
        return new StInteriorRingN(Tools.field(value), position);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stInteriorRingN(Field<Geometry> value, int position) {
        return new StInteriorRingN(value, Tools.field(position));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Geometry> stInteriorRingN(Field<Geometry> value, Field<? extends Number> position) {
        return new StInteriorRingN(value, position);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stIntersection(T arg1, T arg2) {
        return new StIntersection<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stIntersection(T arg1, Field<T> arg2) {
        return new StIntersection<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stIntersection(Field<T> arg1, T arg2) {
        return new StIntersection<T>(arg1, Tools.field(arg2, arg1));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stIntersection(Field<T> arg1, Field<T> arg2) {
        return new StIntersection<T>(arg1, arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stIntersects(T arg1, T arg2) {
        return new StIntersects<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stIntersects(T arg1, Field<T> arg2) {
        return new StIntersects<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stIntersects(Field<T> arg1, T arg2) {
        return new StIntersects<T>(arg1, Tools.field(arg2, arg1));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stIntersects(Field<T> arg1, Field<T> arg2) {
        return new StIntersects<T>(arg1, arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Condition stIsClosed(Spatial value) {
        return new StIsClosed(Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Condition stIsClosed(Field<? extends Spatial> value) {
        return new StIsClosed(value);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Condition stIsEmpty(Spatial value) {
        return new StIsEmpty(Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Condition stIsEmpty(Field<? extends Spatial> value) {
        return new StIsEmpty(value);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Double> stLength(Spatial value) {
        return new StLength(Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Double> stLength(Field<? extends Spatial> value) {
        return new StLength(value);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Integer> stNumGeometries(Spatial value) {
        return new StNumGeometries(Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Integer> stNumGeometries(Field<? extends Spatial> value) {
        return new StNumGeometries(value);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Integer> stNumInteriorRings(Geometry value) {
        return new StNumInteriorRings(Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Integer> stNumInteriorRings(Field<Geometry> value) {
        return new StNumInteriorRings(value);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Integer> stNumPoints(Spatial value) {
        return new StNumPoints(Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Integer> stNumPoints(Field<? extends Spatial> value) {
        return new StNumPoints(value);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stOverlaps(T arg1, T arg2) {
        return new StOverlaps<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stOverlaps(T arg1, Field<T> arg2) {
        return new StOverlaps<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stOverlaps(Field<T> arg1, T arg2) {
        return new StOverlaps<T>(arg1, Tools.field(arg2, arg1));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stOverlaps(Field<T> arg1, Field<T> arg2) {
        return new StOverlaps<T>(arg1, arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stPointN(T value, int position) {
        return new StPointN<T>(Tools.field(value), Tools.field(position));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stPointN(T value, Field<? extends Number> position) {
        return new StPointN<T>(Tools.field(value), position);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stPointN(Field<T> value, int position) {
        return new StPointN<T>(value, Tools.field(position));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stPointN(Field<T> value, Field<? extends Number> position) {
        return new StPointN<T>(value, position);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Integer> stSrid(Spatial value) {
        return new StSrid(Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Integer> stSrid(Field<? extends Spatial> value) {
        return new StSrid(value);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stStartPoint(T value) {
        return new StStartPoint<T>(Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stStartPoint(Field<T> value) {
        return new StStartPoint<T>(value);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stTouches(T arg1, T arg2) {
        return new StTouches<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stTouches(T arg1, Field<T> arg2) {
        return new StTouches<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stTouches(Field<T> arg1, T arg2) {
        return new StTouches<T>(arg1, Tools.field(arg2, arg1));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stTouches(Field<T> arg1, Field<T> arg2) {
        return new StTouches<T>(arg1, arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stUnion(T arg1, T arg2) {
        return new StUnion<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stUnion(T arg1, Field<T> arg2) {
        return new StUnion<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stUnion(Field<T> arg1, T arg2) {
        return new StUnion<T>(arg1, Tools.field(arg2, arg1));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Field<T> stUnion(Field<T> arg1, Field<T> arg2) {
        return new StUnion<T>(arg1, arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stWithin(T arg1, T arg2) {
        return new StWithin<T>(Tools.field(arg1), Tools.field(arg2));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stWithin(T arg1, Field<T> arg2) {
        return new StWithin<T>(Tools.field(arg1), arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stWithin(Field<T> arg1, T arg2) {
        return new StWithin<T>(arg1, Tools.field(arg2, arg1));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static <T extends Spatial> Condition stWithin(Field<T> arg1, Field<T> arg2) {
        return new StWithin<T>(arg1, arg2);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Double> stX(Geometry value) {
        return new StX(Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Double> stX(Field<Geometry> value) {
        return new StX(value);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Double> stY(Geometry value) {
        return new StY(Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER})
    @NotNull
    @Pro
    public static Field<Double> stY(Field<Geometry> value) {
        return new StY(value);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE})
    @NotNull
    @Pro
    public static Field<Double> stZ(Geometry value) {
        return new StZ(Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE})
    @NotNull
    @Pro
    public static Field<Double> stZ(Field<Geometry> value) {
        return new StZ(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<String> rpad(Field<String> field2, int length, char character) {
        return DSL.rpad(field2, length, Character.toString(character));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<String> lpad(Field<String> field2, int length, char character) {
        return DSL.lpad(field2, length, Character.toString(character));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA})
    @NotNull
    public static String escape(String value, char escape) {
        String esc = "" + escape;
        return StringUtils.replace(StringUtils.replace(StringUtils.replace(value, esc, esc + esc), "%", esc + "%"), "_", esc + "_");
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA})
    @NotNull
    public static Field<String> escape(Field<String> field2, char escape) {
        Field<String> replace = field2;
        String esc = "" + escape;
        replace = DSL.replace(replace, DSL.inline(esc), DSL.inline(esc + esc));
        replace = DSL.replace(replace, DSL.inline("%"), DSL.inline(esc + "%"));
        replace = DSL.replace(replace, DSL.inline("_"), DSL.inline(esc + "_"));
        return replace;
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> regexpReplaceAll(Field<String> field2, String pattern, String replacement) {
        return DSL.regexpReplaceAll(field2, Tools.field(pattern), Tools.field(replacement));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> regexpReplaceAll(Field<String> field2, Field<String> pattern, Field<String> replacement) {
        return new RegexpReplace(field2, Tools.nullSafe(pattern), Tools.nullSafe(replacement), true);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> regexpReplaceFirst(Field<String> field2, String pattern, String replacement) {
        return DSL.regexpReplaceFirst(field2, Tools.field(pattern), Tools.field(replacement));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> regexpReplaceFirst(Field<String> field2, Field<String> pattern, Field<String> replacement) {
        return new RegexpReplace(field2, Tools.nullSafe(pattern), Tools.nullSafe(replacement), false);
    }

    @Support
    @NotNull
    public static Field<String> insert(Field<String> in, Number startIndex, Number length, String placing) {
        return DSL.insert(Tools.nullSafe(in), Tools.field(startIndex), Tools.field(length), Tools.field(placing));
    }

    @Support
    @NotNull
    public static Field<String> insert(Field<String> in, Field<? extends Number> startIndex, Field<? extends Number> length, Field<String> placing) {
        return DSL.overlay(in, placing, startIndex, length);
    }

    @Support
    @NotNull
    public static Field<String> concat(Field<String> field2, String value) {
        return DSL.concat(Tools.nullSafe(field2), Tools.field(value));
    }

    @Support
    @NotNull
    public static Field<String> concat(String value, Field<String> field2) {
        return DSL.concat(Tools.field(value), Tools.nullSafe(field2));
    }

    @Support
    @NotNull
    public static Field<String> concat(String ... values2) {
        return DSL.concat(Tools.fieldsArray(values2));
    }

    @Support
    @NotNull
    public static Field<String> concat(Field<?> ... fields) {
        return new Concat(Tools.nullSafe(fields));
    }

    @Support
    @NotNull
    public static Field<java.sql.Date> currentDate() {
        return new CurrentDate<java.sql.Date>(SQLDataType.DATE.notNull());
    }

    @Support
    @NotNull
    public static Field<Time> currentTime() {
        return new CurrentTime<Time>(SQLDataType.TIME.notNull());
    }

    @Support
    @NotNull
    public static Field<Timestamp> currentTimestamp() {
        return new CurrentTimestamp<Timestamp>(SQLDataType.TIMESTAMP.notNull());
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<Timestamp> currentTimestamp(int precision) {
        return DSL.currentTimestamp(Tools.field(precision));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<Timestamp> currentTimestamp(Field<Integer> precision) {
        return new CurrentTimestamp<Timestamp>(SQLDataType.TIMESTAMP.notNull(), precision);
    }

    @Support
    @NotNull
    public static Field<Timestamp> now() {
        return DSL.currentTimestamp();
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<Timestamp> now(Field<Integer> precision) {
        return DSL.currentTimestamp(precision);
    }

    @Support
    @NotNull
    public static Field<LocalDate> currentLocalDate() {
        return new CurrentDate<LocalDate>(SQLDataType.LOCALDATE.notNull());
    }

    @Support
    @NotNull
    public static Field<LocalTime> currentLocalTime() {
        return new CurrentTime<LocalTime>(SQLDataType.LOCALTIME.notNull());
    }

    @Support
    @NotNull
    public static Field<LocalDateTime> currentLocalDateTime() {
        return new CurrentTimestamp<LocalDateTime>(SQLDataType.LOCALDATETIME.notNull());
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDateTime> currentLocalDateTime(int precision) {
        return DSL.currentLocalDateTime(Tools.field(precision));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDateTime> currentLocalDateTime(Field<Integer> precision) {
        return new CurrentTimestamp<LocalDateTime>(SQLDataType.LOCALDATETIME.notNull(), precision);
    }

    @Support
    @NotNull
    public static Field<OffsetTime> currentOffsetTime() {
        return DSL.currentTime().cast(SQLDataType.OFFSETTIME.notNull());
    }

    @Support
    @NotNull
    public static Field<OffsetDateTime> currentOffsetDateTime() {
        return DSL.currentTimestamp().cast(SQLDataType.OFFSETDATETIME.notNull());
    }

    @Support
    @NotNull
    public static Field<Instant> currentInstant() {
        return DSL.currentTimestamp().cast(SQLDataType.INSTANT.notNull());
    }

    @Support
    @NotNull
    public static Field<Integer> dateDiff(java.sql.Date endDate, java.sql.Date startDate) {
        return DSL.dateDiff(Tools.field(endDate), Tools.field(startDate));
    }

    @Support
    @NotNull
    public static Field<Integer> dateDiff(Field<java.sql.Date> endDate, java.sql.Date startDate) {
        return DSL.dateDiff(Tools.nullSafe(endDate), Tools.field(startDate));
    }

    @Support
    @NotNull
    public static Field<Integer> dateDiff(java.sql.Date endDate, Field<java.sql.Date> startDate) {
        return DSL.dateDiff(Tools.field(endDate), Tools.nullSafe(startDate));
    }

    @Support
    @NotNull
    public static Field<Integer> dateDiff(Field<java.sql.Date> endDate, Field<java.sql.Date> startDate) {
        return new DateDiff<java.sql.Date>(null, Tools.nullSafe(startDate), Tools.nullSafe(endDate));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> dateDiff(DatePart part, java.sql.Date startDate, java.sql.Date endDate) {
        return DSL.dateDiff(part, Tools.field(startDate), Tools.field(endDate));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> dateDiff(DatePart part, Field<java.sql.Date> startDate, java.sql.Date endDate) {
        return DSL.dateDiff(part, Tools.nullSafe(startDate), Tools.field(endDate));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> dateDiff(DatePart part, java.sql.Date startDate, Field<java.sql.Date> endDate) {
        return DSL.dateDiff(part, Tools.field(startDate), Tools.nullSafe(endDate));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> dateDiff(DatePart part, Field<java.sql.Date> startDate, Field<java.sql.Date> endDate) {
        return new DateDiff<java.sql.Date>(part, Tools.nullSafe(startDate), Tools.nullSafe(endDate));
    }

    @Support
    @NotNull
    public static Field<java.sql.Date> dateSub(java.sql.Date date, Number interval) {
        return DSL.dateSub(Tools.field(date), Tools.field(interval));
    }

    @Support
    @NotNull
    public static <T> Field<java.sql.Date> dateSub(java.sql.Date date, Field<? extends Number> interval) {
        return DSL.dateSub(Tools.field(date), Tools.nullSafe(interval));
    }

    @Support
    @NotNull
    public static <T> Field<java.sql.Date> dateSub(Field<java.sql.Date> date, Number interval) {
        return DSL.dateSub(Tools.nullSafe(date), Tools.field(interval));
    }

    @Support
    @NotNull
    public static Field<java.sql.Date> dateSub(Field<java.sql.Date> date, Field<? extends Number> interval) {
        return Tools.nullSafe(date).sub(interval);
    }

    @Support
    @NotNull
    public static Field<java.sql.Date> dateSub(java.sql.Date date, Number interval, DatePart datePart) {
        return DSL.dateSub(Tools.field(date), Tools.field(interval), datePart);
    }

    @Support
    @NotNull
    public static Field<java.sql.Date> dateSub(java.sql.Date date, Field<? extends Number> interval, DatePart datePart) {
        return DSL.dateSub(Tools.field(date), Tools.nullSafe(interval), datePart);
    }

    @Support
    @NotNull
    public static Field<java.sql.Date> dateSub(Field<java.sql.Date> date, Number interval, DatePart datePart) {
        return DSL.dateSub(Tools.nullSafe(date), Tools.field(interval), datePart);
    }

    @Support
    @NotNull
    public static Field<java.sql.Date> dateSub(Field<java.sql.Date> date, Field<? extends Number> interval, DatePart datePart) {
        return new DateAdd<java.sql.Date>(Tools.nullSafe(date), Tools.nullSafe(interval).neg(), datePart);
    }

    @Support
    @NotNull
    public static Field<Timestamp> timestampSub(Timestamp timestamp, Number interval) {
        return DSL.timestampSub(Tools.field(timestamp), Tools.field(interval));
    }

    @Support
    @NotNull
    public static Field<Timestamp> timestampSub(Field<Timestamp> timestamp, Field<? extends Number> interval) {
        return Tools.nullSafe(timestamp).sub(interval);
    }

    @Support
    @NotNull
    public static Field<Timestamp> timestampSub(Timestamp date, Number interval, DatePart datePart) {
        return new DateAdd<Timestamp>(Tools.field(date), Tools.field(interval).neg(), datePart);
    }

    @Support
    @NotNull
    public static Field<Timestamp> timestampSub(Timestamp date, Field<? extends Number> interval, DatePart datePart) {
        return new DateAdd<Timestamp>(Tools.field(date), Tools.nullSafe(interval).neg(), datePart);
    }

    @Support
    @NotNull
    public static Field<Timestamp> timestampSub(Field<Timestamp> date, Number interval, DatePart datePart) {
        return new DateAdd<Timestamp>(Tools.nullSafe(date), Tools.field(interval).neg(), datePart);
    }

    @Support
    @NotNull
    public static Field<Timestamp> timestampSub(Field<Timestamp> date, Field<? extends Number> interval, DatePart datePart) {
        return new DateAdd<Timestamp>(Tools.nullSafe(date), Tools.nullSafe(interval).neg(), datePart);
    }

    @Support
    @NotNull
    public static Field<DayToSecond> timestampDiff(Timestamp timestamp1, Timestamp timestamp2) {
        return DSL.timestampDiff(Tools.field(timestamp1), Tools.field(timestamp2));
    }

    @Support
    @NotNull
    public static Field<DayToSecond> timestampDiff(Field<Timestamp> timestamp1, Timestamp timestamp2) {
        return DSL.timestampDiff(Tools.nullSafe(timestamp1), Tools.field(timestamp2));
    }

    @Support
    @NotNull
    public static Field<DayToSecond> timestampDiff(Timestamp timestamp1, Field<Timestamp> timestamp2) {
        return DSL.timestampDiff(Tools.field(timestamp1), Tools.nullSafe(timestamp2));
    }

    @Support
    @NotNull
    public static Field<DayToSecond> timestampDiff(Field<Timestamp> timestamp1, Field<Timestamp> timestamp2) {
        return new TimestampDiff<Timestamp>(Tools.nullSafe(timestamp1), Tools.nullSafe(timestamp2));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> timestampDiff(DatePart part, Timestamp startDate, Timestamp endDate) {
        return DSL.timestampDiff(part, Tools.field(startDate), Tools.field(endDate));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> timestampDiff(DatePart part, Field<Timestamp> startDate, Timestamp endDate) {
        return DSL.timestampDiff(part, Tools.nullSafe(startDate), Tools.field(endDate));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> timestampDiff(DatePart part, Timestamp startDate, Field<Timestamp> endDate) {
        return DSL.timestampDiff(part, Tools.field(startDate), Tools.nullSafe(endDate));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> timestampDiff(DatePart part, Field<Timestamp> startDate, Field<Timestamp> endDate) {
        return new DateDiff<Timestamp>(part, Tools.nullSafe(startDate), Tools.nullSafe(endDate));
    }

    @Support
    @NotNull
    public static Field<Integer> localDateDiff(LocalDate endDate, LocalDate startDate) {
        return DSL.localDateDiff(Tools.field(endDate), Tools.field(startDate));
    }

    @Support
    @NotNull
    public static Field<Integer> localDateDiff(Field<LocalDate> endDate, LocalDate startDate) {
        return DSL.localDateDiff(Tools.nullSafe(endDate), Tools.field(startDate));
    }

    @Support
    @NotNull
    public static Field<Integer> localDateDiff(LocalDate endDate, Field<LocalDate> startDate) {
        return DSL.localDateDiff(Tools.field(endDate), Tools.nullSafe(startDate));
    }

    @Support
    @NotNull
    public static Field<Integer> localDateDiff(Field<LocalDate> endDate, Field<LocalDate> startDate) {
        return new DateDiff<LocalDate>(null, Tools.nullSafe(startDate), Tools.nullSafe(endDate));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> localDateDiff(DatePart part, LocalDate startDate, LocalDate endDate) {
        return DSL.localDateDiff(part, Tools.field(startDate), Tools.field(endDate));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> localDateDiff(DatePart part, Field<LocalDate> startDate, LocalDate endDate) {
        return DSL.localDateDiff(part, Tools.nullSafe(startDate), Tools.field(endDate));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> localDateDiff(DatePart part, LocalDate startDate, Field<LocalDate> endDate) {
        return DSL.localDateDiff(part, Tools.field(startDate), Tools.nullSafe(endDate));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> localDateDiff(DatePart part, Field<LocalDate> startDate, Field<LocalDate> endDate) {
        return new DateDiff<LocalDate>(part, Tools.nullSafe(startDate), Tools.nullSafe(endDate));
    }

    @Support
    @NotNull
    public static Field<LocalDate> localDateSub(LocalDate date, Number interval) {
        return DSL.localDateSub(Tools.field(date), Tools.field(interval));
    }

    @Support
    @NotNull
    public static Field<LocalDate> localDateSub(Field<LocalDate> date, Field<? extends Number> interval) {
        return Tools.nullSafe(date).sub(interval);
    }

    @Support
    @NotNull
    public static Field<LocalDate> localDateSub(LocalDate date, Number interval, DatePart datePart) {
        return DSL.localDateSub(Tools.field(date), Tools.field(interval), datePart);
    }

    @Support
    @NotNull
    public static Field<LocalDate> localDateSub(LocalDate date, Field<? extends Number> interval, DatePart datePart) {
        return DSL.localDateSub(Tools.field(date), Tools.nullSafe(interval), datePart);
    }

    @Support
    @NotNull
    public static Field<LocalDate> localDateSub(Field<LocalDate> date, Number interval, DatePart datePart) {
        return DSL.localDateSub(Tools.nullSafe(date), Tools.field(interval), datePart);
    }

    @Support
    @NotNull
    public static Field<LocalDate> localDateSub(Field<LocalDate> date, Field<? extends Number> interval, DatePart datePart) {
        return new DateAdd<LocalDate>(Tools.nullSafe(date), Tools.nullSafe(interval).neg(), datePart);
    }

    @Support
    @NotNull
    public static Field<LocalDateTime> localDateTimeSub(LocalDateTime timestamp, Number interval) {
        return DSL.localDateTimeSub(Tools.field(timestamp), Tools.field(interval));
    }

    @Support
    @NotNull
    public static Field<LocalDateTime> localDateTimeSub(Field<LocalDateTime> timestamp, Field<? extends Number> interval) {
        return Tools.nullSafe(timestamp).sub(interval);
    }

    @Support
    @NotNull
    public static Field<LocalDateTime> localDateTimeSub(LocalDateTime date, Number interval, DatePart datePart) {
        return new DateAdd<LocalDateTime>(Tools.field(date), Tools.field(interval).neg(), datePart);
    }

    @Support
    @NotNull
    public static Field<LocalDateTime> localDateTimeSub(LocalDateTime date, Field<? extends Number> interval, DatePart datePart) {
        return new DateAdd<LocalDateTime>(Tools.field(date), Tools.nullSafe(interval).neg(), datePart);
    }

    @Support
    @NotNull
    public static Field<LocalDateTime> localDateTimeSub(Field<LocalDateTime> date, Number interval, DatePart datePart) {
        return new DateAdd<LocalDateTime>(Tools.nullSafe(date), Tools.field(interval).neg(), datePart);
    }

    @Support
    @NotNull
    public static Field<LocalDateTime> localDateTimeSub(Field<LocalDateTime> date, Field<? extends Number> interval, DatePart datePart) {
        return new DateAdd<LocalDateTime>(Tools.nullSafe(date), Tools.nullSafe(interval).neg(), datePart);
    }

    @Support
    @NotNull
    public static Field<DayToSecond> localDateTimeDiff(LocalDateTime timestamp1, LocalDateTime timestamp2) {
        return DSL.localDateTimeDiff(Tools.field(timestamp1), Tools.field(timestamp2));
    }

    @Support
    @NotNull
    public static Field<DayToSecond> localDateTimeDiff(Field<LocalDateTime> timestamp1, LocalDateTime timestamp2) {
        return DSL.localDateTimeDiff(Tools.nullSafe(timestamp1), Tools.field(timestamp2));
    }

    @Support
    @NotNull
    public static Field<DayToSecond> localDateTimeDiff(LocalDateTime timestamp1, Field<LocalDateTime> timestamp2) {
        return DSL.localDateTimeDiff(Tools.field(timestamp1), Tools.nullSafe(timestamp2));
    }

    @Support
    @NotNull
    public static Field<DayToSecond> localDateTimeDiff(Field<LocalDateTime> timestamp1, Field<LocalDateTime> timestamp2) {
        return new TimestampDiff<LocalDateTime>(Tools.nullSafe(timestamp1), Tools.nullSafe(timestamp2));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> localDateTimeDiff(DatePart part, LocalDateTime startDate, LocalDateTime endDate) {
        return DSL.localDateTimeDiff(part, Tools.field(startDate), Tools.field(endDate));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> localDateTimeDiff(DatePart part, Field<LocalDateTime> startDate, LocalDateTime endDate) {
        return DSL.localDateTimeDiff(part, Tools.nullSafe(startDate), Tools.field(endDate));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> localDateTimeDiff(DatePart part, LocalDateTime startDate, Field<LocalDateTime> endDate) {
        return DSL.localDateTimeDiff(part, Tools.field(startDate), Tools.nullSafe(endDate));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> localDateTimeDiff(DatePart part, Field<LocalDateTime> startDate, Field<LocalDateTime> endDate) {
        return new DateDiff<LocalDateTime>(part, Tools.nullSafe(startDate), Tools.nullSafe(endDate));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.VERTICA})
    @NotNull
    public static Field<java.sql.Date> trunc(java.sql.Date date) {
        return DSL.trunc(date, DatePart.DAY);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.VERTICA})
    @NotNull
    public static Field<java.sql.Date> trunc(java.sql.Date date, DatePart part) {
        return DSL.trunc(Tools.field(date), part);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDate> trunc(LocalDate date) {
        return DSL.trunc(date, DatePart.DAY);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDate> trunc(LocalDate date, DatePart part) {
        return DSL.trunc(Tools.field(date), part);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.VERTICA})
    @NotNull
    public static Field<Timestamp> trunc(Timestamp timestamp) {
        return DSL.trunc(timestamp, DatePart.DAY);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.VERTICA})
    @NotNull
    public static Field<Timestamp> trunc(Timestamp timestamp, DatePart part) {
        return DSL.trunc(Tools.field(timestamp), part);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDateTime> trunc(LocalDateTime timestamp) {
        return DSL.trunc(timestamp, DatePart.DAY);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDateTime> trunc(LocalDateTime timestamp, DatePart part) {
        return DSL.trunc(Tools.field(timestamp), part);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.VERTICA})
    @NotNull
    public static <T> Field<T> trunc(Field<T> date) {
        return DSL.trunc(date, DatePart.DAY);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.VERTICA})
    @NotNull
    public static <T> Field<T> trunc(Field<T> date, DatePart part) {
        return new TruncDate<T>(date, part);
    }

    @Support
    @NotNull
    public static Field<Integer> extract(Date value, DatePart datePart) {
        return DSL.extract(Tools.field(Convert.convert((Object)value, Timestamp.class)), datePart);
    }

    @Support
    @NotNull
    public static Field<Integer> extract(Temporal value, DatePart datePart) {
        return DSL.extract(Tools.field(value), datePart);
    }

    @Support
    @NotNull
    public static Field<Integer> extract(Field<?> field2, DatePart datePart) {
        return new Extract(Tools.nullSafe(field2), datePart);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE})
    @NotNull
    public static Field<Integer> epoch(Date value) {
        return DSL.extract(value, DatePart.EPOCH);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE})
    @NotNull
    public static Field<Integer> epoch(Temporal value) {
        return DSL.extract(value, DatePart.EPOCH);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE})
    @NotNull
    public static Field<Integer> epoch(Field<?> field2) {
        return DSL.extract(field2, DatePart.EPOCH);
    }

    @Support
    @NotNull
    public static Field<Integer> millennium(Date value) {
        return DSL.extract(value, DatePart.MILLENNIUM);
    }

    @Support
    @NotNull
    public static Field<Integer> millennium(Temporal value) {
        return DSL.extract(value, DatePart.MILLENNIUM);
    }

    @Support
    @NotNull
    public static Field<Integer> millennium(Field<?> field2) {
        return DSL.extract(field2, DatePart.MILLENNIUM);
    }

    @Support
    @NotNull
    public static Field<Integer> century(Date value) {
        return DSL.extract(value, DatePart.CENTURY);
    }

    @Support
    @NotNull
    public static Field<Integer> century(Temporal value) {
        return DSL.extract(value, DatePart.CENTURY);
    }

    @Support
    @NotNull
    public static Field<Integer> century(Field<?> field2) {
        return DSL.extract(field2, DatePart.CENTURY);
    }

    @Support
    @NotNull
    public static Field<Integer> decade(Date value) {
        return DSL.extract(value, DatePart.DECADE);
    }

    @Support
    @NotNull
    public static Field<Integer> decade(Temporal value) {
        return DSL.extract(value, DatePart.DECADE);
    }

    @Support
    @NotNull
    public static Field<Integer> decade(Field<?> field2) {
        return DSL.extract(field2, DatePart.DECADE);
    }

    @Support
    @NotNull
    public static Field<Integer> quarter(Date value) {
        return DSL.extract(value, DatePart.QUARTER);
    }

    @Support
    @NotNull
    public static Field<Integer> quarter(Temporal value) {
        return DSL.extract(value, DatePart.QUARTER);
    }

    @Support
    @NotNull
    public static Field<Integer> quarter(Field<?> field2) {
        return DSL.extract(field2, DatePart.QUARTER);
    }

    @Support
    @NotNull
    public static Field<Integer> year(Date value) {
        return DSL.extract(value, DatePart.YEAR);
    }

    @Support
    @NotNull
    public static Field<Integer> year(Temporal value) {
        return DSL.extract(value, DatePart.YEAR);
    }

    @Support
    @NotNull
    public static Field<Integer> year(Field<?> field2) {
        return DSL.extract(field2, DatePart.YEAR);
    }

    @Support
    @NotNull
    public static Field<Integer> month(Date value) {
        return DSL.extract(value, DatePart.MONTH);
    }

    @Support
    @NotNull
    public static Field<Integer> month(Temporal value) {
        return DSL.extract(value, DatePart.MONTH);
    }

    @Support
    @NotNull
    public static Field<Integer> month(Field<?> field2) {
        return DSL.extract(field2, DatePart.MONTH);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES})
    @NotNull
    public static Field<Integer> week(Date value) {
        return DSL.extract(value, DatePart.WEEK);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES})
    @NotNull
    public static Field<Integer> week(Temporal value) {
        return DSL.extract(value, DatePart.WEEK);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES})
    @NotNull
    public static Field<Integer> week(Field<?> field2) {
        return DSL.extract(field2, DatePart.WEEK);
    }

    @Support
    @NotNull
    public static Field<Integer> day(Date value) {
        return DSL.extract(value, DatePart.DAY);
    }

    @Support
    @NotNull
    public static Field<Integer> day(Temporal value) {
        return DSL.extract(value, DatePart.DAY);
    }

    @Support
    @NotNull
    public static Field<Integer> day(Field<?> field2) {
        return DSL.extract(field2, DatePart.DAY);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE})
    @NotNull
    public static Field<Integer> dayOfWeek(Date value) {
        return DSL.extract(value, DatePart.DAY_OF_WEEK);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE})
    @NotNull
    public static Field<Integer> dayOfWeek(Temporal value) {
        return DSL.extract(value, DatePart.DAY_OF_WEEK);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE})
    @NotNull
    public static Field<Integer> dayOfWeek(Field<?> field2) {
        return DSL.extract(field2, DatePart.DAY_OF_WEEK);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE})
    @NotNull
    public static Field<Integer> isoDayOfWeek(Date value) {
        return DSL.extract(value, DatePart.ISO_DAY_OF_WEEK);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE})
    @NotNull
    public static Field<Integer> isoDayOfWeek(Temporal value) {
        return DSL.extract(value, DatePart.ISO_DAY_OF_WEEK);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE})
    @NotNull
    public static Field<Integer> isoDayOfWeek(Field<?> field2) {
        return DSL.extract(field2, DatePart.ISO_DAY_OF_WEEK);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE})
    @NotNull
    public static Field<Integer> dayOfYear(Date value) {
        return DSL.extract(value, DatePart.DAY_OF_YEAR);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE})
    @NotNull
    public static Field<Integer> dayOfYear(Temporal value) {
        return DSL.extract(value, DatePart.DAY_OF_YEAR);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE})
    @NotNull
    public static Field<Integer> dayOfYear(Field<?> field2) {
        return DSL.extract(field2, DatePart.DAY_OF_YEAR);
    }

    @Support
    @NotNull
    public static Field<Integer> hour(Date value) {
        return DSL.extract(value, DatePart.HOUR);
    }

    @Support
    @NotNull
    public static Field<Integer> hour(Temporal value) {
        return DSL.extract(value, DatePart.HOUR);
    }

    @Support
    @NotNull
    public static Field<Integer> hour(Field<?> field2) {
        return DSL.extract(field2, DatePart.HOUR);
    }

    @Support
    @NotNull
    public static Field<Integer> minute(Date value) {
        return DSL.extract(value, DatePart.MINUTE);
    }

    @Support
    @NotNull
    public static Field<Integer> minute(Temporal value) {
        return DSL.extract(value, DatePart.MINUTE);
    }

    @Support
    @NotNull
    public static Field<Integer> minute(Field<?> field2) {
        return DSL.extract(field2, DatePart.MINUTE);
    }

    @Support
    @NotNull
    public static Field<Integer> second(Date value) {
        return DSL.extract(value, DatePart.SECOND);
    }

    @Support
    @NotNull
    public static Field<Integer> second(Temporal value) {
        return DSL.extract(value, DatePart.SECOND);
    }

    @Support
    @NotNull
    public static Field<Integer> second(Field<?> field2) {
        return DSL.extract(field2, DatePart.SECOND);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> millisecond(Date value) {
        return DSL.extract(value, DatePart.MILLISECOND);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> millisecond(Temporal value) {
        return DSL.extract(value, DatePart.MILLISECOND);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> millisecond(Field<?> field2) {
        return DSL.extract(field2, DatePart.MILLISECOND);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> microsecond(Date value) {
        return DSL.extract(value, DatePart.MICROSECOND);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> microsecond(Temporal value) {
        return DSL.extract(value, DatePart.MICROSECOND);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> microsecond(Field<?> field2) {
        return DSL.extract(field2, DatePart.MICROSECOND);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
    @NotNull
    public static Field<Integer> timezone(Date value) {
        return DSL.extract(value, DatePart.TIMEZONE);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
    @NotNull
    public static Field<Integer> timezone(Temporal value) {
        return DSL.extract(value, DatePart.TIMEZONE);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
    @NotNull
    public static Field<Integer> timezone(Field<?> field2) {
        return DSL.extract(field2, DatePart.TIMEZONE);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
    @NotNull
    public static Field<Integer> timezoneHour(Date value) {
        return DSL.extract(value, DatePart.TIMEZONE_HOUR);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
    @NotNull
    public static Field<Integer> timezoneHour(Temporal value) {
        return DSL.extract(value, DatePart.TIMEZONE_HOUR);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
    @NotNull
    public static Field<Integer> timezoneHour(Field<?> field2) {
        return DSL.extract(field2, DatePart.TIMEZONE_HOUR);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
    @NotNull
    public static Field<Integer> timezoneMinute(Date value) {
        return DSL.extract(value, DatePart.TIMEZONE_MINUTE);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
    @NotNull
    public static Field<Integer> timezoneMinute(Temporal value) {
        return DSL.extract(value, DatePart.TIMEZONE_MINUTE);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
    @NotNull
    public static Field<Integer> timezoneMinute(Field<?> field2) {
        return DSL.extract(field2, DatePart.TIMEZONE_MINUTE);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<java.sql.Date> date(String value) {
        return Tools.field(Convert.convert((Object)value, java.sql.Date.class));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<java.sql.Date> date(Date value) {
        return Tools.field(Convert.convert((Object)value, java.sql.Date.class));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<java.sql.Date> date(Field<? extends Date> field2) {
        return new DateOrTime<java.sql.Date>(field2, SQLDataType.DATE);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<Time> time(String value) {
        return Tools.field(Convert.convert((Object)value, Time.class));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<Time> time(Date value) {
        return Tools.field(Convert.convert((Object)value, Time.class));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<Time> time(Field<? extends Date> field2) {
        return new DateOrTime<Time>(field2, SQLDataType.TIME);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<Timestamp> timestamp(String value) {
        return Tools.field(Convert.convert((Object)value, Timestamp.class));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<Timestamp> timestamp(Date value) {
        return Tools.field(Convert.convert((Object)value, Timestamp.class));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<Timestamp> timestamp(Field<? extends Date> field2) {
        return new DateOrTime<Timestamp>(field2, SQLDataType.TIMESTAMP);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDate> localDate(String value) {
        return Tools.field(Convert.convert((Object)value, LocalDate.class));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDate> localDate(LocalDate value) {
        return Tools.field(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDate> localDate(Field<LocalDate> field2) {
        return new DateOrTime<LocalDate>(field2, SQLDataType.LOCALDATE);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalTime> localTime(String value) {
        return Tools.field(Convert.convert((Object)value, LocalTime.class));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalTime> localTime(LocalTime value) {
        return Tools.field(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalTime> localTime(Field<LocalTime> field2) {
        return new DateOrTime<LocalTime>(field2, SQLDataType.LOCALTIME);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDateTime> localDateTime(String value) {
        return Tools.field(Convert.convert((Object)value, LocalDateTime.class));
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDateTime> localDateTime(LocalDateTime value) {
        return Tools.field(value);
    }

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDateTime> localDateTime(Field<LocalDateTime> field2) {
        return new DateOrTime<LocalDateTime>(field2, SQLDataType.LOCALDATETIME);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE})
    @NotNull
    public static Field<OffsetTime> offsetTime(String value) {
        return Tools.field(Convert.convert((Object)value, OffsetTime.class));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE})
    @NotNull
    public static Field<OffsetTime> offsetTime(OffsetTime value) {
        return Tools.field(value);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE})
    @NotNull
    public static Field<OffsetTime> offsetTime(Field<OffsetTime> field2) {
        return new DateOrTime<OffsetTime>(field2, SQLDataType.OFFSETTIME);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE})
    @NotNull
    public static Field<OffsetDateTime> offsetDateTime(String value) {
        return Tools.field(Convert.convert((Object)value, OffsetDateTime.class));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE})
    @NotNull
    public static Field<OffsetDateTime> offsetDateTime(OffsetDateTime value) {
        return Tools.field(value);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE})
    @NotNull
    public static Field<OffsetDateTime> offsetDateTime(Field<OffsetDateTime> field2) {
        return new DateOrTime<OffsetDateTime>(field2, SQLDataType.OFFSETDATETIME);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE})
    @NotNull
    public static Field<Instant> instant(String value) {
        return Tools.field(Convert.convert((Object)value, Instant.class));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE})
    @NotNull
    public static Field<Instant> instant(Instant value) {
        return Tools.field(value);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE})
    @NotNull
    public static Field<Instant> instant(Field<Instant> field2) {
        return new DateOrTime<Instant>(field2, SQLDataType.INSTANT);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDate> toLocalDate(String value, String format) {
        return DSL.toDate(value, format).coerce(SQLDataType.LOCALDATE);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDate> toLocalDate(String value, Field<String> format) {
        return DSL.toDate(value, format).coerce(SQLDataType.LOCALDATE);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDate> toLocalDate(Field<String> value, String format) {
        return DSL.toDate(value, format).coerce(SQLDataType.LOCALDATE);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDate> toLocalDate(Field<String> value, Field<String> format) {
        return DSL.toDate(value, format).coerce(SQLDataType.LOCALDATE);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDateTime> toLocalDateTime(String value, String format) {
        return DSL.toTimestamp(value, format).coerce(SQLDataType.LOCALDATETIME);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDateTime> toLocalDateTime(String value, Field<String> format) {
        return DSL.toTimestamp(value, format).coerce(SQLDataType.LOCALDATETIME);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDateTime> toLocalDateTime(Field<String> value, String format) {
        return DSL.toTimestamp(value, format).coerce(SQLDataType.LOCALDATETIME);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.VERTICA})
    @NotNull
    public static Field<LocalDateTime> toLocalDateTime(Field<String> value, Field<String> format) {
        return DSL.toTimestamp(value, format).coerce(SQLDataType.LOCALDATETIME);
    }

    @Support
    @NotNull
    public static GroupField emptyGroupingSet() {
        return EmptyGroupingSet.INSTANCE;
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static GroupField rollup(Field<?> ... fields) {
        return DSL.rollup((FieldOrRow[])Tools.nullSafe(fields));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static GroupField rollup(FieldOrRow ... fields) {
        return new Rollup(fields);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static GroupField cube(Field<?> ... fields) {
        return DSL.cube((FieldOrRow[])Tools.nullSafe(fields));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static GroupField cube(FieldOrRow ... fields) {
        return new Cube(fields);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static GroupField groupingSets(Field<?> ... fields) {
        List[] array = Tools.map(fields, f -> Arrays.asList(f), List[]::new);
        return DSL.groupingSets(array);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static GroupField groupingSets(Field<?>[] ... fieldSets) {
        List[] array = Tools.map(fieldSets, f -> Arrays.asList(f), List[]::new);
        return DSL.groupingSets(array);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @SafeVarargs
    @NotNull
    public static GroupField groupingSets(Collection<? extends Field<?>> ... fieldSets) {
        return new GroupingSets(fieldSets);
    }

    @Support(value={SQLDialect.ORACLE, SQLDialect.VERTICA})
    @NotNull
    @Pro
    public static Field<Integer> groupId() {
        return DSL.function("group_id", Integer.class, new Field[0]);
    }

    @Support(value={SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.VERTICA})
    @NotNull
    @Pro
    public static Field<Integer> groupingId(Field<?> ... fields) {
        return DSL.function("grouping_id", Integer.class, fields);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static Field<Integer> grouping(Field<?> field2) {
        return DSL.function("grouping", Integer.class, field2);
    }

    @Support
    @SafeVarargs
    @NotNull
    public static <T> Field<T> greatest(T value, T ... values2) {
        return DSL.greatest(Tools.field(value), Tools.fieldsArray(values2));
    }

    @Support
    @SafeVarargs
    @NotNull
    public static <T> Field<T> greatest(Field<T> field2, Field<?> ... others) {
        return new Greatest(Tools.nullSafe(Tools.combine(field2, others)));
    }

    @Support
    @SafeVarargs
    @NotNull
    public static <T> Field<T> least(T value, T ... values2) {
        return DSL.least(Tools.field(value), Tools.fieldsArray(values2));
    }

    @Support
    @SafeVarargs
    @NotNull
    public static <T> Field<T> least(Field<T> field2, Field<?> ... others) {
        return new Least(Tools.nullSafe(Tools.combine(field2, others)));
    }

    @Support
    @NotNull
    public static <T extends Number> Field<T> neg(Field<T> field2) {
        return field2.neg();
    }

    @Support
    @NotNull
    public static <T extends Number> Field<T> minus(Field<T> field2) {
        return field2.neg();
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    public static <T extends Number> Field<T> trunc(T number) {
        return DSL.trunc(Tools.field(number), DSL.inline(0));
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlparseDocument(String content) {
        return DSL.xmlparseDocument(Tools.field(content));
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlparseDocument(Field<String> content) {
        return new XMLParse(content, QOM.DocumentOrContent.DOCUMENT);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlparseContent(String content) {
        return DSL.xmlparseContent(Tools.field(content));
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlparseContent(Field<String> content) {
        return new XMLParse(content, QOM.DocumentOrContent.CONTENT);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlelement(String name, Field<?> ... content) {
        return DSL.xmlelement(DSL.name(name), (XMLAttributes)null, Arrays.asList(content));
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlelement(String name, Collection<? extends Field<?>> content) {
        return DSL.xmlelement(DSL.name(name), (XMLAttributes)null, content);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlelement(Name name, Field<?> ... content) {
        return DSL.xmlelement(name, (XMLAttributes)null, Arrays.asList(content));
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlelement(Name name, Collection<? extends Field<?>> content) {
        return DSL.xmlelement(name, (XMLAttributes)null, content);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlelement(String name, XMLAttributes attributes, Field<?> ... content) {
        return DSL.xmlelement(DSL.name(name), attributes, Arrays.asList(content));
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlelement(String name, XMLAttributes attributes, Collection<? extends Field<?>> content) {
        return DSL.xmlelement(DSL.name(name), attributes, content);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlelement(Name name, XMLAttributes attributes, Field<?> ... content) {
        return DSL.xmlelement(name, attributes, Arrays.asList(content));
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static Field<XML> xmlelement(Name name, XMLAttributes attributes, Collection<? extends Field<?>> content) {
        return new XMLElement(name, attributes, content);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static XMLAttributes xmlattributes(Field<?> ... attributes) {
        return DSL.xmlattributes(Arrays.asList(attributes));
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static XMLAttributes xmlattributes(Collection<? extends Field<?>> attributes) {
        return new XMLAttributesImpl(attributes);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.TERADATA})
    @NotNull
    public static XMLAggOrderByStep<XML> xmlagg(Field<XML> field2) {
        return new XMLAgg(field2);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static XMLQueryPassingStep xmlquery(String xpath) {
        return DSL.xmlquery(Tools.field(xpath));
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static XMLQueryPassingStep xmlquery(Field<String> xpath) {
        return new XMLQuery(xpath);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static XMLExistsPassingStep xmlexists(String xpath) {
        return DSL.xmlexists(Tools.field(xpath));
    }

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static XMLExistsPassingStep xmlexists(Field<String> xpath) {
        return new XMLExists(xpath);
    }

    @Support(value={SQLDialect.DB2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES_10, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static XMLTablePassingStep xmltable(String xpath) {
        return DSL.xmltable(Tools.field(xpath));
    }

    @Support(value={SQLDialect.DB2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES_10, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    public static XMLTablePassingStep xmltable(Field<String> xpath) {
        return new XMLTable(xpath);
    }

    @Support(value={SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONValueOnStep<JSON> jsonValue(Field<JSON> json, String path) {
        return DSL.jsonValue(json, Tools.field(path));
    }

    @Support(value={SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONValueOnStep<JSON> jsonValue(Field<JSON> json, Field<String> path) {
        return new JSONValue<JSON>(SQLDataType.JSON, json, path, null);
    }

    @Support(value={SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONValueOnStep<JSONB> jsonbValue(Field<JSONB> json, String path) {
        return DSL.jsonbValue(json, Tools.field(path));
    }

    @Support(value={SQLDialect.DB2_11, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONValueOnStep<JSONB> jsonbValue(Field<JSONB> json, Field<String> path) {
        return new JSONValue<JSONB>(SQLDataType.JSONB, json, path, null);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONEntryValueStep key(String key) {
        return DSL.key(Tools.field(key));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONEntryValueStep key(Field<String> key) {
        return new JSONEntryImpl(key);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> JSONEntry<T> jsonEntry(Field<T> value) {
        return DSL.jsonEntry(value.getName(), value);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> JSONEntry<T> jsonEntry(String key, T value) {
        return DSL.jsonEntry(Tools.field(key), Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> JSONEntry<T> jsonEntry(String key, Field<T> value) {
        return DSL.jsonEntry(Tools.field(key), value);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> JSONEntry<T> jsonEntry(String key, Select<? extends Record1<T>> value) {
        return DSL.jsonEntry(Tools.field(key), DSL.field(value));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> JSONEntry<T> jsonEntry(Field<String> key, T value) {
        return DSL.jsonEntry(key, Tools.field(value));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> JSONEntry<T> jsonEntry(Field<String> key, Field<T> value) {
        return new JSONEntryImpl<T>(key, value);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> JSONEntry<T> jsonEntry(Field<String> key, Select<? extends Record1<T>> value) {
        return DSL.jsonEntry(key, DSL.field(value));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectNullStep<JSON> jsonObject(String key, Field<?> value) {
        return DSL.jsonObject(DSL.jsonEntry(key, value));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectNullStep<JSON> jsonObject(Field<String> key, Field<?> value) {
        return DSL.jsonObject(DSL.jsonEntry(key, value));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectNullStep<JSON> jsonObject(Field<String> key, Select<? extends Record1<?>> value) {
        return DSL.jsonObject(DSL.jsonEntry(key, value));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @Deprecated(forRemoval=true, since="3.19")
    @NotNull
    public static JSONObjectNullStep<JSON> jsonObject(Field<?> ... entries) {
        return new JSONObject<JSON>(SQLDataType.JSON, Tools.jsonEntries(entries));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectNullStep<JSONB> jsonbObject(String key, Field<?> value) {
        return DSL.jsonbObject(DSL.jsonEntry(key, value));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectNullStep<JSONB> jsonbObject(Field<String> key, Field<?> value) {
        return DSL.jsonbObject(DSL.jsonEntry(key, value));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectNullStep<JSONB> jsonbObject(Field<String> key, Select<? extends Record1<?>> value) {
        return DSL.jsonbObject(DSL.jsonEntry(key, value));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @Deprecated(forRemoval=true, since="3.19")
    @NotNull
    public static JSONObjectNullStep<JSONB> jsonbObject(Field<?> ... entries) {
        return new JSONObject<JSONB>(SQLDataType.JSONB, Tools.jsonEntries(entries));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectNullStep<JSON> jsonObject() {
        return DSL.jsonObject(Tools.EMPTY_JSONENTRY);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectNullStep<JSONB> jsonbObject() {
        return DSL.jsonbObject(Tools.EMPTY_JSONENTRY);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONArrayAggOrderByStep<JSON> jsonArrayAgg(Field<?> value) {
        return new JSONArrayAgg<JSON>(SQLDataType.JSON, value, false);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONArrayAggOrderByStep<JSONB> jsonbArrayAgg(Field<?> value) {
        return new JSONArrayAgg<JSONB>(SQLDataType.JSONB, value, false);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE})
    @NotNull
    public static JSONArrayAggOrderByStep<JSON> jsonArrayAggDistinct(Field<?> value) {
        return new JSONArrayAgg<JSON>(SQLDataType.JSON, value, true);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONArrayAggOrderByStep<JSONB> jsonbArrayAggDistinct(Field<?> value) {
        return new JSONArrayAgg<JSONB>(SQLDataType.JSONB, value, true);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectAggNullStep<JSON> jsonObjectAgg(Field<?> value) {
        return DSL.jsonObjectAgg(DSL.jsonEntry(value));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectAggNullStep<JSON> jsonObjectAgg(String key, Field<?> value) {
        return DSL.jsonObjectAgg(Tools.field(key), value);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectAggNullStep<JSON> jsonObjectAgg(Field<String> key, Field<?> value) {
        return DSL.jsonObjectAgg(DSL.jsonEntry(key, value));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectAggNullStep<JSON> jsonObjectAgg(JSONEntry<?> entry) {
        return new JSONObjectAgg<JSON>(SQLDataType.JSON, entry);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectAggNullStep<JSONB> jsonbObjectAgg(Field<?> field2) {
        return DSL.jsonbObjectAgg(DSL.jsonEntry(field2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectAggNullStep<JSONB> jsonbObjectAgg(String key, Field<?> value) {
        return DSL.jsonbObjectAgg(Tools.field(key), value);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectAggNullStep<JSONB> jsonbObjectAgg(Field<String> key, Field<?> value) {
        return DSL.jsonbObjectAgg(DSL.jsonEntry(key, value));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONObjectAggNullStep<JSONB> jsonbObjectAgg(JSONEntry<?> entry) {
        return new JSONObjectAgg<JSONB>(SQLDataType.JSONB, entry);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLITE})
    @NotNull
    public static JSONExistsOnStep jsonExists(Field<JSON> field2, String path) {
        return DSL.jsonExists(field2, Tools.field(path));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONExistsOnStep jsonExists(Field<JSON> field2, Field<String> path) {
        return new JSONExists(field2, Tools.nullSafe(path));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONExistsOnStep jsonbExists(Field<JSONB> field2, String path) {
        return DSL.jsonbExists(field2, Tools.field(path));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.MARIADB_10_5, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONExistsOnStep jsonbExists(Field<JSONB> field2, Field<String> path) {
        return new JSONExists(field2, Tools.nullSafe(path));
    }

    @Support(value={SQLDialect.DB2_11, SQLDialect.HANA, SQLDialect.MARIADB_10_6, SQLDialect.MYSQL_8_0_19, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONTableColumnsFirstStep jsonTable(JSON json, String path) {
        return DSL.jsonTable(Tools.field(json), Tools.field(path));
    }

    @Support(value={SQLDialect.DB2_11, SQLDialect.HANA, SQLDialect.MARIADB_10_6, SQLDialect.MYSQL_8_0_19, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONTableColumnsFirstStep jsonTable(Field<JSON> json, Field<String> path) {
        return new JSONTable(Tools.nullSafe(json), Tools.nullSafe(path));
    }

    @Support(value={SQLDialect.DB2_11, SQLDialect.HANA, SQLDialect.MARIADB_10_6, SQLDialect.MYSQL_8_0_19, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONTableColumnsFirstStep jsonbTable(JSONB json, String path) {
        return DSL.jsonbTable(Tools.field(json), Tools.field(path));
    }

    @Support(value={SQLDialect.DB2_11, SQLDialect.HANA, SQLDialect.MARIADB_10_6, SQLDialect.MYSQL_8_0_19, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLSERVER2016, SQLDialect.YUGABYTEDB})
    @NotNull
    public static JSONTableColumnsFirstStep jsonbTable(Field<JSONB> json, Field<String> path) {
        return new JSONTable(Tools.nullSafe(json), Tools.nullSafe(path));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> array(T ... values2) {
        return DSL.array(Tools.fields(values2));
    }

    @SafeVarargs
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> array(Field<T> ... fields) {
        return DSL.array(Arrays.asList(fields));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> array(Collection<? extends Field<T>> fields) {
        return new Array(fields);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> array(Select<? extends Record1<T>> select) {
        return new ArrayQuery(select);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <R extends Record> Field<Result<R>> multiset(TableLike<R> table) {
        return new Multiset<R>(table);
    }

    @Support
    @NotNull
    public static AggregateFunction<Integer> count() {
        return DSL.count((Field)DefaultAggregateFunction.ASTERISK.get());
    }

    @Support
    @NotNull
    public static AggregateFunction<Integer> count(SelectFieldOrAsterisk field2) {
        return new DefaultAggregateFunction<Integer>(Names.N_COUNT, SQLDataType.INTEGER, DSL.field("{0}", field2));
    }

    @Support
    @NotNull
    public static AggregateFunction<Integer> count(Table<?> table) {
        return new CountTable(table, false);
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static AggregateFunction<Integer> countDistinct(SelectFieldOrAsterisk field2) {
        return new DefaultAggregateFunction<Integer>(true, Names.N_COUNT, SQLDataType.INTEGER, DSL.field("{0}", field2));
    }

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static AggregateFunction<Integer> countDistinct(Table<?> table) {
        return new CountTable(table, true);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE})
    @NotNull
    public static AggregateFunction<Integer> countDistinct(Field<?> ... fields) {
        return (fields = Tools.nullSafe(fields)).length == 0 ? DSL.countDistinct(DSL.asterisk()) : new DefaultAggregateFunction<Integer>(true, Names.N_COUNT, SQLDataType.INTEGER, fields);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> ArrayAggOrderByStep<T[]> arrayAgg(Field<T> field2) {
        return new ArrayAgg<T>(false, Tools.nullSafe(field2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> ArrayAggOrderByStep<T[]> arrayAggDistinct(Field<T> field2) {
        return new ArrayAgg<T>(true, Tools.nullSafe(field2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static ArrayAggOrderByStep<Result<Record>> multisetAgg(Collection<? extends Field<?>> fields) {
        return new MultisetAgg<Record>(false, DSL.row(fields));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static ArrayAggOrderByStep<Result<Record>> multisetAgg(Field<?> ... fields) {
        return new MultisetAgg<Record>(false, DSL.row(fields));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1> ArrayAggOrderByStep<Result<Record1<T1>>> multisetAgg(SelectField<T1> field1) {
        return new MultisetAgg<Record1<T1>>(false, DSL.row(field1));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2> ArrayAggOrderByStep<Result<Record2<T1, T2>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2) {
        return new MultisetAgg<Record2<T1, T2>>(false, DSL.row(field1, field2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3> ArrayAggOrderByStep<Result<Record3<T1, T2, T3>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3) {
        return new MultisetAgg<Record3<T1, T2, T3>>(false, DSL.row(field1, field2, field3));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4> ArrayAggOrderByStep<Result<Record4<T1, T2, T3, T4>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4) {
        return new MultisetAgg<Record4<T1, T2, T3, T4>>(false, DSL.row(field1, field2, field3, field4));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5> ArrayAggOrderByStep<Result<Record5<T1, T2, T3, T4, T5>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5) {
        return new MultisetAgg<Record5<T1, T2, T3, T4, T5>>(false, DSL.row(field1, field2, field3, field4, field5));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6> ArrayAggOrderByStep<Result<Record6<T1, T2, T3, T4, T5, T6>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6) {
        return new MultisetAgg<Record6<T1, T2, T3, T4, T5, T6>>(false, DSL.row(field1, field2, field3, field4, field5, field6));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7> ArrayAggOrderByStep<Result<Record7<T1, T2, T3, T4, T5, T6, T7>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7) {
        return new MultisetAgg<Record7<T1, T2, T3, T4, T5, T6, T7>>(false, DSL.row(field1, field2, field3, field4, field5, field6, field7));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8> ArrayAggOrderByStep<Result<Record8<T1, T2, T3, T4, T5, T6, T7, T8>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8) {
        return new MultisetAgg<Record8<T1, T2, T3, T4, T5, T6, T7, T8>>(false, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> ArrayAggOrderByStep<Result<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9) {
        return new MultisetAgg<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>(false, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ArrayAggOrderByStep<Result<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10) {
        return new MultisetAgg<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>(false, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ArrayAggOrderByStep<Result<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11) {
        return new MultisetAgg<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>(false, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ArrayAggOrderByStep<Result<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12) {
        return new MultisetAgg<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>(false, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> ArrayAggOrderByStep<Result<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13) {
        return new MultisetAgg<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>(false, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> ArrayAggOrderByStep<Result<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14) {
        return new MultisetAgg<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>(false, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> ArrayAggOrderByStep<Result<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15) {
        return new MultisetAgg<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>(false, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> ArrayAggOrderByStep<Result<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16) {
        return new MultisetAgg<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>(false, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> ArrayAggOrderByStep<Result<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17) {
        return new MultisetAgg<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>>(false, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> ArrayAggOrderByStep<Result<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18) {
        return new MultisetAgg<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>>(false, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> ArrayAggOrderByStep<Result<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19) {
        return new MultisetAgg<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>>(false, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> ArrayAggOrderByStep<Result<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20) {
        return new MultisetAgg<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>>(false, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> ArrayAggOrderByStep<Result<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21) {
        return new MultisetAgg<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>>(false, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> ArrayAggOrderByStep<Result<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>>> multisetAgg(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21, SelectField<T22> field22) {
        return new MultisetAgg<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>>(false, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static ArrayAggOrderByStep<Result<Record>> multisetAggDistinct(Collection<? extends Field<?>> fields) {
        return new MultisetAgg<Record>(true, DSL.row(fields));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static ArrayAggOrderByStep<Result<Record>> multisetAggDistinct(Field<?> ... fields) {
        return new MultisetAgg<Record>(true, DSL.row(fields));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1> ArrayAggOrderByStep<Result<Record1<T1>>> multisetAggDistinct(SelectField<T1> field1) {
        return new MultisetAgg<Record1<T1>>(true, DSL.row(field1));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2> ArrayAggOrderByStep<Result<Record2<T1, T2>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2) {
        return new MultisetAgg<Record2<T1, T2>>(true, DSL.row(field1, field2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3> ArrayAggOrderByStep<Result<Record3<T1, T2, T3>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3) {
        return new MultisetAgg<Record3<T1, T2, T3>>(true, DSL.row(field1, field2, field3));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4> ArrayAggOrderByStep<Result<Record4<T1, T2, T3, T4>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4) {
        return new MultisetAgg<Record4<T1, T2, T3, T4>>(true, DSL.row(field1, field2, field3, field4));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5> ArrayAggOrderByStep<Result<Record5<T1, T2, T3, T4, T5>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5) {
        return new MultisetAgg<Record5<T1, T2, T3, T4, T5>>(true, DSL.row(field1, field2, field3, field4, field5));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6> ArrayAggOrderByStep<Result<Record6<T1, T2, T3, T4, T5, T6>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6) {
        return new MultisetAgg<Record6<T1, T2, T3, T4, T5, T6>>(true, DSL.row(field1, field2, field3, field4, field5, field6));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7> ArrayAggOrderByStep<Result<Record7<T1, T2, T3, T4, T5, T6, T7>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7) {
        return new MultisetAgg<Record7<T1, T2, T3, T4, T5, T6, T7>>(true, DSL.row(field1, field2, field3, field4, field5, field6, field7));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8> ArrayAggOrderByStep<Result<Record8<T1, T2, T3, T4, T5, T6, T7, T8>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8) {
        return new MultisetAgg<Record8<T1, T2, T3, T4, T5, T6, T7, T8>>(true, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> ArrayAggOrderByStep<Result<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9) {
        return new MultisetAgg<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>(true, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ArrayAggOrderByStep<Result<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10) {
        return new MultisetAgg<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>(true, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ArrayAggOrderByStep<Result<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11) {
        return new MultisetAgg<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>(true, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ArrayAggOrderByStep<Result<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12) {
        return new MultisetAgg<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>(true, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> ArrayAggOrderByStep<Result<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13) {
        return new MultisetAgg<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>(true, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> ArrayAggOrderByStep<Result<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14) {
        return new MultisetAgg<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>(true, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> ArrayAggOrderByStep<Result<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15) {
        return new MultisetAgg<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>(true, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> ArrayAggOrderByStep<Result<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16) {
        return new MultisetAgg<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>(true, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> ArrayAggOrderByStep<Result<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17) {
        return new MultisetAgg<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>>(true, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> ArrayAggOrderByStep<Result<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18) {
        return new MultisetAgg<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>>(true, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> ArrayAggOrderByStep<Result<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19) {
        return new MultisetAgg<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>>(true, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> ArrayAggOrderByStep<Result<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20) {
        return new MultisetAgg<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>>(true, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> ArrayAggOrderByStep<Result<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21) {
        return new MultisetAgg<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>>(true, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.H2, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> ArrayAggOrderByStep<Result<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>>> multisetAggDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21, SelectField<T22> field22) {
        return new MultisetAgg<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>>(true, DSL.row(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22));
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public static <T, A extends ArrayRecord<T>> ArrayAggOrderByStep<A> collect(Field<T> field2, Class<A> type) {
        return DSL.collect(Tools.nullSafe(field2), DSL.getDataType(type));
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public static <T, A extends ArrayRecord<T>> ArrayAggOrderByStep<A> collect(Field<T> field2, DataType<A> type) {
        return new Collect<T, A>(field2, type, false);
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public static <T, A extends ArrayRecord<T>> ArrayAggOrderByStep<A> collectDistinct(Field<T> field2, Class<A> type) {
        return DSL.collectDistinct(Tools.nullSafe(field2), DSL.getDataType(type));
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public static <T, A extends ArrayRecord<T>> ArrayAggOrderByStep<A> collectDistinct(Field<T> field2, DataType<A> type) {
        return new Collect<T, A>(field2, type, true);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_4, SQLDialect.SNOWFLAKE})
    @NotNull
    public static <T> AggregateFunction<T> mode(Field<T> field2) {
        return new Mode<T>(Tools.nullSafe(field2));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE11G, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLSERVER2017, SQLDialect.SYBASE, SQLDialect.TERADATA})
    @NotNull
    public static OrderedAggregateFunction<String> listAgg(Field<?> field2) {
        return new ListAgg(false, Tools.nullSafe(field2));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE11G, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLSERVER2017, SQLDialect.SYBASE, SQLDialect.TERADATA})
    @NotNull
    public static OrderedAggregateFunction<String> listAgg(Field<?> field2, String separator) {
        return new ListAgg(false, Tools.nullSafe(field2), DSL.inline(separator));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2_11, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SYBASE})
    @NotNull
    public static OrderedAggregateFunction<String> listAggDistinct(Field<?> field2) {
        return new ListAgg(true, Tools.nullSafe(field2));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2_11, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SYBASE})
    @NotNull
    public static OrderedAggregateFunction<String> listAggDistinct(Field<?> field2, String separator) {
        return new ListAgg(true, Tools.nullSafe(field2), DSL.inline(separator));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLITE, SQLDialect.SQLSERVER2017, SQLDialect.SYBASE, SQLDialect.TERADATA})
    @NotNull
    public static GroupConcatOrderByStep groupConcat(Field<?> field2) {
        return new GroupConcat(Tools.nullSafe(field2));
    }

    @Deprecated(forRemoval=true, since="3.12")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLITE, SQLDialect.SQLSERVER2017, SQLDialect.SYBASE, SQLDialect.TERADATA})
    @NotNull
    public static AggregateFunction<String> groupConcat(Field<?> field2, String separator) {
        return new GroupConcat(Tools.nullSafe(field2)).separator(separator);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2_11, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SYBASE})
    @NotNull
    public static GroupConcatOrderByStep groupConcatDistinct(Field<?> field2) {
        return new GroupConcat(Tools.nullSafe(field2), true);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.POSTGRES_9_4, SQLDialect.YUGABYTEDB})
    @NotNull
    public static OrderedAggregateFunctionOfDeferredType mode() {
        return new ModeDeferred();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_4, SQLDialect.YUGABYTEDB})
    @NotNull
    public static OrderedAggregateFunction<Integer> rank(Field<?> ... fields) {
        return new DefaultAggregateFunction<Integer>(Names.N_RANK, SQLDataType.INTEGER, fields);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_4, SQLDialect.YUGABYTEDB})
    @NotNull
    public static OrderedAggregateFunction<Integer> rank(Collection<? extends Field<?>> fields) {
        return new DefaultAggregateFunction<Integer>(Names.N_RANK, SQLDataType.INTEGER, fields.toArray(Tools.EMPTY_FIELD));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_4, SQLDialect.YUGABYTEDB})
    @NotNull
    public static OrderedAggregateFunction<Integer> denseRank(Field<?> ... fields) {
        return new DefaultAggregateFunction<Integer>(Names.N_DENSE_RANK, SQLDataType.INTEGER, fields);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_4, SQLDialect.YUGABYTEDB})
    @NotNull
    public static OrderedAggregateFunction<Integer> denseRank(Collection<? extends Field<?>> fields) {
        return new DefaultAggregateFunction<Integer>(Names.N_DENSE_RANK, SQLDataType.INTEGER, fields.toArray(Tools.EMPTY_FIELD));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_4, SQLDialect.YUGABYTEDB})
    @NotNull
    public static OrderedAggregateFunction<Integer> percentRank(Field<?> ... fields) {
        return new DefaultAggregateFunction<Integer>(Names.N_PERCENT_RANK, SQLDataType.INTEGER, fields);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_4, SQLDialect.YUGABYTEDB})
    @NotNull
    public static OrderedAggregateFunction<Integer> percentRank(Collection<? extends Field<?>> fields) {
        return new DefaultAggregateFunction<Integer>(Names.N_PERCENT_RANK, SQLDataType.INTEGER, fields.toArray(Tools.EMPTY_FIELD));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_4, SQLDialect.YUGABYTEDB})
    @NotNull
    public static OrderedAggregateFunction<BigDecimal> cumeDist(Field<?> ... fields) {
        return new DefaultAggregateFunction<BigDecimal>(Names.N_CUME_DIST, SQLDataType.NUMERIC, fields);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_4, SQLDialect.YUGABYTEDB})
    @NotNull
    public static OrderedAggregateFunction<BigDecimal> cumeDist(Collection<? extends Field<?>> fields) {
        return new DefaultAggregateFunction<BigDecimal>(Names.N_CUME_DIST, SQLDataType.NUMERIC, fields.toArray(Tools.EMPTY_FIELD));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2_11, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.MARIADB_10_3, SQLDialect.MEMSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_4, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2012, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static OrderedAggregateFunction<BigDecimal> percentileCont(Number number) {
        return DSL.percentileCont(DSL.val(number));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2_11, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.MARIADB_10_3, SQLDialect.MEMSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_4, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2012, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static OrderedAggregateFunction<BigDecimal> percentileCont(Field<? extends Number> field2) {
        return new DefaultAggregateFunction<BigDecimal>(Names.N_PERCENTILE_CONT, SQLDataType.NUMERIC, Tools.nullSafe(field2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2_11, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.MARIADB_10_3, SQLDialect.MEMSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_4, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2012, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static OrderedAggregateFunction<BigDecimal> percentileDisc(Number number) {
        return DSL.percentileDisc(DSL.val(number));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2_11, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.MARIADB_10_3, SQLDialect.MEMSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_4, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2012, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static OrderedAggregateFunction<BigDecimal> percentileDisc(Field<? extends Number> field2) {
        return new DefaultAggregateFunction<BigDecimal>(Names.N_PERCENTILE_DISC, SQLDataType.NUMERIC, Tools.nullSafe(field2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationOrderByStep partitionBy(GroupField ... fields) {
        return new WindowSpecificationImpl().partitionBy(fields);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationOrderByStep partitionBy(Collection<? extends GroupField> fields) {
        return new WindowSpecificationImpl().partitionBy((Collection)fields);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationRowsStep orderBy(Field<?> ... fields) {
        return new WindowSpecificationImpl().orderBy(fields);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationRowsStep orderBy(OrderField<?> ... fields) {
        return new WindowSpecificationImpl().orderBy((OrderField[])fields);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationRowsStep orderBy(Collection<? extends OrderField<?>> fields) {
        return new WindowSpecificationImpl().orderBy((Collection)fields);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationExcludeStep rowsUnboundedPreceding() {
        return new WindowSpecificationImpl().rowsUnboundedPreceding();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationExcludeStep rowsPreceding(int number) {
        return new WindowSpecificationImpl().rowsPreceding(number);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationExcludeStep rowsCurrentRow() {
        return new WindowSpecificationImpl().rowsCurrentRow();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationExcludeStep rowsUnboundedFollowing() {
        return new WindowSpecificationImpl().rowsUnboundedFollowing();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationExcludeStep rowsFollowing(int number) {
        return new WindowSpecificationImpl().rowsFollowing(number);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationRowsAndStep rowsBetweenUnboundedPreceding() {
        return new WindowSpecificationImpl().rowsBetweenUnboundedPreceding();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationRowsAndStep rowsBetweenPreceding(int number) {
        return new WindowSpecificationImpl().rowsBetweenPreceding(number);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationRowsAndStep rowsBetweenCurrentRow() {
        return new WindowSpecificationImpl().rowsBetweenCurrentRow();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationRowsAndStep rowsBetweenUnboundedFollowing() {
        return new WindowSpecificationImpl().rowsBetweenUnboundedFollowing();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationRowsAndStep rowsBetweenFollowing(int number) {
        return new WindowSpecificationImpl().rowsBetweenFollowing(number);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationExcludeStep rangeUnboundedPreceding() {
        return new WindowSpecificationImpl().rangeUnboundedPreceding();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationExcludeStep rangePreceding(int number) {
        return new WindowSpecificationImpl().rangePreceding(number);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationExcludeStep rangeCurrentRow() {
        return new WindowSpecificationImpl().rangeCurrentRow();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationExcludeStep rangeUnboundedFollowing() {
        return new WindowSpecificationImpl().rangeUnboundedFollowing();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationExcludeStep rangeFollowing(int number) {
        return new WindowSpecificationImpl().rangeFollowing(number);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationRowsAndStep rangeBetweenUnboundedPreceding() {
        return new WindowSpecificationImpl().rangeBetweenUnboundedPreceding();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationRowsAndStep rangeBetweenPreceding(int number) {
        return new WindowSpecificationImpl().rangeBetweenPreceding(number);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationRowsAndStep rangeBetweenCurrentRow() {
        return new WindowSpecificationImpl().rangeBetweenCurrentRow();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationRowsAndStep rangeBetweenUnboundedFollowing() {
        return new WindowSpecificationImpl().rangeBetweenUnboundedFollowing();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationRowsAndStep rangeBetweenFollowing(int number) {
        return new WindowSpecificationImpl().rangeBetweenFollowing(number);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationExcludeStep groupsUnboundedPreceding() {
        return new WindowSpecificationImpl().groupsUnboundedPreceding();
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationExcludeStep groupsPreceding(int number) {
        return new WindowSpecificationImpl().groupsPreceding(number);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationExcludeStep groupsCurrentRow() {
        return new WindowSpecificationImpl().groupsCurrentRow();
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationExcludeStep groupsUnboundedFollowing() {
        return new WindowSpecificationImpl().groupsUnboundedFollowing();
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationExcludeStep groupsFollowing(int number) {
        return new WindowSpecificationImpl().groupsFollowing(number);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationRowsAndStep groupsBetweenUnboundedPreceding() {
        return new WindowSpecificationImpl().groupsBetweenUnboundedPreceding();
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationRowsAndStep groupsBetweenPreceding(int number) {
        return new WindowSpecificationImpl().groupsBetweenPreceding(number);
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationRowsAndStep groupsBetweenCurrentRow() {
        return new WindowSpecificationImpl().groupsBetweenCurrentRow();
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationRowsAndStep groupsBetweenUnboundedFollowing() {
        return new WindowSpecificationImpl().groupsBetweenUnboundedFollowing();
    }

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.POSTGRES_11, SQLDialect.SQLITE_3_28, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowSpecificationRowsAndStep groupsBetweenFollowing(int number) {
        return new WindowSpecificationImpl().groupsBetweenFollowing(number);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowOverStep<Integer> rowNumber() {
        return new RowNumber();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowOverStep<Integer> rank() {
        return new Rank();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowOverStep<Integer> denseRank() {
        return new DenseRank();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowOverStep<BigDecimal> percentRank() {
        return new PercentRank();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowOverStep<BigDecimal> cumeDist() {
        return new CumeDist();
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2_11, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowOverStep<Integer> ntile(int number) {
        return new Ntile(DSL.inline(number));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2_11, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowOverStep<Integer> ntile(Field<Integer> number) {
        return new Ntile(number);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2_11, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowOverStep<BigDecimal> ratioToReport(Number number) {
        return DSL.ratioToReport(Tools.field(number));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2_11, SQLDialect.EXASOL, SQLDialect.FIREBIRD_4_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static WindowOverStep<BigDecimal> ratioToReport(Field<? extends Number> field2) {
        return new RatioToReport(Tools.nullSafe(field2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> WindowIgnoreNullsStep<T> firstValue(Field<T> field2) {
        return new FirstValue<T>(Tools.nullSafe(field2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> WindowIgnoreNullsStep<T> lastValue(Field<T> field2) {
        return new LastValue<T>(Tools.nullSafe(field2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> WindowFromFirstLastStep<T> nthValue(Field<T> field2, int nth) {
        return DSL.nthValue(field2, DSL.val(nth));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2_11, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> WindowFromFirstLastStep<T> nthValue(Field<T> field2, Field<Integer> nth) {
        return new NthValue<T>(Tools.nullSafe(field2), Tools.nullSafe(nth));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> WindowIgnoreNullsStep<T> lead(Field<T> field2) {
        return new Lead<T>(Tools.nullSafe(field2), null, null);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> WindowIgnoreNullsStep<T> lead(Field<T> field2, int offset) {
        return DSL.lead(field2, DSL.inline(offset));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> WindowIgnoreNullsStep<T> lead(Field<T> field2, Field<Integer> offset) {
        return new Lead<T>(Tools.nullSafe(field2), Tools.nullSafe(offset), null);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> WindowIgnoreNullsStep<T> lead(Field<T> field2, int offset, T defaultValue) {
        return DSL.lead(Tools.nullSafe(field2), DSL.inline(offset), Tools.field(defaultValue, field2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> WindowIgnoreNullsStep<T> lead(Field<T> field2, int offset, Field<T> defaultValue) {
        return DSL.lead(field2, DSL.inline(offset), defaultValue);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> WindowIgnoreNullsStep<T> lead(Field<T> field2, Field<Integer> offset, T defaultValue) {
        return DSL.lead(field2, Tools.nullSafe(offset), Tools.field(defaultValue, field2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> WindowIgnoreNullsStep<T> lead(Field<T> field2, Field<Integer> offset, Field<T> defaultValue) {
        return new Lead<T>(Tools.nullSafe(field2), Tools.nullSafe(offset), Tools.nullSafe(defaultValue));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> WindowIgnoreNullsStep<T> lag(Field<T> field2) {
        return new Lag<T>(Tools.nullSafe(field2), null, null);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> WindowIgnoreNullsStep<T> lag(Field<T> field2, int offset) {
        return DSL.lag(field2, DSL.inline(offset));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> WindowIgnoreNullsStep<T> lag(Field<T> field2, Field<Integer> offset) {
        return new Lag<T>(Tools.nullSafe(field2), Tools.nullSafe(offset), null);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> WindowIgnoreNullsStep<T> lag(Field<T> field2, int offset, T defaultValue) {
        return DSL.lag(Tools.nullSafe(field2), DSL.inline(offset), Tools.field(defaultValue, field2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> WindowIgnoreNullsStep<T> lag(Field<T> field2, int offset, Field<T> defaultValue) {
        return DSL.lag(field2, DSL.inline(offset), defaultValue);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> WindowIgnoreNullsStep<T> lag(Field<T> field2, Field<Integer> offset, T defaultValue) {
        return DSL.lag(field2, offset, Tools.field(defaultValue, field2));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2012, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> WindowIgnoreNullsStep<T> lag(Field<T> field2, Field<Integer> offset, Field<T> defaultValue) {
        return new Lag<T>(Tools.nullSafe(field2), Tools.nullSafe(offset), Tools.nullSafe(defaultValue));
    }

    @Support
    @NotNull
    public static <T> Param<Object> param() {
        return DSL.param(Object.class);
    }

    @Support
    @NotNull
    public static <T> Param<T> param(Class<T> type) {
        return DSL.param(DefaultDataType.getDataType(null, type));
    }

    @Support
    @NotNull
    public static <T> Param<T> param(DataType<T> type) {
        return new Val<Object>(null, type, false);
    }

    @Support
    @NotNull
    public static <T> Param<T> param(Field<T> field2) {
        return DSL.param(field2.getDataType());
    }

    @Support
    @NotNull
    public static Param<Object> param(String name) {
        return DSL.param(name, Object.class);
    }

    @Support
    @NotNull
    public static <T> Param<T> param(String name, Class<T> type) {
        return DSL.param(name, DefaultDataType.getDataType(null, type));
    }

    @Support
    @NotNull
    public static <T> Param<T> param(String name, DataType<T> type) {
        return new Val<Object>(null, type, false, name);
    }

    @Support
    @NotNull
    public static <T> Param<T> param(String name, Field<T> type) {
        return DSL.param(name, type.getDataType());
    }

    @Support
    @NotNull
    public static <T> Param<T> param(String name, T value) {
        return new Val<T>(value, DSL.val(value).getDataType(), false, name);
    }

    @Support
    @NotNull
    public static <T> Param<T> value(T value) {
        return DSL.val(value);
    }

    @Support
    @NotNull
    public static Param<Byte> value(byte value) {
        return DSL.value((Object)value, SQLDataType.TINYINT);
    }

    @Support
    @NotNull
    public static Param<Byte> value(Byte value) {
        return DSL.value((Object)value, SQLDataType.TINYINT);
    }

    @Support
    @NotNull
    public static Param<UByte> value(UByte value) {
        return DSL.value((Object)value, SQLDataType.TINYINTUNSIGNED);
    }

    @Support
    @NotNull
    public static Param<Short> value(short value) {
        return DSL.value((Object)value, SQLDataType.SMALLINT);
    }

    @Support
    @NotNull
    public static Param<Short> value(Short value) {
        return DSL.value((Object)value, SQLDataType.SMALLINT);
    }

    @Support
    @NotNull
    public static Param<UShort> value(UShort value) {
        return DSL.value((Object)value, SQLDataType.SMALLINTUNSIGNED);
    }

    @Support
    @NotNull
    public static Param<Integer> value(int value) {
        return DSL.value((Object)value, SQLDataType.INTEGER);
    }

    @Support
    @NotNull
    public static Param<Integer> value(Integer value) {
        return DSL.value((Object)value, SQLDataType.INTEGER);
    }

    @Support
    @NotNull
    public static Param<UInteger> value(UInteger value) {
        return DSL.value((Object)value, SQLDataType.INTEGERUNSIGNED);
    }

    @Support
    @NotNull
    public static Param<Long> value(long value) {
        return DSL.value((Object)value, SQLDataType.BIGINT);
    }

    @Support
    @NotNull
    public static Param<Long> value(Long value) {
        return DSL.value((Object)value, SQLDataType.BIGINT);
    }

    @Support
    @NotNull
    public static Param<ULong> value(ULong value) {
        return DSL.value((Object)value, SQLDataType.BIGINTUNSIGNED);
    }

    @Support
    @NotNull
    public static Param<Float> value(float value) {
        return DSL.value((Object)Float.valueOf(value), SQLDataType.REAL);
    }

    @Support
    @NotNull
    public static Param<Float> value(Float value) {
        return DSL.value((Object)value, SQLDataType.REAL);
    }

    @Support
    @NotNull
    public static Param<Double> value(double value) {
        return DSL.value((Object)value, SQLDataType.DOUBLE);
    }

    @Support
    @NotNull
    public static Param<Double> value(Double value) {
        return DSL.value((Object)value, SQLDataType.DOUBLE);
    }

    @Support
    @NotNull
    public static Param<Boolean> value(boolean value) {
        return DSL.value((Object)value, SQLDataType.BOOLEAN);
    }

    @Support
    @NotNull
    public static Param<Boolean> value(Boolean value) {
        return DSL.value((Object)value, SQLDataType.BOOLEAN);
    }

    @Support
    @NotNull
    public static Param<BigDecimal> value(BigDecimal value) {
        return DSL.value((Object)value, SQLDataType.DECIMAL);
    }

    @Support
    @NotNull
    public static Param<BigInteger> value(BigInteger value) {
        return DSL.value((Object)value, SQLDataType.DECIMAL_INTEGER);
    }

    @Support
    @NotNull
    public static Param<byte[]> value(byte[] value) {
        return DSL.value((Object)value, SQLDataType.VARBINARY);
    }

    @Support
    @NotNull
    public static Param<String> value(String value) {
        return DSL.value((Object)value, SQLDataType.VARCHAR);
    }

    @Support
    @NotNull
    public static Param<java.sql.Date> value(java.sql.Date value) {
        return DSL.value((Object)value, SQLDataType.DATE);
    }

    @Support
    @NotNull
    public static Param<Time> value(Time value) {
        return DSL.value((Object)value, SQLDataType.TIME);
    }

    @Support
    @NotNull
    public static Param<Timestamp> value(Timestamp value) {
        return DSL.value((Object)value, SQLDataType.TIMESTAMP);
    }

    @Support
    @NotNull
    public static Param<LocalDate> value(LocalDate value) {
        return DSL.value((Object)value, SQLDataType.LOCALDATE);
    }

    @Support
    @NotNull
    public static Param<LocalTime> value(LocalTime value) {
        return DSL.value((Object)value, SQLDataType.LOCALTIME);
    }

    @Support
    @NotNull
    public static Param<LocalDateTime> value(LocalDateTime value) {
        return DSL.value((Object)value, SQLDataType.LOCALDATETIME);
    }

    @Support
    @NotNull
    public static Param<OffsetTime> value(OffsetTime value) {
        return DSL.value((Object)value, SQLDataType.OFFSETTIME);
    }

    @Support
    @NotNull
    public static Param<OffsetDateTime> value(OffsetDateTime value) {
        return DSL.value((Object)value, SQLDataType.OFFSETDATETIME);
    }

    @Support
    @NotNull
    public static Param<Instant> value(Instant value) {
        return DSL.value((Object)value, SQLDataType.INSTANT);
    }

    @Support
    @NotNull
    public static Param<UUID> value(UUID value) {
        return DSL.value((Object)value, SQLDataType.UUID);
    }

    @Support
    @NotNull
    public static Param<JSON> value(JSON value) {
        return DSL.value((Object)value, SQLDataType.JSON);
    }

    @Support
    @NotNull
    public static Param<JSONB> value(JSONB value) {
        return DSL.value((Object)value, SQLDataType.JSONB);
    }

    @Support
    @NotNull
    public static Param<XML> value(XML value) {
        return DSL.value((Object)value, SQLDataType.XML);
    }

    @Support
    @NotNull
    public static Param<Geography> value(Geography value) {
        return DSL.value((Object)value, SQLDataType.GEOGRAPHY);
    }

    @Support
    @NotNull
    public static Param<Geometry> value(Geometry value) {
        return DSL.value((Object)value, SQLDataType.GEOMETRY);
    }

    @Support
    @NotNull
    public static <T> Param<T> value(Object value, Class<T> type) {
        return DSL.val(value, type);
    }

    @Support
    @NotNull
    public static <T> Param<T> value(Object value, Field<T> field2) {
        return DSL.val(value, field2);
    }

    @Support
    @NotNull
    public static <T> Param<T> value(Object value, DataType<T> type) {
        return DSL.val(value, type);
    }

    @Support
    @NotNull
    public static <T> Field<T> inlined(Field<T> field2) {
        return CustomField.of(field2.getQualifiedName(), field2.getDataType(), c -> c.visit(field2, ParamType.INLINED));
    }

    @Support
    @NotNull
    public static Condition inlined(Condition condition) {
        return CustomCondition.of(c -> c.visit(condition, ParamType.INLINED));
    }

    @Support
    @NotNull
    public static QueryPart inlined(QueryPart part) {
        return CustomQueryPart.of(c -> c.visit(part, ParamType.INLINED));
    }

    @Support
    @NotNull
    public static Statement inlined(Statement statement) {
        return CustomStatement.of(c -> c.visit(statement, ParamType.INLINED));
    }

    @Support
    @NotNull
    public static <T> Param<T> inline(T value) {
        AbstractParamX val = (AbstractParamX)DSL.val(value);
        val.setInline0(true);
        return val;
    }

    @Support
    @NotNull
    public static Param<Byte> inline(byte value) {
        return DSL.inline((Object)value, SQLDataType.TINYINT);
    }

    @Support
    @NotNull
    public static Param<Byte> inline(Byte value) {
        return DSL.inline((Object)value, SQLDataType.TINYINT);
    }

    @Support
    @NotNull
    public static Param<UByte> inline(UByte value) {
        return DSL.inline((Object)value, SQLDataType.TINYINTUNSIGNED);
    }

    @Support
    @NotNull
    public static Param<Short> inline(short value) {
        return DSL.inline((Object)value, SQLDataType.SMALLINT);
    }

    @Support
    @NotNull
    public static Param<Short> inline(Short value) {
        return DSL.inline((Object)value, SQLDataType.SMALLINT);
    }

    @Support
    @NotNull
    public static Param<UShort> inline(UShort value) {
        return DSL.inline((Object)value, SQLDataType.SMALLINTUNSIGNED);
    }

    @Support
    @NotNull
    public static Param<Integer> inline(int value) {
        return DSL.inline((Object)value, SQLDataType.INTEGER);
    }

    @Support
    @NotNull
    public static Param<Integer> inline(Integer value) {
        return DSL.inline((Object)value, SQLDataType.INTEGER);
    }

    @Support
    @NotNull
    public static Param<UInteger> inline(UInteger value) {
        return DSL.inline((Object)value, SQLDataType.INTEGERUNSIGNED);
    }

    @Support
    @NotNull
    public static Param<Long> inline(long value) {
        return DSL.inline((Object)value, SQLDataType.BIGINT);
    }

    @Support
    @NotNull
    public static Param<Long> inline(Long value) {
        return DSL.inline((Object)value, SQLDataType.BIGINT);
    }

    @Support
    @NotNull
    public static Param<ULong> inline(ULong value) {
        return DSL.inline((Object)value, SQLDataType.BIGINTUNSIGNED);
    }

    @Support
    @NotNull
    public static Param<Float> inline(float value) {
        return DSL.inline((Object)Float.valueOf(value), SQLDataType.REAL);
    }

    @Support
    @NotNull
    public static Param<Float> inline(Float value) {
        return DSL.inline((Object)value, SQLDataType.REAL);
    }

    @Support
    @NotNull
    public static Param<Double> inline(double value) {
        return DSL.inline((Object)value, SQLDataType.DOUBLE);
    }

    @Support
    @NotNull
    public static Param<Double> inline(Double value) {
        return DSL.inline((Object)value, SQLDataType.DOUBLE);
    }

    @Support
    @NotNull
    public static Param<Boolean> inline(boolean value) {
        return DSL.inline((Object)value, SQLDataType.BOOLEAN);
    }

    @Support
    @NotNull
    public static Param<Boolean> inline(Boolean value) {
        return DSL.inline((Object)value, SQLDataType.BOOLEAN);
    }

    @Support
    @NotNull
    public static Param<BigDecimal> inline(BigDecimal value) {
        return DSL.inline((Object)value, SQLDataType.DECIMAL);
    }

    @Support
    @NotNull
    public static Param<BigInteger> inline(BigInteger value) {
        return DSL.inline((Object)value, SQLDataType.DECIMAL_INTEGER);
    }

    @Support
    @NotNull
    public static Param<byte[]> inline(byte[] value) {
        return DSL.inline((Object)value, SQLDataType.VARBINARY);
    }

    @Support
    @NotNull
    public static Param<String> inline(String value) {
        return DSL.inline((Object)value, SQLDataType.VARCHAR);
    }

    @Support
    @NotNull
    public static Param<java.sql.Date> inline(java.sql.Date value) {
        return DSL.inline((Object)value, SQLDataType.DATE);
    }

    @Support
    @NotNull
    public static Param<Time> inline(Time value) {
        return DSL.inline((Object)value, SQLDataType.TIME);
    }

    @Support
    @NotNull
    public static Param<Timestamp> inline(Timestamp value) {
        return DSL.inline((Object)value, SQLDataType.TIMESTAMP);
    }

    @Support
    @NotNull
    public static Param<LocalDate> inline(LocalDate value) {
        return DSL.inline((Object)value, SQLDataType.LOCALDATE);
    }

    @Support
    @NotNull
    public static Param<LocalTime> inline(LocalTime value) {
        return DSL.inline((Object)value, SQLDataType.LOCALTIME);
    }

    @Support
    @NotNull
    public static Param<LocalDateTime> inline(LocalDateTime value) {
        return DSL.inline((Object)value, SQLDataType.LOCALDATETIME);
    }

    @Support
    @NotNull
    public static Param<OffsetTime> inline(OffsetTime value) {
        return DSL.inline((Object)value, SQLDataType.OFFSETTIME);
    }

    @Support
    @NotNull
    public static Param<OffsetDateTime> inline(OffsetDateTime value) {
        return DSL.inline((Object)value, SQLDataType.OFFSETDATETIME);
    }

    @Support
    @NotNull
    public static Param<Instant> inline(Instant value) {
        return DSL.inline((Object)value, SQLDataType.INSTANT);
    }

    @Support
    @NotNull
    public static Param<UUID> inline(UUID value) {
        return DSL.inline((Object)value, SQLDataType.UUID);
    }

    @Support
    @NotNull
    public static Param<JSON> inline(JSON value) {
        return DSL.inline((Object)value, SQLDataType.JSON);
    }

    @Support
    @NotNull
    public static Param<JSONB> inline(JSONB value) {
        return DSL.inline((Object)value, SQLDataType.JSONB);
    }

    @Support
    @NotNull
    public static Param<XML> inline(XML value) {
        return DSL.inline((Object)value, SQLDataType.XML);
    }

    @Support
    @NotNull
    public static Param<Geography> inline(Geography value) {
        return DSL.inline((Object)value, SQLDataType.GEOGRAPHY);
    }

    @Support
    @NotNull
    public static Param<Geometry> inline(Geometry value) {
        return DSL.inline((Object)value, SQLDataType.GEOMETRY);
    }

    @Support
    @NotNull
    public static Param<String> inline(char character) {
        return DSL.inline("" + character);
    }

    @Support
    @NotNull
    public static Param<String> inline(Character character) {
        return DSL.inline(character == null ? null : "" + character);
    }

    @Support
    @NotNull
    public static Param<String> inline(CharSequence character) {
        return DSL.inline(character == null ? null : String.valueOf(character));
    }

    @Support
    @NotNull
    public static <T> Param<T> inline(Object value, Class<T> type) {
        AbstractParamX val = (AbstractParamX)DSL.val(value, type);
        val.setInline0(true);
        return val;
    }

    @Support
    @NotNull
    public static <T> Param<T> inline(Object value, Field<T> field2) {
        AbstractParamX val = (AbstractParamX)DSL.val(value, field2);
        val.setInline0(true);
        return val;
    }

    @Support
    @NotNull
    public static <T> Param<T> inline(Object value, DataType<T> type) {
        AbstractParamX val = (AbstractParamX)DSL.val(value, type);
        val.setInline0(true);
        return val;
    }

    @Support
    @NotNull
    public static <T> Param<T> val(T value) {
        return DSL.val0(value, false);
    }

    static <T> Param<T> val0(T value, boolean inferredDataType) {
        Class type = value == null ? Object.class : value.getClass();
        return DSL.val0(value, DSL.getDataType0(type), inferredDataType);
    }

    @Support
    @NotNull
    public static Param<Byte> val(byte value) {
        return DSL.val((Object)value, SQLDataType.TINYINT);
    }

    @Support
    @NotNull
    public static Param<Byte> val(Byte value) {
        return DSL.val((Object)value, SQLDataType.TINYINT);
    }

    @Support
    @NotNull
    public static Param<UByte> val(UByte value) {
        return DSL.val((Object)value, SQLDataType.TINYINTUNSIGNED);
    }

    @Support
    @NotNull
    public static Param<Short> val(short value) {
        return DSL.val((Object)value, SQLDataType.SMALLINT);
    }

    @Support
    @NotNull
    public static Param<Short> val(Short value) {
        return DSL.val((Object)value, SQLDataType.SMALLINT);
    }

    @Support
    @NotNull
    public static Param<UShort> val(UShort value) {
        return DSL.val((Object)value, SQLDataType.SMALLINTUNSIGNED);
    }

    @Support
    @NotNull
    public static Param<Integer> val(int value) {
        return DSL.val((Object)value, SQLDataType.INTEGER);
    }

    @Support
    @NotNull
    public static Param<Integer> val(Integer value) {
        return DSL.val((Object)value, SQLDataType.INTEGER);
    }

    @Support
    @NotNull
    public static Param<UInteger> val(UInteger value) {
        return DSL.val((Object)value, SQLDataType.INTEGERUNSIGNED);
    }

    @Support
    @NotNull
    public static Param<Long> val(long value) {
        return DSL.val((Object)value, SQLDataType.BIGINT);
    }

    @Support
    @NotNull
    public static Param<Long> val(Long value) {
        return DSL.val((Object)value, SQLDataType.BIGINT);
    }

    @Support
    @NotNull
    public static Param<ULong> val(ULong value) {
        return DSL.val((Object)value, SQLDataType.BIGINTUNSIGNED);
    }

    @Support
    @NotNull
    public static Param<Float> val(float value) {
        return DSL.val((Object)Float.valueOf(value), SQLDataType.REAL);
    }

    @Support
    @NotNull
    public static Param<Float> val(Float value) {
        return DSL.val((Object)value, SQLDataType.REAL);
    }

    @Support
    @NotNull
    public static Param<Double> val(double value) {
        return DSL.val((Object)value, SQLDataType.DOUBLE);
    }

    @Support
    @NotNull
    public static Param<Double> val(Double value) {
        return DSL.val((Object)value, SQLDataType.DOUBLE);
    }

    @Support
    @NotNull
    public static Param<Boolean> val(boolean value) {
        return DSL.val((Object)value, SQLDataType.BOOLEAN);
    }

    @Support
    @NotNull
    public static Param<Boolean> val(Boolean value) {
        return DSL.val((Object)value, SQLDataType.BOOLEAN);
    }

    @Support
    @NotNull
    public static Param<BigDecimal> val(BigDecimal value) {
        return DSL.val((Object)value, SQLDataType.DECIMAL);
    }

    @Support
    @NotNull
    public static Param<BigInteger> val(BigInteger value) {
        return DSL.val((Object)value, SQLDataType.DECIMAL_INTEGER);
    }

    @Support
    @NotNull
    public static Param<byte[]> val(byte[] value) {
        return DSL.val((Object)value, SQLDataType.VARBINARY);
    }

    @Support
    @NotNull
    public static Param<String> val(String value) {
        return DSL.val((Object)value, SQLDataType.VARCHAR);
    }

    @Support
    @NotNull
    public static Param<java.sql.Date> val(java.sql.Date value) {
        return DSL.val((Object)value, SQLDataType.DATE);
    }

    @Support
    @NotNull
    public static Param<Time> val(Time value) {
        return DSL.val((Object)value, SQLDataType.TIME);
    }

    @Support
    @NotNull
    public static Param<Timestamp> val(Timestamp value) {
        return DSL.val((Object)value, SQLDataType.TIMESTAMP);
    }

    @Support
    @NotNull
    public static Param<LocalDate> val(LocalDate value) {
        return DSL.val((Object)value, SQLDataType.LOCALDATE);
    }

    @Support
    @NotNull
    public static Param<LocalTime> val(LocalTime value) {
        return DSL.val((Object)value, SQLDataType.LOCALTIME);
    }

    @Support
    @NotNull
    public static Param<LocalDateTime> val(LocalDateTime value) {
        return DSL.val((Object)value, SQLDataType.LOCALDATETIME);
    }

    @Support
    @NotNull
    public static Param<OffsetTime> val(OffsetTime value) {
        return DSL.val((Object)value, SQLDataType.OFFSETTIME);
    }

    @Support
    @NotNull
    public static Param<OffsetDateTime> val(OffsetDateTime value) {
        return DSL.val((Object)value, SQLDataType.OFFSETDATETIME);
    }

    @Support
    @NotNull
    public static Param<Instant> val(Instant value) {
        return DSL.val((Object)value, SQLDataType.INSTANT);
    }

    @Support
    @NotNull
    public static Param<UUID> val(UUID value) {
        return DSL.val((Object)value, SQLDataType.UUID);
    }

    @Support
    @NotNull
    public static Param<JSON> val(JSON value) {
        return DSL.val((Object)value, SQLDataType.JSON);
    }

    @Support
    @NotNull
    public static Param<JSONB> val(JSONB value) {
        return DSL.val((Object)value, SQLDataType.JSONB);
    }

    @Support
    @NotNull
    public static Param<XML> val(XML value) {
        return DSL.val((Object)value, SQLDataType.XML);
    }

    @Support
    @NotNull
    public static Param<Geography> val(Geography value) {
        return DSL.val((Object)value, SQLDataType.GEOGRAPHY);
    }

    @Support
    @NotNull
    public static Param<Geometry> val(Geometry value) {
        return DSL.val((Object)value, SQLDataType.GEOMETRY);
    }

    @Support
    @NotNull
    public static <T> Param<T> val(Object value, Class<T> type) {
        return DSL.val(value, DSL.getDataType(type));
    }

    @Support
    @NotNull
    public static <T> Param<T> val(Object value, Field<T> field2) {
        return DSL.val(value, Tools.nullSafeDataType(field2));
    }

    @Support
    @NotNull
    public static <T> Param<T> val(Object value, DataType<T> type) {
        return DSL.val0(value, type, false);
    }

    private static <T> Param<T> val0(Object value, DataType<T> type, boolean inferredDataType) {
        if (value instanceof QualifiedRecord) {
            QualifiedRecord r = (QualifiedRecord)value;
            return new QualifiedRecordConstant<QualifiedRecord>(r, r.getQualifier());
        }
        if (value == null && QualifiedRecord.class.isAssignableFrom(type.getType())) {
            return new QualifiedRecordConstant<Object>(null, Tools.getRecordQualifier(type));
        }
        if (value instanceof ArrayRecord) {
            ArrayRecord a = (ArrayRecord)value;
            return new ArrayConstant<ArrayRecord>(a);
        }
        if (value instanceof AssociativeArrayRecord) {
            AssociativeArrayRecord a = (AssociativeArrayRecord)value;
            return new AssociativeArrayConstant<AssociativeArrayRecord>(a, a.getArrayType());
        }
        if (value == null && AssociativeArrayRecord.class.isAssignableFrom(type.getType())) {
            return new AssociativeArrayConstant<Object>(null, (DataType<Object>)type);
        }
        if (value instanceof Val) {
            Val p = (Val)value;
            return p.convertTo(type);
        }
        T converted = type.convert(value);
        return new Val<T>(converted, DSL.mostSpecific(converted, type), inferredDataType);
    }

    private static <T> DataType<T> mostSpecific(T value, DataType<T> dataType) {
        Class<T> coercionType;
        Class<?> valueType;
        if (value != null && !(dataType instanceof ConvertedDataType) && (valueType = value.getClass()) != (coercionType = dataType.getType()) && coercionType.isAssignableFrom(valueType)) {
            return DefaultDataType.getDataType(null, valueType, dataType);
        }
        return dataType;
    }

    @NotNull
    public static <T1> RecordType<Record> recordType(Field<?>[] fields) {
        return new FieldsImpl<Record>(fields);
    }

    @NotNull
    public static <T1> RecordType<Record> recordType(Collection<? extends Field<?>> fields) {
        return new FieldsImpl<Record>(fields);
    }

    @NotNull
    public static <T1> RecordType<Record1<T1>> recordType(Field<T1> field1) {
        return new FieldsImpl<Record1<T1>>(field1);
    }

    @NotNull
    public static <T1, T2> RecordType<Record2<T1, T2>> recordType(Field<T1> field1, Field<T2> field2) {
        return new FieldsImpl<Record2<T1, T2>>(field1, field2);
    }

    @NotNull
    public static <T1, T2, T3> RecordType<Record3<T1, T2, T3>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
        return new FieldsImpl<Record3<T1, T2, T3>>(field1, field2, field3);
    }

    @NotNull
    public static <T1, T2, T3, T4> RecordType<Record4<T1, T2, T3, T4>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
        return new FieldsImpl<Record4<T1, T2, T3, T4>>(field1, field2, field3, field4);
    }

    @NotNull
    public static <T1, T2, T3, T4, T5> RecordType<Record5<T1, T2, T3, T4, T5>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
        return new FieldsImpl<Record5<T1, T2, T3, T4, T5>>(field1, field2, field3, field4, field5);
    }

    @NotNull
    public static <T1, T2, T3, T4, T5, T6> RecordType<Record6<T1, T2, T3, T4, T5, T6>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
        return new FieldsImpl<Record6<T1, T2, T3, T4, T5, T6>>(field1, field2, field3, field4, field5, field6);
    }

    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7> RecordType<Record7<T1, T2, T3, T4, T5, T6, T7>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
        return new FieldsImpl<Record7<T1, T2, T3, T4, T5, T6, T7>>(field1, field2, field3, field4, field5, field6, field7);
    }

    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8> RecordType<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
        return new FieldsImpl<Record8<T1, T2, T3, T4, T5, T6, T7, T8>>(field1, field2, field3, field4, field5, field6, field7, field8);
    }

    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> RecordType<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
        return new FieldsImpl<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>(field1, field2, field3, field4, field5, field6, field7, field8, field9);
    }

    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> RecordType<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
        return new FieldsImpl<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
    }

    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> RecordType<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
        return new FieldsImpl<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
    }

    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> RecordType<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
        return new FieldsImpl<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
    }

    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> RecordType<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
        return new FieldsImpl<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
    }

    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> RecordType<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
        return new FieldsImpl<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
    }

    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> RecordType<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
        return new FieldsImpl<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
    }

    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> RecordType<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
        return new FieldsImpl<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
    }

    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> RecordType<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
        return new FieldsImpl<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
    }

    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> RecordType<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
        return new FieldsImpl<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
    }

    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> RecordType<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
        return new FieldsImpl<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
    }

    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> RecordType<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
        return new FieldsImpl<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
    }

    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> RecordType<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
        return new FieldsImpl<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
    }

    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> RecordType<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> recordType(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
        return new FieldsImpl<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
    }

    @Support
    @NotNull
    public static <T1> Row1<T1> row(T1 t1) {
        return DSL.row(Tools.field(t1));
    }

    @Support
    @NotNull
    public static <T1, T2> Row2<T1, T2> row(T1 t1, T2 t2) {
        return DSL.row(Tools.field(t1), Tools.field(t2));
    }

    @Support
    @NotNull
    public static <T1, T2, T3> Row3<T1, T2, T3> row(T1 t1, T2 t2, T3 t3) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3));
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4> Row4<T1, T2, T3, T4> row(T1 t1, T2 t2, T3 t3, T4 t4) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4));
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5> Row5<T1, T2, T3, T4, T5> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5));
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6> Row6<T1, T2, T3, T4, T5, T6> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6));
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7> Row7<T1, T2, T3, T4, T5, T6, T7> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7));
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8> Row8<T1, T2, T3, T4, T5, T6, T7, T8> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8));
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9));
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10));
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11));
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12));
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13));
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13), Tools.field(t14));
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13), Tools.field(t14), Tools.field(t15));
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13), Tools.field(t14), Tools.field(t15), Tools.field(t16));
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13), Tools.field(t14), Tools.field(t15), Tools.field(t16), Tools.field(t17));
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13), Tools.field(t14), Tools.field(t15), Tools.field(t16), Tools.field(t17), Tools.field(t18));
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13), Tools.field(t14), Tools.field(t15), Tools.field(t16), Tools.field(t17), Tools.field(t18), Tools.field(t19));
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13), Tools.field(t14), Tools.field(t15), Tools.field(t16), Tools.field(t17), Tools.field(t18), Tools.field(t19), Tools.field(t20));
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13), Tools.field(t14), Tools.field(t15), Tools.field(t16), Tools.field(t17), Tools.field(t18), Tools.field(t19), Tools.field(t20), Tools.field(t21));
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22) {
        return DSL.row(Tools.field(t1), Tools.field(t2), Tools.field(t3), Tools.field(t4), Tools.field(t5), Tools.field(t6), Tools.field(t7), Tools.field(t8), Tools.field(t9), Tools.field(t10), Tools.field(t11), Tools.field(t12), Tools.field(t13), Tools.field(t14), Tools.field(t15), Tools.field(t16), Tools.field(t17), Tools.field(t18), Tools.field(t19), Tools.field(t20), Tools.field(t21), Tools.field(t22));
    }

    @Support
    @NotNull
    public static RowN row(Object ... values2) {
        return DSL.row(Tools.fieldsArray(values2));
    }

    @Support
    @NotNull
    public static <T1> Row1<T1> row(SelectField<T1> field1) {
        return new RowImpl1<T1>(field1);
    }

    @Support
    @NotNull
    public static <T1, T2> Row2<T1, T2> row(SelectField<T1> field1, SelectField<T2> field2) {
        return new RowImpl2<T1, T2>(field1, field2);
    }

    @Support
    @NotNull
    public static <T1, T2, T3> Row3<T1, T2, T3> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3) {
        return new RowImpl3<T1, T2, T3>(field1, field2, field3);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4> Row4<T1, T2, T3, T4> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4) {
        return new RowImpl4<T1, T2, T3, T4>(field1, field2, field3, field4);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5> Row5<T1, T2, T3, T4, T5> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5) {
        return new RowImpl5<T1, T2, T3, T4, T5>(field1, field2, field3, field4, field5);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6> Row6<T1, T2, T3, T4, T5, T6> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6) {
        return new RowImpl6<T1, T2, T3, T4, T5, T6>(field1, field2, field3, field4, field5, field6);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7> Row7<T1, T2, T3, T4, T5, T6, T7> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7) {
        return new RowImpl7<T1, T2, T3, T4, T5, T6, T7>(field1, field2, field3, field4, field5, field6, field7);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8> Row8<T1, T2, T3, T4, T5, T6, T7, T8> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8) {
        return new RowImpl8<T1, T2, T3, T4, T5, T6, T7, T8>(field1, field2, field3, field4, field5, field6, field7, field8);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9) {
        return new RowImpl9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(field1, field2, field3, field4, field5, field6, field7, field8, field9);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10) {
        return new RowImpl10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11) {
        return new RowImpl11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12) {
        return new RowImpl12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13) {
        return new RowImpl13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14) {
        return new RowImpl14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15) {
        return new RowImpl15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16) {
        return new RowImpl16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17) {
        return new RowImpl17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18) {
        return new RowImpl18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19) {
        return new RowImpl19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20) {
        return new RowImpl20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21) {
        return new RowImpl21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
    }

    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21, SelectField<T22> field22) {
        return new RowImpl22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
    }

    @Support
    @NotNull
    public static RowN row(SelectField<?> ... values2) {
        return new RowImplN(values2);
    }

    @Support
    @NotNull
    public static RowN row(Collection<?> values2) {
        return DSL.row(values2.toArray());
    }

    @Support
    @NotNull
    public static Table<Record> values(RowN ... rows) {
        return DSL.values0(rows);
    }

    @Support
    @NotNull
    static Table<Record> values0(Row ... rows) {
        return new Values<Record>(Values.assertNotEmpty(rows));
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1> Table<Record1<T1>> values(Row1<T1> ... rows) {
        return new Values<Record1<T1>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2> Table<Record2<T1, T2>> values(Row2<T1, T2> ... rows) {
        return new Values<Record2<T1, T2>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3> Table<Record3<T1, T2, T3>> values(Row3<T1, T2, T3> ... rows) {
        return new Values<Record3<T1, T2, T3>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3, T4> Table<Record4<T1, T2, T3, T4>> values(Row4<T1, T2, T3, T4> ... rows) {
        return new Values<Record4<T1, T2, T3, T4>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5> Table<Record5<T1, T2, T3, T4, T5>> values(Row5<T1, T2, T3, T4, T5> ... rows) {
        return new Values<Record5<T1, T2, T3, T4, T5>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6> Table<Record6<T1, T2, T3, T4, T5, T6>> values(Row6<T1, T2, T3, T4, T5, T6> ... rows) {
        return new Values<Record6<T1, T2, T3, T4, T5, T6>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7> Table<Record7<T1, T2, T3, T4, T5, T6, T7>> values(Row7<T1, T2, T3, T4, T5, T6, T7> ... rows) {
        return new Values<Record7<T1, T2, T3, T4, T5, T6, T7>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8> Table<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> values(Row8<T1, T2, T3, T4, T5, T6, T7, T8> ... rows) {
        return new Values<Record8<T1, T2, T3, T4, T5, T6, T7, T8>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Table<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> values(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> ... rows) {
        return new Values<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Table<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> values(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ... rows) {
        return new Values<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Table<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> values(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ... rows) {
        return new Values<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Table<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> values(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ... rows) {
        return new Values<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Table<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> values(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> ... rows) {
        return new Values<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Table<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> values(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> ... rows) {
        return new Values<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Table<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> values(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> ... rows) {
        return new Values<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Table<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> values(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> ... rows) {
        return new Values<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Table<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> values(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> ... rows) {
        return new Values<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Table<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> values(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> ... rows) {
        return new Values<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Table<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> values(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> ... rows) {
        return new Values<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Table<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> values(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> ... rows) {
        return new Values<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Table<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> values(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> ... rows) {
        return new Values<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>>(rows);
    }

    @SafeVarargs
    @Support
    @NotNull
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Table<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> values(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> ... rows) {
        return new Values<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>>(rows);
    }

    @Deprecated(forRemoval=true, since="3.15")
    protected static <T> Field<T> nullSafe(Field<T> field2) {
        return Tools.nullSafe(field2);
    }

    @Deprecated(forRemoval=true, since="3.15")
    protected static <T> Field<T> nullSafe(Field<T> field2, DataType<?> type) {
        return Tools.nullSafe(field2, type);
    }

    @Deprecated(forRemoval=true, since="3.15")
    protected static Field<?>[] nullSafe(Field<?> ... fields) {
        return Tools.nullSafe(fields);
    }

    @Deprecated(forRemoval=true, since="3.15")
    protected static Field<?>[] nullSafe(Field<?>[] fields, DataType<?> type) {
        return Tools.nullSafe(fields, type);
    }

    @Deprecated(forRemoval=true, since="3.15")
    protected static List<Field<?>> nullSafeList(Field<?> ... fields) {
        return Tools.nullSafeList(fields);
    }

    @Deprecated(forRemoval=true, since="3.15")
    protected static List<Field<?>> nullSafeList(Field<?>[] fields, DataType<?> type) {
        return Tools.nullSafeList(fields, type);
    }

    @Deprecated(forRemoval=true, since="3.15")
    protected static <T> DataType<T> nullSafeDataType(Field<T> field2) {
        return Tools.nullSafeDataType(field2);
    }

    @Support
    @NotNull
    public static Asterisk asterisk() {
        return AsteriskImpl.INSTANCE.get();
    }

    @Support
    @NotNull
    public static Param<Integer> zero() {
        return DSL.inline(0);
    }

    @Support
    @NotNull
    public static Param<Integer> one() {
        return DSL.inline(1);
    }

    @Support
    @NotNull
    public static Param<Integer> two() {
        return DSL.inline(2);
    }

    @Deprecated(forRemoval=true, since="3.11")
    @Support
    @NotNull
    public static <T> DataType<T> getDataType(Class<T> type) {
        return DefaultDataType.getDataType(SQLDialect.DEFAULT, type);
    }

    static <T> DataType<T> getDataType0(Class<T> type) {
        DataType<Object> t2 = DefaultDataType.getDataType(SQLDialect.DEFAULT, type, SQLDataType.OTHER);
        if (t2 instanceof LegacyConvertedDataType) {
            LegacyConvertedDataType l = (LegacyConvertedDataType)t2;
            return new DataTypeProxy(l);
        }
        if (t2 != SQLDataType.OTHER) {
            return t2;
        }
        return DefaultDataType.getDataType(SQLDialect.DEFAULT, type, new DataTypeProxy((AbstractDataType)SQLDataType.OTHER));
    }

    private static final DSLContext dsl() {
        return DSL.using(new DefaultConfiguration());
    }

    protected DSL() {
        throw new UnsupportedOperationException();
    }
}

