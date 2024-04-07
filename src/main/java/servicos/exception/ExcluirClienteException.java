package servicos.exception;

public class ExcluirClienteException extends  RuntimeException {

    public ExcluirClienteException(String error) {
        super(error);
    }
}
