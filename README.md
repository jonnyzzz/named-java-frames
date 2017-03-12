[ ![Download](https://api.bintray.com/packages/jonnyzzz/maven/named-frames/images/download.svg) ](https://bintray.com/jonnyzzz/maven/named-frames/_latestVersion) [ ![Build Status](https://travis-ci.org/jonnyzzz/named-java-frames.svg?branch=master)](https://travis-ci.org/jonnyzzz/named-java-frames)

named-frames
============

This is a tiny java library to include any text messages
into Java stack traces.


For example, one needs to include the message ```this is my named frame```
into the application stacktrace. The resulting stacktrace should be the
following:

        java.lang.Exception
        	at com.some.corp.something.Object.run
        	at _.this is my named frame._(JavaGeneratorTemplate.java:24)
        	at com.some.corp.something.toTheJob(SomeSource.java:50)

This is done via the following code with the library

        NamedStackFrame.global().forName("this is my named frame").run(
            new Runnable() {
                public void run() {
                     //here goes the code
                }
            });

There could be a number of nice use-cases for the library, for example

- include product version on each stack trace
- include heavy task details into the stack
- and much more!

For more details, see the related [blog post](http://blog.jonnyzzz.name/2014/04/named-stack-frames-for-jvm.html)


Dependencies
------------

The library does not have any non-optional dependencies to allow it
to be widely used without dependencies resolution hell

License
-------

*MIT License*

For more details, see LICENSE.txt in the repository


Building
--------

The project is build with gradle


Binaries
--------
[ ![Download](https://api.bintray.com/packages/jonnyzzz/maven/named-frames/images/download.svg) ](https://bintray.com/jonnyzzz/maven/named-frames/_latestVersion) 

Binaries are uploaded to *jcenter*
Gradle: just add
```
repositories {
  jcenter()
}

dependencies {
  compile 'org.jonnyzzz.named-frames:named-frames:<LATEST VERSION>'
}
```

See [here](https://bintray.com/jonnyzzz/maven/named-frames) for more details

Please note, the `1.1.x` version is not compatible to `1.0.x` version!

Note
====
This is my (Eugene Petrenko) private home project

Support me:
[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=AYHRF77JDGPLA)
