/**
 * JobData is the entity we will be using to represent the particular data of a Job Posting
 */

package jobdataportal;

public class JobData {
    private String company;
    private String position;
    private String seniority;
    private String currency;
    private double salary;
    private String location;
    private String[] stack;

    JobData() {
        this.company = null;
        this.position = null;
        this.seniority = null;
        this.currency = null;
        this.salary = 0;
        this.location = null;
        this.stack = null;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return this.position;
    }

    public void setSeniority(String seniority) {
        this.seniority = seniority;
    }

    public String getSeniority() {
        return this.seniority;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return this.salary;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    public void setStack(String[] stack) {
        this.stack = stack;
    }

    public String[] getStack() {
        return this.stack;
    }

    @Override
    public String toString() {
        if (stack != null) {
            return seniority + " " + position + " at " + company + "/" + location + "\n"
                + salary + currency + "\n"
                + stack.toString();
        }
        return seniority + " " + position + " at " + company + "/" + location + "\n"
            + salary + currency + "\n";
    }
}
