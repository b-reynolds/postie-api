package api.v1.filetypes.daos.implementations.jdbi

import api.v1.filetypes.daos.FileTypesDao
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.kotlin.onDemand

/**
 * Implementation of [FileTypesDao] backed by a [Jdbi] data source.
 */
data class JdbiFileTypesDao(private val dataSource: Jdbi) : FileTypesDao by dataSource.onDemand()
