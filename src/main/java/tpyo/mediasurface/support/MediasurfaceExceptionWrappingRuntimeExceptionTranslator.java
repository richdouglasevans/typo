package tpyo.mediasurface.support;

import com.mediasurface.general.SelfLoggingException;
import tpyo.exception.RuntimeExceptionTranslator;
import tpyo.mediasurface.MediasurfaceException;

/**
 * Translate checked {@link Exception Exceptions} into {@link RuntimeException RuntimeExceptions}, taking special care
 * to translate checked {@link SelfLoggingException MediasurfaceExceptions} into this library's
 * custom runtime {@link MediasurfaceException MediasurfaceExceptions}.
 */
public final class MediasurfaceExceptionWrappingRuntimeExceptionTranslator implements RuntimeExceptionTranslator
{
    /**
     * {@inheritDoc}
     * <p>
     * If the supplied exception is a {@link RuntimeException} then it will be returned as-is and no translation will be
     * performed.
     * <p>
     * If the supplied exception is a checked {@link SelfLoggingException} (or a subclass thereof) then a runtime
     * {@link MediasurfaceException} will be returned.
     *
     * @return {@code RuntimeException}, or {@code null}.
     */
    public RuntimeException translate( Exception ex )
    {
        return ex != null ? (ex instanceof RuntimeException) ? (RuntimeException) ex
              : (ex instanceof SelfLoggingException) ? new MediasurfaceException( ex )
              : new RuntimeException( ex ) : null;
    }
}
