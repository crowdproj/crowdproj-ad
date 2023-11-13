import com.crowdproj.ad.repo.ydb.YdbConnection
import com.crowdproj.ad.repo.ydb.model.AdEntity
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore
class GoTest {
    @Test
    fun go() {
        val conn = YdbConnection("lksjd")
        conn.save(AdEntity(id = "111"))
    }
}
