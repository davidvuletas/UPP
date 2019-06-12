package com.scientificcenter.model.users;

import com.scientificcenter.model.journal.ScientificAreaCodeBook;
import com.scientificcenter.model.journal.ScientificJournal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
@Entity
public class Reviewer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @ManyToMany
    private List<ScientificAreaCodeBook> scientificAreas;

    @OneToMany
    private List<ScientificJournal> scientificJournals;

    @OneToOne
    private User user;

}
