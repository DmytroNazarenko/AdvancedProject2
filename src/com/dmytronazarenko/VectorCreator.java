package com.dmytronazarenko;
import org.apache.lucene.morphology.WrongCharaterException;
import org.apache.lucene.morphology.russian.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VectorCreator {

    static RussianMorphology russianMorphology;
    public static HashMap make(String text) throws IOException {
        russianMorphology = new RussianMorphology();
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        Matcher matcher = Pattern.compile("[А-Яа-яЁё]{4,}").matcher(text);
        int count = 0;
        while (matcher.find() && count < 100){
            String temp = matcher.group();
            temp = temp.toLowerCase();
            try {
                String word = russianMorphology.getNormalForms(temp).get(0);
                Integer frequency = hashMap.get(word);
                hashMap.put(word, frequency == null ? 1 : frequency + 1);
                count++;
            } catch (WrongCharaterException ex) {
            }
        }
        return hashMap;
    }

}
