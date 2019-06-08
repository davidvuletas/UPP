package com.scientificcenter.model.journal;

import com.scientificcenter.model.enums.PaymentMethod;
import com.scientificcenter.model.paper.ScientificPaper;
import com.scientificcenter.model.users.EditorialBoard;
import com.scientificcenter.model.users.Reviewer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
@RequiredArgsConstructor
@Entity
public class ScientificJournal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Pattern(regexp = "\\d{4}-\\d{3}[\\dxX]")
    @Column
    private String issnNumber;

    @Column
    @OneToMany(cascade = CascadeType.ALL)
    private List<ScientificAreaCodeBook> area;

    @Column
    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToOne
    private EditorialBoard board;

    @OneToMany
    private List<Reviewer> reviewers;

    @OneToMany
    private List<ScientificPaper> papers;

}
