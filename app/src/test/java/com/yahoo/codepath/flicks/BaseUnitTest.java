package com.yahoo.codepath.flicks;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Ignore
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 25)
public class BaseUnitTest {
    public BaseUnitTest() {
        MockitoAnnotations.initMocks(this);
    }
}
