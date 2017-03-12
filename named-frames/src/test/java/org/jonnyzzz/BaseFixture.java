package org.jonnyzzz;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Created by eugene.petrenko@gmail.com
 */
@RunWith(Parameterized.class)
public abstract class BaseFixture {
    @NotNull
    private final ExecutorInstance myInstance;
    private final ExecutorMethod myMethod;
    private final ExecutorMode myMode;

    public BaseFixture(@NotNull final ExecutorInstance instance,
                       @NotNull final ExecutorMethod method,
                       @NotNull final ExecutorMode mode) {
        myInstance = instance;
        myMethod = method;
        myMode = mode;
    }

    @Parameterized.Parameters(name = "{0} {1} {2}")
    public static Iterable<Object[]> data() {
        final List<Object[]> methods = new ArrayList<Object[]>();
        for (ExecutorInstance instance : ExecutorInstance.values()) {
            for (ExecutorMethod method : ExecutorMethod.values()) {
                for (ExecutorMode mode : ExecutorMode.values()) {
                    methods.add(new Object[]{instance, method, mode});
                }
            }
        }
        return methods;
    }

    protected String extractStack(@NotNull final String name) throws Throwable {
        return myMode.executeStackFrameForCall(myInstance.call().forName(name), myMethod);
    }

    @NotNull
    protected String testExecutes(@NotNull String aName) throws Throwable {
        final String s = extractStack(aName);
        Assert.assertThat(s, not(containsString("__FailedToCreateNamedExecutor__")));
        Assert.assertThat(s, containsString(aName));
        return s;
    }

    protected void doTest(@NotNull final String aName) throws Throwable {
        final String s = testExecutes(aName);

        Assert.assertThat("named frame name should be only once in:\n" + s, s.split(aName).length, equalTo(2));
    }

}
