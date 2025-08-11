package ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "corp_actions")
@Getter
@Setter
public class CorpActionsEntity {
    @Id
    @Column(name = "caid")
    private Long caid;

    @Column(name = "catype", nullable = false)
    private String caType;

    @Column(name = "reference", nullable = false)
    private String reference;

    @Column(name = "swift_type", nullable = false)
    private String swiftType;

    @Column(name = "ma_vo_code", nullable = false)
    private String maVoCode;

    @Column(name = "date_reg_owners")
    private LocalDate dateRegOwners;

    @Column(name = "isin", nullable = false)
    private String isin;

    @Column(name = "date_start")
    private LocalDate dateStart;

    @Column(name = "date_end")
    private LocalDate dateEnd;
}
