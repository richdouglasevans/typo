`tpyo` makes the [Alterian][0] Mediasurface 5.9.0 API easier to use. Note that since Mediasurface is a proprietary CMS technology, you'll need to provide your own Mediasurface JARs.

Usage
=====

Configure a threadsafe [MediasurfaceConfiguration](https://github.com/richdouglasevans/typo/blob/master/src/main/java/tpyo/mediasurface/MediasurfaceConfiguration.java) and use it to instantiate a [MediasurfaceTemplate](https://github.com/richdouglasevans/typo/blob/master/src/main/java/tpyo/mediasurface/MediasurfaceTemplate.java).
````java
MediasurfaceConfiguration newsSiteConfiguration = new MediasurfaceConfiguration.Builder()
        .serverLocation( "//10.115.40.139:2170" )
        .siteGroup( "Foo Bar Weekly" )
        .host( "news.fbw.com" )
        .usingDefaultAdminCredentials()
        .build();

MediasurfaceOperations newsSite = new MediasurfaceTemplate( newsSiteConfiguration );
````

Many Mediasurface API calls become one-liners.
````java
IItem techNews = newsSite.getItemAtPath( "/articles/2012/june/tech" );
````

Editing existing content is clearer: the necessary workflow calls are handled by the template.
````java
newsSite.edit( techNews, new Editor<IItem>()
    {
        void edit( MediasurfaceSession session, IItem item )
        {
            // make changes to item...
        }
    }
);
````

The full power of the raw Mediasurface API is always available should you need it.
````java
newsSite.execute( new MediasurfaceCallback<IStatus>()
    {
        IStatus execute( MediasurfaceSession session )
        {
            Mediasurface mediasurface = session.getMediasurface();
            // use full API as necessary...
            return session.getNamedStatus( "Published" );
        }
    }
);
````
Tests
=====

A handful of the test fixtures are marked with [@Ignore][1]. If you want to run those test fixtures, you'll need to remove the `@Ignore` annotations and configure the `mediasurface.properties` file with the details of your CAe instances.

The test suite is written in [Groovy][2] because Groovy's syntactic sugar removes a lot of Java's ceremony, leading to clearer tests that are quicker to read and write. The library code is written in Java because the team in which I was working when I wrote this library was reluctant to introduce a dependency on Groovy in production. Using Groovy just for the tests was an experiment on my part, and not a hugely successful one: the test fixtures weren't that much faster to write, and context switching between Java and Groovy was slightly annoying.

License
=======

    Copyright 2012 Rich Douglas Evans

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

Mediasurface is copyright © [Alterian][0].

[0]: http://www.alterian.com/
[1]: http://kentbeck.github.com/junit/javadoc/latest/org/junit/Ignore.html
[2]: http://groovy.codehaus.org/
