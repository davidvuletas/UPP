package com.scientificcenter.service.impl;

import com.scientificcenter.model.journal.ScientificJournal;
import com.scientificcenter.model.paper.ScientificPaper;
import com.scientificcenter.model.users.Reviewer;
import com.scientificcenter.repository.jpa.ReviewerRepository;
import com.scientificcenter.service.JournalService;
import com.scientificcenter.service.ReviewerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewerServiceImpl implements ReviewerService {

    private final ReviewerRepository reviewerRepository;
    private final JournalService journalService;

    public ReviewerServiceImpl(ReviewerRepository reviewerRepository, JournalService journalService) {
        this.reviewerRepository = reviewerRepository;
        this.journalService = journalService;
    }

    @Override
    public List<Reviewer> getAllReviewersForScientificAreaAndPaper(ScientificPaper paper) {
        ScientificJournal journal = this.journalService.findJournalByPaper(paper.getId());
        List<Long> allReviewersForJournal = this.reviewerRepository.findReviewersForJournal(journal.getId());
        return this.reviewerRepository.findReviewersByAreaId(allReviewersForJournal, paper.getScientificArea().getId());
    }

    @Override
    public Reviewer findReviewerById(Long id) {
        return this.reviewerRepository.findReviewerById(id);
    }
}
