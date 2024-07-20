package com.ust.Spring_Reactive.service;

import com.ust.Spring_Reactive.Repository.Triprepo;
import com.ust.Spring_Reactive.dto.Tripdto;

import com.ust.Spring_Reactive.utils.AppUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TripService {
    @Autowired
    private Triprepo repo;


    public Mono<Tripdto> addTrip(Mono<Tripdto> tripdtomono) {
        return tripdtomono.map(AppUtlis::dtoToEntity)
                          .flatMap(repo::insert)
                .map(AppUtlis::entityToDto);
    }

    public Flux<Tripdto> getAllTrips() {
        return repo.findAll().map(AppUtlis::entityToDto);
    }

    public Mono<Tripdto> getTripById(String id) {
        return repo.findById(id).map(AppUtlis::entityToDto);
    }

    public Mono<Tripdto> updateTrip(String id, Mono<Tripdto> updatedTrip) {
        return updatedTrip.map(AppUtlis::dtoToEntity)
                          .flatMap(trip -> repo.findById(id).flatMap(trip1 -> {
                                  trip1.setName(trip.getName());
                                  trip1.setDestination(trip.getDestination());
                                  trip1.setDuration(trip.getDuration());
                                  trip1.setPrice(trip.getPrice());
                                  trip1.setRating(trip.getRating());
                                  trip1.setDescription(trip.getDescription());
                                  trip1.setStatus(trip.getStatus());
                                  return repo.save(trip1);
                          }))
                          .map(AppUtlis::entityToDto);
    }

    public Mono<Void> deleteTrip(String id) {
         return repo.deleteById(id);
    }

    public Flux<Tripdto> getTripsByPriceRange(int minPrice, int maxPrice) {
        return repo.findByPriceBetween(minPrice, maxPrice).map(AppUtlis::entityToDto);
    }

    public Flux<Tripdto> getTripsByRatingRange(int minrate, int maxrate) {
        return repo.findByRatingBetween(minrate, maxrate).map(AppUtlis::entityToDto);
    }
}
