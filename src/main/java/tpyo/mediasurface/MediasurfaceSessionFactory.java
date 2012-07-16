package tpyo.mediasurface;

/** A factory for {@link MediasurfaceSession MediasurfaceSessions} */
public interface MediasurfaceSessionFactory
{
    /**
     * Create a {@link MediasurfaceSession}.
     *
     * @return a {@code MediasurfaceSession}; never {@code null}.
     * @throws Exception if the session creation fails.
     */
    MediasurfaceSession newSession() throws Exception;
}
