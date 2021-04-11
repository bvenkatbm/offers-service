package com.kognitiv_.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OfferRequest {

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate validFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate validTill;

    private String location;
}