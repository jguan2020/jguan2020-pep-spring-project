package com.example.controller;
import com.example.entity.*;
import com.example.repository.AccountRepository;
import com.example.service.*;

import javafx.application.Application;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse.ResponseInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@Controller
public class SocialMediaController {
    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity registerAccount(@RequestBody Account account){
        if(account.getPassword().length()<4 || account.getUsername().equals("")){
            return ResponseEntity.status(400).body("Client Error");
        }
        else if(accountService.checkAccountExists(account.getUsername())){
            return ResponseEntity.status(409).body("Conflict");
        }
        else{
            return ResponseEntity.status(200).body(accountService.registerAccount(account));
        }
    }

    @PostMapping("/login")
    public ResponseEntity verifyAccount(@RequestBody Account account){
        if(accountService.verifyAccount(account)){
            return ResponseEntity.status(200).body(accountService.getAccountByCreds(account));
        }
        else{
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    @PostMapping("/messages")
    public ResponseEntity createMessage(@RequestBody Message message){
        if(message.getMessageText()=="" || message.getMessageText().length()>255){
            return ResponseEntity.status(400).body("Client Error");
        }
        else if(accountService.existsById(message.getPostedBy())){
            return ResponseEntity.status(200).body(messageService.insert(message));
        }
        return ResponseEntity.status(400).body("Client Error");
    }

    @GetMapping("/messages")
    public ResponseEntity retrievaAllMessage(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity retrievaMessageById(@PathVariable String messageId){
        if(messageService.existsById(messageId)){
            return ResponseEntity.status(200).body(messageService.getMessageById(messageId));
        }
        else{
            return ResponseEntity.status(200).body(null);
        }
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity deleteMessagesById(@PathVariable String messageId){
        if(messageService.existsById(messageId)){
            messageService.deleteMessageById(messageId);
            return ResponseEntity.status(200).body(1);
        }
        else{
            return ResponseEntity.status(200).body(null);
        }
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity updateMessagesById(@PathVariable String messageId, @RequestBody Message message){
        if(message.getMessageText().equals("") || message.getMessageText().length()>255){
            return ResponseEntity.status(400).body("Client Error");
        }
        else if(messageService.existsById(messageId)){
            messageService.updateMessageById(messageId, message);
            return ResponseEntity.status(200).body(1);
        }
        else{
            return ResponseEntity.status(400).body("Client Error");
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity retrievaAllMessagesById(@PathVariable String accountId){
        return ResponseEntity.status(200).body(messageService.getAllMessagesById(accountId));
    }
}
