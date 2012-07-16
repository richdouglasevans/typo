package tpyo.mediasurface.support;

import com.google.common.base.Predicate;
import com.mediasurface.client.IStatus;
import tpyo.util.AbstractRuntimeExceptionTranslatingPredicate;

import static com.google.common.base.Predicates.equalTo;

/** Return {@code true} iff a {@link IStatus status'} {@link IStatus#getName() name} matches a desired name. */
public final class StatusNameMatches extends AbstractRuntimeExceptionTranslatingPredicate<IStatus>
{
    protected boolean doApply( IStatus status ) throws Exception
    {
        return status != null && nameMatches.apply( status.getName() );
    }

    private final Predicate<String> nameMatches;

    /**
     * Create an instance of the {@link StatusNameMatches} class.
     * <p>
     * The supplied {@code name} can be {@code null}, in which case the {@link #doApply(IStatus) test} will return
     * {@code true} iff the {@link IStatus status'} {@link IStatus#getName() name} is itself {@code null}.
     *
     * @param name the name to test against; can be {@code null}.
     */
    public StatusNameMatches( String name )
    {
        this.nameMatches = equalTo( name );
    }
}
