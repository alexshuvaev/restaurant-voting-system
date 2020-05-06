package com.alexshuvaev.topjava.gp.repository;

import com.alexshuvaev.topjava.gp.domain.Dish;
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
public interface DishRepository extends JpaRepository<Dish, Integer> {
    /**
     * Retrieve list of Dishes from the data store.
     * @return stored Dishes between @param startDate and @param endDate
     */
    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant r WHERE d.date >= :startDate AND d.date <= :endDate")
    Optional<List<Dish>> getDishesBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * Retrieve list of Dish from the data store.
     * @return stored Dishes of single Restaurant between @param startDate and @param endDate,
     * or Empty if was not found.
     */
    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant r WHERE d.date >= :startDate AND d.date <= :endDate AND d.restaurant.id = :id")
    Optional<List<Dish>> getDishesBetweenSingleRestaurant(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("id") Integer id);
}
