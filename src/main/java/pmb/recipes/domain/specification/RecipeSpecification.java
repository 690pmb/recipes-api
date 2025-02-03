package pmb.recipes.domain.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import pmb.recipes.domain.model.Recipe;
import pmb.recipes.domain.model.Season;

public class RecipeSpecification {

    public static Specification<Recipe> containsTitle(String title) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("title")),
                "%" + title.toLowerCase() + "%");
    }

    public static Specification<Recipe> betweenTime(Integer min, Integer max, String field) {
        return (root, query, builder) -> builder.between(root.get(field), min, max);
    }

    public static Specification<Recipe> isSeason(Season season) {
        return (root, query, builder) -> root.join("seasons").in(List.of(season));
    }

    public static <T> Specification<Recipe> in(List<T> entries, String field) {
        return (root, query, builder) -> root.get(field).in(entries);
    }

}
