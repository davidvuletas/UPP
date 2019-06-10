package com.scientificcenter.service.impl;


import com.scientificcenter.model.journal.ScientificJournal;
import com.scientificcenter.model.journal.ScientificJournalElastic;
import com.scientificcenter.repository.elastic.JournalRepostioryElastic;
import com.scientificcenter.repository.jpa.JournalRepository;
import com.scientificcenter.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalServiceImpl implements JournalService {

    private final JournalRepostioryElastic journalRepostioryElastic;

    private final JournalRepository journalRepository;

    @Autowired
    public JournalServiceImpl(JournalRepostioryElastic journalRepostioryElastic, JournalRepository journalRepository) {
        this.journalRepostioryElastic = journalRepostioryElastic;
        this.journalRepository = journalRepository;
    }

    @Override
    public ScientificJournalElastic save(ScientificJournalElastic journal) {
        return journalRepostioryElastic.save(journal);
    }

    @Override
    public ScientificJournal save(ScientificJournal journal) {
        return this.journalRepository.save(journal);
    }

    @Override
    public List<ScientificJournal> getAllJournals() {
        return this.journalRepository.findAll();
    }

    @Override
    public ScientificJournal findJournalByIdAndName(Long id, String name) {
        return this.journalRepository.findScientificJournalByIdAndName(id, name);
    }

    @Override
    public ScientificJournal findJournalById(Long id) {
        return this.journalRepository.findScientificJournalById(id);
    }

    @Override
    public ScientificJournal findJournalByPaper(Long id) {
        return this.journalRepository.findScientificJournalByPaper(id);
    }
}
