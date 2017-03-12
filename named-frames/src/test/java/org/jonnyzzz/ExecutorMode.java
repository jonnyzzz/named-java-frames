package org.jonnyzzz;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.NamedExecutor;
import org.junit.Assert;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.core.StringContains.containsString;
import static org.jonnyzzz.Stack.stack;
import static org.jonnyzzz.Stack.stackTrace;

/**
 * Created by eugene.petrenko@gmail.com
 */
public enum ExecutorMode {
    NORMAL {
        @NotNull
        public String executeStackFrameForCall(@NotNull final NamedExecutor executor,
                                               @NotNull final ExecutorMethod em) throws Throwable {
            final AtomicReference<String> pStack = new AtomicReference<String>();
            final Runnable captureStack = new Runnable() {
                @Override
                public void run() {
                    pStack.set(stackTrace());
                }
            };

            em.apply(executor, captureStack);

            final String stack = pStack.get();
            Assert.assertNotNull(stack);

            Assert.assertThat(stack, containsString(METHOD_TO_BE_CALLED));
            return stack;
        }

    },

    EXCEPTION {
        class TestSpecificException extends RuntimeException {

        }

        @NotNull
        public String executeStackFrameForCall(@NotNull final NamedExecutor executor,
                                               @NotNull final ExecutorMethod em) throws Throwable {
            final AtomicReference<String> pStack = new AtomicReference<String>();
            final Runnable captureStack = new Runnable() {
                @Override
                public void run() {
                    throw new TestSpecificException();
                }
            };

            try {
                em.apply(executor, captureStack);
            } catch (TestSpecificException e) {
                pStack.set(stack(e));
            }

            final String stack = pStack.get();
            Assert.assertNotNull(stack);

            Assert.assertThat(stack, containsString(METHOD_TO_BE_CALLED));
            return stack;
        }
    };


    @NotNull
    public abstract String executeStackFrameForCall(@NotNull final NamedExecutor executor,
                                                    @NotNull final ExecutorMethod em) throws Throwable;

    private static final String METHOD_TO_BE_CALLED = "this_is_method_to_be_called";
}
