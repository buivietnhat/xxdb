// Generated from /home/nhatbui/workspace/database_system/xxdb/app/src/main/java/dev/xxdb/parser/antlr/Sql.g4 by ANTLR 4.13.2
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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, SELECT=8, FROM=9, 
		WHERE=10, INSERT=11, INTO=12, VALUES=13, UPDATE=14, SET=15, CREATE=16, 
		TABLE=17, INT_TYPE=18, VARCHAR_TYPE=19, IDENTIFIER=20, NUMBER=21, STRING=22, 
		WS=23;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "SELECT", "FROM", 
			"WHERE", "INSERT", "INTO", "VALUES", "UPDATE", "SET", "CREATE", "TABLE", 
			"INT_TYPE", "VARCHAR_TYPE", "IDENTIFIER", "NUMBER", "STRING", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "'('", "')'", "','", "'='", "'>'", "'<'", "'SELECT'", "'FROM'", 
			"'WHERE'", "'INSERT'", "'INTO'", "'VALUES'", "'UPDATE'", "'SET'", "'CREATE'", 
			"'TABLE'", "'INT'", "'VARCHAR'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, "SELECT", "FROM", "WHERE", 
			"INSERT", "INTO", "VALUES", "UPDATE", "SET", "CREATE", "TABLE", "INT_TYPE", 
			"VARCHAR_TYPE", "IDENTIFIER", "NUMBER", "STRING", "WS"
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
		"\u0004\u0000\u0017\u00a2\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0005\u0013\u0089"+
		"\b\u0013\n\u0013\f\u0013\u008c\t\u0013\u0001\u0014\u0004\u0014\u008f\b"+
		"\u0014\u000b\u0014\f\u0014\u0090\u0001\u0015\u0001\u0015\u0005\u0015\u0095"+
		"\b\u0015\n\u0015\f\u0015\u0098\t\u0015\u0001\u0015\u0001\u0015\u0001\u0016"+
		"\u0004\u0016\u009d\b\u0016\u000b\u0016\f\u0016\u009e\u0001\u0016\u0001"+
		"\u0016\u0001\u0096\u0000\u0017\u0001\u0001\u0003\u0002\u0005\u0003\u0007"+
		"\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b"+
		"\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013"+
		"\'\u0014)\u0015+\u0016-\u0017\u0001\u0000\u0004\u0003\u0000AZ__az\u0004"+
		"\u000009AZ__az\u0001\u000009\u0003\u0000\t\n\r\r  \u00a5\u0000\u0001\u0001"+
		"\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001"+
		"\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000"+
		"\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000"+
		"\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000"+
		"\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000"+
		"\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000"+
		"\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000"+
		"\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000"+
		"\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'"+
		"\u0001\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000\u0000+\u0001\u0000"+
		"\u0000\u0000\u0000-\u0001\u0000\u0000\u0000\u0001/\u0001\u0000\u0000\u0000"+
		"\u00031\u0001\u0000\u0000\u0000\u00053\u0001\u0000\u0000\u0000\u00075"+
		"\u0001\u0000\u0000\u0000\t7\u0001\u0000\u0000\u0000\u000b9\u0001\u0000"+
		"\u0000\u0000\r;\u0001\u0000\u0000\u0000\u000f=\u0001\u0000\u0000\u0000"+
		"\u0011D\u0001\u0000\u0000\u0000\u0013I\u0001\u0000\u0000\u0000\u0015O"+
		"\u0001\u0000\u0000\u0000\u0017V\u0001\u0000\u0000\u0000\u0019[\u0001\u0000"+
		"\u0000\u0000\u001bb\u0001\u0000\u0000\u0000\u001di\u0001\u0000\u0000\u0000"+
		"\u001fm\u0001\u0000\u0000\u0000!t\u0001\u0000\u0000\u0000#z\u0001\u0000"+
		"\u0000\u0000%~\u0001\u0000\u0000\u0000\'\u0086\u0001\u0000\u0000\u0000"+
		")\u008e\u0001\u0000\u0000\u0000+\u0092\u0001\u0000\u0000\u0000-\u009c"+
		"\u0001\u0000\u0000\u0000/0\u0005;\u0000\u00000\u0002\u0001\u0000\u0000"+
		"\u000012\u0005(\u0000\u00002\u0004\u0001\u0000\u0000\u000034\u0005)\u0000"+
		"\u00004\u0006\u0001\u0000\u0000\u000056\u0005,\u0000\u00006\b\u0001\u0000"+
		"\u0000\u000078\u0005=\u0000\u00008\n\u0001\u0000\u0000\u00009:\u0005>"+
		"\u0000\u0000:\f\u0001\u0000\u0000\u0000;<\u0005<\u0000\u0000<\u000e\u0001"+
		"\u0000\u0000\u0000=>\u0005S\u0000\u0000>?\u0005E\u0000\u0000?@\u0005L"+
		"\u0000\u0000@A\u0005E\u0000\u0000AB\u0005C\u0000\u0000BC\u0005T\u0000"+
		"\u0000C\u0010\u0001\u0000\u0000\u0000DE\u0005F\u0000\u0000EF\u0005R\u0000"+
		"\u0000FG\u0005O\u0000\u0000GH\u0005M\u0000\u0000H\u0012\u0001\u0000\u0000"+
		"\u0000IJ\u0005W\u0000\u0000JK\u0005H\u0000\u0000KL\u0005E\u0000\u0000"+
		"LM\u0005R\u0000\u0000MN\u0005E\u0000\u0000N\u0014\u0001\u0000\u0000\u0000"+
		"OP\u0005I\u0000\u0000PQ\u0005N\u0000\u0000QR\u0005S\u0000\u0000RS\u0005"+
		"E\u0000\u0000ST\u0005R\u0000\u0000TU\u0005T\u0000\u0000U\u0016\u0001\u0000"+
		"\u0000\u0000VW\u0005I\u0000\u0000WX\u0005N\u0000\u0000XY\u0005T\u0000"+
		"\u0000YZ\u0005O\u0000\u0000Z\u0018\u0001\u0000\u0000\u0000[\\\u0005V\u0000"+
		"\u0000\\]\u0005A\u0000\u0000]^\u0005L\u0000\u0000^_\u0005U\u0000\u0000"+
		"_`\u0005E\u0000\u0000`a\u0005S\u0000\u0000a\u001a\u0001\u0000\u0000\u0000"+
		"bc\u0005U\u0000\u0000cd\u0005P\u0000\u0000de\u0005D\u0000\u0000ef\u0005"+
		"A\u0000\u0000fg\u0005T\u0000\u0000gh\u0005E\u0000\u0000h\u001c\u0001\u0000"+
		"\u0000\u0000ij\u0005S\u0000\u0000jk\u0005E\u0000\u0000kl\u0005T\u0000"+
		"\u0000l\u001e\u0001\u0000\u0000\u0000mn\u0005C\u0000\u0000no\u0005R\u0000"+
		"\u0000op\u0005E\u0000\u0000pq\u0005A\u0000\u0000qr\u0005T\u0000\u0000"+
		"rs\u0005E\u0000\u0000s \u0001\u0000\u0000\u0000tu\u0005T\u0000\u0000u"+
		"v\u0005A\u0000\u0000vw\u0005B\u0000\u0000wx\u0005L\u0000\u0000xy\u0005"+
		"E\u0000\u0000y\"\u0001\u0000\u0000\u0000z{\u0005I\u0000\u0000{|\u0005"+
		"N\u0000\u0000|}\u0005T\u0000\u0000}$\u0001\u0000\u0000\u0000~\u007f\u0005"+
		"V\u0000\u0000\u007f\u0080\u0005A\u0000\u0000\u0080\u0081\u0005R\u0000"+
		"\u0000\u0081\u0082\u0005C\u0000\u0000\u0082\u0083\u0005H\u0000\u0000\u0083"+
		"\u0084\u0005A\u0000\u0000\u0084\u0085\u0005R\u0000\u0000\u0085&\u0001"+
		"\u0000\u0000\u0000\u0086\u008a\u0007\u0000\u0000\u0000\u0087\u0089\u0007"+
		"\u0001\u0000\u0000\u0088\u0087\u0001\u0000\u0000\u0000\u0089\u008c\u0001"+
		"\u0000\u0000\u0000\u008a\u0088\u0001\u0000\u0000\u0000\u008a\u008b\u0001"+
		"\u0000\u0000\u0000\u008b(\u0001\u0000\u0000\u0000\u008c\u008a\u0001\u0000"+
		"\u0000\u0000\u008d\u008f\u0007\u0002\u0000\u0000\u008e\u008d\u0001\u0000"+
		"\u0000\u0000\u008f\u0090\u0001\u0000\u0000\u0000\u0090\u008e\u0001\u0000"+
		"\u0000\u0000\u0090\u0091\u0001\u0000\u0000\u0000\u0091*\u0001\u0000\u0000"+
		"\u0000\u0092\u0096\u0005\'\u0000\u0000\u0093\u0095\t\u0000\u0000\u0000"+
		"\u0094\u0093\u0001\u0000\u0000\u0000\u0095\u0098\u0001\u0000\u0000\u0000"+
		"\u0096\u0097\u0001\u0000\u0000\u0000\u0096\u0094\u0001\u0000\u0000\u0000"+
		"\u0097\u0099\u0001\u0000\u0000\u0000\u0098\u0096\u0001\u0000\u0000\u0000"+
		"\u0099\u009a\u0005\'\u0000\u0000\u009a,\u0001\u0000\u0000\u0000\u009b"+
		"\u009d\u0007\u0003\u0000\u0000\u009c\u009b\u0001\u0000\u0000\u0000\u009d"+
		"\u009e\u0001\u0000\u0000\u0000\u009e\u009c\u0001\u0000\u0000\u0000\u009e"+
		"\u009f\u0001\u0000\u0000\u0000\u009f\u00a0\u0001\u0000\u0000\u0000\u00a0"+
		"\u00a1\u0006\u0016\u0000\u0000\u00a1.\u0001\u0000\u0000\u0000\u0005\u0000"+
		"\u008a\u0090\u0096\u009e\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}