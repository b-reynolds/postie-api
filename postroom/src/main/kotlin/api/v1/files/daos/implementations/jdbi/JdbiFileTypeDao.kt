package api.v1.files.daos.implementations.jdbi

import api.v1.files.daos.FileTypeDao
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.kotlin.onDemand

/**
 * Implementation of [FileTypeDao] backed by a [Jdbi] data source.
 */
data class JdbiFileTypeDao(private val dataSource: Jdbi) : FileTypeDao by dataSource.onDemand()
