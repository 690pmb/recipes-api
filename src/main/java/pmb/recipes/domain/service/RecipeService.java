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

  public RecipeDto save(RecipeDto recipeDto) {
    return recipeMapper.toDto(recipeRepository.save(recipeMapper.toEntity(recipeDto)));
  }

  public Optional<RecipeDto> edit(RecipeDto recipeDto) {
    return recipeRepository.findById(recipeDto.id()).map(r -> save(recipeDto));
  }

  public void delete(Long id) {
    recipeRepository.deleteById(id);
  }
}
