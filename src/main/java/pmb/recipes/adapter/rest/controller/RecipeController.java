package pmb.recipes.adapter.rest.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pmb.recipes.adapter.rest.dto.RecipeDto;
import pmb.recipes.adapter.rest.exception.NotFoundException;
import pmb.recipes.domain.service.RecipeService;

@RestController
@RequestMapping(
    value = "/recipes",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeController {

  private final RecipeService recipeService;

  public RecipeController(final RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  @GetMapping("/{id}")
  public RecipeDto getById(@PathVariable Long id) {
    return recipeService
        .getById(id)
        .orElseThrow(
            () -> new NotFoundException(String.format("Recipe with id '%s' not found", id)));
  }
}