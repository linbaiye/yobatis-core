package integration.org.nalby.yobatis.core.alltype;

import integration.org.nalby.yobatis.core.AbstractSpringSetup;
import org.junit.Test;
import sandbox.alltype.dao.AllDataTypesDao;
import sandbox.alltype.entity.AllDataTypes;
import sandbox.alltype.entity.criteria.AllDataTypesCriteria;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


/*
Test against this table.
CREATE TABLE `all_data_types` (
        `type_bigint` BIGINT PRIMARY KEY AUTO_INCREMENT,
        `type_varchar` VARCHAR( 20 ),
        `type_tinyint` TINYINT,
        `type_smallint` SMALLINT,
        `type_mediumint` MEDIUMINT,
        `type_int` INT,
        `type_text` TEXT,
        `type_date` DATE,
        `type_float` FLOAT( 10, 2 ),
        `type_double` DOUBLE,
        `type_decimal` DECIMAL( 10, 2 ),
        `type_datetime` DATETIME,
        `type_timestamp` TIMESTAMP,
        `type_time` TIME,
        `type_year` YEAR,
        `type_char` CHAR( 10 ),
        `type_tinyblob` TINYBLOB,
        `type_tinytext` TINYTEXT,
        `type_blob` BLOB,
        `type_mediumblob` MEDIUMBLOB,
        `type_mediumtext` MEDIUMTEXT,
        `type_longblob` LONGBLOB,
        `type_longtext` LONGTEXT,
        `type_enum` ENUM( '1', '2', '3' ),
        `type_set` SET( '1', '2', '3' ),
        `type_bool` BOOL,
        `type_binary` BINARY( 20 ),
        `type_varbinary` VARBINARY( 20 )) ENGINE = INNODB ;
*/



/**
 * The main purpose is to make sure that upgrading Yobatis will not mess source code generated
 * by previous ones. It assures that every method for constructing query has consistent semantics.
 */
public class CriteriaTests extends AbstractSpringSetup {

    @Resource
    private AllDataTypesDao allDataTypesDao;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private AllDataTypes create(int v, Date date, boolean bool) {
        AllDataTypes types = new AllDataTypes();
        String vString = String.valueOf(v);
        types.setTypeBigint((long)v);
        types.setTypeBinary(ByteBuffer.allocate(4).putInt(v).array());
        types.setTypeBlob(ByteBuffer.allocate(4).putInt(v).array());
        types.setTypeBool(bool);
        types.setTypeChar(vString);
        types.setTypeDouble(v + 0.1);
        types.setTypeDecimal(new BigDecimal(vString));
        types.setTypeDate(date);
        types.setTypeDatetime(date);
        types.setTypeEnum(vString);
        types.setTypeFloat(v + 0.1f);
        types.setTypeInt(v);
        types.setTypeLongblob(ByteBuffer.allocate(4).putInt(v).array());
        types.setTypeLongtext(vString);

        types.setTypeMediumblob(ByteBuffer.allocate(4).putInt(v).array());
        types.setTypeMediumint(v);
        types.setTypeMediumtext(vString);
        types.setTypeSet(vString);
        types.setTypeSmallint((short)v);
        types.setTypeText(vString);
        types.setTypeTinyblob(ByteBuffer.allocate(4).putInt(v).array());
        types.setTypeTinyint((byte)v);
        types.setTypeTinytext(vString);
        types.setTypeVarbinary(ByteBuffer.allocate(4).putInt(v).array());
        types.setTypeVarchar(vString);
        types.setTypeTime(date);
        types.setTypeTimestamp(date);
        return types;
    }

    private void assertByteArray(byte[] b1, byte[] b2, int len) {
        for (int i = 0; i < len; i++) {
            assertEquals(b1[i], b2[i]);
        }
    }

