package com.sample

import org.junit.Assert.assertEquals
import org.junit.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.SpringVersion

@SpringBootTest
class DemoSpring6AppTest {

    @Test
    fun contextLoads() {
        assertEquals("5.3.31", SpringVersion.getVersion())
    }

}
