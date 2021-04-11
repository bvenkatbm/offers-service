package com.kognitiv_.assignment.utils;

import com.kognitiv_.assignment.domain.Photo;
import com.kognitiv_.assignment.exception.InternalServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.stream.LongStream;

@Component
@Slf4j
public class Utility {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${photos.base.url}")
    private String photosBaseURL;

    public List<String> getImageURLList() {
        List<Photo> photosList = new ArrayList<>();
        try {
            photosList = restTemplate.exchange(photosBaseURL, HttpMethod.GET, null, new ParameterizedTypeReference<List<Photo>>(){}).getBody();
        } catch (RestClientException e) {
            log.error("Unable to get photos: {}", e.toString());
            //todo: if mandatory throw exception
            //throw new InternalServerException("Please try later");
            //considering not mandatory executing this by logging error
        }

        List<String> imageURLList = null;
        if (photosList != null && !photosList.isEmpty()) {
            Random random = new Random();
            imageURLList = new ArrayList<>();
            PrimitiveIterator.OfLong iterator = random.longs(5,1, 5001).sorted().iterator();
            int i = 1;
            while(iterator.hasNext()) {
                long id = iterator.nextLong();
                for (; i <= 5000; i++) {
                    if(id == photosList.get(i-1).getId()) {
                        imageURLList.add(photosList.get(i-1).getUrl());
                        break;
                    }
                }
            }
        }
        return imageURLList;
    }
}
