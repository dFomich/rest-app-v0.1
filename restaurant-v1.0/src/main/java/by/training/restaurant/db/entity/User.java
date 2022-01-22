package by.training.restaurant.db.entity;

import java.io.Serializable;

import java.util.Date;

import by.training.restaurant.db.entity.User;

public class User implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
    private String login;
    private long rolesId;
    private Date createDate;

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public long getRolesId() {
        return rolesId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", rolesId=" + rolesId +
                ", createDate=" + createDate +
                '}';
    }

    public static class Builder {
        User users = new User();

        public Builder setId(long id) {
            users.id = id;
            return this;
        }

        public Builder setLogin(String login) {
            users.login = login;
            return this;
        }

        public Builder setRolesId(long roleId) {
            users.rolesId = roleId;
            return this;
        }

        public Builder setCreateDate(Date createDate) {
            users.createDate = createDate;
            return this;
        }

        public User getUser() {
            return users;
        }
    }
}
