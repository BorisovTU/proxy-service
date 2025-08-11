package ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "client_acc")
@Getter
@Setter
public class ClientAccEntity {
    @Id
    @Column(name = "acc_depo", nullable = false)
    private String accDepo;

    @Column(name = "cftid", nullable = false)
    private Long cftid;

    @Column(name = "sub_acc_depo")
    private String subAccDepo;
}
