package com.wannago.qna.ask.entity;

public enum Category {
    ACTIVITY, FOOD, LOCATION, OTHER;
    public static Category getCategory(String categoryStr) {
        for(Category category : Category.values()) {
            if(category.name().equalsIgnoreCase(categoryStr)) {
                return category;
            }
        }

        return null;
    }
}
