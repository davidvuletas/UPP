package com.scientificcenter.service;

import com.scientificcenter.model.dto.entity.PaperForUploadDto;
import com.scientificcenter.model.paper.ScientificPaper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScientificPaperService{

    ScientificPaper getPaperByName(String name);
    ScientificPaper getPaperById(Long id);
    ScientificPaper savePaper(ScientificPaper scientificPaper);
    ScientificPaper uploadPaperDtoToEntity(PaperForUploadDto dto);
    List<ScientificPaper> getAllPapersForEditor(Long id);
}
