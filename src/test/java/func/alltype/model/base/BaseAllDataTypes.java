package func.alltype.model.base;

import java.math.BigDecimal;
import java.util.Date;

public abstract class BaseAllDataTypes {
    private Long typeBigint;

    private String typeVarchar;

    private Byte typeTinyint;

    private Short typeSmallint;

    private Integer typeMediumint;

    private Integer typeInt;

    private Date typeDate;

    private Float typeFloat;

    private Double typeDouble;

    private BigDecimal typeDecimal;

    private Date typeDatetime;

    private Date typeTimestamp;

    private Date typeTime;

    private Date typeYear;

    private String typeChar;

    private String typeTinytext;

    private String typeEnum;

    private String typeSet;

    private Boolean typeBool;

    private String typeText;

    private byte[] typeTinyblob;

    private byte[] typeBlob;

    private byte[] typeMediumblob;

    private String typeMediumtext;

    private byte[] typeLongblob;

    private String typeLongtext;

    private byte[] typeBinary;

    private byte[] typeVarbinary;

    public Long getTypeBigint() {
        return typeBigint;
    }

    public void setTypeBigint(Long typeBigint) {
        this.typeBigint = typeBigint;
    }

    public String getTypeVarchar() {
        return typeVarchar;
    }

    public void setTypeVarchar(String typeVarchar) {
        this.typeVarchar = typeVarchar;
    }

    public Byte getTypeTinyint() {
        return typeTinyint;
    }

    public void setTypeTinyint(Byte typeTinyint) {
        this.typeTinyint = typeTinyint;
    }

    public Short getTypeSmallint() {
        return typeSmallint;
    }

    public void setTypeSmallint(Short typeSmallint) {
        this.typeSmallint = typeSmallint;
    }

    public Integer getTypeMediumint() {
        return typeMediumint;
    }

    public void setTypeMediumint(Integer typeMediumint) {
        this.typeMediumint = typeMediumint;
    }

    public Integer getTypeInt() {
        return typeInt;
    }

    public void setTypeInt(Integer typeInt) {
        this.typeInt = typeInt;
    }

    public Date getTypeDate() {
        return typeDate;
    }

    public void setTypeDate(Date typeDate) {
        this.typeDate = typeDate;
    }

    public Float getTypeFloat() {
        return typeFloat;
    }

    public void setTypeFloat(Float typeFloat) {
        this.typeFloat = typeFloat;
    }

    public Double getTypeDouble() {
        return typeDouble;
    }

    public void setTypeDouble(Double typeDouble) {
        this.typeDouble = typeDouble;
    }

    public BigDecimal getTypeDecimal() {
        return typeDecimal;
    }

    public void setTypeDecimal(BigDecimal typeDecimal) {
        this.typeDecimal = typeDecimal;
    }

    public Date getTypeDatetime() {
        return typeDatetime;
    }

    public void setTypeDatetime(Date typeDatetime) {
        this.typeDatetime = typeDatetime;
    }

    public Date getTypeTimestamp() {
        return typeTimestamp;
    }

    public void setTypeTimestamp(Date typeTimestamp) {
        this.typeTimestamp = typeTimestamp;
    }

    public Date getTypeTime() {
        return typeTime;
    }

    public void setTypeTime(Date typeTime) {
        this.typeTime = typeTime;
    }

    public Date getTypeYear() {
        return typeYear;
    }

    public void setTypeYear(Date typeYear) {
        this.typeYear = typeYear;
    }

    public String getTypeChar() {
        return typeChar;
    }

    public void setTypeChar(String typeChar) {
        this.typeChar = typeChar;
    }

    public String getTypeTinytext() {
        return typeTinytext;
    }

    public void setTypeTinytext(String typeTinytext) {
        this.typeTinytext = typeTinytext;
    }

    public String getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(String typeEnum) {
        this.typeEnum = typeEnum;
    }

    public String getTypeSet() {
        return typeSet;
    }

    public void setTypeSet(String typeSet) {
        this.typeSet = typeSet;
    }

    public Boolean getTypeBool() {
        return typeBool;
    }

