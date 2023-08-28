package oy.tol.tra;

public class Person implements Comparable<Person>{
    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }

    @Override
    public int hashCode() {
        int hash = 33;
        for(int i = 0; i < firstName.length() - 1; i++) {
            hash = hash * 33 + firstName.charAt(i);
        }
        for(int i = 0; i < lastName.length() - 1; i++) {
            hash = hash * 33 + lastName.charAt(i);
        }
        hash = hash + firstName.length() + lastName.length();
        return hash;
    }
    
    @Override
    public boolean equals(Object person) {
        if (this == person) {
            return true;
        }
        if (person == null) {
            return false;
        }
        if (getClass() != person.getClass()) {
            return false;
        }
        Person other = (Person) person;
        if (getFullName() == null){
            if(other.getFullName() != null){
                return false;
            }
        } else if (!getFullName().equals(other.getFullName())) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Person person) {
    return this.getFullName().compareTo(person.getFullName());
    }

}
