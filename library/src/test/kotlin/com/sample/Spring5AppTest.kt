package com.sample

import org.junit.Assert.assertEquals
import org.junit.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.SpringVersion

@SpringBootTest
class Spring5AppTest : BaseAppTest("5.3.31")
