package com.example.eduforum.activity.repository;

import com.example.eduforum.activity.model.Topic;

import java.util.List;

@Deprecated
public interface TopicCallback {
    void onTopicLoaded(List<Topic> topics);
    void onError(Exception e);
}
