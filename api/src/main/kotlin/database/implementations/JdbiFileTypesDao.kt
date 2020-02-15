package database.implementations

import database.daos.FileTypesDao
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.kotlin.onDemand

/**
 * Implementation of [FileTypesDao] backed by a [Jdbi] data source.
 */
data class JdbiFileTypesDao(private val dataSource: Jdbi) : FileTypesDao by dataSource.onDemand()
