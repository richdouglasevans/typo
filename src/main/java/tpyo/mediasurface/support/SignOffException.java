package tpyo.mediasurface.support;

import tpyo.mediasurface.MediasurfaceException;

/**
 * Thrown when an {@link com.mediasurface.client.IItem IItem} cannot be signed-off.
 *
 * @see SignOffToStatus
 * @see SignOffToStatusBruteForceVersion
 */
public final class SignOffException extends MediasurfaceException
{
    private static final long serialVersionUID = -1289922170054377179L;

    public SignOffException()
    {
    }

    public SignOffException( String message, Throwable ex )
    {
        super( message, ex );
    }

    public SignOffException( String reason )
    {
        super( reason );
    }

    public SignOffException( Throwable ex )
    {
        super( ex );
    }
}
