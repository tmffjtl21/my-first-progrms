package com.github.prgrms.orders;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

public class Orders {

    private final Long seq;
    private Long userSeq;
    private Long productId;
    private Review review;
    private OrderState state;
    private String requestMessage;
    private String rejectMessage;
    private LocalDateTime completedAt;
    private LocalDateTime rejectedAt;
    private final LocalDateTime createAt;

    public Orders(Long seq, Long userSeq, Long productId, Review review, OrderState state, String requestMessage, String rejectMessage,
                  LocalDateTime completedAt, LocalDateTime rejectedAt, LocalDateTime createAt) {
        this.seq = seq;
        this.userSeq = userSeq;
        this.productId = productId;
        this.review = review;
        this.state = state;
        this.requestMessage = requestMessage;
        this.rejectMessage = rejectMessage;
        this.completedAt = completedAt;
        this.rejectedAt = rejectedAt;
        this.createAt = defaultIfNull(createAt, now());
    }

    public Long getSeq() {
        return seq;
    }

    public Long getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(Long userSeq) {
        this.userSeq = userSeq;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public String getRejectMessage() {
        return rejectMessage;
    }

    public void setRejectMessage(String rejectMessage) {
        this.rejectMessage = rejectMessage;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public LocalDateTime getRejectedAt() {
        return rejectedAt;
    }

    public void setRejectedAt(LocalDateTime rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders product = (Orders) o;
        return Objects.equals(seq, product.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    static public class Builder {
        private Long seq;
        private Long userSeq;
        private Long productId;
        private Review review;
        private OrderState orderState;
        private String requestMessage;
        private String rejectMessage;
        private LocalDateTime completedAt;
        private LocalDateTime rejectedAt;
        private LocalDateTime createAt;

        public Builder() {/*empty*/}

        public Builder(Orders orders) {
            this.seq = orders.seq;
            this.userSeq = orders.userSeq;
            this.productId = orders.productId;
            this.review = orders.review;
            this.orderState = orders.state;
            this.requestMessage = orders.requestMessage;
            this.rejectMessage = orders.rejectMessage;
            this.completedAt = orders.completedAt;
            this.rejectedAt = orders.rejectedAt;
            this.createAt = orders.createAt;
        }

        public Builder seq(Long seq) {
            this.seq = seq;
            return this;
        }
        public Builder userSeq(Long userSeq) {
            this.userSeq = userSeq;
            return this;
        }

        public Builder productId(Long productSeq) {
            this.productId = productSeq;
            return this;
        }
        public Builder review(Review review) {
            this.review = review;
            return this;
        }
        public Builder orderState(OrderState orderState) {
            this.orderState = orderState;
            return this;
        }
        public Builder requestMessage(String requestMessage) {
            this.requestMessage = requestMessage;
            return this;
        }
        public Builder rejectMessage(String rejectMessage) {
            this.rejectMessage = rejectMessage;
            return this;
        }
        public Builder completedAt(LocalDateTime completedAt) {
            this.completedAt = completedAt;
            return this;
        }
        public Builder rejectedAt(LocalDateTime rejectedAt) {
            this.rejectedAt = rejectedAt;
            return this;
        }
        public Builder createAt(LocalDateTime createAt) {
            this.createAt = createAt;
            return this;
        }

        public Orders build() {
            return new Orders(
                    seq,
                    userSeq,
                    productId,
                    review,
                    orderState,
                    requestMessage,
                    rejectMessage,
                    completedAt,
                    rejectedAt,
                    createAt
            );
        }
    }

}