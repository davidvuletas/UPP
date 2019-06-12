package com.scientificcenter.repository.jpa;

import com.scientificcenter.model.paper.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT r.* \n" +
            "FROM   review r \n" +
            "WHERE  r.id IN (SELECT spr.reviews_id \n" +
            "                FROM   scientific_paper_reviews spr \n" +
            "                WHERE  spr.scientific_paper_id = :id); ", nativeQuery = true)
    List<Review> findReviewsByPaperId(@Param("id") Long id);
}
