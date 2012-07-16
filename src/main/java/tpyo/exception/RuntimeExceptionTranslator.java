package tpyo.exception;

/**
 * Translate checked {@link Exception Exceptions} into runtime {@link RuntimeException RuntimeExceptions}.
 * <p>
 * The rationale for doing this translation is that checked exceptions offer little value and clutter up the code with
 * exception handling logic that often just logs the exception and rethrows it.
 */
public interface RuntimeExceptionTranslator
{
    /**
     * Translate the supplied exception into a {@link RuntimeException}.
     *
     * @param ex the {@link Exception} to be translated; can be {@code null}.
     * @return the translated exception; {@code null} if {@code ex} is {@code null}.
     */
    RuntimeException translate( Exception ex );
}
