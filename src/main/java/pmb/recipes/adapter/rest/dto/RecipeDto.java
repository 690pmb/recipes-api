package pmb.recipes.adapter.rest.dto;

import java.util.List;
import pmb.recipes.domain.model.Difficulty;
import pmb.recipes.domain.model.Nutriscore;
import pmb.recipes.domain.model.Season;

public record RecipeDto(
    Long id,
    String title,
    Difficulty difficulty,
    Integer preparationTime,
    Integer cookingTime,
    Nutriscore nutriscore,
    List<Season> seasons,
    Integer personCount,
    String link,
    String description) {}
