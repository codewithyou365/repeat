package dao

import org.codewithyou365.ConfigGit
import org.codewithyou365.Database

class ConfigGitDao(private val dbRef: Database) {
    fun insert(url: String, branch: String) =
        dbRef.databaseQueries.insert(url, branch)

    fun selectAll(): List<ConfigGit> =
        dbRef.databaseQueries
            .selectAll()
            .executeAsList()

    fun update(newUrl: String, newBranch: String, oldUrl: String, oldBranch: String) =
        dbRef.databaseQueries.update(newUrl, newBranch, oldUrl, oldBranch)

    fun delete(url: String, branch: String) =
        dbRef.databaseQueries.delete(url, branch)
}


