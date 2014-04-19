package org.jonnyzzz;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.NamedStackFrame;

/**
 * Unit test for simple App.
 */
public class GlobalTest extends BaseUseCases {

    @Override
    @NotNull
    protected NamedStackFrame call() {
        return NamedStackFrame.global();
    }


}
