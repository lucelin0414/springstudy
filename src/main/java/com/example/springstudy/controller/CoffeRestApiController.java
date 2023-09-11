package com.example.springstudy.controller;

import com.example.springstudy.domain.Coffee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coffees")
public class CoffeRestApiController {
    private List<Coffee> coffees = new ArrayList<>();

    public CoffeRestApiController(){
        coffees.addAll(Arrays.asList(
                new Coffee("Cafe Cereza"),
                new Coffee("Cafe Alpha"),
                new Coffee("Cafe Beta"),
                new Coffee("Cafe Delta")
        ));
    }

    @GetMapping
    Iterable<Coffee> getCoffees(){
      return coffees;
    };

    @GetMapping(value = "/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable String id){
        for (Coffee c: coffees) {
            if(c.getId().equals(id)){
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    @PostMapping
    Coffee postCoffee(@RequestBody Coffee coffee){
        coffees.add(coffee);
        return coffee;
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee){
        int coffeeIndex = -1;

        for (Coffee c : coffees) {
            if (c.getId().equals(id)){
                coffeeIndex = coffees.indexOf(c);
                coffees.set(coffeeIndex, coffee);
            }
        }

//        return (coffeeIndex == -1) ? postCoffee(coffee) : coffee;
        return (coffeeIndex == -1) ? new ResponseEntity<>(postCoffee(coffee), HttpStatus.CREATED) : new ResponseEntity<>(coffee, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    void deleteCoffee(@PathVariable String id){
        coffees.removeIf(c -> c.getId().equals(id)); // 람다식 ==> c : 매개변수 && c.getId().equals(id) : 실행문
    }
}
