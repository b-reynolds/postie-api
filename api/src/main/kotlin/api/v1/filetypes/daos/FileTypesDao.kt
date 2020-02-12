package api.v1.filetypes.daos

import api.v1.files.models.File
import api.v1.filetypes.models.FileType
import org.jdbi.v3.sqlobject.kotlin.RegisterKotlinMapper
import org.jdbi.v3.sqlobject.statement.SqlQuery

/**
 * Provides access to the `file_types` table.
 */
interface FileTypesDao {
    /**
     * Returns `true` if a record exists within the specified [id].
     */
    @SqlQuery("select exists(select 1 from file_types where id = ?)")
    fun contains(id: Int): Boolean

    /**
     * Returns all file types.
     */
    @SqlQuery("select * from file_types")
    @RegisterKotlinMapper(File::class)
    fun get(): List<FileType>

    /**
     * Returns the file type associated with the specified [id] or `null` if one does not exist.
     */
    @SqlQuery("select * from file_types where id = ?")
    @RegisterKotlinMapper(File::class)
    fun get(id: Int): FileType?
}
