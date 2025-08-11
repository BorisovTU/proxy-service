package ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.dto.CorporateActionRequestDTO;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.dto.CorporateActionResponse;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.service.CorporateActionService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/corporate-actions")
public class CorporateActionController {

    private final CorporateActionService corporateActionService;

    public CorporateActionController(CorporateActionService corporateActionService) {
        this.corporateActionService = corporateActionService;
    }

    @GetMapping
    public CorporateActionResponse getCorporateActions(
            @RequestParam(required = false) String isin,
            @RequestParam(required = false) String cftid,
            @RequestParam(required = false) String accdepo,
            @RequestParam(required = false) String reference,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date_start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date_end,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate record_date,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "50") int limit) {

        CorporateActionRequestDTO requestDTO = new CorporateActionRequestDTO();
        requestDTO.setIsin(isin);
        requestDTO.setCftid(cftid);
        requestDTO.setAccdepo(accdepo);
        requestDTO.setReference(reference);
        requestDTO.setDateStart(date_start);
        requestDTO.setDateEnd(date_end);
        requestDTO.setRecordDate(record_date);
        requestDTO.setCursor(cursor);
        requestDTO.setLimit(limit);

        return corporateActionService.findCorporateActions(requestDTO);
    }
}
