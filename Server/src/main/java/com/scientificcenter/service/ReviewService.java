package com.scientificcenter.service;

import com.scientificcenter.model.paper.Review;
import com.scientificcenter.model.paper.ScientificPaper;
import com.scientificcenter.model.users.Reviewer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {

    Review saveReview(Review review);

    List<Review> getAllReviewsForPaper(ScientificPaper paper);
}
