package com.kognitiv_.assignment.repository;

import com.kognitiv_.assignment.domain.Photo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "photos", url = "${photos.base.url}")
public interface PhotoRepository {

    @GetMapping("/")
    List<Photo> getPhotos();

    @GetMapping("/")
    List<Photo> getPhotosUsingPageable(Pageable pageable);
}
