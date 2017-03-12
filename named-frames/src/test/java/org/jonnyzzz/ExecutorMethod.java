package org.jonnyzzz;

import org.hamcrest.core.IsEqual;
import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.FrameAction;
import org.jonnyzzz.stack.FrameFunction;
import org.jonnyzzz.stack.NamedExecutor;
import org.junit.Assert;

import java.util.concurrent.Callable;

/**
 * Created by eugene.petrenko@gmail.com
 */
enum ExecutorMethod {
    ACTION {
        @Override
        public void apply(@NotNull final NamedExecutor e,
                          @NotNull final Runnable captureStack) throws Throwable {
            e.action(new FrameAction<Throwable>() {
                @Override
                public void action() throws Throwable {
                    this_is_method_to_be_called();
                }

                private void this_is_method_to_be_called() {
                    captureStack.run();
                }
            });
        }
    },
    FUNCTION {
        @Override
        public void apply(@NotNull final NamedExecutor e,
                          @NotNull final Runnable captureStack) throws Throwable {
            final Object returnValue = new Object();
            final Object r = e.execute(new FrameFunction<Object, Throwable>() {
                @Override
                public Object execute() throws Throwable {
                    this_is_method_to_be_called();
                    return returnValue;
                }

                private void this_is_method_to_be_called() {
                    captureStack.run();
                }
            });
            Assert.assertThat(r, IsEqual.equalTo(returnValue));
        }
    },
    CALLABLE {
        @Override
        public void apply(@NotNull final NamedExecutor e,
                          @NotNull final Runnable captureStack) throws Throwable {
            final Object returnValue = new Object();

            final Object r = e.call(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    this_is_method_to_be_called();
                    return returnValue;
                }

                private void this_is_method_to_be_called() {
                    captureStack.run();
                }
            });

            Assert.assertThat(r, IsEqual.equalTo(returnValue));
        }
    },
    RUNNABLE {
        @Override
        public void apply(@NotNull final NamedExecutor e,
                          @NotNull final Runnable captureStack) throws Throwable {
            e.run(new Runnable() {
                @Override
                public void run() {
                    this_is_method_to_be_called();
                }

                private void this_is_method_to_be_called() {
                    captureStack.run();
                }
            });
        }
    };

    public abstract void apply(@NotNull final NamedExecutor e, @NotNull final Runnable captureStack) throws Throwable;
}
