package com.dmytronazarenko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by ������� on 31.05.2017.
 */
public class Clusterizator {

    int num;
    int textsCount;
    int[] clusters;
    ArrayList<HashMap<String, Integer>> texts;
    ArrayList<HashMap<String, Integer>> centroids;

    public Clusterizator(int num, ArrayList<HashMap<String, Integer>> texts){
        this.texts = texts;
        this.num = num;
        this.textsCount = texts.size();
        clusters = new int[textsCount];
        for (int i = 0; i < textsCount; i++) {
            clusters[i] = -1;
        }
        centroids = new ArrayList<HashMap<String, Integer>>();
    }


    public int[] go(){
        init();
        for (int i = 0; i < 10; i++) {
            clusterize();
            findCentroids();
        }
        return clusters;
    }


    private void init(){
        boolean[] mas = new boolean[texts.size()];
        int rand = 0;
        //int rand =(int)(Math.random()*textsCount);
        clusters[rand] = 0;
        HashMap<String, Integer> h = texts.get(rand);
        centroids.add(h);
        long[] minlen = new long[textsCount];
        int maxx = 0;
        for (int i = 0; i <textsCount ; i++) {
            minlen[i] = ProbabilityMatrix.findDistance(h, texts.get(i));
            if (minlen[i] > minlen[maxx]) maxx = i;
        }
        for (int i = 1; i < num; i++) {
            h = texts.get(maxx);
            centroids.add(h);
            clusters[maxx] = i;
            maxx = 0;
            for (int j = 0; j < textsCount; j++) {
                minlen[j] = Math.min(ProbabilityMatrix.findDistance(h, texts.get(j)), minlen[j]);
                if (minlen[j] > minlen[maxx]){
                    maxx = j;
                }
            }
/*            int j = (int)(Math.random()*textsCount);
            while(mas[j] != false) j = (int)(Math.random()*textsCount);
            clusters[j] = i;
            mas[j] = true;
            centroids.add(texts.get(j));*/
        }
    }

    public void clusterize(){

        for (int i = 0; i < textsCount; i++) {
            long minn = -1;
            int pos = 0;
            HashMap<String,Integer> textVector = texts.get(i);
            for (int j = 0; j < num; j++) {
                long len = ProbabilityMatrix.findDistance(textVector, centroids.get(j));
                if (minn == -1){
                    minn = len;
                    pos = j;
                } else if (minn > len){
                    minn = len;
                    pos = j;
                }
            }
            clusters[i] = pos;
        }
    }


    public void findCentroids(){
        int[] count = new int[num];
        ArrayList<HashMap<String, Integer>> newcentroids = new ArrayList<HashMap<String, Integer>>();
        for (int i = 0; i < num; ++i) {
            newcentroids.add(new HashMap<String, Integer>());
        }
        for (int i = 0; i < textsCount; i++) {
            HashMap<String,Integer> textVector = texts.get(i);
            for (Map.Entry<String, Integer> e : textVector.entrySet()) {
                Integer frequency = newcentroids.get(clusters[i]).get(e.getKey());
                newcentroids.get(clusters[i]).put(e.getKey(), frequency == null ? e.getValue() : frequency + e.getValue());
            }
            count[clusters[i]]++;
        }
        for (int i = 0; i < num; i++) {
            if (count[i] == 0) continue;
            for (Map.Entry<String, Integer> e : newcentroids.get(i).entrySet()) {
                e.setValue((int)Math.ceil(e.getValue()/(double)count[i]));
            }
        }
        centroids = newcentroids;
    }
}
