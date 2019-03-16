import java.io.{InputStream, OutputStream}


object LambdaEntry extends App with RequestStreamHandler {

  override def handleRequest(inputStream: InputStream, outputStream: OutputStream, context: Context): Unit = {

    // Create EMR client
    val emrClient = createEmrClient("us-west-2", new DefaultAWSCredentialsProviderChain)

    // Create job flow request for spark job and run the job on EMR
    val runJobFlowRequest = createRunJobFlowRequest()
    emrClient.runJobFlow(runJobFlowRequest)

  }

}