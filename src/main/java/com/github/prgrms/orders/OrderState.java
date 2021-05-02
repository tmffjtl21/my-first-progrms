package com.github.prgrms.orders;

public enum OrderState {

    REQUESTED("주문 최초 생성"),
    ACCEPTED("주문 접수"),
    REJECTED("주문 거절"),
    SHIPPING("주문 배송"),
    COMPLETED("주문 완료");

    private final String value;

    OrderState(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static OrderState of(String name) {
        for (OrderState orderState : OrderState.values()) {
            if (orderState.name().equalsIgnoreCase(name)) {
                return orderState;
            }
        }
        return null;
    }
}
