/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Eugene Petrenko (eugene.petrenko@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jonnyzzz.stack.impl.gen.jcl;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.NamedExecutor;
import org.jonnyzzz.stack.impl.gen.NamedExecutorsGenerator;

/**
* @author Eugene Petrenko (eugene.petrenko@gmail.com)
*/
public class JavaClassLoaderGenerator extends ClassLoader implements NamedExecutorsGenerator {
    public JavaClassLoaderGenerator() {
        super(NamedExecutor.class.getClassLoader());
    }

    @NotNull
    public NamedExecutor generate(@NotNull final String name) {
        final String clazz = "__." + name;
        final byte[] bytes = JavaClassGenerator.generateWrapper(clazz);

        definePackage(clazz);
        final Class<?> aClass = defineClazz(clazz, bytes);

        try {
            return (NamedExecutor) aClass.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to generate class for name: " + name + ". " + e.getMessage(), e);
        }
    }

    @NotNull
    private Class<?> defineClazz(@NotNull final String name, @NotNull final byte[] bytes) {
        return defineClass(name, bytes, 0, bytes.length);
    }

    private void definePackage(@NotNull final String name) {
        final int i = name.lastIndexOf('.');
        if (i >= 0) {
            final String pkgName = name.substring(0, i);
            // Check if package already loaded.
            final Package pkg = getPackage(pkgName);
            if (pkg == null) {
                try {
                    definePackage(pkgName, null, null, null, null, null, null, null);
                } catch (IllegalArgumentException e) {
                    // do nothing, package already defined by some other thread
                }
            }
        }
    }
}
