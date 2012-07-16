package tpyo.mediasurface;

/**
 * Base class for all Mediasurface-related Exceptions.
 * <p>
 * Introduced to deal with the fact that the Mediasurface API only throws checked exceptions: these checked exceptions
 * provide little value and clutter up the code, hence the presence of this runtime exception.
 *
 * @see tpyo.mediasurface.support.MediasurfaceExceptionWrappingRuntimeExceptionTranslator
 */
public class MediasurfaceException extends RuntimeException
{
    private static final long serialVersionUID = -6317343575702676849L;

    /** Create an instance of the {@link MediasurfaceException} class. */
    public MediasurfaceException()
    {
    }

    /**
     * Create an instance of the {@link MediasurfaceException} class.
     *
     * @param message a message detailing the cause of the exception.
     * @param ex      the exception to be wrapped.
     */
    public MediasurfaceException( String message, Throwable ex )
    {
        super( message, ex );
    }

    /**
     * Create an instance of the {@link MediasurfaceException} class.
     *
     * @param reason a message detailing the cause of the exception.
     */
    public MediasurfaceException( String reason )
    {
        super( reason );
    }

    /**
     * Create an instance of the {@link MediasurfaceException} class.
     *
     * @param ex the exception to be wrapped.
     */
    public MediasurfaceException( Throwable ex )
    {
        super( ex );
    }
}
