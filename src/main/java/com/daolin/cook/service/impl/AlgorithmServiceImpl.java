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
        return str1.contains(str2);
    }
}
