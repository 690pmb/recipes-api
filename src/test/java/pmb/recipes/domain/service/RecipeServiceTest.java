package pmb.recipes.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static pmb.recipes.domain.model.Nutriscore.A;
import static pmb.recipes.utils.TestUtils.buildRecipe;
import static pmb.recipes.utils.TestUtils.buildRecipeDto;

import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import pmb.recipes.adapter.rest.dto.RecipeDto;
import pmb.recipes.adapter.rest.mapper.RecipeMapperImpl;
import pmb.recipes.domain.model.Difficulty;
import pmb.recipes.domain.model.Recipe;
import pmb.recipes.domain.model.Season;
import pmb.recipes.domain.repository.RecipeRepository;
import pmb.recipes.utils.ServiceTestRunner;

@ServiceTestRunner
@Import({RecipeService.class, RecipeRepository.class, RecipeMapperImpl.class})
class RecipeServiceTest {
  @MockBean private RecipeRepository recipeRepository;
  @Autowired private RecipeService recipeService;

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(recipeRepository);
  }

  @Test
  void getById() {
    Recipe recipe = buildRecipe();
    recipe.setId(56L);
    when(recipeRepository.findById(56L)).thenReturn(Optional.of(recipe));

    Optional<RecipeDto> result = recipeService.getById(56L);

    assertTrue(result.isPresent());
    RecipeDto actual = result.get();
    assertAll(
        () -> assertEquals(56L, actual.id()),
        () -> assertEquals("title", actual.title()),
        () -> assertEquals("description", actual.description()),
        () -> assertEquals(4, actual.personCount()),
        () -> assertEquals("link", actual.link()),
        () -> assertEquals(10, actual.cookingTime()),
        () -> assertEquals(A, actual.nutriscore()),
        () -> assertEquals(5, actual.preparationTime()),
        () -> assertEquals(Difficulty.EASY, actual.difficulty()),
        () -> assertEquals(2, actual.seasons().size()),
        () -> assertEquals(Season.SPRING, actual.seasons().get(0)),
        () -> assertEquals(Season.SUMMER, actual.seasons().get(1)));

    verify(recipeRepository).findById(56L);
  }

  @Test
  void create() {
    RecipeDto recipeDto = buildRecipeDto(null);
    when(recipeRepository.save(any())).thenAnswer(a -> a.getArgument(0));

    RecipeDto saved = recipeService.create(recipeDto);

    assertThat(saved).usingRecursiveComparison().isEqualTo(recipeDto);
    verify(recipeRepository).save(any());
  }

  @Test
  void delete() {
    doNothing().when(recipeRepository).deleteById(1L);
    recipeService.delete(1L);
    verify(recipeRepository).deleteById(1L);
  }
}
