/**
 * JobData is the entity we will be using to represent the particular data of a Job Posting
 */

package jobdataportal;

import java.util.ArrayList;
import java.util.List;

public class JobData {
    private String company;
    private String position;
    private String seniority;
    private String currency;
    private double salary;
    private List<String> location;
    private List<String> stack;

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
        if(this.location == null) {
            this.location = new ArrayList<>();
            this.location.add(location);
        } else {
            this.location.add(location);
        }
    }

    public List<String> getLocation() {
        return this.location;
    }

    public void setStack(String stack) {
        if (this.stack == null) {
            this.stack = new ArrayList<>();
            this.stack.add(stack);
        } else {
            this.stack.add(stack);
        }
    }

    public List<String> getStack() {
        return this.stack;
    }

    @Override
    public String toString() {
        try {
            return seniority + " " + position + " at " + company + " / " + location.toString() + "\n"
                + salary + currency + "\n"
                + stack.toString();
        } catch (NullPointerException e) {
            return seniority + " " + position + " at " + company + "\n"
                + salary + currency;
        }
    }
}
