package com.kognitiv_.assignment.utils;

import com.kognitiv_.assignment.domain.OfferRequest;

import java.time.LocalDate;
import java.util.UUID;

public class TestUtils {
    public static OfferRequest getOfferRequest(LocalDate validFrom, LocalDate validTill) {
        OfferRequest offerRequest = new OfferRequest();
        offerRequest.setName(UUID.randomUUID().toString());
        offerRequest.setLocation("Karnataka");
        offerRequest.setValidFrom(validFrom);
        offerRequest.setValidTill(validTill);
        return offerRequest;
    }
}
