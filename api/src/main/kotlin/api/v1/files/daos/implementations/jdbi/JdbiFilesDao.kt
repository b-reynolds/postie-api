package api.v1.files.daos.implementations.jdbi

import api.v1.files.daos.FilesDao
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.kotlin.onDemand

/**
 * Implementation of [FilesDao] backed by a [Jdbi] data source.
 */
data class JdbiFilesDao(private val dataSource: Jdbi) : FilesDao by dataSource.onDemand()
