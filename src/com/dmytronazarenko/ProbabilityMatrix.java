package com.dmytronazarenko;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Дмитрий on 31.05.2017.
 */
public class ProbabilityMatrix {

    static ArrayList<HashMap> textArray;
    long[][] matrix;

    public ProbabilityMatrix() {
        //matrix = new long[textArray.size()][textArray.size()];
        textArray = new ArrayList<HashMap>();
    }

    public void addString(String text) throws IOException {
        HashMap <String, Integer> hashMap = VectorCreator.make(text);
        textArray.add(hashMap);
    }

    public void createMatrix(){
        matrix = new long[textArray.size()][textArray.size()];
        for (int i = 0; i < textArray.size(); i++) {
            for (int j = i+1; j < textArray.size(); j++) {
                matrix[i][j] = findDistance(textArray.get(i),textArray.get(j));
                matrix[j][i] = matrix[i][j];
            }
        }
    }


    public static long findDistance(HashMap <String, Integer> hA, HashMap <String, Integer> hB){
        long result = 0, result2 = 0;
        //if (hA.size() < hB.size()) {
            for (Map.Entry<String, Integer> e : hA.entrySet()) {
                String key = e.getKey();
                if (hB.containsKey(key)) {
                    long pr = (e.getValue() - hB.get(key));
                    result += pr * pr;
                    //hB.remove(key);
                } else {
                    result += e.getValue() * e.getValue();
                }
            }
        //} else {
            for (Map.Entry<String, Integer> e : hB.entrySet()) {
                String key = e.getKey();
                if (hA.containsKey(key)) {
                    long pr = (e.getValue() - hA.get(key));
                    result2 += pr * pr;
                    //hB.remove(key);
                } else {
                    result2 += e.getValue() * e.getValue();
                }
            }
        //}
     /*   for (Map.Entry<String, Integer> e : hB.entrySet()) {
            if (!hA.containsKey(e.getKey())) {
                result += e.getValue() * e.getValue();
            }
        }*/
        return Math.min(result, result2);
    }
}
