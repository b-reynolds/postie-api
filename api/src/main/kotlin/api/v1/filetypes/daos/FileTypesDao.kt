package api.v1.filetypes.daos

import api.v1.files.models.File
import api.v1.filetypes.models.FileType
import org.jdbi.v3.sqlobject.kotlin.RegisterKotlinMapper
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

/**
 * Provides access to the `file_types` table.
 */
interface FileTypesDao {
    /**
     * Inserts a new record within the `file_types` table.
     */
    @SqlUpdate("insert into file_types (name) values(?)")
    @GetGeneratedKeys
    @RegisterKotlinMapper(FileType::class)
    fun insert(name: String): FileType

    /**
     * Returns `true` if a record exists within the specified [id].
     */
    @SqlQuery("select exists(select 1 from file_types where id = ?)")
    fun contains(id: Int): Boolean

    /**
     * Returns `true` if a record exists within the specified [name].
     */
    @SqlQuery("select exists(select 1 from file_types where name = ?)")
    fun contains(name: String): Boolean

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

    /**
     * Returns the file type associated with the specified [name] or `null` if one does not exist.
     */
    @SqlQuery("select * from file_types where name = ?")
    @RegisterKotlinMapper(File::class)
    fun get(name: String): FileType?
}
