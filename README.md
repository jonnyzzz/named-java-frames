named-frames
============

This is a tiny java library to include any text messages
into Java stack traces.


For example, one needs to include the message ```this is my named frame```
into the application stacktrace. The resulting stacktrace should be the
following:

        java.lang.Exception
        	at com.some.corp.something.crashTheJob(SomeOtherOtherSource.java:42)
        	at com.some.corp.something.toItNow(SomeOtherSource.java:52)
        	at _.this is my named frame._(JavaGeneratorTemplate.java:24)
        	at org.jonnyzzz.stack.NamedStackFrame.frame(NamedStackFrame.java:29)
        	at com.some.corp.something.toTheJob(SomeSource.java:50)

This is done via the following code with the library

        NamedStackFrame.global().frame("this is my named frame",
            new Runnable() {
                public void run() {
                     //here goes the code
                }
            });

There could be a number of nice use-cases for the library, for example

- include product version on each stack trace
- include heavy task details into the stack
- and much more!


Dependencies
------------

The library does not have any non-optional dependencies to allow it
to be widely used without dependencies resolution hell

License
-------

*MIT license*

For more details, see LICENSE.txt in the repository


Building
--------

The project is build with Maven


Builds
------

TBD
