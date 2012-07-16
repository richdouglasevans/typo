package tpyo.mediasurface.support;

import tpyo.mediasurface.MediasurfaceCallback;
import tpyo.mediasurface.MediasurfaceSession;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Return the same object that it was {@link #identity initialised with}.
 *
 * @param <T> the type of object to be {@link #execute(tpyo.mediasurface.MediasurfaceSession) exposed}.
 */
public final class IdentityMediasurfaceCallback<T> implements MediasurfaceCallback<T>
{
    /**
     * {@inheritDoc}
     *
     * @return the same object that it was initialised with; never {@code null}.
     */
    public T execute( MediasurfaceSession session ) throws Exception
    {
        return identity;
    }

    /**
     * Create an instance of the {@link IdentityMediasurfaceCallback} class.
     * <p>
     * This generic method exists just to make client code easier to write by obviating the need to specify the type
     * constraint explicitly.
     *
     * @param identity the object to be {@link #execute exposed}; must not be {@code null}.
     * @return identity an instance of the {@code IdentityMediasurfaceCallback} class; never {@code null}.
     * @throws NullPointerException if the supplied {@code identity} object is {@code null}.
     */
    public static <T> IdentityMediasurfaceCallback<T> identity( T identity )
    {
        return new IdentityMediasurfaceCallback<T>( identity );
    }

    private final T identity;

    /**
     * Create an instance of the {@link IdentityMediasurfaceCallback} class.
     *
     * @param identity the object to be {@link #execute exposed}; must not be {@code null}.
     * @throws NullPointerException if the supplied {@code identity} object is {@code null}.
     * @see #identity
     */
    public IdentityMediasurfaceCallback( T identity )
    {
        this.identity = checkNotNull( identity );
    }
}
