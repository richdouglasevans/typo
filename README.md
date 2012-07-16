typo is a small library that makes the [Alterian][0] Mediasurface 5.9.0 API easier to use.

Note that since Mediasurface is a proprietary CMS technology, you'll need to provide your own Mediasurface JARs.

Tests
=====

A handful of the test fixtures are marked with [@Ignore][1]. If you want to run those test fixtures, you'll need to remove the `@Ignore` annotations and configure the `mediasurface.properties` file with the details of your CAe instances.

License
=======

    Copyright 2012 Rick Douglas Evans

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
