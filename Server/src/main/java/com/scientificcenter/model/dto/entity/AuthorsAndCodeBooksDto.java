package com.scientificcenter.model.dto.entity;

import com.scientificcenter.model.journal.ScientificAreaCodeBook;
import com.scientificcenter.model.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthorsAndCodeBooksDto {

    private List<User> authors;
    private List<ScientificAreaCodeBook> codeBooks;
}
