package com.scientificcenter.repository.jpa;

import com.scientificcenter.model.paper.ScientificPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScientificPaperRepository extends JpaRepository<ScientificPaper, Long> {
    ScientificPaper findScientificPaperByTitle(String title);

    @Query(value = "SELECT paper.* \n" +
            "FROM   scientific_paper paper \n" +
            "WHERE  id IN (SELECT papers_id \n" +
            "              FROM   (SELECT sj.id \n" +
            "                      FROM   scientific_journal sj \n" +
            "                             LEFT JOIN editorial_board_editors eb \n" +
            "                                    ON sj.board_id = eb.editorial_board_id \n" +
            "                      WHERE  eb.editors_id = :id)j \n" +
            "                     LEFT JOIN scientific_journal_papers sjp \n" +
            "                            ON sjp.scientific_journal_id = j.id) \n" +
            "       AND status = 'UPLOADED' ", nativeQuery = true)
    List<ScientificPaper> findPapersWhichAreUploadedForEditorId(@Param("id") Long id);
}
