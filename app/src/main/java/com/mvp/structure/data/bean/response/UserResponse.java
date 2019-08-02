package com.mvp.structure.data.bean.response;

import com.mvp.structure.data.bean.model.UserModel;
import com.mvp.structure.data.bean.response.base.BaseResponse;

import java.util.List;

/**
 * Created by thai.cao on 7/31/2019.
 */
public class UserResponse extends BaseResponse {
    private List<UserModel> items;

    public UserResponse(List<UserModel> users) {
        this.items = users;
    }

    public List<UserModel> getUsers() {
        return items;
    }

    public void setUsers(List<UserModel> users) {
        this.items = users;
    }
}
