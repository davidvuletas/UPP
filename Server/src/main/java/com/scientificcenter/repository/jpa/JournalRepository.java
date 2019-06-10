package com.scientificcenter.repository.jpa;

import com.scientificcenter.model.journal.ScientificJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JournalRepository extends JpaRepository<ScientificJournal, Long> {

    ScientificJournal findScientificJournalById(Long id);
    ScientificJournal findScientificJournalByIdAndName(Long id, String name);

    @Query(value = "SELECT * \n" +
            "FROM   scientific_journal \n" +
            "WHERE  id = (SELECT sp.scientific_journal_id \n" +
            "             FROM   scientific_journal_papers sp \n" +
            "             WHERE  sp.papers_id = :id) ", nativeQuery = true)
    ScientificJournal findScientificJournalByPaper(@Param("id") Long id);

}
