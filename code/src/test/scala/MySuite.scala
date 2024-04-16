import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MySuite extends AnyFlatSpec with Matchers {
  "Test" should "example test that succeeds" in {
    val obtained = 42
    val expected = 42
    obtained shouldBe expected
  }
}
