
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class Demo extends Simulation {
  val threads   = Integer.getInteger("users",  10)
  val rampup    = java.lang.Long.getLong("rampup", 1)
  val duration  = java.lang.Long.getLong("duration", 5)
  val assertion   = Integer.getInteger("assertion", 1000)

  val httpConf = http.baseURL("<URL>")
    .header("Content-Type", "application/json")

  val myScenario = scenario("scenario")
    .during(duration seconds) {
           exec(getLops.req)
               .exec(REQUEST)
    }
  setUp(myScenario.inject(rampUsers(threads) over (rampup seconds))).protocols(httpConf)
  .assertions(
      global.responseTime.max.lessThan(assertion)
    )
}
