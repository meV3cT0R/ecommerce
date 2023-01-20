package com.vector.shop.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRatingRepo extends JpaRepository<UserRating,Long>{
    
}
