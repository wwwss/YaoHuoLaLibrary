package com.library.interfaces;

public interface UploadPicturesListener {
    void onUploadSuccess(String result);
    void onUploadStart();
    void onUploadFailure();
}
