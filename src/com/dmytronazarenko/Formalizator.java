package com.dmytronazarenko;

import org.apache.lucene.morphology.russian.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by Дмитрий on 18.05.2017.
 */
public class Formalizator {

    public Formalizator() throws IOException {
        //RussianAnalyzer rus = new RussianAnalyzer();
        RussianMorphology russianMorphology = new RussianMorphology();
        String str = "делаю";
        List<String> l = russianMorphology.getNormalForms(str);
        for (int i = 0; i < l.size(); i++) {
            System.out.println(l.get(i));
        }
    }
}
