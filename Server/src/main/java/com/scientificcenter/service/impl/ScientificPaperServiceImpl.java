package com.scientificcenter.service.impl;

import com.scientificcenter.model.dto.entity.AuthorDto;
import com.scientificcenter.model.dto.entity.PaperForUploadDto;
import com.scientificcenter.model.paper.ScientificPaper;
import com.scientificcenter.model.users.Author;
import com.scientificcenter.model.users.CoAuthor;
import com.scientificcenter.repository.jpa.ScientificAreaCodeBookRepository;
import com.scientificcenter.repository.jpa.ScientificPaperRepository;
import com.scientificcenter.service.ScientificPaperService;
import com.scientificcenter.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScientificPaperServiceImpl implements ScientificPaperService {

    private final ScientificPaperRepository scientificPaperRepository;
    private final UserService userService;
    private final ScientificAreaCodeBookRepository areaCodeBookRepository;
    public ScientificPaperServiceImpl(ScientificPaperRepository scientificPaperRepository, UserService userService, ScientificAreaCodeBookRepository areaCodeBookRepository) {
        this.scientificPaperRepository = scientificPaperRepository;
        this.userService = userService;
        this.areaCodeBookRepository = areaCodeBookRepository;
    }

    @Override
    public ScientificPaper getPaperByName(String name) {
        return this.scientificPaperRepository.findScientificPaperByTitle(name);
    }

    @Override
    public ScientificPaper getPaperById(Long id) {
        return this.scientificPaperRepository.getOne(id);
    }

    @Override
    public ScientificPaper uploadPaperDtoToEntity(PaperForUploadDto dto) {
        ScientificPaper paper = new ScientificPaper();
        paper.setCoAuthors(new ArrayList<>());
        List<AuthorDto> authorDtoList = dto.getCoauthors();

        authorDtoList.forEach(authorDto -> {
            if (authorDto.isPrimary()) {
                paper.getCoAuthors().add(CoAuthor.builder()
                        .author(Author.builder().user(userService.findUserById(Long.valueOf(authorDto.getId()))).build())
                        .build());
                paper.setMainAuthor(Author.builder().user(userService.
                        findUserById(Long.valueOf(authorDto.getId()))).build());
            } else {
                paper.getCoAuthors().add(CoAuthor.builder().name(authorDto.getName().split(" ")[0])
                        .lastname(authorDto.getName().split(" ")[1]).city(authorDto.getCity()).country(authorDto.getCity())
                        .email(authorDto.getEmail()).build());
            }
        });
        paper.setAbstractOfPaper(dto.getAbstractOfPaper());
        paper.setKeywords(dto.getKeywords());
        paper.setTitle(dto.getTitle());
        paper.setScientificArea(areaCodeBookRepository.getOne(Long.valueOf(dto.getScientificArea())));
        return paper;
    }

    @Override
    public ScientificPaper savePaper(ScientificPaper scientificPaper) {
        return this.scientificPaperRepository.save(scientificPaper);
    }
}

