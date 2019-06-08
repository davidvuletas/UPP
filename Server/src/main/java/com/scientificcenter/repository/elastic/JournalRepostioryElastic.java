package com.scientificcenter.repository.elastic;

import com.scientificcenter.model.journal.ScientificJournalElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepostioryElastic extends ElasticsearchRepository<ScientificJournalElastic, String> {
}
