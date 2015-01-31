package com.joseonline.android.imagesearcher.models;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import android.database.Cursor;
import android.util.Log;

/**
 * RecentSearch Query model for suggestions
 */
@Table(name = "RecentSearches")
public class RecentSearch extends Model {

    @Column(name = "query", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String query;

    public RecentSearch() {
        super();
    }

    public RecentSearch(String query) {
        super();
        this.query = query;
    }

    /**
     * @param query
     * @return
     */
    public static Cursor getRecentSearchesCursor(String query) {
        String tableName = Cache.getTableInfo(RecentSearch.class).getTableName();

        String sqlQuery = new Select(tableName + ".*, " + tableName + ".Id AS _id").
                from(RecentSearch.class).
                where("instr(" + tableName + ".query, '" + query + "') > 0").toSql();
        Log.i("SQL", "SQL Query: " + sqlQuery);
        Cursor recentSearchesCursor = Cache.openDatabase().rawQuery(sqlQuery, null);
        return recentSearchesCursor;
    }
}
