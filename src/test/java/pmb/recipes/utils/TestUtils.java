package pmb.recipes.utils;

import static pmb.recipes.domain.model.Nutriscore.A;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.function.Function;
import org.springframework.test.web.servlet.ResultActions;
import pmb.recipes.adapter.rest.dto.RecipeDto;
import pmb.recipes.domain.model.Difficulty;
import pmb.recipes.domain.model.Nutriscore;
import pmb.recipes.domain.model.Recipe;
import pmb.recipes.domain.model.Season;

public class TestUtils {
  private TestUtils() {}

  public static Function<ResultActions, String> readResponse =
      result -> {
        try {
          return result.andReturn().getResponse().getContentAsString();
        } catch (UnsupportedEncodingException e) {
          throw new RuntimeException(e);
        }
      };

  public static Recipe buildRecipe() {
    final Recipe recipe = new Recipe();
    recipe.setTitle("title");
    recipe.setDescription("description");
    recipe.setNutriscore(A);
    recipe.setPreparationTime(5);
    recipe.setCookingTime(10);
    recipe.setPersonCount(4);
    recipe.setDifficulty(Difficulty.EASY);
    recipe.setSeasons(List.of(Season.SPRING, Season.SUMMER));
    recipe.setLink("link");
    return recipe;
  }

  public static RecipeDto buildRecipeDto(Long id) {
    return new RecipeDto(
        id,
        "name",
        Difficulty.HARD,
        5,
        10,
        Nutriscore.D,
        List.of(Season.AUTUMN, Season.SUMMER),
        4,
        "https://link.com",
        "description");
  }
}
