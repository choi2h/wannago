package com.wannago.qna.entity;

public enum Category {
    ALL, ACTIVITY, FOOD, LOCATION, OTHER;
    public static Category getCategory(String categoryStr) {
        for(Category category : Category.values()) {
            if(category.name().equalsIgnoreCase(categoryStr)) {
                return category;
            }
        }

        return null;
    }
}
