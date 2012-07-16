package tpyo.mediasurface;

import com.mediasurface.client.IEditable;
import com.mediasurface.client.Mediasurface;

/** Edit an {@link IEditable editable item} using the {@link Mediasurface} API. */
public interface Editor<T extends IEditable>
{
    /**
     * Edit the supplied {@code item} using the {@link Mediasurface} API.
     *
     * @param session a session instance encapsulating a {@link Mediasurface} instance; never {@code null}.
     * @param item    the item to be edited; never {@code null}.
     * @throws Exception if something goes wrong.
     */
    void edit( MediasurfaceSession session, T item ) throws Exception;
}
