package servicos.exception;

public class RegraDeNegocioException extends RuntimeException {

    public RegraDeNegocioException(String error){
        super(error);
    }
}
