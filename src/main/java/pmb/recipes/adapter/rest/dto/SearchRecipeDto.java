package pmb.recipes.adapter.rest.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pmb.recipes.domain.model.Difficulty;
import pmb.recipes.domain.model.Nutriscore;
import pmb.recipes.domain.model.Season;

public record SearchRecipeDto(
        String title,
        List<@NotNull Difficulty> difficulties,
        @Positive Integer preparationTimeMin,
        @Positive Integer preparationTimeMax,
        @Positive Integer cookingTimeMin,
        @Positive Integer cookingTimeMax,
        List<@NotNull Nutriscore> nutriscores,
        Season season) {
}
