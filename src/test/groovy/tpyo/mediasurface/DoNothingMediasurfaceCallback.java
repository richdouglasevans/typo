package tpyo.mediasurface;

public final class DoNothingMediasurfaceCallback implements MediasurfaceCallback<Object>
{
    public Object execute( MediasurfaceSession session ) throws Exception
    {
        return null;
    }

    public static DoNothingMediasurfaceCallback doNothing()
    {
        return new DoNothingMediasurfaceCallback();
    }

    private DoNothingMediasurfaceCallback()
    {
    }
}
