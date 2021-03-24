package model.builder;

import model.Right;

public class RightBuilder {

    private final Right right;

    private RightBuilder() {
        right = new Right();
    }

    public RightBuilder setId(long id) {
        right.setId(id);
        return this;
    }

    public RightBuilder setRight(String rightTxt) {
        right.setRight(rightTxt);
        return this;
    }

    public Right build() {
        return right;

    }


}
