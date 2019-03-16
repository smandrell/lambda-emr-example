import java.io.{InputStream, OutputStream}

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

import model._

object LambdaEntry extends App with RequestStreamHandler {

  override def handleRequest(inputStream: InputStream, outputStream: OutputStream, context: Context): Unit = {

    // Create object mapper for mapping JSON -> case classes
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)

    // Construct EMR case classes from EMR config file
    val emrConfig = Source.fromFile("./emrConfig.json")
    mapper.readValue[RunJobFlowConfig](json.reader())

    // Create EMR client
    val emrClient = createEmrClient(emrConfig.emrRegion, new DefaultAWSCredentialsProviderChain)

    // Create job flow request for spark job and run the job on EMR
    val runJobFlowRequest = emrConfig.runJobFlowRequest()
    emrClient.runJobFlow(runJobFlowRequest)

  }

}