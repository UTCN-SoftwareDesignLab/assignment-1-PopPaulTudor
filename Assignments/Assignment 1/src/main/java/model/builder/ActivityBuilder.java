package model.builder;

import model.Activity;

public class ActivityBuilder {

    private final Activity activity;

    public ActivityBuilder() {
        this.activity = new Activity();
    }

    public ActivityBuilder setUserId(long userId) {
        activity.setUserId(userId);
        return this;
    }

    public ActivityBuilder setAction(String action) {
        activity.setAction(action);
        return this;
    }

    public ActivityBuilder setDate(long date) {
        activity.setDate(date);
        return this;
    }

    public ActivityBuilder setId(long id) {
        activity.setId(id);
        return this;
    }

    public Activity build() {
        return activity;
    }
}

