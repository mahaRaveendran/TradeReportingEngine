package com.vanguard.trade.reporting.engine.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum XmlElements {

    BUYER_PARTY("buyer_party", "//buyerPartyReference/@href"),
    SELLER_PARTY("seller_party", "//sellerPartyReference/@href"),
    PREMIUM_AMOUNT("premium_amount", "//paymentAmount/amount"),
    PREMIUM_CURRENCY("premium_currency", "//paymentAmount/currency");

    @Getter
    public final String name;
    @Getter
    public final String xPathExpression;
}
