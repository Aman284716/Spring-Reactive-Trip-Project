package com.ust.Spring_Reactive.Repository;

import com.ust.Spring_Reactive.dto.Tripdto;
import com.ust.Spring_Reactive.entity.Trip;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.Optional;

public interface Triprepo extends ReactiveMongoRepository<Trip,String> {
    Flux<Trip> findByPriceBetween(int minPrice, int maxPrice);

    Flux<Trip> findByRatingBetween(int minrate, int maxrate);
}
