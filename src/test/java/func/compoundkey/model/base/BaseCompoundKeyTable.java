package func.compoundkey.model.base;

/*
 * Do not modify, it will be overwritten every time yobatis runs.
 */
public abstract class BaseCompoundKeyTable {
    protected Integer pk1;

    protected String pk2;

    protected String f3;

    public Integer getPk1() {
        return pk1;
    }

    public void setPk1(Integer pk1) {
        this.pk1 = pk1;
    }

    public String getPk2() {
        return pk2;
    }

    public void setPk2(String pk2) {
        this.pk2 = pk2;
    }

    public String getF3() {
        return f3;
    }

    public void setF3(String f3) {
        this.f3 = f3;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("[");
        sb.append("pk1=").append(pk1);
        sb.append(", pk2=").append(pk2);
        sb.append(", f3=").append(f3);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties of this object to {@code dest} object.
     * @param dest the object to copy properties to.
     * @return the dest object.
     */
    public BaseCompoundKeyTable copyTo(BaseCompoundKeyTable dest) {
        if (dest == null) {
            throw new NullPointerException("dest must not be null.");
        }
        dest.pk1 = this.pk1;
        dest.pk2 = this.pk2;
        dest.f3 = this.f3;
        return dest;
    }
}