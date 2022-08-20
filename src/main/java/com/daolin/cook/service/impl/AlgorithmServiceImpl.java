package com.daolin.cook.service.impl;

import com.daolin.cook.service.AlgorithmService;
import org.springframework.stereotype.Service;

@Service
public class AlgorithmServiceImpl implements AlgorithmService {
    @Override
    public boolean strictMatch(String str1, String str2) {
        return str1.equals(str2);
    }

    @Override
    public boolean notStringMatch(String str1, String str2) {
        //Str1 is the name of the recipe
        //str2 is the name of query
        String[] s = str2.split(" ");
        for (int i = 0; i < s.length; i++) {
            if(str1.contains(s[i])){
                return true;
            }
        }
        return false;
    }
}
