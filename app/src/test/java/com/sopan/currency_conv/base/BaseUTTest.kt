package com.sopan.currency_conv.base

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Creates base
 */
abstract class BaseUTTest : KoinTest {

    /**
     * For MockWebServer instance
     */
    private lateinit var mMockServerInstance: MockWebServer

    /**
     * Default, let server be shut down
     */
    private var mShouldStart = false

    @Before
    open fun setUp(){
        startMockServer(true)
    }

    /**
     * Helps to read input file returns the respective data in mocked call
     */
    fun mockNetworkResponseWithFileContent(fileName: String, responseCode: Int) = mMockServerInstance.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(fileName))
    )

    /**
     * Reads input file and converts to json
     */
    fun getJson(fileName: String) : String {
        val resourceDirectory: Path = Paths.get("src", "test", "resources")
        val absolutePath: String = resourceDirectory.toFile().absolutePath
        val filePath="$absolutePath\\$fileName"
        val file = File(filePath)
        return String(file.readBytes())
    }

    /**
     * Start Mockwebserver
     */
    private fun startMockServer(shouldStart: Boolean){
        if (shouldStart){
            mShouldStart = shouldStart
            mMockServerInstance = MockWebServer()
            mMockServerInstance.start()
        }
    }

    /**
     * Set Mockwebserver url
     */
    fun getMockWebServerUrl() = mMockServerInstance.url("/").toString()

    /**
     * Stop Mockwebserver
     */
    private fun stopMockServer() {
        if (mShouldStart){
            mMockServerInstance.shutdown()
        }
    }

    @After
    open fun tearDown(){
        //Stop Mock server
        stopMockServer()
        //Stop Koin as well
        stopKoin()
    }
}