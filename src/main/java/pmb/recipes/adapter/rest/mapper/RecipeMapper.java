package pmb.recipes.adapter.rest.mapper;

import org.mapstruct.Mapper;
import pmb.recipes.adapter.rest.dto.RecipeDto;
import pmb.recipes.domain.model.Recipe;

@Mapper
public interface RecipeMapper extends EntityDtoMapper<Recipe, RecipeDto> {}
