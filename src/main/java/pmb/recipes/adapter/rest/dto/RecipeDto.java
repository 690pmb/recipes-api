package pmb.recipes.adapter.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;
import pmb.recipes.domain.model.Difficulty;
import pmb.recipes.domain.model.Nutriscore;
import pmb.recipes.domain.model.Season;

public record RecipeDto(
    @Null Long id,
    @Size(min = 1, max = 255) @NotNull String title,
    @NotNull Difficulty difficulty,
    @NotNull @Positive Integer preparationTime,
    @Positive Integer cookingTime,
    @NotNull Nutriscore nutriscore,
    List<Season> seasons,
    @NotNull @Positive Integer personCount,
    @Pattern(regexp = "https://.+\\..+") @Size(min = 11, max = 512) String link,
    @NotBlank String description) {}
