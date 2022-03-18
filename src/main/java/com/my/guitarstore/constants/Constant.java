package com.my.guitarstore.constants;

public class Constant {


    public static class ErrorMessages {
        public static final String INVALID_REQUEST = "Bad Request - Given request object is missing key data elements. ";
        public static final String UNAUTHORIZED = "Provided authentication credentials are invalid.";
        public static final String FORBIDDEN = "Valid authentication credentials are provided but user is not authorized to access this resource.";
        public static final String NOT_FOUND = "Resource not found";
        public static final String INTERNAL_ERROR = "The server encountered an internal error.";


    }
}
