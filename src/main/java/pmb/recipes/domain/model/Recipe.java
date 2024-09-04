package pmb.recipe.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import pmb.recipes.domain.model.Difficulty;
import pmb.recipes.domain.model.Season;

@Entity
@Table(name = "recipe")
public class Recipe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Difficulty difficulty;

  @Column(nullable = false)
  private Integer duration;

  @Enumerated(EnumType.STRING)
  private Season season;

  @Column(name = "person_count", nullable = false)
  private Integer personCount;

  private String link;

  private String description;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public Difficulty getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(final Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(final Integer duration) {
    this.duration = duration;
  }

  public Season getSeason() {
    return season;
  }

  public void setSeason(final Season season) {
    this.season = season;
  }

  public Integer getPersonCount() {
    return personCount;
  }

  public void setPersonCount(final Integer personCount) {
    this.personCount = personCount;
  }

  public String getLink() {
    return link;
  }

  public void setLink(final String link) {
    this.link = link;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }
}
