package by.training.restaurant.db.entity;

import java.io.Serializable;


import by.training.restaurant.db.entity.Category;

public class Category implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        Category categories = new Category();

        public Category getCategory() {
            return categories;
        }

        public Builder setId(long id) {
            categories.id = id;
            return this;
        }

        public Builder setName(String name) {
            categories.name = name;
            return this;
        }
    }

}
