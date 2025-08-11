package ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.entity.ClientAccEntity;

public interface ClientAccRepository extends JpaRepository<ClientAccEntity, String> {
}