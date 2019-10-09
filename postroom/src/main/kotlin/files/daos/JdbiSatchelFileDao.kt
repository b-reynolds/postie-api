package files.daos

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.kotlin.onDemand

/**
 * Implementation of [SatchelFileDao] backed by a [Jdbi] data source.
 */
data class JdbiSatchelFileDao(private val dataSource: Jdbi) : SatchelFileDao by dataSource.onDemand()