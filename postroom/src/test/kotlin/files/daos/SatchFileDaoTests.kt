package files.daos

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.testcontainers.junit.jupiter.Container
import utils.KPostgreSQLContainer

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class SatchelFileDaoTests {
    @Container
    private val container = KPostgreSQLContainer.testInstance().apply { start() }

    private val satchelFileDao = JdbiSatchelFileDao(
        Jdbi.create(container.connectionString).apply {
            installPlugin(KotlinSqlObjectPlugin())
            installPlugin(KotlinPlugin())
        }
    )

    @Test
    fun `Get returns null if a file with the specified ID could not be found`() {
        Assertions.assertNull(satchelFileDao.get(1))
    }

    @Test
    fun `Get returns non-null if a file with the specified ID is found`() {
        val fileId = satchelFileDao.insert("", 1, "", null).id

        Assertions.assertNotNull(satchelFileDao.get(fileId))
    }
}