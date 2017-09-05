package com.networknt.portal.usermanagement.model.common.event;


import com.networknt.portal.usermanagement.model.common.domain.UserInfo;



public class UserSignEvent implements UserEvent {

    private UserInfo userInfo;

    private UserSignEvent() {
    }

    public UserSignEvent(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }


}