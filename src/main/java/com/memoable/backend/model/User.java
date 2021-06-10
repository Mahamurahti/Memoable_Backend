package com.memoable.backend.model;

import org.bson.types.ObjectId;

/**
 * Class representing user details from the database
 */
public class User {

    /**
     * Username of the user
     */
    private String username;

    /**
     * Password of the user
     */
    private String password;

    /**
     * Id of the user
     */
    private ObjectId id;

    public User() {
        this.username = "";
        this.password = "";
        this.id = new ObjectId();
    }

    /**
     * Constructor with username and password
     * @param username username to set to the user
     * @param password password to set to the user
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
		this.id = new ObjectId();
    }

    /**
     * Returns the username of the user
     * @return username of the user
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the username of the user
     * @param username username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of the user
     * @return password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user
     * @param password password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the objectId of the user
     * @return users's id
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Sets the objectId of the user
     * @param id ObjectId to be set
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                '}';
    }
}
