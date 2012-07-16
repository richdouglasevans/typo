package tpyo.exception;

/** Translate any checked {@link Exception} into a {@link RuntimeException}. */
public final class SimpleRuntimeExceptionTranslator implements RuntimeExceptionTranslator
{
    /**
     * {@inheritDoc}
     * <p>
     * If the supplied exception is a {@link RuntimeException} then it will be returned as-is and no translation will be
     * performed.
     *
     * @return {@code RuntimeException}, or {@code null}.
     */
    public RuntimeException translate( Exception ex )
    {
        return ex != null
              ? (ex instanceof RuntimeException)
              ? (RuntimeException) ex
              : new RuntimeException( ex )
              : null;
    }
}
