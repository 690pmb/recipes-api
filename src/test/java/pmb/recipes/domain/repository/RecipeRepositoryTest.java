package pmb.recipes.domain.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import pmb.recipes.domain.model.Difficulty;
import pmb.recipes.domain.model.Nutriscore;
import pmb.recipes.domain.model.Recipe;
import pmb.recipes.domain.model.Season;

@DataJpaTest
class RecipeRepositoryTest {
  @Autowired RecipeRepository recipeRepository;

  @Test
  @Sql(
      statements = {
        "INSERT INTO recipe (id, title, description, person_count, link, cooking_time, preparation_time, difficulty, nutriscore) VALUES (2, 'test', 'long description', 4,'https://test.com', 30, 40, 'HARD', 'A')",
        "INSERT INTO season (recipe, name) VALUES (2, 'SPRING')",
        "INSERT INTO season (recipe, name) VALUES (2, 'WINTER')"
      })
  void findById() {
    Optional<Recipe> recipe = recipeRepository.findById(2L);
    assertTrue(recipe.isPresent());
    Recipe actual = recipe.get();
    assertAll(
        () -> assertEquals(2L, actual.getId()),
        () -> assertEquals("test", actual.getTitle()),
        () -> assertEquals("long description", actual.getDescription()),
        () -> assertEquals(4, actual.getPersonCount()),
        () -> assertEquals("https://test.com", actual.getLink()),
        () -> assertEquals(30, actual.getCookingTime()),
        () -> assertEquals(40, actual.getPreparationTime()),
        () -> assertEquals(Difficulty.HARD, actual.getDifficulty()),
        () -> assertEquals(Nutriscore.A, actual.getNutriscore()),
        () -> assertEquals(2, actual.getSeasons().size()),
        () -> assertEquals(Season.SPRING, actual.getSeasons().get(0)),
        () -> assertEquals(Season.WINTER, actual.getSeasons().get(1)));
  }
}
