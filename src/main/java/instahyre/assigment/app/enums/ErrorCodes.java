package instahyre.assigment.app.enums;

public enum ErrorCodes {

    NOT_NULL_VALIDATION("APP_000");

    private String value;

    public String getValue() {
        return value;
    }

    protected void setValue(String value) {
        this.value = value;
    }

    private ErrorCodes(String value) {
        this.value = value;
    }

}
