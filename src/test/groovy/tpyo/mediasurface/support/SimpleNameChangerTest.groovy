package tpyo.mediasurface.support

import org.junit.Test

final class SimpleNameChangerTest
{
    @Test(expected = NullPointerException)
    public void ctorRejectsNullSimpleName( )
    {
        new SimpleNameChanger( null )
    }

    @Test(expected = IllegalArgumentException)
    public void ctorRejectsEmptySimpleName( )
    {
        new SimpleNameChanger( "" )
    }

    @Test(expected = IllegalArgumentException)
    public void ctorRejectsWhitespaceOnlySimpleName( )
    {
        new SimpleNameChanger( "   " )
    }
}
