package com.daolin.cook.service.impl;


import com.daolin.cook.model.Message;
import com.daolin.cook.model.MessageRepository;
import com.daolin.cook.model.User;
import com.daolin.cook.model.UserRepository;
import com.daolin.cook.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Message getServiceById(Integer id) {
        return messageRepository.findById(id);
    }

    @Override
    public void sendMessageByUser(String receiverName, Message message) {
        User user = userRepository.findByName(receiverName);
        messageRepository.save(message);
        Set<Message> messageList = user.getMessageList();
        messageList.add(message);
        user.setMessageList(messageList);
        userRepository.save(user);
    }

    @Override
    public void addMessage(Message message) {
        messageRepository.save(message);
    }
}
