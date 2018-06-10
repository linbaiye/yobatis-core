package func.compoundkey.dao;

import func.compoundkey.model.criteria.BaseCriteria;
import java.util.List;

/*
 * Do NOT modify, it will be overwrote every time yobatis runs.
 */
public interface BaseDao<T extends B, B, PK> {
    /**
     * Insert all fields of the record into table.
     * @param record the record to insert.
     * @return 1 if inserted successfully.
     * @throws IllegalArgumentException if record is null.
     */
    int insertAll(B record);

    /**
     * Insert all fields of the record into table.
     * @param record the record to insert.
     * @return 1 if inserted successfully, 0 if the insertion can not be done.
     * @throws IllegalArgumentException if record is null.
     */
    int insertAllIgnore(B record);

    /**
     * Insert non-null fields into the table. If the table has an auto_increment pk,
     * the primary key field will hold the generated key after insertion.
     * @param record the record to insert.
     * @return 1 if inserted successfully.
     * @throws IllegalArgumentException if record is null.
     */
    int insert(B record);

    /**
     * Select a record by primary key.
     * @param pk the primary key.
     * @return the record if found, null else.
     * @throws IllegalArgumentException if pk is null.
     */
    T selectOne(PK pk);

    /**
     * Select a record by criteria, a MybatisSystemException will be thrown if
     * more than one records meet the criteria.
     * @param criteria the criteria.
     * @return the record if one single record is selected, null if none selected.
     * @throws IllegalArgumentException if criteria is null or empty.
     * @throws MybatisSystemException if more than one records are yielded by the criteria.
     */
    T selectOne(BaseCriteria criteria);

    /**
     * Select records by criteria.
     * @param criteria the criteria.
     * @return a list of selected records, or an empty list if none meets the criteria.
     * @throws IllegalArgumentException if criteria is null or empty.
     */
    List<T> selectList(BaseCriteria criteria);

    /**
     * Count row number of the whole table.
     * @return the row number.
     */
    long countAll();

    /**
     * Count row number by criteria.
     * @param criteria the criteria to query rows.
     * @return the number of rows that meet the criteria.
     */
    long count(BaseCriteria criteria);

    /**
     * Update the record by primary key, null fields are ignored.
     * @param record the record that holds new values and the primary key.
     * @return 1 if updated successfully, 0 if no such a record.
     * @throws IllegalArgumentException if record is null.
     */
    int update(B record);

    /**
     * Update the record by primary key, all fields including null ones will be updated.
     * @param record the record that holds new values and the primary key.
     * @return 1 if updated successfully, 0 if no such a record.
     * @throws IllegalArgumentException if record is null.
     */
    int updateAll(B record);

    /**
     * Update non-null fields of the {@code record} to corresponding columns of the table.
     * @param record the new values.
     * @param criteria to query rows to update.
     * @return the number of affected rows.
     */
    int update(B record, BaseCriteria criteria);

    /**
     * Update all fields of the {@code record} to corresponding columns of the table.
     * @param record the new values.
     * @param criteria to query rows to update.
     * @return the number of affected rows.
     */
    int updateAll(B record, BaseCriteria criteria);

    /**
     * Delete the record by primary key.
     * @param pk the primary key.
     * @return 1 if deleted successfully, 0 if no such a record.
     * @throws IllegalArgumentException if pk is null.
     */
    int delete(PK pk);

    /**
     * Delete records by criteria.
     * @param criteria the criteria.
     * @return the number of deleted records.
     * @throws IllegalArgumentException if criteria is null or empty.
     */
    int delete(BaseCriteria criteria);
}