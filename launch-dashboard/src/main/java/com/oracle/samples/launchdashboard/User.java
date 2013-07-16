/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.samples.launchdashboard;

import java.util.Objects;

/**
 *
 * @author Bruno Borges <bruno.borges at oracle.com>
 */
public class User {

    private boolean admin = false;
    private String twitterName;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.admin ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.twitterName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.admin != other.admin) {
            return false;
        }
        if (!Objects.equals(this.twitterName, other.twitterName)) {
            return false;
        }
        return true;
    }

    public User(String twitterName) {
        this.twitterName = twitterName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getTwitterName() {
        return twitterName;
    }

    public void setTwitterName(String twitterName) {
        this.twitterName = twitterName;
    }
}
