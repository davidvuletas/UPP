package com.scientificcenter.model.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
public class EditorialBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @OneToOne
    private Editor chiefEditor;

    @OneToMany
    private Set<Editor> editors;
}
