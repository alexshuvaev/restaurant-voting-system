package com.alexshuvaev.topjava.gp.repository;

import com.alexshuvaev.topjava.gp.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    /**
     * Delete a Restaurant to the data store by id.
     * @param id of <code>Restaurant</code>
     * @return int 1 if successful deleted, int 0 if <code>Restaurant</code> with provided id was not found.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    /**
     *
     * @param id of Restaurant.
     * @param today LocalDate.now()
     * @return Restaurant and Dishes if they was found, and return Empty if not.
     */
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.dishes d WHERE r.id = :id AND d.date = :today")
    Optional<Restaurant> getRestaurantWithDishesForToday(@Param("id") Integer id, @Param("today") LocalDate today);

}
