package pmb.recipes.adapter.rest.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pmb.recipes.adapter.rest.dto.OnCreate;
import pmb.recipes.adapter.rest.dto.OnEdit;
import pmb.recipes.adapter.rest.dto.RecipeDto;
import pmb.recipes.adapter.rest.exception.NotFoundException;
import pmb.recipes.domain.service.RecipeService;

@Validated
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

  @PostMapping
  @Validated(OnCreate.class)
  @ResponseStatus(HttpStatus.CREATED)
  public RecipeDto create(@RequestBody @Valid RecipeDto recipeDto) {
    return recipeService.save(recipeDto);
  }

  @PutMapping
  @Validated(OnEdit.class)
  public RecipeDto edit(@RequestBody @Valid RecipeDto recipeDto) {
    return recipeService
        .edit(recipeDto)
        .orElseThrow(
            () ->
                new NotFoundException(
                    String.format("Recipe with id '%s' not found", recipeDto.id())));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    recipeService.delete(id);
  }
}
