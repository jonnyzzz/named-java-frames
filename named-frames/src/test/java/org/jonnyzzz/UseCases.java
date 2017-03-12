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

package org.jonnyzzz;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public final class UseCases extends BaseFixture {
    public UseCases(@NotNull ExecutorInstance instance, @NotNull ExecutorMethod method, @NotNull ExecutorMode mode) {
        super(instance, method, mode);
    }

    @Test
    public void test_names_invalid() throws Throwable {
        // https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html

        final String brokenName = " . ; [ / < > \0 \42\n\r\t";

        final String s = extractStack(brokenName);
        System.out.println(s);
        Assert.assertThat(s, not(containsString("__FailedToCreateNamedExecutor__")));
    }

    @Test
    public void test_basic() throws Throwable {
        doTest("IDDQD-IDKFA-" + System.currentTimeMillis());
    }

    @Test
    public void test_names_clash_execute() throws Throwable {
        testExecutes("execute");
    }

    @Test
    public void test_names_clash_to_string() throws Throwable {
        testExecutes("toString");
    }

    @Test
    public void test_long_names_function() throws Throwable {
        doTest("вот это вот тут так получилось");
    }
}
