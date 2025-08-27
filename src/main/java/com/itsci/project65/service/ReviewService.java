package com.itsci.project65.service;

import com.itsci.project65.model.Review;

public interface ReviewService {
    public Review createReview(Review review);
    public Review updateReview(Review review);
    public Review getReviewById(int reviewId);
    public void deleteReview(int reviewId);
}
