package tpyo.mediasurface.support;

import com.mediasurface.client.Mediasurface;
import tpyo.mediasurface.MediasurfaceConfiguration;
import tpyo.mediasurface.MediasurfaceSession;
import tpyo.mediasurface.MediasurfaceSessionFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/** Always create a new {@link MediasurfaceSession} for every invocation of {@link #newSession()}. */
public final class NewSessionEveryTimeMediasurfaceSessionFactory implements MediasurfaceSessionFactory
{
    /**
     * {@inheritDoc}
     *
     * @return a new {@link MediasurfaceSession}, always; never {@code null}.
     * @see Mediasurface#init(String, String)
     */
    public MediasurfaceSession newSession() throws Exception
    {
        return configuration.newSession();
    }

    private final MediasurfaceConfiguration configuration;

    /**
     * Create an instance of the {@link NewSessionEveryTimeMediasurfaceSessionFactory} class.
     *
     * @param configuration used to initialise the underlying {@link Mediasurface}; must not be {@code null}.
     */
    public NewSessionEveryTimeMediasurfaceSessionFactory( MediasurfaceConfiguration configuration )
    {
        this.configuration = checkNotNull( configuration );
    }
}
