package com.example.demo.controller;

import com.example.demo.domain.Message;
import com.example.demo.domain.User;
import com.example.demo.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ApplicationController {

    private final MessageRepository messageRepository;


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
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                Byte[] fileToLoadBytes = new Byte[file.getBytes().length];
                int count = 0;
                for (byte b : file.getBytes()) fileToLoadBytes[count++] = b;

                message.setBytes(fileToLoadBytes);
            }

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

    @GetMapping("/coolFoto")
    public String coolFoto() {
        return "coolFoto";
    }

    @GetMapping("/mainMessanger")
    public String mainMessanger(@AuthenticationPrincipal User user,
                                Model model) {
        model.addAttribute("user", user);

        return "mainMessanger";
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
