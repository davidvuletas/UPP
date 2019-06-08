package com.scientificcenter.repository.jpa;

import com.scientificcenter.model.paper.ScientificPaper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScientificPaperRepository extends JpaRepository<ScientificPaper, Long> {
    ScientificPaper findScientificPaperByTitle(String title);
}
