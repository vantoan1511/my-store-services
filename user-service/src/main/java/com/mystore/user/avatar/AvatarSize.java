package com.mystore.user.avatar;

public enum AvatarSize {
    SMALL(100, 100),
    MEDIUM(200, 200),
    LARGE(400, 400);

    final int width;
    final int height;

    AvatarSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
