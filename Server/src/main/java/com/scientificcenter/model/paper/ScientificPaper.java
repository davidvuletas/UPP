package com.scientificcenter.model.paper;

import com.scientificcenter.model.enums.Status;
import com.scientificcenter.model.journal.ScientificAreaCodeBook;
import com.scientificcenter.model.users.Author;
import com.scientificcenter.model.users.CoAuthor;
import com.scientificcenter.model.users.Reviewer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
@Entity
public class ScientificPaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String title;

    @Column
    private String keywords;

    @Column(columnDefinition = "TEXT")
    private String abstractOfPaper;

    @OneToOne(cascade = CascadeType.ALL)
    private Author mainAuthor;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CoAuthor> coAuthors;

    @OneToOne
    private ScientificAreaCodeBook scientificArea;

    @Column
    private String pathToPDF;

    @OneToMany
    private List<Review> reviews;

    @OneToMany
    List<Reviewer> reviewers;

    @Column
    private String doi;

    @Enumerated(EnumType.STRING)
    private Status status;

}
