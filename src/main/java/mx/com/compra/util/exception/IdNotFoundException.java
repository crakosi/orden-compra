package mx.com.compra.util.exception;

public class IdNotFoundException  extends RuntimeException {

	private static final long serialVersionUID = -3449067384959894981L;
	private static final String ERROR_MESSAGE = "Registro no existe, %s";

    public IdNotFoundException(String tableName) {
        super(String.format(ERROR_MESSAGE, tableName));
    }
}
