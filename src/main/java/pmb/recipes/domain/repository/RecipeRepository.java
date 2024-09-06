package pmb.recipes.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pmb.recipes.domain.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {}
