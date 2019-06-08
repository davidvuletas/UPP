package com.scientificcenter.repository.jpa;

import com.scientificcenter.model.journal.ScientificJournal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<ScientificJournal, Long> {

    ScientificJournal findScientificJournalById(Long id);
    ScientificJournal findScientificJournalByIdAndName(Long id, String name);
}
