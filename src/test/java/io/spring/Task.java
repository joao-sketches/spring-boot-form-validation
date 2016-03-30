package io.spring;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Joao Pedro Evangelista
 */
public class Task {

    @NotBlank(message = "Title must be not empty nor blank.")
    private String title;

    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
