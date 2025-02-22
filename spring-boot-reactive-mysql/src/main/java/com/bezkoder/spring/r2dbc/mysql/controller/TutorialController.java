package com.bezkoder.spring.r2dbc.mysql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.spring.r2dbc.mysql.model.Tutorial;
import com.bezkoder.spring.r2dbc.mysql.service.TutorialService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
@Tag(name = "Tutorial API", description = "Operations related to tutorials")
public class TutorialController {

  @Autowired
  TutorialService tutorialService;

  @Operation(summary = "Get all tutorials", description = "Retrieve all tutorials or filter by title")
  @GetMapping("/tutorials")
  @ResponseStatus(HttpStatus.OK)
  public Flux<Tutorial> getAllTutorials(@RequestParam(required = false) String title) {
    return (title == null) ? tutorialService.findAll() : tutorialService.findByTitleContaining(title);
  }

  @Operation(summary = "Get tutorial by ID", description = "Retrieve a tutorial by its ID")
  @GetMapping("/tutorials/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Tutorial> getTutorialById(@PathVariable("id") int id) {
    return tutorialService.findById(id);
  }

  @Operation(summary = "Create a tutorial", description = "Add a new tutorial")
  @PostMapping("/tutorials")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
    return tutorialService.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
  }

  @Operation(summary = "Update a tutorial", description = "Update an existing tutorial by ID")
  @PutMapping("/tutorials/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Tutorial> updateTutorial(@PathVariable("id") int id, @RequestBody Tutorial tutorial) {
    return tutorialService.update(id, tutorial);
  }

  @Operation(summary = "Delete tutorial by ID", description = "Remove a tutorial by its ID")
  @DeleteMapping("/tutorials/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteTutorial(@PathVariable("id") int id) {
    return tutorialService.deleteById(id);
  }

  @Operation(summary = "Delete all tutorials", description = "Remove all tutorials from the database")
  @DeleteMapping("/tutorials")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteAllTutorials() {
    return tutorialService.deleteAll();
  }

  @Operation(summary = "Find published tutorials", description = "Retrieve tutorials that are marked as published")
  @GetMapping("/tutorials/published")
  @ResponseStatus(HttpStatus.OK)
  public Flux<Tutorial> findByPublished() {
    return tutorialService.findByPublished(true);
  }
}
