package tpyo.mediasurface.support;

import com.mediasurface.datatypes.ItemKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tpyo.mediasurface.MediasurfaceSession;

/**
 * Delete an {@link com.mediasurface.client.IItem IItem}, <strong>permanently</strong>.
 * <p>
 * This includes <strong>all</strong> versions, and also any entry in the (Recycle) Bin.
 * <p/>
 * Differs from {@link DeleteItemPermanently} in that any exceptions arising from the delete operation are swallowed:
 * this is done so that a failure to delete one item in a series of such items to be deleted will not prevent other
 * items from being deleted.
 * <p/>
 * <strong>Use with care, because this does exactly what it says on the tin.</strong>
 *
 * @see DeleteItemPermanently
 * @see tpyo.mediasurface.MediasurfaceOperations#deleteItemPermanently(ItemKey)
 */
public final class DeleteItemPermanentlyBruteForceVersion extends MediasurfaceCallbackWithoutResult
{
    @Override
    protected void doExecute( MediasurfaceSession session ) throws Exception
    {
        String siteName = session.getSite().getName();
        LOGGER.debug( "Brute force deleting an item from the '{}' site: [{}].", siteName, deleteItemPermanently );
        try
        {
            deleteItemPermanently.execute( session );
        }
        catch ( Exception ex )
        {
            LOGGER.warn( "Failed to delete an item from the '{}' site: [{}]; swallowing exception and carrying on.",
                  siteName, deleteItemPermanently );
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger( DeleteItemPermanentlyBruteForceVersion.class );

    private final DeleteItemPermanently deleteItemPermanently;

    /**
     * Create an instance of the {@link DeleteItemPermanentlyBruteForceVersion} class.
     *
     * @param keyOfTheItemToBeDeletedPermanently
     *         the key of the item to be deleted; must not be {@code null}.
     */
    public DeleteItemPermanentlyBruteForceVersion( ItemKey keyOfTheItemToBeDeletedPermanently )
    {
        this.deleteItemPermanently = new DeleteItemPermanently( keyOfTheItemToBeDeletedPermanently );
    }
}
