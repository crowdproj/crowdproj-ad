import com.crowdproj.ad.repo.ydb.YdbConnection
import com.crowdproj.ad.repo.ydb.model.AdEntity
import kotlin.test.Test

class GoTest {
    @Test
    fun go() {
        val conn = YdbConnection("lksjd")
        conn.save(AdEntity(id = "111"))
    }
}
