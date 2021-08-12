package com.kognitiv_.assignment.utils;

import com.kognitiv_.assignment.domain.Photo;
import com.kognitiv_.assignment.repository.PhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;

@Component
@Slf4j
public class Utility {

    @Value("${photos.base.url}")
    private String photosBaseURL;

    @Autowired
    private PhotoRepository photoRepository;

    public List<String> getImageURLList() {
        List<Photo> photosList = new ArrayList<>();
        try {
            photosList = photoRepository.getPhotos();
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
