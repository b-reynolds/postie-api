package files.daos

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.kotlin.onDemand

/**
 * Implementation of [SatchelFileTypeDao] backed by a [Jdbi] data source.
 */
data class JdbiSatchelFileTypeDao(private val dataSource: Jdbi) : SatchelFileTypeDao by dataSource.onDemand()