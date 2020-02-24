package com.jovo.ScienceCenter.elasticsearch_plugin.serbian_analyzer;

import com.jovo.ScienceCenter.elasticsearch_plugin.serbian_analyzer.filter.LatCyrFilter;
import com.jovo.ScienceCenter.elasticsearch_plugin.serbian_analyzer.filter.RemoveAccentsFilter;
import com.jovo.ScienceCenter.elasticsearch_plugin.serbian_analyzer.stemmer.SerbianStemmer;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import java.util.Arrays;
import java.util.List;

public final class SerbianAnalyzer extends StopwordAnalyzerBase {

    public static final CharArraySet SERBIAN_STOP_WORDS_SET;

    static {
        final List<String> serbianStopWords = Arrays.asList(
                "biti", "ne",
                "jesam", "sam", "jesi", "si", "je", "jesmo", "smo", "jeste", "ste", "jesu", "su",
                "nijesam", "nisam", "nijesi", "nisi", "nije", "nijesmo", "nismo", "nijeste", "niste", "nijesu", "nisu",
                "budem", "budeš", "bude", "budemo", "budete", "budu",
                "budes",
                "bih", "bi", "bismo", "biste", "biše",
                "bise",
                "bio", "bili", "budimo", "budite", "bila", "bilo", "bile",
                "ću", "ćeš", "će", "ćemo", "ćete",
                "neću", "nećeš", "neće", "nećemo", "nećete",
                "cu", "ces", "ce", "cemo", "cete",
                "necu", "neces", "nece", "necemo", "necete",
                "mogu", "možeš", "može", "možemo", "možete",
                "mozes", "moze", "mozemo", "mozete"
        );
        final CharArraySet stopSet = new CharArraySet(serbianStopWords, false);
        SERBIAN_STOP_WORDS_SET = CharArraySet.unmodifiableSet(stopSet);
    }

    private final CharArraySet stemExclusionSet;

    public static CharArraySet getDefaultStopSet() {
        return SERBIAN_STOP_WORDS_SET;
    }


    public SerbianAnalyzer() {
        this(SERBIAN_STOP_WORDS_SET);
    }

    public SerbianAnalyzer(CharArraySet stopwords) {
        this(stopwords, CharArraySet.EMPTY_SET);
    }

    public SerbianAnalyzer(CharArraySet stopwords, CharArraySet stemExclusionSet) {
        super(stopwords);
        this.stemExclusionSet = CharArraySet.unmodifiableSet(CharArraySet.copy(stemExclusionSet));
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer source = new StandardTokenizer();
        TokenStream result = new LowerCaseFilter(source);
        result = new LatCyrFilter(result);
        result = new StopFilter(result, stopwords);
        result = new SnowballFilter(result, new SerbianStemmer());
        result = new RemoveAccentsFilter(result);
        return new TokenStreamComponents(source, result);
    }

    @Override
    protected TokenStream normalize(String fieldName, TokenStream in) {
        return new LowerCaseFilter(in);
    }
}
