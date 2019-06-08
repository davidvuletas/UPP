package com.scientificcenter.model.users;

import com.scientificcenter.model.journal.ScientificAreaCodeBook;
import com.scientificcenter.model.journal.ScientificJournal;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
@Entity
public class Editor{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String title;

    @OneToMany
    private List<ScientificAreaCodeBook> scientificAreas;

    @OneToOne
    private ScientificJournal scientificJournal;

    @OneToOne
    private User user;

}
