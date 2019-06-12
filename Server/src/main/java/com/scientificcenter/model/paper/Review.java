package com.scientificcenter.model.paper;

import com.scientificcenter.model.enums.Suggestion;
import com.scientificcenter.model.users.Reviewer;
import com.scientificcenter.model.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    Suggestion suggestionForAccept;

    @Column
    String commentForAuthor;

    @Column
    String commentForEditor;

    @ManyToOne
    Reviewer reviewer;
}
