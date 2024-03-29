package com.example.myagent.objects;

public class Document {
    private String agentId;
    private String documentName;
    private String status;
    private String userFullName;
    private String userId;
    private String path;


    public Document(){}

    public Document(String agentId, String documentName, String status, String userFullName, String userId,String path) {
        this.agentId = agentId;
        this.documentName = documentName;
        this.status = status;
        this.userFullName = userFullName;
        this.userId = userId;
        this.path = path;


    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
