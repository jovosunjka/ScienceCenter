/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jovo.ScienceCenter.elasticsearch_plugin.serbian_analyzer.filter;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

/**
 *
 * @author Milan Deket
 * @author Marko Martonosi (update to es7.4)
 */
public class RemoveAccentsFilter extends TokenFilter {
    private CharTermAttribute termAttribute;

    public RemoveAccentsFilter(TokenStream input) {
        super(input);
        termAttribute = input.addAttribute(CharTermAttribute.class);
    }


    @Override
    public boolean incrementToken() throws IOException {
        if (input.incrementToken()) {
            String text = termAttribute.toString();
            termAttribute.setEmpty();
            termAttribute.append(LatCyrFilterUtils.removeAccents(text).replace("Dj", "D").replace("dj", "d"));
            return true;
        }
        return false;
    }
}