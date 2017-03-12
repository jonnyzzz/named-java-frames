package org.jonnyzzz.stack.impl;

/**
 * Created by eugene.petrenko@gmail.com
 */
public class GlobalCachedNamedStackFrames extends CachedNamedStackFrames {
    public static final CachedNamedStackFrames INSTANCE = new GlobalCachedNamedStackFrames();
}
