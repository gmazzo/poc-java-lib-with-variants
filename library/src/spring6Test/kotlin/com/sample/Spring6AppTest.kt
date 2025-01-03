package com.sample

import org.junit.Assert.assertEquals
import org.junit.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.SpringVersion

@SpringBootTest
class Spring6AppTest {

    @Test
    fun contextLoads() {
        assertEquals("6.2.1", SpringVersion.getVersion())
    }

}
