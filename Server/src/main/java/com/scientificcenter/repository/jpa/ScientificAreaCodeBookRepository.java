package com.scientificcenter.repository.jpa;

import com.scientificcenter.model.journal.ScientificAreaCodeBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScientificAreaCodeBookRepository extends JpaRepository<ScientificAreaCodeBook, Long> {

    @Query(value = "SELECT sacb.id \n" +
            "FROM   scientific_journal_area sja \n" +
            "       LEFT JOIN scientific_area_code_book sacb \n" +
            "              ON sja.area_id = sacb.id \n" +
            "WHERE  sja.scientific_journal_id = :id", nativeQuery = true)
    List<Long> getScientificAreasForJournal(@Param("id") Long id);
}
