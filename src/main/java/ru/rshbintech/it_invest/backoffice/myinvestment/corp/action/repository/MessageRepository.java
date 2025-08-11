package ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.entity.message.BaseMessage;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<BaseMessage, UUID> {
    List<BaseMessage> findByStatus(Long status);
}