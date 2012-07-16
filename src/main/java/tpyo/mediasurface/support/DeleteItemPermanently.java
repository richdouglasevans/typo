package tpyo.mediasurface.support;

import com.google.common.base.Objects;
import com.mediasurface.client.IItem;
import com.mediasurface.datatypes.ItemKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tpyo.mediasurface.MediasurfaceOperations;
import tpyo.mediasurface.MediasurfaceSession;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Delete an {@link IItem}, <strong>permanently</strong>.
 * <p>
 * This includes <strong>all</strong> versions, and also any entry in the (Recycle) Bin.
 * <p>
 * Use with care, because this does exactly what it says on the tin.
 *
 * @see MediasurfaceOperations#deleteItemPermanently(ItemKey)
 * @see DeleteItemPermanentlyBruteForceVersion
 */
public final class DeleteItemPermanently extends MediasurfaceCallbackWithoutResult
{
    @Override
    protected void doExecute( MediasurfaceSession session ) throws Exception
    {
        LOGGER.debug( "Deleting item with key={}.", keyOfTheItemToBeDeletedPermanently );
        session.deleteItemPermanently( keyOfTheItemToBeDeletedPermanently );
    }

    private static final Logger LOGGER = LoggerFactory.getLogger( DeleteItemPermanently.class );

    private final ItemKey keyOfTheItemToBeDeletedPermanently;

    /**
     * Create an instance of the {@link DeleteItemPermanently} class.
     *
     * @param keyOfTheItemToBeDeletedPermanently
     *         the key of the item to be deleted; must not be {@code null}.
     */
    public DeleteItemPermanently( ItemKey keyOfTheItemToBeDeletedPermanently )
    {
        this.keyOfTheItemToBeDeletedPermanently = checkNotNull( keyOfTheItemToBeDeletedPermanently );
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper( this ).add( "key", keyOfTheItemToBeDeletedPermanently ).toString();
    }
}
