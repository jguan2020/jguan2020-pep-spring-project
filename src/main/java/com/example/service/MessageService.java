package com.example.service;
import com.example.repository.MessageRepository;

import com.example.entity.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message insert(Message message){
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public boolean existsById(String messageId){
        return messageRepository.existsById(Integer.parseInt(messageId));
    }

    public Message getMessageById(String messageId){
        return messageRepository.findById(Integer.parseInt(messageId)).get();
    }

    public void deleteMessageById(String messageId){
        messageRepository.deleteById(Integer.parseInt(messageId));
    }

    public void updateMessageById(String messageId, Message message){
        Message newMessage = messageRepository.findById(Integer.parseInt(messageId)).get();
        newMessage.setMessageText(message.getMessageText());
    }

    public List<Message> getAllMessagesById(String accountId){
        return messageRepository.findAllByPostedBy(Integer.parseInt(accountId));
    }
    

    
}
