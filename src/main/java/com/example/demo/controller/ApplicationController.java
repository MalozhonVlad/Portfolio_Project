package com.example.demo.controller;

import com.example.demo.domain.Message;
import com.example.demo.domain.User;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class ApplicationController {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;


    @GetMapping("/greeting")
    public String greetingPage(@AuthenticationPrincipal User user,
                               Model model) {
        model.addAttribute("user", user);
        return "greeting";
    }

    @GetMapping("/messenger")
    public String messenger(Model model) {

        Iterable<Message> messages = messageRepository.findAll();

        uploadFotoFromDb(messages);

        model.addAttribute("messages", messages);

        return "messenger";
    }

    @PostMapping("/filter")
    public String filter(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model) {
        Iterable<Message> messageByTag;

        if (filter != null && !filter.isEmpty()) {
            messageByTag = messageRepository.findByTag(filter);
        } else {
            messageByTag = messageRepository.findAll();
        }

        uploadFotoFromDb(messageByTag);

        model.addAttribute("messages", messageByTag);

        return "messenger";
    }

    @PostMapping("/messenger")
    public String messengerPost(@AuthenticationPrincipal User user,
                                @Valid Message message,
                                BindingResult bindingResult,
                                Model model,
                                @RequestParam("file") MultipartFile file) throws IOException {
        List<Message> messageList = new ArrayList<>();

        if (bindingResult.hasErrors()) {

            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            saveFileToDB(message, file);

            messageList.add(message);
            user.setMessageList(messageList);

            message.setAuthor(user);
            messageRepository.save(message);
        }

        Iterable<Message> messages = messageRepository.findAll();

        uploadFotoFromDb(messages);


        model.addAttribute("messages", messages);

        return "messenger";
    }

    private void saveFileToDB(@Valid Message message,
                              @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            Byte[] fileToLoadBytes = new Byte[file.getBytes().length];
            int count = 0;
            for (byte b : file.getBytes()) fileToLoadBytes[count++] = b;

            message.setBytes(fileToLoadBytes);
        }
    }

    @GetMapping("/coolFoto")
    public String coolFoto() {
        return "coolFoto";
    }

    @GetMapping("/mountains")
    public String mountains() {
        return "mountains";
    }

    @GetMapping("/mainMessanger")
    public String mainMessanger(@AuthenticationPrincipal User user,
                                Model model) {
        model.addAttribute("user", user);

        return "mainMessanger";
    }

    @GetMapping("/user-messages/{user}")
    public String userMessagesss(@AuthenticationPrincipal User currentUser,
                                 @PathVariable User user,
                                 Model model,
                                 @RequestParam(required = false) Message message) {

        List<Message> messageList = user.getMessageList();

        Set<Message> messagesSet = new HashSet<>();

        for (Message message1 : messageList) {
            messagesSet.add(message1);
        }

        uploadFotoFromDb(messagesSet);



        model.addAttribute("messages", messagesSet);
        model.addAttribute("message", message);
        model.addAttribute("isCurrentUser", currentUser.equals(user));

        return "userMessages";
    }

    @PostMapping("/user-messages/{user}")
    public String updateMessage(@AuthenticationPrincipal User currentUser,
                                @PathVariable Long user,
                                @RequestParam("id") Message message,
                                @RequestParam("text") String text,
                                @RequestParam("tag") String tag,
                                @RequestParam("file") MultipartFile file,
                                Model model) throws IOException {


        if (message.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(text)) {
                message.setText(text);
            } else {
                model.addAttribute("text", "Text cannot be empty");
                return "redirect:/user-messages/" + user;
            }

            if (!StringUtils.isEmpty(tag)) {
                message.setTag(tag);
            }


            if (file != null && !file.getOriginalFilename().isEmpty()) {
                saveFileToDB(message, file);
            }


            messageRepository.save(message);
        }

        return "redirect:/user-messages/" + user;
    }

    @GetMapping("/deleteMessage/{messageId}")
    public String deleteMessage(@AuthenticationPrincipal User currentUser,
                                @PathVariable("messageId") String messageId) {

        long deleteLong = Long.valueOf(messageId);

        Message byId = messageRepository.findById(deleteLong).get();


        User byUsername = userRepository.findByUsername(currentUser.getUsername());

        List<Message> messageList = byUsername.getMessageList();

        if (messageList.contains(byId)) {
            byUsername.getMessageList().remove(byId);
            byUsername.setMessageList(messageList);
        }

        userRepository.save(byUsername);

        messageRepository.delete(byId);

        return "redirect:/user-messages/" + currentUser.getId();
//        return "allMessagesByUser";
    }

    @GetMapping("/allMessagesByUser")
    public String allMessagesByUser(@AuthenticationPrincipal User user,
                                    Model model) {

//        Iterable<Message> messages = messageRepository.findAll();

        List<Message> messages = user.getMessageList();

        uploadFotoFromDb(messages);

        model.addAttribute("messages", messages);

        return "allMessagesByUser";
    }


    private void uploadFotoFromDb(Iterable<Message> messages) {
        for (Message message1 : messages) {
            if (message1.getBytes() != null) {
                byte[] unloadBytes = new byte[message1.getBytes().length];
                int count = 0;
                for (byte b : message1.getBytes()) unloadBytes[count++] = b;
                byte[] imgBytesAsBase64 = Base64.encodeBase64(unloadBytes);
                String imgDataAsBase64 = new String(imgBytesAsBase64);
                String imgAsBase64 = "data:image/png;base64," + imgDataAsBase64;
                message1.setFilename(imgAsBase64);
            }
        }
    }

}
