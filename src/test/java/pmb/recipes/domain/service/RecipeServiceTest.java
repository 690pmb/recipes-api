package pmb.recipes.domain.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static pmb.recipes.domain.model.Nutriscore.A;

import java.util.List;
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
    Recipe recipe = new Recipe();
    recipe.setId(56L);
    recipe.setTitle("title");
    recipe.setDescription("description");
    recipe.setNutriscore(A);
    recipe.setPreparationTime(5);
    recipe.setCookingTime(10);
    recipe.setPersonCount(4);
    recipe.setDifficulty(Difficulty.EASY);
    recipe.setSeasons(List.of(Season.SPRING, Season.SUMMER));
    recipe.setLink("link");

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
}