    public void setTypeBool(Boolean typeBool) {
        this.typeBool = typeBool;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public byte[] getTypeTinyblob() {
        return typeTinyblob;
    }

    public void setTypeTinyblob(byte[] typeTinyblob) {
        this.typeTinyblob = typeTinyblob;
    }

    public byte[] getTypeBlob() {
        return typeBlob;
    }

    public void setTypeBlob(byte[] typeBlob) {
        this.typeBlob = typeBlob;
    }

    public byte[] getTypeMediumblob() {
        return typeMediumblob;
    }

    public void setTypeMediumblob(byte[] typeMediumblob) {
        this.typeMediumblob = typeMediumblob;
    }

    public String getTypeMediumtext() {
        return typeMediumtext;
    }

    public void setTypeMediumtext(String typeMediumtext) {
        this.typeMediumtext = typeMediumtext;
    }

    public byte[] getTypeLongblob() {
        return typeLongblob;
    }

    public void setTypeLongblob(byte[] typeLongblob) {
        this.typeLongblob = typeLongblob;
    }

    public String getTypeLongtext() {
        return typeLongtext;
    }

    public void setTypeLongtext(String typeLongtext) {
        this.typeLongtext = typeLongtext;
    }

    public byte[] getTypeBinary() {
        return typeBinary;
    }

    public void setTypeBinary(byte[] typeBinary) {
        this.typeBinary = typeBinary;
    }

    public byte[] getTypeVarbinary() {
        return typeVarbinary;
    }

    public void setTypeVarbinary(byte[] typeVarbinary) {
        this.typeVarbinary = typeVarbinary;
    }

    /**
     * Copy properties of this object to {@code dest} object.
     * @param dest the object to copy properties to.
     * @return the dest object.
     */
    public BaseAllDataTypes copy(BaseAllDataTypes dest) {
        if (dest == null) {
            throw new NullPointerException("dest must not be null.");
        }
        dest.typeBigint = this.typeBigint;
        dest.typeVarchar = this.typeVarchar;
        dest.typeTinyint = this.typeTinyint;
        dest.typeSmallint = this.typeSmallint;
        dest.typeMediumint = this.typeMediumint;
        dest.typeInt = this.typeInt;
        dest.typeDate = this.typeDate;
        dest.typeFloat = this.typeFloat;
        dest.typeDouble = this.typeDouble;
        dest.typeDecimal = this.typeDecimal;
        dest.typeDatetime = this.typeDatetime;
        dest.typeTimestamp = this.typeTimestamp;
        dest.typeTime = this.typeTime;
        dest.typeYear = this.typeYear;
        dest.typeChar = this.typeChar;
        dest.typeTinytext = this.typeTinytext;
        dest.typeEnum = this.typeEnum;
        dest.typeSet = this.typeSet;
        dest.typeBool = this.typeBool;
        dest.typeText = this.typeText;
        dest.typeTinyblob = this.typeTinyblob;
        dest.typeBlob = this.typeBlob;
        dest.typeMediumblob = this.typeMediumblob;
        dest.typeMediumtext = this.typeMediumtext;
        dest.typeLongblob = this.typeLongblob;
        dest.typeLongtext = this.typeLongtext;
        dest.typeBinary = this.typeBinary;
        dest.typeVarbinary = this.typeVarbinary;
        return dest;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("[");
        sb.append("typeBigint=").append(typeBigint);
        sb.append(", typeVarchar=").append(typeVarchar);
        sb.append(", typeTinyint=").append(typeTinyint);
        sb.append(", typeSmallint=").append(typeSmallint);
        sb.append(", typeMediumint=").append(typeMediumint);
        sb.append(", typeInt=").append(typeInt);
        sb.append(", typeDate=").append(typeDate);
        sb.append(", typeFloat=").append(typeFloat);
        sb.append(", typeDouble=").append(typeDouble);
        sb.append(", typeDecimal=").append(typeDecimal);
        sb.append(", typeDatetime=").append(typeDatetime);
        sb.append(", typeTimestamp=").append(typeTimestamp);
        sb.append(", typeTime=").append(typeTime);
        sb.append(", typeYear=").append(typeYear);
        sb.append(", typeChar=").append(typeChar);
        sb.append(", typeTinytext=").append(typeTinytext);
        sb.append(", typeEnum=").append(typeEnum);
        sb.append(", typeSet=").append(typeSet);
        sb.append(", typeBool=").append(typeBool);
        sb.append(", typeText=").append(typeText);
        sb.append(", typeTinyblob=").append(typeTinyblob);
        sb.append(", typeBlob=").append(typeBlob);
        sb.append(", typeMediumblob=").append(typeMediumblob);
        sb.append(", typeMediumtext=").append(typeMediumtext);
        sb.append(", typeLongblob=").append(typeLongblob);
        sb.append(", typeLongtext=").append(typeLongtext);
        sb.append(", typeBinary=").append(typeBinary);
        sb.append(", typeVarbinary=").append(typeVarbinary);
        sb.append("]");
        return sb.toString();
    }
}