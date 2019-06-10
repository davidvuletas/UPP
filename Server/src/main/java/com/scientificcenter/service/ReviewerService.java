package com.scientificcenter.service;

import com.scientificcenter.model.journal.ScientificAreaCodeBook;
import com.scientificcenter.model.paper.ScientificPaper;
import com.scientificcenter.model.users.Reviewer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewerService {
    List<Reviewer> getAllReviewersForScientificAreaAndPaper(ScientificPaper paper);
    Reviewer findReviewerById(Long id);
}
