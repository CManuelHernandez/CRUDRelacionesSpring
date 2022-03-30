package crud.CrudRelaciones.content.Exceptions;

import java.util.Date;

public class CustomResponse {

    private Date timestamp;
    private String mensaje;
    private int httpCode;

    public CustomResponse(Date timestamp, String message, int httpCode) {
        super();
        this.timestamp = timestamp;
        this.mensaje = message;
        this.httpCode=httpCode;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMensaje() {
        return mensaje;
    }
}
