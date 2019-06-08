package com.scientificcenter.model.journal;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Builder
@Data
@RequiredArgsConstructor
@Entity
public class ScientificAreaCodeBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String area;

    @Column
    private String subArea;

    @Pattern(regexp = "[A-Z]{2}-[A-Z]{2}")
    @Column
    private String acronym;

}
