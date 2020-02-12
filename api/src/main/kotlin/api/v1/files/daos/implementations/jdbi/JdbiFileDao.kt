package api.v1.files.daos.implementations.jdbi

import api.v1.files.daos.FileDao
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.kotlin.onDemand

/**
 * Implementation of [FileDao] backed by a [Jdbi] data source.
 */
data class JdbiFileDao(private val dataSource: Jdbi) : FileDao by dataSource.onDemand()
