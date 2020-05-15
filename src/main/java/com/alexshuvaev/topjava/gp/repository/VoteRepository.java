package com.alexshuvaev.topjava.gp.repository;

import com.alexshuvaev.topjava.gp.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    /**
     * Retrieve list of Users Votes from the data store between @param startDate and @param endDate
     * @param userId id of User
     * @return list of Votes or Empty, if was not found.
     */
    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant JOIN FETCH v.user u WHERE v.date >= :startDate AND v.date <= :endDate AND u.id= :userId ORDER BY v.date DESC, u.id")
    Optional<List<Vote>> getAll(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("userId") Integer userId);

    /**
     * FInd users Vote for today, if User already voted.
     * @param id of User.
     * @param date today
     * @return users Vote, or Empty if user not voted yet.
     */
    Optional<Vote> findByUserIdAndDate(Integer id, LocalDate date);
}
