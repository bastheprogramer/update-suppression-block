package com.vsdguzman;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class UpdateSuppressionError extends RuntimeException {
    public UpdateSuppressionError() {
        super();
    }

    public static void suppressCrashes(Throwable throwable) throws Throwable {
        if (ExceptionUtils.indexOfThrowable(throwable, UpdateSuppressionError.class) != -1 || ExceptionUtils.hasCause(throwable, UpdateSuppressionError.class)) {
            // Catch all UpdateSuppressionErrors to avoid crashing the game when we suppress an update from certain origins (e.g. random ticking a grass block)
            UpdateSuppression.LOGGER.warn("Suppressed a game crash. (This diverges from vanilla behaviour)");
        }
        else {
            // Propagate any other errors
            //  This technically intentionally allows game crashes not caused by this mod's update suppression to go through (Not really sure if this is "good" but it avoids changing behaviour)
            throw throwable;
        }
    }
}
