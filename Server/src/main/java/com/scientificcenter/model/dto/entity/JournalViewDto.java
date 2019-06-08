package com.scientificcenter.model.dto.entity;

import com.scientificcenter.model.enums.PaymentMethod;
import com.scientificcenter.model.journal.ScientificAreaCodeBook;
import com.scientificcenter.model.journal.ScientificJournal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class JournalViewDto {

    private Long id;

    private String name;

    private String issnNumber;

    private List<ScientificAreaCodeBook> area;

    private PaymentMethod paymentMethod;

    public JournalViewDto(ScientificJournal journal) {
        this.area = journal.getArea();
        this.name = journal.getName();
        this.issnNumber = journal.getIssnNumber();
        this.paymentMethod = journal.getPaymentMethod();
        this.id = journal.getId();
    }

}
