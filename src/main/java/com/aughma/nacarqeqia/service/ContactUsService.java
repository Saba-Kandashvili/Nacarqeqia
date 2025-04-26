package com.aughma.nacarqeqia.service;

import com.aughma.nacarqeqia.entity.Message;
import com.aughma.nacarqeqia.model.ContactUs;
import com.aughma.nacarqeqia.repository.ContactUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ContactUsService {
    @Autowired private ContactUsRepository repository;
    @Transactional
    public Message save(ContactUs dto) {
        Message msg = new Message();
        msg.setName(dto.getName());
        msg.setEmail(dto.getEmail());
        msg.setMessage(dto.getMessage());
        msg.setSubmittedAt(LocalDateTime.now());
        return repository.save(msg);
    }
}
