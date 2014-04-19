package org.jonnyzzz;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.NamedStackFrame;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class NewInstanceTest extends BaseUseCases {
    @NotNull
    @Override
    protected NamedStackFrame call() {
        return NamedStackFrame.newInstance();
    }
}
