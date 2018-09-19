package func.allkey.model.base;

public abstract class BaseAllKeyTable {
    private Integer pk1;

    private String pk2;

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

    /**
     * Copy properties of this object to {@code dest} object.
     * @param dest the object to copy properties to.
     * @return the dest object.
     */
    public BaseAllKeyTable copy(BaseAllKeyTable dest) {
        if (dest == null) {
            throw new NullPointerException("dest must not be null.");
        }
        dest.pk1 = this.pk1;
        dest.pk2 = this.pk2;
        return dest;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("[");
        sb.append("pk1=").append(pk1);
        sb.append(", pk2=").append(pk2);
        sb.append("]");
        return sb.toString();
    }
}