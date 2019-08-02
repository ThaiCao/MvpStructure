package com.mvp.structure.data.bean.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by thai.cao on 7/31/2019.
 */
public class UserModel implements Parcelable {
//    "login": "larikraun",
//            "id": 2240178,
//            "node_id": "MDQ6VXNlcjIyNDAxNzg=",
//            "avatar_url": "https://avatars2.githubusercontent.com/u/2240178?v=4",
//            "avatar_url": "",
//            "url": "https://api.github.com/users/larikraun",
//            "html_url": "https://github.com/larikraun",
//            "followers_url": "https://api.github.com/users/larikraun/followers",
//            "following_url": "https://api.github.com/users/larikraun/following{/other_user}",
//            "gists_url": "https://api.github.com/users/larikraun/gists{/gist_id}",
//            "starred_url": "https://api.github.com/users/larikraun/starred{/owner}{/repo}",
//            "subscriptions_url": "https://api.github.com/users/larikraun/subscriptions",
//            "organizations_url": "https://api.github.com/users/larikraun/orgs",
//            "repos_url": "https://api.github.com/users/larikraun/repos",
//            "events_url": "https://api.github.com/users/larikraun/events{/privacy}",
//            "received_events_url": "https://api.github.com/users/larikraun/received_events",
//            "type": "User",
//            "site_admin": false,
//            "score": 1.0
    private String login;
    private int id;
    @SerializedName("node_id")
    private String nodeId;
    @SerializedName("avatar_url")
    private String avatarUrl;
    private String url;

    public UserModel(){}

    protected UserModel(Parcel in) {
        login = in.readString();
        id = in.readInt();
        nodeId = in.readString();
        avatarUrl = in.readString();
        url = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(login);
        parcel.writeInt(id);
        parcel.writeString(nodeId);
        parcel.writeString(avatarUrl);
        parcel.writeString(url);
    }
}
