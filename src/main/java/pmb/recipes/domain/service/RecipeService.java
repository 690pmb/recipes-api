package pmb.recipes.domain.service;

import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import pmb.recipes.adapter.rest.dto.RecipeDto;
import pmb.recipes.adapter.rest.dto.SearchRecipeDto;
import pmb.recipes.adapter.rest.mapper.RecipeMapper;
import pmb.recipes.domain.model.Recipe;
import pmb.recipes.domain.repository.RecipeRepository;
import pmb.recipes.domain.specification.RecipeSpecification;

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

  public Page<RecipeDto> search(SearchRecipeDto search, Pageable pageable) {
    Specification<Recipe> spec = Specification.where(null);
    if (search.cookingTimeMax() != null && search.cookingTimeMin() != null) {
      spec = spec
          .and(RecipeSpecification.betweenTime(search.cookingTimeMin(), search.cookingTimeMax(), "cookingTime"));
    }
    if (search.preparationTimeMax() != null && search.preparationTimeMin() != null) {
      spec = spec
          .and(RecipeSpecification.betweenTime(search.preparationTimeMin(), search.preparationTimeMax(),
              "preparationTime"));
    }
    if (StringUtils.isNotBlank(search.title())) {
      spec = spec
          .and(RecipeSpecification.containsTitle(search.title()));
    }
    if (search.season() != null) {
      spec = spec
          .and(RecipeSpecification.isSeason(search.season()));
    }
    if (CollectionUtils.isNotEmpty(search.difficulties())) {
      spec = spec
          .and(RecipeSpecification.in(search.difficulties(), "difficulty"));
    }
    if (CollectionUtils.isNotEmpty(search.nutriscores())) {
      spec = spec
          .and(RecipeSpecification.in(search.nutriscores(), "nutriscore"));
    }
    return recipeMapper.toDtoPage(recipeRepository.findAll(spec, pageable));
  }
}
