package pmb.recipes.adapter.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pmb.recipes.utils.TestUtils.buildRecipeDto;
import static pmb.recipes.utils.TestUtils.readResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pmb.recipes.adapter.rest.dto.RecipeDto;
import pmb.recipes.domain.service.RecipeService;
import pmb.recipes.utils.ControllerTestRunner;

@ControllerTestRunner(controller = RecipeController.class)
class RecipeControllerTest {
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
      RecipeDto recipe = buildRecipeDto(id);

      when(recipeService.getById(id)).thenReturn(Optional.of(recipe));

      assertThat(recipe)
          .usingRecursiveComparison()
          .isEqualTo(
              objectMapper.readValue(
                  readResponse.apply(
                      mockMvc
                          .perform(
                              get("/recipes/{id}", String.valueOf(id))
                                  .contentType(MediaType.APPLICATION_JSON_VALUE))
                          .andExpect(status().isOk())),
                  RecipeDto.class));

      verify(recipeService).getById(id);
    }
  }

  @Test
  void create() throws Exception {
    RecipeDto recipe = buildRecipeDto(null);
    when(recipeService.create(recipe)).thenAnswer(a -> a.getArgument(0));

    assertThat(recipe)
        .usingRecursiveComparison()
        .isEqualTo(
            objectMapper.readValue(
                readResponse.apply(
                    mockMvc
                        .perform(
                            post("/recipes")
                                .content(objectMapper.writeValueAsString(recipe))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isCreated())),
                RecipeDto.class));

    verify(recipeService).create(any());
  }

  @Nested
  class Edit {

    @Test
    void not_found() throws Exception {
      Long id = 6L;
      RecipeDto recipe = buildRecipeDto(id);

      when(recipeService.edit(recipe)).thenReturn(Optional.empty());

      mockMvc
          .perform(
              put("/recipes")
                  .content(objectMapper.writeValueAsString(recipe))
                  .contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$").value(equalTo("Recipe with id '6' not found")));

      verify(recipeService).edit(recipe);
    }

    @Test
    void ok() throws Exception {
      Long id = 6L;
      RecipeDto recipe = buildRecipeDto(id);

      when(recipeService.edit(recipe)).thenReturn(Optional.of(recipe));

      assertThat(recipe)
          .usingRecursiveComparison()
          .isEqualTo(
              objectMapper.readValue(
                  readResponse.apply(
                      mockMvc
                          .perform(
                              put("/recipes")
                                  .content(objectMapper.writeValueAsString(recipe))
                                  .contentType(MediaType.APPLICATION_JSON_VALUE))
                          .andExpect(status().isOk())),
                  RecipeDto.class));

      verify(recipeService).edit(recipe);
    }
  }

  @Test
  void deleteById() throws Exception {
    Long id = 6L;

    doNothing().when(recipeService).delete(id);
    mockMvc
        .perform(delete("/recipes/{id}", id).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent());

    verify(recipeService).delete(id);
  }
}
