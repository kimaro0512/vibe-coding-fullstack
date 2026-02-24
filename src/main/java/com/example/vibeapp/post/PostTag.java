package com.example.vibeapp.post;

public class PostTag {

    private Long id;
    private Long postId;
    private String tagName;

    public PostTag() {
    }

    public PostTag(Long id, Long postId, String tagName) {
        this.id = id;
        this.postId = postId;
        this.tagName = tagName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "PostTag{id=" + id + ", postId=" + postId + ", tagName='" + tagName + "'}";
    }
}
