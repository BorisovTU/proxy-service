package ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CorporateActionResponse {
    private List<CorporateActionNotificationDTO> data;
    private PaginationResponse pagination;
}