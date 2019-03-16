import com.amazonaws.services.elasticmapreduce.model.Application

case class ApplicationConfig(name: String) {

  val emrApplication: Application =
    new Application()
      .withName(name)

}
