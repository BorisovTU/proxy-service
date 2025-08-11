package ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationResponse {
    private long total;
    private int page;
    private int per_page;
    private int total_pages;
    private String next_cursor;
    private boolean has_next;
}
