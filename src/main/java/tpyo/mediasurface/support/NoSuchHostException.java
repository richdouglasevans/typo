package tpyo.mediasurface.support;

import tpyo.mediasurface.MediasurfaceException;

/**
 * Thrown when an {@link com.mediasurface.client.IHost IHost} cannot be found.
 * <p>
 * This might be because the serverLocation name has been spelled incorrectly in configuration.
 *
 * @see MediasurfaceUtils#getNamedHost(com.mediasurface.client.ISite, String)
 */
public final class NoSuchHostException extends MediasurfaceException
{
    public NoSuchHostException()
    {
    }

    public NoSuchHostException( String message, Throwable ex )
    {
        super( message, ex );
    }

    public NoSuchHostException( String reason )
    {
        super( reason );
    }

    public NoSuchHostException( Throwable ex )
    {
        super( ex );
    }
}
