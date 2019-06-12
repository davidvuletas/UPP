package com.scientificcenter.service.impl;

import com.scientificcenter.model.paper.Review;
import com.scientificcenter.model.paper.ScientificPaper;
import com.scientificcenter.repository.jpa.ReviewRepository;
import com.scientificcenter.service.ReviewService;
import org.springframework.stereotype.Service;

import java.awt.print.Paper;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review saveReview(Review review) {
       return this.reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReviewsForPaper(ScientificPaper paper) {
        return this.reviewRepository.findReviewsByPaperId(paper.getId());
    }


}
