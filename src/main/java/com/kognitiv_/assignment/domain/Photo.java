package com.kognitiv_.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Photo {

    private Long id;
    private Long albumId;
    private String title;
    private String url;
    private String thumbnailUrl;
}
