// Generated from C:/workspace/xxdb/app/src/main/java/dev/xxdb/parser/antlr/Sql.g4 by ANTLR 4.13.2
package dev.xxdb.parser.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class SqlLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "SELECT", "FROM", "WHERE", "INSERT", "INTO", "VALUES", 
			"UPDATE", "SET", "CREATE", "TABLE", "JOIN", "ON", "LIMIT", "AND", "OR", 
			"INT_TYPE", "VARCHAR_TYPE", "IDENTIFIER", "NUMBER", "STRING", "WS"
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


	public SqlLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Sql.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000 \u00d4\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0001\u0000\u0001\u0000\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t"+
		"\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a"+
		"\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0005\u001c\u00bb\b\u001c"+
		"\n\u001c\f\u001c\u00be\t\u001c\u0001\u001d\u0004\u001d\u00c1\b\u001d\u000b"+
		"\u001d\f\u001d\u00c2\u0001\u001e\u0001\u001e\u0005\u001e\u00c7\b\u001e"+
		"\n\u001e\f\u001e\u00ca\t\u001e\u0001\u001e\u0001\u001e\u0001\u001f\u0004"+
		"\u001f\u00cf\b\u001f\u000b\u001f\f\u001f\u00d0\u0001\u001f\u0001\u001f"+
		"\u0001\u00c8\u0000 \u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t"+
		"\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f"+
		"\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\'\u0014"+
		")\u0015+\u0016-\u0017/\u00181\u00193\u001a5\u001b7\u001c9\u001d;\u001e"+
		"=\u001f? \u0001\u0000\u0004\u0003\u0000AZ__az\u0004\u000009AZ__az\u0001"+
		"\u000009\u0003\u0000\t\n\r\r  \u00d7\u0000\u0001\u0001\u0000\u0000\u0000"+
		"\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000"+
		"\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000"+
		"\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f"+
		"\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013"+
		"\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017"+
		"\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b"+
		"\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f"+
		"\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000"+
		"\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000"+
		"\u0000\u0000)\u0001\u0000\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000"+
		"-\u0001\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000\u00001\u0001"+
		"\u0000\u0000\u0000\u00003\u0001\u0000\u0000\u0000\u00005\u0001\u0000\u0000"+
		"\u0000\u00007\u0001\u0000\u0000\u0000\u00009\u0001\u0000\u0000\u0000\u0000"+
		";\u0001\u0000\u0000\u0000\u0000=\u0001\u0000\u0000\u0000\u0000?\u0001"+
		"\u0000\u0000\u0000\u0001A\u0001\u0000\u0000\u0000\u0003C\u0001\u0000\u0000"+
		"\u0000\u0005E\u0001\u0000\u0000\u0000\u0007G\u0001\u0000\u0000\u0000\t"+
		"I\u0001\u0000\u0000\u0000\u000bK\u0001\u0000\u0000\u0000\rM\u0001\u0000"+
		"\u0000\u0000\u000fO\u0001\u0000\u0000\u0000\u0011R\u0001\u0000\u0000\u0000"+
		"\u0013U\u0001\u0000\u0000\u0000\u0015X\u0001\u0000\u0000\u0000\u0017Z"+
		"\u0001\u0000\u0000\u0000\u0019a\u0001\u0000\u0000\u0000\u001bf\u0001\u0000"+
		"\u0000\u0000\u001dl\u0001\u0000\u0000\u0000\u001fs\u0001\u0000\u0000\u0000"+
		"!x\u0001\u0000\u0000\u0000#\u007f\u0001\u0000\u0000\u0000%\u0086\u0001"+
		"\u0000\u0000\u0000\'\u008a\u0001\u0000\u0000\u0000)\u0091\u0001\u0000"+
		"\u0000\u0000+\u0097\u0001\u0000\u0000\u0000-\u009c\u0001\u0000\u0000\u0000"+
		"/\u009f\u0001\u0000\u0000\u00001\u00a5\u0001\u0000\u0000\u00003\u00a9"+
		"\u0001\u0000\u0000\u00005\u00ac\u0001\u0000\u0000\u00007\u00b0\u0001\u0000"+
		"\u0000\u00009\u00b8\u0001\u0000\u0000\u0000;\u00c0\u0001\u0000\u0000\u0000"+
		"=\u00c4\u0001\u0000\u0000\u0000?\u00ce\u0001\u0000\u0000\u0000AB\u0005"+
		";\u0000\u0000B\u0002\u0001\u0000\u0000\u0000CD\u0005(\u0000\u0000D\u0004"+
		"\u0001\u0000\u0000\u0000EF\u0005)\u0000\u0000F\u0006\u0001\u0000\u0000"+
		"\u0000GH\u0005,\u0000\u0000H\b\u0001\u0000\u0000\u0000IJ\u0005=\u0000"+
		"\u0000J\n\u0001\u0000\u0000\u0000KL\u0005>\u0000\u0000L\f\u0001\u0000"+
		"\u0000\u0000MN\u0005<\u0000\u0000N\u000e\u0001\u0000\u0000\u0000OP\u0005"+
		"<\u0000\u0000PQ\u0005=\u0000\u0000Q\u0010\u0001\u0000\u0000\u0000RS\u0005"+
		">\u0000\u0000ST\u0005=\u0000\u0000T\u0012\u0001\u0000\u0000\u0000UV\u0005"+
		"!\u0000\u0000VW\u0005=\u0000\u0000W\u0014\u0001\u0000\u0000\u0000XY\u0005"+
		".\u0000\u0000Y\u0016\u0001\u0000\u0000\u0000Z[\u0005S\u0000\u0000[\\\u0005"+
		"E\u0000\u0000\\]\u0005L\u0000\u0000]^\u0005E\u0000\u0000^_\u0005C\u0000"+
		"\u0000_`\u0005T\u0000\u0000`\u0018\u0001\u0000\u0000\u0000ab\u0005F\u0000"+
		"\u0000bc\u0005R\u0000\u0000cd\u0005O\u0000\u0000de\u0005M\u0000\u0000"+
		"e\u001a\u0001\u0000\u0000\u0000fg\u0005W\u0000\u0000gh\u0005H\u0000\u0000"+
		"hi\u0005E\u0000\u0000ij\u0005R\u0000\u0000jk\u0005E\u0000\u0000k\u001c"+
		"\u0001\u0000\u0000\u0000lm\u0005I\u0000\u0000mn\u0005N\u0000\u0000no\u0005"+
		"S\u0000\u0000op\u0005E\u0000\u0000pq\u0005R\u0000\u0000qr\u0005T\u0000"+
		"\u0000r\u001e\u0001\u0000\u0000\u0000st\u0005I\u0000\u0000tu\u0005N\u0000"+
		"\u0000uv\u0005T\u0000\u0000vw\u0005O\u0000\u0000w \u0001\u0000\u0000\u0000"+
		"xy\u0005V\u0000\u0000yz\u0005A\u0000\u0000z{\u0005L\u0000\u0000{|\u0005"+
		"U\u0000\u0000|}\u0005E\u0000\u0000}~\u0005S\u0000\u0000~\"\u0001\u0000"+
		"\u0000\u0000\u007f\u0080\u0005U\u0000\u0000\u0080\u0081\u0005P\u0000\u0000"+
		"\u0081\u0082\u0005D\u0000\u0000\u0082\u0083\u0005A\u0000\u0000\u0083\u0084"+
		"\u0005T\u0000\u0000\u0084\u0085\u0005E\u0000\u0000\u0085$\u0001\u0000"+
		"\u0000\u0000\u0086\u0087\u0005S\u0000\u0000\u0087\u0088\u0005E\u0000\u0000"+
		"\u0088\u0089\u0005T\u0000\u0000\u0089&\u0001\u0000\u0000\u0000\u008a\u008b"+
		"\u0005C\u0000\u0000\u008b\u008c\u0005R\u0000\u0000\u008c\u008d\u0005E"+
		"\u0000\u0000\u008d\u008e\u0005A\u0000\u0000\u008e\u008f\u0005T\u0000\u0000"+
		"\u008f\u0090\u0005E\u0000\u0000\u0090(\u0001\u0000\u0000\u0000\u0091\u0092"+
		"\u0005T\u0000\u0000\u0092\u0093\u0005A\u0000\u0000\u0093\u0094\u0005B"+
		"\u0000\u0000\u0094\u0095\u0005L\u0000\u0000\u0095\u0096\u0005E\u0000\u0000"+
		"\u0096*\u0001\u0000\u0000\u0000\u0097\u0098\u0005J\u0000\u0000\u0098\u0099"+
		"\u0005O\u0000\u0000\u0099\u009a\u0005I\u0000\u0000\u009a\u009b\u0005N"+
		"\u0000\u0000\u009b,\u0001\u0000\u0000\u0000\u009c\u009d\u0005O\u0000\u0000"+
		"\u009d\u009e\u0005N\u0000\u0000\u009e.\u0001\u0000\u0000\u0000\u009f\u00a0"+
		"\u0005L\u0000\u0000\u00a0\u00a1\u0005I\u0000\u0000\u00a1\u00a2\u0005M"+
		"\u0000\u0000\u00a2\u00a3\u0005I\u0000\u0000\u00a3\u00a4\u0005T\u0000\u0000"+
		"\u00a40\u0001\u0000\u0000\u0000\u00a5\u00a6\u0005A\u0000\u0000\u00a6\u00a7"+
		"\u0005N\u0000\u0000\u00a7\u00a8\u0005D\u0000\u0000\u00a82\u0001\u0000"+
		"\u0000\u0000\u00a9\u00aa\u0005O\u0000\u0000\u00aa\u00ab\u0005R\u0000\u0000"+
		"\u00ab4\u0001\u0000\u0000\u0000\u00ac\u00ad\u0005I\u0000\u0000\u00ad\u00ae"+
		"\u0005N\u0000\u0000\u00ae\u00af\u0005T\u0000\u0000\u00af6\u0001\u0000"+
		"\u0000\u0000\u00b0\u00b1\u0005V\u0000\u0000\u00b1\u00b2\u0005A\u0000\u0000"+
		"\u00b2\u00b3\u0005R\u0000\u0000\u00b3\u00b4\u0005C\u0000\u0000\u00b4\u00b5"+
		"\u0005H\u0000\u0000\u00b5\u00b6\u0005A\u0000\u0000\u00b6\u00b7\u0005R"+
		"\u0000\u0000\u00b78\u0001\u0000\u0000\u0000\u00b8\u00bc\u0007\u0000\u0000"+
		"\u0000\u00b9\u00bb\u0007\u0001\u0000\u0000\u00ba\u00b9\u0001\u0000\u0000"+
		"\u0000\u00bb\u00be\u0001\u0000\u0000\u0000\u00bc\u00ba\u0001\u0000\u0000"+
		"\u0000\u00bc\u00bd\u0001\u0000\u0000\u0000\u00bd:\u0001\u0000\u0000\u0000"+
		"\u00be\u00bc\u0001\u0000\u0000\u0000\u00bf\u00c1\u0007\u0002\u0000\u0000"+
		"\u00c0\u00bf\u0001\u0000\u0000\u0000\u00c1\u00c2\u0001\u0000\u0000\u0000"+
		"\u00c2\u00c0\u0001\u0000\u0000\u0000\u00c2\u00c3\u0001\u0000\u0000\u0000"+
		"\u00c3<\u0001\u0000\u0000\u0000\u00c4\u00c8\u0005\'\u0000\u0000\u00c5"+
		"\u00c7\t\u0000\u0000\u0000\u00c6\u00c5\u0001\u0000\u0000\u0000\u00c7\u00ca"+
		"\u0001\u0000\u0000\u0000\u00c8\u00c9\u0001\u0000\u0000\u0000\u00c8\u00c6"+
		"\u0001\u0000\u0000\u0000\u00c9\u00cb\u0001\u0000\u0000\u0000\u00ca\u00c8"+
		"\u0001\u0000\u0000\u0000\u00cb\u00cc\u0005\'\u0000\u0000\u00cc>\u0001"+
		"\u0000\u0000\u0000\u00cd\u00cf\u0007\u0003\u0000\u0000\u00ce\u00cd\u0001"+
		"\u0000\u0000\u0000\u00cf\u00d0\u0001\u0000\u0000\u0000\u00d0\u00ce\u0001"+
		"\u0000\u0000\u0000\u00d0\u00d1\u0001\u0000\u0000\u0000\u00d1\u00d2\u0001"+
		"\u0000\u0000\u0000\u00d2\u00d3\u0006\u001f\u0000\u0000\u00d3@\u0001\u0000"+
		"\u0000\u0000\u0005\u0000\u00bc\u00c2\u00c8\u00d0\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}