package com.daolin.cook.service;

import com.daolin.cook.model.CostumeList;

public interface CostumeListService {

    void addCostumeListByUser(String name, CostumeList costumeList);

    void deleteListByUser(String name, String ListName);
}
