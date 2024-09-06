package pmb.recipes.domain.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.List;

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

  @Column(name = "preparation_time", nullable = false)
  private Integer preparationTime;

  @Column(name = "cooking_time")
  private Integer cookingTime;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Nutriscore nutriscore;

  @Column(name = "name")
  @Enumerated(EnumType.STRING)
  @CollectionTable(
      name = "season",
      joinColumns = @JoinColumn(name = "recipe", referencedColumnName = "id"))
  @ElementCollection(targetClass = Season.class)
  private List<Season> seasons;

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

  public Integer getPreparationTime() {
    return preparationTime;
  }

  public void setPreparationTime(final Integer preparationTime) {
    this.preparationTime = preparationTime;
  }

  public Integer getCookingTime() {
    return cookingTime;
  }

  public void setCookingTime(final Integer cookingTime) {
    this.cookingTime = cookingTime;
  }

  public Nutriscore getNutriscore() {
    return nutriscore;
  }

  public void setNutriscore(final Nutriscore nutriscore) {
    this.nutriscore = nutriscore;
  }

  public List<Season> getSeasons() {
    return seasons;
  }

  public void setSeasons(final List<Season> seasons) {
    this.seasons = seasons;
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
