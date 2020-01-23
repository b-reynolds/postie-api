package files.daos

import files.models.SatchelFile
import org.jdbi.v3.sqlobject.kotlin.RegisterKotlinMapper
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import java.sql.Timestamp

/**
 * Provides access to the `files` table.
 */
interface SatchelFileDao {
    /**
     * Inserts a new record within the `files` table.
     */
    @SqlUpdate("insert into files (name, file_type_id, contents, expires_at) values(?, ?, ?, ?)")
    @GetGeneratedKeys
    @RegisterKotlinMapper(SatchelFile::class)
    fun insert(name: String, fileTypeId: Int, contents: String, expiresAt: Timestamp?): SatchelFile

    /**
     * Returns the record associated with the specified [id] or `null` if one does not exist.
     */
    @SqlQuery("select * from files where id = ?::uuid and (expires_at is null or expires_at > now())")
    @RegisterKotlinMapper(SatchelFile::class)
    fun get(id: String): SatchelFile?
}