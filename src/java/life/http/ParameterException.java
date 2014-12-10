package life.http;

import life.LifeException;
import org.json.simple.JSONStreamAware;

final class ParameterException extends LifeException {

    private final JSONStreamAware errorResponse;

    ParameterException(JSONStreamAware errorResponse) {
        this.errorResponse = errorResponse;
    }

    JSONStreamAware getErrorResponse() {
        return errorResponse;
    }

}
