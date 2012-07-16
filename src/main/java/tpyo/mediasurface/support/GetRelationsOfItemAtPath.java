package tpyo.mediasurface.support;

import com.mediasurface.client.IItem;
import tpyo.mediasurface.MediasurfaceCallback;
import tpyo.mediasurface.MediasurfaceSession;

import static com.google.common.base.Preconditions.checkNotNull;
import static tpyo.mediasurface.support.LinkChildItemExtractor.extractChildItemsFrom;

public final class GetRelationsOfItemAtPath implements MediasurfaceCallback<Iterable<IItem>>
{
    public Iterable<IItem> execute( MediasurfaceSession session ) throws Exception
    {
        IItem sourceItem = session.getItemAtPath( path );
        return extractChildItemsFrom( sourceItem.getRelations() );
    }

    private final String path;

    public GetRelationsOfItemAtPath( String path )
    {
        this.path = checkNotNull( path );
    }
}
