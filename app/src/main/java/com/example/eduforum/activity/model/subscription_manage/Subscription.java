package com.example.eduforum.activity.model.subscription_manage;

public class Subscription {
    private String communityID;
    private String postID;
    private String userID;

    public Subscription(String communityID, String postID, String userID) {
        this.communityID = communityID;
        this.postID = postID;
        this.userID = userID;
    }
    public Subscription(){
    }
    public String getCommunityID() {
        return communityID;
    }
    public String getPostID() {
        return communityID;
    }
    public String getUserID() {
        return communityID;
    }


    public void setCommunityID(String communityID) {
        this.communityID = communityID;
    }
    public void setPostID(String postID) {
        this.postID = postID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
}
