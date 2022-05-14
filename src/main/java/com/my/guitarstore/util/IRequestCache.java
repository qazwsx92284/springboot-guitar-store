package com.my.guitarstore.util;

public interface IRequestCache {

    public void setRequestInProgress(String method, String path, String itemId);

    public boolean isRequestInProgress(String method, String path, String itemId);

    public void setRequestDone(String method, String path, String itemId);
}
