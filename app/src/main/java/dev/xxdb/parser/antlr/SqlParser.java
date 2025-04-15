// Generated from C:/workspace/xxdb/app/src/main/java/dev/xxdb/parser/antlr/Sql.g4 by ANTLR 4.13.2
package dev.xxdb.parser.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class SqlParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, SELECT=12, FROM=13, WHERE=14, INSERT=15, INTO=16, VALUES=17, 
		UPDATE=18, SET=19, CREATE=20, TABLE=21, JOIN=22, ON=23, LIMIT=24, AND=25, 
		OR=26, INT_TYPE=27, VARCHAR_TYPE=28, IDENTIFIER=29, NUMBER=30, STRING=31, 
		WS=32;
	public static final int
		RULE_sql = 0, RULE_statement = 1, RULE_selectStatement = 2, RULE_joinClause = 3, 
		RULE_limitClause = 4, RULE_insertStatement = 5, RULE_updateStatement = 6, 
		RULE_createTableStatement = 7, RULE_columnDefinitionList = 8, RULE_columnDefinition = 9, 
		RULE_assignmentList = 10, RULE_assignment = 11, RULE_columnList = 12, 
		RULE_valueList = 13, RULE_whereClause = 14, RULE_condition = 15, RULE_operator = 16, 
		RULE_dataType = 17, RULE_columnName = 18, RULE_tableName = 19, RULE_value = 20;
	private static String[] makeRuleNames() {
		return new String[] {
			"sql", "statement", "selectStatement", "joinClause", "limitClause", "insertStatement", 
			"updateStatement", "createTableStatement", "columnDefinitionList", "columnDefinition", 
			"assignmentList", "assignment", "columnList", "valueList", "whereClause", 
			"condition", "operator", "dataType", "columnName", "tableName", "value"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "'('", "')'", "','", "'='", "'>'", "'<'", "'<='", "'>='", 
			"'!='", "'.'", "'SELECT'", "'FROM'", "'WHERE'", "'INSERT'", "'INTO'", 
			"'VALUES'", "'UPDATE'", "'SET'", "'CREATE'", "'TABLE'", "'JOIN'", "'ON'", 
			"'LIMIT'", "'AND'", "'OR'", "'INT'", "'VARCHAR'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"SELECT", "FROM", "WHERE", "INSERT", "INTO", "VALUES", "UPDATE", "SET", 
			"CREATE", "TABLE", "JOIN", "ON", "LIMIT", "AND", "OR", "INT_TYPE", "VARCHAR_TYPE", 
			"IDENTIFIER", "NUMBER", "STRING", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Sql.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SqlParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SqlContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode EOF() { return getToken(SqlParser.EOF, 0); }
		public SqlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sql; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterSql(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitSql(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitSql(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SqlContext sql() throws RecognitionException {
		SqlContext _localctx = new SqlContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_sql);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			statement();
			setState(44); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(43);
				match(T__0);
				}
				}
				setState(46); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__0 );
			setState(48);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public SelectStatementContext selectStatement() {
			return getRuleContext(SelectStatementContext.class,0);
		}
		public InsertStatementContext insertStatement() {
			return getRuleContext(InsertStatementContext.class,0);
		}
		public UpdateStatementContext updateStatement() {
			return getRuleContext(UpdateStatementContext.class,0);
		}
		public CreateTableStatementContext createTableStatement() {
			return getRuleContext(CreateTableStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(54);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SELECT:
				enterOuterAlt(_localctx, 1);
				{
				setState(50);
				selectStatement();
				}
				break;
			case INSERT:
				enterOuterAlt(_localctx, 2);
				{
				setState(51);
				insertStatement();
				}
				break;
			case UPDATE:
				enterOuterAlt(_localctx, 3);
				{
				setState(52);
				updateStatement();
				}
				break;
			case CREATE:
				enterOuterAlt(_localctx, 4);
				{
				setState(53);
				createTableStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SelectStatementContext extends ParserRuleContext {
		public TerminalNode SELECT() { return getToken(SqlParser.SELECT, 0); }
		public ColumnListContext columnList() {
			return getRuleContext(ColumnListContext.class,0);
		}
		public TerminalNode FROM() { return getToken(SqlParser.FROM, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public JoinClauseContext joinClause() {
			return getRuleContext(JoinClauseContext.class,0);
		}
		public WhereClauseContext whereClause() {
			return getRuleContext(WhereClauseContext.class,0);
		}
		public LimitClauseContext limitClause() {
			return getRuleContext(LimitClauseContext.class,0);
		}
		public SelectStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterSelectStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitSelectStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitSelectStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectStatementContext selectStatement() throws RecognitionException {
		SelectStatementContext _localctx = new SelectStatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_selectStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			match(SELECT);
			setState(57);
			columnList();
			setState(58);
			match(FROM);
			setState(59);
			tableName();
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==JOIN) {
				{
				setState(60);
				joinClause();
				}
			}

			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHERE) {
				{
				setState(63);
				whereClause();
				}
			}

			setState(67);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LIMIT) {
				{
				setState(66);
				limitClause();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JoinClauseContext extends ParserRuleContext {
		public TerminalNode JOIN() { return getToken(SqlParser.JOIN, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public TerminalNode ON() { return getToken(SqlParser.ON, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public JoinClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_joinClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterJoinClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitJoinClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitJoinClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JoinClauseContext joinClause() throws RecognitionException {
		JoinClauseContext _localctx = new JoinClauseContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_joinClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			match(JOIN);
			setState(70);
			tableName();
			setState(71);
			match(ON);
			setState(72);
			condition(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LimitClauseContext extends ParserRuleContext {
		public TerminalNode LIMIT() { return getToken(SqlParser.LIMIT, 0); }
		public TerminalNode NUMBER() { return getToken(SqlParser.NUMBER, 0); }
		public LimitClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_limitClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterLimitClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitLimitClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitLimitClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LimitClauseContext limitClause() throws RecognitionException {
		LimitClauseContext _localctx = new LimitClauseContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_limitClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			match(LIMIT);
			setState(75);
			match(NUMBER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InsertStatementContext extends ParserRuleContext {
		public TerminalNode INSERT() { return getToken(SqlParser.INSERT, 0); }
		public TerminalNode INTO() { return getToken(SqlParser.INTO, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public ColumnListContext columnList() {
			return getRuleContext(ColumnListContext.class,0);
		}
		public TerminalNode VALUES() { return getToken(SqlParser.VALUES, 0); }
		public ValueListContext valueList() {
			return getRuleContext(ValueListContext.class,0);
		}
		public InsertStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insertStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterInsertStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitInsertStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitInsertStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InsertStatementContext insertStatement() throws RecognitionException {
		InsertStatementContext _localctx = new InsertStatementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_insertStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			match(INSERT);
			setState(78);
			match(INTO);
			setState(79);
			tableName();
			setState(80);
			match(T__1);
			setState(81);
			columnList();
			setState(82);
			match(T__2);
			setState(83);
			match(VALUES);
			setState(84);
			match(T__1);
			setState(85);
			valueList();
			setState(86);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UpdateStatementContext extends ParserRuleContext {
		public TerminalNode UPDATE() { return getToken(SqlParser.UPDATE, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public TerminalNode SET() { return getToken(SqlParser.SET, 0); }
		public AssignmentListContext assignmentList() {
			return getRuleContext(AssignmentListContext.class,0);
		}
		public WhereClauseContext whereClause() {
			return getRuleContext(WhereClauseContext.class,0);
		}
		public UpdateStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_updateStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterUpdateStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitUpdateStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitUpdateStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UpdateStatementContext updateStatement() throws RecognitionException {
		UpdateStatementContext _localctx = new UpdateStatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_updateStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			match(UPDATE);
			setState(89);
			tableName();
			setState(90);
			match(SET);
			setState(91);
			assignmentList();
			setState(93);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHERE) {
				{
				setState(92);
				whereClause();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateTableStatementContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(SqlParser.CREATE, 0); }
		public TerminalNode TABLE() { return getToken(SqlParser.TABLE, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public ColumnDefinitionListContext columnDefinitionList() {
			return getRuleContext(ColumnDefinitionListContext.class,0);
		}
		public CreateTableStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createTableStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterCreateTableStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitCreateTableStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitCreateTableStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateTableStatementContext createTableStatement() throws RecognitionException {
		CreateTableStatementContext _localctx = new CreateTableStatementContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_createTableStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(CREATE);
			setState(96);
			match(TABLE);
			setState(97);
			tableName();
			setState(98);
			match(T__1);
			setState(99);
			columnDefinitionList();
			setState(100);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ColumnDefinitionListContext extends ParserRuleContext {
		public List<ColumnDefinitionContext> columnDefinition() {
			return getRuleContexts(ColumnDefinitionContext.class);
		}
		public ColumnDefinitionContext columnDefinition(int i) {
			return getRuleContext(ColumnDefinitionContext.class,i);
		}
		public ColumnDefinitionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnDefinitionList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterColumnDefinitionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitColumnDefinitionList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitColumnDefinitionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnDefinitionListContext columnDefinitionList() throws RecognitionException {
		ColumnDefinitionListContext _localctx = new ColumnDefinitionListContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_columnDefinitionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			columnDefinition();
			setState(107);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(103);
				match(T__3);
				setState(104);
				columnDefinition();
				}
				}
				setState(109);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ColumnDefinitionContext extends ParserRuleContext {
		public ColumnNameContext columnName() {
			return getRuleContext(ColumnNameContext.class,0);
		}
		public DataTypeContext dataType() {
			return getRuleContext(DataTypeContext.class,0);
		}
		public ColumnDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterColumnDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitColumnDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitColumnDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnDefinitionContext columnDefinition() throws RecognitionException {
		ColumnDefinitionContext _localctx = new ColumnDefinitionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_columnDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			columnName();
			setState(111);
			dataType();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignmentListContext extends ParserRuleContext {
		public List<AssignmentContext> assignment() {
			return getRuleContexts(AssignmentContext.class);
		}
		public AssignmentContext assignment(int i) {
			return getRuleContext(AssignmentContext.class,i);
		}
		public AssignmentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterAssignmentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitAssignmentList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitAssignmentList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentListContext assignmentList() throws RecognitionException {
		AssignmentListContext _localctx = new AssignmentListContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_assignmentList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			assignment();
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(114);
				match(T__3);
				setState(115);
				assignment();
				}
				}
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignmentContext extends ParserRuleContext {
		public ColumnNameContext columnName() {
			return getRuleContext(ColumnNameContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			columnName();
			setState(122);
			match(T__4);
			setState(123);
			value();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ColumnListContext extends ParserRuleContext {
		public List<ColumnNameContext> columnName() {
			return getRuleContexts(ColumnNameContext.class);
		}
		public ColumnNameContext columnName(int i) {
			return getRuleContext(ColumnNameContext.class,i);
		}
		public ColumnListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterColumnList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitColumnList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitColumnList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnListContext columnList() throws RecognitionException {
		ColumnListContext _localctx = new ColumnListContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_columnList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			columnName();
			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(126);
				match(T__3);
				setState(127);
				columnName();
				}
				}
				setState(132);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValueListContext extends ParserRuleContext {
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public ValueListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterValueList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitValueList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitValueList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueListContext valueList() throws RecognitionException {
		ValueListContext _localctx = new ValueListContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_valueList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			value();
			setState(138);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(134);
				match(T__3);
				setState(135);
				value();
				}
				}
				setState(140);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhereClauseContext extends ParserRuleContext {
		public TerminalNode WHERE() { return getToken(SqlParser.WHERE, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public WhereClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whereClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterWhereClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitWhereClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitWhereClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhereClauseContext whereClause() throws RecognitionException {
		WhereClauseContext _localctx = new WhereClauseContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_whereClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			match(WHERE);
			setState(142);
			condition(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConditionContext extends ParserRuleContext {
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
	 
		public ConditionContext() { }
		public void copyFrom(ConditionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SimpleColumnConditionContext extends ConditionContext {
		public List<ColumnNameContext> columnName() {
			return getRuleContexts(ColumnNameContext.class);
		}
		public ColumnNameContext columnName(int i) {
			return getRuleContext(ColumnNameContext.class,i);
		}
		public OperatorContext operator() {
			return getRuleContext(OperatorContext.class,0);
		}
		public SimpleColumnConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterSimpleColumnCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitSimpleColumnCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitSimpleColumnCondition(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SimpleValueConditionContext extends ConditionContext {
		public ColumnNameContext columnName() {
			return getRuleContext(ColumnNameContext.class,0);
		}
		public OperatorContext operator() {
			return getRuleContext(OperatorContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public SimpleValueConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterSimpleValueCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitSimpleValueCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitSimpleValueCondition(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrConditionContext extends ConditionContext {
		public List<ConditionContext> condition() {
			return getRuleContexts(ConditionContext.class);
		}
		public ConditionContext condition(int i) {
			return getRuleContext(ConditionContext.class,i);
		}
		public TerminalNode OR() { return getToken(SqlParser.OR, 0); }
		public OrConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterOrCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitOrCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitOrCondition(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndConditionContext extends ConditionContext {
		public List<ConditionContext> condition() {
			return getRuleContexts(ConditionContext.class);
		}
		public ConditionContext condition(int i) {
			return getRuleContext(ConditionContext.class,i);
		}
		public TerminalNode AND() { return getToken(SqlParser.AND, 0); }
		public AndConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterAndCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitAndCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitAndCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		return condition(0);
	}

	private ConditionContext condition(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ConditionContext _localctx = new ConditionContext(_ctx, _parentState);
		ConditionContext _prevctx = _localctx;
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_condition, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				_localctx = new SimpleValueConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(145);
				columnName();
				setState(146);
				operator();
				setState(147);
				value();
				}
				break;
			case 2:
				{
				_localctx = new SimpleColumnConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(149);
				columnName();
				setState(150);
				operator();
				setState(151);
				columnName();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(163);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(161);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						_localctx = new AndConditionContext(new ConditionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_condition);
						setState(155);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(156);
						match(AND);
						setState(157);
						condition(5);
						}
						break;
					case 2:
						{
						_localctx = new OrConditionContext(new ConditionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_condition);
						setState(158);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(159);
						match(OR);
						setState(160);
						condition(4);
						}
						break;
					}
					} 
				}
				setState(165);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OperatorContext extends ParserRuleContext {
		public OperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorContext operator() throws RecognitionException {
		OperatorContext _localctx = new OperatorContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 2016L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DataTypeContext extends ParserRuleContext {
		public TerminalNode INT_TYPE() { return getToken(SqlParser.INT_TYPE, 0); }
		public TerminalNode VARCHAR_TYPE() { return getToken(SqlParser.VARCHAR_TYPE, 0); }
		public DataTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterDataType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitDataType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitDataType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DataTypeContext dataType() throws RecognitionException {
		DataTypeContext _localctx = new DataTypeContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_dataType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			_la = _input.LA(1);
			if ( !(_la==INT_TYPE || _la==VARCHAR_TYPE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ColumnNameContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(SqlParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(SqlParser.IDENTIFIER, i);
		}
		public ColumnNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterColumnName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitColumnName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitColumnName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnNameContext columnName() throws RecognitionException {
		ColumnNameContext _localctx = new ColumnNameContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_columnName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			match(IDENTIFIER);
			setState(173);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(171);
				match(T__10);
				setState(172);
				match(IDENTIFIER);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TableNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(SqlParser.IDENTIFIER, 0); }
		public TableNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterTableName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitTableName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitTableName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableNameContext tableName() throws RecognitionException {
		TableNameContext _localctx = new TableNameContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_tableName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValueContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(SqlParser.STRING, 0); }
		public TerminalNode NUMBER() { return getToken(SqlParser.NUMBER, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SqlListener ) ((SqlListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SqlVisitor ) return ((SqlVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(177);
			_la = _input.LA(1);
			if ( !(_la==NUMBER || _la==STRING) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 15:
			return condition_sempred((ConditionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean condition_sempred(ConditionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001 \u00b4\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0001\u0000\u0001\u0000"+
		"\u0004\u0000-\b\u0000\u000b\u0000\f\u0000.\u0001\u0000\u0001\u0000\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u00017\b\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002>\b"+
		"\u0002\u0001\u0002\u0003\u0002A\b\u0002\u0001\u0002\u0003\u0002D\b\u0002"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0003\u0006^\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0005\bj"+
		"\b\b\n\b\f\bm\t\b\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0005"+
		"\nu\b\n\n\n\f\nx\t\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\f\u0001\f\u0001\f\u0005\f\u0081\b\f\n\f\f\f\u0084\t\f\u0001\r\u0001\r"+
		"\u0001\r\u0005\r\u0089\b\r\n\r\f\r\u008c\t\r\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u009a\b\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0005"+
		"\u000f\u00a2\b\u000f\n\u000f\f\u000f\u00a5\t\u000f\u0001\u0010\u0001\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012"+
		"\u00ae\b\u0012\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0000\u0001\u001e\u0015\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012"+
		"\u0014\u0016\u0018\u001a\u001c\u001e \"$&(\u0000\u0003\u0001\u0000\u0005"+
		"\n\u0001\u0000\u001b\u001c\u0001\u0000\u001e\u001f\u00ae\u0000*\u0001"+
		"\u0000\u0000\u0000\u00026\u0001\u0000\u0000\u0000\u00048\u0001\u0000\u0000"+
		"\u0000\u0006E\u0001\u0000\u0000\u0000\bJ\u0001\u0000\u0000\u0000\nM\u0001"+
		"\u0000\u0000\u0000\fX\u0001\u0000\u0000\u0000\u000e_\u0001\u0000\u0000"+
		"\u0000\u0010f\u0001\u0000\u0000\u0000\u0012n\u0001\u0000\u0000\u0000\u0014"+
		"q\u0001\u0000\u0000\u0000\u0016y\u0001\u0000\u0000\u0000\u0018}\u0001"+
		"\u0000\u0000\u0000\u001a\u0085\u0001\u0000\u0000\u0000\u001c\u008d\u0001"+
		"\u0000\u0000\u0000\u001e\u0099\u0001\u0000\u0000\u0000 \u00a6\u0001\u0000"+
		"\u0000\u0000\"\u00a8\u0001\u0000\u0000\u0000$\u00aa\u0001\u0000\u0000"+
		"\u0000&\u00af\u0001\u0000\u0000\u0000(\u00b1\u0001\u0000\u0000\u0000*"+
		",\u0003\u0002\u0001\u0000+-\u0005\u0001\u0000\u0000,+\u0001\u0000\u0000"+
		"\u0000-.\u0001\u0000\u0000\u0000.,\u0001\u0000\u0000\u0000./\u0001\u0000"+
		"\u0000\u0000/0\u0001\u0000\u0000\u000001\u0005\u0000\u0000\u00011\u0001"+
		"\u0001\u0000\u0000\u000027\u0003\u0004\u0002\u000037\u0003\n\u0005\u0000"+
		"47\u0003\f\u0006\u000057\u0003\u000e\u0007\u000062\u0001\u0000\u0000\u0000"+
		"63\u0001\u0000\u0000\u000064\u0001\u0000\u0000\u000065\u0001\u0000\u0000"+
		"\u00007\u0003\u0001\u0000\u0000\u000089\u0005\f\u0000\u00009:\u0003\u0018"+
		"\f\u0000:;\u0005\r\u0000\u0000;=\u0003&\u0013\u0000<>\u0003\u0006\u0003"+
		"\u0000=<\u0001\u0000\u0000\u0000=>\u0001\u0000\u0000\u0000>@\u0001\u0000"+
		"\u0000\u0000?A\u0003\u001c\u000e\u0000@?\u0001\u0000\u0000\u0000@A\u0001"+
		"\u0000\u0000\u0000AC\u0001\u0000\u0000\u0000BD\u0003\b\u0004\u0000CB\u0001"+
		"\u0000\u0000\u0000CD\u0001\u0000\u0000\u0000D\u0005\u0001\u0000\u0000"+
		"\u0000EF\u0005\u0016\u0000\u0000FG\u0003&\u0013\u0000GH\u0005\u0017\u0000"+
		"\u0000HI\u0003\u001e\u000f\u0000I\u0007\u0001\u0000\u0000\u0000JK\u0005"+
		"\u0018\u0000\u0000KL\u0005\u001e\u0000\u0000L\t\u0001\u0000\u0000\u0000"+
		"MN\u0005\u000f\u0000\u0000NO\u0005\u0010\u0000\u0000OP\u0003&\u0013\u0000"+
		"PQ\u0005\u0002\u0000\u0000QR\u0003\u0018\f\u0000RS\u0005\u0003\u0000\u0000"+
		"ST\u0005\u0011\u0000\u0000TU\u0005\u0002\u0000\u0000UV\u0003\u001a\r\u0000"+
		"VW\u0005\u0003\u0000\u0000W\u000b\u0001\u0000\u0000\u0000XY\u0005\u0012"+
		"\u0000\u0000YZ\u0003&\u0013\u0000Z[\u0005\u0013\u0000\u0000[]\u0003\u0014"+
		"\n\u0000\\^\u0003\u001c\u000e\u0000]\\\u0001\u0000\u0000\u0000]^\u0001"+
		"\u0000\u0000\u0000^\r\u0001\u0000\u0000\u0000_`\u0005\u0014\u0000\u0000"+
		"`a\u0005\u0015\u0000\u0000ab\u0003&\u0013\u0000bc\u0005\u0002\u0000\u0000"+
		"cd\u0003\u0010\b\u0000de\u0005\u0003\u0000\u0000e\u000f\u0001\u0000\u0000"+
		"\u0000fk\u0003\u0012\t\u0000gh\u0005\u0004\u0000\u0000hj\u0003\u0012\t"+
		"\u0000ig\u0001\u0000\u0000\u0000jm\u0001\u0000\u0000\u0000ki\u0001\u0000"+
		"\u0000\u0000kl\u0001\u0000\u0000\u0000l\u0011\u0001\u0000\u0000\u0000"+
		"mk\u0001\u0000\u0000\u0000no\u0003$\u0012\u0000op\u0003\"\u0011\u0000"+
		"p\u0013\u0001\u0000\u0000\u0000qv\u0003\u0016\u000b\u0000rs\u0005\u0004"+
		"\u0000\u0000su\u0003\u0016\u000b\u0000tr\u0001\u0000\u0000\u0000ux\u0001"+
		"\u0000\u0000\u0000vt\u0001\u0000\u0000\u0000vw\u0001\u0000\u0000\u0000"+
		"w\u0015\u0001\u0000\u0000\u0000xv\u0001\u0000\u0000\u0000yz\u0003$\u0012"+
		"\u0000z{\u0005\u0005\u0000\u0000{|\u0003(\u0014\u0000|\u0017\u0001\u0000"+
		"\u0000\u0000}\u0082\u0003$\u0012\u0000~\u007f\u0005\u0004\u0000\u0000"+
		"\u007f\u0081\u0003$\u0012\u0000\u0080~\u0001\u0000\u0000\u0000\u0081\u0084"+
		"\u0001\u0000\u0000\u0000\u0082\u0080\u0001\u0000\u0000\u0000\u0082\u0083"+
		"\u0001\u0000\u0000\u0000\u0083\u0019\u0001\u0000\u0000\u0000\u0084\u0082"+
		"\u0001\u0000\u0000\u0000\u0085\u008a\u0003(\u0014\u0000\u0086\u0087\u0005"+
		"\u0004\u0000\u0000\u0087\u0089\u0003(\u0014\u0000\u0088\u0086\u0001\u0000"+
		"\u0000\u0000\u0089\u008c\u0001\u0000\u0000\u0000\u008a\u0088\u0001\u0000"+
		"\u0000\u0000\u008a\u008b\u0001\u0000\u0000\u0000\u008b\u001b\u0001\u0000"+
		"\u0000\u0000\u008c\u008a\u0001\u0000\u0000\u0000\u008d\u008e\u0005\u000e"+
		"\u0000\u0000\u008e\u008f\u0003\u001e\u000f\u0000\u008f\u001d\u0001\u0000"+
		"\u0000\u0000\u0090\u0091\u0006\u000f\uffff\uffff\u0000\u0091\u0092\u0003"+
		"$\u0012\u0000\u0092\u0093\u0003 \u0010\u0000\u0093\u0094\u0003(\u0014"+
		"\u0000\u0094\u009a\u0001\u0000\u0000\u0000\u0095\u0096\u0003$\u0012\u0000"+
		"\u0096\u0097\u0003 \u0010\u0000\u0097\u0098\u0003$\u0012\u0000\u0098\u009a"+
		"\u0001\u0000\u0000\u0000\u0099\u0090\u0001\u0000\u0000\u0000\u0099\u0095"+
		"\u0001\u0000\u0000\u0000\u009a\u00a3\u0001\u0000\u0000\u0000\u009b\u009c"+
		"\n\u0004\u0000\u0000\u009c\u009d\u0005\u0019\u0000\u0000\u009d\u00a2\u0003"+
		"\u001e\u000f\u0005\u009e\u009f\n\u0003\u0000\u0000\u009f\u00a0\u0005\u001a"+
		"\u0000\u0000\u00a0\u00a2\u0003\u001e\u000f\u0004\u00a1\u009b\u0001\u0000"+
		"\u0000\u0000\u00a1\u009e\u0001\u0000\u0000\u0000\u00a2\u00a5\u0001\u0000"+
		"\u0000\u0000\u00a3\u00a1\u0001\u0000\u0000\u0000\u00a3\u00a4\u0001\u0000"+
		"\u0000\u0000\u00a4\u001f\u0001\u0000\u0000\u0000\u00a5\u00a3\u0001\u0000"+
		"\u0000\u0000\u00a6\u00a7\u0007\u0000\u0000\u0000\u00a7!\u0001\u0000\u0000"+
		"\u0000\u00a8\u00a9\u0007\u0001\u0000\u0000\u00a9#\u0001\u0000\u0000\u0000"+
		"\u00aa\u00ad\u0005\u001d\u0000\u0000\u00ab\u00ac\u0005\u000b\u0000\u0000"+
		"\u00ac\u00ae\u0005\u001d\u0000\u0000\u00ad\u00ab\u0001\u0000\u0000\u0000"+
		"\u00ad\u00ae\u0001\u0000\u0000\u0000\u00ae%\u0001\u0000\u0000\u0000\u00af"+
		"\u00b0\u0005\u001d\u0000\u0000\u00b0\'\u0001\u0000\u0000\u0000\u00b1\u00b2"+
		"\u0007\u0002\u0000\u0000\u00b2)\u0001\u0000\u0000\u0000\u000e.6=@C]kv"+
		"\u0082\u008a\u0099\u00a1\u00a3\u00ad";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}