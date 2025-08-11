package ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.entity.ResultEntity;

public interface ResultRepository extends JpaRepository<ResultEntity, Long> {
}
