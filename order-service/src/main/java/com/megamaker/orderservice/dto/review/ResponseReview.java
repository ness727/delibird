package com.megamaker.orderservice.dto.review;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseReview {
    private String review;
    private Byte rating;
//    private String image1;
//    private String image2;
//    private String image3;
    private String createdAt;
}
