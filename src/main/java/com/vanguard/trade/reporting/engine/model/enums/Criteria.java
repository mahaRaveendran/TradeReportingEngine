package com.vanguard.trade.reporting.engine.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Criteria {

    EMU_BANK("EMU_BANK", "AUD"),
    BISON_BANK("BISON_BANK", "USD");

    @Getter
    private String sellerParty;
    @Getter
    private String premiumCurrency;

}
