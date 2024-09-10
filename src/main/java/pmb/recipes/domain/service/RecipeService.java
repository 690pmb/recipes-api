package pmb.recipes.domain.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import pmb.recipes.adapter.rest.dto.RecipeDto;
import pmb.recipes.adapter.rest.mapper.RecipeMapper;
import pmb.recipes.domain.repository.RecipeRepository;

@Service
public class RecipeService {
  private final RecipeRepository recipeRepository;
  private final RecipeMapper recipeMapper;

  public RecipeService(RecipeRepository recipeRepository, RecipeMapper recipeMapper) {
    this.recipeRepository = recipeRepository;
    this.recipeMapper = recipeMapper;
  }

  public Optional<RecipeDto> getById(Long id) {
    return recipeRepository.findById(id).map(recipeMapper::toDto);
  }

  public RecipeDto create(RecipeDto recipeDto) {
    return recipeMapper.toDto(recipeRepository.save(recipeMapper.toEntity(recipeDto)));
  }
}
