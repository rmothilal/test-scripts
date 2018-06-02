package com.mojaloop.utils;

import java.util.HashMap;
import java.util.Map;

public class MLResponse {

    public String responseCode;

    public String responseBody;

    public String errorCode;

    public String errorDescription;

    public Map<String,String> headers;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void addHeader(String headerName, String headerValue){
        if(headers == null)
            headers = new HashMap<>();

        headers.put(headerName,headerValue);
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }



}
