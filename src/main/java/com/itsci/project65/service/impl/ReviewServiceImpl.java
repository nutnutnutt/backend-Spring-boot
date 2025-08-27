package com.itsci.project65.service.impl;

import com.itsci.project65.model.Review;
import com.itsci.project65.repository.ReviewRepository;
import com.itsci.project65.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Review review) {
        Optional<Review> existingReview = reviewRepository.findById(review.getReviewId());
        if (existingReview.isPresent()) {
            return reviewRepository.save(review);
        } else {
            throw new RuntimeException("ไม่พบรีวิวที่ต้องการแก้ไข (ID: " + review.getReviewId() + ")");
        }
    }

    @Override
    public Review getReviewById(int reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("ไม่พบรีวิวที่ต้องการค้นหา (ID: " + reviewId + ")"));
    }

    @Override
    public void deleteReview(int reviewId) {
        Optional<Review> existingReview = reviewRepository.findById(reviewId);
        if (existingReview.isPresent()) {
            reviewRepository.delete(existingReview.get());
        } else {
            throw new RuntimeException("ไม่พบรีวิวที่ต้องการลบ (ID: " + reviewId + ")");
        }
    }
}
