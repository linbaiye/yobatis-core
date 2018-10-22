package func.autoinckey.model.base;

import java.math.BigDecimal;

/*
 * Do not modify, it will be overwritten every time yobatis runs.
 */
public abstract class BaseCustomer {
    protected Long id;

    protected String f1;

    protected BigDecimal f3;

    protected String f2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    public BigDecimal getF3() {
        return f3;
    }

    public void setF3(BigDecimal f3) {
        this.f3 = f3;
    }

    public String getF2() {
        return f2;
    }

    public void setF2(String f2) {
        this.f2 = f2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("[");
        sb.append("id=").append(id);
        sb.append(", f1=").append(f1);
        sb.append(", f3=").append(f3);
        sb.append(", f2=").append(f2);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties of this object to {@code dest} object.
     * @param dest the object to copy properties to.
     * @return the dest object.
     */
    public BaseCustomer copyTo(BaseCustomer dest) {
        if (dest == null) {
            throw new NullPointerException("dest must not be null.");
        }
        dest.id = this.id;
        dest.f1 = this.f1;
        dest.f3 = this.f3;
        dest.f2 = this.f2;
        return dest;
    }
}