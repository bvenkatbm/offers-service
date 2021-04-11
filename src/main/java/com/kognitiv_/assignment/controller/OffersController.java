package com.kognitiv_.assignment.controller;

import com.kognitiv_.assignment.domain.Offer;
import com.kognitiv_.assignment.domain.OfferRequest;
import com.kognitiv_.assignment.domain.PostOfferResponse;
import com.kognitiv_.assignment.service.OffersService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

@Slf4j
@RestController
@Api(value = "Offers Service API",
        tags = {"Offers"})
//@RequestMapping("/v1")
public class OffersController {

    @Autowired
    private OffersService offersService;

    @ApiOperation(
            value = "View Offers",
            notes = "API returns available offers",
            response = Offer.class,
            responseContainer = "Page",
            authorizations ={@Authorization(value = "basicAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved offers"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
    })
    @GetMapping(value = "/collect/offer",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    @PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
    public Page<Offer> getOffers(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "location", required = false) String location,
                                 @RequestParam(value = "validFrom", required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate validFrom,
                                 @RequestParam(value = "validTill", required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate validTill,
                                 Pageable pageable) {
        return offersService.getOffers(name, location, validFrom, validTill, pageable);
    }

    @ApiOperation(
            value = "Add an Offer",
            notes = "API adds requested Offer",
            response = PostOfferResponse.class,
            authorizations ={@Authorization(value = "basicAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Added requested offer into the system"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @PostMapping(value = "/collect/offer", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<PostOfferResponse> postOffer(@RequestBody OfferRequest offerRequest) {
        return ResponseEntity.created(URI.create("/collect/offer")).body(offersService.addOffer(offerRequest));
    }
}
