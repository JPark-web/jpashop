package jpabook.jpashop.domain.item;

public enum ItemType {
    BOOK("도서"), FOOD("식품"), ETC("기타");

    private final String description;

    ItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

// 상품 종류는 ENUM을 사용. 설명을 위해 description 필드를 추가