package tpyo.mediasurface.support;

import com.mediasurface.client.Mediasurface;
import tpyo.mediasurface.MediasurfaceConfiguration;
import tpyo.mediasurface.MediasurfaceSession;
import tpyo.mediasurface.MediasurfaceSessionFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Always return the same (singleton) {@link MediasurfaceSession} for every invocation of {@link #newSession()}.
 * <p>
 * <strong>Not intended for use in production code:</strong> seriously, don't use it because it caches a
 * {@code MediasurfaceSession} in a field and never invalidates that session.
 * <p>
 * Pretty useful in testing to dispense with the expense of (re)acquiring a {@link Mediasurface} connection for every
 * invocation of {@link #newSession()}.
 */
public final class SingletonInstanceMediasurfaceSessionFactory implements MediasurfaceSessionFactory
{
    public MediasurfaceSession newSession() throws Exception
    {
        return session;
    }

    private final MediasurfaceSession session;

    /**
     * Create an instance of the {@link SingletonInstanceMediasurfaceSessionFactory} class.
     *
     * @param session (singleton) {@link MediasurfaceSession} to be returned with every invocation of
     *                {@link #newSession()}; must not be {@code null}.
     */
    public SingletonInstanceMediasurfaceSessionFactory( MediasurfaceSession session )
    {
        this.session = checkNotNull( session );
    }

    /**
     * Create an instance of the {@link SingletonInstanceMediasurfaceSessionFactory} class.
     * <p>
     * <strong>Note:</strong> this constructor will immediately (eagerly) attempt to get a new
     * {@link MediasurfaceSession} from the supplied {@code configuration}.
     *
     * @param configuration used to initialise the underlying {@link Mediasurface}; must not be {@code null}.
     * @throws Exception if a new {@code MediasurfaceSession} cannot be sourced from the supplied {@code configuration}.
     * @see MediasurfaceConfiguration#newSession()
     */
    public SingletonInstanceMediasurfaceSessionFactory( MediasurfaceConfiguration configuration ) throws Exception
    {
        this( checkNotNull( configuration ).newSession() );
    }
}
