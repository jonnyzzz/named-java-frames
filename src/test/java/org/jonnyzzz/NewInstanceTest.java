package org.jonnyzzz;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.NamedStackFrame;
import org.junit.Test;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class NewInstanceTest extends BaseUseCases {
    @NotNull
    @Override
    protected NamedStackFrame call() {
        return NamedStackFrame.newInstance();
    }

    @Test
    public void should_not_reach_permgen() {
        for (int i = 0; i < 10 * 1000; i++) {
            call().frame("test" + i, new Runnable() {
                public void run() {
                    //NOP
                }
            });
        }
    }
}
