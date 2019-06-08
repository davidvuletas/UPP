package com.scientificcenter.model.dto.entity;

import com.scientificcenter.model.users.Author;
import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PaperForUploadDto {

    private String title;
    private String keywords;
    private String abstractOfPaper;
    private String scientificArea;
    private List<AuthorDto> coauthors;


}
