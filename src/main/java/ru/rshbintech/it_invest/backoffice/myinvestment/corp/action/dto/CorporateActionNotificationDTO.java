package ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CorporateActionNotificationDTO {

    @JsonProperty("CorporateActionNotification")
    private CorporateActionNotification corporateActionNotification;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CorporateActionNotification {
        @JsonProperty("CorporateActionIssuerID")
        private String corporateActionIssuerID;

        @JsonProperty("CorporateActionType")
        private String corporateActionType;

        @JsonProperty("CorpActnEvtId")
        private String corpActnEvtId;

        @JsonProperty("EvtTp")
        private String evtTp;

        @JsonProperty("MndtryVlntryEvtTp")
        private String mndtryVlntryEvtTp;

        @JsonProperty("RcrdDt")
        private String rcrdDt;

        @JsonProperty("OrgNm")
        private String orgNm;

        @JsonProperty("SfkpgAcct")
        private String sfkpgAcct;

        @JsonProperty("FinInstrmId")
        private FinInstrmId finInstrmId;

        @JsonProperty("AddtlInf")
        private String addtlInf;

        @JsonProperty("LwsInPlcCd")
        private String lwsInPlcCd;

        @JsonProperty("SbrdntLwsInPlcCd")
        private String sbrdntLwsInPlcCd;

        @JsonProperty("CorpActnOptnDtls")
        private List<CorpActnOptnDtls> corpActnOptnDtls;

        @JsonProperty("BnfclOwnrDtls")
        private List<BnfclOwnrDtls> bnfclOwnrDtls;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FinInstrmId {
        @JsonProperty("ISIN")
        private String isin;

        @JsonProperty("RegNumber")
        private String regNumber;

        @JsonProperty("NSDR")
        private String nsdr;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CorpActnOptnDtls {
        @JsonProperty("OptnNb")
        private String optnNb;

        @JsonProperty("OptnTp")
        private String optnTp;

        @JsonProperty("DfltOptnInd")
        private String dfltOptnInd;

        @JsonProperty("PricVal")
        private String pricVal;

        @JsonProperty("PricValCcy")
        private String pricValCcy;

        @JsonProperty("ActnPrd")
        private ActnPrd actnPrd;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ActnPrd {
        @JsonProperty("StartDt")
        private String startDt;

        @JsonProperty("EndDt")
        private String endDt;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BnfclOwnrDtls {
        @JsonProperty("OwnerSecurityID")
        private String ownerSecurityID;

        @JsonProperty("CFTID")
        private String cftid;

        @JsonProperty("Acct")
        private String acct;

        @JsonProperty("SubAcct")
        private String subAcct;

        @JsonProperty("Bal")
        private String bal;
    }
}
