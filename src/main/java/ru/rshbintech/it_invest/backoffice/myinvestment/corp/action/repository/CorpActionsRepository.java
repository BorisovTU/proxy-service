package ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.dto.CorporateActionRequestDTO;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.entity.CorpActionsEntity;
import ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.entity.projection.ICorporateActionProjection;

public interface CorpActionsRepository extends JpaRepository<CorpActionsEntity, Long> {

    @Query(value = """
    SELECT 
        ca.caid AS caid,
        ca.catype AS catype,
        ca.reference AS reference,
        ca.swift_type AS swiftType,
        ca.ma_vo_code AS maVoCode,
        ca.date_reg_owners AS dateRegOwners,
        ca.isin AS isin,
        ca.date_start AS dateStart,
        ca.date_end AS dateEnd
    FROM corp_actions ca
    LEFT JOIN link l ON ca.caid = l.caid
    LEFT JOIN client_acc c ON l.acc_depo = c.acc_depo
    WHERE 
        (cast(:#{#req.isin} as text) IS NULL OR ca.isin = cast(:#{#req.isin} as text)) AND
        (cast(:#{#req.cftid} as text) IS NULL OR c.cftid::text = cast(:#{#req.cftid} as text)) AND
        (cast(:#{#req.accdepo} as text) IS NULL OR c.acc_depo = cast(:#{#req.accdepo} as text)) AND
        (cast(:#{#req.reference} as text) IS NULL OR ca.reference::text = cast(:#{#req.reference} as text)) AND
        (cast(:#{#req.dateStart} as date) IS NULL OR ca.date_start >= cast(:#{#req.dateStart} as date)) AND
        (cast(:#{#req.dateEnd} as date) IS NULL OR ca.date_end <= cast(:#{#req.dateEnd} as date)) AND
        (cast(:#{#req.recordDate} as date) IS NULL OR ca.date_reg_owners = cast(:#{#req.recordDate} as date)) AND
        (cast(:#{#req.cursor} as bigint) IS NULL OR ca.caid > cast(:#{#req.cursor} as bigint))
    ORDER BY ca.caid ASC
    LIMIT cast(:#{#req.limit} as integer)
    """,
            nativeQuery = true)
    List<ICorporateActionProjection> findCorporateActionsNative(
            @Param("req") CorporateActionRequestDTO requestDTO);

}