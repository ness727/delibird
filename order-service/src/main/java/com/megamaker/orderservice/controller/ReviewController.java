package com.megamaker.orderservice.controller;

import com.megamaker.orderservice.dto.review.ResponseReview;
import com.megamaker.orderservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/reviews")
@RequiredArgsConstructor
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{storeId}")
    public List<ResponseReview> getReviewList(@PathVariable Long storeId) {
        return reviewService.getReviewList(storeId);
    }
}
