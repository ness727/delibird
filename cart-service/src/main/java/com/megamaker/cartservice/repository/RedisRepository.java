package com.megamaker.cartservice.repository;

import com.megamaker.cartservice.domain.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface RedisRepository extends CrudRepository<Cart, String> {
}
