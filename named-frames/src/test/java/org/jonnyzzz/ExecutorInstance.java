package org.jonnyzzz;

import org.jonnyzzz.stack.NamedStackFrame;

/**
 * Created by eugene.petrenko@gmail.com
 */
public enum ExecutorInstance {
    FRESH {
        @Override
        public NamedStackFrame call() {
            return NamedStackFrame.newInstance();
        }
    },

    GLOBAL {
        @Override
        public NamedStackFrame call() {
            return NamedStackFrame.global();
        }
    };

    public abstract NamedStackFrame call();
}
