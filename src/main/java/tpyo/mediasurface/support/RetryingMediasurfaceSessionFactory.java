package tpyo.mediasurface.support;

import tpyo.exception.RuntimeExceptionTranslator;
import tpyo.mediasurface.MediasurfaceSession;
import tpyo.mediasurface.MediasurfaceSessionFactory;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/** A {@link MediasurfaceSessionFactory} that retries failed attempts to create new {@link MediasurfaceSession sessions}. */
public final class RetryingMediasurfaceSessionFactory implements MediasurfaceSessionFactory
{
    public MediasurfaceSession newSession() throws Exception
    {
        MediasurfaceSession session = null;
        int attempts = 0;
        while ( session == null && attempts <= retryAttempts )
        {
            try
            {
                session = delegate.newSession();
            }
            catch ( Exception ex )
            {
                if ( ++attempts >= retryAttempts )
                {
                    throw runtimeExceptionTranslator.translate( ex );
                }
                else
                {
                    try
                    {
                        Thread.sleep( delayBetweenRetriesInMilliseconds );
                    }
                    catch ( InterruptedException ignored )
                    {
                    }
                }
            }
        }
        return session;
    }

    private final MediasurfaceSessionFactory delegate;
    private final int retryAttempts;
    private final int delayBetweenRetriesInMilliseconds;

    private RuntimeExceptionTranslator runtimeExceptionTranslator;

    private RetryingMediasurfaceSessionFactory(
          MediasurfaceSessionFactory delegate,
          int retryAttempts,
          int delayBetweenRetriesInMilliseconds )
    {
        checkNotNull( delegate );
        checkArgument( retryAttempts > 1, "The retryAttempts must be greater than 1." );
        checkArgument( delayBetweenRetriesInMilliseconds > 0, "The delayBetweenRetriesInMilliseconds must be positive." );

        this.delegate = delegate;
        this.retryAttempts = retryAttempts;
        this.delayBetweenRetriesInMilliseconds = delayBetweenRetriesInMilliseconds;

        runtimeExceptionTranslator = new MediasurfaceExceptionWrappingRuntimeExceptionTranslator();
    }

    public void setRuntimeExceptionTranslator( RuntimeExceptionTranslator runtimeExceptionTranslator )
    {
        this.runtimeExceptionTranslator = checkNotNull( runtimeExceptionTranslator );
    }

    /**
     * Build {@link RetryingMediasurfaceSessionFactory} instances.
     *
     * @see <a href="http://en.wikipedia.org/wiki/Builder_pattern">Builder Pattern</a>
     */
    public static final class Builder
    {
        public static final int DEFAULT_NUMBER_OF_RETRY_ATTEMPTS = 3;
        public static final int DEFAULT_DELAY_BETWEEN_RETRIES_IN_MILLISECONDS = 1000;

        private int retryAttempts = DEFAULT_NUMBER_OF_RETRY_ATTEMPTS;
        private int delayBetweenRetriesInMilliseconds = DEFAULT_DELAY_BETWEEN_RETRIES_IN_MILLISECONDS;

        private final MediasurfaceSessionFactory delegate;

        public Builder( MediasurfaceSessionFactory delegate )
        {
            this.delegate = checkNotNull( delegate );
        }

        public RetryingMediasurfaceSessionFactory build()
        {
            return new RetryingMediasurfaceSessionFactory( delegate, retryAttempts, delayBetweenRetriesInMilliseconds );
        }

        public Builder retryAttempts( int retryAttempts )
        {
            this.retryAttempts = retryAttempts;
            return this;
        }

        public Builder delayBetweenRetriesInMilliseconds( int delayBetweenRetriesInMilliseconds )
        {
            this.delayBetweenRetriesInMilliseconds = delayBetweenRetriesInMilliseconds;
            return this;
        }

        public Builder delayBetweenRetriesInSeconds( int delayBetweenRetriesInSeconds )
        {
            return delayBetweenRetriesInMilliseconds( delayBetweenRetriesInSeconds * 1000 );
        }
    }
}
