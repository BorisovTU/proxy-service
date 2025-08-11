package ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class CorporateActionRequestDTO {
    private String isin;
    private String cftid;
    private String accdepo;
    private String reference;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private LocalDate recordDate;
    private String cursor;
    private int limit = 50;
}
