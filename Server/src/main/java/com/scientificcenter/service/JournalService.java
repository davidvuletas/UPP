package com.scientificcenter.service;

import com.scientificcenter.model.journal.ScientificJournal;
import com.scientificcenter.model.journal.ScientificJournalElastic;
import com.scientificcenter.model.paper.ScientificPaper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JournalService {

    ScientificJournalElastic save(ScientificJournalElastic journal);

    ScientificJournal save(ScientificJournal journal);

    List<ScientificJournal> getAllJournals();

    ScientificJournal findJournalByIdAndName(Long id, String name);

    ScientificJournal findJournalById(Long id);

    ScientificJournal findJournalByPaper(Long id);

}
