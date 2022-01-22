package by.training.restaurant.db.entity;

import java.io.Serializable;


import java.util.Date;
import java.util.List;

import by.training.restaurant.db.entity.Receipt;

public class Receipt implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
    private long usersId;
    private Status status;
    private int total;
    private long managerId;
    private Date createDate;
    private List<Dish> dishes;

    public long getId() {
        return id;
    }

    public long getUsersId() {
        return usersId;
    }

    public Status getStatus() {
        return status;
    }

    public int getTotal() {
        return total;
    }

    public long getManagerId() {
        return managerId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public static class Builder {
        private final Receipt receipts = new Receipt();

        public Builder setId(long id) {
            receipts.id = id;
            return this;
        }

        public Builder setUserId(long usersId) {
            receipts.usersId = usersId;
            return this;
        }

        public Builder setStatusId(long statusId) {
            receipts.status = Status.getStatusById(statusId);
            return this;
        }

        public Builder setTotal(int total) {
            receipts.total = total;
            return this;
        }

        public Builder setManagerId(long managerId) {
            receipts.managerId = managerId;
            return this;
        }

        public Builder setCreateDate(Date createDate) {
            receipts.createDate = createDate;
            return this;
        }

        public Builder setDishes(List<Dish> dishes) {
            receipts.dishes = dishes;
            return this;
        }

        public Receipt getReceipts() {
            return receipts;
        }
    }

    public static class Dish {
        private long id;
        private String name;
        private int count;
        private int price;

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getCount() {
            return count;
        }

        public int getPrice() {
            return price;
        }

        public static class Builder {
            private final Dish dishes = new Dish();

            public Builder setId(long id) {
                dishes.id = id;
                return this;
            }

            public Builder setName(String name) {
                dishes.name = name;
                return this;
            }

            public Builder setCount(int count) {
                dishes.count = count;
                return this;
            }

            public Builder setPrice(int price) {
                dishes.price = price;
                return this;
            }

            public Dish getDish() {
                return dishes;
            }
        }
    }
}
