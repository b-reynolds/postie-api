package files.daos

import org.jdbi.v3.sqlobject.statement.SqlQuery

/**
 * Provides access to the `file_types` table.
 */
interface SatchelFileTypeDao {
    /**
     * Returns `true` if a record exists within the specified [id].
     */
    @SqlQuery("select exists(select 1 from file_types where id = ?)")
    fun contains(id: Int): Boolean
}