package tpyo.mediasurface.support;

import com.mediasurface.client.IItem;
import com.mediasurface.client.IStatus;
import com.mediasurface.datatypes.ItemKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tpyo.mediasurface.MediasurfaceSession;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Sign-off an {@link IItem} to a target {@link IStatus}.
 * <p>
 * Differs from {@link SignOffToStatus} in that any {@link SignOffException} being thrown as a result of a failed
 * sign-off will be swallowed. If you want to have calling code make its own mind up about whether or not to continue in
 * the face of a sign-off exception, just use the {@code SignOffToStatus} class directly.
 *
 * <p>A detailed message is written to the log explaining the exception in the hope that someone smart can rectify the
 * issue manually.
 */
public final class SignOffToStatusBruteForceVersion extends MediasurfaceCallbackWithoutResult
{
    @Override
    protected void doExecute( MediasurfaceSession session ) throws Exception
    {
        try
        {
            signOffToStatus.execute( session );
        }
        catch ( SignOffException ex )
        {
            LOGGER.warn( "Failed to sign-off item with key={} to targetStatus='{}'; swallowing exception and carrying on.",
                  keyOfItemToBeSignedOff, targetStatus.getName() );
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger( "Actions" );

    private final ItemKey keyOfItemToBeSignedOff;
    private final IStatus targetStatus;
    private final SignOffToStatus signOffToStatus;

    /**
     * Create an instance of the {@link SignOffToStatusBruteForceVersion} class.
     *
     * @param keyOfItemToBeSignedOff the key of the {@link IItem} to be signed off; must not be {@code null}.
     * @param targetStatus           the status that the item must be signed-off into; must not be {@code null}.
     */
    public SignOffToStatusBruteForceVersion( ItemKey keyOfItemToBeSignedOff, IStatus targetStatus )
    {
        this.keyOfItemToBeSignedOff = checkNotNull( keyOfItemToBeSignedOff );
        this.targetStatus = checkNotNull( targetStatus );
        this.signOffToStatus = new SignOffToStatus( keyOfItemToBeSignedOff, targetStatus );
    }
}
