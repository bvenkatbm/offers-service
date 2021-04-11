package com.kognitiv_.assignment.service;

import com.kognitiv_.assignment.domain.OfferRequest;
import com.kognitiv_.assignment.domain.Offer;
import com.kognitiv_.assignment.domain.OfferResponse;
import com.kognitiv_.assignment.domain.PostOfferResponse;
import com.kognitiv_.assignment.entity.OfferEntity;
import com.kognitiv_.assignment.exception.BadRequestException;
import com.kognitiv_.assignment.repository.OfferRepository;
import com.kognitiv_.assignment.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OffersService {

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    Utility utility;

    public PostOfferResponse addOffer(OfferRequest offerRequest) {
        PostOfferResponse response = new PostOfferResponse();
        validateRequest(offerRequest);
        OfferEntity offerEntity = new OfferEntity();
        mapOffer(offerEntity, offerRequest);
        setImages(offerEntity);
        try {
            offerRepository.save(offerEntity);
            response.setSuccess(true);
        } catch (Exception e) {
            //todo: throw exception (not clear)
            log.error("Unable to save in db because: {}", e.toString());
        }
        return response;
    }

    private void setImages(OfferEntity offerEntity) {
        offerEntity.setImages(utility.getImageURLList());
    }

    public Page<Offer> getOffers(String name, String location, LocalDate validFrom, LocalDate validTill, Pageable pageable) {
        Page<OfferEntity> offers;
        if (name != null || location != null || validFrom != null || validTill != null) {
            if(validFrom != null && validTill != null && validFrom.isAfter(validTill))
                throw new BadRequestException("Please provide proper dates");
            offers = offerRepository.findByParameters(name, location, validFrom, validTill, pageable);
        } else
            offers = offerRepository.findAll(pageable);
        return new PageImpl<>(getOffersList(offers), pageable, offers.getTotalElements());
    }

    private List<Offer> getOffersList(Page<OfferEntity> offers) {
        List<Offer> offerResponses = new ArrayList<>();
        offers.forEach(offerEntity -> {
            Offer offer = new Offer();
            offer.setName(offerEntity.getName());
            offer.setLocation(offerEntity.getLocation());
            offer.setValidFrom(offerEntity.getValidFrom());
            offer.setValidTill(offerEntity.getValidTill());
            offer.setImages(offerEntity.getImages());
            offerResponses.add(offer);
        });
        return offerResponses;
    }

    private void mapOffer(OfferEntity offerEntity, OfferRequest offerRequest) {
        offerEntity.setName(offerRequest.getName());
        offerEntity.setValidFrom(offerRequest.getValidFrom());
        offerEntity.setValidTill(offerRequest.getValidTill());
        offerEntity.setLocation(offerRequest.getLocation());
    }

    private void validateRequest(OfferRequest offerRequest) {
        if (offerRequest.getName() == null)
            throw new BadRequestException("Please provide a proper name");

        if(offerRequest.getValidFrom() == null && offerRequest.getValidTill() == null)
            throw new BadRequestException("Please provide dates");

        if(offerRequest.getValidFrom() == null)
            throw new BadRequestException("Please provide valid start date");

        if(offerRequest.getValidTill() == null)
            throw new BadRequestException("Please provide valid end date");

        if(offerRequest.getLocation() == null)
            throw new BadRequestException("Please provide a location");

        if(offerRequest.getValidFrom().compareTo(offerRequest.getValidTill()) > 0)
            throw new BadRequestException("Please provide proper valid from and till date");
    }
}
