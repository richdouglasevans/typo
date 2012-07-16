package tpyo.mediasurface;

import com.mediasurface.client.Mediasurface;

/** A block of logic encapsulating {@link Mediasurface} API calls. */
public interface MediasurfaceCallback<T>
{
    /**
     * Execute logic using the {@link Mediasurface} API.
     *
     * @param session an encapsulation of a {@link Mediasurface} instance; never {@code null}.
     * @return the result of executing the callback logic; can be {@code null}.
     * @throws Exception if something goes wrong.
     */
    T execute( MediasurfaceSession session ) throws Exception;
}
