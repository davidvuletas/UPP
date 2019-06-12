package com.scientificcenter.repository.jpa;

import com.scientificcenter.model.users.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewerRepository extends JpaRepository<Reviewer, Long> {

    @Query(value = "SELECT rsj.reviewers_id \n" +
            "FROM   scientific_journal sj \n" +
            "       LEFT JOIN scientific_journal_reviewers rsj \n" +
            "              ON sj.id = rsj.scientific_journal_id \n" +
            "WHERE  sj.id = :journalId ", nativeQuery = true)
    List<Long>  findReviewersForJournal(@Param("journalId") Long journalId);

    @Query(value = "SELECT r.* \n" +
            "FROM   reviewer r \n" +
            "WHERE  id IN (SELECT rsa.reviewer_id \n" +
            "              FROM   reviewer_scientific_areas rsa \n" +
            "              WHERE  rsa.reviewer_id IN ( :ids ) \n" +
            "                     AND rsa.scientific_areas_id = :areaId)", nativeQuery = true)
    List<Reviewer> findReviewersByAreaId(@Param("ids") List<Long> ids, @Param("areaId") Long areaId);

    Reviewer findReviewerById(Long id);
    Reviewer findReviewerByUser_Id(Long id);
}
