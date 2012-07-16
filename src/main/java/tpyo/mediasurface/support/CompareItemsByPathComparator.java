package tpyo.mediasurface.support;

import com.mediasurface.client.IItem;
import tpyo.util.AbstractRuntimeExceptionTranslatingComparator;

/** Compare {@link IItem IItems} by {@link IItem#getPath() path}. */
public final class CompareItemsByPathComparator extends AbstractRuntimeExceptionTranslatingComparator<IItem>
{
    @Override
    protected int doCompare( IItem first, IItem second ) throws Exception
    {
        return String.CASE_INSENSITIVE_ORDER.compare( first.getPath(), second.getPath() );
    }

    public CompareItemsByPathComparator()
    {
        setRuntimeExceptionTranslator( new MediasurfaceExceptionWrappingRuntimeExceptionTranslator() );
    }
}
