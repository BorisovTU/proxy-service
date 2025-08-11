package ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "link")
@Getter
@Setter
public class LinkEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "caid", nullable = false)
    private Long caid;

    @Column(name = "acc_depo", nullable = false)
    private String accDepo;

    @Column(name = "client_dia_id", nullable = false)
    private Long clientDiaId;
}