    private void assertDate(Date d1, Date d2) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr1 = simpleDateFormat.format(d1);
            String dateStr2 = simpleDateFormat.format(d2);
            assertEquals(dateStr1, dateStr2);
        } catch (Exception e) {
            fail();
        }
    }

    private void assertTime(Date d1, Date d2) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            String dateStr1 = simpleDateFormat.format(d1);
            String dateStr2 = simpleDateFormat.format(d2);
            assertEquals(dateStr1, dateStr2);
        } catch (Exception e) {
            fail();
        }
    }

    private void assertEqual(AllDataTypes types, int v, Date date, boolean bool) {
        String vString = String.valueOf(v);
        assertEquals(v, types.getTypeBigint().intValue());
        assertByteArray(ByteBuffer.allocate(4).putInt(v).array(), types.getTypeBinary(), 4);
        assertByteArray(ByteBuffer.allocate(4).putInt(v).array(), types.getTypeBlob(), 4);
        assertEquals(bool, types.getTypeBool());
        assertByteArray(ByteBuffer.allocate(4).putInt(v).array(), types.getTypeBlob(), 4);
        assertEquals(vString, types.getTypeChar());
        assertEquals(v, types.getTypeDouble(), 0.101);
        assertDate(date, types.getTypeDate());
        assertEquals(date, types.getTypeDatetime());
        assertTrue(new BigDecimal(v).compareTo(types.getTypeDecimal()) == 0);
        assertEquals(vString, types.getTypeEnum());
        assertEquals(v, types.getTypeFloat(), 0.101);
        assertEquals(v, types.getTypeInt().intValue());
        assertByteArray(ByteBuffer.allocate(4).putInt(v).array(), types.getTypeLongblob(), 4);
        assertEquals(vString, types.getTypeLongtext());
        assertByteArray(ByteBuffer.allocate(4).putInt(v).array(), types.getTypeMediumblob(), 4);
        assertEquals(v, types.getTypeMediumint().intValue());
        assertEquals(vString, types.getTypeMediumtext());
        assertEquals(vString ,types.getTypeSet());
        assertEquals(v, types.getTypeSmallint().shortValue());
        assertEquals(vString, types.getTypeText());
        assertByteArray(ByteBuffer.allocate(4).putInt(v).array(), types.getTypeTinyblob(), 4);
        assertEquals(v, types.getTypeTinyint().byteValue());
        assertEquals(vString, types.getTypeTinytext());
        assertByteArray(ByteBuffer.allocate(4).putInt(v).array(), types.getTypeVarbinary(), 4);
        assertTime(date, types.getTypeTime());
        assertEquals(vString, types.getTypeVarchar());
    }

    public void setup() {
        try {
            AllDataTypes types = create(1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);
            allDataTypesDao.insert(types);

            types = create(2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);
            allDataTypesDao.insert(types);

            types = create(3, simpleDateFormat.parse("2018-08-13 13:00:00"), false);
            allDataTypesDao.insert(types);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // `type_bigint` BIGINT PRIMARY KEY AUTO_INCREMENT,
    @Test
    public void selectBigint() throws Exception {
        AllDataTypes allDataTypes = allDataTypesDao.selectOne(AllDataTypesCriteria.typeBigintEqualTo(1L));
        assertEqual(allDataTypes, 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);

        List<AllDataTypes> allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeBigintNotEqualTo(3L));
        assertEqual(allDataTypesList.get(0), 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);
        assertEqual(allDataTypesList.get(1), 2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);

        allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeBigintBetween(1L, 3L));
        assertEqual(allDataTypesList.get(0), 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);
        assertEqual(allDataTypesList.get(1), 2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);
        assertEqual(allDataTypesList.get(2), 3, simpleDateFormat.parse("2018-08-13 13:00:00"), false);

        allDataTypes = allDataTypesDao.selectOne(AllDataTypesCriteria.typeBigintNotBetween(1L, 2L));
        assertEqual(allDataTypes, 3, simpleDateFormat.parse("2018-08-13 13:00:00"), false);

        allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeBigintIsNotNull());
        assertEqual(allDataTypesList.get(0), 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);
        assertEqual(allDataTypesList.get(1), 2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);
        assertEqual(allDataTypesList.get(2), 3, simpleDateFormat.parse("2018-08-13 13:00:00"), false);

        allDataTypes = allDataTypesDao.selectOne(AllDataTypesCriteria.typeBigintIsNull());
        assertNull(allDataTypes);

        allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeBigintIn(Arrays.asList(1L, 2L)));
        assertEqual(allDataTypesList.get(0), 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);
        assertEqual(allDataTypesList.get(1), 2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);

        allDataTypes = allDataTypesDao.selectOne(AllDataTypesCriteria.typeBigintNotIn(Arrays.asList(1L, 2L)));
        assertEqual(allDataTypes, 3, simpleDateFormat.parse("2018-08-13 13:00:00"), false);

        allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeBigintGreaterThan(0L));
        assertEqual(allDataTypesList.get(0), 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);
        assertEqual(allDataTypesList.get(1), 2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);
        assertEqual(allDataTypesList.get(2), 3, simpleDateFormat.parse("2018-08-13 13:00:00"), false);

        allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeBigintGreaterThanOrEqualTo(1L));
        assertEqual(allDataTypesList.get(0), 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);
        assertEqual(allDataTypesList.get(1), 2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);
        assertEqual(allDataTypesList.get(2), 3, simpleDateFormat.parse("2018-08-13 13:00:00"), false);

        allDataTypes = allDataTypesDao.selectOne(AllDataTypesCriteria.typeBigintLessThan(2L));
        assertEqual(allDataTypes, 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);

        allDataTypes = allDataTypesDao.selectOne(AllDataTypesCriteria.typeBigintLessThanOrEqualTo(1L));
        assertEqual(allDataTypes, 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);
    }

    // type_bool bool;
    @Test
    public void selectBool() throws Exception {
        AllDataTypes allDataTypes = allDataTypesDao.selectOne(AllDataTypesCriteria.typeBoolEqualTo(true));
        assertEqual(allDataTypes, 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);

        List<AllDataTypes> allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeBoolEqualTo(false));
        assertEqual(allDataTypesList.get(0), 2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);
        assertEqual(allDataTypesList.get(1), 3, simpleDateFormat.parse("2018-08-13 13:00:00"), false);

        allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeBoolIsNotNull());
        assertEqual(allDataTypesList.get(0), 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);
        assertEqual(allDataTypesList.get(1), 2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);
        assertEqual(allDataTypesList.get(2), 3, simpleDateFormat.parse("2018-08-13 13:00:00"), false);

        allDataTypes = allDataTypesDao.selectOne(AllDataTypesCriteria.typeBoolIsNull());
        assertNull(allDataTypes);
    }

    // type_char char(10);
    @Test
    public void selectChar() throws Exception {
        AllDataTypes allDataTypes = allDataTypesDao.selectOne(AllDataTypesCriteria.typeCharEqualTo("1"));
        assertEqual(allDataTypes, 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);

        List<AllDataTypes> allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeCharNotEqualTo("3"));
        assertEqual(allDataTypesList.get(0), 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);
        assertEqual(allDataTypesList.get(1), 2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);

        allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeCharBetween("1", "3"));
        assertEqual(allDataTypesList.get(0), 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);
        assertEqual(allDataTypesList.get(1), 2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);
        assertEqual(allDataTypesList.get(2), 3, simpleDateFormat.parse("2018-08-13 13:00:00"), false);

        allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeCharNotBetween("1", "3"));
        assertTrue(allDataTypesList.isEmpty());

        allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeCharIsNull());
        assertTrue(allDataTypesList.isEmpty());

        allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeCharIsNotNull());
        assertEqual(allDataTypesList.get(0), 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);
        assertEqual(allDataTypesList.get(1), 2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);
        assertEqual(allDataTypesList.get(2), 3, simpleDateFormat.parse("2018-08-13 13:00:00"), false);

        allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeCharIn(Arrays.asList("1", "2")));
        assertEqual(allDataTypesList.get(0), 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);
        assertEqual(allDataTypesList.get(1), 2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);

        allDataTypes = allDataTypesDao.selectOne(AllDataTypesCriteria.typeCharNotIn(Arrays.asList("1", "2")));
        assertEqual(allDataTypes, 3, simpleDateFormat.parse("2018-08-13 13:00:00"), false);

        allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeCharGreaterThan("0"));
        assertEqual(allDataTypesList.get(0), 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);
        assertEqual(allDataTypesList.get(1), 2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);
        assertEqual(allDataTypesList.get(2), 3, simpleDateFormat.parse("2018-08-13 13:00:00"), false);

        allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeCharGreaterThanOrEqualTo("1"));
        assertEqual(allDataTypesList.get(0), 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);
        assertEqual(allDataTypesList.get(1), 2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);
        assertEqual(allDataTypesList.get(2), 3, simpleDateFormat.parse("2018-08-13 13:00:00"), false);

        allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeCharLike("%").ascOrderBy("type_bigint"));
        assertEqual(allDataTypesList.get(0), 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);
        assertEqual(allDataTypesList.get(1), 2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);
        assertEqual(allDataTypesList.get(2), 3, simpleDateFormat.parse("2018-08-13 13:00:00"), false);

        allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeCharNotLike("1%").ascOrderBy("type_bigint"));
        assertEqual(allDataTypesList.get(0), 2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);
        assertEqual(allDataTypesList.get(1), 3, simpleDateFormat.parse("2018-08-13 13:00:00"), false);

        allDataTypes = allDataTypesDao.selectOne(AllDataTypesCriteria.typeCharLessThan("2"));
        assertEqual(allDataTypes, 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);

        allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeCharLessThanOrEqualTo("3"));
        assertEqual(allDataTypesList.get(0), 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);
        assertEqual(allDataTypesList.get(1), 2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);
    }

    @Test
    public void selectDouble() throws Exception {
        AllDataTypes allDataTypes = allDataTypesDao.selectOne(AllDataTypesCriteria.typeDoubleEqualTo(1.1));
        assertEqual(allDataTypes, 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);

        List<AllDataTypes> allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeDoubleNotEqualTo(3.1));
        assertEqual(allDataTypesList.get(0), 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);
        assertEqual(allDataTypesList.get(1), 2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);

        allDataTypesList = allDataTypesDao.selectList(AllDataTypesCriteria.typeDoubleBetween(0.0, 2.5));
        assertEqual(allDataTypesList.get(0), 1, simpleDateFormat.parse("2018-08-11 13:00:00"), true);
        assertEqual(allDataTypesList.get(1), 2, simpleDateFormat.parse("2018-08-12 13:00:00"), false);

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeDoubleNotBetween(0.0, 2.5)));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeDoubleIsNull()));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeDoubleIsNotNull()));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeDoubleGreaterThan(2.4)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeDoubleGreaterThanOrEqualTo(2.1)));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeDoubleLessThan(1.1)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeDoubleLessThanOrEqualTo(1.1)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeDoubleIn(Arrays.asList(1.1, 2.1))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeDoubleNotIn(Arrays.asList(1.1, 2.1))));
    }

    @Test
    public void selectDecimal() throws Exception {
        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeDecimalEqualTo(new BigDecimal(1))));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeDecimalNotEqualTo(new BigDecimal(1))));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria
                .typeDecimalBetween(new BigDecimal(1), new BigDecimal(2))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria
                .typeDecimalNotBetween(new BigDecimal(1), new BigDecimal(2))));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeDecimalIsNull()));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeDecimalIsNotNull()));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeDecimalGreaterThan(new BigDecimal(2))));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeDecimalGreaterThanOrEqualTo(new BigDecimal(2))));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeDecimalLessThan(new BigDecimal(3))));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeDecimalLessThanOrEqualTo(new BigDecimal(3))));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeDecimalIn(
                Arrays.asList(new BigDecimal(2), new BigDecimal(3)))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeDecimalNotIn(
                Arrays.asList(new BigDecimal(2), new BigDecimal(3)))));
    }

    @Test
    public void selectDate() throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2018-08-11");
        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeDateEqualTo(date)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeDateNotEqualTo(date)));

        assertEquals(2, allDataTypesDao.count(
                AllDataTypesCriteria.typeDateBetween(date, simpleDateFormat.parse("2018-08-12"))));

        assertEquals(1, allDataTypesDao.count(
                AllDataTypesCriteria.typeDateNotBetween(date, simpleDateFormat.parse("2018-08-12"))));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeDateLessThan(date)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeDateLessThanOrEqualTo(date)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeDateGreaterThan(date)));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeDateGreaterThanOrEqualTo(date)));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeDateIsNotNull()));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeDateIsNull()));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeDateNotIn(Arrays.asList(
                date, simpleDateFormat.parse("2018-08-12")
        ))));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeDateIn(Arrays.asList(
                date, simpleDateFormat.parse("2018-08-12")
        ))));

        SimpleDateFormat simpleDatetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = simpleDatetimeFormat.parse("2018-08-11 23:01:02");
        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeDateEqualTo(date)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeDateNotEqualTo(date)));

        date = simpleDatetimeFormat.parse("2018-08-11 13:01:02");

        assertEquals(2, allDataTypesDao.count(
                AllDataTypesCriteria.typeDateBetween(date, simpleDatetimeFormat.parse("2018-08-12 00:00:01"))));

        assertEquals(1, allDataTypesDao.count(
                AllDataTypesCriteria.typeDateNotBetween(date, simpleDatetimeFormat.parse("2018-08-12 11:11:01"))));

        date = simpleDatetimeFormat.parse("2018-08-11 13:01:02");
        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeDateLessThan(date)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeDateLessThanOrEqualTo(date)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeDateGreaterThan(date)));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeDateGreaterThanOrEqualTo(date)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeDateNotIn(Arrays.asList(
                date, simpleDateFormat.parse("2018-08-12")
        ))));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeDateIn(Arrays.asList(
                date, simpleDateFormat.parse("2018-08-12")
        ))));

    }

    @Test
    public void selectDatetime() throws Exception {
        Date date = simpleDateFormat.parse("2018-08-11 13:00:00");
        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeDatetimeEqualTo(date)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeDatetimeNotEqualTo(date)));

        assertEquals(2, allDataTypesDao.count(
                AllDataTypesCriteria.typeDatetimeBetween(date, simpleDateFormat.parse("2018-08-12 13:00:00"))));

        assertEquals(1, allDataTypesDao.count(
                AllDataTypesCriteria.typeDatetimeNotBetween(date, simpleDateFormat.parse("2018-08-12 13:00:00"))));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeDatetimeLessThan(date)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeDatetimeLessThanOrEqualTo(date)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeDatetimeGreaterThan(date)));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeDatetimeGreaterThanOrEqualTo(date)));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeDatetimeIsNotNull()));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeDatetimeIsNull()));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeDatetimeNotIn(Arrays.asList(
                date, simpleDateFormat.parse("2018-08-12 13:00:00")
        ))));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeDatetimeIn(Arrays.asList(
                date, simpleDateFormat.parse("2018-08-12 13:00:00")
        ))));
    }

    @Test
    public void selectEnum() throws Exception {
        String vString = "1";
        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeEnumEqualTo(vString)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeEnumNotEqualTo(vString)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeEnumBetween("3", "5")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeEnumNotBetween("3", "5")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeEnumGreaterThanOrEqualTo("2")));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeEnumGreaterThan("2")));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeEnumIsNull()));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeEnumIsNotNull()));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeEnumLessThan("2")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeEnumLessThanOrEqualTo("2")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeEnumIn(Arrays.asList("1", "2"))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeEnumNotIn(Arrays.asList("1", "2"))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeEnumLike("1%")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeEnumNotLike("1%")));
    }

    @Test
    public void selectFloat() throws Exception {
        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeFloatEqualTo(1.1f)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeFloatNotEqualTo(1.1f)));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeFloatLessThan(1.1f)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeFloatLessThanOrEqualTo(1.1f)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeFloatBetween(1.0f, 1.2f)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeFloatNotBetween(1.0f, 1.2f)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeFloatGreaterThan(2.2f)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeFloatGreaterThanOrEqualTo(2.09f)));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeFloatIsNotNull()));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeFloatIsNull()));

        allDataTypesDao.count(AllDataTypesCriteria.typeFloatIn(Arrays.asList(1.1f, 2.1f)));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeFloatNotIn(Arrays.asList(0.1f, 0.2f))));
    }

    @Test
    public void selectInt() {
        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeIntEqualTo(1)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeIntNotEqualTo(2)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeIntLessThan(2)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeIntLessThanOrEqualTo(2)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeIntGreaterThan(2)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeIntGreaterThanOrEqualTo(2)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeIntBetween(1, 2)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeIntNotBetween(1, 2)));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeIntIsNull()));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeIntIsNotNull()));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeIntIn(Arrays.asList(1, 2))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeIntNotIn(Arrays.asList(1, 2))));
    }

    @Test
    public void selectLongText() {
        String vString = "1";
        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeLongtextEqualTo(vString)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeLongtextNotEqualTo(vString)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeLongtextBetween("3", "5")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeLongtextNotBetween("3", "5")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeLongtextGreaterThanOrEqualTo("2")));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeLongtextGreaterThan("2")));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeLongtextIsNull()));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeLongtextIsNotNull()));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeLongtextLessThan("2")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeLongtextLessThanOrEqualTo("2")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeLongtextIn(Arrays.asList("1", "2"))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeLongtextNotIn(Arrays.asList("1", "2"))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeLongtextLike("1%")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeLongtextNotLike("1%")));
    }

    @Test
    public void selectMediumMediumint() {
        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeMediumintEqualTo(1)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeMediumintNotEqualTo(2)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeMediumintLessThan(2)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeMediumintLessThanOrEqualTo(2)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeMediumintGreaterThan(2)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeMediumintGreaterThanOrEqualTo(2)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeMediumintBetween(1, 2)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeMediumintNotBetween(1, 2)));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeMediumintIsNull()));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeMediumintIsNotNull()));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeMediumintIn(Arrays.asList(1, 2))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeMediumintNotIn(Arrays.asList(1, 2))));

    }

    @Test
    public void selectMediumtext() {
        String vString = "1";
        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeMediumtextEqualTo(vString)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeMediumtextNotEqualTo(vString)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeMediumtextBetween("3", "5")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeMediumtextNotBetween("3", "5")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeMediumtextGreaterThanOrEqualTo("2")));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeMediumtextGreaterThan("2")));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeMediumtextIsNull()));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeMediumtextIsNotNull()));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeMediumtextLessThan("2")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeMediumtextLessThanOrEqualTo("2")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeMediumtextIn(Arrays.asList("1", "2"))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeMediumtextNotIn(Arrays.asList("1", "2"))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeMediumtextLike("1%")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeMediumtextNotLike("1%")));
    }

    @Test
    public void selectSet() {
        String vString = "1";
        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeSetEqualTo(vString)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeSetNotEqualTo(vString)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeSetBetween("3", "5")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeSetNotBetween("3", "5")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeSetGreaterThanOrEqualTo("2")));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeSetGreaterThan("2")));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeSetIsNull()));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeSetIsNotNull()));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeSetLessThan("2")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeSetLessThanOrEqualTo("2")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeSetIn(Arrays.asList("1", "2"))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeSetNotIn(Arrays.asList("1", "2"))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeSetLike("1%")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeSetNotLike("1%")));
    }

    @Test
    public void selectSmallint() {
        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeSmallintEqualTo((short)1)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeSmallintNotEqualTo((short)2)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeSmallintLessThan((short)2)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeSmallintLessThanOrEqualTo((short)2)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeSmallintGreaterThan((short)2)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeSmallintGreaterThanOrEqualTo((short)2)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeSmallintBetween((short)1, (short)2)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeSmallintNotBetween((short)1, (short)2)));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeSmallintIsNull()));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeSmallintIsNotNull()));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeSmallintIn(Arrays.asList((short)1, (short)2))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeSmallintNotIn(Arrays.asList((short)1, (short)2))));
    }

    @Test
    public void selectText() {
        String vString = "1";
        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTextEqualTo(vString)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTextNotEqualTo(vString)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTextBetween("3", "5")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTextNotBetween("3", "5")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTextGreaterThanOrEqualTo("2")));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTextGreaterThan("2")));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeTextIsNull()));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeTextIsNotNull()));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTextLessThan("2")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTextLessThanOrEqualTo("2")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTextIn(Arrays.asList("1", "2"))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTextNotIn(Arrays.asList("1", "2"))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTextLike("1%")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTextNotLike("1%")));
    }

    @Test
    public void selectTinyint() {
        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTinyintEqualTo((byte)1)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTinyintNotEqualTo((byte)2)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTinyintLessThan((byte)2)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTinyintLessThanOrEqualTo((byte)2)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTinyintGreaterThan((byte)2)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTinyintGreaterThanOrEqualTo((byte)2)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTinyintBetween((byte)1, (byte)2)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTinyintNotBetween((byte)1, (byte)2)));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeTinyintIsNull()));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeTinyintIsNotNull()));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTinyintIn(Arrays.asList((byte)1, (byte)2))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTinyintNotIn(Arrays.asList((byte)1, (byte)2))));
    }

    @Test
    public void selectTinytext() {
        String vString = "1";
        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTinytextEqualTo(vString)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTinytextNotEqualTo(vString)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTinytextBetween("3", "5")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTinytextNotBetween("3", "5")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTinytextGreaterThanOrEqualTo("2")));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTinytextGreaterThan("2")));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeTinytextIsNull()));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeTinytextIsNotNull()));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTinytextLessThan("2")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTinytextLessThanOrEqualTo("2")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTinytextIn(Arrays.asList("1", "2"))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTinytextNotIn(Arrays.asList("1", "2"))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTinytextLike("1%")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTinytextNotLike("1%")));
    }

    @Test
    public void selectVarchar() {
        String vString = "1";
        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeVarcharEqualTo(vString)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeVarcharNotEqualTo(vString)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeVarcharBetween("3", "5")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeVarcharNotBetween("3", "5")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeVarcharGreaterThanOrEqualTo("2")));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeVarcharGreaterThan("2")));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeVarcharIsNull()));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeVarcharIsNotNull()));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeVarcharLessThan("2")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeVarcharLessThanOrEqualTo("2")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeVarcharIn(Arrays.asList("1", "2"))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeVarcharNotIn(Arrays.asList("1", "2"))));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeVarcharLike("1%")));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeVarcharNotLike("1%")));

    }

    @Test
    public void selectTimestamp() throws Exception {
        Date date = simpleDateFormat.parse("2018-08-11 13:00:00");
        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTimestampEqualTo(date)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTimestampNotEqualTo(date)));

        assertEquals(2, allDataTypesDao.count(
                AllDataTypesCriteria.typeTimestampBetween(date, simpleDateFormat.parse("2018-08-12 13:00:00"))));

        assertEquals(1, allDataTypesDao.count(
                AllDataTypesCriteria.typeTimestampNotBetween(date, simpleDateFormat.parse("2018-08-12 13:00:00"))));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeTimestampLessThan(date)));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTimestampLessThanOrEqualTo(date)));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTimestampGreaterThan(date)));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeTimestampGreaterThanOrEqualTo(date)));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeTimestampIsNotNull()));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeTimestampIsNull()));

        assertEquals(1, allDataTypesDao.count(AllDataTypesCriteria.typeTimestampNotIn(Arrays.asList(
                date, simpleDateFormat.parse("2018-08-12 13:00:00")
        ))));

        assertEquals(2, allDataTypesDao.count(AllDataTypesCriteria.typeTimestampIn(Arrays.asList(
                date, simpleDateFormat.parse("2018-08-12 13:00:00")
        ))));
    }

    @Test
    public void selectBinary() {
        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeBlobIsNotNull()));
        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeBlobIsNull()));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeBinaryIsNotNull()));
        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeBinaryIsNull()));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeVarbinaryIsNotNull()));
        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeVarbinaryIsNull()));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeTinyblobIsNotNull()));
        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeTinyblobIsNull()));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeLongblobIsNotNull()));
        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeLongblobIsNull()));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeMediumblobIsNotNull()));
        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeMediumblobIsNull()));
    }

    @Test
    public void selectTime() throws Exception {
        Date date = simpleDateFormat.parse("2018-08-11 13:00:00");
        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeTimeEqualTo(date)));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeTimeNotEqualTo(date)));

        assertEquals(3, allDataTypesDao.count(
                AllDataTypesCriteria.typeTimeBetween(date, simpleDateFormat.parse("2018-08-12 14:00:00"))));

        assertEquals(0, allDataTypesDao.count(
                AllDataTypesCriteria.typeTimeNotBetween(date, simpleDateFormat.parse("2018-08-12 13:00:00"))));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeTimeLessThan(date)));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeTimeLessThanOrEqualTo(date)));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeTimeGreaterThan(date)));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeTimeGreaterThanOrEqualTo(date)));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeTimeIsNotNull()));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeTimeIsNull()));

        assertEquals(0, allDataTypesDao.count(AllDataTypesCriteria.typeTimeNotIn(Arrays.asList(
                date, simpleDateFormat.parse("2018-08-12 13:00:00")
        ))));

        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeTimeIn(Arrays.asList(
                date, simpleDateFormat.parse("2018-08-12 13:00:00")
        ))));
    }

    @Test
    public void variousMethods() {
        AllDataTypesCriteria criteria = new AllDataTypesCriteria();
        criteria.setForUpdate(true).setLimit(1L).setOffset(1L).or();
        criteria.descOrderBy("type_time").ascOrderBy("type_time");
        assertEquals(3, allDataTypesDao.count(AllDataTypesCriteria.typeBigintIsNotNull()));
    }

}
