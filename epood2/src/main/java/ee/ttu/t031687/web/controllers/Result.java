package ee.ttu.t031687.web.controllers;

public class Result {
    
    private final boolean success;
    private final String message;
    private final Object data;
    
    private Result(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public Object getData() {
        return data;
    }
    
    public static Result success() {
        return success(null);
    }
    
    public static Result success(Object data) {
        return new Result(true, null, data);
    }
    
    public static Result fail(String message) {
        return new Result(false, message, null);
    }
    
    public static Result authfail() {
        return fail("AUTH_FAIL");
    }

    public static Result notFound() {
        return fail("NOT_FOUND");
    }
    
}
