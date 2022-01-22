package by.training.restaurant.db.entity;

import java.io.Serializable;

import java.util.Objects;

import by.training.restaurant.db.entity.Dish;

public class Dish implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
    private String name;
    private long categoriesId;
    private long price;
    private long weight;
    private String description;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getCategoriesId() {
        return categoriesId;
    }

    public long getPrice() {
        return price;
    }

    public long getWeight() {
        return weight;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dishes = (Dish) o;
        return id == dishes.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class Builder {
        Dish dishes = new Dish();

        public Dish getDishes() {
            return dishes;
        }

        public Builder setId(long id) {
            dishes.id = id;
            return this;
        }

        public Builder setName(String name) {
            dishes.name = name;
            return this;
        }

        public Builder setCategoriesId(long categoriesId) {
            dishes.categoriesId = categoriesId;
            return this;
        }

        public Builder setPrice(long price) {
            dishes.price = price;
            return this;
        }

        public Builder setWeight(long weight) {
            dishes.weight = weight;
            return this;
        }

        public Builder setDescription(String description) {
            dishes.description = description;
            return this;
        }
    }
}
