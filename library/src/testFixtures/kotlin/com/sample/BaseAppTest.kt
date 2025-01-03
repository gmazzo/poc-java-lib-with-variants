package com.sample

import org.junit.Assert.assertEquals
import org.junit.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.SpringVersion

@SpringBootTest
abstract class BaseAppTest(private val expectedVersion: String) {

    @Test
    fun contextLoads() {
        assertEquals(expectedVersion, SpringVersion.getVersion())
    }

}
