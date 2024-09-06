package pmb.recipes.adapter.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pmb.recipes.adapter.rest.dto.RecipeDto;
import pmb.recipes.domain.model.Difficulty;
import pmb.recipes.domain.model.Nutriscore;
import pmb.recipes.domain.model.Season;
import pmb.recipes.domain.service.RecipeService;
import pmb.recipes.utils.ControllerTestRunner;
import pmb.recipes.utils.TestUtils;

@ControllerTestRunner(controller = RecipeController.class)
public class RecipeControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @MockBean private RecipeService recipeService;

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(recipeService);
  }

  @Nested
  class GetById {

    @Test
    void when_invalid_id_then_bad_request() throws Exception {
      mockMvc
          .perform(get("/recipes/{id}", "azert").contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isBadRequest())
          .andExpect(
              jsonPath("$")
                  .value(
                      equalTo(
                          "Parameter 'id' doesn't match type definition, expected type: 'class java.lang.Long'")));

      verify(recipeService, never()).getById(any());
    }

    @Test
    void not_found() throws Exception {
      Long id = 6L;

      when(recipeService.getById(id)).thenReturn(Optional.empty());

      mockMvc
          .perform(get("/recipes/{id}", id).contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$").value(equalTo("Recipe with id '6' not found")));

      verify(recipeService).getById(id);
    }

    @Test
    void ok() throws Exception {
      Long id = 6L;
      RecipeDto recipe =
          new RecipeDto(
              id,
              "name",
              Difficulty.HARD,
              5,
              10,
              Nutriscore.D,
              List.of(Season.AUTUMN, Season.SUMMER),
              4,
              "link",
              "description");

      when(recipeService.getById(id)).thenReturn(Optional.of(recipe));

      assertThat(recipe)
          .usingRecursiveComparison()
          .isEqualTo(
              objectMapper.readValue(
                  TestUtils.readResponse.apply(
                      mockMvc
                          .perform(
                              get("/recipes/{id}", String.valueOf(id))
                                  .contentType(MediaType.APPLICATION_JSON_VALUE))
                          .andExpect(status().isOk())),
                  RecipeDto.class));

      verify(recipeService).getById(id);
    }
  }
}
