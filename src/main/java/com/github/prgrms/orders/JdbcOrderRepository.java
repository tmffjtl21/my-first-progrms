package com.github.prgrms.orders;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.github.prgrms.utils.DateTimeUtils.dateTimeOf;
import static java.util.Optional.ofNullable;

@Repository
public class JdbcOrderRepository implements OrdersRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Orders> findById(long id) {
        List<Orders> results = jdbcTemplate.query(
                "SELECT ORDERS.*, R.seq AS r_seq, R.user_seq AS r_user_seq, " +
                        "R.product_seq r_product_seq, R.content r_content, R.create_at r_create_at FROM ORDERS LEFT JOIN REVIEWS R " +
                        "on ORDERS.REVIEW_SEQ = R.SEQ where ORDERS.seq = ?",
                mapper,
                id
        );
        return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public List<Orders> findAll(Long offset, Long size) {
        return jdbcTemplate.query(
                "SELECT ORDERS.*, R.seq AS r_seq, R.user_seq AS r_user_seq, " +
                        "R.product_seq r_product_seq, R.content r_content, R.create_at r_create_at FROM ORDERS LEFT JOIN REVIEWS R " +
                        "on ORDERS.REVIEW_SEQ = R.SEQ ORDER BY seq DESC LIMIT ? OFFSET ?",
                mapper,
                size,
                offset
        );
    }

    @Override
    public Boolean accept(Long id) {

        Optional<Orders> orders = findById(id);
        if(orders.isPresent() && orders.get().getState().equals(OrderState.REQUESTED)){
            jdbcTemplate.update(
                    "UPDATE ORDERS SET state=? WHERE seq=?",
                    OrderState.ACCEPTED.name(),
                    id
            );
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean reject(Long id, String message) {

        Optional<Orders> orders = findById(id);
        if(orders.isPresent() && orders.get().getState().equals(OrderState.REQUESTED)){
            jdbcTemplate.update(
                    "UPDATE ORDERS SET state=?, REJECT_MSG = ?, REJECTED_AT = now() WHERE seq=?",
                    OrderState.REJECTED.name(),
                    message,
                    id
            );
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean shipping(Long id) {

        Optional<Orders> orders = findById(id);
        if(orders.isPresent() && orders.get().getState().equals(OrderState.ACCEPTED)){
            jdbcTemplate.update(
                    "UPDATE ORDERS SET state=? WHERE seq=?",
                    OrderState.SHIPPING.name(),
                    id
            );
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean complete(Long id) {

        Optional<Orders> orders = findById(id);
        if(orders.isPresent() && orders.get().getState().equals(OrderState.SHIPPING)){
            jdbcTemplate.update(
                    "UPDATE ORDERS SET state=?, COMPLETED_AT = now() WHERE seq=?",
                    OrderState.COMPLETED.name(),
                    id
            );
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public ReviewDto review(Long id, String content) {
        // TODO
        return null;
    }

    static RowMapper<Orders> mapper = (rs, rowNum) ->{

        Review review = new Review();

        if(rs.getLong("r_seq") == 0L){
            review = null;
        }else{
            review.setSeq(rs.getLong("r_seq"));
            review.setProductId(rs.getLong("r_product_seq"));
            review.setContent(rs.getString("r_content"));
            review.setCreateAt(dateTimeOf(rs.getTimestamp("r_create_at")));
        }

        return new Orders.Builder()
                .seq(rs.getLong("seq"))
                .userSeq(rs.getLong("user_seq"))
                .productId(rs.getLong("product_seq"))
                .orderState(OrderState.of(rs.getString("state")))
                .requestMessage(rs.getString("request_msg"))
                .rejectMessage(rs.getString("reject_msg"))
                .completedAt(dateTimeOf(rs.getTimestamp("completed_at")))
                .rejectedAt(dateTimeOf(rs.getTimestamp("rejected_at")))
                .createAt(dateTimeOf(rs.getTimestamp("create_at")))
                .review(review)
                .build();
    };
}