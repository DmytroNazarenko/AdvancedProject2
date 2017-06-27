package com.dmytronazarenko;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Spliter implements Iterable<String>{

    Matcher matcher;

    public Spliter(String text) {
        //String text = Part1.readFile(fileName);
        matcher = Pattern.compile("[A-Za-zÀ-ßà-ÿ¨¸²³ªº¯¿]+").matcher(text);
    }
    private class Itr implements Iterator<String> {

        public boolean hasNext() {
            return matcher.find();
        }

        public String next() {
            return matcher.group();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    @Override
    public Iterator<String> iterator() {
        return new Itr();
    }
}
