package com.aughma.nacarqeqia.repository;

import com.aughma.nacarqeqia.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactUsRepository extends JpaRepository<Message, Long> {

}
