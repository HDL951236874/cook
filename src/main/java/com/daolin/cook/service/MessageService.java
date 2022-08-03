package com.daolin.cook.service;

import com.daolin.cook.model.Message;

import java.util.Optional;

public interface MessageService {
    Message getServiceById(Integer id);

    void sendMessageByUser(String receiverName, Message message);

    void addMessage(Message message);
}
