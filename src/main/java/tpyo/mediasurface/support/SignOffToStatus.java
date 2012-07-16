package tpyo.mediasurface.support;

import com.google.common.base.Objects;
import com.mediasurface.client.IItem;
import com.mediasurface.client.IStatus;
import com.mediasurface.datatypes.ItemKey;
import com.mediasurface.general.SelfLoggingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tpyo.mediasurface.MediasurfaceSession;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Sign-off an {@link IItem} to a target {@link IStatus}.
 * <p>
 * The strategy employed is very naive and riddled full of gaping holes: this class does <strong>no</strong> checking of
 * the "well-formed-ness" of the {@link IStatus}: for example, if the target {@link IItem} is in the 'Published' status
 * and the target {@code IStatus} is 'New' (which comes before 'Published' in the workflow), then the signing-off
 * process will likely leave the system in an undefined state.
 *
 * @see SignOffToStatusBruteForceVersion
 */
public final class SignOffToStatus extends MediasurfaceCallbackWithoutResult
{
    @Override
    protected void doExecute( MediasurfaceSession session ) throws Exception
    {
        IItem item = session.getItemByKey( keyOfItemToBeSignedOff );

        String targetStatusName = targetStatus.getName();
        String itemPath = item.getPath();

        if ( LOGGER.isDebugEnabled() )
        {
            LOGGER.debug( "Signing off '{}' at '{}' to '{}' status.",
                  new Object[]{item.getFullName(), itemPath, targetStatusName} );
        }

        while ( !matchesStatusOfTarget( item.getStatus() ) )
        {
            try
            {
                item.signOff( "Automatic sign-off during content replication." );
                // refetch the item being signed-off 'cos the new status isn't reflected otherwise
                item = session.getItemByKey( keyOfItemToBeSignedOff );
            }
            catch ( SelfLoggingException ex )
            {
                String message = String.format( "Cannot sign-off item at '%s' to '%s'.", itemPath, targetStatusName );
                throw new SignOffException( message, ex );
            }
        }
    }

    private boolean matchesStatusOfTarget( IStatus itemStatus ) throws Exception
    {
        return itemStatus.getName().equals( targetStatus.getName() );
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper( this )
              .add( "keyOfItemToBeSignedOff", keyOfItemToBeSignedOff )
              .add( "targetStatus", targetStatus )
              .toString();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger( SignOffToStatus.class );

    private final ItemKey keyOfItemToBeSignedOff;
    private final IStatus targetStatus;

    /**
     * Create an instance of the {@link SignOffToStatus} class.
     *
     * @param keyOfItemToBeSignedOff the key of the {@link IItem} to be signed off; must not be {@code null}.
     * @param targetStatus           the status that the item must be signed-off into; must not be {@code null}.
     */
    public SignOffToStatus( ItemKey keyOfItemToBeSignedOff, IStatus targetStatus )
    {
        this.keyOfItemToBeSignedOff = checkNotNull( keyOfItemToBeSignedOff );
        this.targetStatus = checkNotNull( targetStatus );
    }
}
