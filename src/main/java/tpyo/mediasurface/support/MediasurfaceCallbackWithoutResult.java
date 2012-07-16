package tpyo.mediasurface.support;

import com.mediasurface.client.Mediasurface;
import tpyo.mediasurface.MediasurfaceCallback;
import tpyo.mediasurface.MediasurfaceOperations;
import tpyo.mediasurface.MediasurfaceSession;

/**
 * A convenient {@link MediasurfaceCallback} implementation for those times when you want to do something with a
 * {@link MediasurfaceSession} but aren't going to return a value.
 */
public abstract class MediasurfaceCallbackWithoutResult implements MediasurfaceCallback<Object>
{
    /**
     * {@inheritDoc}
     *
     * @see #doExecute(tpyo.mediasurface.MediasurfaceSession)
     */
    public final Object execute( MediasurfaceSession session ) throws Exception
    {
        doExecute( session );
        return null;
    }

    /**
     * Execute logic using the {@link Mediasurface} API.
     *
     * @param session a session instance encapsulating a {@link Mediasurface} instance; never {@code null}.
     * @throws Exception if something goes wrong.
     * @see MediasurfaceOperations#execute(MediasurfaceCallback)
     */
    protected abstract void doExecute( MediasurfaceSession session ) throws Exception;
}
