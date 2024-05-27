package com.megamaker.orderservice.service;

import com.megamaker.orderservice.dto.review.ResponseReview;
import com.megamaker.orderservice.repository.ReviewRepository;
import com.megamaker.orderservice.util.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<ResponseReview> getReviewList(Long storeId) {
        return reviewRepository.findAllByStoreId(storeId).stream()
                .map(ReviewMapper.INSTANCE::toResponseReview)
                .toList();
    }
}
