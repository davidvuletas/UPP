package com.scientificcenter.model.journal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Document(indexName = "scientificcenter", type = "journal", shards = 1, replicas = 0)
public class ScientificJournalElastic {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String text;

    @Pattern(regexp = "\\d{4}-\\d{3}[\\dxX]")
    @Field(type = FieldType.Text)
    private String issnNumber;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<ScientificAreaCodeBook> area;

    @Field(type = FieldType.Boolean)
    private boolean published;

    //.....

}
